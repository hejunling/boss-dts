package com.ancun.boss.controller.statistics;

import com.ancun.boss.pojo.statistics.ProfitCost;
import com.ancun.boss.pojo.statistics.StatisticsProfitCostInput;
import com.ancun.boss.pojo.statistics.StatisticsProfitCostOutput;
import com.ancun.boss.service.statistics.IProfitCostService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 收益成本
 *
 * @Created on 2015年11月3日
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class ProfitCostController {
	@Resource
    private IProfitCostService profitCostService;
	
	/**
	 * 收益成本-查询
	 * 
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/statisticsProfitCost", method = RequestMethod.POST)
	public RespBody<StatisticsProfitCostOutput> statisticsProfitCost(ReqBody<StatisticsProfitCostInput> input) {
		
		ProfitCost profitCost= profitCostService.statisticsProfitCost(input.getContent());
		
		//返回对象组装
		StatisticsProfitCostOutput outputMode = new StatisticsProfitCostOutput();
		outputMode.setProfitcost(profitCost);
		
		RespBody<StatisticsProfitCostOutput> output = new RespBody<StatisticsProfitCostOutput>(outputMode);
		
		return output;
	}
	
	
}
