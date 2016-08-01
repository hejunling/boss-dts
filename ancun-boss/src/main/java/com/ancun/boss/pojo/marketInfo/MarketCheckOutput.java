package com.ancun.boss.pojo.marketInfo;

import java.util.List;

import com.ancun.core.page.Page;
import com.ancun.boss.persistence.model.MarketCheck;



public class MarketCheckOutput {

	/**
	 * 营销质检列表
	 */
	private List<MarketCheckInfoPojo> marketChcekList;
	
	/**
	 * 营销质检详细
	 */
	private MarketCheckInfoPojo marketCheck;
	
	/**
	 * 分页信息
	 */
    private Page pageinfo;
	
	public List<MarketCheckInfoPojo> getMarketChcekList() {
		return marketChcekList;
	}

	public void setMarketChcekList(List<MarketCheckInfoPojo> marketChcekList) {
		this.marketChcekList = marketChcekList;
	}

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}

	public MarketCheckInfoPojo getMarketCheck() {
		return marketCheck;
	}

	public void setMarketCheck(MarketCheckInfoPojo marketCheck) {
		this.marketCheck = marketCheck;
	}

}
