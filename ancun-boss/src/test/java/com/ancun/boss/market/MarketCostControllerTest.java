package com.ancun.boss.market;

import org.junit.Test;

import com.ancun.boss.pojo.marketInfo.QueryCostCalcListInput;
import com.ancun.test.api.BaseAPi;

/**
 * 营销成本管理单元测试用例
 *
 * @Created on 2015年10月12日
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class MarketCostControllerTest extends BaseAPi {
    
    @Test
    public void queryCostCalcList() throws Exception {
    	QueryCostCalcListInput input = new QueryCostCalcListInput();

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setPagesize("10");
        input.setCurrentpage("0");
        super.responseJSON("queryCostCalcList", input);
    }
}
