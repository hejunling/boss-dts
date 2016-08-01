package com.ancun.datadispense.service.cp.unicom;

import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.util.CustomException;

public interface UnicomUserService {
	
    /**
     * 用户新增
     *
     * @param input
     */
	public void openUser(BizUserInfo input) throws CustomException;
	
    /**
     * 用户修改
     *
     * @param input
     */
	public void updateUser(BizUserInfo input) throws CustomException;
	
}
