package com.ancun.boss.business.pojo.bizopenuser;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.validate.annotation.In;
import com.ancun.validate.annotation.PhoneNum;
import com.ancun.validate.annotation.RangeLength;

/**
 * @author chenb
 *
 */
public class OpenBizUserInput extends BossBasePojo{
	/**
	 * 业务账号
	 */
	@RangeLength(label = "业务用户编码", max = 12, min = 6, required = false, message = ""
	        + MessageConstant.BIZUSERNO_FORMAT_ERROR)
	private String bizuserno;

	/**
	 * 省份编码
	 */
	@RangeLength(label="省份编码",max=6,min=1,message=""+MessageConstant.RPCODE_FORMAT_ERROR)
	private String rpcode;

	/**
	 * 套餐编号
	 */
	@NotNull(message = "" + MessageConstant.TCCODE_NOTNULL)
	private Long taocanid;

	/**
	 * 开通时间
	 */
	@NotNull(message=""+MessageConstant.INFO_LENGTH_ILLEGAL)
	private Date opendatetime;

	/**
	 * 联系电话
	 */
	@PhoneNum(label = "电话号码格式", message = "" + MessageConstant.CONTACT_PHONE_FORMAT_ERROR)
	private String phone;

	/**
	 * 注册来源
	 */
	@NotBlank(message=""+MessageConstant.SIGNUPSOURCE_MUST_BE_NOT_EMPTY)
	private String isignupsource;

	/**
	 * 备注
	 */
	@RangeLength(label="备注",max=200,required=false,message=""+MessageConstant.REMARK_FORMAT_ERROR)
	private String remark;

	/**
	 * 外呼来源
	 */
	@RangeLength(label="外呼来源",max=10,required=false,message=""+MessageConstant.CALLSOURCE_FORMAT_ERROR)
	private String callsource;
	
	/**
	 * 运营商类型
	 */
	@NotBlank(message=""+MessageConstant.TYPE_CODE_NOTNULL)
	private String type_code;
	
	/**
	 * 主账号
	 */
	private String mainAccount;
	
	/**
	 * 用户类型
	 */
	@In(value = Constant.USER_TYPE_PER_ENT)
	private String userType;
	
	/**
	 * 主叫提示音标记
	 */
	private String callervoice;

	/**
	 * 被叫提示音标记
	 */
	private String calledvoice;
	
	/**
	 * 业务编号
	 */
	@NotBlank(message=""+MessageConstant.BIZNO_NOTNULL)
	private String bizNo;
	
	/***
	 * 单位传真
	 */
	private String fax;
	
	//----------------------个人信息---------------begin
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 身份证号
	 */
	private String identify;
	//----------------------个人信息---------------end
	
	//----------------------企业信息---------------begin
	/**
	 * 工商注册号
	 */
	private String entRegNo;
	
	/***
	 * 单位名称
	 */
	private String entName;
	
	/***
	 * 单位地址
	 */
	private String entAddress;
	//----------------------企业信息---------------end
	
	//----------------------公共信息---------------begin
	/**
	 * 是否全包
	 */
	private String fullPackage;
	
	/**
	 * 唯一号码
	 */
	private String uniqueNo;
	//----------------------公共信息---------------end
	
	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getEntAddress() {
		return entAddress;
	}

	public void setEntAddress(String entAddress) {
		this.entAddress = entAddress;
	}

	public String getCallervoice() {
		return callervoice;
	}

	public void setCallervoice(String callervoice) {
		this.callervoice = callervoice;
	}

	public String getCalledvoice() {
		return calledvoice;
	}

	public void setCalledvoice(String calledvoice) {
		this.calledvoice = calledvoice;
	}

	public String getMainAccount() {
		return mainAccount;
	}

	public void setMainAccount(String mainAccount) {
		this.mainAccount = mainAccount;
	}

	public String getBizuserno() {
		return bizuserno;
	}

	public void setBizuserno(String bizuserno) {
		this.bizuserno = bizuserno;
	}

	public String getRpcode() {
		return rpcode;
	}

	public void setRpcode(String rpcode) {
		this.rpcode = rpcode;
	}

	public Long getTaocanid() {
		return taocanid;
	}

	public void setTaocanid(Long taocanid) {
		this.taocanid = taocanid;
	}

	public Date getOpendatetime() {
		return opendatetime;
	}

	public void setOpendatetime(Date opendatetime) {
		this.opendatetime = opendatetime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIsignupsource() {
		return isignupsource;
	}

	public void setIsignupsource(String isignupsource) {
		this.isignupsource = isignupsource;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCallsource() {
		return callsource;
	}

	public void setCallsource(String callsource) {
		this.callsource = callsource;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getFullPackage() {
		return fullPackage;
	}

	public void setFullPackage(String fullPackage) {
		this.fullPackage = fullPackage;
	}

	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}
	
	public boolean isPersonalEmpty() {
		if(StringUtils.isNotBlank(sex)
				|| StringUtils.isNotBlank(identify)) {
			return false;
		}
		return true;
	}
	
	public boolean isEntEmpty() {
		if(StringUtils.isNotBlank(this.entRegNo)
				|| StringUtils.isNotBlank(entName)
				|| StringUtils.isNotBlank(entAddress)) {
			return false;
		}
			return true;
	}
	
	public OpenBizUserInput setInfo() {
		if(isPersonalEmpty() || isEntEmpty()) {
			setFullPackage(BizRequestConstant.FULL_PACKAGE_TRUE);
		}else {
			setFullPackage(BizRequestConstant.FULL_PACKAGE_FALSE);
		}
		setUniqueNo(UUID.randomUUID().toString());
		return this;
	}

	public String getEntRegNo() {
		return entRegNo;
	}

	public void setEntRegNo(String entRegNo) {
		this.entRegNo = entRegNo;
	}
	
}
