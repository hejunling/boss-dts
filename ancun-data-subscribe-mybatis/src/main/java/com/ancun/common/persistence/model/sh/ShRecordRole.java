package com.ancun.common.persistence.model.sh;

import javax.persistence.*;

@Table(name = "record_role")
public class ShRecordRole {
    /**
     * 主键，角色ID
     */
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "ROLE_NAME")
    private String roleName;

    /**
     * 角色状态(Y:启用，N:禁用)
     */
    @Column(name = "ROLE_STATUS")
    private String roleStatus;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 用户编号
     */
    @Column(name = "USER_NO")
    private String userNo;

    /**
     * 获取主键，角色ID
     *
     * @return ID - 主键，角色ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键，角色ID
     *
     * @param id 主键，角色ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色名称
     *
     * @return ROLE_NAME - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取角色状态(Y:启用，N:禁用)
     *
     * @return ROLE_STATUS - 角色状态(Y:启用，N:禁用)
     */
    public String getRoleStatus() {
        return roleStatus;
    }

    /**
     * 设置角色状态(Y:启用，N:禁用)
     *
     * @param roleStatus 角色状态(Y:启用，N:禁用)
     */
    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    /**
     * 获取备注
     *
     * @return REMARK - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取用户编号
     *
     * @return USER_NO - 用户编号
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * 设置用户编号
     *
     * @param userNo 用户编号
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}