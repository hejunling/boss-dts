package com.ancun.common.persistence.model.cp.unicom;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_info")
public class UniUserInfo {
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

    @Column(name = "CITYCODE")
    private String citycode;

    /**
     * 区号
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

    @Column(name = "ALLOWMAXSTORE")
    private Double allowmaxstore;

    @Column(name = "USEDINGSTORE")
    private Double usedingstore;

    @Column(name = "RECTIPFLAG")
    private String rectipflag;

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
     * 注册来源（1.app;2.web;3.ivr;5.后台;6.短信;7.彩信）
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

    @Column(name = "TAOCANNAME")
    private String taocanname;

    @Column(name = "OPENFLAG")
    private String openflag;

    @Column(name = "OPENDATETIME")
    private Date opendatetime;

    @Column(name = "CANCELDATETIME")
    private Date canceldatetime;

    @Column(name = "BUSINESSTYPE")
    private String businesstype;

    @Column(name = "OPENMONMUM")
    private Long openmonmum;

    /**
     * 区号
     */
    @Column(name = "AREACODE")
    private String areacode;

    @Column(name = "TCID")
    private Long tcid;

    /**
     * 后台导入标记（0:否;1:是;默认:0否）
     */
    @Column(name = "IMPORTFLAG")
    private String importflag;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 代理商编号
     */
    @Column(name = "AGENTID")
    private String agentid;

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
     * @return CITYCODE
     */
    public String getCitycode() {
        return citycode;
    }

    /**
     * @param citycode
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    /**
     * 获取区号
     *
     * @return PHONE - 区号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置区号
     *
     * @param phone 区号
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
     * @return ALLOWMAXSTORE
     */
    public Double getAllowmaxstore() {
        return allowmaxstore;
    }

    /**
     * @param allowmaxstore
     */
    public void setAllowmaxstore(Double allowmaxstore) {
        this.allowmaxstore = allowmaxstore;
    }

    /**
     * @return USEDINGSTORE
     */
    public Double getUsedingstore() {
        return usedingstore;
    }

    /**
     * @param usedingstore
     */
    public void setUsedingstore(Double usedingstore) {
        this.usedingstore = usedingstore;
    }

    /**
     * @return RECTIPFLAG
     */
    public String getRectipflag() {
        return rectipflag;
    }

    /**
     * @param rectipflag
     */
    public void setRectipflag(String rectipflag) {
        this.rectipflag = rectipflag;
    }

    /**
     * @return DEFAULTPWDFLAG
     */
    public String getDefaultpwdflag() {
        return defaultpwdflag;
    }

    /**
     * @param defaultpwdflag
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
     * 获取注册来源（1.app;2.web;3.ivr;5.后台;6.短信;7.彩信）
     *
     * @return ISIGNUPSOURCE - 注册来源（1.app;2.web;3.ivr;5.后台;6.短信;7.彩信）
     */
    public String getIsignupsource() {
        return isignupsource;
    }

    /**
     * 设置注册来源（1.app;2.web;3.ivr;5.后台;6.短信;7.彩信）
     *
     * @param isignupsource 注册来源（1.app;2.web;3.ivr;5.后台;6.短信;7.彩信）
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
     * @return TAOCANNAME
     */
    public String getTaocanname() {
        return taocanname;
    }

    /**
     * @param taocanname
     */
    public void setTaocanname(String taocanname) {
        this.taocanname = taocanname;
    }

    /**
     * @return OPENFLAG
     */
    public String getOpenflag() {
        return openflag;
    }

    /**
     * @param openflag
     */
    public void setOpenflag(String openflag) {
        this.openflag = openflag;
    }

    /**
     * @return OPENDATETIME
     */
    public Date getOpendatetime() {
        return opendatetime;
    }

    /**
     * @param opendatetime
     */
    public void setOpendatetime(Date opendatetime) {
        this.opendatetime = opendatetime;
    }

    /**
     * @return CANCELDATETIME
     */
    public Date getCanceldatetime() {
        return canceldatetime;
    }

    /**
     * @param canceldatetime
     */
    public void setCanceldatetime(Date canceldatetime) {
        this.canceldatetime = canceldatetime;
    }

    /**
     * @return BUSINESSTYPE
     */
    public String getBusinesstype() {
        return businesstype;
    }

    /**
     * @param businesstype
     */
    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    /**
     * @return OPENMONMUM
     */
    public Long getOpenmonmum() {
        return openmonmum;
    }

    /**
     * @param openmonmum
     */
    public void setOpenmonmum(Long openmonmum) {
        this.openmonmum = openmonmum;
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
     * @return TCID
     */
    public Long getTcid() {
        return tcid;
    }

    /**
     * @param tcid
     */
    public void setTcid(Long tcid) {
        this.tcid = tcid;
    }

    /**
     * 获取后台导入标记（0:否;1:是;默认:0否）
     *
     * @return IMPORTFLAG - 后台导入标记（0:否;1:是;默认:0否）
     */
    public String getImportflag() {
        return importflag;
    }

    /**
     * 设置后台导入标记（0:否;1:是;默认:0否）
     *
     * @param importflag 后台导入标记（0:否;1:是;默认:0否）
     */
    public void setImportflag(String importflag) {
        this.importflag = importflag;
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
     * 获取代理商编号
     *
     * @return AGENTID - 代理商编号
     */
    public String getAgentid() {
        return agentid;
    }

    /**
     * 设置代理商编号
     *
     * @param agentid 代理商编号
     */
    public void setAgentid(String agentid) {
        this.agentid = agentid;
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
        return "UniUserInfo{" +
                "cpid=" + cpid +
                ", userno='" + userno + '\'' +
                ", usertype='" + usertype + '\'' +
                ", phonetype='" + phonetype + '\'' +
                ", rpcode='" + rpcode + '\'' +
                ", citycode='" + citycode + '\'' +
                ", phone='" + phone + '\'' +
                ", taocanname='" + taocanname + '\'' +
                ", tcid=" + tcid +
                ", updateTime=" + updateTime +
                '}';
    }
}