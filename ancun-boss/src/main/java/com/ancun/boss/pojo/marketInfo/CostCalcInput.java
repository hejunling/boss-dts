package com.ancun.boss.pojo.marketInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 营销成本新增或修改的请求对象
 *
 * @Created on 2015-10-12
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class CostCalcInput extends BossPagePojo{
	
	/*
	 * >编号(新增时无)</id>
	 * */
	private Long id;
	
	/*
	 * 结款方式（数据字典）
	 * */
	private String type;
	
	/*
	 * 业务（项目名称）
	 * */
	private String business;
	
	/*
	 * 外呼团队
	 * */
	private String calledteam;
	
	/*
	 * 日期（格式为yyyy-MM）
	 * */
	private String month;
	
	/*
	 * 坐席工资
	 * */
	private String emppay;
	
	/*
	 * 话费
	 * */
	private String bill;
	
	/*
	 * 管理成本分摊
	 * */
	private String managepay;
	
	/*
	 * 管理成本公式
	 * */
	private String manageexp;
	
	/*
	 * 保险
	 * */
	private String insurance;
	
	/*
	 * 水电费
	 * */
	private String wepay;
	
	/*
	 * 水电费公式
	 * */
	private String weexp;
	
	
	/*
	 * 网费
	 * */
	private String netpay;
	
	/*
	 * 网费公式
	 * */
	private String netexp;
	
	/*
	 * 硬件设备费
	 * */
	private String devicepay;
	
	/*
	 * 房租
	 * */
	private String rent;
	
	/*
	 * 房租公式
	 * */
	private String rentexp;
	
	/*
	 * 总成本
	 * */
	private String totalcost;
	
	/*
	 * 成功件数
	 * */
	private String successnumber;
	
	/*
	 * 单件成本(共成本/成功件数)
	 * */
	private String avgcost;
	
	/*
	 * 运营商
	 * */
	private String operator;
	
	/*
	 * 包席价格
	 * */
	private String sitprice;
	
	/*
	 * 坐席数
	 * */
	private String sitnumber;
	
	/*
	 * 成功价单价</id>
	 * */
	private String sucperprice;	
	
	/*
	 * 成功量
	 * */
	private String sucnumber;
	
	/*
	 * xsl表序号
	 * */
	private String xslindex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getCalledteam() {
		return calledteam;
	}

	public void setCalledteam(String calledteam) {
		this.calledteam = calledteam;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getEmppay() {
		return emppay;
	}

	public void setEmppay(String emppay) {
		this.emppay = emppay;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public String getManagepay() {
		return managepay;
	}

	public void setManagepay(String managepay) {
		this.managepay = managepay;
	}

	public String getManageexp() {
		return manageexp;
	}

	public void setManageexp(String manageexp) {
		this.manageexp = manageexp;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getWepay() {
		return wepay;
	}

	public void setWepay(String wepay) {
		this.wepay = wepay;
	}

	public String getWeexp() {
		return weexp;
	}

	public void setWeexp(String weexp) {
		this.weexp = weexp;
	}

	public String getNetpay() {
		return netpay;
	}

	public void setNetpay(String netpay) {
		this.netpay = netpay;
	}

	public String getNetexp() {
		return netexp;
	}

	public void setNetexp(String netexp) {
		this.netexp = netexp;
	}

	public String getDevicepay() {
		return devicepay;
	}

	public void setDevicepay(String devicepay) {
		this.devicepay = devicepay;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getRentexp() {
		return rentexp;
	}

	public void setRentexp(String rentexp) {
		this.rentexp = rentexp;
	}

	public String getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(String totalcost) {
		this.totalcost = totalcost;
	}

	public String getSuccessnumber() {
		return successnumber;
	}

	public void setSuccessnumber(String successnumber) {
		this.successnumber = successnumber;
	}

	public String getAvgcost() {
		return avgcost;
	}

	public void setAvgcost(String avgcost) {
		this.avgcost = avgcost;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSitprice() {
		return sitprice;
	}

	public void setSitprice(String sitprice) {
		this.sitprice = sitprice;
	}

	public String getSitnumber() {
		return sitnumber;
	}

	public void setSitnumber(String sitnumber) {
		this.sitnumber = sitnumber;
	}

	public String getSucperprice() {
		return sucperprice;
	}

	public void setSucperprice(String sucperprice) {
		this.sucperprice = sucperprice;
	}

	public String getSucnumber() {
		return sucnumber;
	}

	public void setSucnumber(String sucnumber) {
		this.sucnumber = sucnumber;
	}

	public String getXslindex() {
		return xslindex;
	}

	public void setXslindex(String xslindex) {
		this.xslindex = xslindex;
	}


}
