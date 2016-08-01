package com.ancun.datasyn.service.master;

import com.ancun.common.persistence.model.master.BizTimerConfig;

import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/13
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IBizTimerCofigService {

    /**
     * 查询所有定时设置
     *
     * @return
     */
    List<BizTimerConfig> selectAllBizTimerConfigs();

    /**
     * 根据模块查询用户信息
     *
     * @param muoudle
     * @return
     */
    BizTimerConfig selectByMoudle(String muoudle);

    /**
     * 更新定时器
     *
     * @param moudle
     */
    void updateBizTimerConfig(String moudle);
}
