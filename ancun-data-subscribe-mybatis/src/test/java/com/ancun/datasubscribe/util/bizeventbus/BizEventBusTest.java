package com.ancun.datasubscribe.util.bizeventbus;

import com.google.common.base.Throwables;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 测试事件广播
 *
 * @Created on 2016年03月30日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizEventBusTest {

    BizEventBus eventBus = null;

    @Before
    public void initEventBus() {
        eventBus = new BizEventBus(new SubscriberExceptionHandler() {
            @Override
            public void handleException(Throwable exception, SubscriberExceptionContext context) {
                throw Throwables.propagate(exception);
            }
        });
        DemoListener demo = new DemoListener();
        eventBus.register(demo);
    }

    @Test(expected= RuntimeException.class)
    public void testExceptionInPost() throws NoSuchMethodException {
        eventBus.post(PostEvent.builde().setEvent(new Integer(1)));
        DemoListener demo = new DemoListener();
        Method method = demo.getClass().getMethod("throwEeception", Integer.class);
        Subscribe subscribe1 = method.getAnnotation(Subscribe.class);

        DemoListener2 demo2 = new DemoListener2();
        Method method2 = demo.getClass().getMethod("throwEeception", Integer.class);
        Subscribe subscribe2 = method.getAnnotation(Subscribe.class);

        Subscribe subscribe3 = new Subscribe() {
            @Override
            public String tableName() {
                return "DX";
            }

            @Override
            public String bizNo() {
                return "DX";
            }

            @Override
            public String userType() {
                return "DX";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Subscribe.class;
            }
        };

        System.out.println(subscribe1 == subscribe2);
        System.out.println(subscribe1 == subscribe3);

    }
}
