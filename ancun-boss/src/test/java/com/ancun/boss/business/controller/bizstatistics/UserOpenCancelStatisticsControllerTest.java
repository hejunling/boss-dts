package com.ancun.boss.business.controller.bizstatistics;

import com.ancun.boss.business.pojo.bizuser.UserOpenCancelStatisticInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * 用户开通退订统计测试类
 * User: zkai
 * Date: 2016/6/17
 * Time: 11:03
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class UserOpenCancelStatisticsControllerTest  extends BaseAPi {
    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:8087";
        this.accessId = "243073adce25fc66b5a9633b5a991599";
        this.accessKey = "MWEzNGIyZjlhYTUwNjUyMTUzZjA5MWQwNjc1OWRiZTA=";
    }

    @Test
    public void testUserOpenCancelStatistic() throws Exception {
        UserOpenCancelStatisticInput input = new UserOpenCancelStatisticInput();
        input.setUserno("103");

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setStatus("1");
        input.setBizno("DX-GUANGXI");
        input.setSource("2");
        super.responseJSON("bizUserOpenCancelStatistic", input);

    }
}