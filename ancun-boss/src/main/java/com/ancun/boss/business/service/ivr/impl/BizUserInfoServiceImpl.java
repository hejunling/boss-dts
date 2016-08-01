package com.ancun.boss.business.service.ivr.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ancun.boss.business.persistence.mapper.BizComboInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoMapper;
import com.ancun.boss.business.persistence.module.BizComboInfo;
import com.ancun.boss.business.persistence.module.BizComboInfoExample;
import com.ancun.boss.business.persistence.module.BizUserInfo;
import com.ancun.boss.business.persistence.module.BizUserInfoExample;
import com.ancun.boss.business.service.ivr.IBizUserInfoService;
import com.ancun.boss.constant.BizRequestConstant;

public class BizUserInfoServiceImpl implements IBizUserInfoService{

	@Resource
	private BizUserInfoMapper bizUserInfoMapper;
	
	@Resource
	private BizComboInfoMapper bizComboInfoMapper;
	
	public List<BizUserInfo> selectSingle(String userNo){
		BizUserInfoExample ex = new BizUserInfoExample();
		BizUserInfoExample.Criteria cr = ex.createCriteria(); 
		cr.andUserNoEqualTo(userNo);
		cr.andStatusEqualTo(BizRequestConstant.OPENED_USER);
		
		return bizUserInfoMapper.selectByExample(ex);
	}
	
	public int deleteSingle(String userNo){
		BizUserInfoExample ex = new BizUserInfoExample();
		BizUserInfoExample.Criteria cr = ex.createCriteria(); 
		cr.andUserNoEqualTo(userNo);
		
		return bizUserInfoMapper.deleteByExample(ex);
	}
	
	public List<BizComboInfo> selectDefaultComboByRpcode(String rpcode){
		BizComboInfoExample ex = new BizComboInfoExample();
		BizComboInfoExample.Criteria cr = ex.createCriteria(); 
		cr.andRpcodeEqualTo(rpcode);
		cr.andStatusEqualTo("1");
		
		return bizComboInfoMapper.selectByExample(ex);
	}
	
	public List<BizComboInfo> selecComboTypeById(Long id){
		BizComboInfoExample ex = new BizComboInfoExample();
		BizComboInfoExample.Criteria cr = ex.createCriteria(); 
		cr.andTcidEqualTo(id);
		cr.andStatusEqualTo("1");
		
		return bizComboInfoMapper.selectByExample(ex);
	}
	
	
	
}
