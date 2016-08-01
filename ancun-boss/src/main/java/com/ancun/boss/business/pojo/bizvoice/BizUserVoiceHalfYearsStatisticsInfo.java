package com.ancun.boss.business.pojo.bizvoice;

/**
 * 业务用户录音半年总量
 * User: zkai
 * Date: 2016/6/23
 * Time: 10:50
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizUserVoiceHalfYearsStatisticsInfo {
    private String bizno; // 业务编号
    private String bizname; // 业务名称
    private String entno; // 企业编号
    private String userno; // 用户编号
    private String cpcertificateflg; // 是否申请公证
    private int voiceCount; // 总量
    private int firstHalfYear; // 上半年
    private int secondHalfYear; // 下半年

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

    public int getFirstHalfYear() {
        return firstHalfYear;
    }

    public void setFirstHalfYear(int firstHalfYear) {
        this.firstHalfYear = firstHalfYear;
    }

    public int getSecondHalfYear() {
        return secondHalfYear;
    }

    public void setSecondHalfYear(int secondHalfYear) {
        this.secondHalfYear = secondHalfYear;
    }
}
