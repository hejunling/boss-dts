package com.ancun.boss.business.utils.fptcsrvc;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.utils.HttpClientUtils;

public class UnicomFtpUtil {
	
	private static final Logger log = LoggerFactory.getLogger(UnicomFtpUtil.class);
	
	public static boolean fareRquest(String key, String type, String fareUrl, Object... params){
        // 默认计费开通成功
        boolean fareFlg = true;
        // 设置参数
        fareUrl = MessageFormat.format(fareUrl, params);
        try {
            // 发送计费开通请求
            byte[] result = HttpClientUtils.getBytes(fareUrl);
            // 开通失败
            if (result == null) {
                fareFlg = false;
            }
        } catch (Exception e) {
            log.error("业务异常,发送计费开通请求异常", e);
            fareFlg = false;
        }

        return fareFlg;
	}

}
