package com.ancun.boss.custmer;

import org.junit.Before;
import org.junit.Test;

import com.ancun.boss.pojo.callInfo.CallInRecordInput;
import com.ancun.test.api.BaseAPi;

/**
 * 呼入登记管理测试
 *
 * @Created on 2015年10月13日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CallInRecordControllerTest extends BaseAPi{
	  @Before
	    public void setUp() throws Exception {
	        this.url = "http://127.0.0.1:8080/ancun-boss";
	        this.accessId = "ce69a1c0d3285a46d16beb7cb81049db";
	        this.accessKey = "YjdmNzU1MDZhMTMwMzNiMzA0YzY0ZWRiYTAxMjZjNDc=";
	    }

	    @Test
	    public void testQueryListByModuleCode() throws Exception {
	    	CallInRecordInput input = new CallInRecordInput();

	        input.setAccessid(accessId);
	        input.setAccesskey(accessKey);
	        input.setBusiness("上海音证宝");
	        input.setCallback("1");
	        input.setCallphone("18888887777");
	        input.setCallTime("2015/09/29 09:09");
	        input.setName("11111");
	        input.setSex("1");
	        input.setModifyflag("1");
	        input.setUserno("001");
	        super.responseJSON("addOrUpdateCallinInfo", input);
	    }
}
