package com.ancun.boss.persistence.biz.mapper;

import com.ancun.boss.pojo.callQualityInfo.*;

import java.util.List;


public interface CallQualityMapper {
	/**
	 * 列表
	 */
	List<CallQualityInfo> selectByRequest(CallQualityListInput input);
	
	/**
	 * 详细
	 */
	CallQualityInfo selectByPrimaryKey(Long id);

	/**
	 * 月个人平均
	 */
	List<CallQualityMonthStatisticsInfo> statisticsMonthAverage(CallQualityStatisticsInput input);


	/**
	 * 季度平均分
	 */
	List<CallQualityQuarterStatisticsInfo> statisticsQuarterAverage(CallQualityStatisticsInput input);

	/**
	 * 半年平均分
	 */
	List<CallQualityHalfYearStatisticsInfo> statisticsHalfYearAverage(CallQualityStatisticsInput input);

	/**
	 * 年平均分
	 */
	List<CallQualityYearStatisticsInfo> statisticsYearAverage(CallQualityStatisticsInput input);

}
