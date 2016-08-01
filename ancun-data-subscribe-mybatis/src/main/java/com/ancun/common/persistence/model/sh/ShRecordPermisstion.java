package com.ancun.common.persistence.model.sh;

import javax.persistence.*;

@Table(name = "record_permisstion")
public class ShRecordPermisstion {
    /**
     * 主键,权限ID
     */
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 部门编号
     */
    @Column(name = "ORG_NO")
    private String orgNo;

    /**
     * 权限名称
     */
    @Column(name = "PERMISSTION_NAME")
    private String permisstionName;

    /**
     * 权限类型(1:功能权限,2:数据权限)
     */
    @Column(name = "PERMISSTION_TYPE")
    private String permisstionType;

    /**
     * 权限代码
     */
    @Column(name = "PERMISSION_CODE")
    private String permissionCode;

    /**
     * 权限父ID
     */
    @Column(name = "PARENT_PER_ID")
    private Long parentPerId;

    /**
     * 排序顺序
     */
    @Column(name = "SORT_ORDER")
    private Long sortOrder;

    /**
     * 用户编号
     */
    @Column(name = "USER_NO")
    private String userNo;

    /**
     * 获取主键,权限ID
     *
     * @return ID - 主键,权限ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键,权限ID
     *
     * @param id 主键,权限ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取部门编号
     *
     * @return ORG_NO - 部门编号
     */
    public String getOrgNo() {
        return orgNo;
    }

    /**
     * 设置部门编号
     *
     * @param orgNo 部门编号
     */
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    /**
     * 获取权限名称
     *
     * @return PERMISSTION_NAME - 权限名称
     */
    public String getPermisstionName() {
        return permisstionName;
    }

    /**
     * 设置权限名称
     *
     * @param permisstionName 权限名称
     */
    public void setPermisstionName(String permisstionName) {
        this.permisstionName = permisstionName;
    }

    /**
     * 获取权限类型(1:功能权限,2:数据权限)
     *
     * @return PERMISSTION_TYPE - 权限类型(1:功能权限,2:数据权限)
     */
    public String getPermisstionType() {
        return permisstionType;
    }

    /**
     * 设置权限类型(1:功能权限,2:数据权限)
     *
     * @param permisstionType 权限类型(1:功能权限,2:数据权限)
     */
    public void setPermisstionType(String permisstionType) {
        this.permisstionType = permisstionType;
    }

    /**
     * 获取权限代码
     *
     * @return PERMISSION_CODE - 权限代码
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * 设置权限代码
     *
     * @param permissionCode 权限代码
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    /**
     * 获取权限父ID
     *
     * @return PARENT_PER_ID - 权限父ID
     */
    public Long getParentPerId() {
        return parentPerId;
    }

    /**
     * 设置权限父ID
     *
     * @param parentPerId 权限父ID
     */
    public void setParentPerId(Long parentPerId) {
        this.parentPerId = parentPerId;
    }

    /**
     * 获取排序顺序
     *
     * @return SORT_ORDER - 排序顺序
     */
    public Long getSortOrder() {
        return sortOrder;
    }

    /**
     * 设置排序顺序
     *
     * @param sortOrder 排序顺序
     */
    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
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