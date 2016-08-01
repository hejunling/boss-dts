package com.ancun.datasyn.pojo.voice;

/**
 * 录音信息查询input
 * User: zkai
 * Date: 2016/5/19
 * Time: 20:26
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class VoiceInput {
    private String startime;
    private String endtime;

    public String getStartime() {
        return startime;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
