package com.ancun.boss.business.controller.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizEntSubAccountListInput;
import com.ancun.boss.business.pojo.bizuser.BizEntAddSubAccountInput;
import com.ancun.boss.business.pojo.bizuser.BizEntAddSubAccountOutPut;
import com.ancun.boss.business.pojo.bizuser.BizEntSubAccountListOutPut;
import com.ancun.boss.business.service.bizuser.IBizEntSubAccountService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 企业用户子账号操作controller
 *
 * @author zkai
 * @version 1.0
 * @Created on 2016/4/25
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class BizEntSubAccountController extends BaseController {

    @Resource
    IBizEntSubAccountService bizEntSubAccountService;

    /**
     * 企业用户添加子账号
     *
     * @param input
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/bizEntAddSubAccount", method = RequestMethod.POST)
    public RespBody<BizEntAddSubAccountOutPut> bizEntAddSubAccount(ReqBody<BizEntAddSubAccountInput> input) throws EduException {
        BizEntAddSubAccountInput in = input.getContent();

        ThreadLocalUtil.setContent("企业用户添加子账号");

        return new RespBody<BizEntAddSubAccountOutPut>(bizEntSubAccountService.bizEntAddSubAccount(in));
    }

    /**
     * 企业用户子账号列表
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/bizEntSubAccountList", method = RequestMethod.POST)
    public RespBody<BizEntSubAccountListOutPut> bizEntSubAccountList(ReqBody<BizEntSubAccountListInput> input) throws EduException {
        BizEntSubAccountListInput in = input.getContent();

        ThreadLocalUtil.setContent("企业用户子账号列表");

        return new RespBody<BizEntSubAccountListOutPut>(bizEntSubAccountService.bizEntSubAccountList(in));
    }

}
