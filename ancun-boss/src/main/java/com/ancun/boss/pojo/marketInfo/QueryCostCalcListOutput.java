package com.ancun.boss.pojo.marketInfo;

import java.util.List;

import com.ancun.boss.persistence.model.CostCalc;
import com.ancun.core.page.Page;

public class QueryCostCalcListOutput {
	private List<CostCalcInfo> costcalclist;
	
	private Page pageinfo;					//分页信息

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<CostCalcInfo> getCostcalclist() {
		return costcalclist;
	}

	public void setCostcalclist(List<CostCalcInfo> costcalclist) {
		this.costcalclist = costcalclist;
	}
}
