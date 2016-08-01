package com.ancun.boss.controller.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.persistence.model.SystemMenuInfo;
import com.ancun.boss.service.system.ISystemMenuInfoService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.utils.StringUtil;

/**
 * 
 *
 * @Created on 2015年5月25日
 * @author kfc
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class SystemMenuInfoController {

	@Resource
	private ISystemMenuInfoService systemMenuInfoService;

	/**
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/selectPoplarSystemMenuInfo", method = RequestMethod.POST)
	public RespBody<List<SystemMenuInfo>> selectPoplarSystemMenuInfo(
			ReqBody<SystemMenuInfo> input) {
		List<SystemMenuInfo> systemMenuInfoList = systemMenuInfoService
				.selectPoplarSystemMenuInfo(input.getContent());
		RespBody<List<SystemMenuInfo>> respBody = new RespBody<List<SystemMenuInfo>>();
		respBody.setContent(systemMenuInfoList);
		return respBody;
	}

	/**
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/selectSingleSystemMenuInfo", method = RequestMethod.POST)
	public RespBody<SystemMenuInfo> selectSingleSystemMenuInfo(
			ReqBody<SystemMenuInfo> input) {
		SystemMenuInfo systemMenuInfo = systemMenuInfoService
				.selectSingleSystemMenuInfo(input.getContent());
		RespBody<SystemMenuInfo> respBody = new RespBody<SystemMenuInfo>();
		respBody.setContent(systemMenuInfo);
		return respBody;
	}

	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/gatherSystemMenuInfo", method = RequestMethod.POST)
	public RespBody<Integer> gatherSingleSystemMenuInfo(
			ReqBody<SystemMenuInfo> input) {
		RespBody<Integer> respBody = new RespBody<Integer>();

		SystemMenuInfo systemMenuInfo = input.getContent();
		if (StringUtil.isEmpty(systemMenuInfo.getMenuno()))
			respBody.setContent(systemMenuInfoService
					.insertSystemMenuInfo(systemMenuInfo));
		else {
			respBody.setContent(systemMenuInfoService
					.updateNotOrderInfoSystemMenuInfo(systemMenuInfo));
		}
		return respBody;
	}
	
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/deleteSystemMenuInfo", method = RequestMethod.POST)
	public RespBody<Integer> deleteSingleSystemMenuInfo(
			ReqBody<SystemMenuInfo> input) {
		RespBody<Integer> respBody = new RespBody<Integer>();
		SystemMenuInfo systemMenuInfo = input.getContent();		
		respBody.setContent(systemMenuInfoService
					.deleteLogicalSingleSystemMenuInfo(systemMenuInfo));
		return respBody;
	}

}
