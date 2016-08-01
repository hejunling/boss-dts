package com.ancun.boss.business.pojo.bizopenuser;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ancun.boss.business.persistence.module.BizUserInfo;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.validate.annotation.RangeLength;

public class OpenBizUserBatchInput extends BossBasePojo{
	
	/**
	 * 省份编码
	 */
	@RangeLength(label="省份编码",max=6,min=1,message=""+MessageConstant.RPCODE_FORMAT_ERROR)
	private String rpcode;

	/**
	 * 注册来源
	 */
	@NotBlank(message=""+MessageConstant.SIGNUPSOURCE_MUST_BE_NOT_EMPTY)
	private String isignupsource;

	/**
	 * 套餐资费
	 */
	@NotNull(message = "" + MessageConstant.TCCODE_NOTNULL)
	private Long taocanid;
	
	/**
	 * 运营商类型
	 */
	@NotBlank(message=""+MessageConstant.TYPE_CODE_NOTNULL)
	private String type_code;
	
	/**
	 * 业务编号
	 */
	@NotBlank(message=""+MessageConstant.BIZNO_NOTNULL)
	private String bizno;
	
	/**
	 * 固话类型
	 */
	@NotBlank(message=""+MessageConstant.PHONETYPE_NOTNULL)
	private String phoneType;

	/**
	 * 批量List
	 */
	private List<OpenBizUserInfo> userlist;

	public String getRpcode() {
		return rpcode;
	}

	public void setRpcode(String rpcode) {
		this.rpcode = rpcode;
	}

	public String getIsignupsource() {
		return isignupsource;
	}

	public void setIsignupsource(String isignupsource) {
		this.isignupsource = isignupsource;
	}

	public Long getTaocanid() {
		return taocanid;
	}

	public void setTaocanid(Long taocanid) {
		this.taocanid = taocanid;
	}
	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public List<OpenBizUserInfo> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<OpenBizUserInfo> userlist) {
		this.userlist = userlist;
	}

	public String getBizno() {
		return bizno;
	}

	public void setBizno(String bizno) {
		this.bizno = bizno;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
}
