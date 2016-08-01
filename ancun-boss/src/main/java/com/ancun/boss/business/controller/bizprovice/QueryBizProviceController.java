package com.ancun.boss.business.controller.bizprovice;


import com.ancun.boss.business.pojo.bizprovice.SelectBizProviceInput;
import com.ancun.boss.business.pojo.bizprovice.SelectBizProviceOutput;
import com.ancun.boss.business.service.bizprovice.IBizProviceService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 业务省份信息相关controller
 * @Created on 2016-4-1
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class QueryBizProviceController {
	@Resource
	IBizProviceService service;
	
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/getBizProvice", method = RequestMethod.POST)
	public RespBody<SelectBizProviceOutput> getBizProvice(ReqBody<SelectBizProviceInput> input){

		SelectBizProviceOutput output = service.selectBizProvice(input.getContent());
		
		return new RespBody<SelectBizProviceOutput>(output);
		
	}
}
