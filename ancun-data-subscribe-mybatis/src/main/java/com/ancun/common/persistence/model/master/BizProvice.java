package com.ancun.common.persistence.model.master;

import javax.persistence.*;

@Table(name = "BIZ_PROVICE")
public class BizProvice {
    /**
     * 业务代码
     */
    @Column(name = "biz_no")
    private String bizNo;

    /**
     * 省份代码
     */
    private String rpcode;

    /**
     * 获取业务代码
     *
     * @return biz_no - 业务代码
     */
    public String getBizNo() {
        return bizNo;
    }

    /**
     * 设置业务代码
     *
     * @param bizNo 业务代码
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    /**
     * 获取省份代码
     *
     * @return rpcode - 省份代码
     */
    public String getRpcode() {
        return rpcode;
    }

    /**
     * 设置省份代码
     *
     * @param rpcode 省份代码
     */
    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }
}