package com.ancun.boss.business.pojo.bizprovice;

import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 条件查询业务省份关系表
 *
 * @Created on 2016-4-1
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class SelectBizProviceInput extends BossPagePojo {

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

	private String bizNo;//业务编号
	
	private String prcode;//省份编号

	public String getPrcode() {
		return prcode;
	}

	public void setPrcode(String prcode) {
		this.prcode = prcode;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
}
