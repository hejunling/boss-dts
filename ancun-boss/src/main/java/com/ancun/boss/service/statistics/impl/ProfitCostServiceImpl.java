package com.ancun.boss.service.statistics.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.persistence.biz.BizUserInfoBizMapper;
import com.ancun.boss.persistence.biz.CostCalcBizMapper;
import com.ancun.boss.pojo.statistics.ProfitCost;
import com.ancun.boss.pojo.statistics.StatisticsProfitCostInput;
import com.ancun.boss.service.statistics.IProfitCostService;

/**
 * 收益成本统计传逻辑实现类
 *
 * @Created on 2015年11月4日
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class ProfitCostServiceImpl implements IProfitCostService{
	@Resource
	private CostCalcBizMapper costCalcBizMapper;
	
	@Resource
	private BizUserInfoBizMapper bizUserInfoBizMapper;
	
	@Override
    public ProfitCost statisticsProfitCost(StatisticsProfitCostInput input) {
		//查询统计成本
		String cost  = costCalcBizMapper.statisticsCost(input);
		
		//查询统计收益
		String profit = bizUserInfoBizMapper.statisticsProfit(input);
		
		ProfitCost profitCost = new ProfitCost();
		profitCost.setCost(cost);
		profitCost.setProfit(profit);
	    return profitCost;
    }

}
