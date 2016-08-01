package com.ancun.boss.business.pojo.bizuser;

import com.ancun.boss.business.persistence.module.BizEntInfo;
import com.ancun.boss.business.persistence.module.BizPersonInfo;
import com.ancun.boss.business.persistence.module.BizUserInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/22
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizUserDetaiOutput {

    /**
     * 用户信息
     */
    private BizUserInfo bizUserInfo;

    /**
     * 个人用户详情
     */
    private BizPersonInfo bizPersonInfo;

    /**
     * 企业用户详情
     */
    private BizEntInfo bizEntInfo;


    public BizUserInfo getBizUserInfo() {
        return bizUserInfo;
    }

    public void setBizUserInfo(BizUserInfo bizUserInfo) {
        this.bizUserInfo = bizUserInfo;
    }

    public BizPersonInfo getBizPersonInfo() {
        return bizPersonInfo;
    }

    public void setBizPersonInfo(BizPersonInfo bizPersonInfo) {
        this.bizPersonInfo = bizPersonInfo;
    }

    public BizEntInfo getBizEntInfo() {
        return bizEntInfo;
    }

    public void setBizEntInfo(BizEntInfo bizEntInfo) {
        this.bizEntInfo = bizEntInfo;
    }
}
