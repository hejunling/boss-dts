package com.ancun.boss.business.pojo.taocanInfo;

import com.ancun.core.page.Page;
import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 套餐查询输出类
 *
 * @Created on 2015年4月5日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class TaocanListOutput {
	private List<TaocanBaseInfo> taocanlist = Lists.newArrayList();


	// 分页信息
	private Page pageinfo = new Page();

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<TaocanBaseInfo> getTaocanlist() {
		return taocanlist;
	}

	public void setTaocanlist(List<TaocanBaseInfo> taocanlist) {
		this.taocanlist = taocanlist;
	}
}
