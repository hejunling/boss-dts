package com.ancun.boss.system;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.pojo.userInfo.UserInfoListInput;
import org.junit.Before;
import org.junit.Test;

import com.ancun.boss.pojo.userInfo.UserInfoInput;
import com.ancun.test.api.BaseAPi;

/**
 * 用户信息测试
 * @author hjl
 *
 * @时间 2015-9-24 上午9:22:32
 */
public class UserInfoControllerTest extends BaseAPi {
	
	@Before
	public void setUp() {
		this.url = "http://127.0.0.1:8080/ancun-boss";
		this.accessId = "1a68f027825606cd3d8aafdc7167dabe";
		this.accessKey = "MTI4NDZlN2VmMDRmOTUwZDc5YmE0MmMwZmY3OTc0NGE=";
	}
	
	@Test
	public void tesQueryUserList() {
		UserInfoListInput input = new UserInfoListInput();
		input.setUserno("9");
//		input.setUsername("1");
//		input.setJobno("0103");
//		input.setJob("程");
		input.setOrgno("123");

		input.setPagesize("10");
		input.setCurrentpage("1");
		input.setAccessid(accessId);
		input.setAccesskey(accessKey);
		super.responseJSON("queryUserList", input);
	}


	@Test
	public void testQueryUserInfo() throws Exception {
		UserInfoListInput input = new UserInfoListInput();
		input.setJobno("9");
		input.setAccessid(accessId);
		input.setAccesskey(accessKey);
		super.responseJSON("queryUserInfo", input);
	}

	@Test
	public void testAddOrUpdateUserInfo() throws Exception {
		UserInfoInput input = new UserInfoInput();

		input.setJobno("mino_test_02");
		input.setUsername("mif_test_02");
		input.setOrgno("3");
		input.setContactphone("18943851426");
		input.setEmail("mif_test_02@sina.com");
		input.setModifyflag(BizRequestConstant.TO_ADD);


		input.setAccessid(accessId);
		input.setAccesskey(accessKey);
		super.responseJSON("addOrUpdateUserInfo", input);

	}

	@Test
	public void testDeleteUserInfo() throws Exception {
		UserInfoListInput input = new UserInfoListInput();
		input.setJobno("mino_test_01");

		input.setAccessid(accessId);
		input.setAccesskey(accessKey);
		super.responseJSON("deleteUserInfo", input);

	}

	@Test
	public void testUserSelectList() throws Exception {

	}

}
