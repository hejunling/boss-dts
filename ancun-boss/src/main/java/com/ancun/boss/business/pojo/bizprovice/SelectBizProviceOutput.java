package com.ancun.boss.business.pojo.bizprovice;


import com.ancun.boss.business.persistence.module.BizProvice;

import java.util.List;

/**
 * 条件查询业务省份关系表
 *
 * @Created on 2016-4-01
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class SelectBizProviceOutput {
	private List<BizProvice> bizProvicelist;

	public List<BizProvice> getBizProvicelist() {
		return bizProvicelist;
	}

	public void setBizProvicelist(List<BizProvice> bizProvicelist) {
		this.bizProvicelist = bizProvicelist;
	}

}
