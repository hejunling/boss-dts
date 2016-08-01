package com.ancun.boss.business.pojo.bizcanceluser;

/**
 * 操作类型：
 * 0：订购
 * 1：退订
 * @author tom.tang
 */
public enum ActionTypeEnum {
	Open(0), Unsubscribe(1);
	
	private ActionTypeEnum(int value){
		this.value = value;
	}
	
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
