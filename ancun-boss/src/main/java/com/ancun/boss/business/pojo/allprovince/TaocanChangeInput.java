package com.ancun.boss.business.pojo.allprovince;

public class TaocanChangeInput {
	
	private String userno;
	private String changeTcType;
	private String oldTcType;
	private String newTcType;
	private boolean rollback;
	private String accessid;
	private String provinceCode;
	private String requestType;
	
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getChangeTcType() {
		return changeTcType;
	}
	public void setChangeTcType(String changeTcType) {
		this.changeTcType = changeTcType;
	}
	public String getOldTcType() {
		return oldTcType;
	}
	public void setOldTcType(String oldTcType) {
		this.oldTcType = oldTcType;
	}
	public String getNewTcType() {
		return newTcType;
	}
	public void setNewTcType(String newTcType) {
		this.newTcType = newTcType;
	}
	public boolean isRollback() {
		return rollback;
	}
	public void setRollback(boolean rollback) {
		this.rollback = rollback;
	}
	public String getAccessid() {
		return accessid;
	}
	public void setAccessid(String accessid) {
		this.accessid = accessid;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
}
