package com.ancun.boss.controller.system;

import com.ancun.boss.pojo.systemNotice.SystemNoticeInput;
import com.ancun.boss.pojo.systemNotice.SystemNoticeListInput;
import com.ancun.boss.pojo.systemNotice.SystemNoticeListOutput;
import com.ancun.boss.pojo.systemNotice.SystemNoticeOutput;
import com.ancun.boss.service.systemNotice.ISystemNoticeService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 消息提醒controller
 *
 * @Created on 2015年10月26日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class SystemNoticeController extends BaseController {
	
    @Resource
    private ISystemNoticeService service;

	/**
	 * 系统消息通知-全部查询
	 *
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/getSystemNoticeList", method = RequestMethod.POST)
	public RespBody<SystemNoticeListOutput> getSystemNoticeList(ReqBody<SystemNoticeListInput> input) {

		SystemNoticeListOutput output = service.getSystemNoticeListinfo(input.getContent());
//		ThreadLocalUtil.setContent("我查询了系统消息通知");

		return new RespBody<SystemNoticeListOutput>(output);
	}
	
	/**
	 * 消息提醒-详情
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/getSystemNoticeinfo", method = RequestMethod.POST)
	public RespBody<SystemNoticeOutput> getSystemNoticeinfo(ReqBody<SystemNoticeInput> input){
		
		SystemNoticeOutput output = service.getSystemNoticeinfo(input.getContent().getId());
		
		return new RespBody<SystemNoticeOutput>(output);
	}
	
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/updateSystemNoticeinfo", method = RequestMethod.POST)
	public RespBody<String> updateSystemNoticeinfo(ReqBody<SystemNoticeInput> input){
		
		service.updateSystemNoticeinfo(input.getContent().getId());
		
		return new RespBody<String>();
	}
}
