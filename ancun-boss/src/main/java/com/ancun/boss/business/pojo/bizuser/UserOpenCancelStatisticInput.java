package com.ancun.boss.business.pojo.bizuser;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossPagePojo;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 用户开通退订统计输入类
 * User: zkai
 * Date: 2016/6/16
 * Time: 16:36
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class UserOpenCancelStatisticInput extends BossPagePojo {

    private String bizno; // 业务编号

    private String status; // 状态

    private String source; // 注册、退订 来源

    private Date startime; // 查询开始时间

    private Date endtime; //  查询结束时间

    public String getBizno() {
        return bizno;
    }

    public void setBizno(String bizno) {
        this.bizno = bizno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getStartime() {
        return startime;
    }

    public void setStartime(Date startime) {
        this.startime = startime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
