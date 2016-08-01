package com.ancun.datasyn.service.cp.unicom;

import com.ancun.datasyn.pojo.taocan.TaocanInput;

/**
 * cp联通接口实现类
 * User: zkai
 * Date: 2016/5/19
 * Time: 15:10
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICpUnicomTcService {
    /**
     * 插入分省联通队列
     */
    public void insertUnicomTcInfoQ(TaocanInput input);

}
