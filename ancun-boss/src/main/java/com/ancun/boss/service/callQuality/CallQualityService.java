package com.ancun.boss.service.callQuality;

import com.ancun.boss.pojo.callQualityInfo.*;


/**
 * 呼入质检业务逻辑接口
 *
 * @Created on 2015年10月15日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface CallQualityService {

    /**
     * 呼入质检-全部查询
     */
    public CallQualityListOutput getCallQualityListinfo(CallQualityListInput input);

    /**
     * 呼入质检-新增/修改
     */
    public void addOrUpdateCallQualityInfo(CallQualityInput input);

    /**
     * 呼入质检-单个查询(详细)
     *
     * @param id 编号
     * @return
     * @throws com.ancun.core.exception.EduException
     */
    public CallQualityDetailOutput getCallQualityInfo(int id);

    /**
     * 呼入质检-删除
     *
     * @param id id 编号
     * @return
     * @throws com.ancun.core.exception.EduException
     */
    public void deleteCallQualityInfo(int id);

    /**
     * 呼入质检数据导入
     * @param input
     */
    public String importcallQualityInfo(CallQualityImportInput input);

    /**
     * 呼入质检数据导出
     * @param input
     */
    public CallQualityStatisticsListOutput statisticsCallQualityInfo(CallQualityStatisticsInput input);
}
