package com.ancun.boss.service.cancelRequireCharts;

import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsInput;
import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsListOutput;

/**
 * 退费退订统计报表接口
 *
 * @Created on 2015年11月10日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICancelRequireChartsService {
	
	/**
	 * 查询退订退费
	 * @param input
	 * @return
	 */
	public CancelRequireChartsListOutput queryCancelRequireChartsList(CancelRequireChartsInput input);
	
}
