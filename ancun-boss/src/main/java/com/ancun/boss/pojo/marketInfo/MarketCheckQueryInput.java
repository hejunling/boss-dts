package com.ancun.boss.pojo.marketInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 营销质检查询的请求对象
 *
 * @Created on 2015-9-21
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class MarketCheckQueryInput extends BossPagePojo{

	private String business;  // 业务
	private String marketWay; // 推广方式
	private String operator; // 运营商
	private String marketNo; // 营销工号
	private String checkNo; // 质检工号
	private String checkResult; // 质检结果
	private Integer totalpage; // 总页数
	private Integer totalcount; // 记录总数
	private String calledTeam; // 外呼团队
	private String phoneNo; // 电话号码
	private String marketTime;
	
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getMarketWay() {
		return marketWay;
	}
	public void setMarketWay(String marketWay) {
		this.marketWay = marketWay;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getMarketNo() {
		return marketNo;
	}
	public void setMarketNo(String marketNo) {
		this.marketNo = marketNo;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public Integer getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(Integer totalpage) {
		this.totalpage = totalpage;
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public String getCalledTeam() {
		return calledTeam;
	}
	public void setCalledTeam(String calledTeam) {
		this.calledTeam = calledTeam;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getMarketTime() {
		return marketTime;
	}
	public void setMarketTime(String marketTime) {
		this.marketTime = marketTime;
	}
	
	
}
