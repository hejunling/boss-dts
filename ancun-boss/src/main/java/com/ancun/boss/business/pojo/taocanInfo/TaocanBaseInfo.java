package com.ancun.boss.business.pojo.taocanInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: zkai
 * Date: 2016/4/5
 * Time: 16:58
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

@XStreamAlias("taocaninfo")
public class TaocanBaseInfo {
    /**
     * 套餐ID
     */
    private Long tcid;

    /**
     * 套餐名称
     */
    private String taocanname;

    /**
     * 套餐价格
     */
    private BigDecimal taocanprice;

    /**
     * 套餐功能
     */
    private String taocantype;

    /**
     * 存储空间
     */
    private String storespace;

    /**
     * 套餐说明
     */
    private String taocanremark;

    /**
     * 省份编号
     */
    private String rpcode;

    /**
     * 省份名称
     */
    private String rpname;

    /**
     * 套餐时长
     */
    private String tcduration;

    /**
     * 默认套餐
     * @return
     */
    private String tcflag;

    /**
     * 业务编号
     */
    private String bizno;

    /**
     * 业务名称
     */
    private String bizname;

    public Long getTcid() {
        return tcid;
    }

    public void setTcid(Long tcid) {
        this.tcid = tcid;
    }

    public String getTaocanname() {
        return taocanname;
    }

    public void setTaocanname(String taocanname) {
        this.taocanname = taocanname;
    }

    public BigDecimal getTaocanprice() {
        return taocanprice;
    }

    public void setTaocanprice(BigDecimal taocanprice) {
        this.taocanprice = taocanprice;
    }

    public String getTaocantype() {
        return taocantype;
    }

    public void setTaocantype(String taocantype) {
        this.taocantype = taocantype;
    }

    public String getStorespace() {
        return storespace;
    }

    public void setStorespace(String storespace) {
        this.storespace = storespace;
    }

    public String getTaocanremark() {
        return taocanremark;
    }

    public void setTaocanremark(String taocanremark) {
        this.taocanremark = taocanremark;
    }

    public String getRpcode() {
        return rpcode;
    }

    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }

    public String getRpname() {
        return rpname;
    }

    public void setRpname(String rpname) {
        this.rpname = rpname;
    }

    public String getTcduration() {
        return tcduration;
    }

    public void setTcduration(String tcduration) {
        this.tcduration = tcduration;
    }

    public String getTcflag() {
        return tcflag;
    }

    public void setTcflag(String tcflag) {
        this.tcflag = tcflag;
    }

    public String getBizno() {
        return bizno;
    }

    public void setBizno(String bizno) {
        this.bizno = bizno;
    }

    public String getBizname() {
        return bizname;
    }

    public void setBizname(String bizname) {
        this.bizname = bizname;
    }
}
