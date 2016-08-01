package com.ancun.boss.service.cancelRequireCharts.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.persistence.biz.mapper.BizCancelRequireChartsMapper;
import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsInput;
import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsListOutput;
import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsOutput;
import com.ancun.boss.service.cancelRequireCharts.ICancelRequireChartsService;
import com.ancun.utils.StringUtil;

/**
 * 退费退订统计报表实现类
 *
 * @Created on 2015年11月10日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class CancelRequireChartsImpl implements ICancelRequireChartsService{
	
	@Resource
	private BizCancelRequireChartsMapper bizCancelRequireChartsMapper;

	@Override
	public CancelRequireChartsListOutput queryCancelRequireChartsList(CancelRequireChartsInput input) {
		
		List<CancelRequireChartsOutput> list;
		if(StringUtil.isNotEmpty(input.getCancelTimeEnd())){
			StringBuffer cancelTimeEnd = new StringBuffer();
			cancelTimeEnd.append(input.getCancelTimeEnd());
			cancelTimeEnd.append("-1");
			input.setCancelTimeEnd(cancelTimeEnd.toString());
		}
		
		if(StringUtil.isNotEmpty(input.getCancelReqire())&&input.getCancelReqire().equals(BizRequestConstant.CANCEL_REQIRE_TF)){
			list = bizCancelRequireChartsMapper.queryCancelRequireChartsTDTFList(input);
		}else{			
			list = bizCancelRequireChartsMapper.queryCancelRequireChartsTDList(input);
		}
	
		CancelRequireChartsListOutput output = new CancelRequireChartsListOutput();
		output.setCancelRequireChartsOutput(list);
		return output;
	}

}
