package com.ancun.boss.system;

import com.ancun.boss.persistence.model.SystemUserInfo;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * 登录测试
 * @author hjl
 *
 * @时间 2015-9-24 上午9:35:32
 */
public class LogInControllerTest extends BaseAPi {

	@Before
	public void setUp() {
		this.url = "http://127.0.0.1:8082/ancun-boss";
		this.accessId = "ce69a1c0d3285a46d16beb7cb81049db";
		this.accessKey = "YjdmNzU1MDZhMTMwMzNiMzA0YzY0ZWRiYTAxMjZjNDc=";
	}
	
	@Test
	public void tesLogin() {
		SystemUserInfo input = new SystemUserInfo();
		input.setUserno("mif1");
		input.setPwd("123456");
		super.responseJSON("login", input);
	}
}
