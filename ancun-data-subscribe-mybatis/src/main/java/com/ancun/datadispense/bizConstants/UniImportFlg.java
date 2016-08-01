package com.ancun.datadispense.bizConstants;

/**
 * CP联通后台导入枚举
 *
 * @Created on 2016年4月11日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
public enum UniImportFlg {
	
    /** 否 */
    NO("0"),
    /** 是 */
    YES("1");

    /** 属性值 */
    private String text;

    UniImportFlg(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
