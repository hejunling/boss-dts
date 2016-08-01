package com.ancun.boss.service.statistics;

import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.boss.pojo.statistics.MarketDailyStatistics;
import com.ancun.boss.pojo.statistics.MarketDailyStatisticsOutput;
import com.ancun.core.exception.EduException;

/**
 * 营销日报统计 接口
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/11/3
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IMarketDailyStatisticsService {

    /**
     * 营销日报统计 详情
     *
     * @param marketDailyInput
     * @return
     * @throws EduException
     */
    MarketDailyStatisticsOutput statisticsDetail(MarketDailyInput marketDailyInput) throws EduException;

    /**
     * 营销日报统计 合计
     *
     * @param marketDailyInput
     * @return
     * @throws EduException
     */
    MarketDailyStatistics statisticsTotal(MarketDailyInput marketDailyInput) throws EduException;
}
