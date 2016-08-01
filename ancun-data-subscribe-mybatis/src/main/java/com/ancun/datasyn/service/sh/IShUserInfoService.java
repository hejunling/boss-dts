package com.ancun.datasyn.service.sh;

import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.common.persistence.model.sh.ShBizUserInfo;

import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IShUserInfoService {

    /**
     * 查询上海音证宝用户
     *
     * @param bizTimerConfig
     * @return
     */
    List<ShBizUserInfo> selectShUserInfo(BizTimerConfig bizTimerConfig);
}
