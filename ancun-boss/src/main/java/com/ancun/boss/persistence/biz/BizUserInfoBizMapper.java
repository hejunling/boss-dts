package com.ancun.boss.persistence.biz;

import com.ancun.boss.business.pojo.bizuser.UserOpenCancelInfo;
import com.ancun.boss.business.pojo.bizuser.UserOpenCancelStatisticInput;
import com.ancun.boss.pojo.statistics.StatisticsProfitCostInput;

import java.util.List;

/**
 * 收益统计查询mapper。
 *
 * @Created on 2015年11月4日
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizUserInfoBizMapper {
	public String statisticsProfit(StatisticsProfitCostInput input);

	/**
	 * 用户开通退订数目统计
	 * add by zkai on 2016/06/17
	 * @param input
	 * @return
     */
	public List<UserOpenCancelInfo>   userOpenCancelStatistic(UserOpenCancelStatisticInput input);
}
