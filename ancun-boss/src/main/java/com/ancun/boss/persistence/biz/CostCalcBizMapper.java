package com.ancun.boss.persistence.biz;

import java.util.List;

import com.ancun.boss.pojo.marketInfo.CostCalcInfo;
import com.ancun.boss.pojo.marketInfo.QueryCostCalcListInput;
import com.ancun.boss.pojo.statistics.StatisticsProfitCostInput;

/**
 * 营销成本查询mapper。
 *
 * @Created on 2015年10月12日
 * @author kfc
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface CostCalcBizMapper {
	/**
	 * 根据查询条件，取得录音文件信息的总条数（录音正在上传中的过滤掉）
	 * 
	 * @param input
	 * @return 
	 */
	public int getCount(QueryCostCalcListInput input);
	
	
	public List<CostCalcInfo> queryCostCalcList(QueryCostCalcListInput input);
	
	/*
	 * 统计成本
	 * */
	public String statisticsCost(StatisticsProfitCostInput input);
}
