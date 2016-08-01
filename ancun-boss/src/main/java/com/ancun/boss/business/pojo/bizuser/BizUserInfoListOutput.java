package com.ancun.boss.business.pojo.bizuser;

import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 用户列表
 *
 * @Created on 2015年4月5日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizUserInfoListOutput {

	private List<BizUserInfoOutput> bizuserInfolist;

	// 分页信息
	private Page pageinfo;

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<BizUserInfoOutput> getBizuserInfolist() {
		return bizuserInfolist;
	}

	public void setBizuserInfolist(List<BizUserInfoOutput> bizuserInfolist) {
		this.bizuserInfolist = bizuserInfolist;
	}
}
