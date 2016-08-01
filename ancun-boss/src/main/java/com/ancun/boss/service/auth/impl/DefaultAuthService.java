package com.ancun.boss.service.auth.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.business.persistence.module.BizUserWhiteList;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.mapper.BizUserWhiteAuthMapper;
import com.ancun.boss.service.auth.IPhoneAuthService;
import com.ancun.common.biz.pojo.common.RegionAreaInfo;
import com.ancun.common.biz.regionarea.RegionAreaInfoBiz;
import com.ancun.core.exception.EduException;
import com.ancun.utils.StringUtil;
/**
 * 判断是否在鉴权白名单中
 * @author 静好
 *
 */
@Service(value = "defaultAuthService")
public class DefaultAuthService implements IPhoneAuthService {
	
	@Resource
	private RegionAreaInfoBiz regionAreaInfoBiz;
	
	@Resource
	private BizUserWhiteAuthMapper bizUserWhiteAuthMapper;
	
	/**
	 * 是否在鉴权白名单中
	 * @param provinceCode
	 * @return
	 * @throws EduException
	 */
	@Override
	public boolean auth(String usertel,String provinceCode, String bussniessCode) {
		List<BizUserWhiteList> list = bizUserWhiteAuthMapper.selectByNoAndType(usertel, BizRequestConstant.JQ_WHITE_LIST,provinceCode);
		
		if(null == list || list.size()==0){
			return false;
			
		}else if(list != null && list.size()==1){
			return true;
		}else{
			//当数量大于一条记录时，抛鉴权白名单信息不唯一信息
			throw new EduException(MessageConstant.AUTH_INFO_IF_NOT_ONLY);
		}
	}
	
	
	/**
	 * 根据主叫号码取得归属地信息
	 *
	 * @param callfrom
	 * @return
	 */
	public RegionAreaInfo getRegionAreaInfo(String callfrom) {
		return regionAreaInfoBiz.getRegionAreaInfo(callfrom);
	}

}
