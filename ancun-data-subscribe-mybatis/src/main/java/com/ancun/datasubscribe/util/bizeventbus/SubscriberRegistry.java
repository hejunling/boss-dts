package com.ancun.datasubscribe.util.bizeventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.*;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 事件总线监听注册器(参照guava 19.0的EventBus)
 *
 * @Created on 2016年03月03日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class SubscriberRegistry {
    /**
     * All registered subscribers, indexed by event type.
     *
     * <p>The {@link CopyOnWriteArraySet} values make it easy and relatively lightweight to get an
     * immutable snapshot of all current subscribers to an event without any locking.
     */
    private final ConcurrentMap<SubscriberIdentifier, CopyOnWriteArraySet<Subscriber>> subscribers =
            Maps.newConcurrentMap();

    /**
     * The event bus this registry belongs to.
     */
    private final BizEventBus bus;

    SubscriberRegistry(BizEventBus bus) {
        this.bus = checkNotNull(bus);
    }

    /**
     * Registers all subscriber methods on the given listener object.
     */
    void register(Object listener) {
        Multimap<SubscriberIdentifier, Subscriber> listenerMethods = findAllSubscribers(listener);

        for (Map.Entry<SubscriberIdentifier, Collection<Subscriber>> entry : listenerMethods.asMap().entrySet()) {
            SubscriberIdentifier identifier = entry.getKey();
            Collection<Subscriber> eventMethodsInListener = entry.getValue();

            CopyOnWriteArraySet<Subscriber> eventSubscribers = subscribers.get(identifier);

            if (eventSubscribers == null) {
                CopyOnWriteArraySet<Subscriber> newSet = new CopyOnWriteArraySet<Subscriber>();
                eventSubscribers = MoreObjects.firstNonNull(
                        subscribers.putIfAbsent(identifier, newSet), newSet);
            }

            eventSubscribers.addAll(eventMethodsInListener);
        }
    }

    /**
     * Unregisters all subscribers on the given listener object.
     */
    void unregister(Object listener) {
        Multimap<SubscriberIdentifier, Subscriber> listenerMethods = findAllSubscribers(listener);

        for (Map.Entry<SubscriberIdentifier, Collection<Subscriber>> entry : listenerMethods.asMap().entrySet()) {
            SubscriberIdentifier identifier = entry.getKey();
            Collection<Subscriber> listenerMethodsForType = entry.getValue();

            CopyOnWriteArraySet<Subscriber> currentSubscribers = subscribers.get(identifier);
            if (currentSubscribers == null || !currentSubscribers.removeAll(listenerMethodsForType)) {
                // if removeAll returns true, all we really know is that at least one subscriber was
                // removed... however, barring something very strange we can assume that if at least one
                // subscriber was removed, all subscribers on listener for that event type were... after
                // all, the definition of subscribers on a particular class is totally static
                throw new IllegalArgumentException(
                        "missing event subscriber for an annotated method. Is " + listener + " registered?");
            }

            // don't try to remove the set if it's empty; that can't be done safely without a lock
            // anyway, if the set is empty it'll just be wrapping an array of length 0
        }
    }

    @VisibleForTesting
    Set<Subscriber> getSubscribersForTesting(SubscriberIdentifier identifier) {
        return MoreObjects.firstNonNull(subscribers.get(identifier), ImmutableSet.<Subscriber>of());
    }

    /**
     * Gets an iterator representing an immutable snapshot of all subscribers to the given event at
     * the time this method is called.
     */
    Iterator<Subscriber> getSubscribers(PostEvent event) {
        ImmutableSet<SubscriberIdentifier> identifiers = flattenHierarchy(fromPostEvent(event));

        List<Iterator<Subscriber>> subscriberIterators =
                Lists.newArrayListWithCapacity(identifiers.size());

        for (SubscriberIdentifier identifier : identifiers) {
            CopyOnWriteArraySet<Subscriber> eventSubscribers = subscribers.get(identifier);
            if (eventSubscribers != null) {
                // eager no-copy snapshot
                subscriberIterators.add(eventSubscribers.iterator());
            }
        }

        return Iterators.concat(subscriberIterators.iterator());
    }

    /**
     * A thread-safe cache that contains the mapping from each class to all methods in that class and
     * all super-classes, that are annotated with {@code @Subscribe}. The cache is shared across all
     * instances of this class; this greatly improves performance if multiple EventBus instances are
     * created and objects of the same class are registered on all of them.
     */
    private static final LoadingCache<Class<?>, ImmutableList<Method>> subscriberMethodsCache =
            CacheBuilder.newBuilder()
                    .weakKeys()
                    .build(new CacheLoader<Class<?>, ImmutableList<Method>>() {
                        @Override
                        public ImmutableList<Method> load(Class<?> concreteClass) throws Exception {
                            return getAnnotatedMethodsNotCached(concreteClass);
                        }
                    });

    /**
     * Returns all subscribers for the given listener grouped by the type of event they subscribe to.
     */
    private Multimap<SubscriberIdentifier, Subscriber> findAllSubscribers(Object listener) {
        Multimap<SubscriberIdentifier, Subscriber> methodsInListener = HashMultimap.create();
        Class<?> clazz = listener.getClass();
        for (Method method : getAnnotatedMethods(clazz)) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> eventType = parameterTypes[0];
            Subscribe subscribe = method.getAnnotation(Subscribe.class);
            SubscriberIdentifier identifier =
                    new SubscriberIdentifier(subscribe.tableName().toLowerCase(), subscribe.userType(), eventType);
            methodsInListener.put(identifier, Subscriber.create(bus, listener, method));
        }
        return methodsInListener;
    }

    private static ImmutableList<Method> getAnnotatedMethods(Class<?> clazz) {
        return subscriberMethodsCache.getUnchecked(clazz);
    }

    private static ImmutableList<Method> getAnnotatedMethodsNotCached(Class<?> clazz) {
        Set<? extends Class<?>> supertypes = TypeToken.of(clazz).getTypes().rawTypes();
        Map<MethodIdentifier, Method> identifiers = Maps.newHashMap();
        for (Class<?> supertype : supertypes) {
            for (Method method : supertype.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Subscribe.class) && !method.isSynthetic()) {
                    // TODO(cgdecker): Should check for a generic parameter type and error out
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    checkArgument(parameterTypes.length == 1,
                            "Method %s has @Subscribe annotation but has %s parameters."
                                    + "Subscriber methods must have exactly 1 parameter.",
                            method, parameterTypes.length);

                    MethodIdentifier ident = new MethodIdentifier(method);
                    if (!identifiers.containsKey(ident)) {
                        identifiers.put(ident, method);
                    }
                }
            }
        }
        return ImmutableList.copyOf(identifiers.values());
    }

    /**
     * Global cache of classes to their flattened hierarchy of supertypes.
     */
    private static final LoadingCache<SubscriberIdentifier, ImmutableSet<SubscriberIdentifier>> flattenHierarchyCache =
            CacheBuilder.newBuilder()
                    .weakKeys()
                    .build(new CacheLoader<SubscriberIdentifier, ImmutableSet<SubscriberIdentifier>>() {
                        @SuppressWarnings("RedundantTypeArguments") // <Class<?>> is actually needed to compile
                        @Override
                        public ImmutableSet<SubscriberIdentifier> load(final SubscriberIdentifier concreteIdentifier) {

                            // 取得当前事件类的所有父类和接口列表
                            ImmutableSet<Class<?>> eventTypes = ImmutableSet.<Class<?>>copyOf(
                                    TypeToken.of(concreteIdentifier.eventType).getTypes().rawTypes());

                            // 将类型列表转换成监听身份列表
                            Set<SubscriberIdentifier> identifiers = FluentIterable.from(eventTypes).transform(new Function<Class<?>, SubscriberIdentifier>() {
                                @Nullable
                                @Override
                                public SubscriberIdentifier apply(@Nullable Class<?> input) {
//                                    return new SubscriberIdentifier(concreteIdentifier.tableName, concreteIdentifier.bizNo, concreteIdentifier.userType, input);
                                    return new SubscriberIdentifier(concreteIdentifier.tableName.toLowerCase(), concreteIdentifier.userType, input);
                                }
                            }).toSet();

                            return ImmutableSet.copyOf(identifiers);
                        }
                    });

    /**
     * Flattens a class's type hierarchy into a set of {@code Class} objects including all
     * superclasses (transitively) and all interfaces implemented by these superclasses.
     */
    @VisibleForTesting
    static ImmutableSet<SubscriberIdentifier> flattenHierarchy(SubscriberIdentifier concreteIdentifier) {
        try {
            return flattenHierarchyCache.getUnchecked(concreteIdentifier);
        } catch (UncheckedExecutionException e) {
            throw Throwables.propagate(e.getCause());
        }
    }

    /**
     * 将广播参数转换为订阅者标识
     *
     * @param event 广播参数
     * @return      订阅者标识
     */
    private SubscriberIdentifier fromPostEvent(PostEvent event){
//        return new SubscriberIdentifier(event.getTableName(), event.getBizNo(), event.getUserType(), event.getEvent().getClass());
        return new SubscriberIdentifier(event.getTableName().toLowerCase(), event.getUserType(), event.getEvent().getClass());
    }

    private static final class MethodIdentifier {

        private final String name;
        private final List<Class<?>> parameterTypes;

        MethodIdentifier(Method method) {
            this.name = method.getName();
            this.parameterTypes = Arrays.asList(method.getParameterTypes());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name, parameterTypes);
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (o instanceof MethodIdentifier) {
                MethodIdentifier ident = (MethodIdentifier) o;
                return name.equals(ident.name) && parameterTypes.equals(ident.parameterTypes);
            }
            return false;
        }
    }

    private static final class SubscriberIdentifier {

        private final String tableName;
//        private final String bizNo;
        private final String userType;
        private final Class<?> eventType;

//        public SubscriberIdentifier(String tableName, String bizNo, String userType, Class<?> eventType) {
//            this.tableName = tableName;
//            this.bizNo = bizNo;
//            this.userType = userType;
//            this.eventType = eventType;
//        }

        public SubscriberIdentifier(String tableName, String userType, Class<?> eventType) {
            this.tableName = tableName;
//            this.bizNo = bizNo;
            this.userType = userType;
            this.eventType = eventType;
        }

        @Override
        public int hashCode() {
//            return Objects.hashCode(bizNo, userType, eventType);
            return Objects.hashCode(userType, eventType);
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (o instanceof SubscriberIdentifier) {
                SubscriberIdentifier ident = (SubscriberIdentifier) o;
//                return bizNo.equals(ident.bizNo) && userType.equals(ident.userType) && eventType == ident.eventType;
                return tableName.equals(ident.tableName) && userType.equals(ident.userType) && eventType == ident.eventType;
            }
            return false;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("SubscriberIdentifier")
//                    .add("bizNo", bizNo)
                    .add("eventType", eventType)
                    .add("userType", userType)
                    .add("tableName", tableName)
                    .toString();
        }
    }
}
