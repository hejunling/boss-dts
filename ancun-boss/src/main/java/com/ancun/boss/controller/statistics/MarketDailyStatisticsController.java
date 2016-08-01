package com.ancun.boss.controller.statistics;

import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.boss.pojo.statistics.MarketDailyStatistics;
import com.ancun.boss.pojo.statistics.MarketDailyStatisticsOutput;
import com.ancun.boss.service.statistics.IMarketDailyStatisticsService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 营销日报统计 控制类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/11/3
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class MarketDailyStatisticsController extends BaseController {

    @Resource
    private IMarketDailyStatisticsService marketDailyStatisticsService;

    /**
     * 合计
     *
     * @param marketDailyInputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/marketDailyStatisticsTotal", method = RequestMethod.POST)
    public RespBody<MarketDailyStatistics> statisticsTotal(ReqBody<MarketDailyInput> marketDailyInputReqBody) throws EduException {
        MarketDailyInput marketDailyInput = marketDailyInputReqBody.getContent();
        MarketDailyStatistics statistics = marketDailyStatisticsService.statisticsTotal(marketDailyInput);
        return new RespBody<MarketDailyStatistics>(statistics);
    }

    /**
     * 统计报表详情
     *
     * @param marketDailyInputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/marketDailyStatisticsDetail", method = RequestMethod.POST)
    public RespBody<MarketDailyStatisticsOutput> statisticsDetail(ReqBody<MarketDailyInput> marketDailyInputReqBody) throws EduException {
        MarketDailyInput marketDailyInput = marketDailyInputReqBody.getContent();

        MarketDailyStatisticsOutput output = marketDailyStatisticsService.statisticsDetail(marketDailyInput);
        return new RespBody<MarketDailyStatisticsOutput>(output);
    }


}
