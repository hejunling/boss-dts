package com.ancun.boss.business.service.bizcanceluser;

import com.ancun.boss.business.pojo.bizcanceluser.BizBatchCancelUserInput;
import com.ancun.boss.business.pojo.bizcanceluser.BizCancelUserInput;
import com.ancun.core.exception.EduException;

/**
 * 退订业务接口
 *
 * @Created on 2016年3月10日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IBizCanlelUserService {

	/**
	 * 单个业务用户退订
	 * @throws EduException
	 */
	public void bizCancelUser(BizCancelUserInput input) throws EduException;
	
	/**
	 * 业务用户批量退订
	 * @param input
	 * @return
	 * @throws EduException
	 */
	public void bizBatchCancelUser(BizBatchCancelUserInput input) throws EduException;
}
