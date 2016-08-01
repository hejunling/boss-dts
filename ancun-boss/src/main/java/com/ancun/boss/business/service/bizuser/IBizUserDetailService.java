package com.ancun.boss.business.service.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizUserDetaiOutput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoInput;
import com.ancun.boss.business.pojo.bizuser.BizUserLifeCircleListOutput;
import com.ancun.core.exception.EduException;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/19
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IBizUserDetailService {

    /**
     * 用户详情
     *
     * @param input
     * @return
     */
    BizUserDetaiOutput querybizUserDetail(BizUserInfoInput input) throws EduException;

    /**
     * 查询用户生命周期
     *
     * @param input
     * @return
     * @throws EduException
     */
    BizUserLifeCircleListOutput queryBizUserLifeCircle(BizUserInfoInput input) throws EduException;

}
