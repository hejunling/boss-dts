package com.ancun.boss.business.pojo.bizcanceluser;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.validate.annotation.In;
import com.ancun.validate.annotation.RangeLength;

/**
 * 退费业务用户信息
 *
 * @Created on 2016年3月10日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CancelUserInfo {

	/**
	 * 业务用户no
	 */
	@RangeLength(label = "业务用户编码", max = 12, min = 6, required = false, message = ""
	        + MessageConstant.PHONE_FORMAT_ILLEGAL)
	private String bizuserno;
	
	/**
	 * 企业编号
	 */
	private String entno;
	
	/**
	 * 用户类型(1:个人;2:企业)
	 */
	@NotBlank(message = ""+MessageConstant.BIZ_USER_TYPE_IS_NULL)
	@In(value = Constant.USER_TYPE_PER_ENT)
	private String usertype;
	
	/**
	 * 账号类型(1:主账号;2:子账号)
	 */
	private String accounttype;

	/**
	 * 是否退费
	 */
	@NotNull(message = "" + MessageConstant.ISREFUNDS_IS_NULL)
	private boolean isrefunds;

	/**
	 * 退费金额
	 */
	private double refundsmoney;

	/**
	 * 描述信息
	 */
	private String description;
	
	/**
	 * 退订日期
	 */
	private Date canceldate;


	public String getBizuserno() {
		return bizuserno;
	}

	public void setBizuserno(String bizuserno) {
		this.bizuserno = bizuserno;
	}

	public String getEntno() {
		return entno;
	}

	public void setEntno(String entno) {
		this.entno = entno;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public boolean getIsrefunds() {
		return isrefunds;
	}

	public void setIsrefunds(boolean isrefunds) {
		this.isrefunds = isrefunds;
	}

	public double getRefundsmoney() {
		return refundsmoney;
	}

	public void setRefundsmoney(double refundsmoney) {
		this.refundsmoney = refundsmoney;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCanceldate() {
		return canceldate;
	}

	public void setCanceldate(Date canceldate) {
		this.canceldate = canceldate;
	}


}
