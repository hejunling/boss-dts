package com.ancun.common.persistence.model.dx;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "USER_INFO")
public class UserInfo {
    /**
     * 主键
     */
    @Id
    @Column(name = "CPID")
    private Long cpid;

    /**
     * 用户编号
     */
    @Column(name = "USERNO")
    private String userno;

    /**
     * 密码(MD5值DES加密)
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 用户类别(1:个人;2:企业)
     */
    @Column(name = "USERTYPE")
    private String usertype;

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
     * 电话号码
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * 用户创建时间
     */
    @Column(name = "CREATETIME")
    private Date createtime;

    /**
     * 用户状态(1:正常;2:暂停;3:注销)
     */
    @Column(name = "USERSTATUS")
    private String userstatus;

    /**
     * 允许最大的存储空间
     */
    @Column(name = "ALLOWMAXSTORE")
    private Double allowmaxstore;

    /**
     * 实际已使用存储空间
     */
    @Column(name = "USEDINGSTORE")
    private Double usedingstore;

    /**
     * 录音提示标志-->(0:否; 1:是)
     */
    @Column(name = "RECTIPFLAG")
    private String rectipflag;

    /**
     * 初始密码设置标志-->(0:否; 1:是)
     */
    @Column(name = "DEFAULTPWDFLAG")
    private String defaultpwdflag;

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
     * 套餐ID
     */
    @Column(name = "TAOCANID")
    private Long taocanid;

    /**
     * 开通状态
     */
    @Column(name = "OPENFLAG")
    private String openflag;

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
     * 业务类型
     */
    @Column(name = "BUSINESSTYPE")
    private String businesstype;

    /**
     * 区域代码
     */
    @Column(name = "AREACODE")
    private String areacode;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

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
     * 最近更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 获取主键
     *
     * @return CPID - 主键
     */
    public Long getCpid() {
        return cpid;
    }

    /**
     * 设置主键
     *
     * @param cpid 主键
     */
    public void setCpid(Long cpid) {
        this.cpid = cpid;
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
     * 获取用户类别(1:个人;2:企业)
     *
     * @return USERTYPE - 用户类别(1:个人;2:企业)
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * 设置用户类别(1:个人;2:企业)
     *
     * @param usertype 用户类别(1:个人;2:企业)
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
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
     * 获取电话号码
     *
     * @return PHONE - 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话号码
     *
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取用户创建时间
     *
     * @return CREATETIME - 用户创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置用户创建时间
     *
     * @param createtime 用户创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
     * 获取允许最大的存储空间
     *
     * @return ALLOWMAXSTORE - 允许最大的存储空间
     */
    public Double getAllowmaxstore() {
        return allowmaxstore;
    }

    /**
     * 设置允许最大的存储空间
     *
     * @param allowmaxstore 允许最大的存储空间
     */
    public void setAllowmaxstore(Double allowmaxstore) {
        this.allowmaxstore = allowmaxstore;
    }

    /**
     * 获取实际已使用存储空间
     *
     * @return USEDINGSTORE - 实际已使用存储空间
     */
    public Double getUsedingstore() {
        return usedingstore;
    }

    /**
     * 设置实际已使用存储空间
     *
     * @param usedingstore 实际已使用存储空间
     */
    public void setUsedingstore(Double usedingstore) {
        this.usedingstore = usedingstore;
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
     * 获取开通状态
     *
     * @return OPENFLAG - 开通状态
     */
    public String getOpenflag() {
        return openflag;
    }

    /**
     * 设置开通状态
     *
     * @param openflag 开通状态
     */
    public void setOpenflag(String openflag) {
        this.openflag = openflag;
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
     * 获取业务类型
     *
     * @return BUSINESSTYPE - 业务类型
     */
    public String getBusinesstype() {
        return businesstype;
    }

    /**
     * 设置业务类型
     *
     * @param businesstype 业务类型
     */
    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    /**
     * 获取区域代码
     *
     * @return AREACODE - 区域代码
     */
    public String getAreacode() {
        return areacode;
    }

    /**
     * 设置区域代码
     *
     * @param areacode 区域代码
     */
    public void setAreacode(String areacode) {
        this.areacode = areacode;
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
        return "UserInfo{" +
                "userno='" + userno + '\'' +
                ", usertype='" + usertype + '\'' +
                ", rpcode='" + rpcode + '\'' +
                ", userstatus='" + userstatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) obj;
            return (userno.equals(userInfo.userno) && usertype.equals(userInfo.usertype));
        }
        return super.equals(obj);
    }

}