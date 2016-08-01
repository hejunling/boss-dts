package com.ancun.boss.service.statistics;

import com.ancun.boss.pojo.statistics.ProfitCost;
import com.ancun.boss.pojo.statistics.StatisticsProfitCostInput;


public interface IProfitCostService {
	public ProfitCost statisticsProfitCost(StatisticsProfitCostInput input);
}
