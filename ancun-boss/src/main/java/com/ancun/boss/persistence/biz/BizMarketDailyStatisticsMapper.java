package com.ancun.boss.persistence.biz;

import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.boss.pojo.statistics.MarketDailyStatistics;

import java.util.List;

/**
 * 营销日报统计映射接口
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/11/3
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizMarketDailyStatisticsMapper {

    /**
     * 详情统计
     *
     * @param input
     * @return
     */
    public List<MarketDailyStatistics> statisticsDetail(MarketDailyInput input);


    /**
     * 合计
     *
     * @param input
     * @return
     */
    public MarketDailyStatistics statisticsTotal(MarketDailyInput input);
}
