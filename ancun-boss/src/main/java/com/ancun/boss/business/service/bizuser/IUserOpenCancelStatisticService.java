package com.ancun.boss.business.service.bizuser;

import com.ancun.boss.business.pojo.bizuser.UserOpenCancelStatisticInput;
import com.ancun.boss.business.pojo.bizuser.UserOpenCancelStatisticOutput;

import java.util.List;

/**
 * 用户开通退订统计接口
 * User: zkai
 * Date: 2016/6/16
 * Time: 15:05
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IUserOpenCancelStatisticService {

    /**
     * 取得用户开通退订统计信息
     * @param input
     * @return
     */
    public UserOpenCancelStatisticOutput getUserOpenCancelStatistic(UserOpenCancelStatisticInput input);

}
