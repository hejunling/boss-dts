package com.ancun.boss.business.pojo.bizuser;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.validate.annotation.RangeLength;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * 企业用户添加子账号输入类
 * User: zkai
 * Date: 2016/4/25
 * Time: 21:11
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizEntAddSubAccountInput extends BossBasePojo {

    /**
     * 省份编码
     */
    @RangeLength(label="省份编码",max=6,min=1,message=""+ MessageConstant.RPCODE_FORMAT_ERROR)
    private String rpcode;

    /**
     * 套餐编号
     */
    @NotNull(message = "" + MessageConstant.TCCODE_NOTNULL)
    private Long taocanid;

    /**
     * 开通时间
     */
    @NotNull(message=""+MessageConstant.INFO_LENGTH_ILLEGAL)
    private Date opendatetime;


    /**
     * 主叫提示音标记
     */
    private String callervoice;

    /**
     * 被叫提示音标记
     */
    private String calledvoice;

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

    /**
     * 子账号列表
     * @return
     */
    @NotBlank(message=""+MessageConstant.BIZ_ENT_ADD_SUB_ACCOUNTS_IS_NULL)
    private String accounts;

    /**
     * 运营商类型
     */
    @NotBlank(message=""+MessageConstant.TYPE_CODE_NOTNULL)
    private String typecode;

    public String getRpcode() {
        return rpcode;
    }

    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }

    public Long getTaocanid() {
        return taocanid;
    }

    public void setTaocanid(Long taocanid) {
        this.taocanid = taocanid;
    }

    public Date getOpendatetime() {
        return opendatetime;
    }

    public void setOpendatetime(Date opendatetime) {
        this.opendatetime = opendatetime;
    }

    public String getCallervoice() {
        return callervoice;
    }

    public void setCallervoice(String callervoice) {
        this.callervoice = callervoice;
    }

    public String getCalledvoice() {
        return calledvoice;
    }

    public void setCalledvoice(String calledvoice) {
        this.calledvoice = calledvoice;
    }

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

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }
}
