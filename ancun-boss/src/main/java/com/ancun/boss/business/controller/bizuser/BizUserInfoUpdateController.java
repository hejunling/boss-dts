package com.ancun.boss.business.controller.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizUserInfoInput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoUpdateInput;
import com.ancun.boss.business.service.bizuser.IBizUserInfoUpdateService;
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
 * 修改业务用户信息
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/4/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class BizUserInfoUpdateController extends BaseController {


    @Resource
    private IBizUserInfoUpdateService bizUserInfoUpdateService;

    /**
     * 业务用户修改
     *
     * @param reqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/bizUpdateUser", method = RequestMethod.POST)
    public RespBody<String> bizUpdateUser(ReqBody<BizUserInfoUpdateInput> reqBody) throws EduException {

        bizUserInfoUpdateService.updateBizUserInfo(reqBody.getContent());

        ThreadLocalUtil.setContent("业务用户修改");

        return new RespBody<String>(null);
    }

    /**
     * 清密
     *
     * @param reqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/bizUserCleanPwd", method = RequestMethod.POST)
    public RespBody<String> bizUserCleanPwd(ReqBody<BizUserInfoInput> reqBody) throws EduException {

        bizUserInfoUpdateService.bizUserCleanPwd(reqBody.getContent());
        ThreadLocalUtil.setContent("清密");

        return new RespBody<String>(null);
    }

}
