package com.ancun.datasyn.service.cp.unicom;

import com.ancun.datasyn.pojo.userlife.UserLifeInput;

/**
 * cp联通生命周期
 * User: zkai
 * Date: 2016/5/27
 * Time: 15:06
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICpUnicomUserLifeService {
    /**
     * 插入cp联通用户生命周期列表
     */
    public void insertCpUnicomUserLifeInfoQ(UserLifeInput input);
}
