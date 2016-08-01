package com.ancun.boss.pojo.phoneResp;

import java.util.Date;

/**
 * 号码库列表用号码库对象
 *
 * @Created on 2015年11月13日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class PhoneRepInfo {

    /** 唯一主键 */
    private String id;

    /** 号码 */
    private String phone;

    /** 业务名称 */
    private String bizName;

    /** 获取时间 */
    private Date getTime;

    /** 批次 */
//    private String phoneSource;

    /** 过滤层级 */
    private String status;

    /** 过滤完成 */
    private String filterOver;

    /** 清洗状态 */
    private String cleanStatus;

    /** 付费账号 */
    private String payphone;

    /** 营销团队 */
    private String dividtoname;

    /** 分配时间 */
    private Date dividtime;
//
//    /** 营销状况 */
//    private String marketStatus;
//
//    /** 营销成功时间 */
//    private Date marketTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilterOver() {
        return filterOver;
    }

    public void setFilterOver(String filterOver) {
        this.filterOver = filterOver;
    }

    public String getCleanStatus() {
        return cleanStatus;
    }

    public void setCleanStatus(String cleanStatus) {
        this.cleanStatus = cleanStatus;
    }

    public String getPayphone() {
        return payphone;
    }

    public void setPayphone(String payphone) {
        this.payphone = payphone;
    }

    public String getDividtoname() {
        return dividtoname;
    }

    public void setDividtoname(String dividtoname) {
        this.dividtoname = dividtoname;
    }

    public Date getDividtime() {
        return dividtime;
    }

    public void setDividtime(Date dividtime) {
        this.dividtime = dividtime;
    }
}
