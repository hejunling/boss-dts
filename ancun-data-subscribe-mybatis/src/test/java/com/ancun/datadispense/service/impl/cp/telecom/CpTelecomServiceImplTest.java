package com.ancun.datadispense.service.impl.cp.telecom;

import com.ancun.Application;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.cp.telecom.ICpTelecomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
/**
 * 
 *
 * @Created on 2016年4月12日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class CpTelecomServiceImplTest {

    @Resource
    private ICpTelecomService cpTelecomService;

    @Test
    public void testOpenUser() throws Exception {
//        BizUserInfo bizUserInfo = new BizUserInfo();
//        bizUserInfo.setUserNo("15957178759");
//        bizUserInfo.setPasswd("h0GDwSQVx45YqI7DUCTLu4ZHAMzt6iA7EJllEa5urb6mxJIyt0ofXA==");
//        bizUserInfo.setUserType(BizConstants.USER_TYPE_PERSONAL);
//        bizUserInfo.setTcid(3l);
//        bizUserInfo.setStatus(BizConstants.USERSTATUS_NORMAL);
//        bizUserInfo.setRpcode("350000");
//        bizUserInfo.setCityCode("22000");
//        bizUserInfo.setPhone("15957178759");
//        bizUserInfo.setRectipflag(BizConstants.MARK_NO);
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
//
//        cpTelecomService.openUser(bizUserInfo);

    }

    @Test
    public void testUpdateUser() throws Exception {
        BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setUserNo("15957178759");
        bizUserInfo.setPasswd("h0GDwSQVx45YqI7DUCTLu4ZHAMzt6iA7EJllEa5urb6mxJIyt0ofXA==");
        bizUserInfo.setUserType(BizConstants.USER_TYPE_PERSONAL);
        bizUserInfo.setTcid(3l);
        bizUserInfo.setStatus(BizConstants.USERSTATUS_CANCEL);
        bizUserInfo.setRpcode("350000");
        bizUserInfo.setCityCode("22000");
        bizUserInfo.setPhone("15957178759");
        bizUserInfo.setCallerVoice(BizConstants.MARK_NO);

        bizUserInfo.setPhonetype(BizConstants.USER_PHONETYPE_CELL_PHONE);
        bizUserInfo.setIntime(new Date());
        bizUserInfo.setInsource("2");
        bizUserInfo.setCalledflag("1");
        bizUserInfo.setCallerRecord("0");
        bizUserInfo.setIsrefund("1");
        bizUserInfo.setFax("2");
        bizUserInfo.setRemark("test DTS by mif");
        bizUserInfo.setBusinesstype("DX");
        bizUserInfo.setCallerVoice("1");
        bizUserInfo.setCalledRecord("0");

        cpTelecomService.updateUser(bizUserInfo);
    }
}