package com.ancun.boss.callquality;

import com.ancun.boss.pojo.callQualityInfo.CallQualityStatisticsInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * 呼入质检测试类
 * Created by zkai on 2015/12/14.
 */
public class callQualityTest extends BaseAPi {
    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:8099";
        this.accessId = "ce69a1c0d3285a46d16beb7cb81049db";
        this.accessKey = "YjdmNzU1MDZhMTMwMzNiMzA0YzY0ZWRiYTAxMjZjNDc=";
    }


    @Test
    public void run() throws Exception {
        CallQualityStatisticsInput input = new CallQualityStatisticsInput();

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setExportType("1");
        input.setUserno("001");
        super.responseJSON("callQualityInfoExport", input);
    }

//    @Test
//    public void run() throws Exception {
//        CallQualityListInput input = new CallQualityListInput();
//
//        input.setAccessid(accessId);
//        input.setAccesskey(accessKey);
//        input.setBeginCheckTime("2015-01-01");
//        input.setEndCheckTime("2015-12-31");
//        super.responseJSON("selectSerialCallQualityInfo", input);
//    }
}
