package com.ancun.boss.persistence.biz;

import java.util.List;

import com.ancun.boss.pojo.workorder.WorkOrderExchangePojo;
import com.ancun.boss.pojo.workorder.WorkOrderQueryInput;

/**
 * @author chenb
 * @version 1.0
 * @Created on 2015/10/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizWorkOrderExchangeMapper {
	
    /**
     * 用户工单流转详细取得
     *
     * @param input
     * @return
     */
	List<WorkOrderExchangePojo> selectExchangeList(WorkOrderQueryInput input);
}
