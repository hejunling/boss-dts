package com.ancun.boss.business.controller.dataDic;


import com.ancun.boss.business.pojo.dataDicInfo.GetDictionaryInput;
import com.ancun.boss.business.pojo.dataDicInfo.GetDictionaryOutput;
import com.ancun.boss.business.service.dataDic.IQueryDictionaryService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 数据字典相关controller
 * 获取数据字典信息
 *
 * @Created on 2016-4-1
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class QueryDictionaryController {
	@Resource
	IQueryDictionaryService queryDictionaryService;
	
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/getDictionary", method = RequestMethod.POST)
	public RespBody<GetDictionaryOutput> getDic(ReqBody<GetDictionaryInput> input){
		
		GetDictionaryOutput output = queryDictionaryService.getDic(input.getContent());
		
		return new RespBody<GetDictionaryOutput>(output);
		
	}
}
