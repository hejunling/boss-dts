package com.ancun.boss.business.pojo.bizuser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 企业用户添加子账号输出类
 * User: zkai
 * Date: 2016/4/25
 * Time: 21:11
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizEntAddSubAccountOutPut {


    /**
     * 成功的子账号，以；分隔
     */
    private String successaccounts = null;

    /**
     * 失败的子帐号（失败原因），以；分隔
     */
    private String failaccounts = null;

    public String getSuccessaccounts() {
        return successaccounts;
    }

    public void setSuccessaccounts(String successaccounts) {
        this.successaccounts = successaccounts;
    }

    public String getFailaccounts() {
        return failaccounts;
    }

    public void setFailaccounts(String failaccounts) {
        this.failaccounts = failaccounts;
    }
}
