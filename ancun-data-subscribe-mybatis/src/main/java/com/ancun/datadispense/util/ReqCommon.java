package com.ancun.datadispense.util;

import com.ancun.utils.TimeUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(value = XmlAccessType.PROPERTY)
public class ReqCommon {
	private String action;
	private String reqtime;
	
	public ReqCommon(){
		this.reqtime = TimeUtils.getSysTimeLong();
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getReqtime() {
		return reqtime;
	}
	public void setReqtime(String reqtime) {
		this.reqtime = reqtime;
	}
	
}
