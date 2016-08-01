package com.ancun.datadispense.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 邮件发送相关配置
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/3/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Configuration
@ConfigurationProperties(prefix = "emailConf")
public class EmailConf {

    /**
     * 发送邮件开关
     */
    private Boolean onOff;

    /**
     * 邮件发送服务器路径
     */
    private String serverUrl;

    /**
     * 接收人
     */
    private String sendTo;

    /**
     * 邮件主题
     */
    private String subject;

    public Boolean getOnOff() {
        return onOff;
    }

    public void setOnOff(Boolean onOff) {
        this.onOff = onOff;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
