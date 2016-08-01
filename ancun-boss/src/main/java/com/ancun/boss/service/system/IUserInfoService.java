package com.ancun.boss.service.system;

import com.ancun.boss.persistence.model.SystemUserInfo;
import com.ancun.boss.pojo.userInfo.*;
import com.ancun.core.exception.EduException;

import java.util.List;

/**
 * 用户管理
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/21
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IUserInfoService {

	/**
	 * 查询系统用户列表
	 *
	 * @param input
	 * @return
	 * @throws EduException
	 */
	public UserInfoListOutput queryUserList(UserInfoListInput input)
			throws EduException;

	/**
	 * 根据工号查询用户信息
	 *
	 * @param userNo
	 * @return
	 * @throws EduException
	 */
	public UserInfoOutput queryUserInfo(String userNo) throws EduException;

	/**
	 * 根据工号查询用户信息
	 *
	 * @param userNo
	 * @return
	 * @throws EduException
	 */
	public SystemUserInfo selectSingleSystemUserInfoLogin(
			SystemUserInfo systemUserInfo) throws EduException;

	/**
	 * 根据工号查询用户信息
	 *
	 * @param userNo
	 * @return
	 * @throws EduException
	 */
	public SystemUserInfo selectSingleSystemUserInfo(
			SystemUserInfo systemUserInfo) throws EduException;

	/**
	 * 新增/修改用户信息
	 *
	 * @param input
	 * @throws EduException
	 */
	public void addOrUpdateUserInfo(UserInfoInput input) throws EduException;

	/**
	 * 删除用户信息
	 *
	 * @param userNO
	 * @throws EduException
	 */
	public void deleteUserInfo(String userNO) throws EduException;

	/**
	 * 根据用户名查询用户信息
	 *
	 * @param userName
	 * @throws EduException
	 */
	public SystemUserInfo queryByUserName(String userName) throws EduException;

	/**
	 * 根据用户名中关键字模糊查询用户信息
	 *
	 * @return 用户下拉框用数据
	 */
	public List<UserSelectOutput> queryAllUserForSelect();
	
	public int updateIndividualUserPwd(SystemUserInfo systemUserInfo)
			throws EduException;
	

}
