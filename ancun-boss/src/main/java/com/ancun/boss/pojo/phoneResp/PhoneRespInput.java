package com.ancun.boss.pojo.phoneResp;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 号码库列表input参数
 *
 * @Created on 2015年11月05日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class PhoneRespInput extends BossPagePojo {

    /** 号码 */
    private String phone;

    /** 业务 */
    private String business;

    /** 过滤层级 */
    private String status;

    /** 获取时间 */
    private String getTime;

    /** 过滤完成 */
    private String filterOver;

    /** 付费号码 */
    private String payphone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getFilterOver() {
        return filterOver;
    }

    public void setFilterOver(String filterOver) {
        this.filterOver = filterOver;
    }

    public String getPayphone() {
        return payphone;
    }

    public void setPayphone(String payphone) {
        this.payphone = payphone;
    }
}
