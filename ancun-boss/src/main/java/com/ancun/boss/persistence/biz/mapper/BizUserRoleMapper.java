package com.ancun.boss.persistence.biz.mapper;

import com.ancun.boss.persistence.model.SystemRoleInfo;

import java.util.List;

/**
 *
 * 用户权限map
 * @Created on 2016年06月12日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizUserRoleMapper {
	
	// 用户权限
	List<SystemRoleInfo> queryUserRoleList(String  userno);

}
