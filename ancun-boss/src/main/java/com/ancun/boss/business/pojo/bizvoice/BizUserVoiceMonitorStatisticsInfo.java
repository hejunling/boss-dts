package com.ancun.boss.business.pojo.bizvoice;

/**
 * 业务用户录音月总量
 * User: zkai
 * Date: 2016/6/23
 * Time: 10:50
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizUserVoiceMonitorStatisticsInfo {
    private String bizno; // 业务编号
    private String bizname; // 业务名称
    private String entno; // 企业编号
    private String userno; // 用户编号
    private String cpcertificateflg; // 是否申请公证
    private int voiceCount; // 总量
    private int januaryCount; // 1月
    private int februaryCount; // 2月
    private int marchCount; // 3月
    private int aprilCount; // 4月
    private int mayCount; // 5月
    private int juneCount; // 6月
    private int julyCount; // 7月
    private int augustCount; // 8月
    private int septemberCount; // 9月
    private int octoberCount; // 10月
    private int novemberCount; // 11月
    private int decemberCount; // 12月

    public String getBizno() {
        return bizno;
    }

    public void setBizno(String bizno) {
        this.bizno = bizno;
    }

    public int getDecemberCount() {
        return decemberCount;
    }

    public void setDecemberCount(int decemberCount) {
        this.decemberCount = decemberCount;
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

    public int getJanuaryCount() {
        return januaryCount;
    }

    public void setJanuaryCount(int januaryCount) {
        this.januaryCount = januaryCount;
    }

    public int getFebruaryCount() {
        return februaryCount;
    }

    public void setFebruaryCount(int februaryCount) {
        this.februaryCount = februaryCount;
    }

    public int getMarchCount() {
        return marchCount;
    }

    public void setMarchCount(int marchCount) {
        this.marchCount = marchCount;
    }

    public int getAprilCount() {
        return aprilCount;
    }

    public void setAprilCount(int aprilCount) {
        this.aprilCount = aprilCount;
    }

    public int getMayCount() {
        return mayCount;
    }

    public void setMayCount(int mayCount) {
        this.mayCount = mayCount;
    }

    public int getJuneCount() {
        return juneCount;
    }

    public void setJuneCount(int juneCount) {
        this.juneCount = juneCount;
    }

    public int getJulyCount() {
        return julyCount;
    }

    public void setJulyCount(int julyCount) {
        this.julyCount = julyCount;
    }

    public int getAugustCount() {
        return augustCount;
    }

    public void setAugustCount(int augustCount) {
        this.augustCount = augustCount;
    }

    public int getSeptemberCount() {
        return septemberCount;
    }

    public void setSeptemberCount(int septemberCount) {
        this.septemberCount = septemberCount;
    }

    public int getOctoberCount() {
        return octoberCount;
    }

    public void setOctoberCount(int octoberCount) {
        this.octoberCount = octoberCount;
    }

    public int getNovemberCount() {
        return novemberCount;
    }

    public void setNovemberCount(int novemberCount) {
        this.novemberCount = novemberCount;
    }
}
