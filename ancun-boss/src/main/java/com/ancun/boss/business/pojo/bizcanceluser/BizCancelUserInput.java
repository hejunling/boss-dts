package com.ancun.boss.business.pojo.bizcanceluser;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.validate.annotation.In;
import com.ancun.validate.annotation.RangeLength;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 单个退订
 *
 * @Created on 2016年3月10日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value=XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class BizCancelUserInput extends BossBasePojo{
	/**
	 * 省份编码
	 */
	@RangeLength(label="省份编码", max=6, min=6, required = true, message=""+MessageConstant.RPCODE_FORMAT_ERROR)
	private String provinceCode;
	
	/**
	 * 业务用户no
	 */
	@RangeLength(label="业务用户编码", max=12, min=6, required = true, message=""+MessageConstant.PHONE_FORMAT_ILLEGAL)
	private String bizuserno;
	
	/**
	 * 业务no
	 */
	@RangeLength(label="业务编码", max=20, min=1, required = true, message=""+MessageConstant.INFO_LENGTH_ILLEGAL)
	private String bizno;
	
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
	@NotNull(message=""+MessageConstant.ISREFUNDS_IS_NULL)
	private boolean isrefunds;
	
	/**
	 * 退费金额
	 */
	private double refundsmoney;
	
	/**
	 * 描述信息
	 */
	@RangeLength(label="描述信息",max=1024,required=false,message=""+MessageConstant.INFO_LENGTH_ILLEGAL)
	private String description;
	
	/**
	 * 电信/联通
	 */
	/** update by zkai 运营商类型要不为空 */
//	@In(value = Constant.TYPE_OF_TEL_UNI, required = true, message = "" +MessageConstant.TYPE_CODE_NOTNULL)
	@NotBlank(message=""+MessageConstant.TYPE_CODE_NOTNULL)
	private String type;

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getBizuserno() {
		return bizuserno;
	}

	public void setBizuserno(String bizuserno) {
		this.bizuserno = bizuserno;
	}

	public String getBizno() {
		return bizno;
	}

	public void setBizno(String bizno) {
		this.bizno = bizno;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
