package com.ancun.boss.pojo.callQualityInfo;

import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 呼入质检输入请求封装类
 *
 * @author zkai
 * @version 1.0
 * @Created on 2015/10/15
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class CallQualityListInput extends BossPagePojo {

    // 质检员工号
    private String checkUserno;


    // 被质检人工号
    private String checkedUserno;

    /**
     * （格式为yyyy-MM-dd）
     */
    // 质检开始时间
    private String beginCheckTime;

    /**
     * （格式为yyyy-MM-dd）
     */
    // 质检结束时间
    private String endCheckTime;


    /**
     * （格式为yyyy-MM-dd）
     */
    // 来电开始时间
    private String beginTelTime;

    /**
     * （格式为yyyy-MM-dd）
     */
    // 来电结束时间
    private String endTelTime;


    // 来电号码
    private String telNo;

    private Integer totalpage; //总页数

    private Integer totalcount; //记录总数

    public String getCheckUserno() {
        return checkUserno;
    }

    public void setCheckUserno(String checkUserno) {
        this.checkUserno = checkUserno;
    }

    public String getCheckedUserno() {
        return checkedUserno;
    }

    public void setCheckedUserno(String checkedUserno) {
        this.checkedUserno = checkedUserno;
    }

    public String getBeginCheckTime() {
        return beginCheckTime;
    }

    public void setBeginCheckTime(String beginCheckTime) {
        this.beginCheckTime = beginCheckTime;
    }

    public String getEndCheckTime() {
        return endCheckTime;
    }

    public void setEndCheckTime(String endCheckTime) {
        this.endCheckTime = endCheckTime;
    }

    public Integer getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }

    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }

    public String getBeginTelTime() {
        return beginTelTime;
    }

    public void setBeginTelTime(String beginTelTime) {
        this.beginTelTime = beginTelTime;
    }

    public String getEndTelTime() {
        return endTelTime;
    }

    public void setEndTelTime(String endTelTime) {
        this.endTelTime = endTelTime;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
}
