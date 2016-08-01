package com.ancun.boss.system;

import org.junit.Before;
import org.junit.Test;

import com.ancun.boss.persistence.model.SystemMenuInfo;
import com.ancun.test.api.BaseAPi;

public class SystemMenuInfoControllerTest extends BaseAPi {
	
	@Before
    public void setUp() throws Exception {
        this.url = "http://127.0.0.1:8080/ancun-boss";
        this.accessId = "ce69a1c0d3285a46d16beb7cb81049db";
        this.accessKey = "YjdmNzU1MDZhMTMwMzNiMzA0YzY0ZWRiYTAxMjZjNDc=";
    }
    
    @Test
    public void testSelectPoplarSystemMenuInfo() throws Exception {
    	SystemMenuInfo input = new SystemMenuInfo();
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        super.responseJSON("selectPoplarSystemMenuInfo", input);
    }
    
    @Test
    public void testSelectSingleSystemMenuInfo() throws Exception {
    	SystemMenuInfo input = new SystemMenuInfo();    	
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setMenuno("02a4a6fa-76da-46c5-b230-3a3e6141c966");
        super.responseJSON("selectSingleSystemMenuInfo", input);
    }
    
    @Test
    public void testGatherSystemMenuInfo() throws Exception {
    	SystemMenuInfo input = new SystemMenuInfo();
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setMenuname("数据字典管理");
        input.setMenuurl("systemBasicConfig");
        input.setRemark("数据字典管理");
       
        //有ID就是修改，无ID就是新增
        //input.setMenuno("02a4a6fa-76da-46c5-b230-3a3e6141c966");
        //有PID就是2级以上节点，无PID就是一级节点
        //input.setPmenuinfo("4b46b81b-e9be-42e1-aa8c-c2b3bfabc4a3");
        super.responseJSON("gatherSystemMenuInfo", input);
    }
    
    @Test
    public void testDeleteSystemMenuInf() throws Exception {
    	SystemMenuInfo input = new SystemMenuInfo();
    	input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setMenuno("02a4a6fa-76da-46c5-b230-3a3e6141c966");
        super.responseJSON("deleteSystemMenuInf", input);
    }


}
