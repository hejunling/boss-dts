package com.ancun.boss.controller.statistics;

import org.junit.Before;
import org.junit.Test;

import com.ancun.boss.pojo.statistics.StatisticsProfitCostInput;
import com.ancun.test.api.BaseAPi;

public class ProfitCostControllerTest extends BaseAPi {
	 @Before
    public void setUp() throws Exception {
        this.url = "http://127.0.0.1:8080/ancun-boss";
        this.accessId = "ce69a1c0d3285a46d16beb7cb81049db";
        this.accessKey = "YjdmNzU1MDZhMTMwMzNiMzA0YzY0ZWRiYTAxMjZjNDc=";
    }
    @Test
    public void testStatistics() throws Exception {
    	StatisticsProfitCostInput input = new StatisticsProfitCostInput();
        input.setBusiness("DX-SHANGHAI");
        input.setCalledteam("WHTD-001");
        input.setStarttime("2015-10");;
        input.setEndtime("2015-11");


        super.responseJSON("statisticsProfitCost", input);
    }
}
