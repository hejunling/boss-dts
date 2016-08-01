package com.ancun.boss.business.pojo.bizuser;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.validate.annotation.PhoneNum;
import com.ancun.validate.annotation.RangeLength;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 业务用户通用 请求封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/4/25
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizUserInfoInput extends BossBasePojo {
    /**
     * 用户账号
     */
    @PhoneNum(label = "业务用户编码", message = "" + MessageConstant.BIZUSERNO_FORMAT_ERROR)
    private String userNum;

    /**
     * 业务编码
     */
    @RangeLength(label = "业务编码", min = 1, max = 20, message = "" + MessageConstant.INFO_LENGTH_ILLEGAL)
    private String bizNo;


    /**
     * 企业账号
     */
    private String entNo;

    /**
     * 用户类型
     */
    private String userType;


    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getEntNo() {
        return entNo;
    }

    public void setEntNo(String entNo) {
        this.entNo = entNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
