package com.ancun.boss.business.pojo.bizvoice;

/**
 * 业务用户录音季度总量
 * User: zkai
 * Date: 2016/6/23
 * Time: 10:50
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizUserVoiceQuarterStatisticsInfo {
    private String bizno; // 业务编号
    private String bizname; // 业务名称
    private String entno; // 企业编号
    private String userno; // 用户编号
    private String cpcertificateflg; // 是否申请公证
    private int voiceCount; // 总量
    private int oneQuarter; // 1季度
    private int twoQuarter; // 2季度
    private int threeQuarter; // 3季度
    private int fourQuarter; // 4季度

    public String getBizno() {
        return bizno;
    }

    public void setBizno(String bizno) {
        this.bizno = bizno;
    }

    public String getBizname() {
        return bizname;
    }

    public void setBizname(String bizname) {
        this.bizname = bizname;
    }

    public String getEntno() {
        return entno;
    }

    public void setEntno(String entno) {
        this.entno = entno;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getCpcertificateflg() {
        return cpcertificateflg;
    }

    public void setCpcertificateflg(String cpcertificateflg) {
        this.cpcertificateflg = cpcertificateflg;
    }

    public int getVoiceCount() {
        return voiceCount;
    }

    public void setVoiceCount(int voiceCount) {
        this.voiceCount = voiceCount;
    }

    public int getOneQuarter() {
        return oneQuarter;
    }

    public void setOneQuarter(int oneQuarter) {
        this.oneQuarter = oneQuarter;
    }

    public int getTwoQuarter() {
        return twoQuarter;
    }

    public void setTwoQuarter(int twoQuarter) {
        this.twoQuarter = twoQuarter;
    }

    public int getThreeQuarter() {
        return threeQuarter;
    }

    public void setThreeQuarter(int threeQuarter) {
        this.threeQuarter = threeQuarter;
    }

    public int getFourQuarter() {
        return fourQuarter;
    }

    public void setFourQuarter(int fourQuarter) {
        this.fourQuarter = fourQuarter;
    }
}
