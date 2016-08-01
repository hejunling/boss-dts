package com.ancun.common.persistence.model.sh;

import javax.persistence.*;

@Table(name = "role_permisstion")
public class ShRolePermisstion {
    /**
     * 角色ID
     */
    @Column(name = "ROLE_ID")
    private Long roleId;

    /**
     * 权限ID
     */
    @Column(name = "PERMISSTION_ID")
    private Long permisstionId;

    /**
     * 获取角色ID
     *
     * @return ROLE_ID - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取权限ID
     *
     * @return PERMISSTION_ID - 权限ID
     */
    public Long getPermisstionId() {
        return permisstionId;
    }

    /**
     * 设置权限ID
     *
     * @param permisstionId 权限ID
     */
    public void setPermisstionId(Long permisstionId) {
        this.permisstionId = permisstionId;
    }
}