package com.ancun.boss.pojo.cancelRequireCharts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 退费退订统计输入请求封装类
 *
 * @Created on 2015年11月10日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value=XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class CancelRequireChartsInput extends BossPagePojo{
	
	//业务
	private String business;
	
	//退订开始时间
    private String cancelTimeStart;
    
   // 退订结束时间
    private String cancelTimeEnd;
    
    //退订退费
    private String cancelReqire;

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getCancelTimeStart() {
		return cancelTimeStart;
	}

	public void setCancelTimeStart(String cancelTimeStart) {
		this.cancelTimeStart = cancelTimeStart;
	}

	public String getCancelTimeEnd() {
		return cancelTimeEnd;
	}

	public void setCancelTimeEnd(String cancelTimeEnd) {
		this.cancelTimeEnd = cancelTimeEnd;
	}

	public String getCancelReqire() {
		return cancelReqire;
	}

	public void setCancelReqire(String cancelReqire) {
		this.cancelReqire = cancelReqire;
	}
    
}
