package com.ancun.datasyn.service.provice.impl;

import com.ancun.Application;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.datasyn.service.provice.IDXUserinfoService;
import com.ancun.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class DXUserinfoServiceImplTest {

    @Resource
    private IDXUserinfoService dxUserinfoService;

    @Test
    public void testSelectAllPersonRpcodes() throws Exception {
        BizTimerConfig config = new BizTimerConfig();
        config.setSynStartTimer(DateUtils.convertStringToDate("yyyy-MM-dd HH:mm:ss", "2015-1-1 00:00:00"));
        config.setSynEndTimer(new Date());
        List<String> rpcodes = dxUserinfoService.selectAllPersonRpcodes(config);
        System.out.println("--------->" + rpcodes.size());
    }

    @Test
    public void testSelectAllEntRpcodes() throws Exception {
        BizTimerConfig config = new BizTimerConfig();
        config.setSynStartTimer(DateUtils.convertStringToDate("yyyy-MM-dd HH:mm:ss", "2015-1-1 00:00:00"));
        config.setSynEndTimer(new Date());
        List<String> rpcodes = dxUserinfoService.selectAllEntRpcodes(config);
        System.out.println("--------->" + rpcodes.size());
    }
}