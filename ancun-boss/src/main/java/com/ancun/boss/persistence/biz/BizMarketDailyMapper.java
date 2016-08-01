package com.ancun.boss.persistence.biz;

import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.boss.pojo.market.MarketDailyOutPut;
import com.ancun.core.exception.EduException;

import java.util.List;

/** 营销日报
 * @author mif
 * @version 1.0
 * @Created on 2015/9/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizMarketDailyMapper {
    /**
     * 查询营销日报
     * @param input
     * @return
     * @throws EduException
     */
    List<MarketDailyOutPut> queryDailyList(MarketDailyInput input);
}
