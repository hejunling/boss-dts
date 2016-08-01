package com.ancun.datasyn.service.cp.telecom;

import com.ancun.common.persistence.model.cp.telecom.TelUserInfo;
import com.ancun.common.persistence.model.master.BizTimerConfig;

import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICpTelUserInfoService {

    /**
     * 总部电信用户
     * @param bizTimerConfig
     * @return
     */
    List<TelUserInfo> selectTelUserInfo(BizTimerConfig bizTimerConfig);
}
