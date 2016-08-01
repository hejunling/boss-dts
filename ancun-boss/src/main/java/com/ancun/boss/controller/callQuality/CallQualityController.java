package com.ancun.boss.controller.callQuality;

import com.ancun.boss.pojo.callQualityInfo.*;
import com.ancun.boss.service.callQuality.CallQualityService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 
 *
 * @Created on 2015年10月15日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class CallQualityController extends BaseController {
	
    @Resource
    private CallQualityService service;

	/**
	 * 呼入质检-全部查询
	 *
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/selectSerialCallQualityInfo", method = RequestMethod.POST)
	public RespBody<CallQualityListOutput> getCallQualityInfoList(ReqBody<CallQualityListInput> input) {

		CallQualityListOutput output = service.getCallQualityListinfo(input.getContent());
		ThreadLocalUtil.setContent("我查询了呼入质检内容");

		return new RespBody<CallQualityListOutput>(output);
	}
	
	/**
	 * 呼入质检-新增/修改
	 * 
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/gatherSingleCallQualityInfo", method = RequestMethod.POST)
	public RespBody<String> callQualityInfoSave(ReqBody<CallQualityInput> input) {

		service.addOrUpdateCallQualityInfo(input.getContent());
		if (StringUtil.isEmpty(input.getContent().getId())) {
			ThreadLocalUtil.setContent("新建了一条呼入质检记录：");
		} else {
			ThreadLocalUtil.setContent("更新了一条呼入质检记录：" + input.getContent().getId());
		}
		
		
		return new RespBody<String>();
	}
	
	/**
	 * 呼入质检-单个查询
	 *
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/selectSingleCallQualityInfo", method = RequestMethod.POST)
	public RespBody<CallQualityDetailOutput> getCallQualityInfo(ReqBody<CallQualityInfo> input) {

		CallQualityDetailOutput output = new CallQualityDetailOutput();

		output = service.getCallQualityInfo(Integer.valueOf(input.getContent().getId()));
		ThreadLocalUtil.setContent("查询了一条呼入质检详细：" + input.getContent().getId());
		return new RespBody<CallQualityDetailOutput>(output);
	}

	/**
	 * 呼入质检-删除
	 *
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/deleteCallQualityInfo", method = RequestMethod.POST)
	public RespBody<String> deleteCallQualityInfo(ReqBody<CallQualityInfo> input) {

		service.deleteCallQualityInfo(Integer.valueOf(input.getContent().getId()));
		ThreadLocalUtil.setContent("删除了一条呼入质检详细：" + input.getContent().getId());
		return new RespBody<String>();
	}

	/**
	 * 呼入质检数据导入
	 *
	 * @return 呼入质检数据导入结果
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
	@RequestMapping(value = "/callQualityInfoImport", method = RequestMethod.POST)
	public RespBody<String> callQualityInfoImport(ReqBody<CallQualityImportInput> input) {
		String out =service.importcallQualityInfo(input.getContent());
		ThreadLocalUtil.setContent("我批量导入了呼入质检：");
		return new RespBody<String>(out);
	}


	/**
	 * 呼入质检统计
	 *
	 * @return 查询结果
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
	@RequestMapping(value = "/callQualityStatistics", method = RequestMethod.POST)
	public RespBody<CallQualityStatisticsListOutput> callQualityInfoExport(ReqBody<CallQualityStatisticsInput> input) {
		CallQualityStatisticsListOutput out =service.statisticsCallQualityInfo(input.getContent());
		ThreadLocalUtil.setContent("我统计了呼入质检：");
		return new RespBody<CallQualityStatisticsListOutput>(out);
	}

}
