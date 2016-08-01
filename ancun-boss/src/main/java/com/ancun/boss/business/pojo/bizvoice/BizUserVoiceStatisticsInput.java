package com.ancun.boss.business.pojo.bizvoice;

import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 业务录音录音统计输入类（月，半年，年，季度数量统计)
 * User: zkai
 * Date: 2016/6/23
 * Time: 14:16
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class BizUserVoiceStatisticsInput extends BossPagePojo {
    private String bizno; // 业务编号
    private String  cpcertificateflg; // 是否申请公证(1:已申请;2:未申请)
    private String year;  // 年份
    private String exporttype;  // 统计类型(1:月平均分, 2:季度平均分，3:半年平均分，4：年平均分)

    public String getBizno() {
        return bizno;
    }

    public void setBizno(String bizno) {
        this.bizno = bizno;
    }

    public String getCpcertificateflg() {
        return cpcertificateflg;
    }

    public void setCpcertificateflg(String cpcertificateflg) {
        this.cpcertificateflg = cpcertificateflg;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getExporttype() {
        return exporttype;
    }

    public void setExporttype(String exporttype) {
        this.exporttype = exporttype;
    }
}
