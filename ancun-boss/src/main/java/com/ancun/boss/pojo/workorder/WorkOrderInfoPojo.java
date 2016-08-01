package com.ancun.boss.pojo.workorder;

import com.ancun.boss.persistence.model.UserWorkOrderInfo;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenb
 * @version 1.0
 * @Created on 2015/10/16
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XStreamAlias("workorderinfo")
public class WorkOrderInfoPojo  extends UserWorkOrderInfo{
	
	/** 业务名称  */
	private String businessName;
	
	/** 工单类型  */
	private String ordertypeName;
	
	/** 工单来源  */
	private String orderSourceName;
	
	/** 投诉来源  */
	private String complaintSourceName;
	
	/** 投诉类型  */
	private String complaintTypeName;
	
	/** 投诉属性  */
	private String complaintPropertyName;
	
	/** 状态名称  */
	private String orderStatusName;
	
	/** 当前登录用户  */
	private String loginUserNo;
	
	/** 创建人 */
	private String createUserName;
	
	/** 处理流程条数 */
	private Integer orderExchangSize;
	
	public String getOrdertypeName() {
		return ordertypeName;
	}

	public void setOrdertypeName(String ordertypeName) {
		this.ordertypeName = ordertypeName;
	}

	public String getOrderSourceName() {
		return orderSourceName;
	}

	public void setOrderSourceName(String orderSourceName) {
		this.orderSourceName = orderSourceName;
	}

	public String getComplaintSourceName() {
		return complaintSourceName;
	}

	public void setComplaintSourceName(String complaintSourceName) {
		this.complaintSourceName = complaintSourceName;
	}

	public String getComplaintTypeName() {
		return complaintTypeName;
	}

	public void setComplaintTypeName(String complaintTypeName) {
		this.complaintTypeName = complaintTypeName;
	}

	public String getComplaintPropertyName() {
		return complaintPropertyName;
	}

	public void setComplaintPropertyName(String complaintPropertyName) {
		this.complaintPropertyName = complaintPropertyName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getLoginUserNo() {
		return loginUserNo;
	}

	public void setLoginUserNo(String loginUserNo) {
		this.loginUserNo = loginUserNo;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Integer getOrderExchangSize() {
		return orderExchangSize;
	}

	public void setOrderExchangSize(Integer orderExchangSize) {
		this.orderExchangSize = orderExchangSize;
	}

}
