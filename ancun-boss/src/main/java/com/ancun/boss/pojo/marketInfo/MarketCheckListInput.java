package com.ancun.boss.pojo.marketInfo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.persistence.model.MarketCheck;
import com.ancun.boss.pojo.BossBasePojo;

/**
 * 营销质检对象
 *
 * @Created on 2015-9-21
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class MarketCheckListInput extends BossBasePojo{
	
	private List<MarketCheck> marketCheckList;

	public List<MarketCheck> getMarketCheckList() {
		return marketCheckList;
	}

	public void setMarketCheckList(List<MarketCheck> marketCheckList) {
		this.marketCheckList = marketCheckList;
	}

}
