package com.ancun.common.persistence.model.master;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "BIZ_USER_LIFE_CIRCLE")
public class BizUserLifeCircle implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 业务账号
     */
    @Column(name = "USER_NO")
    private String userNo;

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
     * 账号类型(1:主账号;2:子账号)
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
     * 开通/退订(1:开通,2:退订)
     */
    @Column(name = "OPEN_CANCEL")
    private String openCancel;

    /**
     * 来源
     */
    @Column(name = "SOURCE")
    private String source;

    /**
     * 时间
     */
    @Column(name = "TIMERS")
    private Date timers;

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
     * 更新(操作)时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 号码类别(0:固话;1:手机)
     */
    @Column(name = "PHONETYPE")
    private String phonetype;

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
     * 获取账号类型(1:主账号;2:子账号)
     *
     * @return ACCOUNT_TYPE - 账号类型(1:主账号;2:子账号)
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * 设置账号类型(1:主账号;2:子账号)
     *
     * @param accountType 账号类型(1:主账号;2:子账号)
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
     * 获取开通/退订(1:开通,2:退订)
     *
     * @return OPEN_CANCEL - 开通/退订(1:开通,2:退订)
     */
    public String getOpenCancel() {
        return openCancel;
    }

    /**
     * 设置开通/退订(1:开通,2:退订)
     *
     * @param openCancel 开通/退订(1:开通,2:退订)
     */
    public void setOpenCancel(String openCancel) {
        this.openCancel = openCancel;
    }

    /**
     * 获取来源
     *
     * @return SOURCE - 来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置来源
     *
     * @param source 来源
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取时间
     *
     * @return TIMERS - 时间
     */
    public Date getTimers() {
        return timers;
    }

    /**
     * 设置时间
     *
     * @param timers 时间
     */
    public void setTimers(Date timers) {
        this.timers = timers;
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
     * 获取更新(操作)时间
     *
     * @return UPDATE_TIME - 更新(操作)时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新(操作)时间
     *
     * @param updateTime 更新(操作)时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    @Override
    public String toString() {
        return "BizUserLifeCircle{" +
                "userNo='" + userNo + '\'' +
                ", userType='" + userType + '\'' +
                ", entNo='" + entNo + '\'' +
                ", accountType='" + accountType + '\'' +
                ", bizNo='" + bizNo + '\'' +
                ", tcid=" + tcid +
                ", rpcode='" + rpcode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}