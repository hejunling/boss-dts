package com.ancun.boss.service.callincharts;

import com.ancun.boss.pojo.callincharts.CallinChartsInput;
import com.ancun.boss.pojo.callincharts.CallinChartsListOutput;


/**
 * 呼入电话统计报表接口
 * @author cys1993
 *
 */
public interface ICallinChartsService {
	/**
	 * 查询
	 */
	public CallinChartsListOutput queryCallinChartsList(CallinChartsInput input);

}
