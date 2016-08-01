package com.ancun.datasubscribe.listener;

import com.ancun.utils.EncryptUtil;
import com.ancun.utils.encrypt.BootstrapPasswdInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * spring启动监听，当使用aes加密时要启动aes密码输入启动器
 *
 * @Created on 2016年01月26日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class AESEncript implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger log = LoggerFactory.getLogger(AESEncript.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        ConfigurableEnvironment environment = applicationEnvironmentPreparedEvent.getEnvironment();
        // 加密类型
        String encrypt = environment.getProperty("common.encrypt");

        // 如果是AES加密
        if (EncryptUtil.EncryptEnum.getEncrypt(encrypt) == EncryptUtil.EncryptEnum.AES) {
            // AES加密启动密钥字符串
            String datakeyforbootstrap = environment.getProperty("common.datakeyforbootstrap");
            // 密码可见性
            Boolean visiblePassword = environment.getProperty("common.visiblePassword", Boolean.class, false);
            // 启动aes密码输入
            BootstrapPasswdInit bp = new BootstrapPasswdInit();
            bp.init(visiblePassword, datakeyforbootstrap);
        }

    }
}
