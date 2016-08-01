package com.ancun.boss.business.pojo.taocanInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 获取套餐信息
 *
 * @Created on 2016年4月6日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XStreamAlias("taocaninfo")
public class TaocanDetailedOutput {

	private TaocanBaseInfo taocaninfo;

	public TaocanBaseInfo getTaocaninfo() {
		return taocaninfo;
	}

	public void setTaocaninfo(TaocanBaseInfo taocaninfo) {
		this.taocaninfo = taocaninfo;
	}
}

