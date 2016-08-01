package com.ancun.common.persistence.model.master;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "BIZ_USER_INFO")
public class BizUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 性别(1:男;2:女)  来自BIZ_PERSON_INFO
     */
    private String sex;

    /**
     * 身份证号 来自BIZ_PERSON_INFO
     */
    private String identify;

    /**
     * 工商注册号  来自BIZ_ENT_INFO
     */
    private String regNo;

    /**
     * 企业名称  来自BIZ_ENT_INFO
     */
    private String name;

    /**
     * 企业地址  来自BIZ_ENT_INFO
     */
    private String address;

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 业务账号
     */
    @Column(name = "USER_NO")
    private String userNo;

    /**
     * 密码
     */
    @Column(name = "PASSWD")
    private String passwd;

    /**
     * 用户类型(1:个人;2:企业)
     */
    @Column(name = "USER_TYPE")
    private String userType;

    /**
     * 企业编号
     */
    @Column(name = "ENT_NO")
    private String entNo;

    /**
     * 账号类型(1:主账号;2:子账号;3:个人账号)
     */
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    /**
     * 业务编号
     */
    @Column(name = "BIZ_NO")
    private String bizNo;

    /**
     * 套餐ID
     */
    @Column(name = "TCID")
    private Long tcid;

    /**
     * 组织编号
     */
    @Column(name = "ORG_NO")
    private String orgNo;

    /**
     * (1:正常;2:暂停;3:注销)
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 省份代码
     */
    @Column(name = "RPCODE")
    private String rpcode;

    /**
     * 城市代码
     */
    @Column(name = "CITY_CODE")
    private String cityCode;

    /**
     * 电话号码
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * IVR录音提示标志(0:否; 1:是)
     */
    @Column(name = "RECTIP")
    private String rectip;

    /**
     * 创建时间
     */
    @Column(name = "INTIME")
    private Date intime;

    /**
     * 注册来源(1:个人后台开通,2:IVR,3:ANDROID,4:IOS,5:地推,6:短信,7:彩信,8:企业后台开通)
     */
    @Column(name = "INSOURCE")
    private String insource;

    /**
     * 退订来源
     */
    @Column(name = "OFFSOURCE")
    private String offsource;

    /**
     * 退订时间
     */
    @Column(name = "OFFTIME")
    private Date offtime;

    /**
     * 主叫提示音(0:关闭;1:开启)
     */
    @Column(name = "CALLER_VOICE")
    private String callerVoice;

    /**
     * 被叫提示音(0:关闭;1:开启)
     */
    @Column(name = "CALLED_VOICE")
    private String calledVoice;

    /**
     * 主叫录音标记(0:否;1:是)
     */
    @Column(name = "CALLER_RECORD")
    private String callerRecord;

    /**
     * 被叫录音标记(0:否;1:是)
     */
    @Column(name = "CALLED_RECORD")
    private String calledRecord;

    /**
     * 号码类别(0:固话;1:手机)
     */
    @Column(name = "PHONETYPE")
    private String phonetype;

    /**
     * 初始密码设置标志(0:否; 1:是)
     */
    @Column(name = "DEFAULTPWDFLAG")
    private String defaultpwdflag;

    /**
     * 是否退费(1:退费;2:不退费)
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
     * 是否允许短信登录(1:允许;2:不允许)
     */
    @Column(name = "SMS_LOGIN")
    private String smsLogin;

    /**
     * 常用邮箱
     */
    @Column(name = "EMAIL")
    private String email;

    /**
     * 业务类型[预留，现在只有上海电信双向录音]
     */
    @Column(name = "BUSINESSTYPE")
    private String businesstype;

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
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 传真
     */
    @Column(name = "FAX")
    private String fax;

    /**
     * 是否全包（TRUE/FALSE）
     */
    @Column(name = "FULLPACKAGE")
    private String fullpackage;

    /**
     * UUID唯一标识（JAVA程序生成）
     */
    @Column(name = "UNIQUENO")
    private String uniqueno;

    /**
     * 操作（是否个人转企业。0：否；1：是）
     */
    @Column(name = "OPERATION")
    private String operation;

    /**
     * 最近更新时间
     */
    @Column(name = "modifyTimes")
    private Date modifytimes;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取业务账号
     *
     * @return USER_NO - 业务账号
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * 设置业务账号
     *
     * @param userNo 业务账号
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * 获取密码
     *
     * @return PASSWD - 密码
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * 设置密码
     *
     * @param passwd 密码
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * 获取用户类型(1:个人;2:企业)
     *
     * @return USER_TYPE - 用户类型(1:个人;2:企业)
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 设置用户类型(1:个人;2:企业)
     *
     * @param userType 用户类型(1:个人;2:企业)
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 获取企业编号
     *
     * @return ENT_NO - 企业编号
     */
    public String getEntNo() {
        return entNo;
    }

    /**
     * 设置企业编号
     *
     * @param entNo 企业编号
     */
    public void setEntNo(String entNo) {
        this.entNo = entNo;
    }

    /**
     * 获取账号类型(1:主账号;2:子账号;3:个人账号)
     *
     * @return ACCOUNT_TYPE - 账号类型(1:主账号;2:子账号;3:个人账号)
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * 设置账号类型(1:主账号;2:子账号;3:个人账号)
     *
     * @param accountType 账号类型(1:主账号;2:子账号;3:个人账号)
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * 获取业务编号
     *
     * @return BIZ_NO - 业务编号
     */
    public String getBizNo() {
        return bizNo;
    }

    /**
     * 设置业务编号
     *
     * @param bizNo 业务编号
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    /**
     * 获取套餐ID
     *
     * @return TCID - 套餐ID
     */
    public Long getTcid() {
        return tcid;
    }

    /**
     * 设置套餐ID
     *
     * @param tcid 套餐ID
     */
    public void setTcid(Long tcid) {
        this.tcid = tcid;
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
     * 获取(1:正常;2:暂停;3:注销)
     *
     * @return STATUS - (1:正常;2:暂停;3:注销)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置(1:正常;2:暂停;3:注销)
     *
     * @param status (1:正常;2:暂停;3:注销)
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return CITY_CODE - 城市代码
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置城市代码
     *
     * @param cityCode 城市代码
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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
     * 获取IVR录音提示标志(0:否; 1:是)
     *
     * @return RECTIP - IVR录音提示标志(0:否; 1:是)
     */
    public String getRectip() {
        return rectip;
    }

    /**
     * 设置IVR录音提示标志(0:否; 1:是)
     *
     * @param rectip IVR录音提示标志(0:否; 1:是)
     */
    public void setRectip(String rectip) {
        this.rectip = rectip;
    }

    /**
     * 获取创建时间
     *
     * @return INTIME - 创建时间
     */
    public Date getIntime() {
        return intime;
    }

    /**
     * 设置创建时间
     *
     * @param intime 创建时间
     */
    public void setIntime(Date intime) {
        this.intime = intime;
    }

    /**
     * 获取注册来源(1:个人后台开通,2:IVR,3:ANDROID,4:IOS,5:地推,6:短信,7:彩信,8:企业后台开通)
     *
     * @return INSOURCE - 注册来源(1:个人后台开通,2:IVR,3:ANDROID,4:IOS,5:地推,6:短信,7:彩信,8:企业后台开通)
     */
    public String getInsource() {
        return insource;
    }

    /**
     * 设置注册来源(1:个人后台开通,2:IVR,3:ANDROID,4:IOS,5:地推,6:短信,7:彩信,8:企业后台开通)
     *
     * @param insource 注册来源(1:个人后台开通,2:IVR,3:ANDROID,4:IOS,5:地推,6:短信,7:彩信,8:企业后台开通)
     */
    public void setInsource(String insource) {
        this.insource = insource;
    }

    /**
     * 获取退订来源
     *
     * @return OFFSOURCE - 退订来源
     */
    public String getOffsource() {
        return offsource;
    }

    /**
     * 设置退订来源
     *
     * @param offsource 退订来源
     */
    public void setOffsource(String offsource) {
        this.offsource = offsource;
    }

    /**
     * 获取退订时间
     *
     * @return OFFTIME - 退订时间
     */
    public Date getOfftime() {
        return offtime;
    }

    /**
     * 设置退订时间
     *
     * @param offtime 退订时间
     */
    public void setOfftime(Date offtime) {
        this.offtime = offtime;
    }

    /**
     * 获取主叫提示音(0:关闭;1:开启)
     *
     * @return CALLER_VOICE - 主叫提示音(0:关闭;1:开启)
     */
    public String getCallerVoice() {
        return callerVoice;
    }

    /**
     * 设置主叫提示音(0:关闭;1:开启)
     *
     * @param callerVoice 主叫提示音(0:关闭;1:开启)
     */
    public void setCallerVoice(String callerVoice) {
        this.callerVoice = callerVoice;
    }

    /**
     * 获取被叫提示音(0:关闭;1:开启)
     *
     * @return CALLED_VOICE - 被叫提示音(0:关闭;1:开启)
     */
    public String getCalledVoice() {
        return calledVoice;
    }

    /**
     * 设置被叫提示音(0:关闭;1:开启)
     *
     * @param calledVoice 被叫提示音(0:关闭;1:开启)
     */
    public void setCalledVoice(String calledVoice) {
        this.calledVoice = calledVoice;
    }

    /**
     * 获取主叫录音标记(0:否;1:是)
     *
     * @return CALLER_RECORD - 主叫录音标记(0:否;1:是)
     */
    public String getCallerRecord() {
        return callerRecord;
    }

    /**
     * 设置主叫录音标记(0:否;1:是)
     *
     * @param callerRecord 主叫录音标记(0:否;1:是)
     */
    public void setCallerRecord(String callerRecord) {
        this.callerRecord = callerRecord;
    }

    /**
     * 获取被叫录音标记(0:否;1:是)
     *
     * @return CALLED_RECORD - 被叫录音标记(0:否;1:是)
     */
    public String getCalledRecord() {
        return calledRecord;
    }

    /**
     * 设置被叫录音标记(0:否;1:是)
     *
     * @param calledRecord 被叫录音标记(0:否;1:是)
     */
    public void setCalledRecord(String calledRecord) {
        this.calledRecord = calledRecord;
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
     * 获取初始密码设置标志(0:否; 1:是)
     *
     * @return DEFAULTPWDFLAG - 初始密码设置标志(0:否; 1:是)
     */
    public String getDefaultpwdflag() {
        return defaultpwdflag;
    }

    /**
     * 设置初始密码设置标志(0:否; 1:是)
     *
     * @param defaultpwdflag 初始密码设置标志(0:否; 1:是)
     */
    public void setDefaultpwdflag(String defaultpwdflag) {
        this.defaultpwdflag = defaultpwdflag;
    }

    /**
     * 获取是否退费(1:退费;2:不退费)
     *
     * @return ISREFUND - 是否退费(1:退费;2:不退费)
     */
    public String getIsrefund() {
        return isrefund;
    }

    /**
     * 设置是否退费(1:退费;2:不退费)
     *
     * @param isrefund 是否退费(1:退费;2:不退费)
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
     * 获取是否允许短信登录(1:允许;2:不允许)
     *
     * @return SMS_LOGIN - 是否允许短信登录(1:允许;2:不允许)
     */
    public String getSmsLogin() {
        return smsLogin;
    }

    /**
     * 设置是否允许短信登录(1:允许;2:不允许)
     *
     * @param smsLogin 是否允许短信登录(1:允许;2:不允许)
     */
    public void setSmsLogin(String smsLogin) {
        this.smsLogin = smsLogin;
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
     * 获取业务类型[预留，现在只有上海电信双向录音]
     *
     * @return BUSINESSTYPE - 业务类型[预留，现在只有上海电信双向录音]
     */
    public String getBusinesstype() {
        return businesstype;
    }

    /**
     * 设置业务类型[预留，现在只有上海电信双向录音]
     *
     * @param businesstype 业务类型[预留，现在只有上海电信双向录音]
     */
    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
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
     * 获取是否全包（TRUE/FALSE）
     *
     * @return FULLPACKAGE - 是否全包（TRUE/FALSE）
     */
    public String getFullpackage() {
        return fullpackage;
    }

    /**
     * 设置是否全包（TRUE/FALSE）
     *
     * @param fullpackage 是否全包（TRUE/FALSE）
     */
    public void setFullpackage(String fullpackage) {
        this.fullpackage = fullpackage;
    }

    /**
     * 获取UUID唯一标识（JAVA程序生成）
     *
     * @return UNIQUENO - UUID唯一标识（JAVA程序生成）
     */
    public String getUniqueno() {
        return uniqueno;
    }

    /**
     * 设置UUID唯一标识（JAVA程序生成）
     *
     * @param uniqueno UUID唯一标识（JAVA程序生成）
     */
    public void setUniqueno(String uniqueno) {
        this.uniqueno = uniqueno;
    }

    /**
     * 获取操作（是否个人转企业。0：否；1：是）
     *
     * @return OPERATION - 操作（是否个人转企业。0：否；1：是）
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置操作（是否个人转企业。0：否；1：是）
     *
     * @param operation 操作（是否个人转企业。0：否；1：是）
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 获取最近更新时间
     *
     * @return modifyTimes - 最近更新时间
     */
    public Date getModifytimes() {
        return modifytimes;
    }

    /**
     * 设置最近更新时间
     *
     * @param modifytimes 最近更新时间
     */
    public void setModifytimes(Date modifytimes) {
        this.modifytimes = modifytimes;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "BizUserInfo{" +
                "id=" + id +
                ", userNo='" + userNo + '\'' +
                ", userType='" + userType + ":用户类型(1:个人;2:企业)" + '\'' +
                ", entNo='" + entNo + '\'' +
                ", accountType='" + accountType + ":账号类型(1:主账号;2:子账号)" + '\'' +
                ", bizNo='" + bizNo + '\'' +
                ", tcid=" + tcid +
                ", status='" + status + '\'' +
                ", rpcode='" + rpcode + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BizUserInfo that = (BizUserInfo) o;

        if (userNo != null ? !userNo.equals(that.userNo) : that.userNo != null) return false;
        if (passwd != null ? !passwd.equals(that.passwd) : that.passwd != null) return false;
        if (userType != null ? !userType.equals(that.userType) : that.userType != null) return false;
        if (entNo != null ? !entNo.equals(that.entNo) : that.entNo != null) return false;
        if (accountType != null ? !accountType.equals(that.accountType) : that.accountType != null) return false;
        if (bizNo != null ? !bizNo.equals(that.bizNo) : that.bizNo != null) return false;
        if (tcid != null ? !tcid.equals(that.tcid) : that.tcid != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (rpcode != null ? !rpcode.equals(that.rpcode) : that.rpcode != null) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (rectip != null ? !rectip.equals(that.rectip) : that.rectip != null) return false;
        if (offtime != null ? !offtime.equals(that.offtime) : that.offtime != null) return false;
        if (callerVoice != null ? !callerVoice.equals(that.callerVoice) : that.callerVoice != null) return false;
        if (calledVoice != null ? !calledVoice.equals(that.calledVoice) : that.calledVoice != null) return false;
        if (callerRecord != null ? !callerRecord.equals(that.callerRecord) : that.callerRecord != null) return false;
        if (calledRecord != null ? !calledRecord.equals(that.calledRecord) : that.calledRecord != null) return false;
        if (phonetype != null ? !phonetype.equals(that.phonetype) : that.phonetype != null) return false;
        if (defaultpwdflag != null ? !defaultpwdflag.equals(that.defaultpwdflag) : that.defaultpwdflag != null)
            return false;
        if (isrefund != null ? !isrefund.equals(that.isrefund) : that.isrefund != null) return false;
        if (refundamount != null ? !refundamount.equals(that.refundamount) : that.refundamount != null) return false;
        if (refundremark != null ? !refundremark.equals(that.refundremark) : that.refundremark != null) return false;
        if (smsLogin != null ? !smsLogin.equals(that.smsLogin) : that.smsLogin != null) return false;
        if (callerflag != null ? !callerflag.equals(that.callerflag) : that.callerflag != null) return false;
        if (calledflag != null ? !calledflag.equals(that.calledflag) : that.calledflag != null) return false;
        return remark != null ? remark.equals(that.remark) : that.remark == null;

    }

    @Override
    public int hashCode() {
        int result = userNo != null ? userNo.hashCode() : 0;
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (entNo != null ? entNo.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (bizNo != null ? bizNo.hashCode() : 0);
        result = 31 * result + (tcid != null ? tcid.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (rpcode != null ? rpcode.hashCode() : 0);
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (rectip != null ? rectip.hashCode() : 0);
        result = 31 * result + (offtime != null ? offtime.hashCode() : 0);
        result = 31 * result + (callerVoice != null ? callerVoice.hashCode() : 0);
        result = 31 * result + (calledVoice != null ? calledVoice.hashCode() : 0);
        result = 31 * result + (callerRecord != null ? callerRecord.hashCode() : 0);
        result = 31 * result + (calledRecord != null ? calledRecord.hashCode() : 0);
        result = 31 * result + (phonetype != null ? phonetype.hashCode() : 0);
        result = 31 * result + (defaultpwdflag != null ? defaultpwdflag.hashCode() : 0);
        result = 31 * result + (isrefund != null ? isrefund.hashCode() : 0);
        result = 31 * result + (refundamount != null ? refundamount.hashCode() : 0);
        result = 31 * result + (refundremark != null ? refundremark.hashCode() : 0);
        result = 31 * result + (smsLogin != null ? smsLogin.hashCode() : 0);
        result = 31 * result + (callerflag != null ? callerflag.hashCode() : 0);
        result = 31 * result + (calledflag != null ? calledflag.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}