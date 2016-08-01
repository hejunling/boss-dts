package com.ancun.datadispense.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 队列控制配置
 * User: zkai
 * Date: 2016/6/7
 * Time: 9:35
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

@Configuration
@ConfigurationProperties(prefix = "queueConf")
public class QueueConf {

    /**
     * 录音队列开关
     */
    private Boolean voiceOnOff ;

    /**
     * 套餐队列开关
     */
    private Boolean taocanOnOff ;

    /**
     * 用户队列开关
     */
    private Boolean userOnOff ;

    /**
     * 用户生命周期队列开关
     */
    private Boolean userLifeOnOff ;

    public Boolean getVoiceOnOff() {
        return voiceOnOff;
    }

    public void setVoiceOnOff(Boolean voiceOnOff) {
        this.voiceOnOff = voiceOnOff;
    }

    public Boolean getTaocanOnOff() {
        return taocanOnOff;
    }

    public void setTaocanOnOff(Boolean taocanOnOff) {
        this.taocanOnOff = taocanOnOff;
    }

    public Boolean getUserOnOff() {
        return userOnOff;
    }

    public void setUserOnOff(Boolean userOnOff) {
        this.userOnOff = userOnOff;
    }

    public Boolean getUserLifeOnOff() {
        return userLifeOnOff;
    }

    public void setUserLifeOnOff(Boolean userLifeOnOff) {
        this.userLifeOnOff = userLifeOnOff;
    }
}
