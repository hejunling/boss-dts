package com.ancun;

import com.ancun.common.datasource.DynamicDataSourceRegister;
import com.ancun.datasubscribe.listener.AESEncript;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

/**
 * 数据订阅服务启动入口
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2015年12月07日
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableJms
@Import({DynamicDataSourceRegister.class}) // 注册动态多数据源
public class Application {

    /**
     * 用户Queue
     *
     * @return
     */
    @Bean(name = "userQueue")
    public Queue userQueue() {
        return new ActiveMQQueue("user.queue");
    }

    /**
     * 录音Queue
     *
     * @return
     */
    @Bean(name = "voiceQueue")
    public Queue VoiceQueue() {
        return new ActiveMQQueue("voice.queue");
    }

    /**
     * 套餐Queue
     *
     * @return
     */
    @Bean(name = "comboQueue")
    public Queue comboQueue() {
        return new ActiveMQQueue("combo.queue");
    }

    /**
     * 用户生命周期Queue
     *
     * @return
     */
    @Bean(name = "lifeCircleQueue")
    public Queue lifeCircleQueue() {
        return new ActiveMQQueue("lifeCircle.queue");
    }


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.addListeners(new AESEncript());
        app.run(args);
    }

}
