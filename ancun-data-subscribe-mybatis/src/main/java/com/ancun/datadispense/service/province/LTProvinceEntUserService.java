package com.ancun.datadispense.service.province;

import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.util.CustomException;

/**
 * 企业用户接口
 *
 * @author chenb
 * @version 1.0
 * @Created on 2016/3/9
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface LTProvinceEntUserService {
    /**
     * 开通企业用户
     *
     * @param bizUserInfo 业务用户
     * @throws Exception
     */
    public void openEntUser(BizUserInfo bizUserInfo) throws CustomException;

    /**
     * 退订企鹅用户
     *
     * @throws Exception
     */
    public void updateEntUser(BizUserInfo bizUserInfo) throws CustomException;
}
