package com.ancun.boss.business.pojo.taocanInfo;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossPagePojo;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * 套餐查询入类
 *
 * @Created on 2015年04月05日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class TaocanQueryInput extends BossPagePojo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 套餐名称
	 */
	private String taocanname;

	/**
	 * 套餐资费
	 */
	private String taocanprice;

	/**
	 * 省份
	 */
	private String rpcode;

	/**
	 * 业务
	 */
	private String bizno;

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

	public String getRpcode() {
		return rpcode;
	}

	public void setRpcode(String rpcode) {
		this.rpcode = rpcode;
	}

	public String getBizno() {
		return bizno;
	}

	public void setBizno(String bizno) {
		this.bizno = bizno;
	}
}
