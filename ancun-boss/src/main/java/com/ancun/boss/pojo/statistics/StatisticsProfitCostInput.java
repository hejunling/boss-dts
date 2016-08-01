package com.ancun.boss.pojo.statistics;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 收益成本统计的请求对象
 *
 * @Created on 2015-11-4
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class StatisticsProfitCostInput extends BossPagePojo{
	/*
	 * 业务（项目名称）
	 * */
	private String business;
	
	/*
	 * 外呼团队
	 * */
	private String calledteam;
	
	/*
	 * 开始时间
	 * */
	private String starttime;
	
	/*
	 * 结束时间
	 * */
	private String endtime;

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getCalledteam() {
		return calledteam;
	}

	public void setCalledteam(String calledteam) {
		this.calledteam = calledteam;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
