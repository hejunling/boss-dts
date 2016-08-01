package com.ancun.datadispense.service.province.impl;

import com.ancun.Application;
import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.province.DXProvinceBizComboInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/12
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class DXProvinceBizComboInfoServiceImplTest {
    @Resource
    private DXProvinceBizComboInfoService dxProvinceBizComboInfoService;

    @Test
    public void testInsertUserTaocan() throws Exception {
        BizComboInfo bizComboInfo = new BizComboInfo();
        bizComboInfo.setTcid(11l);
        bizComboInfo.setName("test套餐");
        bizComboInfo.setPrice(new BigDecimal(5600.00));
        bizComboInfo.setType("1");
        bizComboInfo.setCreateTime(new Date());
        bizComboInfo.setSpace(1024 * 1024l);
        bizComboInfo.setBizNo("DX-YUNNAN");
        bizComboInfo.setRemark("Remark Test");
        bizComboInfo.setCategory("1");
        bizComboInfo.setDefaultTaocan(BizConstants.MARK_YES);
        bizComboInfo.setRpcode("560000");

        dxProvinceBizComboInfoService.insertUserTaocan(bizComboInfo);
    }

    @Test
    public void testUpdateUserTaocan() throws Exception {
        BizComboInfo bizComboInfo = new BizComboInfo();
        bizComboInfo.setTcid(11l);
        bizComboInfo.setName("test套餐");
        bizComboInfo.setPrice(new BigDecimal(5600.00));
        bizComboInfo.setType("1");
        bizComboInfo.setCreateTime(new Date());
        bizComboInfo.setSpace(1024 * 1024l);
        bizComboInfo.setBizNo("DX-YUNNAN");
        bizComboInfo.setRemark("Remark Test 11");
        bizComboInfo.setCategory("1");
        bizComboInfo.setDefaultTaocan("");
        bizComboInfo.setRpcode("560000");

        dxProvinceBizComboInfoService.updateUserTaocan(bizComboInfo);
    }

    @Test
    public void testDeleteUserTaocan() throws Exception {
        BizComboInfo bizComboInfo = new BizComboInfo();
        bizComboInfo.setTcid(11l);
        dxProvinceBizComboInfoService.deleteUserTaocan(bizComboInfo);
    }
}