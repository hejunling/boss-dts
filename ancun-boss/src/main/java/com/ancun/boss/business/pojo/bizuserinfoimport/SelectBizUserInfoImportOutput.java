package com.ancun.boss.business.pojo.bizuserinfoimport;


import com.ancun.core.page.Page;

import java.util.List;

/**
 * 用户导入失败列表输出类
 *
 * @Created on 2016-4-01
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class SelectBizUserInfoImportOutput {
	private List<BizUserInfoImportInfo> bizUserInfoImportInfoList;

	/**
	 * 分页信息
	 */
	private Page pageinfo;

	public List<BizUserInfoImportInfo> getBizUserInfoImportInfoList() {
		return bizUserInfoImportInfoList;
	}

	public void setBizUserInfoImportInfoList(List<BizUserInfoImportInfo> bizUserInfoImportInfoList) {
		this.bizUserInfoImportInfoList = bizUserInfoImportInfoList;
	}

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}
}
