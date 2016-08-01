package com.ancun.boss.business.pojo.allprovince;

import java.util.Date;

public class ProvinceServiceInput {
	
	// 呼叫类型
	private String type;
	
	// 用户号码
	private String userTel;
	
	// 业务账号
	private String userno;
	
	// 用户类型
	private String userType;
	
	// 套餐ID
	private String tcID;
	
	// 企业编号
	private String entUser;
	
	// 套餐变更类型
	private String changeTcType;
	
	// 旧套餐
	private String oldTc;
	
	// 新套餐
	private String newTc;
	
	// 回滚
	private boolean rollback;
	
	// 开通时间
	private Date openTime;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTcID() {
		return tcID;
	}

	public void setTcID(String tcID) {
		this.tcID = tcID;
	}

	public String getEntUser() {
		return entUser;
	}

	public void setEntUser(String entUser) {
		this.entUser = entUser;
	}

	public String getChangeTcType() {
		return changeTcType;
	}

	public void setChangeTcType(String changeTcType) {
		this.changeTcType = changeTcType;
	}

	public String getOldTc() {
		return oldTc;
	}

	public void setOldTc(String oldTc) {
		this.oldTc = oldTc;
	}

	public String getNewTc() {
		return newTc;
	}

	public void setNewTc(String newTc) {
		this.newTc = newTc;
	}

	public boolean isRollback() {
		return rollback;
	}

	public void setRollback(boolean rollback) {
		this.rollback = rollback;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	
}
