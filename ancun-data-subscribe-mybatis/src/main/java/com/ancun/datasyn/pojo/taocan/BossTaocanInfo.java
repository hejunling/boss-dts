package com.ancun.datasyn.pojo.taocan;

import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.datasyn.pojo.BaseInfo;

import java.util.Date;

/**
 * boss 套餐同步对象
 * User: zkai
 * Date: 2016/5/25
 * Time: 19:17
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BossTaocanInfo  extends BaseInfo {

    private BizComboInfo bizComboInfo; // boss系统套餐信息

    public String getBizname() {
        return bizname;
    }

    public void setBizname(String bizname) {
        this.bizname = bizname;
    }

    public Date getSynTime() {
        return synTime;
    }

    public void setSynTime(Date synTime) {
        this.synTime = synTime;
    }

    public int getSynSize() {
        return synSize;
    }

    public void setSynSize(int synSize) {
        this.synSize = synSize;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public BizComboInfo getBizComboInfo() {
        return bizComboInfo;
    }

    public void setBizComboInfo(BizComboInfo bizComboInfo) {
        this.bizComboInfo = bizComboInfo;
    }

    @Override
    public String toString() {
        return "BossTaocanInfo{" +
                "synTime=" + synTime +
                ", synSize=" + synSize +
                ", errorInfo='" + errorInfo + '\'' +
                ", bizComboInfo=" + bizComboInfo +
                ", bizname='" + bizname + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
