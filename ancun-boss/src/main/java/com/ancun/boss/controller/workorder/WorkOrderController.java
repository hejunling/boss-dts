package com.ancun.boss.controller.workorder;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.pojo.workorder.*;
import com.ancun.boss.service.workorder.IWorkOrderService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.constant.ResponseConst;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;

import com.google.common.base.Objects;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * 工单服务Controller
 *
 * @Created on 2015年10月15日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class WorkOrderController {

    /** 工单服务Service */
    @Resource
    private IWorkOrderService workOrderService;

    /**
     * 工单服务-新增/修改
     *
     * @param reqBody   请求体
     * @return  操作结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/gatherWorkOrderInfome", method = RequestMethod.POST)
    public RespBody<String> gatherWorkOrderInfome(ReqBody<GatherWorkOrderInfomeInput> reqBody) {

    	String msg = "";
    	msg = workOrderService.validateWorkOrderInfo(reqBody.getContent());

    	if (msg != "") {
    		RespBody<String> out = new RespBody<String>(null);
       	 	int code = ResponseConst.SYSTEM_EXCEPTION;
       	 	out.getInfo().setCode(code);
       	 	out.getInfo().setMsg(msg);
       	 	return out;
    	}

        String orderNo = workOrderService.updateUserWorkOrderInfo(reqBody.getContent());
        if (Objects.equal(reqBody.getContent().getAction(), BizRequestConstant.TO_ADD)) {
			ThreadLocalUtil.setContent("新建了一条工单：" + reqBody.getContent().getOrderNo());
		} else if (Objects.equal(reqBody.getContent().getAction(), BizRequestConstant.TO_UPDATE)) {
			ThreadLocalUtil.setContent("更新了一条工单：" + reqBody.getContent().getOrderNo());
		}

        return new RespBody<String>(orderNo);
    }

    /**
     * 工单服务-查询
     *
     * @param reqBody   请求体
     * @return  操作结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/queryWorkOrderList", method = RequestMethod.POST)
    public RespBody<WorkOrderInfoOutput> queryWorkOrderList(ReqBody<WorkOrderQueryInput> reqBody) {


    	WorkOrderInfoOutput output = new WorkOrderInfoOutput();

    	// 工单信息取得
    	List<WorkOrderInfoPojo> orderList = workOrderService.queryWorkOrderInfoList(reqBody.getContent());

    	output.setOrderList(orderList);

    	// 设置分页信息
    	output.setPageinfo(reqBody.getContent().getPage());

        return new RespBody<WorkOrderInfoOutput>(output);
    }

    /**
     * 工单服务-详细查询
     *
     * @param reqBody   请求体
     * @return  操作结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/queryWorkOrderInfo", method = RequestMethod.POST)
    public RespBody<WorkOrderInfoOutput> queryWorkOrderInfo(ReqBody<WorkOrderQueryInput> reqBody) {

    	WorkOrderInfoOutput output = new WorkOrderInfoOutput();

    	output = workOrderService.queryWorkOrder(reqBody.getContent());

        return new RespBody<WorkOrderInfoOutput>(output);
    }

    /**
     * 工单服务-详细查询
     *
     * @param reqBody   请求体
     * @return  操作结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/queryWorkOrderExchange", method = RequestMethod.POST)
    public RespBody<WorkOrderInfoOutput> queryWorkOrderExchange(ReqBody<WorkOrderQueryInput> reqBody) {

    	WorkOrderInfoOutput output = new WorkOrderInfoOutput();

    	output = workOrderService.queryWorkorderForHandle(reqBody.getContent());

        return new RespBody<WorkOrderInfoOutput>(output);
    }

    /**
     * 工单服务-处理记录查询
     *
     * @param reqBody   请求体
     * @return  操作结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/queryWorkOrderVisitInfo", method = RequestMethod.POST)
    public RespBody<WorkOrderInfoOutput> queryWorkOrderVisitInfo(ReqBody<WorkOrderQueryInput> reqBody) {

    	WorkOrderInfoOutput output = new WorkOrderInfoOutput();

    	output = workOrderService.queryWorkorderForHandle(reqBody.getContent());

        return new RespBody<WorkOrderInfoOutput>(output);
    }

    /**
     * 工单服务-转交
     *
     * @param reqBody   请求体
     * @return  操作结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/transformWorkOrderInfo", method = RequestMethod.POST)
    public RespBody<WorkOrderDealOutput> transformWorkOrderInfo(ReqBody<TransformWorkOrderInfoInput> reqBody) {

        WorkOrderDealOutput output = workOrderService.transformUserWorkOrder(reqBody.getContent());
        ThreadLocalUtil.setContent("转交了一条工单：" + reqBody.getContent().getOrderno());

        return new RespBody<WorkOrderDealOutput>(output);
    }

    /**
     * 工单服务-导入数据
     *
     * @param reqBody   请求体
     * @return  操作结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/importWorkOrderInfo", method = RequestMethod.POST)
    public RespBody<String> importWorkOrderInfo(ReqBody<WorkOrderInfoListInput> reqBody) {

    	String msg = "";

    	msg = workOrderService.importWorkOrderInfo(reqBody.getContent());

        return new RespBody<String>(msg);
    }

    /**
     * 工单服务-回访信息登录
     *
     * @param reqBody   请求体
     * @return  操作结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/addVisitRecord", method = RequestMethod.POST)
    public RespBody<String> addVisitRecord(ReqBody<WorkOrderVisitRecordInput> reqBody) {

    	workOrderService.insertWorkOrderVisitRecord(reqBody.getContent());

        return new RespBody<String>();
    }

    /**
     * 用户工单记录
     *
     * @param reqBody   请求体
     * @return  操作结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/workOrderDetail", method = RequestMethod.POST)
    public RespBody<WorkOrderDetailOutput> workOrderDetail(ReqBody<WorkOrderDetailInput> reqBody) {
        return new RespBody<WorkOrderDetailOutput>(workOrderService.workOrderDetail(reqBody.getContent()));
    }

}
