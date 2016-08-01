package com.ancun.boss.pojo.cancelRequireCharts;

/**
 * 退费退订统计输出请求封装类
 *
 * @Created on 2015年11月10日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CancelRequireChartsOutput {
	
	//月份统计
	private String month;
	//费用统计
	private Integer money;
	//人数统计
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}	
	
}
