package com.ancun.datadispense.service.province;

import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.util.CustomException;

/**
 * 电信分省个人用户接口
 *
 * @author chenb
 * @version 1.0
 * @Created on 2016/3/9
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface LTProvincePersonalUserService {

    /**
     * 开通个人用户
     *
     * @param bizUserInfo 业务用户
     * @throws Exception
     */
    public void openPersonalUser(BizUserInfo bizUserInfo) throws CustomException;

    /**
     * 修改个人用户
     *
     * @param bizUserInfo 业务用户
     * @throws Exception
     */
    public void UpdatePersonalUser(BizUserInfo bizUserInfo) throws CustomException;
}
