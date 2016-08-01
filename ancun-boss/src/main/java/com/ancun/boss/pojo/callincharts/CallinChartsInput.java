package com.ancun.boss.pojo.callincharts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 输入请求封装类
 * @author cys1993
 *
 */
@XmlAccessorType(value=XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class CallinChartsInput extends BossPagePojo{
	//业务
	private String business;
	
	//查询开始时间
	private String callinTimeStart;
	
	//查询结束时间
	private String callinTimeEnd;

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getCallinTimeStart() {
		return callinTimeStart;
	}

	public void setCallinTimeStart(String callinTimeStart) {
		this.callinTimeStart = callinTimeStart;
	}

	public String getCallinTimeEnd() {
		return callinTimeEnd;
	}

	public void setCallinTimeEnd(String callinTimeEnd) {
		this.callinTimeEnd = callinTimeEnd;
	}
	
	

}
