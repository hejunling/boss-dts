package com.ancun.boss.pojo.phoneResp;

import com.ancun.boss.pojo.BossBasePojo;

/**
 * 获取时间选择项inPut参数
 *
 * @Created on 2015年12月24日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class GetTimeOptionsInput extends BossBasePojo {

    /** 业务类型 */
    private String business;

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
}
