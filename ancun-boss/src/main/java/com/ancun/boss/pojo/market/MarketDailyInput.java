package com.ancun.boss.pojo.market;

import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 营销日报请求封装类
 * @author mif
 * @version 1.0
 * @Created on 2015/9/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class MarketDailyInput extends BossPagePojo {

    /**
     * 业务（项目名称）
     */
    private String business;

    /**
     * 外呼团队
     */
    private String calledTeam;

    /**
     * 开始时间（格式为yyyy-MM-dd）
     */
    private String dateDailyb;

    /**
     * （格式为yyyy-MM-dd）
     */
    private String dateDailye;

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCalledTeam() {
        return calledTeam;
    }

    public void setCalledTeam(String calledTeam) {
        this.calledTeam = calledTeam;
    }

    public String getDateDailyb() {
        return dateDailyb;
    }

    public void setDateDailyb(String dateDailyb) {
        this.dateDailyb = dateDailyb;
    }

    public String getDateDailye() {
        return dateDailye;
    }

    public void setDateDailye(String dateDailye) {
        this.dateDailye = dateDailye;
    }
}
