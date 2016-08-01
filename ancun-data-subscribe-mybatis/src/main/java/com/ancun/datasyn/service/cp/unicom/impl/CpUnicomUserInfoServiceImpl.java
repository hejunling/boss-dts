package com.ancun.datasyn.service.cp.unicom.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.cp.unicom.UniUserInfoMapper;
import com.ancun.common.persistence.model.cp.unicom.UniUserInfo;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.service.cp.unicom.ICpUnicomUserInfoService;
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
@TargetDataSource(name = "cpUnicom")
public class CpUnicomUserInfoServiceImpl implements ICpUnicomUserInfoService {

    @Resource
    private UniUserInfoMapper uniUserInfoMapper;

    @Override
    public List<UniUserInfo> selectUniUserInfo(BizTimerConfig bizTimerConfig) {
        Example example = new Example(UniUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andBetween("updateTime", bizTimerConfig.getSynStartTimer(), bizTimerConfig.getSynEndTimer());

        //或者时间为 0000-00-00 00:00:00
        example.or(example.createCriteria().andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return uniUserInfoMapper.selectByExample(example);
    }
}
