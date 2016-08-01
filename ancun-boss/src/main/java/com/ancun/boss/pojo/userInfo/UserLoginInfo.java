package com.ancun.boss.pojo.userInfo;

import com.ancun.boss.persistence.model.FunctionInfo;
import com.ancun.boss.persistence.model.MenuFunctionInfo;
import com.ancun.boss.persistence.model.RoleMenuFunction;
import com.ancun.boss.persistence.model.SystemBasicConfig;
import com.ancun.boss.persistence.model.SystemRoleInfo;
import com.ancun.boss.persistence.model.SystemUserInfo;
import com.ancun.boss.persistence.model.UserDatapermission;
import com.ancun.boss.persistence.model.UserRole;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.common.biz.persistence.model.SystemBasicData;

public class UserLoginInfo extends BossBasePojo {
	private SystemUserInfo systemUserInfo;

	private SystemRoleInfo systemRoleInfo;

	private SystemBasicConfig systemBasicConfig;

	private MenuFunctionInfo menuFunctionInfo;

	private FunctionInfo functionInfo;

	private RoleMenuFunction roleMenuFunction;

	private SystemBasicData systemBasicData;

	private UserRole userRole;

	private UserDatapermission userDatapermission;

	public SystemUserInfo getSystemUserInfo() {
		return systemUserInfo;
	}

	public void setSystemUserInfo(SystemUserInfo systemUserInfo) {
		this.systemUserInfo = systemUserInfo;
	}

	public SystemRoleInfo getSystemRoleInfo() {
		return systemRoleInfo;
	}

	public void setSystemRoleInfo(SystemRoleInfo systemRoleInfo) {
		this.systemRoleInfo = systemRoleInfo;
	}

	public SystemBasicConfig getSystemBasicConfig() {
		return systemBasicConfig;
	}

	public void setSystemBasicConfig(SystemBasicConfig systemBasicConfig) {
		this.systemBasicConfig = systemBasicConfig;
	}

	public MenuFunctionInfo getMenuFunctionInfo() {
		return menuFunctionInfo;
	}

	public void setMenuFunctionInfo(MenuFunctionInfo menuFunctionInfo) {
		this.menuFunctionInfo = menuFunctionInfo;
	}

	public FunctionInfo getFunctionInfo() {
		return functionInfo;
	}

	public void setFunctionInfo(FunctionInfo functionInfo) {
		this.functionInfo = functionInfo;
	}

	public RoleMenuFunction getRoleMenuFunction() {
		return roleMenuFunction;
	}

	public void setRoleMenuFunction(RoleMenuFunction roleMenuFunction) {
		this.roleMenuFunction = roleMenuFunction;
	}

	public SystemBasicData getSystemBasicData() {
		return systemBasicData;
	}

	public void setSystemBasicData(SystemBasicData systemBasicData) {
		this.systemBasicData = systemBasicData;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public UserDatapermission getUserDatapermission() {
		return userDatapermission;
	}

	public void setUserDatapermission(UserDatapermission userDatapermission) {
		this.userDatapermission = userDatapermission;
	}

}
