package com.ancun.boss.business.pojo.dataDicInfo;


import com.ancun.boss.persistence.model.DataDic;
import com.ancun.core.page.Page;

import java.util.List;

/**
 * 返回数据字典实体pojo
 *
 * @Created on 2016-4-01
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class GetDictionaryOutput {
	private List<DataDic> datalist;

	/**
	 * 分页信息
	 */
	private Page pageinfo;

	public List<DataDic> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<DataDic> datalist) {
		this.datalist = datalist;
	}

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}
}
