package com.ancun.common.persistence.model.sh;

import java.util.Date;
import javax.persistence.*;

@Table(name = "account_info")
public class ShAccountInfo {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private Long id;

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
     * 上次登录来源
     */
    @Column(name = "LOGINSOURCE")
    private String loginsource;

    /**
     * 上次登录IP地址
     */
    @Column(name = "LOGINIP")
    private String loginip;

    /**
     * 上次登录时间
     */
    @Column(name = "LOGINTIME")
    private Date logintime;

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
     * 文件记录编号
     */
    @Column(name = "RECORDNO")
    private String recordno;

    /**
     * 区号
     */
    @Column(name = "AREACODE")
    private String areacode;

    /**
     * 最近注销时间
     */
    @Column(name = "LGOINOUT_TIME")
    private Date lgoinoutTime;

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
     * 登录失败次数
     */
    @Column(name = "LOGIN_FAILED_TIMES")
    private Long loginFailedTimes;

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
     * 第n次开通
     */
    @Column(name = "OPEN_SEQ")
    private Long openSeq;

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

    @Column(name = "LAST_LOGINFAILED_TIME")
    private Date lastLoginfailedTime;

    /**
     * 最新登录失败时间
     */
    @Column(name = "LAST_LOGIN_FAILED_TIME")
    private Date lastLoginFailedTime;

    /**
     * 被叫录音提示标志-->(0:否; 1:是)
     */
    @Column(name = "CALLED_RECTIPFLAG")
    private String calledRectipflag;

    /**
     * 获取ID
     *
     * @return ID - ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户编号
     *
     * @return USER_NO - 用户编号
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * 设置用户编号
     *
     * @param userNo 用户编号
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * 获取电话
     *
     * @return USER_TEL - 电话
     */
    public String getUserTel() {
        return userTel;
    }

    /**
     * 设置电话
     *
     * @param userTel 电话
     */
    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    /**
     * 获取密码(MD5值DES加密)
     *
     * @return PASSWORD - 密码(MD5值DES加密)
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码(MD5值DES加密)
     *
     * @param password 密码(MD5值DES加密)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取帐号类型(1:主帐号;2:子帐号;3:个人帐号)
     *
     * @return ACCOUNTTYPE - 帐号类型(1:主帐号;2:子帐号;3:个人帐号)
     */
    public String getAccounttype() {
        return accounttype;
    }

    /**
     * 设置帐号类型(1:主帐号;2:子帐号;3:个人帐号)
     *
     * @param accounttype 帐号类型(1:主帐号;2:子帐号;3:个人帐号)
     */
    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    /**
     * 获取上次登录来源
     *
     * @return LOGINSOURCE - 上次登录来源
     */
    public String getLoginsource() {
        return loginsource;
    }

    /**
     * 设置上次登录来源
     *
     * @param loginsource 上次登录来源
     */
    public void setLoginsource(String loginsource) {
        this.loginsource = loginsource;
    }

    /**
     * 获取上次登录IP地址
     *
     * @return LOGINIP - 上次登录IP地址
     */
    public String getLoginip() {
        return loginip;
    }

    /**
     * 设置上次登录IP地址
     *
     * @param loginip 上次登录IP地址
     */
    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    /**
     * 获取上次登录时间
     *
     * @return LOGINTIME - 上次登录时间
     */
    public Date getLogintime() {
        return logintime;
    }

    /**
     * 设置上次登录时间
     *
     * @param logintime 上次登录时间
     */
    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    /**
     * 获取号码类别(0:固话;1:手机)
     *
     * @return PHONETYPE - 号码类别(0:固话;1:手机)
     */
    public String getPhonetype() {
        return phonetype;
    }

    /**
     * 设置号码类别(0:固话;1:手机)
     *
     * @param phonetype 号码类别(0:固话;1:手机)
     */
    public void setPhonetype(String phonetype) {
        this.phonetype = phonetype;
    }

    /**
     * 获取省份代码
     *
     * @return RPCODE - 省份代码
     */
    public String getRpcode() {
        return rpcode;
    }

    /**
     * 设置省份代码
     *
     * @param rpcode 省份代码
     */
    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }

    /**
     * 获取城市代码
     *
     * @return CITYCODE - 城市代码
     */
    public String getCitycode() {
        return citycode;
    }

    /**
     * 设置城市代码
     *
     * @param citycode 城市代码
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    /**
     * 获取用户状态(1:正常;2:暂停;3:注销)
     *
     * @return USERSTATUS - 用户状态(1:正常;2:暂停;3:注销)
     */
    public String getUserstatus() {
        return userstatus;
    }

    /**
     * 设置用户状态(1:正常;2:暂停;3:注销)
     *
     * @param userstatus 用户状态(1:正常;2:暂停;3:注销)
     */
    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    /**
     * 获取文件记录编号
     *
     * @return RECORDNO - 文件记录编号
     */
    public String getRecordno() {
        return recordno;
    }

    /**
     * 设置文件记录编号
     *
     * @param recordno 文件记录编号
     */
    public void setRecordno(String recordno) {
        this.recordno = recordno;
    }

    /**
     * 获取区号
     *
     * @return AREACODE - 区号
     */
    public String getAreacode() {
        return areacode;
    }

    /**
     * 设置区号
     *
     * @param areacode 区号
     */
    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    /**
     * 获取最近注销时间
     *
     * @return LGOINOUT_TIME - 最近注销时间
     */
    public Date getLgoinoutTime() {
        return lgoinoutTime;
    }

    /**
     * 设置最近注销时间
     *
     * @param lgoinoutTime 最近注销时间
     */
    public void setLgoinoutTime(Date lgoinoutTime) {
        this.lgoinoutTime = lgoinoutTime;
    }

    /**
     * 获取组织编号
     *
     * @return ORG_NO - 组织编号
     */
    public String getOrgNo() {
        return orgNo;
    }

    /**
     * 设置组织编号
     *
     * @param orgNo 组织编号
     */
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    /**
     * 获取初始密码设置标志-->(0:否; 1:是)
     *
     * @return DEFAULTPWDFLAG - 初始密码设置标志-->(0:否; 1:是)
     */
    public String getDefaultpwdflag() {
        return defaultpwdflag;
    }

    /**
     * 设置初始密码设置标志-->(0:否; 1:是)
     *
     * @param defaultpwdflag 初始密码设置标志-->(0:否; 1:是)
     */
    public void setDefaultpwdflag(String defaultpwdflag) {
        this.defaultpwdflag = defaultpwdflag;
    }

    /**
     * 获取登录失败次数
     *
     * @return LOGIN_FAILED_TIMES - 登录失败次数
     */
    public Long getLoginFailedTimes() {
        return loginFailedTimes;
    }

    /**
     * 设置登录失败次数
     *
     * @param loginFailedTimes 登录失败次数
     */
    public void setLoginFailedTimes(Long loginFailedTimes) {
        this.loginFailedTimes = loginFailedTimes;
    }

    /**
     * 获取最新开通时间
     *
     * @return OPENDATETIME - 最新开通时间
     */
    public Date getOpendatetime() {
        return opendatetime;
    }

    /**
     * 设置最新开通时间
     *
     * @param opendatetime 最新开通时间
     */
    public void setOpendatetime(Date opendatetime) {
        this.opendatetime = opendatetime;
    }

    /**
     * 获取最新退订时间
     *
     * @return CANCELDATETIME - 最新退订时间
     */
    public Date getCanceldatetime() {
        return canceldatetime;
    }

    /**
     * 设置最新退订时间
     *
     * @param canceldatetime 最新退订时间
     */
    public void setCanceldatetime(Date canceldatetime) {
        this.canceldatetime = canceldatetime;
    }

    /**
     * 获取备注
     *
     * @return REMARK - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取是否退费(2:不退费；1：退费)
     *
     * @return ISREFUND - 是否退费(2:不退费；1：退费)
     */
    public String getIsrefund() {
        return isrefund;
    }

    /**
     * 设置是否退费(2:不退费；1：退费)
     *
     * @param isrefund 是否退费(2:不退费；1：退费)
     */
    public void setIsrefund(String isrefund) {
        this.isrefund = isrefund;
    }

    /**
     * 获取退费金额
     *
     * @return REFUNDAMOUNT - 退费金额
     */
    public Double getRefundamount() {
        return refundamount;
    }

    /**
     * 设置退费金额
     *
     * @param refundamount 退费金额
     */
    public void setRefundamount(Double refundamount) {
        this.refundamount = refundamount;
    }

    /**
     * 获取退费说明
     *
     * @return REFUNDREMARK - 退费说明
     */
    public String getRefundremark() {
        return refundremark;
    }

    /**
     * 设置退费说明
     *
     * @param refundremark 退费说明
     */
    public void setRefundremark(String refundremark) {
        this.refundremark = refundremark;
    }

    /**
     * 获取第n次开通
     *
     * @return OPEN_SEQ - 第n次开通
     */
    public Long getOpenSeq() {
        return openSeq;
    }

    /**
     * 设置第n次开通
     *
     * @param openSeq 第n次开通
     */
    public void setOpenSeq(Long openSeq) {
        this.openSeq = openSeq;
    }

    /**
     * 获取是否允许普通登录，1：允许，2：不允许
     *
     * @return SMS_LOGIN - 是否允许普通登录，1：允许，2：不允许
     */
    public String getSmsLogin() {
        return smsLogin;
    }

    /**
     * 设置是否允许普通登录，1：允许，2：不允许
     *
     * @param smsLogin 是否允许普通登录，1：允许，2：不允许
     */
    public void setSmsLogin(String smsLogin) {
        this.smsLogin = smsLogin;
    }

    /**
     * 获取联系电话
     *
     * @return CONTACTPHONE - 联系电话
     */
    public String getContactphone() {
        return contactphone;
    }

    /**
     * 设置联系电话
     *
     * @param contactphone 联系电话
     */
    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    /**
     * 获取套餐ID
     *
     * @return TAOCANID - 套餐ID
     */
    public Long getTaocanid() {
        return taocanid;
    }

    /**
     * 设置套餐ID
     *
     * @param taocanid 套餐ID
     */
    public void setTaocanid(Long taocanid) {
        this.taocanid = taocanid;
    }

    /**
     * 获取常用邮箱
     *
     * @return EMAIL - 常用邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置常用邮箱
     *
     * @param email 常用邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取主叫开通标记(0:否;1:是)
     *
     * @return CALLERFLAG - 主叫开通标记(0:否;1:是)
     */
    public String getCallerflag() {
        return callerflag;
    }

    /**
     * 设置主叫开通标记(0:否;1:是)
     *
     * @param callerflag 主叫开通标记(0:否;1:是)
     */
    public void setCallerflag(String callerflag) {
        this.callerflag = callerflag;
    }

    /**
     * 获取被叫开通标记(0:否;1:是)
     *
     * @return CALLEDFLAG - 被叫开通标记(0:否;1:是)
     */
    public String getCalledflag() {
        return calledflag;
    }

    /**
     * 设置被叫开通标记(0:否;1:是)
     *
     * @param calledflag 被叫开通标记(0:否;1:是)
     */
    public void setCalledflag(String calledflag) {
        this.calledflag = calledflag;
    }

    /**
     * 获取录音提示标志-->(0:否; 1:是)
     *
     * @return RECTIPFLAG - 录音提示标志-->(0:否; 1:是)
     */
    public String getRectipflag() {
        return rectipflag;
    }

    /**
     * 设置录音提示标志-->(0:否; 1:是)
     *
     * @param rectipflag 录音提示标志-->(0:否; 1:是)
     */
    public void setRectipflag(String rectipflag) {
        this.rectipflag = rectipflag;
    }

    /**
     * @return LAST_LOGINFAILED_TIME
     */
    public Date getLastLoginfailedTime() {
        return lastLoginfailedTime;
    }

    /**
     * @param lastLoginfailedTime
     */
    public void setLastLoginfailedTime(Date lastLoginfailedTime) {
        this.lastLoginfailedTime = lastLoginfailedTime;
    }

    /**
     * 获取最新登录失败时间
     *
     * @return LAST_LOGIN_FAILED_TIME - 最新登录失败时间
     */
    public Date getLastLoginFailedTime() {
        return lastLoginFailedTime;
    }

    /**
     * 设置最新登录失败时间
     *
     * @param lastLoginFailedTime 最新登录失败时间
     */
    public void setLastLoginFailedTime(Date lastLoginFailedTime) {
        this.lastLoginFailedTime = lastLoginFailedTime;
    }

    /**
     * 获取被叫录音提示标志-->(0:否; 1:是)
     *
     * @return CALLED_RECTIPFLAG - 被叫录音提示标志-->(0:否; 1:是)
     */
    public String getCalledRectipflag() {
        return calledRectipflag;
    }

    /**
     * 设置被叫录音提示标志-->(0:否; 1:是)
     *
     * @param calledRectipflag 被叫录音提示标志-->(0:否; 1:是)
     */
    public void setCalledRectipflag(String calledRectipflag) {
        this.calledRectipflag = calledRectipflag;
    }
}