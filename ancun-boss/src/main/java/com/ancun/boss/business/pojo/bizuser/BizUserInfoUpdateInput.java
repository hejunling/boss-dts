package com.ancun.boss.business.pojo.bizuser;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.persistence.module.BizEntInfo;
import com.ancun.boss.business.persistence.module.BizPersonInfo;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.validate.annotation.In;
import com.ancun.validate.annotation.PhoneNum;
import com.ancun.validate.annotation.RangeLength;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 业务用户修改请求封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/4/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizUserInfoUpdateInput extends BossBasePojo {

    /**
     * 业务账号
     */
    @PhoneNum(label = "业务用户编码", message = "" + MessageConstant.BIZUSERNO_FORMAT_ERROR)
    private String userNum;

    /**
     * 套餐id
     */
    @NotNull(message = "" + MessageConstant.TCCODE_NOTNULL)
    private Long tcId;


    /**
     * 联系电话
     */
    @PhoneNum(label = "电话号码格式", message = "" + MessageConstant.CONTACT_PHONE_FORMAT_ERROR)
    private String phone;


    /**
     * 用户类型(1:个人;2:企业)
     */
    @NotBlank(message = "" + MessageConstant.BIZ_USER_TYPE_IS_NULL)
    @In(value = Constant.USER_TYPE_PER_ENT)
    private String userType;

    /**
     * 注册来源
     */
    @NotBlank(message = "" + MessageConstant.SIGNUPSOURCE_MUST_BE_NOT_EMPTY)
    private String insource;

    /**
     * 备注
     */
    @RangeLength(label = "备注", max = 200, required = false, message = "" + MessageConstant.REMARK_FORMAT_ERROR)
    private String remark;

    /**
     * 是否允许短信登录
     */
    @NotBlank(message = "" + MessageConstant.SMS_LOGIN_IS_NULL)
    @In(value = Constant.MARK1OR2)
    private String smsLogin;


    /**
     * 邮箱
     */
    @Email(message = MessageConstant.EMAIL_IS_WRONG + "")
    private String email;

    /**
     * 传真
     */
    private String fax;

    /**
     * 企业编号
     */
    private String entNo;

    /**
     * 业务编号
     */
    @NotBlank(message = "" + MessageConstant.BIZNO_NOTNULL)
    private String bizNo;

    /**
     * 主叫提示音标记
     */
    private String callerVoice;

    /**
     * 被叫提示音标记
     */
    private String calledVoice;

    /**
     * 录音提示
     */
    private String rectip;

    /**
     * 套餐类型
     */
    @NotBlank(message = "" + MessageConstant.BIZ_USER_TAOCAN_NOT_FOUND)
    private String tcType;

    /**
     * 用户状态
     */
    @NotBlank(message = "" + MessageConstant.BIZ_USER_STATE_ERROR)
    private String status;

    /**
     * 个人用户
     */
    private BizPersonInfo bizPersonInfo;

    /**
     * 企业用户
     */
    private BizEntInfo bizEntInfo;


    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public Long getTcId() {
        return tcId;
    }

    public void setTcId(Long tcId) {
        this.tcId = tcId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getInsource() {
        return insource;
    }

    public void setInsource(String insource) {
        this.insource = insource;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSmsLogin() {
        return smsLogin;
    }

    public void setSmsLogin(String smsLogin) {
        this.smsLogin = smsLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEntNo() {
        return entNo;
    }

    public void setEntNo(String entNo) {
        this.entNo = entNo;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getCallerVoice() {
        return callerVoice;
    }

    public void setCallerVoice(String callerVoice) {
        this.callerVoice = callerVoice;
    }

    public String getCalledVoice() {
        return calledVoice;
    }

    public void setCalledVoice(String calledVoice) {
        this.calledVoice = calledVoice;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRectip() {
        return rectip;
    }

    public void setRectip(String rectip) {
        this.rectip = rectip;
    }

    public String getTcType() {
        return tcType;
    }

    public void setTcType(String tcType) {
        this.tcType = tcType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
