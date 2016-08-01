package com.ancun.common.persistence.model.cp.telecom;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_operatelog")
public class TelUserOperateLog {
    /**
     * 主键
     */
    @Id
    @Column(name = "CPID")
    private Long cpid;

    /**
     * 操作日志编号
     */
    @Column(name = "OPERATELOGNO")
    private String operatelogno;

    /**
     * 登录来源
     */
    @Column(name = "LOGINSOURCE")
    private String loginsource;

    /**
     * 用户类别
     */
    @Column(name = "USERTYPE")
    private String usertype;

    /**
     * 操作用户
     */
    @Column(name = "OPUSER")
    private String opuser;

    /**
     * 日志内容
     */
    @Column(name = "LOGCONTENT")
    private String logcontent;

    /**
     * 操作代码
     */
    @Column(name = "OPCODE")
    private String opcode;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 操作时间
     */
    @Column(name = "OPDATETIME")
    private Date opdatetime;

    /**
     * IP地址
     */
    @Column(name = "IPADDRESS")
    private String ipaddress;

    /**
     * 请求地址
     */
    @Column(name = "REQUESTURL")
    private String requesturl;

    /**
     * 通行证编号
     */
    @Column(name = "ACCESSID")
    private String accessid;

    /**
     * 通行证密钥
     */
    @Column(name = "ACCESSKEY")
    private String accesskey;

    /**
     * 操作记录编号
     */
    @Column(name = "OPRECORDNO")
    private Long oprecordno;

    /**
     * 操作类名称
     */
    @Column(name = "OPCLASSNM")
    private String opclassnm;

    /**
     * 操作方法名称
     */
    @Column(name = "OPMETHODNM")
    private String opmethodnm;

    /**
     * 操作接口名称
     */
    @Column(name = "OPINTERFACENM")
    private String opinterfacenm;

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
     * 获取操作日志编号
     *
     * @return OPERATELOGNO - 操作日志编号
     */
    public String getOperatelogno() {
        return operatelogno;
    }

    /**
     * 设置操作日志编号
     *
     * @param operatelogno 操作日志编号
     */
    public void setOperatelogno(String operatelogno) {
        this.operatelogno = operatelogno;
    }

    /**
     * 获取登录来源
     *
     * @return LOGINSOURCE - 登录来源
     */
    public String getLoginsource() {
        return loginsource;
    }

    /**
     * 设置登录来源
     *
     * @param loginsource 登录来源
     */
    public void setLoginsource(String loginsource) {
        this.loginsource = loginsource;
    }

    /**
     * 获取用户类别
     *
     * @return USERTYPE - 用户类别
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * 设置用户类别
     *
     * @param usertype 用户类别
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    /**
     * 获取操作用户
     *
     * @return OPUSER - 操作用户
     */
    public String getOpuser() {
        return opuser;
    }

    /**
     * 设置操作用户
     *
     * @param opuser 操作用户
     */
    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }

    /**
     * 获取日志内容
     *
     * @return LOGCONTENT - 日志内容
     */
    public String getLogcontent() {
        return logcontent;
    }

    /**
     * 设置日志内容
     *
     * @param logcontent 日志内容
     */
    public void setLogcontent(String logcontent) {
        this.logcontent = logcontent;
    }

    /**
     * 获取操作代码
     *
     * @return OPCODE - 操作代码
     */
    public String getOpcode() {
        return opcode;
    }

    /**
     * 设置操作代码
     *
     * @param opcode 操作代码
     */
    public void setOpcode(String opcode) {
        this.opcode = opcode;
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
     * 获取操作时间
     *
     * @return OPDATETIME - 操作时间
     */
    public Date getOpdatetime() {
        return opdatetime;
    }

    /**
     * 设置操作时间
     *
     * @param opdatetime 操作时间
     */
    public void setOpdatetime(Date opdatetime) {
        this.opdatetime = opdatetime;
    }

    /**
     * 获取IP地址
     *
     * @return IPADDRESS - IP地址
     */
    public String getIpaddress() {
        return ipaddress;
    }

    /**
     * 设置IP地址
     *
     * @param ipaddress IP地址
     */
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    /**
     * 获取请求地址
     *
     * @return REQUESTURL - 请求地址
     */
    public String getRequesturl() {
        return requesturl;
    }

    /**
     * 设置请求地址
     *
     * @param requesturl 请求地址
     */
    public void setRequesturl(String requesturl) {
        this.requesturl = requesturl;
    }

    /**
     * 获取通行证编号
     *
     * @return ACCESSID - 通行证编号
     */
    public String getAccessid() {
        return accessid;
    }

    /**
     * 设置通行证编号
     *
     * @param accessid 通行证编号
     */
    public void setAccessid(String accessid) {
        this.accessid = accessid;
    }

    /**
     * 获取通行证密钥
     *
     * @return ACCESSKEY - 通行证密钥
     */
    public String getAccesskey() {
        return accesskey;
    }

    /**
     * 设置通行证密钥
     *
     * @param accesskey 通行证密钥
     */
    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    /**
     * 获取操作记录编号
     *
     * @return OPRECORDNO - 操作记录编号
     */
    public Long getOprecordno() {
        return oprecordno;
    }

    /**
     * 设置操作记录编号
     *
     * @param oprecordno 操作记录编号
     */
    public void setOprecordno(Long oprecordno) {
        this.oprecordno = oprecordno;
    }

    /**
     * 获取操作类名称
     *
     * @return OPCLASSNM - 操作类名称
     */
    public String getOpclassnm() {
        return opclassnm;
    }

    /**
     * 设置操作类名称
     *
     * @param opclassnm 操作类名称
     */
    public void setOpclassnm(String opclassnm) {
        this.opclassnm = opclassnm;
    }

    /**
     * 获取操作方法名称
     *
     * @return OPMETHODNM - 操作方法名称
     */
    public String getOpmethodnm() {
        return opmethodnm;
    }

    /**
     * 设置操作方法名称
     *
     * @param opmethodnm 操作方法名称
     */
    public void setOpmethodnm(String opmethodnm) {
        this.opmethodnm = opmethodnm;
    }

    /**
     * 获取操作接口名称
     *
     * @return OPINTERFACENM - 操作接口名称
     */
    public String getOpinterfacenm() {
        return opinterfacenm;
    }

    /**
     * 设置操作接口名称
     *
     * @param opinterfacenm 操作接口名称
     */
    public void setOpinterfacenm(String opinterfacenm) {
        this.opinterfacenm = opinterfacenm;
    }
}