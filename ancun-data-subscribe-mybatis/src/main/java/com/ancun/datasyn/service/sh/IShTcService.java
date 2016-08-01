package com.ancun.datasyn.service.sh;

import com.ancun.datasyn.pojo.taocan.TaocanInput;

/**
 * 上海音证宝接口类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:24
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IShTcService {
    /**
     * 插入上海套餐队列
     */
    public void insertShTcInfoQ(TaocanInput input);
}
