package com.ancun.boss.authenty;

import org.junit.Before;
import org.junit.Test;

import com.ancun.boss.pojo.callincharts.TestAuthInput;
import com.ancun.test.api.BaseAPi;

/**
 * 号码鉴权测试类
 * @author 静好
 *
 */
public class AuthentyTest extends BaseAPi{
	
//	public static void main(String[] args) throws EduException{
		
//		AuthenticationUtil au = new AuthenticationUtil();
		//第一种测试情况,电话号码为空,省份不为上海(测试结果:抛"电话号码不能为空"异常)
//		AuthenticationUtil.validCheck("", "350000");
		//第二种测试情况,电话号码格式非法,不是手机或固话格式,省份不为上海(测试结果:抛"电话号码格式非法"异常)
		//0218545412,05785412411,057163509449,02185454125
//		AuthenticationUtil.validCheck("057163509449", "350000");//1875827903,137582790,147582790,1575827,17758279,1875827903,11758279038,12758279038,16758279038,19758279038
		//第三种测试情况,号码格式正确,但是不是鉴权白名单,省份不为上海(测试结果:)
//		au.validCheck("18758279038", "350000");
		//第四种测试情况,号码格式正确,但是是鉴权白名单,省份不为上海(测试结果:)
		
		//第五种测试情况,号码格式正确,但是不是鉴权白名单,用户省份和请求省份一致(测试结果:)
		
		//第六种测试情况,号码格式正确,但是不是鉴权白名单,用户省份和请求省份不一致(测试结果:)	
//	}
	
//}

    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:9080/ancun-boss";
        this.accessId = "1a68f027825606cd3d8aafdc7167dabe";
        this.accessKey = "MTI4NDZlN2VmMDRmOTUwZDc5YmE0MmMwZmY3OTc0NGE=";
    }


    @Test
    public void run() throws Exception {
    	TestAuthInput bl =new TestAuthInput();
    	bl.setAccessid(accessId);
    	bl.setAccesskey(accessKey);
    	bl.setUserTel("18758279038");
    	bl.setProvinceCode("330000");
    	bl.setBusinessCode("DX");
    	bl.setBizNo("DX-SHANGHAI");
    	
    	
    	super.responseJSON("testauth", bl);
    }

}

