package com.ancun.boss.pojo.statistics;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 收益成本的请求对象
 *
 * @Created on 2015-10-12
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class StatisticsProfitCostOutput {
	private ProfitCost profitcost;

	public ProfitCost getProfitcost() {
		return profitcost;
	}

	public void setProfitcost(ProfitCost profitcost) {
		this.profitcost = profitcost;
	}
}
