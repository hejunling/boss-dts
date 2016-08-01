package com.ancun.boss.pojo.marketInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 营销成本查询的请求对象
 *
 * @Created on 2015-10-12
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class QueryCostCalcListInput extends BossPagePojo{
	
	public Long id;
	
	/*
	 * 结款方式（数据字典）
	 * */
	private String type;
	
	/*
	 * 业务（项目名称）
	 * */
	private String business;
	
	
	/*
	 * 外呼团队
	 * */
	private String calledteam;


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	

}
