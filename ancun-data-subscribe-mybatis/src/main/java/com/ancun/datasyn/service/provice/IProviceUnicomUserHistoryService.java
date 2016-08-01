package com.ancun.datasyn.service.provice;

import com.ancun.datasyn.pojo.userlife.UserLifeInput;

/**
 * 分省联通用户历史接口
 * User: zkai
 * Date: 2016/5/30
 * Time: 14:12
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IProviceUnicomUserHistoryService {

    /**
     * 插入分省联通用户历史队列
     */
    public void insertProviceUserHistoryInfoQ(UserLifeInput input);
}
