package com.ancun.boss.business.service.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizUserInfoInput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoUpdateInput;
import com.ancun.core.exception.EduException;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IBizUserInfoUpdateService {

    /**
     * 修改业务用户
     *
     * @param infoUpDateInput
     * @throws EduException
     */
    void updateBizUserInfo(BizUserInfoUpdateInput infoUpDateInput) throws EduException;


    /**
     * 清密
     *
     * @param bizUserInfoInput
     * @throws EduException
     */
    void bizUserCleanPwd(BizUserInfoInput bizUserInfoInput) throws EduException;
}
