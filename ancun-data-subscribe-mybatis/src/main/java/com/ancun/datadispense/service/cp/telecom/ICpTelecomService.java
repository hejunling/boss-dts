package com.ancun.datadispense.service.cp.telecom;

import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.util.CustomException;

/**
 * CP电信
 *
 * @Created on 2016年4月11日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICpTelecomService {
	
	/**
	 * 开通
	 */
	void openUser(BizUserInfo bizUserInfo) throws CustomException;
	
	/**
	 * 修改
	 */
	void updateUser(BizUserInfo bizUserInfo) throws CustomException;

}
