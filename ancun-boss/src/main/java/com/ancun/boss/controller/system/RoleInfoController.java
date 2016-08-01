package com.ancun.boss.controller.system;

import com.ancun.boss.persistence.model.SystemRoleInfo;
import com.ancun.boss.pojo.roleInfo.RoleInfoInput;
import com.ancun.boss.pojo.roleInfo.RoleInfoListOutput;
import com.ancun.boss.pojo.roleInfo.RolePermissionOutput;
import com.ancun.boss.service.system.IRoleInfoService;
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

/**
 * 角色管理
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class RoleInfoController extends BaseController {

    @Resource
    private IRoleInfoService roleInfoService;

    /**
     * 角色列表
     *
     * @param roleInfoReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/queryRoleList", method = RequestMethod.POST)
    public RespBody<RoleInfoListOutput> queryRoleList(ReqBody<RoleInfoInput> roleInfoReqBody) throws EduException {
        RoleInfoInput roleInfo = roleInfoReqBody.getContent();
        RoleInfoListOutput roleInfoListOutput = roleInfoService.queryRoleList(roleInfo);
        return new RespBody<RoleInfoListOutput>(roleInfoListOutput);
    }

    /**
     * 查询角色详情
     *
     * @param roleInfoReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/queryRoleInfo", method = RequestMethod.POST)
    public RespBody<SystemRoleInfo> queryRoleInfo(ReqBody<SystemRoleInfo> roleInfoReqBody) throws EduException {
        SystemRoleInfo roleInfo = roleInfoReqBody.getContent();
        return new RespBody<SystemRoleInfo>(roleInfoService.queryRoleInfo(roleInfo.getRoleno()));
    }

    /**
     * 新增或修改角色
     *
     * @param roleInfoReqBody
     * @return
     * @throws Exception
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/addOrUpdateRoleInfo", method = RequestMethod.POST)
    public RespBody<String> addOrUPdateRoleInfo(ReqBody<SystemRoleInfo> roleInfoReqBody) throws Exception {
        SystemRoleInfo roleInfo = roleInfoReqBody.getContent();
        roleInfoService.addOrUpdateRoleInfo(roleInfo);
        return new RespBody<String>();
    }

    /**
     * 删除角色
     *
     * @param roleInfoReqBody
     * @return
     * @throws Exception
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/deleteRoleInfo", method = RequestMethod.POST)
    public RespBody<String> deleteRoleInfo(ReqBody<SystemRoleInfo> roleInfoReqBody) throws Exception {
        SystemRoleInfo roleInfo = roleInfoReqBody.getContent();
        roleInfoService.deleteRoleInfo(roleInfo.getRoleno());
        return new RespBody<String>();
    }

    /**
     * 获取角色权限
     *
     * @param roleInfoReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/rolePermission", method = RequestMethod.POST)
    public RespBody<RolePermissionOutput> rolePermission(ReqBody<SystemRoleInfo> roleInfoReqBody) throws EduException {
        SystemRoleInfo roleInfo = roleInfoReqBody.getContent();
        RolePermissionOutput output = roleInfoService.rolePermission(roleInfo.getRoleno());
        return new RespBody<RolePermissionOutput>(output);
    }
}

