package com.ancun.boss.pojo.workorder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 工单服务查询input
 *
 * @Created on 2015年10月15日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class WorkOrderQueryInput extends BossPagePojo {
	
	/**	工单编号 */
	private String orderno;
	
	/**	创建人 */
	private String userNo;
	
	/** 来电号码  */
	private String callPhone;
	
	/** 业务  */
	private String business;
	
	/** 状态  */
	private String status;
	
	/** 提交时间-开始  */
	private String submitStart;
	
	/** 提交时间-结束  */
	private String submitEnd;
	
	/** 完成时间-开始  */
	private String completeStart;
	
	/** 完成时间-结束 */
	private String completeEnd;
	
	/** 数据权限 */
	private String dataAccess; // 1:全部 2：部分
	
	private Integer totalpage; // 总页数
	private Integer totalcount; // 记录总数

	public Integer getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(Integer totalpage) {
		this.totalpage = totalpage;
	}

	public Integer getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getCallPhone() {
		return callPhone;
	}

	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubmitStart() {
		return submitStart;
	}

	public void setSubmitStart(String submitStart) {
		this.submitStart = submitStart;
	}

	public String getSubmitEnd() {
		return submitEnd;
	}

	public void setSubmitEnd(String submitEnd) {
		this.submitEnd = submitEnd;
	}

	public String getCompleteStart() {
		return completeStart;
	}

	public void setCompleteStart(String completeStart) {
		this.completeStart = completeStart;
	}

	public String getCompleteEnd() {
		return completeEnd;
	}

	public void setCompleteEnd(String completeEnd) {
		this.completeEnd = completeEnd;
	}

	public String getOrderNo() {
		return orderno;
	}

	public void setOrderNo(String orderno) {
		this.orderno = orderno;
	}

	public String getDataAccess() {
		return dataAccess;
	}

	public void setDataAccess(String dataAccess) {
		this.dataAccess = dataAccess;
	}
	
}
