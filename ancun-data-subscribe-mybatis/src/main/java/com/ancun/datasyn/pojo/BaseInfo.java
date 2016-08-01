package com.ancun.datasyn.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 基础信息
 * User: zkai
 * Date: 2016/6/6
 * Time: 14:18
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BaseInfo  implements Serializable {
    private static final long serialVersionUID = 1L;

    public UUID uuid;
    public Date synTime;  // 同步时间

    public int synSize; // 同步数量

    public String errorInfo; //  错误信息

    public String bizname; // 业务编号

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

    public String getBizname() {
        return bizname;
    }

    public void setBizname(String bizname) {
        this.bizname = bizname;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
