package com.ancun.boss.controller.inOutNetCount;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.pojo.inOutNetCount.InOutNetCountOutput;
import com.ancun.boss.pojo.inOutNetCount.InOutNetQueryInput;
import com.ancun.boss.pojo.marketInfo.MarketCheckOutput;
import com.ancun.boss.service.inOutNetCount.InOutNetCountService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;

/**
 * 
 *
 * @Created on 2015年11月4日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class InOutNetCountController extends BaseController {
	
	@Resource
	private InOutNetCountService inOutService;
	
	/**
	 * 在网离网数据取得
	 * 
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/inOutNetCountBarGet", method = RequestMethod.POST)
	public RespBody<InOutNetCountOutput> getInOutNetCountLine(ReqBody<InOutNetQueryInput> input){
		
		InOutNetCountOutput output = new InOutNetCountOutput();
		
		output = inOutService.getInOutNetData(input.getContent());
		
		return new RespBody<InOutNetCountOutput>(output);
	}
	
}
