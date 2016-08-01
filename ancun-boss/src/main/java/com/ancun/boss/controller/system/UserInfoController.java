package com.ancun.boss.controller.system;

import com.ancun.boss.pojo.userInfo.*;
import com.ancun.boss.service.system.IUserInfoService;
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
import java.util.List;

/**
 * 用户管理 控制类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/18
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class UserInfoController extends BaseController {

    @Resource
    private IUserInfoService userInfoService;

    /**
     * 查询用户列表
     *
     * @param inputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/queryUserList", method = RequestMethod.POST)
    public RespBody<UserInfoListOutput> queryUserList(ReqBody<UserInfoListInput> inputReqBody) throws
            EduException {
        UserInfoListInput input = inputReqBody.getContent();

        UserInfoListOutput output = userInfoService.queryUserList(input);
        ThreadLocalUtil.setContent("我查询了用户数据内容");

        return new RespBody<UserInfoListOutput>(output);
    }


    /**
     * 用户信息查询
     *
     * @param inputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/queryUserInfo", method = RequestMethod.POST)
    public RespBody<UserInfoOutput> queryUserInfo(ReqBody<UserInfoListInput> inputReqBody) throws EduException {

        UserInfoListInput input = inputReqBody.getContent();
        UserInfoOutput systemUserInfo = userInfoService.queryUserInfo(input.getJobno());

        return new RespBody<UserInfoOutput>(systemUserInfo);
    }


    /**
     * 新增或修改用户信息
     *
     * @param infoInputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/addOrUpdateUserInfo", method = RequestMethod.POST)
    public RespBody<String> addOrUpdateUserInfo(ReqBody<UserInfoInput> infoInputReqBody) throws EduException {
        userInfoService.addOrUpdateUserInfo(infoInputReqBody.getContent());
        return new RespBody<String>();
    }

    /**
     * 删除用户信息
     *
     * @param infoInputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/deleteUserInfo", method = RequestMethod.POST)
    public RespBody<String> deleteUserInfo(ReqBody<UserInfoListInput> infoInputReqBody) throws EduException {
        userInfoService.deleteUserInfo(infoInputReqBody.getContent().getJobno());
        return new RespBody<String>();
    }

    /**
     * 用户下拉框
     *
     * @param reqBody   查询条件参数列表
     * @return  用户下拉框显示用列表
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/userSelectList", method = RequestMethod.POST)
    public RespBody<List<UserSelectOutput>> userSelectList(ReqBody<UserSelectInput> reqBody) {

//        // 模糊查询用用户名关键字key
//        String key = reqBody.getContent().getName();

        List<UserSelectOutput> outputList = userInfoService.queryAllUserForSelect();

        return new RespBody<List<UserSelectOutput>>(outputList);
    }
}

