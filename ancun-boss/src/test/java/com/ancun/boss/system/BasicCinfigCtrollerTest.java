package com.ancun.boss.system;

import com.ancun.boss.pojo.system.BasicConfigInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2015/9/29.
 */
public class BasicCinfigCtrollerTest extends BaseAPi {
    @Before
    public void setUp() throws Exception {
        this.url = "http://127.0.0.1:8080/ancun-boss";
        this.accessId = "ce69a1c0d3285a46d16beb7cb81049db";
        this.accessKey = "YjdmNzU1MDZhMTMwMzNiMzA0YzY0ZWRiYTAxMjZjNDc=";
    }
    
    @Test
    public void testQueryListByModuleCode() throws Exception {
        BasicConfigInput input = new BasicConfigInput();
        input.setMoudlecode("BIZ-NAME");
//        input.setMoudlecode("ORG");
//        input.setName("S");
        input.setName("营");
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        super.responseJSON("queryListByMoudleCode", input);
    }
   
    @Test
    public void testQueryBasicConfigList() throws Exception {
        BasicConfigInput input = new BasicConfigInput();

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setCurrentpage("1");
        input.setMoudlecode("SEX");

        super.responseJSON("queryBasicConfigList", input);
    }

    @Test
    public void testQueryBasicConfigTypes() throws Exception {
        super.responseJSON("queryBasicConfigTypes", null);
    }



    @Test
    public void testUpdateSystemBasicConfigName() throws Exception {
        BasicConfigInput basicConfigInput = new BasicConfigInput();

        basicConfigInput.setAccessid(accessId);
        basicConfigInput.setAccesskey(accessKey);
        basicConfigInput.setBasicno(25);
        basicConfigInput.setName("运营商业务");

        super.responseJSON("updateSystemBasicConfigName", basicConfigInput);
    }
}