package com.ancun.datasyn.service.provice.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.dx.EntUserInfoMapper;
import com.ancun.common.persistence.mapper.dx.UserInfoMapper;
import com.ancun.common.persistence.model.dx.EntUserInfo;
import com.ancun.common.persistence.model.dx.UserInfo;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.service.provice.ILTUserinfoService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分省联通用户同步
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/5/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "ltProvince")
public class LTUserinfoServiceImpl implements ILTUserinfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private EntUserInfoMapper entUserInfoMapper;


    @Override
    public List<String> selectAllPersonRpcodes(BizTimerConfig bizTimerConfig) {
        return userInfoMapper.selectAllPersonRpcodes(bizTimerConfig);
    }

    @Override
    public List<String> selectAllEntRpcodes(BizTimerConfig bizTimerConfig) {
        return entUserInfoMapper.selectAllEntRpcodes(bizTimerConfig);
    }

    @Override
    public List<UserInfo> selectUserInfo(BizTimerConfig bizTimerConfig, String rpcode) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andBetween("updateTime", bizTimerConfig.getSynStartTimer(), bizTimerConfig.getSynEndTimer())
                .andEqualTo("rpcode", rpcode);

        //或者时间为 0000-00-00 00:00:00
        example.or(example.createCriteria().andEqualTo("rpcode", rpcode).andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return userInfoMapper.selectByExample(example);
    }

    @Override
    public List<EntUserInfo> selectEntUserInfo(BizTimerConfig bizTimerConfig, String rpcode) {
        Example example = new Example(EntUserInfo.class);
        example.createCriteria().andBetween("updateTime", bizTimerConfig.getSynStartTimer(), bizTimerConfig.getSynEndTimer())
                .andEqualTo("rpcode", rpcode);

        //或者时间为 0000-00-00 00:00:00
        example.or(example.createCriteria().andEqualTo("rpcode", rpcode).andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return entUserInfoMapper.selectByExample(example);
    }

    @Override
    public int selectAllPersonCount(BizTimerConfig bizTimerConfig) {
        return userInfoMapper.selectAllPersonCount(bizTimerConfig);
    }

    @Override
    public int selectAllEntCount(BizTimerConfig bizTimerConfig) {
        return entUserInfoMapper.selectAllEntCount(bizTimerConfig);
    }
}
