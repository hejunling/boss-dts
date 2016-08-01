package com.ancun.boss.pojo.callInfo;

import java.util.List;

import com.ancun.boss.persistence.model.CallInRecord;

/**
 * 信息返回封装类
 * @author cys
 *
 */
public class CallInRecordBusinessOutput extends CallInRecord{
	
	private String businessName;
	
	private String callBackName;
	
	private String sexName;
	
	private List<CallInRecord> callinrecord;
	
	public String getCallBackName() {
		return callBackName;
	}

	public void setCallBackName(String callBackName) {
		this.callBackName = callBackName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<CallInRecord> getCallinrecord() {
		return callinrecord;
	}

	public void setCallinrecord(List<CallInRecord> callinrecord) {
		this.callinrecord = callinrecord;
	}
	

}
