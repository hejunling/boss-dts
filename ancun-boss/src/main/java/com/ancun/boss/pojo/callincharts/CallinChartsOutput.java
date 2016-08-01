package com.ancun.boss.pojo.callincharts;

import com.ancun.utils.StringUtil;


/**
 * 呼入来电输出请求封装类
 * @author cys1993
 *
 */
public class CallinChartsOutput {
	
	//电话数量统计
	private String count;
	
    //业务（项目名称）
    private String businessName;
    
    //呼入登记和投诉退订标识
    private String mark;
    
    //时间间隔统计
    private String space;
    
    //日均接听量
    private String callinAvg;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}
	
	public String getCallinAvg() {
		//日均接听量
		if (StringUtil.isNotEmpty(this.count)&& StringUtil.isNotEmpty(this.space)) {
            return StringUtil.getAvg(this.count, space);
        }
        return "-";
	}

	public void setCallinAvg(String callinAvg) {
		this.callinAvg = callinAvg;
	}
	
	

}
