package com.ancun.boss.service.system.impl;


import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.persistence.mapper.*;
import com.ancun.boss.persistence.model.*;
import com.ancun.boss.service.system.IUserRoleService;
import com.ancun.boss.util.ContainerUtil;
import com.ancun.core.exception.EduException;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
	@Resource
	private UserRoleMapper userRoleMapper;

	@Resource
	private SystemRoleInfoMapper systemRoleInfoMapper;

	@Resource
	private UserDatapermissionMapper userDatapermissionMapper;

	@Resource
	private RoleMenuFunctionMapper roleMenuFunctionMapper;
	
	@Resource
	private FunctionInfoMapper functionInfoMapper;

	@Override
	public List<UserRole> selectSerialUserRoleByUserNo(UserRole userRole)
			throws EduException {
		UserRoleExample example = new UserRoleExample();
		UserRoleExample.Criteria criteria = example.createCriteria();
		criteria.andUsernoEqualTo(userRole.getUserno());
		return userRoleMapper.selectByExample(example);

	}

	@Override
	public SystemRoleInfo selectSerialSystemRoleInfoListByRoleNo(
			SystemRoleInfo systemRoleInfo) throws EduException {
		SystemRoleInfoExample e = new SystemRoleInfoExample();
		SystemRoleInfoExample.Criteria c = e.createCriteria();
		c.andRolenoEqualTo(systemRoleInfo.getRoleno());
		c.andDeletedEqualTo(BizRequestConstant.NO);

		List<SystemRoleInfo> sList = systemRoleInfoMapper.selectByExample(e);
		
		if(ContainerUtil.isNotEmptyList(sList)){
			return sList.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public List<UserDatapermission> selectSerialUserDatapermissionByUserNo(
			UserDatapermission userDatapermission) throws EduException {
		UserDatapermissionExample ex = new UserDatapermissionExample();
		UserDatapermissionExample.Criteria c = ex.createCriteria();

		c.andUsernoEqualTo(userDatapermission.getUserno());

		return userDatapermissionMapper.selectByExample(ex);
	}

	@Override
	public List<RoleMenuFunction> selectSerialRoleMenuFunctionByRoleNo(
			RoleMenuFunction roleMenuFunction) throws EduException {
		RoleMenuFunctionExample ex = new RoleMenuFunctionExample();
		RoleMenuFunctionExample.Criteria c = ex.createCriteria();

		c.andRolenoEqualTo(roleMenuFunction.getRoleno());

		return roleMenuFunctionMapper.selectByExample(ex);
	}

	@Override
	public FunctionInfo selectSinglefunFunctionInfoByFucNo(
			FunctionInfo functionInfo) throws EduException {
				return functionInfoMapper.selectByPrimaryKey(functionInfo.getFucno());
	}
	@Override
	public List<FunctionInfo> selectSerialFunctionInfoByRoleMenuFunction(
			RoleMenuFunction roleMenuFunction) throws EduException {
				return functionInfoMapper.selectSerialFunctionInfoByRoleMenuFunction(roleMenuFunction);
	}
	
	@Override
	public List<FunctionInfo> selectSerialFunctionInfoByMenuFunction(
			MenuFunctionInfo menuFunctionInfo) throws EduException{
				return functionInfoMapper.selectSerialFunctionInfoByMenuFunction(menuFunctionInfo);
	}
	
	
}
