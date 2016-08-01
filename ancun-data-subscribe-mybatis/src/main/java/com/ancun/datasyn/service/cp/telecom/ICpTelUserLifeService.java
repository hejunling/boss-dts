package com.ancun.datasyn.service.cp.telecom;

import com.ancun.datasyn.pojo.userlife.UserLifeInput;

/**
 * cp电信生命周期
 * User: zkai
 * Date: 2016/5/27
 * Time: 15:06
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICpTelUserLifeService {
    /**
     * 插入cp电信用户生命周期列表
     */
    public void insertCpTelUserLifeInfoQ(UserLifeInput input);
}
