package com.ancun.boss.pojo.callincharts;

import java.util.List;

import com.ancun.core.page.Page;
//import com.ancun.boss.pojo.callincharts.CallinChartsOutput;

/**
 * 呼入电话统计输出请求封装类
 * @author cys1993
 *
 */
public class CallinChartsListOutput {
	//呼入来电统计输出list
	private List<CallinChartsOutput> callinChartsOutput;
	
	//投诉退订来电统计输出list
//	private List<CallinChartsOutput> cancelChartsOutput;
	
	//分页信息
	private Page pageinfo;
	
	public List<CallinChartsOutput> getCallinChartsOutput() {
		return callinChartsOutput;
	}

	public void setCallinChartsOutput(List<CallinChartsOutput> callinChartsOutput) {
		this.callinChartsOutput = callinChartsOutput;
	}

//	public List<CallinChartsOutput> getCancelChartsOutput() {
//		return cancelChartsOutput;
//	}
//
//	public void setCancelChartsOutput(List<CallinChartsOutput> cancelChartsOutput) {
//		this.cancelChartsOutput = cancelChartsOutput;
//	}

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}
	
	
	
	
}
