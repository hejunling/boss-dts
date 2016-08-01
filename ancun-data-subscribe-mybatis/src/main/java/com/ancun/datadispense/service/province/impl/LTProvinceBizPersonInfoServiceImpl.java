package com.ancun.datadispense.service.province.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.dx.UserInfoMapper;
import com.ancun.common.persistence.model.dx.UserInfo;
import com.ancun.common.persistence.model.master.BizPersonInfo;
import com.ancun.datadispense.service.province.LTProvinceBizPersonInfoService;
import com.ancun.datadispense.service.province.ProvincesBaseService;
import com.ancun.datadispense.util.CustomException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author chenb
 * @version 1.0
 * @Created on 2016/4/8
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "ltProvince")
public class LTProvinceBizPersonInfoServiceImpl implements LTProvinceBizPersonInfoService{
    @Resource
    private ProvincesBaseService provincesBaseService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public void updateBizPersonInfo(BizPersonInfo personInfo) throws CustomException {

        UserInfo userInfo = provincesBaseService.selectPersonalUserInfo(personInfo.getUserNo());
        if (null == userInfo) {
            throw new CustomException("修改个人用户信息失败：业务数据库用户不存在");
        }

        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("userno", personInfo.getUserNo());

        UserInfo temp = new UserInfo();
        temp.setSex(personInfo.getSex());
        temp.setCertificatenum(personInfo.getIdentify());

        userInfoMapper.updateByExampleSelective(temp, example);
    }
}
