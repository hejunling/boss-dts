package com.ancun.boss.controller.customer;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.pojo.callInfo.CallInRecordBusinessOutput;
import com.ancun.boss.pojo.callInfo.CallInRecordInput;
import com.ancun.boss.pojo.callInfo.CallInRecordOutput;
import com.ancun.boss.service.customer.ICallInRecordService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;

/**
 * 呼入登记管理
 * 
 * @author cys
 *
 */
@RestController
public class CallInRecordController extends BaseController{
	@Resource
	private ICallInRecordService callInRecordService;
	
	/**
	 * 查询呼入登记列表
	 * 
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/callinRecordGetList", method = RequestMethod.POST)
    public RespBody<CallInRecordOutput> queryCallInRecordList(ReqBody<CallInRecordInput> inputReqBody) throws
            EduException {
        CallInRecordInput input = inputReqBody.getContent();

        CallInRecordOutput output = callInRecordService.queryCallInRecordList(input);
        ThreadLocalUtil.setContent("我查询了呼入登记内容");

        return new RespBody<CallInRecordOutput>(output);
    }
	
	/**
	 * 呼入登记详情查询
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/queryCallinInfo", method = RequestMethod.POST)
    public RespBody<CallInRecordBusinessOutput> queryUserInfo(ReqBody<CallInRecordInput> inputReqBody) throws EduException {

        CallInRecordInput input = inputReqBody.getContent();
        CallInRecordBusinessOutput callinrecord = callInRecordService.queryCallInRecordInfo(input.getId());

        return new RespBody<CallInRecordBusinessOutput>(callinrecord);
    }
	
	/**
	 * 新增或修改呼入
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/addOrUpdateCallinInfo", method = RequestMethod.POST)
    public RespBody<String> addOrUpdateCallinfo(ReqBody<CallInRecordInput> infoInputReqBody) throws EduException {
		callInRecordService.addOrUpdateCallInfo(infoInputReqBody.getContent());
        return new RespBody<String>();
    }
	
	/**
	 * 呼入登记删除
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/deleteCallinInfo", method = RequestMethod.POST)
    public RespBody<String> deleteCallinInfo(ReqBody<CallInRecordInput> infoInputReqBody) throws EduException {
		callInRecordService.deleteCallinInfo(infoInputReqBody.getContent());
        return new RespBody<String>();
    }

}
