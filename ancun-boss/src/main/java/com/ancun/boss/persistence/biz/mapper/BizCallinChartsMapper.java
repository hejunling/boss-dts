package com.ancun.boss.persistence.biz.mapper;

import java.util.List;

import com.ancun.boss.pojo.callincharts.CallinChartsInput;
import com.ancun.boss.pojo.callincharts.CallinChartsOutput;
/**
 * 
 * @author cys1993
 *
 */
public interface BizCallinChartsMapper {
	
	//呼入电话统计查询
	List<CallinChartsOutput> queryCallinChartsCountList(CallinChartsInput input);
	
	//投诉退订电话统计查询
	List<CallinChartsOutput> queryCancelChartsCountList(CallinChartsInput input);

}
