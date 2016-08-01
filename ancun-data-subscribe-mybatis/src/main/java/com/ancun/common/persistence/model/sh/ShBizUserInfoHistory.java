package com.ancun.common.persistence.model.sh;

import javax.persistence.Column;
import java.util.Date;

/**
 * 上海音证宝用户历史信息
 * User: zkai
 * Date: 2016/5/30
 * Time: 10:10
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class ShBizUserInfoHistory {
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
     * 区号
     */
    @Column(name = "AREACODE")
    private String areacode;

    /**
     * 组织编号
     */
    @Column(name = "ORG_NO")
    private String orgNo;

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
     * 套餐ID
     */
    @Column(name = "TAOCANID")
    private Long taocanid;


    /**
     * 联系电话
     */
    @Column(name = "CONTACTPHONE")
    private String contactphone;

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
     * 用户类别(1:个人;2:企业)
     */
    @Column(name = "USERTYPE")
    private String usertype;


    /**
     * 注册来源
     */
    @Column(name = "ISIGNUPSOURCE")
    private String isignupsource;


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

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
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

    public Long getTaocanid() {
        return taocanid;
    }

    public void setTaocanid(Long taocanid) {
        this.taocanid = taocanid;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
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

    public Date getOpendatetime() {
        return opendatetime;
    }

    public void setOpendatetime(Date opendatetime) {
        this.opendatetime = opendatetime;
    }

    @Override
    public String toString() {
        return "ShBizUserInfoHistory{" +
                "userNo='" + userNo + '\'' +
                ", userTel='" + userTel + '\'' +
                ", accounttype='" + accounttype + '\'' +
                '}';
    }
}
