package com.ancun.common.persistence.mapper.sh;

import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.common.persistence.model.sh.ShBizUserInfo;
import com.ancun.common.persistence.model.sh.ShUserInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ShUserInfoMapper extends Mapper<ShUserInfo> {

    /**
     * 组装查询上海用户变化数据
     *
     * @param bizTimerConfig
     * @return
     */
    List<ShBizUserInfo> selectShBizUserInfo(BizTimerConfig bizTimerConfig);
}