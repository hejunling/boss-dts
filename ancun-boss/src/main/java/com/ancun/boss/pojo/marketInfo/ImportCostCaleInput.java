package com.ancun.boss.pojo.marketInfo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 营销成本导入的请求对象
 *
 * @Created on 2015-10-12
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class ImportCostCaleInput extends BossPagePojo{
	private String business;
	
	private String calledteam;
	
	private String month;
	
	private String type;
	
	private String businessname ;
	private String calledteamname;
	private String typename;
	
	private List<CostCalcInput> costcalelist;
	

	public String getBusinessname() {
		return businessname;
	}

	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}

	public String getCalledteamname() {
		return calledteamname;
	}

	public void setCalledteamname(String calledteamname) {
		this.calledteamname = calledteamname;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public List<CostCalcInput> getCostcalelist() {
		return costcalelist;
	}

	public void setCostcalelist(List<CostCalcInput> costcalelist) {
		this.costcalelist = costcalelist;
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
