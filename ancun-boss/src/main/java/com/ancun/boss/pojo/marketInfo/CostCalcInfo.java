package com.ancun.boss.pojo.marketInfo;

import com.ancun.boss.persistence.model.CostCalc;

public class CostCalcInfo extends CostCalc{
	private String businessname;
	private String calledteamname;
	private String typename;
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
}
