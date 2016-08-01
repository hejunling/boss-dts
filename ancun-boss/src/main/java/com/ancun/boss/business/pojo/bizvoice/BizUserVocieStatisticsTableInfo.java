package com.ancun.boss.business.pojo.bizvoice;

/**
 * 业务用户录音统计信息（表格显示展示）
 * User: zkai
 * Date: 2016/6/27
 * Time: 16:22
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizUserVocieStatisticsTableInfo {
    private String bizno; // 业务编号
    private String bizname; // 业务名称
    private String entno; // 企业编号
    private String userno; // 用户编号
    private String cpcertificateflg; // 是否申请公证
    private int voicecount; // 总量
    private String creattime; // 创建时间
    private String starttime; // 统计开始时间
    private String endtime; // 统计结束时间

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

    public int getVoicecount() {
        return voicecount;
    }

    public void setVoicecount(int voicecount) {
        this.voicecount = voicecount;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }
}
