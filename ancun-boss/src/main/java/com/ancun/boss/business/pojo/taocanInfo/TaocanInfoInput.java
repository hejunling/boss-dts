package com.ancun.boss.business.pojo.taocanInfo;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossPagePojo;
import com.ancun.validate.annotation.RangeLength;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *更新、删除、添加操作返回
 * @Created on 2016年04月06日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "content")
public class TaocanInfoInput extends BossPagePojo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 套餐ID
	 */
	private Long tcid;

	/**
	 * 老的业务编号
	 */
	private String oldbizno;

	/**
	 * 套餐名称
	 */
	@NotBlank(message = "" + MessageConstant.MANAGER_TAOCAN_NAME_IS_NULL)
	private String taocanname;

	/**
	 * 套餐价格
	 */
	@Min(value=0, message=""+MessageConstant.TAOCAN_PRICE_IS_ERROR)
	private String taocanprice;

	/**
	 * 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
	 */
	@NotBlank(message = "" + MessageConstant.MANAGER_TAOCAN_TYPE_IS_NULL)
	private String taocantype;

	/**
	 * 存储空间
	 */
	@Min(value=0, message=""+MessageConstant.MANAGER_TAOCAN_SPACE_IS_ERROE)
	private String storespace;

	/**
	 * 套餐说明
	 */
	@RangeLength(label = "套餐说明", min = 1, max = 200,message=""+MessageConstant.INFO_LENGTH_ILLEGAL)
	private String taocanremark;

	/**
	 * 业务编号
	 */
	@NotBlank(message = "" + MessageConstant.BIZNO_NOTNULL)
	private String bizno;

	/**
	 * 套餐时长
	 */
	@Min(value=0, message=""+MessageConstant.MANAGER_TAOCAN_DURATION_IS_ERROE)
	private String tcduration;

	/**
	 * 套餐标记默认为NULL(1:默认套餐)
	 */
	private String tcflag;

	public Long getTcid() {
		return tcid;
	}

	public void setTcid(Long tcid) {
		this.tcid = tcid;
	}

	public String getTaocanname() {
		return taocanname;
	}

	public void setTaocanname(String taocanname) {
		this.taocanname = taocanname;
	}

	public String getTaocanprice() {
		return taocanprice;
	}

	public void setTaocanprice(String taocanprice) {
		this.taocanprice = taocanprice;
	}

	public String getTaocantype() {
		return taocantype;
	}

	public void setTaocantype(String taocantype) {
		this.taocantype = taocantype;
	}

	public String getStorespace() {
		return storespace;
	}

	public void setStorespace(String storespace) {
		this.storespace = storespace;
	}

	public String getTaocanremark() {
		return taocanremark;
	}

	public void setTaocanremark(String taocanremark) {
		this.taocanremark = taocanremark;
	}

	public String getBizno() {
		return bizno;
	}

	public void setBizno(String bizno) {
		this.bizno = bizno;
	}

	public String getTcduration() {
		return tcduration;
	}

	public void setTcduration(String tcduration) {
		this.tcduration = tcduration;
	}

	public String getTcflag() {
		return tcflag;
	}

	public void setTcflag(String tcflag) {
		this.tcflag = tcflag;
	}

	public String getOldbizno() {
		return oldbizno;
	}

	public void setOldbizno(String oldbizno) {
		this.oldbizno = oldbizno;
	}
}
