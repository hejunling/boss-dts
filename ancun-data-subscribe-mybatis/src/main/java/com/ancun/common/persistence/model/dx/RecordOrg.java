package com.ancun.common.persistence.model.dx;

import javax.persistence.*;

@Table(name = "RECORD_ORG")
public class RecordOrg {
    /**
     * 部门编号[UUID]
     */
    @Id
    @Column(name = "ORG_NO")
    private String orgNo;

    /**
     * 部门名称
     */
    @Column(name = "ORG_NAME")
    private String orgName;

    /**
     * 父级部门ID
     */
    @Column(name = "PARENT_ORG_NO")
    private String parentOrgNo;

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
     * 获取部门编号[UUID]
     *
     * @return ORG_NO - 部门编号[UUID]
     */
    public String getOrgNo() {
        return orgNo;
    }

    /**
     * 设置部门编号[UUID]
     *
     * @param orgNo 部门编号[UUID]
     */
    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    /**
     * 获取部门名称
     *
     * @return ORG_NAME - 部门名称
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置部门名称
     *
     * @param orgName 部门名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取父级部门ID
     *
     * @return PARENT_ORG_NO - 父级部门ID
     */
    public String getParentOrgNo() {
        return parentOrgNo;
    }

    /**
     * 设置父级部门ID
     *
     * @param parentOrgNo 父级部门ID
     */
    public void setParentOrgNo(String parentOrgNo) {
        this.parentOrgNo = parentOrgNo;
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