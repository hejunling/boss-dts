package com.ancun.common.persistence.model.master;

import javax.persistence.*;

@Table(name = "BIZ_PERSON_INFO")
public class BizPersonInfo {
    /**
     * 业务账号
     */
    @Id
    @Column(name = "USER_NO")
    private String userNo;

    /**
     * 性别(1:男;2:女)
     */
    @Column(name = "SEX")
    private String sex;

    /**
     * 身份证号
     */
    @Column(name = "IDENTIFY")
    private String identify;

    /**
     * 是否全包（TRUE/FALSE）
     */
    @Column(name = "FULLPACKAGE")
    private String fullpackage;

    /**
     * UUID唯一标识（JAVA程序生成）
     */
    @Column(name = "UNIQUENO")
    private String uniqueno;

    /**
     * 业务编号
     */
    @Column(name = "BIZ_NO")
    private String bizNo;

    /**
     * 获取业务账号
     *
     * @return USER_NO - 业务账号
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * 设置业务账号
     *
     * @param userNo 业务账号
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * 获取性别(1:男;2:女)
     *
     * @return SEX - 性别(1:男;2:女)
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别(1:男;2:女)
     *
     * @param sex 性别(1:男;2:女)
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取身份证号
     *
     * @return IDENTIFY - 身份证号
     */
    public String getIdentify() {
        return identify;
    }

    /**
     * 设置身份证号
     *
     * @param identify 身份证号
     */
    public void setIdentify(String identify) {
        this.identify = identify;
    }

    /**
     * 获取是否全包（TRUE/FALSE）
     *
     * @return FULLPACKAGE - 是否全包（TRUE/FALSE）
     */
    public String getFullpackage() {
        return fullpackage;
    }

    /**
     * 设置是否全包（TRUE/FALSE）
     *
     * @param fullpackage 是否全包（TRUE/FALSE）
     */
    public void setFullpackage(String fullpackage) {
        this.fullpackage = fullpackage;
    }

    /**
     * 获取UUID唯一标识（JAVA程序生成）
     *
     * @return UNIQUENO - UUID唯一标识（JAVA程序生成）
     */
    public String getUniqueno() {
        return uniqueno;
    }

    /**
     * 设置UUID唯一标识（JAVA程序生成）
     *
     * @param uniqueno UUID唯一标识（JAVA程序生成）
     */
    public void setUniqueno(String uniqueno) {
        this.uniqueno = uniqueno;
    }

    /**
     * 获取业务编号
     *
     * @return BIZ_NO - 业务编号
     */
    public String getBizNo() {
        return bizNo;
    }

    /**
     * 设置业务编号
     *
     * @param bizNo 业务编号
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }


    @Override
    public String toString() {
        return "BizPersonInfo{" +
                "userNo='" + userNo + '\'' +
                ", sex='" + sex + '\'' +
                ", identify='" + identify + '\'' +
                ", fullpackage='" + fullpackage + '\'' +
                ", uniqueno='" + uniqueno + '\'' +
                ", bizNo='" + bizNo + '\'' +
                '}';
    }
}