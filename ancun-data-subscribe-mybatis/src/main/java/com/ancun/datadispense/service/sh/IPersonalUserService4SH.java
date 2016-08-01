package com.ancun.datadispense.service.sh;

import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.util.CustomException;

/**
 * 上海个人用户接口(电信/联通)
 *
 * @Created on 2016年3月21日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IPersonalUserService4SH {

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
