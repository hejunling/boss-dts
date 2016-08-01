package com.ancun.datasyn.pojo.user;

import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datasyn.pojo.BaseInfo;


/**
 * boss 用户信息同步对象
 * User: zkai
 * Date: 2016/5/25
 * Time: 20:31
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BossUserInfo  extends BaseInfo {

    private BizUserInfo bizUserInfo; // boss用户信息


    public BizUserInfo getBizUserInfo() {
        return bizUserInfo;
    }

    public void setBizUserInfo(BizUserInfo bizUserInfo) {
        this.bizUserInfo = bizUserInfo;
    }

    @Override
    public String toString() {
        return "BossUserInfo{" +
                "bizUserInfo=" + bizUserInfo +
                ", UUID='" + uuid + '\'' +
                ", synTime=" + synTime +'\'' +
                ", synSize=" + synSize + '\'' +
                ", errorInfo='" + errorInfo + '\'' +
                ", bizname='" + bizname + '\'' +
                '}';
    }
}
