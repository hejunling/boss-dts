package com.ancun.boss.business.pojo.bizuser;

import java.util.Date;

/**
 * 用户开通退订信息
 * User: zkai
 * Date: 2016/6/16
 * Time: 16:36
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class UserOpenCancelInfo {
    private int number; // 开通，退订数量
    private String bizno; // 业务名称
    private String insource; // 开通来源
    private String offsource; // 退订来源
    private Date intime; // 开通时间
    private Date offtime; // 退订时间
    private String status; // 状态

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBizno() {
        return bizno;
    }

    public void setBizno(String bizno) {
        this.bizno = bizno;
    }

    public String getInsource() {
        return insource;
    }

    public void setInsource(String insource) {
        this.insource = insource;
    }

    public String getOffsource() {
        return offsource;
    }

    public void setOffsource(String offsource) {
        this.offsource = offsource;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Date getOfftime() {
        return offtime;
    }

    public void setOfftime(Date offtime) {
        this.offtime = offtime;
    }
}
