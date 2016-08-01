package com.ancun.boss.business.utils.fptcsrvc;

/**
 * 录音提示枚举
 * 0：不提示
 * 1：有录音提示
 * @author tom.tang
 */
public enum RecordTipEnum {
	NO("0"), YES("1");
	
	private RecordTipEnum(String value){
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
