package com.ancun.datadispense.service.province.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.dx.EntUserInfoMapper;
import com.ancun.common.persistence.model.dx.EntUserInfo;
import com.ancun.common.persistence.model.master.BizEntInfo;
import com.ancun.datadispense.service.province.LTProvinceBizEntInfoService;
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
public class LTProvinceBizEntInfoServiceImpl implements LTProvinceBizEntInfoService{
	   @Resource
	    private ProvincesBaseService provincesBaseService;

	    @Resource
	    private EntUserInfoMapper entUserInfoMapper;

	    @Override
	    public void updateBizEntInfo(BizEntInfo bizEntInfo) throws CustomException {

	        EntUserInfo entUserInfo = provincesBaseService.selectEntUserInfo(bizEntInfo.getEntNo(), bizEntInfo.getEntNo(), null);
	        if (null == entUserInfo) {
	            throw new CustomException("修改企业用户信息失败：业务数据库不存在该用户");
	        }

	        Example example = new Example(EntUserInfo.class);
	        example.createCriteria().andEqualTo("userno", bizEntInfo.getEntNo());

	        EntUserInfo temp = new EntUserInfo();
	        temp.setCertificatenum(bizEntInfo.getRegNo());
	        temp.setAddress(bizEntInfo.getAddress());
	        temp.setUsername(bizEntInfo.getName());

	        entUserInfoMapper.updateByExampleSelective(temp, example);
	    }
}
