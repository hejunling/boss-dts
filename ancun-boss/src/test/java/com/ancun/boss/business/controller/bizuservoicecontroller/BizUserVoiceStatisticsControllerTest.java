package com.ancun.boss.business.controller.bizuservoicecontroller;

import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsInput;
import com.ancun.boss.business.pojo.bizvoice.BizUserVoiceStatisticsTableInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * Create with com.ancun.boss.business.controller.bizuservoicecontroller
 * User: zkai
 * Date: 2016/6/24
 * Time: 10:37
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizUserVoiceStatisticsControllerTest extends BaseAPi {
    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:8087";
        this.accessId = "1793c3898eec681197ef79d9469c5c23";
        this.accessKey = "OWYxNGQ2NmI5NWU5OGU2M2I2OWY5ODIwNjRhZjA4MDM=";
    }

//    @Test
//    public void test() throws Exception {
//        BizUserVoiceStatisticsInput input = new BizUserVoiceStatisticsInput();
//        input.setAccessid(accessId);
//        input.setUserno("zkai");
//        input.setAccesskey(accessKey);
//        input.setYear("2016");
//
//        super.responseJSON("bizUserVoiceCountStatistics", input);
//    }

    @Test
    public void test() throws Exception {
        BizUserVoiceStatisticsTableInput input = new BizUserVoiceStatisticsTableInput();
        input.setAccessid(accessId);
        input.setUserno("zkai");
        input.setAccesskey(accessKey);
        input.setBizno("DX-HAINAN");
        input.setCpcertificateflg("2");


        super.responseJSON("bizUserVoiceStatisticsTable", input);
    }

}