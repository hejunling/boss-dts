package com.ancun.boss.service.system;

import com.ancun.boss.persistence.model.SystemRoleInfo;
import com.ancun.boss.pojo.roleInfo.RoleInfoInput;
import com.ancun.boss.pojo.roleInfo.RoleInfoListOutput;
import com.ancun.boss.pojo.roleInfo.RolePermissionOutput;
import com.ancun.core.exception.EduException;

/**
 * 角色管理接口
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IRoleInfoService {

    /**
     * 分页查询角色列表
     *
     * @param roleInfoInput
     * @return
     * @throws EduException
     */
    public RoleInfoListOutput queryRoleList(RoleInfoInput roleInfoInput) throws EduException;

    /**
     * 查询角色详情
     *
     * @param roleno
     * @return
     * @throws EduException
     */
    public SystemRoleInfo queryRoleInfo(String roleno) throws EduException;

    /**
     * 新增或修改角色
     *
     * @param roleInfo
     * @throws EduException
     */
    public void addOrUpdateRoleInfo(SystemRoleInfo roleInfo) throws EduException;

    /**
     * 删除角色
     *
     * @param roleno
     * @throws EduException
     */
    public void deleteRoleInfo(String roleno) throws EduException;

    /**
     * 角色权限信息
     * @param roleno
     * @return
     * @throws EduException
     */
    public RolePermissionOutput rolePermission(String roleno) throws EduException;
}
