package com.ancun.boss.pojo.callInfo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.core.page.Page;

/**
 * 呼入登记返回封装类
 * 
 * @author cys
 *
 */
@XmlAccessorType(value=XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class CallInRecordOutput{
	
	/**
	 * 呼入登记信息列表
	 */
	private List<CallInRecordBusinessOutput> callinrecordlist;
	
	private Page pageinfo;

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<CallInRecordBusinessOutput> getCallinrecordlist() {
		return callinrecordlist;
	}

	public void setCallinrecordlist(List<CallInRecordBusinessOutput> callinrecordlist) {
		this.callinrecordlist = callinrecordlist;
	}	

}
