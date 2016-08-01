package com.ancun.boss.pojo.market;

import com.ancun.boss.persistence.model.MarketDaily;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author mif
 * @version 1.0
 * @Created on 2015/9/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XStreamAlias("marketdaily")
public class MarketDailyOutPut extends MarketDaily {

    /**
     * 业务（项目名称）
     */
    private String businessName;

    /**
     * 外呼团队
     */
    private String calledTeamName;

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
