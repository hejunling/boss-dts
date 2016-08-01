package com.ancun.boss.pojo.marketInfo;

import com.ancun.boss.persistence.model.MarketCheck;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenb
 * @version 1.0
 * @Created on 2015/10/12
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XStreamAlias("marketcheckinfo")
public class MarketCheckInfoPojo extends MarketCheck{
	private String checkResultName;
	private String businessName;
	private String calledTeamName;
	private String voiceID;
	
	public String getCheckResultName() {
		return checkResultName;
	}
	public void setCheckResultName(String checkResultName) {
		this.checkResultName = checkResultName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getCalledTeamName() {
		return calledTeamName;
	}
	public void setCalledTeamName(String calledTeamName) {
		this.calledTeamName = calledTeamName;
	}
	public String getVoiceID() {
		return voiceID;
	}
	public void setVoiceID(String voiceID) {
		this.voiceID = voiceID;
	}
}
