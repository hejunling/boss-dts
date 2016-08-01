package com.ancun.boss.pojo.phoneResp;

import com.ancun.boss.pojo.BossBasePojo;

/**
 * 号码数input参数
 *
 * @Created on 2015年12月21日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CountPhoneInput extends BossBasePojo {

    /** 业务 */
    private String business;

    /** 获取时间 */
    private String getTime;

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }
}
