package com.ancun.boss.pojo.callInfo;

import java.util.List;

import com.ancun.boss.persistence.model.CancelRecord;

/**
 * 投诉退订信息返回封装类
 * @author cys1993
 *
 */
public class CancelRecordBusinessOutput extends CancelRecord{
	
	/**
	 * 业务名称
	 */
	private String businessName;
	
	private String cancelReqireName;
	
	private String sourceName;
	
	private List<CancelRecord> cancelrecord;
	

	public String getCancelReqireName() {
		return cancelReqireName;
	}

	public void setCancelReqireName(String cancelReqireName) {
		this.cancelReqireName = cancelReqireName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<CancelRecord> getCancelrecord() {
		return cancelrecord;
	}

	public void setCancelrecord(List<CancelRecord> cancelrecord) {
		this.cancelrecord = cancelrecord;
	}
	

}
