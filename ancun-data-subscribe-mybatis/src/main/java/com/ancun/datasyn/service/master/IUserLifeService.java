package com.ancun.datasyn.service.master;

import com.ancun.common.persistence.model.master.BizUserLifeCircle;
import com.ancun.datasyn.pojo.userlife.UserLifeInput;

import java.util.Date;

/**
 * 用户生命周期接口
 * User: zkai
 * Date: 2016/5/30
 * Time: 14:18
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IUserLifeService {

    /**
     * 将个业务信息插入到队列
     */
    public void insertUserLifeInfoQ(UserLifeInput input);


    /**
     * 同步boss用户历史信息（插入
     * @param bizUserLifeCircle
     * @param optenTime 开通时间
     * @param cancelTime 退订时间
     */
    public void synUserHistory(BizUserLifeCircle bizUserLifeCircle, Date optenTime , Date cancelTime);
}
