package com.ancun.boss.cancelRequireCharts;

import org.junit.Before;
import org.junit.Test;

import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsInput;
import com.ancun.test.api.BaseAPi;

/**
 * 
 *
 * @Created on 2015年11月10日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CancelRequireChartsTest extends BaseAPi{
	
	 @Before
	    public void setUp() throws Exception {
	        this.url = "http://127.0.0.1:8080/ancun-boss";
	        this.accessId = "1a68f027825606cd3d8aafdc7167dabe";
	        this.accessKey = "MTI4NDZlN2VmMDRmOTUwZDc5YmE0MmMwZmY3OTc0NGE=";
	    }

	    @Test
	    public void testCancelRequireCharts() throws Exception {
	    	CancelRequireChartsInput input = new CancelRequireChartsInput();

	        input.setAccessid(accessId);
	        input.setAccesskey(accessKey);
	        input.setBusiness("TD-001");
	        input.setUserno("9");
//	        input.setCallback("1");
//	        input.setCallphone("18888887777");
//	        input.setCallTime("2015/09/29 09:09");
	        super.responseJSON("cancelRequireChartsList", input);
	    }
}
