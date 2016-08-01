package com.ancun.datasyn.pojo.voice;

import com.ancun.common.persistence.model.master.BizUserVoiceInfo;
import com.ancun.datasyn.pojo.BaseInfo;


/**
 * boss 录音同步对象
 * User: zkai
 * Date: 2016/5/25
 * Time: 15:35
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BossVoiceInfo extends BaseInfo  {

    private BizUserVoiceInfo bizUserVoiceInfo; // boss系统录音信息


    public BizUserVoiceInfo getBizUserVoiceInfo() {
        return bizUserVoiceInfo;
    }

    public void setBizUserVoiceInfo(BizUserVoiceInfo bizUserVoiceInfo) {
        this.bizUserVoiceInfo = bizUserVoiceInfo;
    }

    @Override
    public String toString() {
        return "BossVoiceInfo{" +
                "synTime=" + synTime +
                ", UUID='" + uuid + '\'' +
                ", synSize=" + synSize +
                ", errorInfo='" + errorInfo + '\'' +
                ", bizUserVoiceInfo=" + bizUserVoiceInfo +
                ", bizname='" + bizname + '\'' +
                '}';
    }
}
