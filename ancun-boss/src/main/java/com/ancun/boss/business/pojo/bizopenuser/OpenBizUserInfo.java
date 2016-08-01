package com.ancun.boss.business.pojo.bizopenuser;

import java.util.Date;

public class OpenBizUserInfo {
	
	/**
	 * 业务账号
	 */
	private String bizuserno;
	
	/**
	 * 开通时间
	 */
	private Date opendatetime;
	
	public String getBizuserno() {
		return bizuserno;
	}

	public void setBizuserno(String bizuserno) {
		this.bizuserno = bizuserno;
	}

	public Date getOpendatetime() {
		return opendatetime;
	}

	public void setOpendatetime(Date opendatetime) {
		this.opendatetime = opendatetime;
	}
}
