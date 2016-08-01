package com.ancun.boss.business.controller.bizcanceluser;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.business.pojo.bizcanceluser.BizBatchCancelUserInput;
import com.ancun.boss.business.pojo.bizcanceluser.BizCancelUserInput;
import com.ancun.boss.business.service.bizcanceluser.IBizCanlelUserService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;

/**
 * 业务用户退订
 *
 * @Created on 2016年3月9日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class BizCancelUserController extends BaseController {
	
	@Resource
	private IBizCanlelUserService bizCancelService;

	/**
	 * 单个退订
	 * @param reqBody
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/bizUnifiedCancelUser", method = RequestMethod.POST)
    public RespBody<String> bizUnifiedCancelUser(ReqBody<BizCancelUserInput> reqBody){
		BizCancelUserInput input = reqBody.getContent();
		bizCancelService.bizCancelUser(input);
		ThreadLocalUtil.setContent("业务用户退订");
        return new RespBody<String>();
    }
	
	/**
	 * 批量退订
	 * @param reqBody
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/bizUnifiedBatchCancelUser", method = RequestMethod.POST)
	public RespBody<String> bizUnifiedBatchCancelUser(ReqBody<BizBatchCancelUserInput> reqBody){
		BizBatchCancelUserInput input = reqBody.getContent();
		bizCancelService.bizBatchCancelUser(input);
		ThreadLocalUtil.setContent("业务用户批量退订");
		return new RespBody<String>();
	}

}
