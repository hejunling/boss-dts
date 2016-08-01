package com.ancun.boss.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizEntAddSubAccountInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class BizEntSubAccountTest extends BaseAPi{
	
    @Before
    public void setUp() {
    	this.url = "http://localhost:8087";
        this.accessId = "f1ceb171680bbf1be30e54c2d442fcb4";
        this.accessKey = "YzkzYjg5MWNjZjExMWY3MzQwNjU2ZDE2MzI4Zjk2YmI=";
    }
    
	
	@Test
	public void addEntSubAccount() throws Exception{
		BizEntAddSubAccountInput input = new BizEntAddSubAccountInput();
        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        input.setUserno("103");
		input.setAccounts("18100800203,18100800204,18100800205");
		input.setBizno("DX-CHENGDU");
		input.setRpcode("510000");
		input.setTaocanid(53L);
		input.setTypecode("DX");
		input.setEntno("18100800201");
//		input.setCallervoice("1");
//		input.setCalledvoice("1");
		input.setOpendatetime(new Date());
		
		super.responseJSON("bizEntAddSubAccount", input);
		
	}
}
