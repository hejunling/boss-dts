package com.ancun.boss.business.pojo.taocanInfo;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossPagePojo;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 无非空判断套餐请求类
 *
 * @Created on 2016年4月6日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "content")
public class TaocanInput extends BossPagePojo {
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

	/**
	 * 业务编号
	 */
	private String bizno;

	public String getBizno() {
		return bizno;
	}

	public void setBizno(String bizno) {
		this.bizno = bizno;
	}
}
