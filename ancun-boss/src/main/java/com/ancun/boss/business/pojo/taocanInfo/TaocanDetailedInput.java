package com.ancun.boss.business.pojo.taocanInfo;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossPagePojo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 套餐详细input类
 *
 * @Created on 2016年4月6日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "content")
public class TaocanDetailedInput extends BossPagePojo {
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
	/**
	 * 套餐ID
	 */
    @NotNull(message=""+ MessageConstant.TCCODE_NOTNULL)
	private Long tcid;

	/**
	 * 套餐ID
	 */
	@NotBlank(message=""+ MessageConstant.BIZNO_NOTNULL)
	private String bizno;

	public Long getTcid() {
		return tcid;
	}

	public void setTcid(Long tcid) {
		this.tcid = tcid;
	}

	public String getBizno() {
		return bizno;
	}

	public void setBizno(String bizno) {
		this.bizno = bizno;
	}
}
