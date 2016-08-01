package com.ancun.datasubscribe.eventbus.listener;

import com.ancun.datasubscribe.eventbus.DataSubscribeEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

/**
 * 基础监听器
 *
 * @Created on 2016年03月04日
 * @author 静好
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public abstract class BaseListener implements InitializingBean {

    /** 日志操作类 */
    protected final Logger logger;

    /** 事件总线 */
    @Resource
    protected DataSubscribeEventBus eventBus;

    /**
     * 实例化日志类
     */
    public BaseListener() {
        logger = LoggerFactory.getLogger(this.getClass().getName());
    }

    /**
     * Bean初始化完全后，将自己注册到事件总线中
     *
     * @throws Exception    异常信息
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        eventBus.register(this);
    }
}
