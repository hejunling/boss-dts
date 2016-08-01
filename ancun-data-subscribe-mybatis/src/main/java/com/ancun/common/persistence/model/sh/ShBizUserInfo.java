package com.ancun.common.persistence.model.sh;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/23
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class ShBizUserInfo {

    /**
     * 用户编号
     */
    @Column(name = "USER_NO")
    private String userNo;

    /**
     * 电话
     */
    @Column(name = "USER_TEL")
    private String userTel;

    /**
     * 密码(MD5值DES加密)
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 帐号类型(1:主帐号;2:子帐号;3:个人帐号)
     */
    @Column(name = "ACCOUNTTYPE")
    private String accounttype;

    /**
     * 号码类别(0:固话;1:手机)
     */
    @Column(name = "PHONETYPE")
    private String phonetype;

    /**
     * 省份代码
     */
    @Column(name = "RPCODE")
    private String rpcode;

    /**
     * 城市代码
     */
    @Column(name = "CITYCODE")
    private String citycode;

    /**
     * 用户状态(1:正常;2:暂停;3:注销)
     */
    @Column(name = "USERSTATUS")
    private String userstatus;

    /**
     * 组织编号
     */
    @Column(name = "ORG_NO")
    private String orgNo;

    /**
     * 初始密码设置标志-->(0:否; 1:是)
     */
    @Column(name = "DEFAULTPWDFLAG")
    private String defaultpwdflag;

    /**
     * 最新开通时间
     */
    @Column(name = "OPENDATETIME")
    private Date opendatetime;

    /**
     * 最新退订时间
     */
    @Column(name = "CANCELDATETIME")
    private Date canceldatetime;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 是否退费(2:不退费；1：退费)
     */
    @Column(name = "ISREFUND")
    private String isrefund;

    /**
     * 退费金额
     */
    @Column(name = "REFUNDAMOUNT")
    private Double refundamount;

    /**
     * 退费说明
     */
    @Column(name = "REFUNDREMARK")
    private String refundremark;

    /**
     * 是否允许普通登录，1：允许，2：不允许
     */
    @Column(name = "SMS_LOGIN")
    private String smsLogin;

    /**
     * 联系电话
     */
    @Column(name = "CONTACTPHONE")
    private String contactphone;

    /**
     * 套餐ID
     */
    @Column(name = "TAOCANID")
    private Long taocanid;

    /**
     * 常用邮箱
     */
    @Column(name = "EMAIL")
    private String email;

    /**
     * 主叫开通标记(0:否;1:是)
     */
    @Column(name = "CALLERFLAG")
    private String callerflag;

    /**
     * 被叫开通标记(0:否;1:是)
     */
    @Column(name = "CALLEDFLAG")
    private String calledflag;

    /**
     * 录音提示标志-->(0:否; 1:是)
     */
    @Column(name = "RECTIPFLAG")
    private String rectipflag;

    /**
     * 被叫录音提示标志-->(0:否; 1:是)
     */
    @Column(name = "CALLED_RECTIPFLAG")
    private String calledRectipflag;

    /**
     * 传真
     */
    @Column(name = "FAX")
    private String fax;

    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 业务类型[预留，现在只有上海电信双向录音]
     */
    @Column(name = "BUSINESSTYPE")
    private String businesstype;

    /**
     * 用户类别(1:个人;2:企业)
     */
    @Column(name = "USERTYPE")
    private String usertype;
    /**
     * 注册来源
     */
    @Column(name = "ISIGNUPSOURCE")
    private String isignupsource;

    /**
     * 注册时间
     */
    @Column(name = "SIGNUPTIME")
    private Date signuptime;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getPhonetype() {
        return phonetype;
    }

    public void setPhonetype(String phonetype) {
        this.phonetype = phonetype;
    }

    public String getRpcode() {
        return rpcode;
    }

    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getDefaultpwdflag() {
        return defaultpwdflag;
    }

    public void setDefaultpwdflag(String defaultpwdflag) {
        this.defaultpwdflag = defaultpwdflag;
    }

    public Date getOpendatetime() {
        return opendatetime;
    }

    public void setOpendatetime(Date opendatetime) {
        this.opendatetime = opendatetime;
    }

    public Date getCanceldatetime() {
        return canceldatetime;
    }

    public void setCanceldatetime(Date canceldatetime) {
        this.canceldatetime = canceldatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsrefund() {
        return isrefund;
    }

    public void setIsrefund(String isrefund) {
        this.isrefund = isrefund;
    }

    public Double getRefundamount() {
        return refundamount;
    }

    public void setRefundamount(Double refundamount) {
        this.refundamount = refundamount;
    }

    public String getRefundremark() {
        return refundremark;
    }

    public void setRefundremark(String refundremark) {
        this.refundremark = refundremark;
    }

    public String getSmsLogin() {
        return smsLogin;
    }

    public void setSmsLogin(String smsLogin) {
        this.smsLogin = smsLogin;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public Long getTaocanid() {
        return taocanid;
    }

    public void setTaocanid(Long taocanid) {
        this.taocanid = taocanid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallerflag() {
        return callerflag;
    }

    public void setCallerflag(String callerflag) {
        this.callerflag = callerflag;
    }

    public String getCalledflag() {
        return calledflag;
    }

    public void setCalledflag(String calledflag) {
        this.calledflag = calledflag;
    }

    public String getRectipflag() {
        return rectipflag;
    }

    public void setRectipflag(String rectipflag) {
        this.rectipflag = rectipflag;
    }

    public String getCalledRectipflag() {
        return calledRectipflag;
    }

    public void setCalledRectipflag(String calledRectipflag) {
        this.calledRectipflag = calledRectipflag;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getIsignupsource() {
        return isignupsource;
    }

    public void setIsignupsource(String isignupsource) {
        this.isignupsource = isignupsource;
    }

    public Date getSignuptime() {
        return signuptime;
    }

    public void setSignuptime(Date signuptime) {
        this.signuptime = signuptime;
    }

    @Override
    public String toString() {
        return "ShBizUserInfo{" +
                "userNo='" + userNo + '\'' +
                ", userTel='" + userTel + '\'' +
                ", accounttype='" + accounttype + '\'' +
                ", phonetype='" + phonetype + '\'' +
                ", rpcode='" + rpcode + '\'' +
                ", citycode='" + citycode + '\'' +
                ", taocanid=" + taocanid +
                '}';
    }
}
