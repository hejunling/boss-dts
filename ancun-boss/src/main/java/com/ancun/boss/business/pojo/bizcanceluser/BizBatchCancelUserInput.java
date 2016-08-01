package com.ancun.boss.business.pojo.bizcanceluser;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.validate.annotation.In;
import com.ancun.validate.annotation.RangeLength;

import java.util.List;

/**
 * 业务用户批量退订
 *
 * @Created on 2016年3月10日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizBatchCancelUserInput extends BossBasePojo {

	/**
	 * 省份编码
	 */
	@RangeLength(label="省份编码", max=6, min=6, required=true, message=""+MessageConstant.RPCODE_FORMAT_ERROR)
	private String provinceCode;
	
	private List<CancelUserInfo> canceluserlist;
	
	/**
	 * 业务no
	 */
	@RangeLength(label="业务编码", max=20, min=1, required=true, message=""+MessageConstant.INFO_LENGTH_ILLEGAL)
	private String bizno;
	
	/**
	 * 电信/联通
	 */
	@In(value = Constant.TYPE_OF_TEL_UNI, required = true, message = "" +MessageConstant.TYPE_CODE_NOTNULL)
	private String type;

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public List<CancelUserInfo> getCanceluserlist() {
		return canceluserlist;
	}

	public void setCanceluserlist(List<CancelUserInfo> canceluserlist) {
		this.canceluserlist = canceluserlist;
	}

	public String getBizno() {
		return bizno;
	}

	public void setBizno(String bizno) {
		this.bizno = bizno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
