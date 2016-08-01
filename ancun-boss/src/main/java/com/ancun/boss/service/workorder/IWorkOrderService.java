package com.ancun.boss.service.workorder;

import com.ancun.boss.pojo.workorder.*;

import java.util.List;

/**
 * 工单服务service接口
 *
 * @Created on 2015年10月15日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IWorkOrderService {

    /**
     * 更新用户工单信息
     *
     * @param input 用户工单参数信息
     * @return  影响条数
     */
    String updateUserWorkOrderInfo(GatherWorkOrderInfomeInput input);

    /**
     * 流转用户工单信息
     *
     * @param input 用户工单流转详细参数信息
     * @return  工单处理信息
     */
    WorkOrderDealOutput transformUserWorkOrder(TransformWorkOrderInfoInput input);
    
    /**
     * 查询用户工单信息
     *
     * @param input 用户工单参数信息
     * @return 用户工单信息
     */
    List<WorkOrderInfoPojo> queryWorkOrderInfoList(WorkOrderQueryInput input);
    
    /**
     * 用户工单导入
     *
     * @param input 用户工单导入参数
     * @return  
     */
    String importWorkOrderInfo(WorkOrderInfoListInput input);
    
    /**
     * 用户工单详细取得
     *
     * @param input 用户工单参数信息
     * @return  
     */
    WorkOrderInfoOutput queryWorkOrder(WorkOrderQueryInput input);
    
    /**
     * 用户工单处理数据取得
     *
     * @param input 用户工单参数信息
     * @return  
     */
    WorkOrderInfoOutput queryWorkorderForHandle(WorkOrderQueryInput input);
    
    /**
     * 回访记录信息登陆
     *
     * @param input 处理记录参数信息
     * @return  
     */
    void insertWorkOrderVisitRecord(WorkOrderVisitRecordInput input);

    /**
     * 初期化用户工单详细界面
     *
     * @return  用户工单详细
     */
    WorkOrderDetailOutput workOrderDetail(WorkOrderDetailInput input);
    
    /**
     * 工单内容校验
     *
     * @return  错误信息
     */
    String validateWorkOrderInfo(GatherWorkOrderInfomeInput input);

}
