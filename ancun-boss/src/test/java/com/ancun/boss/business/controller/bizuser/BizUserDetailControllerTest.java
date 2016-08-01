package com.ancun.boss.business.controller.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizUserInfoInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/19
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizUserDetailControllerTest extends BaseAPi {
    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:8082/ancun-boss";
        this.accessId = "9f2cbc1c216e52d6bf09c424cb75e128";
        this.accessKey = "ZGY1MjAxOWU1NjgwZDM0NjJiYjUyYWI1YTI4YTFkZTA=";
    }


    @Test
    public void testBizUserDetail() throws Exception {
        BizUserInfoInput input = new BizUserInfoInput();
        input.setUserno("1111");

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setUserNum("15957178759");
//        input.setBizNo("DX-GUANGXI");

        super.responseXML("bizUserDetail", input);

    }

    @Test
    public void testBizUserLifeCircle() throws Exception {
        BizUserInfoInput input = new BizUserInfoInput();
        input.setUserno("1111");

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setUserNum("18868807345");
        input.setBizNo("DX-SHANGHAI");
        input.setEntNo("");
        input.setUserType("");

        super.responseXML("bizUserLifeCircle", input);

    }
}