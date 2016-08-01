package com.ancun.boss.pojo.workorder;

import java.util.List;

import com.ancun.boss.persistence.model.WorkOrderVisitRecord;
import com.ancun.core.page.Page;

public class WorkOrderInfoOutput {
	
	/**
	 * 工单详细
	 */
	private WorkOrderInfoPojo orderInfo;
	
	/**
	 * 工单List
	 */
	private List<WorkOrderInfoPojo>	orderList;
	
	/**
	 * 分页信息
	 */
    private Page pageinfo;
    
	/**
	 * 工单流转list
	 */
    private List<WorkOrderExchangePojo> exchangeList;
    
	/**
	 * 工单回访list
	 */
    private List<WorkOrderVisitRecord> visitList;
    
	/**
	 * 登陆用户部门名称
	 */
    private String orgName;
    
	/**
	 * 登陆用户部门名称
	 */
    private String userName;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<WorkOrderInfoPojo> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<WorkOrderInfoPojo> orderList) {
		this.orderList = orderList;
	}

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}

	public WorkOrderInfoPojo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(WorkOrderInfoPojo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public List<WorkOrderExchangePojo> getExchangeList() {
		return exchangeList;
	}

	public void setExchangeList(List<WorkOrderExchangePojo> exchangeList) {
		this.exchangeList = exchangeList;
	}

	public List<WorkOrderVisitRecord> getVisitList() {
		return visitList;
	}

	public void setVisitList(List<WorkOrderVisitRecord> visitList) {
		this.visitList = visitList;
	}
}
