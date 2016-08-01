package com.ancun.boss.service.statistics.impl;

import com.ancun.boss.persistence.biz.BizMarketDailyStatisticsMapper;
import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.boss.pojo.statistics.MarketDailyStatistics;
import com.ancun.boss.pojo.statistics.MarketDailyStatisticsOutput;
import com.ancun.boss.service.statistics.IMarketDailyStatisticsService;
import com.ancun.core.exception.EduException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 营销日报统计接口实现类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/11/3
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class MarketDailyStatisticsServiceImpl implements IMarketDailyStatisticsService {
    @Resource
    private BizMarketDailyStatisticsMapper bizMarketDailyStatisticsMapper;

    @Override
    public MarketDailyStatisticsOutput statisticsDetail(MarketDailyInput marketDailyInput) throws EduException {

        List<MarketDailyStatistics> marketDailyStatisticsList = bizMarketDailyStatisticsMapper.statisticsDetail(marketDailyInput);

        MarketDailyStatisticsOutput output = new MarketDailyStatisticsOutput();
        output.setMarketDailyStatisticsList(marketDailyStatisticsList);
        output.setPageinfo(marketDailyInput.getPage());

        return output;
    }

    @Override
    public MarketDailyStatistics statisticsTotal(MarketDailyInput marketDailyInput) throws EduException {
        MarketDailyStatistics statistics = bizMarketDailyStatisticsMapper.statisticsTotal(marketDailyInput);

//        if (statistics == null) {
//            statistics = new MarketDailyStatistics();
//        }
        return statistics;

    }
}
