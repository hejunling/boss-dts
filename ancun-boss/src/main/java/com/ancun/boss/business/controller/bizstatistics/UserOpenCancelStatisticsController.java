package com.ancun.boss.business.controller.bizstatistics;

import com.ancun.boss.business.pojo.bizuser.UserOpenCancelStatisticInput;
import com.ancun.boss.business.pojo.bizuser.UserOpenCancelStatisticOutput;
import com.ancun.boss.business.service.bizuser.IUserOpenCancelStatisticService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户开通退订统计
 * User: zkai
 * Date: 2016/6/16
 * Time: 15:02
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class UserOpenCancelStatisticsController {

    @Resource
    IUserOpenCancelStatisticService userOpenCancelStatisticService;

    /**
     * 用户在一定时间区间内开通退订统计人数
     *
     * @param reqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/bizUserOpenCancelStatistic", method = RequestMethod.POST)
    public RespBody<UserOpenCancelStatisticOutput> getUserOpenCancelStatistic(ReqBody<UserOpenCancelStatisticInput> reqBody) throws EduException {
        UserOpenCancelStatisticInput input = reqBody.getContent();

        ThreadLocalUtil.setContent("用户在一定时间区间内开通退订统计人数");

        return new RespBody<UserOpenCancelStatisticOutput>(userOpenCancelStatisticService.getUserOpenCancelStatistic(input));
    }
}
