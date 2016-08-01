package com.ancun.boss.service.auth.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.business.persistence.module.BizUserAuthInfo;
import com.ancun.boss.business.persistence.module.BizUserWhiteList;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.mapper.BizUserAuthInfosMapper;
import com.ancun.boss.persistence.biz.mapper.BizUserWhiteAuthMapper;
import com.ancun.boss.service.auth.IPhoneAuthService;
import com.ancun.boss.util.YulucnUtil;
import com.ancun.core.exception.EduException;
import com.ancun.utils.PhoneCallCheck;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
/**
 * 上海音证宝特殊鉴权
 * @author 静好
 *
 */
@Service(value="shAuthService")
public class ShAuthService implements IPhoneAuthService {
	
	
	@Resource
	private BizUserWhiteAuthMapper bizUserWhiteAuthMapper;
	
	@Resource
	private BizUserAuthInfosMapper bizUserAuthInfosMapper;
	
	
	/**
	 * 上海音证宝特殊鉴权
	 * 调用上海电信的鉴权接口
	 * @param userno
	 * @param provinceCode 省份
	 * @param businessCode 运营商代号
	 * @throws EduException
	 */
	@Override
	public boolean auth(String usertel, String provinceCode, String businessCode) {
		//鉴权白名单记录查询
		List<BizUserWhiteList> list = bizUserWhiteAuthMapper.selectByNoAndType(usertel, BizRequestConstant.JQ_WHITE_LIST,provinceCode);
		//鉴权合法记录查询
		List<BizUserAuthInfo> listauth = bizUserAuthInfosMapper.selectAuthExist(usertel, BizRequestConstant.AUTH_USER_TRUE);
		//鉴权返回值初始化设置false
		String authResult = BizRequestConstant.AUTH_USER_FALSE;
		
		//不存在于鉴权白名单中
		if(null == list || list.size()==0){
			//不存在鉴权成功记录的情况
			if(null == listauth || listauth.size()==0 ){
				//调用电信的鉴权接口
				shTelecomAuth(usertel);
				authResult = BizRequestConstant.AUTH_USER_TRUE;
				bizUserAuthInfosMapper.insertAuthInfo(usertel, authResult,BizRequestConstant.AUTH_SOURCE_IVR);
				return true;
			}else{
				//存在鉴权成功的记录
				return true;
			}
		}else if(list != null && list.size()==1){ //存在鉴权白名单且唯一
			return true;
		}else{
			//当数量大于一条记录时，抛鉴权白名单信息不唯一信息
			throw new EduException(MessageConstant.AUTH_INFO_IF_NOT_ONLY);
		}
	}

	
	/**
	 * 调用上海电信鉴权接口
	 * @param userTel
	 */
	public  void shTelecomAuth(String userTel){
		String phone = userTel;
		//固话去除去除021区号
		if(!PhoneCallCheck.checkMobile(userTel)){
			phone = userTel.substring(userTel.indexOf(BizRequestConstant.SH_EREACODE)+BizRequestConstant.SH_EREACODE.length());
		}
		YulucnUtil.checkShyzbphone(phone);
	}

}
