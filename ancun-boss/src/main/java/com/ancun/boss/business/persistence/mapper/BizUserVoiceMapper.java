package com.ancun.boss.business.persistence.mapper;

import com.ancun.boss.business.pojo.bizvoice.*;

import java.util.List;


public interface BizUserVoiceMapper {
	/**
	 * 月总量
	 */
	List<BizUserVoiceMonitorStatisticsInfo> statisticsMonthCount(BizUserVoiceStatisticsInput input);


	/**
	 * 季度总量
	 */
	List<BizUserVoiceQuarterStatisticsInfo> statisticsQuarterCount(BizUserVoiceStatisticsInput input);

	/**
	 * 半年总量
	 */
	List<BizUserVoiceHalfYearsStatisticsInfo> statisticsHalfYearCount(BizUserVoiceStatisticsInput input);

	/**
	 * 年总量
	 */
	List<BizUserVoiceYearStatisticsInfo> statisticsYearCount(BizUserVoiceStatisticsInput input);

	/**
	 * 业务录音统计
	 */
	List<BizUserVocieStatisticsTableInfo> statisticsVoiceCount(BizUserVoiceStatisticsTableInput input);
}
