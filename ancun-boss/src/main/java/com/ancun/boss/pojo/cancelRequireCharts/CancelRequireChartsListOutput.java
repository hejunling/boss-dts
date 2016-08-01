package com.ancun.boss.pojo.cancelRequireCharts;

import java.util.List;

import com.ancun.core.page.Page;

/**
 * 退费退订统计输出请求封装类
 *
 * @Created on 2015年11月10日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CancelRequireChartsListOutput {
	
	//退费退订统计输出list
	private List<CancelRequireChartsOutput> cancelRequireChartsOutput;

	public List<CancelRequireChartsOutput> getCancelRequireChartsOutput() {
		return cancelRequireChartsOutput;
	}

	public void setCancelRequireChartsOutput(List<CancelRequireChartsOutput> cancelRequireChartsOutput) {
		this.cancelRequireChartsOutput = cancelRequireChartsOutput;
	}
		
}
