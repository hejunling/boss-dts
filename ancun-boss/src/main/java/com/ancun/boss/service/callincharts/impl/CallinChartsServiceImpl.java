package com.ancun.boss.service.callincharts.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.persistence.biz.mapper.BizCallinChartsMapper;
import com.ancun.boss.pojo.callincharts.CallinChartsInput;
import com.ancun.boss.pojo.callincharts.CallinChartsListOutput;
import com.ancun.boss.service.callincharts.ICallinChartsService;
import com.ancun.utils.StringUtil;
@Service
public class CallinChartsServiceImpl implements ICallinChartsService{
	
	@Resource
	private BizCallinChartsMapper bizCallinChartsMapper;

	@Override
	public CallinChartsListOutput queryCallinChartsList(CallinChartsInput input) {
		CallinChartsListOutput output = new CallinChartsListOutput();
		if(StringUtil.isNotEmpty(input.getCallinTimeEnd())){
			StringBuffer callinTimeEnd = new StringBuffer();
			callinTimeEnd.append(input.getCallinTimeEnd());
			callinTimeEnd.append("-1");
			input.setCallinTimeEnd(callinTimeEnd.toString());
		}
		
		//呼入登记统计
		output.setCallinChartsOutput(bizCallinChartsMapper.queryCallinChartsCountList(input));
		//投诉退订统计
//		output.setCancelChartsOutput(bizCallinChartsMapper.queryCancelChartsCountList(input));
		output.setPageinfo(input.getPage());
		return output;
	}
}
