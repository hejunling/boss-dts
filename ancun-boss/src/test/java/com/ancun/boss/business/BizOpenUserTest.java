package com.ancun.boss.business;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserInput;
import com.ancun.test.api.BaseAPi;

public class BizOpenUserTest extends BaseAPi {
    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:9080/ancun-boss";
        this.accessId = "78d2569fc87f9ae2f7ffef605e3dee67";
        this.accessKey = "OTcwOGIwNDNmNmQ2OTI1NGIwYzQ3ODI1OTBjYzBhZGQ=";
    }


    @Test
    public void run() throws Exception {
    	OpenBizUserInput input = new  OpenBizUserInput();
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setBizNo("DX-001");
        input.setBizuserno("13616810257");
        input.setIsignupsource("1");
        input.setPhone("13616810257");
        input.setTaocanid(1l);
        input.setUserno("103");
        input.setUserType("1");
        input.setRpcode("510000");
        input.setType_code("DX");
        input.setOpendatetime(new Date());
        
        super.responseJSON("addBizUser", input);
    }

}
