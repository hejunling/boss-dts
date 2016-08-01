package com.ancun.boss.pojo.inOutNetCount;

import com.ancun.boss.pojo.BossBasePojo;

public class InOutNetQueryInput extends BossBasePojo{
	
	//开始日期
	private String startDate;
	
	//结束日期
	private String endDate;
	
	//业务
	private String business;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	
}
