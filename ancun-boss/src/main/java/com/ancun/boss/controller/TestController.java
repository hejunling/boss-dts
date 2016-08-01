package com.ancun.boss.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;

/**
 * 
 *
 * @Created on 2015年5月25日
 * @author kfc
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class TestController {
	/**
	 * 测试。
	 * 
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public RespBody<String> updatePassword(ReqBody<String> input) {
		RespBody<String> a = new RespBody<String>();
		a.setContent("aaa");
		return a;
	}
}
