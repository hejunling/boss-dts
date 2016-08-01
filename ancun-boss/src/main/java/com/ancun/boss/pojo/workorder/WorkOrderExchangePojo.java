package com.ancun.boss.pojo.workorder;

import com.ancun.boss.persistence.model.UserWorkOrderExchangeDetailInfo;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenb
 * @version 1.0
 * @Created on 2015/10/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XStreamAlias("workorderexchangeinfo")
public class WorkOrderExchangePojo extends UserWorkOrderExchangeDetailInfo{
	
	// 组织名
	private String orgnoName;
	
	// 用户名
	private String userName;

	public String getOrgnoName() {
		return orgnoName;
	}

	public void setOrgnoName(String orgnoName) {
		this.orgnoName = orgnoName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
