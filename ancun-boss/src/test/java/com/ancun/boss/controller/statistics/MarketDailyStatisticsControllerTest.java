package com.ancun.boss.controller.statistics;

import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mif on 2015/11/3.
 */
public class MarketDailyStatisticsControllerTest extends BaseAPi {

    @Before
    public void setUp() throws Exception {
        this.url = "http://127.0.0.1:8080/ancun-boss";
        this.accessId = "ce69a1c0d3285a46d16beb7cb81049db";
        this.accessKey = "YjdmNzU1MDZhMTMwMzNiMzA0YzY0ZWRiYTAxMjZjNDc=";
    }

    @Test
    public void testStatistics() throws Exception {
        MarketDailyInput input = new MarketDailyInput();
        input.setBusiness("DX-001");
//        input.setCalledTeam("WHTD-001");
        input.setDateDailyb("2015-11-02");
        input.setDateDailye("2015-11-02");

        input.setPagesize("10");
        input.setCurrentpage("0");

        super.responseJSON("marketDailyStatistics", input);
    }

    @Test
    public void statisticsDetail() throws Exception {
        MarketDailyInput input = new MarketDailyInput();
        input.setBusiness("DX-SHANGHAI");
//        input.setCalledTeam("WHTD-001");
        input.setDateDailyb("2015-11-02");
        input.setDateDailye("2015-11-02");

        super.responseJSON("marketDailyStatisticsDetail", input);
    }

}