package com.ancun.boss.business.controller.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizUserInfoKeyInput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoKeyOutput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoQueryInput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoListOutput;
import com.ancun.boss.business.service.bizuser.IBizQueryUserService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BizQueryUserController {

    @Resource
    private IBizQueryUserService bizUserService;

    /**
     * 分页查询用户列表
     * @param input
     * @return
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/mgrGetUserInfoList", method = RequestMethod.POST)
    public RespBody<BizUserInfoListOutput> mgrGetUserList(
            ReqBody<BizUserInfoQueryInput> input) {

        BizUserInfoQueryInput bizUserInfoIput = input.getContent();

        // 用户信息列表
        BizUserInfoListOutput output = bizUserService.getBizUserInfoList(bizUserInfoIput);

        RespBody<BizUserInfoListOutput> out = new RespBody<BizUserInfoListOutput>(
                output);
        return out;
    }

    /**
     * 根据主键得到业务用户信息
     * @param input
     * @return
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/selectUserInfoByKey", method = RequestMethod.POST)
    public RespBody<BizUserInfoKeyOutput> selectUserInfoByKey(
            ReqBody<BizUserInfoKeyInput> input) {

        BizUserInfoKeyInput in = input.getContent();

        // 用户信息列表
        BizUserInfoKeyOutput output = bizUserService.selectUserInfoByKey(in);

        return new RespBody<BizUserInfoKeyOutput>(output);
    }

}
