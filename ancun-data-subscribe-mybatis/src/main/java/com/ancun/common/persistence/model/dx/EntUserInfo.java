package com.ancun.common.persistence.model.dx;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ENT_USER_INFO")
public class EntUserInfo {
    /**
     * 主键
     */
    @Id
    @Column(name = "UIID")
    private Long uiid;

    /**
     * 用户编号
     */
    @Column(name = "USERNO")
    private String userno;

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
     * 帐号类型(1:主帐号;2:子帐号)
     */
    @Column(name = "ACCOUNTTYPE")
    private String accounttype;

    /**
     * 用户状态(1:正常;2:暂停;3:注销)
     */
    @Column(name = "USERSTATUS")
    private String userstatus;

    /**
     * 用户名称
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * 联系人
     */
    @Column(name = "CONTACTNAME")
    private String contactname;

    /**
     * 联系人号码
     */
    @Column(name = "CONTACTPHONE")
    private String contactphone;

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
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 录音提示标志-->(0:否; 1:是)
     */
    @Column(name = "RECTIPFLAG")
    private String rectipflag;

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
     * 试用开始时间
     */
    @Column(name = "TRIAL_START_TIME")
    private Date trialStartTime;

    /**
     * 试用结束时间
     */
    @Column(name = "TRIAL_END_TIME")
    private Date trialEndTime;

    /**
     * 导入时间
     */
    @Column(name = "IMPORT_TIME")
    private Date importTime;

    /**
     * 注册来源
     */
    @Column(name = "ISIGNUPSOURCE")
    private String isignupsource;

    /**
     * 注册IP地址
     */
    @Column(name = "SIGNUPIP")
    private String signupip;

    /**
     * 注册时间
     */
    @Column(name = "SIGNUPTIME")
    private Date signuptime;

    /**
     * 提取码有效期（小时）
     */
    @Column(name = "ACCESSCODE_ACTIVE")
    private Long accesscodeActive;

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
     * 是否允许通过拨打接入号设置密码，1：允许，2：不允许
     */
    @Column(name = "SERVER_SETPWD")
    private String serverSetpwd;

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
     * 营销工号
     */
    @Column(name = "MARKNO")
    private String markno;

    /**
     * 质检员
     */
    @Column(name = "CHECKER")
    private String checker;

    /**
     * 外呼来源
     */
    @Column(name = "CALLSOURCE")
    private String callsource;

    /**
     * 客户号，领航接口对接用
     */
    @Column(name = "CUST_ID")
    private String custId;

    /**
     * 主叫提示音(0:关闭;1:开启)
     */
    @Column(name = "CALLERVOICE")
    private String callervoice;

    /**
     * 被叫提示音(0:关闭;1:开启)
     */
    @Column(name = "CALLEDVOICE")
    private String calledvoice;

    /**
     * 主叫录音标记(0:否;1:是)
     */
    @Column(name = "CALLERRECORDVOICE")
    private String callerrecordvoice;

    /**
     * 被叫录音标记(0:否;1:是)
     */
    @Column(name = "CALLEDRECORDVOICE")
    private String calledrecordvoice;

    /**
     * 性别(1:男;2:女)
     */
    @Column(name = "SEX")
    private String sex;

    /**
     * 证件号码
     */
    @Column(name = "CERTIFICATENUM")
    private String certificatenum;

    /**
     * 最近更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 获取主键
     *
     * @return UIID - 主键
     */
    public Long getUiid() {
        return uiid;
    }

    /**
     * 设置主键
     *
     * @param uiid 主键
     */
    public void setUiid(Long uiid) {
        this.uiid = uiid;
    }

    /**
     * 获取用户编号
     *
     * @return USERNO - 用户编号
     */
    public String getUserno() {
        return userno;
    }

    /**
     * 设置用户编号
     *
     * @param userno 用户编号
     */
    public void setUserno(String userno) {
        this.userno = userno;
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
     * 获取帐号类型(1:主帐号;2:子帐号)
     *
     * @return ACCOUNTTYPE - 帐号类型(1:主帐号;2:子帐号)
     */
    public String getAccounttype() {
        return accounttype;
    }

    /**
     * 设置帐号类型(1:主帐号;2:子帐号)
     *
     * @param accounttype 帐号类型(1:主帐号;2:子帐号)
     */
    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
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
     * 获取用户名称
     *
     * @return USERNAME - 用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名称
     *
     * @param username 用户名称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取联系人
     *
     * @return CONTACTNAME - 联系人
     */
    public String getContactname() {
        return contactname;
    }

    /**
     * 设置联系人
     *
     * @param contactname 联系人
     */
    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    /**
     * 获取联系人号码
     *
     * @return CONTACTPHONE - 联系人号码
     */
    public String getContactphone() {
        return contactphone;
    }

    /**
     * 设置联系人号码
     *
     * @param contactphone 联系人号码
     */
    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    /**
     * 获取传真
     *
     * @return FAX - 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * 设置传真
     *
     * @param fax 传真
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 获取地址
     *
     * @return ADDRESS - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
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
     * 获取试用开始时间
     *
     * @return TRIAL_START_TIME - 试用开始时间
     */
    public Date getTrialStartTime() {
        return trialStartTime;
    }

    /**
     * 设置试用开始时间
     *
     * @param trialStartTime 试用开始时间
     */
    public void setTrialStartTime(Date trialStartTime) {
        this.trialStartTime = trialStartTime;
    }

    /**
     * 获取试用结束时间
     *
     * @return TRIAL_END_TIME - 试用结束时间
     */
    public Date getTrialEndTime() {
        return trialEndTime;
    }

    /**
     * 设置试用结束时间
     *
     * @param trialEndTime 试用结束时间
     */
    public void setTrialEndTime(Date trialEndTime) {
        this.trialEndTime = trialEndTime;
    }

    /**
     * 获取导入时间
     *
     * @return IMPORT_TIME - 导入时间
     */
    public Date getImportTime() {
        return importTime;
    }

    /**
     * 设置导入时间
     *
     * @param importTime 导入时间
     */
    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    /**
     * 获取注册来源
     *
     * @return ISIGNUPSOURCE - 注册来源
     */
    public String getIsignupsource() {
        return isignupsource;
    }

    /**
     * 设置注册来源
     *
     * @param isignupsource 注册来源
     */
    public void setIsignupsource(String isignupsource) {
        this.isignupsource = isignupsource;
    }

    /**
     * 获取注册IP地址
     *
     * @return SIGNUPIP - 注册IP地址
     */
    public String getSignupip() {
        return signupip;
    }

    /**
     * 设置注册IP地址
     *
     * @param signupip 注册IP地址
     */
    public void setSignupip(String signupip) {
        this.signupip = signupip;
    }

    /**
     * 获取注册时间
     *
     * @return SIGNUPTIME - 注册时间
     */
    public Date getSignuptime() {
        return signuptime;
    }

    /**
     * 设置注册时间
     *
     * @param signuptime 注册时间
     */
    public void setSignuptime(Date signuptime) {
        this.signuptime = signuptime;
    }

    /**
     * 获取提取码有效期（小时）
     *
     * @return ACCESSCODE_ACTIVE - 提取码有效期（小时）
     */
    public Long getAccesscodeActive() {
        return accesscodeActive;
    }

    /**
     * 设置提取码有效期（小时）
     *
     * @param accesscodeActive 提取码有效期（小时）
     */
    public void setAccesscodeActive(Long accesscodeActive) {
        this.accesscodeActive = accesscodeActive;
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
     * 获取是否允许通过拨打接入号设置密码，1：允许，2：不允许
     *
     * @return SERVER_SETPWD - 是否允许通过拨打接入号设置密码，1：允许，2：不允许
     */
    public String getServerSetpwd() {
        return serverSetpwd;
    }

    /**
     * 设置是否允许通过拨打接入号设置密码，1：允许，2：不允许
     *
     * @param serverSetpwd 是否允许通过拨打接入号设置密码，1：允许，2：不允许
     */
    public void setServerSetpwd(String serverSetpwd) {
        this.serverSetpwd = serverSetpwd;
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
     * 获取营销工号
     *
     * @return MARKNO - 营销工号
     */
    public String getMarkno() {
        return markno;
    }

    /**
     * 设置营销工号
     *
     * @param markno 营销工号
     */
    public void setMarkno(String markno) {
        this.markno = markno;
    }

    /**
     * 获取质检员
     *
     * @return CHECKER - 质检员
     */
    public String getChecker() {
        return checker;
    }

    /**
     * 设置质检员
     *
     * @param checker 质检员
     */
    public void setChecker(String checker) {
        this.checker = checker;
    }

    /**
     * 获取外呼来源
     *
     * @return CALLSOURCE - 外呼来源
     */
    public String getCallsource() {
        return callsource;
    }

    /**
     * 设置外呼来源
     *
     * @param callsource 外呼来源
     */
    public void setCallsource(String callsource) {
        this.callsource = callsource;
    }

    /**
     * 获取客户号，领航接口对接用
     *
     * @return CUST_ID - 客户号，领航接口对接用
     */
    public String getCustId() {
        return custId;
    }

    /**
     * 设置客户号，领航接口对接用
     *
     * @param custId 客户号，领航接口对接用
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     * 获取主叫提示音(0:关闭;1:开启)
     *
     * @return CALLERVOICE - 主叫提示音(0:关闭;1:开启)
     */
    public String getCallervoice() {
        return callervoice;
    }

    /**
     * 设置主叫提示音(0:关闭;1:开启)
     *
     * @param callervoice 主叫提示音(0:关闭;1:开启)
     */
    public void setCallervoice(String callervoice) {
        this.callervoice = callervoice;
    }

    /**
     * 获取被叫提示音(0:关闭;1:开启)
     *
     * @return CALLEDVOICE - 被叫提示音(0:关闭;1:开启)
     */
    public String getCalledvoice() {
        return calledvoice;
    }

    /**
     * 设置被叫提示音(0:关闭;1:开启)
     *
     * @param calledvoice 被叫提示音(0:关闭;1:开启)
     */
    public void setCalledvoice(String calledvoice) {
        this.calledvoice = calledvoice;
    }

    /**
     * 获取主叫录音标记(0:否;1:是)
     *
     * @return CALLERRECORDVOICE - 主叫录音标记(0:否;1:是)
     */
    public String getCallerrecordvoice() {
        return callerrecordvoice;
    }

    /**
     * 设置主叫录音标记(0:否;1:是)
     *
     * @param callerrecordvoice 主叫录音标记(0:否;1:是)
     */
    public void setCallerrecordvoice(String callerrecordvoice) {
        this.callerrecordvoice = callerrecordvoice;
    }

    /**
     * 获取被叫录音标记(0:否;1:是)
     *
     * @return CALLEDRECORDVOICE - 被叫录音标记(0:否;1:是)
     */
    public String getCalledrecordvoice() {
        return calledrecordvoice;
    }

    /**
     * 设置被叫录音标记(0:否;1:是)
     *
     * @param calledrecordvoice 被叫录音标记(0:否;1:是)
     */
    public void setCalledrecordvoice(String calledrecordvoice) {
        this.calledrecordvoice = calledrecordvoice;
    }

    /**
     * 获取性别(1:男;2:女)
     *
     * @return SEX - 性别(1:男;2:女)
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别(1:男;2:女)
     *
     * @param sex 性别(1:男;2:女)
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取证件号码
     *
     * @return CERTIFICATENUM - 证件号码
     */
    public String getCertificatenum() {
        return certificatenum;
    }

    /**
     * 设置证件号码
     *
     * @param certificatenum 证件号码
     */
    public void setCertificatenum(String certificatenum) {
        this.certificatenum = certificatenum;
    }

    /**
     * 获取最近更新时间
     *
     * @return UPDATE_TIME - 最近更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置最近更新时间
     *
     * @param updateTime 最近更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "EntUserInfo{" +
                "uiid=" + uiid +
                ", userno='" + userno + '\'' +
                ", userTel='" + userTel + '\'' +
                ", accounttype='" + accounttype + '\'' +
                ", username='" + username + '\'' +
                ", rpcode='" + rpcode + '\'' +
                ", citycode='" + citycode + '\'' +
                ", taocanid=" + taocanid +
                ", updateTime=" + updateTime +
                '}';
    }
}