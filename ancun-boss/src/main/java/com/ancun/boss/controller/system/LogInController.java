package com.ancun.boss.controller.system;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.model.*;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.boss.service.system.ISystemMenuInfoService;
import com.ancun.boss.service.system.IUserInfoService;
import com.ancun.boss.service.system.IUserRoleService;
import com.ancun.common.biz.accesskey.AccessBiz;
import com.ancun.common.biz.persistence.model.Accessinfo;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/22
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class LogInController extends BaseController {
	@Resource
	private IUserInfoService userInfoService;

	@Resource
	private IUserRoleService userRoleService;

	@Resource
	private IBasicConfigService basicConfigService;

	@Resource
	private ISystemMenuInfoService systemMenuInfoService;

	@Resource
	private AccessBiz acbiz;

	@AccessToken(access = AccessType.WEB, checkAccess = false)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public RespBody<Map<String, Object>> login(ReqBody<SystemUserInfo> reqBody)
			throws EduException {

		SystemUserInfo systemUserInfo = reqBody.getContent();
		Map<String, Object> respContent = new HashMap<String, Object>();
		String userNo = systemUserInfo.getUserno();

		// systemUserInfo
		systemUserInfo = userInfoService
				.selectSingleSystemUserInfoLogin(systemUserInfo);
		respContent.put("systemUserInfo", systemUserInfo);

		// accessInfo
		Accessinfo accessInfo = acbiz.createAccessInfo(userNo);
		BossBasePojo pj = new BossBasePojo();
		pj.setAccessid(accessInfo.getAccessid());
		pj.setAccesskey(accessInfo.getAccesskey());
		pj.setUserno(accessInfo.getUserno());
		respContent.put("accessInfo", pj);

		// userDatapermission
		List<UserDatapermission> userDatapermissionList = new ArrayList<UserDatapermission>();
		if (systemUserInfo.getDatapermission() != null
				&& systemUserInfo.getDatapermission().equals("YES")) {
			UserDatapermission userDatapermission = new UserDatapermission();
			userDatapermission.setUserno(userNo);
			userDatapermissionList = userRoleService
					.selectSerialUserDatapermissionByUserNo(userDatapermission);
		}

		// systemBasicConfigList
		List<SystemBasicConfig> systemBasicConfigList = new ArrayList<SystemBasicConfig>();
		for (UserDatapermission userDatapermission2 : userDatapermissionList) {
			SystemBasicConfig systemBasicConfig = new SystemBasicConfig();
			systemBasicConfig.setBasicno(userDatapermission2.getBasicno());
			systemBasicConfig=basicConfigService
					.selectSingleSystemBasicConfigByBasicNo(systemBasicConfig);
			if(systemBasicConfig!=null)
			systemBasicConfigList.add(systemBasicConfig);
		}
		respContent.put("systemBasicConfigList", systemBasicConfigList);

		// userRoleList
		UserRole userRole = new UserRole();
		userRole.setUserno(userNo);
		List<UserRole> userRoleList = userRoleService
				.selectSerialUserRoleByUserNo(userRole);
		if (userRoleList == null || userRoleList.size() == 0) {
			throw new EduException(MessageConstant.NO_AUTHORITY);
		}
		//respContent.put("userRoleList", userRoleList);

		// systemRoleInfoList
		Map<String, SystemRoleInfo> systemRoleInfoMap = new HashMap<String, SystemRoleInfo>();
		String isSuperman = BizRequestConstant.NO;
		for (UserRole userRole2 : userRoleList) {
			SystemRoleInfo systemRoleInfo2 = new SystemRoleInfo();
			systemRoleInfo2.setRoleno(userRole2.getRoleno());
			systemRoleInfo2 = userRoleService
					.selectSerialSystemRoleInfoListByRoleNo(systemRoleInfo2);

			if (systemRoleInfo2 != null) {
				systemRoleInfoMap.put(systemRoleInfo2.getRoleno(),
						systemRoleInfo2);
				if (systemRoleInfo2.getSuperman().equals(
						BizRequestConstant.YES)) {
					isSuperman = BizRequestConstant.YES;
				}
			}
		}
		respContent.put("systemRoleInfoMap", systemRoleInfoMap);

		// roleMenuFunctioniList
		List<RoleMenuFunction> roleMenuFunctioniList = new ArrayList<RoleMenuFunction>();
		for (UserRole userRole2 : userRoleList) {
			RoleMenuFunction roleMenuFunction = new RoleMenuFunction();
			roleMenuFunction.setRoleno(userRole2.getRoleno());
			roleMenuFunctioniList.addAll(userRoleService
					.selectSerialRoleMenuFunctionByRoleNo(roleMenuFunction));
		}

		// systemMenuInfoList
		// 定制树算法
		// 1查出原始树
		List<SystemMenuInfo> systemMenuInfoList = systemMenuInfoService
				.selectPoplarSystemMenuInfo(new SystemMenuInfo());

		//超级管理员不遍历树
		if (!isSuperman.equals("YES")) {
			// 2通过叶子节点判断定制树，并且追溯父节点
			for (RoleMenuFunction roleMenuFunction2 : roleMenuFunctioniList) {
				String menuNo = roleMenuFunction2.getMenuno();
				String parentNo = "";

				// 一定要是倒序遍历
				for (int i = systemMenuInfoList.size() - 1; i > -1; i--) {
					SystemMenuInfo systemMenuInfo2 = systemMenuInfoList.get(i);
					// 如果已经设为定制，不会重复设立。
					if (systemMenuInfo2.getCustomization()) {

					} else {
						// 因为是倒序，所以必然先从子节点开始。如果是子节点，或者父节点，就设为定制。
						if (menuNo.equals(systemMenuInfo2.getMenuno())
								|| parentNo.equals(systemMenuInfo2.getMenuno())) {
							systemMenuInfo2.setCustomization(true);
							// 然后再设立父节点。可以继续追溯
							parentNo = systemMenuInfo2.getPmenuinfo();
						}
					}

				}
			}
			// 遍历删除非定制部分。倒序遍历，可以在删除后不改变元素的顺序值。因为每次删除后，list每个元素的顺序值都会改变。
			for (int i = systemMenuInfoList.size() - 1; i > -1; i--) {
				SystemMenuInfo systemMenuInfo2 = systemMenuInfoList.get(i);
				if (!systemMenuInfo2.getCustomization()) {
					systemMenuInfoList.remove(i);
				}
			}
		}
		respContent.put("systemMenuInfoList", systemMenuInfoList);

		// menuFunctionMap(functionInfoliList)
		Map<String, Map<String, FunctionInfo>> menuFunctionMap = new HashMap<String, Map<String, FunctionInfo>>();
		for (SystemMenuInfo systemMenuInfo2 : systemMenuInfoList) {
			// 只有子节点菜单才能获取功能
			if (systemMenuInfo2.getIsLeaf().equals("true")) {
				Map<String, FunctionInfo> map = new HashMap<String, FunctionInfo>();

				//超级管理员直接获得功能
				if (isSuperman.equals(BizRequestConstant.NO)) {
					for (UserRole userRole2 : userRoleList) {
						RoleMenuFunction roleMenuFunction = new RoleMenuFunction();
						roleMenuFunction.setMenuno(systemMenuInfo2.getMenuno());
						roleMenuFunction.setRoleno(userRole2.getRoleno());
						List<FunctionInfo> functionInfoList2 = userRoleService
								.selectSerialFunctionInfoByRoleMenuFunction(roleMenuFunction);

						// 同名功能直接覆盖
						for (FunctionInfo functionInfo2 : functionInfoList2) {
							map.put(functionInfo2.getFucno(), functionInfo2);
						}
					}
				}else{
					MenuFunctionInfo menuFunction = new MenuFunctionInfo();
					menuFunction.setMenuno(systemMenuInfo2.getMenuno());
					List<FunctionInfo> functionInfoList2 = userRoleService
							.selectSerialFunctionInfoByMenuFunction(menuFunction);

					// 同名功能直接覆盖
					for (FunctionInfo functionInfo2 : functionInfoList2) {
						map.put(functionInfo2.getFucno(), functionInfo2);
					}
					
				}

				// 注销代码执行很慢，研究中……
				/*
				 * for (RoleMenuFunction roleMenuFunction2 :
				 * roleMenuFunctioniList) { FunctionInfo functionInfo = new
				 * FunctionInfo();
				 * functionInfo.setFucno(roleMenuFunction2.getFucno());
				 * functionInfo = userRoleService
				 * .selectSinglefunFunctionInfoByFucNo(functionInfo);
				 * 
				 * // 同名菜单自然覆盖 map.put(functionInfo.getFucno(), functionInfo); }
				 */

				menuFunctionMap.put(systemMenuInfo2.getMenuno(), map);
			}
		}
		respContent.put("menuFunctionMap", menuFunctionMap);

		RespBody<Map<String, Object>> respBody = new RespBody<Map<String, Object>>();
		respBody.setContent(respContent);
		return respBody;
	}
}
