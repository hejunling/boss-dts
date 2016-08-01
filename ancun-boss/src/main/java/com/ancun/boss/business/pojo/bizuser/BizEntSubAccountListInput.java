package com.ancun.boss.business.pojo.bizuser;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossPagePojo;
import org.hibernate.validator.constraints.NotBlank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 企业用户添加子账号输入类
 * User: zkai
 * Date: 2016/4/25
 * Time: 21:11
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizEntSubAccountListInput extends BossPagePojo {

    /**
     * 业务编号
     */
    @NotBlank(message=""+MessageConstant.BIZNO_NOTNULL)
    private String bizno;

    /**
     * 企业用户编号
     */
    @NotBlank(message=""+MessageConstant.USER_ENT_NO_IS_NULL)
    private String entno;


    public String getBizno() {
        return bizno;
    }

    public void setBizno(String bizno) {
        this.bizno = bizno;
    }

    public String getEntno() {
        return entno;
    }

    public void setEntno(String entno) {
        this.entno = entno;
    }


}
