package com.ancun.boss.service.system;

import java.util.List;

import com.ancun.boss.persistence.model.FunctionInfo;
import com.ancun.boss.persistence.model.MenuFunctionInfo;
import com.ancun.boss.persistence.model.RoleMenuFunction;
import com.ancun.boss.persistence.model.SystemRoleInfo;
import com.ancun.boss.persistence.model.UserDatapermission;
import com.ancun.boss.persistence.model.UserRole;
import com.ancun.core.exception.EduException;

public interface IUserRoleService {

	public List<UserRole> selectSerialUserRoleByUserNo(UserRole userRole)
			throws EduException;

	public SystemRoleInfo selectSerialSystemRoleInfoListByRoleNo(
			SystemRoleInfo systemRoleInfo) throws EduException;

	public List<UserDatapermission> selectSerialUserDatapermissionByUserNo(
			UserDatapermission userDatapermission) throws EduException;

	public List<RoleMenuFunction> selectSerialRoleMenuFunctionByRoleNo(
			RoleMenuFunction roleMenuFunction) throws EduException;

	public FunctionInfo selectSinglefunFunctionInfoByFucNo(
			FunctionInfo functionInfo) throws EduException;

	public List<FunctionInfo> selectSerialFunctionInfoByRoleMenuFunction(
			RoleMenuFunction roleMenuFunction) throws EduException;
	
	public List<FunctionInfo> selectSerialFunctionInfoByMenuFunction(
			MenuFunctionInfo menuFunctionInfo) throws EduException;
}
