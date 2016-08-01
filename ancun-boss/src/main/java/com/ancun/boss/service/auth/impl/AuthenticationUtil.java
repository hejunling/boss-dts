package com.ancun.boss.service.auth.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.service.auth.IPhoneAuthService;
import com.ancun.boss.service.auth.PhoneBizCheckService;
import com.ancun.core.exception.EduException;
import com.ancun.core.page.PagingInterceptor;
import com.ancun.utils.PhoneCallCheck;
import com.ancun.utils.StringUtil;
import com.google.common.collect.HashBasedTable;

/**
 * 号码鉴权
 * @author 静好
 *
 */
@Component
public class AuthenticationUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PagingInterceptor.class);
	
	@Resource(name = "shAuthService")
	private IPhoneAuthService shAuthService;
	
	@Resource(name = "defaultAuthService")
	private IPhoneAuthService defaultAuthService;
	
	@Resource
	private PhoneBizCheckService phoneBizCheckService;
	
	
	/**
	 * 号码鉴权
	 * @param usertel
	 * @param provinceCode
	 * @param businessCode : DX/LT/YD
	 * @return
	 * @throws EduException
	 */
	public boolean validCheck(String usertel, String provinceCode, String businessCode,String bizNo) throws EduException{
		//请求省份不能为空
		if(StringUtil.isEmpty(provinceCode)){
			throw new EduException(MessageConstant.PROVINCE_NOT_BE_EMPTY);
		}
		//号码格式判断
		checkFormat(usertel,MessageConstant.PHONENO_MUST_BE_NOT_EMPTY,MessageConstant.PHONE_FORMAT_ILLEGAL);
		
		//根据传过来的省份编号和运营商编号走不通实现方法
		HashBasedTable<String, String, IPhoneAuthService> table = getTables();
		
		IPhoneAuthService iPhoneAuthService = table.get(provinceCode, businessCode);
		if(null == iPhoneAuthService) {
			//没有在table中找到对应实现方法,就走共通实现方法
			iPhoneAuthService = defaultAuthService;
		}
		boolean auth = iPhoneAuthService.auth(usertel, provinceCode, businessCode);
		return auth ? auth : phoneBizCheckService.checkPhoneAreaAndBiz(provinceCode, usertel, bizNo);// 业务，省份，号码属地判断
	}


	private HashBasedTable<String, String, IPhoneAuthService> getTables() {
		HashBasedTable<String, String, IPhoneAuthService> table = HashBasedTable.create();
		//放置上海电信的实现方法
		table.put(BizRequestConstant.PROVINCES_SHANGHAI, Constant.PROVENCE_TYPE_DX, shAuthService);
		return table;
	}
	
	
	/**
	 * 判断号码是否为空/固话/手机
	 * @param phone
	 * @param code1
	 * @param code2
	 * @throws EduException
	 */
	private void checkFormat(String phone,int code1,int code2) throws EduException{
		
		if(StringUtil.isEmpty(phone)){
			throw new EduException(code1);
		}
		
		if(!PhoneCallCheck.checkPhone(phone) && !PhoneCallCheck.checkMobile(phone)){
			throw new EduException(code2);
		}

	}
	
}
