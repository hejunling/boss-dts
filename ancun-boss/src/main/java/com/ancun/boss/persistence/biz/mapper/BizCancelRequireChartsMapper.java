package com.ancun.boss.persistence.biz.mapper;

import java.util.List;

import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsInput;
import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsOutput;

/**
 * 
 *
 * @Created on 2015年11月10日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizCancelRequireChartsMapper {
	
	//退订退费查询
	List<CancelRequireChartsOutput> queryCancelRequireChartsTDTFList(CancelRequireChartsInput input);
	
	//退订查询
	List<CancelRequireChartsOutput> queryCancelRequireChartsTDList(CancelRequireChartsInput input);
}
