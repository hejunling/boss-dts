package com.ancun.datasubscribe.conf;

import com.ancun.utils.EncryptUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 共通配置参数
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016年01月04日
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Configuration
@ConfigurationProperties(prefix = "common")
public class Config {

    /**
     * 异步事件总线中线程池大小
     */
    private int threadsForEventBus;

    /**
     * 加密类型
     */
    private String encrypt;

    /**
     * aes加解密时使用密钥字符串
     */
    private String datakey;

    /**
     * aes启动密钥字符串
     */
    private String datakeyforbootstrap;

    /**
     * 取得真实密码
     *
     * @param encryptPass 已加密密码
     * @return 已解密的真实密码
     */
    public String getRealPassword(String encryptPass) {
        return EncryptUtil.decrypt(encrypt, encryptPass, datakey);
    }

    public String getBizPassword(String encryptPass) {
        return EncryptUtil.decrypt((EncryptUtil.EncryptEnum.DES).toString(), encryptPass, datakey);
    }


    public int getThreadsForEventBus() {
        return threadsForEventBus;
    }

    public void setThreadsForEventBus(int threadsForEventBus) {
        this.threadsForEventBus = threadsForEventBus;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getDatakey() {
        return datakey;
    }

    public void setDatakey(String datakey) {
        this.datakey = datakey;
    }

    public String getDatakeyforbootstrap() {
        return datakeyforbootstrap;
    }

    public void setDatakeyforbootstrap(String datakeyforbootstrap) {
        this.datakeyforbootstrap = datakeyforbootstrap;
    }
}