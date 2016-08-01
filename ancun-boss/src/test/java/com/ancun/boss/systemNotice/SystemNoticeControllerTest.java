package com.ancun.boss.systemNotice;

import com.ancun.boss.pojo.callInfo.CallInRecordInput;
import com.ancun.boss.pojo.systemNotice.SystemNoticeListInput;
import com.ancun.http.enums.ContentType;
import com.ancun.test.api.BaseAPi;
import com.ancun.utils.MD5Utils;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户录音上传单元测试用例
 *
 * @Created on 2015年9月25日
 * @author xieyushi
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class SystemNoticeControllerTest extends BaseAPi {

    @Before
    public void setUp() throws Exception {
        this.url = "http://127.0.0.1:8080";
        this.accessId = "48bf3d3500e2e489f471091000f2894f";
        this.accessKey = "NzRlNDE3MDg1ZmQ2MWJlZDQyNzc0ODkyZDBkMjRhYjQ=";
    }

    @Test
    public void run() throws Exception {
        SystemNoticeListInput input = new SystemNoticeListInput();

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setUserno("001");
        super.responseJSON("getSystemNoticeList", input);
    }
}
