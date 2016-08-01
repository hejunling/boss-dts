package com.ancun.boss.business.controller.bizuservoice;

import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsInput;
import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsListOutput;
import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsTableInput;
import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsTableOutput;
import com.ancun.boss.business.service.bizuservoice.IBizUserVoiceStatisticsService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户录音统计controller
 * User: zkai
 * Date: 2016/6/23
 * Time: 14:48
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class BizUserVoiceStatisticsController {
    @Resource
    private IBizUserVoiceStatisticsService iBizUserVoiceStatisticsService;
    /**
     * 用户录音统计（月，半年，年，季度数量统计）
     *
     * @return 查询结果
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/bizUserVoiceCountStatistics", method = RequestMethod.POST)
    public RespBody<BizUserVoiceStatisticsListOutput> bizUserVoiceCountStatistics(ReqBody<BizUserVoiceStatisticsInput> input) {
        BizUserVoiceStatisticsListOutput out =iBizUserVoiceStatisticsService.statisticsBizUserVoiceInfo(input.getContent());
        ThreadLocalUtil.setContent("用户录音统计(月，半年，年，季度数量统计)：");
        return new RespBody<BizUserVoiceStatisticsListOutput>(out);
    }

    /**
     * 用户录音统计（表格展示）
     *
     * @return 查询结果
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/bizUserVoiceStatisticsTable", method = RequestMethod.POST)
    public RespBody<BizUserVoiceStatisticsTableOutput> bizUserVoiceStatisticsTable(ReqBody<BizUserVoiceStatisticsTableInput> input) {
        BizUserVoiceStatisticsTableOutput out =iBizUserVoiceStatisticsService.statisticsBizUserVoiceInfoTable(input.getContent());
        ThreadLocalUtil.setContent("用户录音统计(表格形式展示)：");
        return new RespBody<BizUserVoiceStatisticsTableOutput>(out);
    }

}
