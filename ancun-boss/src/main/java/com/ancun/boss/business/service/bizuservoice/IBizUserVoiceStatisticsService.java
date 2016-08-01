package com.ancun.boss.business.service.bizuservoice;

import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsInput;
import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsListOutput;
import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsTableInput;
import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsTableOutput;

/**
 * 用户录音统计接口
 * User: zkai
 * Date: 2016/6/23
 * Time: 15:35
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IBizUserVoiceStatisticsService {
    /**
     * 用户录音量(月，半年，年，季度数量统计)
     * @param input
     */
    public BizUserVoiceStatisticsListOutput statisticsBizUserVoiceInfo(BizUserVoiceStatisticsInput input);

    /**
     * 用户录音量(表格形式展示)
     * @param input
     */
    public BizUserVoiceStatisticsTableOutput statisticsBizUserVoiceInfoTable(BizUserVoiceStatisticsTableInput input);
}
