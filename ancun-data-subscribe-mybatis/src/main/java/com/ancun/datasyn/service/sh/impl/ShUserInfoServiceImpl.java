package com.ancun.datasyn.service.sh.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.sh.ShUserInfoMapper;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.common.persistence.model.sh.ShBizUserInfo;
import com.ancun.datasyn.service.sh.IShUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "shV2")
public class ShUserInfoServiceImpl implements IShUserInfoService {

    @Resource
    private ShUserInfoMapper shUserInfoMapper;

    @Override
    public List<ShBizUserInfo> selectShUserInfo(BizTimerConfig bizTimerConfig) {
        return shUserInfoMapper.selectShBizUserInfo(bizTimerConfig);
    }
}
