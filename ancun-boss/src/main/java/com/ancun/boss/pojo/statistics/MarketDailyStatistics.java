package com.ancun.boss.pojo.statistics;

import com.ancun.utils.StringUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 营销日报封装对象
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/11/3
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XStreamAlias("statistics")
public class MarketDailyStatistics {

    /**
     * 座席数
     */
    private String seatsNumbers;

    /**
     * 外呼总量
     */
    private String calledNumbers;

    /**
     * 接通总量
     */
    private String connectNumbers;

    /**
     * 营销成功量
     */
    private String successDailys;


    /**
     * 接通率(接通数/外呼数量)
     */
    private String connectPercent;

    /**
     * 接通成功率（日成功量/接通数）
     */
    private String connectSuccessPercent;

    /**
     * 人均接通数（接通数量/坐席数）
     */
    private String connectedAvg;

    /**
     * 人均成功数（日成功量/坐席数）
     */
    private String successAvg;

    /**
     * 业务（项目名称）
     */
    private String businessName;

    /**
     * 外呼团队
     */
    private String calledTeamName;


    public String getSeatsNumbers() {
        return seatsNumbers;
    }

    public void setSeatsNumbers(String seatsNumbers) {
        this.seatsNumbers = seatsNumbers;
    }

    public String getConnectedAvg() {
        return connectedAvg;
    }

    public void setConnectedAvg(String connectedAvg) {
        this.connectedAvg = connectedAvg;
    }

    public String getSuccessAvg() {
        return successAvg;
    }

    public String getCalledNumbers() {
        return calledNumbers;
    }

    public void setCalledNumbers(String calledNumbers) {
        this.calledNumbers = calledNumbers;
    }

    public String getConnectNumbers() {
        return connectNumbers;
    }

    public void setConnectNumbers(String connectNumbers) {
        this.connectNumbers = connectNumbers;
    }

    public String getSuccessDailys() {
        return successDailys;
    }

    public void setSuccessDailys(String successDailys) {
        this.successDailys = successDailys;
    }

    public String getConnectPercent() {
//            接通率(接通数/外呼数量)
        if (StringUtil.isNotEmpty(this.connectNumbers) && (StringUtil.isNotEmpty(this.calledNumbers))) {
            return StringUtil.percentage(this.connectNumbers, this.calledNumbers);
        }
        return "-";
    }

    public void setConnectPercent(String connectPercent) {
        this.connectPercent = connectPercent;
    }

    public String getConnectSuccessPercent() {
//        接通成功率（日成功量/接通数）
        if (StringUtil.isNotEmpty(this.successDailys) && StringUtil.isNotEmpty(this.connectNumbers)) {
            return StringUtil.percentage(this.successDailys, this.connectNumbers);
        }
        return "-";
    }

    public void setConnectSuccessPercent(String connectSuccessPercent) {
        this.connectSuccessPercent = connectSuccessPercent;
    }

    public void setSuccessAvg(String successAvg) {
        this.successAvg = successAvg;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCalledTeamName() {
        return calledTeamName;
    }

    public void setCalledTeamName(String calledTeamName) {
        this.calledTeamName = calledTeamName;
    }
}
