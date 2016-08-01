package com.ancun.boss.business.pojo.bizuser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * 用户列表查询输出类
 * User: zkai
 * Date: 2016/4/7
 * Time: 16:44
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizUserInfoOutput  {

    /**
     * 唯一主键
     */
    private String id;

    /**
     * 电话号码
     */

    private String usernum;

    /**
     * 企业编号
     */
    private String entno;

    /**
     * 账户类型
     */
    private String usertype ;

    /**
     * 号码状态 1.开通 ;2.暂停;s 3.退订
     */
    private String userstatus;

    /**
     * 开通类型
     */
    private String isignupsource;

    /**
     * 开通时间
     */
    private Date opentime;

    /**
     * 退订时间
     */
    private Date canceltime;


    /**
     * 所属省份名称
     */
    private String rpname;

    /**
     * 省份编号
     */
    private String rpcode;

    /**
     * 套餐类型
     */
    private String taocantype;
    /**
     * 资费套餐名称
     */
    private String taocanname;


    /**
     * 联系电话
     */
    private String phone;

    /**
     * 业务编号
     */
    private String bizno;

    /**
     * 账号类型(1:主账号;2:子账号;3:个人账号)
     */
    private String accounttype;


    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum;
    }

    public String getEntno() {
        return entno;
    }

    public void setEntno(String entno) {
        this.entno = entno;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getIsignupsource() {
        return isignupsource;
    }

    public void setIsignupsource(String isignupsource) {
        this.isignupsource = isignupsource;
    }

    public Date getOpentime() {
        return opentime;
    }

    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    public Date getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(Date canceltime) {
        this.canceltime = canceltime;
    }

    public String getRpname() {
        return rpname;
    }

    public void setRpname(String rpname) {
        this.rpname = rpname;
    }

    public String getTaocanname() {
        return taocanname;
    }

    public void setTaocanname(String taocanname) {
        this.taocanname = taocanname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRpcode() {
        return rpcode;
    }

    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }

    public String getTaocantype() {
        return taocantype;
    }

    public void setTaocantype(String taocantype) {
        this.taocantype = taocantype;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBizno() {
        return bizno;
    }

    public void setBizno(String bizno) {
        this.bizno = bizno;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }
}
