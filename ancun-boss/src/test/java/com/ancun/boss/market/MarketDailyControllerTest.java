package com.ancun.boss.market;

import com.ancun.boss.persistence.model.MarketDaily;
import com.ancun.boss.pojo.market.ImportMarketDaily;
import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2015/10/27.
 */
public class MarketDailyControllerTest extends BaseAPi {

    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:8080/ancun-boss";
        this.accessId = "1a68f027825606cd3d8aafdc7167dabe";
        this.accessKey = "MTI4NDZlN2VmMDRmOTUwZDc5YmE0MmMwZmY3OTc0NGE=";
    }

    @Test
    public void testQueryMarketDailyList() throws Exception {
        MarketDailyInput input = new MarketDailyInput();
        input.setBusiness("DX-001");
        input.setCalledTeam("WHTD-001");
        input.setDateDailyb("2015-10-15");
        input.setDateDailye("2015-10-20");
        input.setUserno("9");
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        super.responseJSON("queryMarketDailyList",input);

    }

//    @Test
    public void testQueryMarketDailyInfo() throws Exception {
        MarketDaily marketDaily = new MarketDaily();
        marketDaily.setId(1l);
        marketDaily.setUserno("9");
        marketDaily.setAccessid(accessId);
        marketDaily.setAccesskey(accessKey);
        super.responseJSON("queryMarketDailyInfo",marketDaily);
    }

//    @Test
    public void testAddOrUpdateMarketDaily() throws Exception {
        MarketDaily marketDaily = new MarketDaily();
        marketDaily.setBusiness("DX-SHANGHAI");
        marketDaily.setCalledTeam("WHTD-001");
        marketDaily.setDateDaily("2015-10-27");
        marketDaily.setCalledNumber("2000");
        marketDaily.setConnectNumber("2000");
        marketDaily.setSuccessDaily("1000");
        marketDaily.setSeatsNumber("10");
        marketDaily.setPhoneSource("KJLFIS-KJLDSF");

        marketDaily.setAccessid(accessId);
        marketDaily.setAccesskey(accessKey);
        super.responseJSON("addOrUpdateMarketDaily",marketDaily);
    }


//    @Test
    public void testImportMarketDaily() throws Exception {
        ImportMarketDaily importMarketDaily = new ImportMarketDaily();

        importMarketDaily.setAccessid(accessId);
        importMarketDaily.setAccesskey(accessKey);
        super.responseJSON("addOrUpdateMarketDaily", importMarketDaily);
    }
}