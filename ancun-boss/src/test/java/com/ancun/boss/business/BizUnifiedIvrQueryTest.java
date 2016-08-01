package com.ancun.boss.business;

import com.ancun.boss.business.pojo.ivr.BizUnifiedIvrQueryInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * IVR统一查询
 *  */
public class BizUnifiedIvrQueryTest extends BaseAPi {
    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:8080/ancun-boss";
        this.accessId = "b406a8f0f1dae854691fb28226c1c527";
        this.accessKey = "MWRiZjM5ZTg2YWFiYmViOGU3NTNhNWVhNjkzOTk3Mjc=";
    }


    @Test
    public void run() throws Exception {
    	BizUnifiedIvrQueryInput input = new  BizUnifiedIvrQueryInput();

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setOwnerno("13616810257");
        input.setUserno("13616810257");
        //input.setRequestType("2");
        input.setProvinceCode("350000");
        input.setType_code("0");
        input.setBizNo("DX-GUANGXI");
        super.responseJSON("ylcnuserinfoGet", input);
    }
}


