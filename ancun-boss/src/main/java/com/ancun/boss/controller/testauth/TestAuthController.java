package com.ancun.boss.controller.testauth;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.pojo.callincharts.TestAuthInput;
import com.ancun.boss.service.auth.impl.AuthenticationUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;

@RestController
public class TestAuthController extends BaseController{
	
	@Resource
	private AuthenticationUtil authenticationUtil;
	
	/**
	 * 测试号码鉴权。
	 * 
	 * @param input
	 * @return
	 */
//	@AccessToken(access = AccessType.WEB,sign = SignType.NORMAL)
//	@RequestMapping(value = "/testauth", method = RequestMethod.GET)
	
	@AccessToken(access = AccessType.WEB, checkAccess = false)
	@RequestMapping(value = "/testauth", method = RequestMethod.POST)
	public RespBody<String> testAuth(ReqBody<TestAuthInput> bl) {
		
		TestAuthInput bi = bl.getContent();
		
//		String b = authenticationUtil.authShanghaiCheck(bi.getUserTel(),bi.getProvinceCode(),bi.getBusinessCode());
		
		boolean b = authenticationUtil.validCheck(bi.getUserTel(), bi.getProvinceCode(),bi.getBusinessCode(),bi.getBizNo());
		
		return new RespBody<String>(String.valueOf(b));
		
	}

}
