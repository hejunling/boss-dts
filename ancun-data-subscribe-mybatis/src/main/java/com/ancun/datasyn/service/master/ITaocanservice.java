package com.ancun.datasyn.service.master;

import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.datasyn.pojo.taocan.TaocanInput;

/**
 * 套餐接口
 * User: zkai
 * Date: 2016/5/16
 * Time: 20:39
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ITaocanservice {

    /**
     * 将个业务信息插入到队列
     */
    public void insertTcInfoQ(TaocanInput input);


    /**
     * 同步boss套餐信息（插入，修改）
     * @param taocanInfo
     */
    public void synBossTaocanInfo(BizComboInfo taocanInfo);
}
