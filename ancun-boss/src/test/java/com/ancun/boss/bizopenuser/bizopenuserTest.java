package com.ancun.boss.bizopenuser;

import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserBatchInput;
import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserInfo;
import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class bizopenuserTest extends BaseAPi{
	
    @Before
    public void setUp() {
    	this.url = "http://localhost:9080/ancun-boss";
        this.accessId = "468565bb96a87d8a34cbe35494cc3f47";
        this.accessKey = "N2U5MTY3MzgxMDQ5Y2JhMzcwY2U5YzM4Njc3MDJlMzk=";
    }
    
	
	@Test
	public void openUser() throws Exception{
		OpenBizUserInput input = new OpenBizUserInput();
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setUserno("mif1");
		input.setBizuserno("13564865859");
		input.setUserType("1");
		input.setBizNo("LT-BEIJING");
		input.setRpcode("110000");
		input.setTaocanid(3l);
		input.setIsignupsource("web");
		input.setType_code("LT");
		input.setOpendatetime(new Date());
		
		super.responseJSON("addBizUser", input);
		
	}
	@Test
	public void openUserCPUNION() throws Exception{
		OpenBizUserInput input = new OpenBizUserInput();
		input.setAccessid(accessId);
		input.setAccesskey(accessKey);
		input.setUserno("mif1");
		input.setBizuserno("13564863323");
		input.setUserType("1");
		input.setBizNo("CP-LT");
		input.setRpcode("000000");
		input.setTaocanid(3l);
		input.setIsignupsource("web");
		input.setType_code("LT");
		input.setOpendatetime(new Date());
		
		super.responseJSON("addBizUser", input);
		
	}
	
	@Test
	public void openUserBatch() throws Exception{
		OpenBizUserBatchInput input = new OpenBizUserBatchInput();
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setUserno("0138");
        
        input.setBizno("DX-SHANGHAI");
        input.setRpcode("510000");
        input.setIsignupsource("1");
        input.setType_code("DX");
        input.setTaocanid(3l);
        input.setPhoneType("1");
        
        
        OpenBizUserInfo user1 = new OpenBizUserInfo();
        user1.setBizuserno("");
        user1.setOpendatetime(new Date());
		
		OpenBizUserInfo user2 = new OpenBizUserInfo();
		user2.setBizuserno("13567140550");
		user2.setOpendatetime(new Date());
		
		OpenBizUserInfo user3 = new OpenBizUserInfo();
		user3.setBizuserno("13567140551");
		user3.setOpendatetime(new Date());
		
		List<OpenBizUserInfo> list = new ArrayList<OpenBizUserInfo>();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		input.setUserlist(list);
		
		super.responseJSON("addBizUserBatch", input);
		
	}
}
