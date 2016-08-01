package com.ancun.boss.pojo.marketInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.validate.annotation.PhoneNum;
import com.ancun.validate.annotation.RangeLength;

/**
 * 营销质检对象
 *
 * @Created on 2015-9-21
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class MarketCheckInput extends BossBasePojo{
	
	@RangeLength(label = "业务", min = 1, max = 32, message = "" + MessageConstant.BUSINESS_LENGTH_ILLEGAL)
	private String business;  //业务
	
	@RangeLength(label = "推广方式", min = 1, max = 16, message = "" + MessageConstant.MARKETWAY_LENGTH_ILLEGAL)
	private String marketWay; //推广方式
	
	private String operator; //运营商
	
	@RangeLength(label = "营销工号", min = 1, max = 12, message = "" + MessageConstant.MARKETNO_LENGTH_ILLEGAL)
	private String marketNo; //营销工号
	
	@RangeLength(label = "质检工号", min = 1, max = 12, message = "" + MessageConstant.CHECKNO_LENGTH_ILLEGAL)
	private String checkNo; //质检工号
	
	@RangeLength(label = "质检结果", min = 1, max = 8, message = "" + MessageConstant.CHECKRESULT_LENGTH_ILLEGAL)
	private String checkResult; //质检结果
	
	@RangeLength(label = "外呼团队", min = 1, max = 32, message = "" + MessageConstant.CALLEDTEAM_LENGTH_ILLEGAL)
	private String calledTeam; //外呼团队
	
	@RangeLength(label = "电话号码", min = 1, max = 16, message = "" + MessageConstant.PHONENO_LENGTH_ILLEGAL)
	@PhoneNum(label = "电话号码", message = "" + MessageConstant.PHONE_FORMAT_ILLEGAL)
	private String phoneNo; //电话号码
	
	@RangeLength(label = "营销时间", min = 1, max = 32, message = "" + MessageConstant.MARKETTIME_LENGTH_ILLEGAL)
	private String marketTime; //营销时间
	
	@RangeLength(label = "备注", min = 0, max = 256, message = "" + MessageConstant.REMARK_LENGTH_ILLEGAL)
	private String remark; // 备注
	
	@RangeLength(label = "质检人", min = 0, max = 12, message = "" + MessageConstant.SAMPLING_LENGTH_ILLEGAL)
	private String sampling; // 质检人
	
	private String modifyflag; // (1:登陆 2:修改)
	
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
	public String getModifyflag() {
		return modifyflag;
	}
	public void setModifyflag(String modifyflag) {
		this.modifyflag = modifyflag;
	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSampling() {
		return sampling;
	}
	public void setSampling(String sampling) {
		this.sampling = sampling;
	}
	
}
