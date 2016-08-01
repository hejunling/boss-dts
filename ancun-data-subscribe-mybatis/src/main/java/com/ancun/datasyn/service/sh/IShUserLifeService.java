package com.ancun.datasyn.service.sh;

import com.ancun.datasyn.pojo.userlife.UserLifeInput;

/**
 * 上海音证宝用户生命周期
 * User: zkai
 * Date: 2016/5/27
 * Time: 15:06
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IShUserLifeService {
    /**
     * 插入上海音证宝用户生命周期
     */
    public void insertShUserLifeInfoQ(UserLifeInput input);
}
