package com.ancun.datadispense.service.province.impl;

import com.ancun.Application;
import com.ancun.common.persistence.model.master.BizPersonInfo;
import com.ancun.datadispense.service.province.DXProvinceBizPersonInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/8
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class DXProvinceBizPersonInfoServiceImplTest {

    @Resource
    private DXProvinceBizPersonInfoService dxProvinceBizPersonInfoService;

    @Test
    public void testUpdateBizPersonInfo() throws Exception {
        BizPersonInfo personInfo = new BizPersonInfo();
        personInfo.setSex("1");
        personInfo.setUserNo("15957178759");
        personInfo.setIdentify("XXXXXXXXXXXXXXXXXXXXXX");

        dxProvinceBizPersonInfoService.updateBizPersonInfo(personInfo);

    }
}