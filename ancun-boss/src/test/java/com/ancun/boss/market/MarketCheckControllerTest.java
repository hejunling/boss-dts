package com.ancun.boss.market;

import com.ancun.http.enums.ContentType;
import com.ancun.test.api.BaseAPi;
import com.ancun.utils.MD5Utils;
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
public class MarketCheckControllerTest extends BaseAPi {

    /**
     * 正常Case
     */
    @Test
    public void marketCheckVoiceImport() throws UnsupportedEncodingException {

        url = "http://127.0.0.1:8080/";
        this.accessId = "1a68f027825606cd3d8aafdc7167dabe";
        this.accessKey = "MTI4NDZlN2VmMDRmOTUwZDc5YmE0MmMwZmY3OTc0NGE=";

        Map<String, String> paramMap = new HashMap<String, String>();
        String filePath = "E:\\boss\\营销录音.zip";
        String md5 = MD5Utils.md5file(filePath);
        paramMap.put("business","test");
        paramMap.put("calledTeam", "3");
        paramMap.put("marketTime", "2015-09-25 9:57:35");
        paramMap.put("fileUpload", "1");
        paramMap.put("phoneStartPos", "0");
        paramMap.put("fileMd5", md5);
        paramMap.put("filename", URLEncoder.encode("营销录音", "UTF-8"));

        System.out.println(doUpFileService("marketCheckVoiceImport", ContentType.JSON, paramMap, filePath));
    }

}
