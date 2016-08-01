package com.ancun.boss.bizcanceluser;

import com.ancun.boss.business.pojo.bizcanceluser.BizBatchCancelUserInput;
import com.ancun.boss.business.pojo.bizcanceluser.CancelUserInfo;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @Created on 2016年3月16日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class bizCancelUserTest extends BaseAPi {
    @Before
    public void setUp() {
    	this.url = "http://127.0.0.1:8080/ancun-boss";
        this.accessId = "ce69a1c0d3285a46d16beb7cb81049db";
        this.accessKey = "YjdmNzU1MDZhMTMwMzNiMzA0YzY0ZWRiYTAxMjZjNDc=";
    }


    /**
     * 单个退订
     * @throws Exception
     */
//    @Test
//    public void run() throws Exception {
//    	BizCancelUserInput input = new BizCancelUserInput();
//
//        input.setAccessid(accessId);
//        input.setAccesskey(accessKey);
//        input.setProvinceCode("310000");
//        input.setUserno("001");
//        input.setBizuserno("02162184872");
//        input.setUsertype("2");
//        input.setAccounttype("2");
//        input.setEntno("123456");
//        input.setBizno("DX-GUANGXI");
//        input.setIsrefunds(false);
//        input.setRefundsmoney(0);
//        input.setDescription("55454546465");
//        input.setType("DX");
//        super.responseJSON("bizUnifiedCancelUser", input);
//    }
    
    /**
     * 批量退订
     * @throws Exception
     */
    @Test
    public void run() throws Exception {
    	BizBatchCancelUserInput input = new BizBatchCancelUserInput();
    	
    	input.setAccessid(accessId);
    	input.setAccesskey(accessKey);
    	input.setProvinceCode("310000");
    	input.setUserno("001");
    	input.setBizno("DX-GUANGXI");
    	input.setType("DX");
    	
    	CancelUserInfo info1 = new CancelUserInfo();
    	info1.setBizuserno("15957178756");
    	info1.setUsertype("1");
    	info1.setAccounttype("");
//    	info1.setEntno("123456");
    	info1.setIsrefunds(false);
    	info1.setRefundsmoney(0);
    	info1.setDescription("55454546465");
    	
    	CancelUserInfo info2 = new CancelUserInfo();
    	info2.setBizuserno("02162184872");
    	info2.setUsertype("2");
    	info2.setAccounttype("2");
    	info2.setEntno("123456");
    	info2.setIsrefunds(false);
    	info2.setRefundsmoney(0);
    	info2.setDescription("55454546465");
    	
    	CancelUserInfo info3 = new CancelUserInfo();
    	info3.setBizuserno("15957178759");
    	info3.setUsertype("1");
    	info3.setAccounttype("2");
    	info3.setEntno("15957178759");
    	info3.setIsrefunds(false);
    	info3.setRefundsmoney(0);
    	info3.setDescription("55454546465");
    	
    	List<CancelUserInfo> list = new ArrayList<CancelUserInfo>();
    	list.add(info1);
    	list.add(info2);
    	list.add(info3);
    	input.setCanceluserlist(list);
    	
    	super.responseJSON("bizUnifiedBatchCancelUser", input);
    }

}
