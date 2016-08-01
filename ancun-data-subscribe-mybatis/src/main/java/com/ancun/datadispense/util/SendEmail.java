package com.ancun.datadispense.util;

import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.conf.EmailConf;
import com.ancun.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.apache.coyote.http11.Constants.a;

/**
 * 邮件发送
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/3/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class SendEmail {

    private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);

    /**
     * 发送邮件
     */
    public static void send(EmailConf emailConf, String message) {
        //不发送
        if (!emailConf.getOnOff()) {
            return;
        }
        String[] array = emailConf.getSendTo().split(",");
        for (String email : array) {
            Map<String, String> content = new HashMap<String, String>();
            content.put("emailTo", email);
            content.put("subject", emailConf.getSubject());
            content.put("message", message);

            String requestJson = JsonUtils.toJson("email", content);
            String emailBack = null ;
            EmailResponse emailResponse = null;
            // update By zkai on 2016/05/30
            try {
                emailBack = HttpClientUtils.postJson2String(emailConf.getServerUrl(), requestJson, BizConstants.CHARSETNAME_DEFAULT);
                emailResponse = JsonUtils.fromJson(emailBack,EmailResponse.class);
            } catch (Exception e) {
                logger.error("业务异常,发送邮件异常，邮件地址：{}; 邮件内容：{}；发送通道：{}。", emailConf.getSendTo(), message, null);
            }

            if(!"100000".equals(emailResponse.getInfo().getCode())){
                logger.error("业务异常,发送邮件异常，邮件地址：{}; 邮件内容：{}；发送通道：{}。", emailConf.getSendTo(), message, null);
            }
        }
    }

    public class EmailResponse{
        private EmailInfo info;

        public EmailInfo getInfo() {
            return info;
        }

        public void setInfo(EmailInfo info) {
            this.info = info;
        }
    }

    public class EmailInfo{
        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
