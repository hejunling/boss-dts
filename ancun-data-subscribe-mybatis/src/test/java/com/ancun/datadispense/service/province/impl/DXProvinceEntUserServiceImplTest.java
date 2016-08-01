package com.ancun.datadispense.service.province.impl;

import com.ancun.Application;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.province.DXProvinceEntUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/3/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class DXProvinceEntUserServiceImplTest {

    @Resource
    private DXProvinceEntUserService dxProvinceEntUserService;

    @Test
    public void openEntUser() throws Exception {
        BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setEntNo("15957178759");
        bizUserInfo.setPasswd("h0GDwSQVx45YqI7DUCTLu4ZHAMzt6iA7EJllEa5urb6mxJIyt0ofXA==");
        bizUserInfo.setUserNo("15957178753");
        bizUserInfo.setAccountType(BizConstants.ACCOUNTTYPE_SUB);
        bizUserInfo.setUserType(BizConstants.USER_TYPE_ENTERPRISES);
        bizUserInfo.setTcid(3l);
        bizUserInfo.setStatus(BizConstants.USERSTATUS_NORMAL);
        bizUserInfo.setRpcode("450000");
        bizUserInfo.setCityCode("22000");
        bizUserInfo.setPhone("15957178753");
//        bizUserInfo.setRectip(BizConstants.MARK_NO);
//
//        bizUserInfo.setPhonetype(BizConstants.USER_PHONETYPE_CELL_PHONE);
//        bizUserInfo.setIntime(new Date());
//        bizUserInfo.setInsource("2");
//        bizUserInfo.setCalledflag("1");
//        bizUserInfo.setCallerRecord("0");
//        bizUserInfo.setIsrefund("1");
//        bizUserInfo.setRefundremark("remark Test ");
//        bizUserInfo.setFax("2");
//        bizUserInfo.setRemark("XXXXXXXXXXXXXXXXXX");
//        bizUserInfo.setBusinesstype("DX");
//        bizUserInfo.setCallerVoice("1");
//        bizUserInfo.setCalledRecord("0");
        dxProvinceEntUserService.openEntUser(bizUserInfo);

    }

    @Test
    public void updateEntUser() throws Exception {
        BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setEntNo("15957178759");
        bizUserInfo.setPasswd("h0GDwSQVx45YqI7DUCTLu4ZHAMzt6iA7EJllEa5urb6mxJIyt0ofXA==");
        bizUserInfo.setUserNo("15957178759");
        bizUserInfo.setAccountType(BizConstants.ACCOUNTTYPE_MAIN);
        bizUserInfo.setUserType(BizConstants.USER_TYPE_ENTERPRISES);
        bizUserInfo.setTcid(3l);
        bizUserInfo.setStatus(BizConstants.USERSTATUS_NORMAL);
//        bizUserInfo.setRpcode("450000");
//        bizUserInfo.setCityCode("22000");
//        bizUserInfo.setPhone("15957178759");
//        bizUserInfo.setRectip(BizConstants.MARK_NO);
//
        bizUserInfo.setPhonetype(BizConstants.USER_PHONETYPE_CELL_PHONE);
        bizUserInfo.setIntime(new Date());
        bizUserInfo.setInsource("2");
//        bizUserInfo.setCalledflag("1");
//        bizUserInfo.setCallerRecord("1");
//        bizUserInfo.setIsrefund("1");
//        bizUserInfo.setRefundremark("remark Test by 87113");
//        bizUserInfo.setFax("2");
//        bizUserInfo.setRemark("Remark");
//        bizUserInfo.setBusinesstype("DX");
//        bizUserInfo.setCallerVoice("1");
//        bizUserInfo.setCalledRecord("0");
        bizUserInfo.setOperation(BizConstants.MARK_YES);

        dxProvinceEntUserService.updateEntUser(bizUserInfo);
    }


}