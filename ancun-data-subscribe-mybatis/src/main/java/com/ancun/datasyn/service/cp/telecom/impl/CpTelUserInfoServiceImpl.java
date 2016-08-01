package com.ancun.datasyn.service.cp.telecom.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.cp.telecom.TelUserInfoMapper;
import com.ancun.common.persistence.model.cp.telecom.TelUserInfo;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.service.cp.telecom.ICpTelUserInfoService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "cpTelecom")
public class CpTelUserInfoServiceImpl implements ICpTelUserInfoService {

    @Resource
    private TelUserInfoMapper telUserInfoMapper;

    @Override
    public List<TelUserInfo> selectTelUserInfo(BizTimerConfig bizTimerConfig) {
        Example example = new Example(TelUserInfo.class);
        example.createCriteria().andBetween("updateTime", bizTimerConfig.getSynStartTimer(), bizTimerConfig.getSynEndTimer());

        //或者时间为 0000-00-00 00:00:00
        example.or(example.createCriteria().andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return telUserInfoMapper.selectByExample(example);
    }
}
