package com.ancun.datasyn.pojo.userlife;

import com.ancun.common.persistence.model.master.BizUserLifeCircle;
import com.ancun.datasyn.pojo.BaseInfo;

import java.util.Date;

/**
 * boss用户生命周期对象
 * User: zkai
 * Date: 2016/5/27
 * Time: 15:38
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BossUserLifeInfo extends BaseInfo {

    private BizUserLifeCircle bizUserLifeCircle; // boss用户生命周期

    private Date openTime; // 开通时间
    private Date cancelTime; // 退订时间

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public BizUserLifeCircle getBizUserLifeCircle() {
        return bizUserLifeCircle;
    }

    public void setBizUserLifeCircle(BizUserLifeCircle bizUserLifeCircle) {
        this.bizUserLifeCircle = bizUserLifeCircle;
    }

    @Override
    public String toString() {
        return "BossUserLifeInfo{" +
                "synTime=" + synTime +
                ", UUID='" + uuid + '\'' +
                ", synSize=" + synSize +
                ", errorInfo='" + errorInfo + '\'' +
                ", bizUserLifeCircle=" + bizUserLifeCircle +
                ", bizname='" + bizname + '\'' +
                '}';
    }
}
