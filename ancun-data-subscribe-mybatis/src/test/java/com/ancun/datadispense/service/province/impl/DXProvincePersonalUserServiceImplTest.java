package com.ancun.datadispense.service.province.impl;

import com.ancun.Application;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.province.DXProvincePersonalUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/3/25
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class DXProvincePersonalUserServiceImplTest {

    @Resource
    private DXProvincePersonalUserService dxProvincePersonalUserService;

    @Test
    public void testOpenPersonalUser() throws Exception {
        BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setUserNo("15957178759");
        bizUserInfo.setPasswd("h0GDwSQVx45YqI7DUCTLu4ZHAMzt6iA7EJllEa5urb6mxJIyt0ofXA==");
        bizUserInfo.setUserType(BizConstants.USER_TYPE_PERSONAL);
        bizUserInfo.setTcid(3l);
        bizUserInfo.setStatus(BizConstants.USERSTATUS_NORMAL);
        bizUserInfo.setRpcode("350000");
//        bizUserInfo.setCityCode("22000");
//        bizUserInfo.setPhone("15957178759");
//        bizUserInfo.setRectip(BizConstants.MARK_NO);
//
//        bizUserInfo.setPhonetype(BizConstants.USER_PHONETYPE_CELL_PHONE);
//        bizUserInfo.setIntime(new Date());
//        bizUserInfo.setInsource("2");
//        bizUserInfo.setCalledflag("1");
//        bizUserInfo.setCallerRecord("0");
//        bizUserInfo.setIsrefund("1");
//        bizUserInfo.setFax("2");
//        bizUserInfo.setRemark("XXXXXXXXXXXXXXXXXX");
//        bizUserInfo.setBusinesstype("DX");
//        bizUserInfo.setCallerVoice("1");
//        bizUserInfo.setCalledRecord("0");

        dxProvincePersonalUserService.openPersonalUser(bizUserInfo);

    }

    @Test
    public void testUpdatePersonalUser() throws Exception {
        BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setUserNo("15957178759");
        bizUserInfo.setPasswd("h0GDwSQVx45YqI7DUCTLu4ZHAMzt6iA7EJllEa5urb6mxJIyt0ofXA==");
        bizUserInfo.setUserType(BizConstants.USER_TYPE_PERSONAL);
        bizUserInfo.setTcid(3l);
        bizUserInfo.setStatus(BizConstants.USERSTATUS_CANCEL);
        bizUserInfo.setRpcode("450000");
        bizUserInfo.setCityCode("22000");
        bizUserInfo.setPhone("15957178759");
        bizUserInfo.setRectip(BizConstants.MARK_NO);

        bizUserInfo.setPhonetype(BizConstants.USER_PHONETYPE_CELL_PHONE);
        bizUserInfo.setIntime(new Date());
        bizUserInfo.setInsource("2");
        bizUserInfo.setCalledflag("1");
        bizUserInfo.setCallerRecord("0");
        bizUserInfo.setIsrefund("1");
        bizUserInfo.setFax("2SDFKLJ");
        bizUserInfo.setRemark("test DTS by mif");
        bizUserInfo.setBusinesstype("DX");
        bizUserInfo.setCallerVoice("1");
        bizUserInfo.setCalledRecord("0");

        dxProvincePersonalUserService.UpdatePersonalUser(bizUserInfo);
    }
}