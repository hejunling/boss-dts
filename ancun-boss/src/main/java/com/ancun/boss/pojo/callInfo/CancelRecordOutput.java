package com.ancun.boss.pojo.callInfo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.ancun.boss.pojo.BossPagePojo;
import com.ancun.core.page.Page;

/**
 * 投诉退订返回封装类
 * @author cys1993
 *
 */
@XmlAccessorType(value=XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class CancelRecordOutput extends BossPagePojo{
	
	/**
	 * 投诉退订信息列表
	 */
	private List<CancelRecordBusinessOutput> cancelrecordlist;
	
	private Page pageinfo;
	

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<CancelRecordBusinessOutput> getCancelrecordlist() {
		return cancelrecordlist;
	}

	public void setCancelrecordlist(List<CancelRecordBusinessOutput> cancelrecordlist) {
		this.cancelrecordlist = cancelrecordlist;
	}
	

}
