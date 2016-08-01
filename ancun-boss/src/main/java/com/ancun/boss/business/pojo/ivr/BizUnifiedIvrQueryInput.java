package com.ancun.boss.business.pojo.ivr;

import com.ancun.boss.pojo.BossBasePojo;

public class BizUnifiedIvrQueryInput extends BossBasePojo {
	private String type_code;
	private String ownerno;
	private String provinceCode;
	
	private String bizNo;

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getOwnerno() {
		return ownerno;
	}

	public void setOwnerno(String ownerno) {
		this.ownerno = ownerno;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	

}
