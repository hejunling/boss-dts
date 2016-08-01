package com.ancun.common.persistence.model.sh;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_account_log")
public class ShUserAccountLog {
    /**
     * 编号
     */
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 用户编号
     */
    @Column(name = "USER_NO")
    private String userNo;

    /**
     * 电话
     */
    @Column(name = "USER_TEL")
    private String userTel;

    /**
     * 个人帐号转主账号时间
     */
    @Column(name = "CHANGE_DATE")
    private Date changeDate;

    /**
     * 过去帐号类型
     */
    @Column(name = "OLD_TYPE")
    private String oldType;

    /**
     * 新帐号类型
     */
    @Column(name = "NEW_TYPE")
    private String newType;

    /**
     * 获取编号
     *
     * @return ID - 编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(Long id) {
        this.id = id;
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

    /**
     * 获取电话
     *
     * @return USER_TEL - 电话
     */
    public String getUserTel() {
        return userTel;
    }

    /**
     * 设置电话
     *
     * @param userTel 电话
     */
    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    /**
     * 获取个人帐号转主账号时间
     *
     * @return CHANGE_DATE - 个人帐号转主账号时间
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * 设置个人帐号转主账号时间
     *
     * @param changeDate 个人帐号转主账号时间
     */
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * 获取过去帐号类型
     *
     * @return OLD_TYPE - 过去帐号类型
     */
    public String getOldType() {
        return oldType;
    }

    /**
     * 设置过去帐号类型
     *
     * @param oldType 过去帐号类型
     */
    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    /**
     * 获取新帐号类型
     *
     * @return NEW_TYPE - 新帐号类型
     */
    public String getNewType() {
        return newType;
    }

    /**
     * 设置新帐号类型
     *
     * @param newType 新帐号类型
     */
    public void setNewType(String newType) {
        this.newType = newType;
    }
}