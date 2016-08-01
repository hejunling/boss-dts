package com.ancun.datasubscribe.util.bizeventbus;

import com.google.common.base.MoreObjects;
import com.google.common.util.concurrent.MoreExecutors;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 业务事件总线(参照guava 19.0的EventBus)
 *
 * @Created on 2016年03月03日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizEventBus {

    private static final Logger logger = Logger.getLogger(BizEventBus.class.getName());

    /** 未知事件相关 */
    public static final String DEAD_EVENT = "DEAD";

    private final String identifier;
    private final Executor executor;
    private final SubscriberExceptionHandler exceptionHandler;

    private final SubscriberRegistry subscribers = new SubscriberRegistry(this);
    private final Dispatcher dispatcher;

    /**
     * Creates a new EventBus named "default".
     */
    public BizEventBus() {
        this("default");
    }

    /**
     * Creates a new EventBus with the given {@code identifier}.
     *
     * @param identifier  a brief name for this bus, for logging purposes.  Should
     *                    be a valid Java identifier.
     */
    public BizEventBus(String identifier) {
        this(identifier, MoreExecutors.directExecutor(),
                Dispatcher.perThreadDispatchQueue(), LoggingHandler.INSTANCE);
    }

    /**
     * Creates a new EventBus with the given {@link SubscriberExceptionHandler}.
     *
     * @param exceptionHandler Handler for subscriber exceptions.
     * @since 16.0
     */
    public BizEventBus(SubscriberExceptionHandler exceptionHandler) {
        this("default",
                MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), exceptionHandler);
    }

    BizEventBus(String identifier, Executor executor, Dispatcher dispatcher,
             SubscriberExceptionHandler exceptionHandler) {
        this.identifier = checkNotNull(identifier);
        this.executor = checkNotNull(executor);
        this.dispatcher = checkNotNull(dispatcher);
        this.exceptionHandler = checkNotNull(exceptionHandler);
    }

    /**
     * Returns the identifier for this event bus.
     *
     * @since 19.0
     */
    public final String identifier() {
        return identifier;
    }

    /**
     * Returns the default executor this event bus uses for dispatching events to subscribers.
     */
    final Executor executor() {
        return executor;
    }

    /**
     * Handles the given exception thrown by a subscriber with the given context.
     */
    public void handleSubscriberException(Throwable e, SubscriberExceptionContext context) {
        checkNotNull(e);
        checkNotNull(context);
        exceptionHandler.handleException(e, context);
    }

    /**
     * Registers all subscriber methods on {@code object} to receive events.
     *
     * @param object  object whose subscriber methods should be registered.
     */
    public void register(Object object) {
        subscribers.register(object);
    }

    /**
     * Unregisters all subscriber methods on a registered {@code object}.
     *
     * @param object  object whose subscriber methods should be unregistered.
     * @throws IllegalArgumentException if the object was not previously registered.
     */
    public void unregister(Object object) {
        subscribers.unregister(object);
    }

    /**
     * Posts an event to all registered subscribers.  This method will return
     * successfully after the event has been posted to all subscribers, and
     * regardless of any exceptions thrown by subscribers.
     *
     * <p>If no subscribers have been subscribed for {@code event}'s class, and
     * {@code event} is not already a {@link DeadEvent}, it will be wrapped in a
     * DeadEvent and reposted.
     *
     * @param postEvent  event to post.
     */
    public void post(PostEvent postEvent) {
        Iterator<Subscriber> eventSubscribers = subscribers.getSubscribers(postEvent);
        if (eventSubscribers.hasNext()) {
            dispatcher.dispatch(postEvent.getEvent(), eventSubscribers);
        } else if (!(postEvent.getEvent() instanceof DeadEvent)) {
            // the event had no subscribers and was not itself a DeadEvent
//            post(PostEvent.builde()
//                    .setBizNo(DEAD_EVENT).setUserType(DEAD_EVENT)
//                    .setEvent(new DeadEvent(this, postEvent.getEvent())));
            throw new RuntimeException("该事件无任何监听者处理");
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(identifier)
                .toString();
    }

    /**
     * Simple logging handler for subscriber exceptions.
     */
    static final class LoggingHandler implements SubscriberExceptionHandler {
        static final LoggingHandler INSTANCE = new LoggingHandler();

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            Logger logger = logger(context);
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, message(context), exception);
            }
        }

        private static Logger logger(SubscriberExceptionContext context) {
            return Logger.getLogger(BizEventBus.class.getName() + "." + context.getEventBus().identifier());
        }

        private static String message(SubscriberExceptionContext context) {
            Method method = context.getSubscriberMethod();
            return "Exception thrown by subscriber method "
                    + method.getName() + '(' + method.getParameterTypes()[0].getName() + ')'
                    + " on subscriber " + context.getSubscriber()
                    + " when dispatching event: " + context.getEvent();
        }
    }

}
