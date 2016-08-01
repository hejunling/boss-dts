package com.ancun.boss.business.pojo.bizuserinfoimport;

import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * 用户导入失败列表输入类
 *
 * @Created on 2016-4-19
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class SelectBizUserInfoImportInput extends BossPagePojo {

    private static final long serialVersionUID = 1L;

	private String userNum;//业务账号
	
	private String userType;//用户类型(1:个人;2:企业)
	
	private String accountType;//账号类型(1:主账号;2:子账号)

	private String insource; // 注册来源

	private Date beginTime; // 导入开始时间

	private Date endTime; // 导入结束时间

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getInsource() {
		return insource;
	}

	public void setInsource(String insource) {
		this.insource = insource;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
