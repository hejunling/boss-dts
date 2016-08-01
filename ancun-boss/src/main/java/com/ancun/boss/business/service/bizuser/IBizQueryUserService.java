package com.ancun.boss.business.service.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizUserInfoKeyInput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoKeyOutput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoListOutput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoQueryInput;


public interface IBizQueryUserService {

	/**
	 * 取得用户列表
	 * @param input
	 * @return
     */
	public BizUserInfoListOutput getBizUserInfoList(BizUserInfoQueryInput input) ;

	/**
	 * 根据主键得到用户信息
	 * @param input
	 * @return
     */
	public BizUserInfoKeyOutput selectUserInfoByKey(BizUserInfoKeyInput input);
}
