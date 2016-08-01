package com.ancun.boss.business.controller.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizUserInfoInput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoUpdateInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizUserInfoUpdateControllerTest extends BaseAPi {

    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:8082/ancun-boss";
        this.accessId = "7d08afcf56bc860fb2154fb0298bb825";
        this.accessKey = "NjE5ODIyOGE3NjU0ZWMwYTljZDJlY2ZjM2UxMWMzODE=";
    }

    @Test
    public void testBizUpdateUser() throws Exception {
        BizUserInfoUpdateInput infoUpdateInput = new BizUserInfoUpdateInput();
        infoUpdateInput.setAccessid(accessId);
        infoUpdateInput.setAccesskey(accessKey);
        infoUpdateInput.setUserno("mif1");

        infoUpdateInput.setUserNum("13124775689");
        infoUpdateInput.setTcId(40l);
        infoUpdateInput.setBizNo("LT-BEIJING");
        infoUpdateInput.setUserType("1");
        infoUpdateInput.setInsource("1");
        infoUpdateInput.setSmsLogin("1");
        infoUpdateInput.setStatus("1");
        infoUpdateInput.setTcType("3");

//        BizPersonInfo personInfo = new BizPersonInfo();

//        infoUpdateInput.setBizPersonInfo(personInfo);


//        BizEntInfo bizEntInfo = new BizEntInfo();

//        infoUpdateInput.setBizEntInfo(bizEntInfo);

        super.responseJSON("bizUpdateUser", infoUpdateInput);
    }

    @Test
    public void testBizUserICleanPwd() throws Exception {
        BizUserInfoInput infoInput = new BizUserInfoInput();
        infoInput.setAccessid(accessId);
        infoInput.setAccesskey(accessKey);
        infoInput.setUserno("mif1");

        infoInput.setUserNum("15957178759");
        infoInput.setBizNo("DX-GUANGXI");

        super.responseJSON("bizUserCleanPwd", infoInput);
    }
}