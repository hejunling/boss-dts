package com.ancun.datasyn.service.cp.unicom;

import com.ancun.common.persistence.model.cp.unicom.UniUserInfo;
import com.ancun.common.persistence.model.master.BizTimerConfig;

import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICpUnicomUserInfoService {

    /**
     * 总部联通用户查询
     *
     * @param bizTimerConfig
     * @return
     */
    List<UniUserInfo> selectUniUserInfo(BizTimerConfig bizTimerConfig);
}
