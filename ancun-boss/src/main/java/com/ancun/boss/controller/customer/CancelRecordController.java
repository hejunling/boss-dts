package com.ancun.boss.controller.customer;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.pojo.callInfo.CancelRecordBusinessOutput;
import com.ancun.boss.pojo.callInfo.CancelRecordInput;
import com.ancun.boss.pojo.callInfo.CancelRecordOutput;
import com.ancun.boss.service.customer.ICancelRecordService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;


/**
 * 投诉退订管理
 * @author cys1993
 *
 */
@RestController
public class CancelRecordController extends BaseController{
	
	@Resource
	private ICancelRecordService cancelRecordService;
	
	/**
	 * 投诉退订列表
	 * 
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/cancelRecordGetList", method = RequestMethod.POST)
    public RespBody<CancelRecordOutput> queryCancelRecordList(ReqBody<CancelRecordInput> inputReqBody) throws
            EduException {
        CancelRecordInput input = inputReqBody.getContent();

        CancelRecordOutput output = cancelRecordService.queryCancelRecordList(input);
        ThreadLocalUtil.setContent("我查询了投诉退订内容");

        return new RespBody<CancelRecordOutput>(output);
    }
	
	/**
	 * 呼入登记详情查询
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/queryCancelRecordInfo", method = RequestMethod.POST)
    public RespBody<CancelRecordBusinessOutput> queryCancelInfo(ReqBody<CancelRecordInput> inputReqBody) throws EduException {

        CancelRecordInput input = inputReqBody.getContent();
        CancelRecordBusinessOutput cancelrecord = (cancelRecordService.queryCancelRecordInfo(input.getId()));

        return new RespBody<CancelRecordBusinessOutput>(cancelrecord);
    }
	
	/**
	 * 新增或修改呼入
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/addOrUpdatecancelRecordInfo", method = RequestMethod.POST)
    public RespBody<String> addOrUpdateCancelInfo(ReqBody<CancelRecordInput> infoInputReqBody) throws EduException {
		cancelRecordService.addOrUpdateCancelInfo(infoInputReqBody.getContent());
        return new RespBody<String>();
    }
	
	/**
	 * 投诉退订删除
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/deletecancelRecordInfo", method = RequestMethod.POST)
    public RespBody<String> deleteCancelInfo(ReqBody<CancelRecordInput> infoInputReqBody) throws EduException {
		cancelRecordService.deleteCancelInfo(infoInputReqBody.getContent().getId());
        return new RespBody<String>();
    }

}
