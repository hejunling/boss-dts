package com.ancun.datadispense.bizConstants;

/**
 * CP联通注册类型枚举
 *
 * @Created on 2016年4月11日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
public enum UniSignupType {
	
    /** 短信 */
    SMS("6"),
    /** 彩信 */
    MMS("7");

    /** 值 */
    private String text;

    UniSignupType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
