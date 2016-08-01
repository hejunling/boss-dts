package com.ancun.common.persistence.model.sh;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_info")
public class ShUserInfo {
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
     * 开通状态
     */
    @Column(name = "OPENFLAG")
    private String openflag;

    /**
     * 业务类型[预留，现在只有上海电信双向录音]
     */
    @Column(name = "BUSINESSTYPE")
    private String businesstype;

    /**
     * 累计开通月数
     */
    @Column(name = "OPENMONMUM")
    private Long openmonmum;

    /**
     * 主叫开通标记(0:否;1:是)
     */
    @Column(name = "CALLERFLAG_bk")
    private String callerflagBk;

    /**
     * 被叫开通标记(0:否;1:是)
     */
    @Column(name = "CALLEDFLAG_bk")
    private String calledflagBk;

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
     * 用户类别(1:个人;2:企业)
     */
    @Column(name = "USERTYPE")
    private String usertype;

    /**
     * 用户创建时间
     */
    @Column(name = "CREATETIME")
    private Date createtime;

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
     * 部门编号
     */
    @Column(name = "ORG_NO")
    private String orgNo;

    /**
     * 提取码有效期（小时）
     */
    @Column(name = "ACCESSCODE_ACTIVE")
    private Long accesscodeActive;

    /**
     * 第n次开通
     */
    @Column(name = "OPEN_SEQ")
    private Long openSeq;

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
     * 获取累计开通月数
     *
     * @return OPENMONMUM - 累计开通月数
     */
    public Long getOpenmonmum() {
        return openmonmum;
    }

    /**
     * 设置累计开通月数
     *
     * @param openmonmum 累计开通月数
     */
    public void setOpenmonmum(Long openmonmum) {
        this.openmonmum = openmonmum;
    }

    /**
     * 获取主叫开通标记(0:否;1:是)
     *
     * @return CALLERFLAG_bk - 主叫开通标记(0:否;1:是)
     */
    public String getCallerflagBk() {
        return callerflagBk;
    }

    /**
     * 设置主叫开通标记(0:否;1:是)
     *
     * @param callerflagBk 主叫开通标记(0:否;1:是)
     */
    public void setCallerflagBk(String callerflagBk) {
        this.callerflagBk = callerflagBk;
    }

    /**
     * 获取被叫开通标记(0:否;1:是)
     *
     * @return CALLEDFLAG_bk - 被叫开通标记(0:否;1:是)
     */
    public String getCalledflagBk() {
        return calledflagBk;
    }

    /**
     * 设置被叫开通标记(0:否;1:是)
     *
     * @param calledflagBk 被叫开通标记(0:否;1:是)
     */
    public void setCalledflagBk(String calledflagBk) {
        this.calledflagBk = calledflagBk;
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
     * 获取部门编号
     *
     * @return ORG_NO - 部门编号
     */
    public String getOrgNo() {
        return orgNo;
    }

    /**
     * 设置部门编号
     *
     * @param orgNo 部门编号
     */
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
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
}