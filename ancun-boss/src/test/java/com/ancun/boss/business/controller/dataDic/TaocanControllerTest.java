package com.ancun.boss.business.controller.dataDic;

import com.ancun.boss.business.pojo.taocanInfo.TaocanDetailedInput;
import com.ancun.boss.business.pojo.taocanInfo.TaocanInfoInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: zkai
 * Date: 2016/5/5
 * Time: 11:17
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

public class TaocanControllerTest extends BaseAPi {

    @Before
    public void setUp() throws Exception {
        this.url = "http://localhost:8087";
        this.accessId = "6838a23c7a91e1462a4a3f523aab634e";
        this.accessKey = "ZGQ2NjVjZGM4NzdlNGJlNzczZjIyM2U1OGYyNGNjZWE=";
    }

    @Test
    public void testMgrGetTcList() throws Exception {

    }

    @Test
    public void testMgrGetTcInfo() throws Exception {
        TaocanDetailedInput input = new TaocanDetailedInput();

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setUserno("103");
        input.setTcid(3L);
        input.setBizno("DX-FUJIANG");
        super.responseJSON("mgrGetTcInfo", input);
    }

    @Test
    public void testMgrAddTcInfo() throws Exception {

    }

    @Test
    public void testMgrSaveTcInfo() throws Exception {
        TaocanInfoInput input = new TaocanInfoInput();
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setUserno("103");
        input.setTcid(3L);
        input.setTaocanname("10元接入号录音");
        input.setOldbizno("DX-SHANG");
        input.setBizno("DX-SHANG");
        input.setTcduration("1000");
        input.setStorespace("1000");
        input.setTaocanprice("10");
        input.setTaocanremark("10块");
        input.setTaocantype("1");
        input.setTcflag("1");
        super.responseJSON("mgrUpdateTcInfo", input);
    }

    @Test
    public void testMgrDeleteTc() throws Exception {

    }

    @Test
    public void testGetTaocanByBizNo() throws Exception {

    }
}