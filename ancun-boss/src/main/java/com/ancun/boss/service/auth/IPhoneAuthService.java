package com.ancun.boss.service.auth;

/**
 * 鉴权接口
 * @author 静好
 *
 */
public interface IPhoneAuthService {
	
		public boolean auth(String usertel,String provinceCode,String businessCode);
		
}
