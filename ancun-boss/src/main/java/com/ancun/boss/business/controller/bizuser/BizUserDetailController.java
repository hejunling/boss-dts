package com.ancun.boss.business.controller.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizUserDetaiOutput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoInput;
import com.ancun.boss.business.pojo.bizuser.BizUserLifeCircleListOutput;
import com.ancun.boss.business.service.bizuser.IBizUserDetailService;
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
 * 用户详情查询
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/4/19
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class BizUserDetailController extends BaseController {

    @Resource
    private IBizUserDetailService bizUserDetailService;

    /**
     * 用户详情查询
     *
     * @param reqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/bizUserDetail", method = RequestMethod.POST)
    public RespBody<BizUserDetaiOutput> bizUserDetail(ReqBody<BizUserInfoInput> reqBody) throws EduException {
        BizUserInfoInput input = reqBody.getContent();

        ThreadLocalUtil.setContent("用户详情查询");

        return new RespBody<BizUserDetaiOutput>(bizUserDetailService.querybizUserDetail(input));
    }

    /**
     * 用户生命周期查询
     *
     * @param respBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/bizUserLifeCircle", method = RequestMethod.POST)
    public RespBody<BizUserLifeCircleListOutput> bizUserLifeCircle(ReqBody<BizUserInfoInput> respBody) throws EduException {
        BizUserInfoInput input = respBody.getContent();

        ThreadLocalUtil.setContent("用户生命周期查询");

        return new RespBody<BizUserLifeCircleListOutput>(bizUserDetailService.queryBizUserLifeCircle(input));
    }
}
