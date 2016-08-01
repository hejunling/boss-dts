package com.ancun.boss.persistence.biz;

import java.util.List;

import com.ancun.boss.pojo.workorder.WorkOrderQueryInput;
import com.ancun.boss.pojo.workorder.WorkOrderInfoPojo;


/**
 * 查询工单信息
 *
 * @author chenb
 * @version 1.0
 * @Created on 2015/10/16
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizWorkOrderMapper {
	
    /**
     * 根据输入条件 查询对应工单
     *
     * @param input
     * @return
     */
	List<WorkOrderInfoPojo> selectOrderList(WorkOrderQueryInput input);
	
    /**
     * 根据工单ID 查询对应工单信息
     *
     * @param input
     * @return
     */
	WorkOrderInfoPojo selectOrderInfo(WorkOrderQueryInput input);
}
