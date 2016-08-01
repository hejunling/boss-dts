package com.ancun.boss.business.pojo.bizuser;

import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * 用户列表查询输入类
 * User: zkai
 * Date: 2016/4/7
 * Time: 16:44
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizUserInfoQueryInput extends BossPagePojo {
    /**
     * 电话号码
     */

    private String usernum;

    /**
     * 联系电话
     */
    private String phone;

//    /**
//     * 所属省份
//     */
//    private String rpcode;

    /**
     * 所属业务
     */
    private String bizno;

    /**
     * 资费套餐
     */
    private Long taocanid;

    /**
     * 号码状态 1.开通 ;2.暂停;s 3.退订
     */
    private String userstatus;

    /**
     * 开通类型
     */
    private String isignupsource;

    /**
     * 开通开始时间
     */
    private Date openstarttime;

    /**
     * 开通结束时间
     */
    private Date openendtime;

    /**
     * 退订开始时间
     */
    private Date cancelstarttime;

    /**
     * 退订结束时间
     */
    private Date cancelendtime;

    /**
     * 用户类型(1:个人;2:企业)
     * @return
     */
    private String usertype;


    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public String getRpcode() {
//        return rpcode;
//    }
//
//    public void setRpcode(String rpcode) {
//        this.rpcode = rpcode;
//    }

    public Long getTaocanid() {
        return taocanid;
    }

    public void setTaocanid(Long taocanid) {
        this.taocanid = taocanid;
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

    public Date getOpenstarttime() {
        return openstarttime;
    }

    public void setOpenstarttime(Date openstarttime) {
        this.openstarttime = openstarttime;
    }

    public Date getOpenendtime() {
        return openendtime;
    }

    public void setOpenendtime(Date openendtime) {
        this.openendtime = openendtime;
    }

    public Date getCancelstarttime() {
        return cancelstarttime;
    }

    public void setCancelstarttime(Date cancelstarttime) {
        this.cancelstarttime = cancelstarttime;
    }

    public Date getCancelendtime() {
        return cancelendtime;
    }

    public void setCancelendtime(Date cancelendtime) {
        this.cancelendtime = cancelendtime;
    }

    public String getBizno() {
        return bizno;
    }

    public void setBizno(String bizno) {
        this.bizno = bizno;
    }
}
