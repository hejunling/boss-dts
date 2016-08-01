package com.ancun.common.persistence.model.master;

import javax.persistence.*;

@Table(name = "BIZ_ENT_INFO")
public class BizEntInfo {
    /**
     * 企业编号
     */
    @Column(name = "ENT_NO")
    private String entNo;

    /**
     * 工商注册号
     */
    @Column(name = "REG_NO")
    private String regNo;

    /**
     * 公司名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 公司地址
     */
    @Column(name = "ADDRESS")
    private String address;

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
     * 获取企业编号
     *
     * @return ENT_NO - 企业编号
     */
    public String getEntNo() {
        return entNo;
    }

    /**
     * 设置企业编号
     *
     * @param entNo 企业编号
     */
    public void setEntNo(String entNo) {
        this.entNo = entNo;
    }

    /**
     * 获取工商注册号
     *
     * @return REG_NO - 工商注册号
     */
    public String getRegNo() {
        return regNo;
    }

    /**
     * 设置工商注册号
     *
     * @param regNo 工商注册号
     */
    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    /**
     * 获取公司名
     *
     * @return NAME - 公司名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置公司名
     *
     * @param name 公司名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取公司地址
     *
     * @return ADDRESS - 公司地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置公司地址
     *
     * @param address 公司地址
     */
    public void setAddress(String address) {
        this.address = address;
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
        return "BizEntInfo{" +
                "entNo='" + entNo + '\'' +
                ", regNo='" + regNo + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", fullpackage='" + fullpackage + '\'' +
                ", uniqueno='" + uniqueno + '\'' +
                ", bizNo='" + bizNo + '\'' +
                '}';
    }
}