package com.ancun.boss.pojo.statistics;

import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 营销日报 响应封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/11/3
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class MarketDailyStatisticsOutput {

    /**
     * 统计列表
     */
    private List<MarketDailyStatistics> marketDailyStatisticsList;

    /**
     * 分页信息
     */
    private Page pageinfo;

    public List<MarketDailyStatistics> getMarketDailyStatisticsList() {
        return marketDailyStatisticsList;
    }

    public void setMarketDailyStatisticsList(List<MarketDailyStatistics> marketDailyStatisticsList) {
        this.marketDailyStatisticsList = marketDailyStatisticsList;
    }

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }
}
