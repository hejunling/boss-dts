package com.ancun.datasyn.task;

import com.ancun.common.persistence.model.master.BizProvice;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.datasyn.cache.ICacheConfigService;
import com.ancun.datasyn.constant.CacheConstant;
import com.ancun.datasyn.service.master.IBizTimerCofigService;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 定时器初始化时启动
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/5/13
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class ScheduleRunner implements CommandLineRunner {

    public static final Logger logger = LoggerFactory.getLogger(ScheduleRunner.class);

    @Resource
    private IBizTimerCofigService bizTimerCofigService;


    @Resource
    private ICacheConfigService iCacheConfigService;

    @Resource
    private SchedulerSynTask schedulerSynTask;


    /**
     * 程序启动时 启动定时器
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {

        initBizRpcodeMap();

        List<BizTimerConfig> list = bizTimerCofigService.selectAllBizTimerConfigs();
        if (null == list || list.size() <= 0)
            return;
        logger.info("启动事件，定时钟个数{}", list.size());

        for (BizTimerConfig config : list) {
            initScheduler(config);
        }

    }


    /**
     * 初始化业务与省份对应map
     */
    private void initBizRpcodeMap() {
        List<BizProvice> proviceList = iCacheConfigService.queryAllBizProvice();
        if (null == proviceList || proviceList.size() <= 0)
            return;
        for (BizProvice provice : proviceList) {
            if (provice.getBizNo().startsWith("DX-")) {
                CacheConstant.DX_PROVINCE_MAP.put(provice.getRpcode(), provice.getBizNo());
            }
            if (provice.getBizNo().startsWith("LT-")) {
                CacheConstant.LT_PROVINCE_MAP.put(provice.getRpcode(), provice.getBizNo());
            }
        }
    }

    /**
     * 初始化定时器
     *
     * @param timeConfig
     */
    private void initScheduler(final BizTimerConfig timeConfig) {

        class MyScheduledService extends AbstractScheduledService {

            @Override
            protected void runOneIteration() {
                // 处理异常，这里如果抛出异常，会使服务状态变为failed同时导致任务终止
                try {
                    schedulerSynTask.run(timeConfig.getMoudle());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected Scheduler scheduler() {
                return Scheduler.newFixedDelaySchedule(0, timeConfig.getIntervalMinute(), TimeUnit.MINUTES);
            }
        }

        MyScheduledService service = new MyScheduledService();
        //监听
        service.addListener(new Service.Listener() {
            @Override
            public void starting() {
                logger.info("服务开始启动,定时器模块={}", timeConfig.getMoudle());
            }

            @Override
            public void running() {
                logger.info("服务开始运行,定时器模块={}", timeConfig.getMoudle());
            }

            @Override
            public void stopping(Service.State from) {
                logger.info("服务关闭中,定时器模块={}", timeConfig.getMoudle());
            }

            @Override
            public void terminated(Service.State from) {
                logger.info("服务终止,定时器模块={}", timeConfig.getMoudle());
            }

            @Override
            public void failed(Service.State from, Throwable failure) {
                logger.info("失败，cause：{},定时器模块={}", failure.getCause(), timeConfig.getMoudle());
            }
        }, MoreExecutors.directExecutor());

        service.startAsync();

        logger.info("服务状态为:" + service.state());
    }


    /**
     * add by zkai
     * 录音异步线程池 初始化
     *
     * @return
     */
    @Bean(name = "voicePool")
    public ThreadPoolTaskExecutor cosumerVoiceTaskExecutor(
            @Value("${consumer.voicePoolSize}") int corePoolSize,
            @Value("${consumer.maxPoolSize}") int maxPoolSize) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        return executor;
    }

    /**
     * add by zkai
     * 用户异步线程池  初始化
     *
     * @return
     */
    @Bean(name = "userPool")
    public ThreadPoolTaskExecutor cosumerUserTaskExecutor(
            @Value("${consumer.userPoolSize}") int corePoolSize,
            @Value("${consumer.maxPoolSize}") int maxPoolSize) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        return executor;
    }


    /**
     * add by zkai
     * 用户生命周期异步线程池 初始化
     *
     * @return
     */
    @Bean(name = "userLifePool")
    public ThreadPoolTaskExecutor cosumerUserLifeTaskExecutor(
            @Value("${consumer.userLifePoolSize}") int corePoolSize,
            @Value("${consumer.maxPoolSize}") int maxPoolSize) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        return executor;
    }

    /**
     * add by zkai
     * 套餐异步线程池 初始化
     *
     * @return
     */
    @Bean(name = "taocanPool")
    public ThreadPoolTaskExecutor cosumerTaocanTaskExecutor(
            @Value("${consumer.taocanPoolSize}") int corePoolSize,
            @Value("${consumer.maxPoolSize}") int maxPoolSize) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        return executor;
    }

}
