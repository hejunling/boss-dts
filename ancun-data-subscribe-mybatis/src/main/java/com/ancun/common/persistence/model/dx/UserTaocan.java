package com.ancun.common.persistence.model.dx;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "USER_TAOCAN")
public class UserTaocan {
    /**
     * 套餐ID
     */
    @Id
    @Column(name = "TCID")
    private Long tcid;

    /**
     * 套餐名称
     */
    @Column(name = "TAOCAN_NAME")
    private String taocanName;

    /**
     * 套餐价格（月/元）
     */
    @Column(name = "TAOCAN_PRICE")
    private BigDecimal taocanPrice;

    /**
     * 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     */
    @Column(name = "TAOCAN_TYPE")
    private String taocanType;

    /**
     * 套餐创建时间
     */
    @Column(name = "TAOCAN_CREATETIME")
    private Date taocanCreatetime;

    /**
     * 套餐停用时间
     */
    @Column(name = "TAOCAN_FINISHTIME")
    private Date taocanFinishtime;

    /**
     * 存储空间
     */
    @Column(name = "STORE_SPACE")
    private String storeSpace;

    /**
     * 排序(越小越靠前)
     */
    @Column(name = "TAOCAN_ORDER")
    private Integer taocanOrder;

    /**
     * 状态(1:启用;2:停用)
     */
    @Column(name = "TAOCAN_STATUS")
    private String taocanStatus;

    /**
     * 套餐服务说明
     */
    @Column(name = "TAOCAN_SERVICE")
    private String taocanService;

    /**
     * 备注
     */
    @Column(name = "TAOCAN_REMARK")
    private String taocanRemark;

    /**
     * 省份代码
     */
    @Column(name = "RPCODE")
    private String rpcode;

    /**
     * 套餐时长
     */
    @Column(name = "TCDURATION")
    private String tcduration;

    /**
     * 套餐标记，默认为NULL-->(1:否认套餐；后续编号依次递增)
     */
    @Column(name = "TC_FLAG")
    private String tcFlag;

    /**
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取套餐ID
     *
     * @return TCID - 套餐ID
     */
    public Long getTcid() {
        return tcid;
    }

    /**
     * 设置套餐ID
     *
     * @param tcid 套餐ID
     */
    public void setTcid(Long tcid) {
        this.tcid = tcid;
    }

    /**
     * 获取套餐名称
     *
     * @return TAOCAN_NAME - 套餐名称
     */
    public String getTaocanName() {
        return taocanName;
    }

    /**
     * 设置套餐名称
     *
     * @param taocanName 套餐名称
     */
    public void setTaocanName(String taocanName) {
        this.taocanName = taocanName;
    }

    /**
     * 获取套餐价格（月/元）
     *
     * @return TAOCAN_PRICE - 套餐价格（月/元）
     */
    public BigDecimal getTaocanPrice() {
        return taocanPrice;
    }

    /**
     * 设置套餐价格（月/元）
     *
     * @param taocanPrice 套餐价格（月/元）
     */
    public void setTaocanPrice(BigDecimal taocanPrice) {
        this.taocanPrice = taocanPrice;
    }

    /**
     * 获取套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     *
     * @return TAOCAN_TYPE - 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     */
    public String getTaocanType() {
        return taocanType;
    }

    /**
     * 设置套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     *
     * @param taocanType 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     */
    public void setTaocanType(String taocanType) {
        this.taocanType = taocanType;
    }

    /**
     * 获取套餐创建时间
     *
     * @return TAOCAN_CREATETIME - 套餐创建时间
     */
    public Date getTaocanCreatetime() {
        return taocanCreatetime;
    }

    /**
     * 设置套餐创建时间
     *
     * @param taocanCreatetime 套餐创建时间
     */
    public void setTaocanCreatetime(Date taocanCreatetime) {
        this.taocanCreatetime = taocanCreatetime;
    }

    /**
     * 获取套餐停用时间
     *
     * @return TAOCAN_FINISHTIME - 套餐停用时间
     */
    public Date getTaocanFinishtime() {
        return taocanFinishtime;
    }

    /**
     * 设置套餐停用时间
     *
     * @param taocanFinishtime 套餐停用时间
     */
    public void setTaocanFinishtime(Date taocanFinishtime) {
        this.taocanFinishtime = taocanFinishtime;
    }

    /**
     * 获取存储空间
     *
     * @return STORE_SPACE - 存储空间
     */
    public String getStoreSpace() {
        return storeSpace;
    }

    /**
     * 设置存储空间
     *
     * @param storeSpace 存储空间
     */
    public void setStoreSpace(String storeSpace) {
        this.storeSpace = storeSpace;
    }

    /**
     * 获取排序(越小越靠前)
     *
     * @return TAOCAN_ORDER - 排序(越小越靠前)
     */
    public Integer getTaocanOrder() {
        return taocanOrder;
    }

    /**
     * 设置排序(越小越靠前)
     *
     * @param taocanOrder 排序(越小越靠前)
     */
    public void setTaocanOrder(Integer taocanOrder) {
        this.taocanOrder = taocanOrder;
    }

    /**
     * 获取状态(1:启用;2:停用)
     *
     * @return TAOCAN_STATUS - 状态(1:启用;2:停用)
     */
    public String getTaocanStatus() {
        return taocanStatus;
    }

    /**
     * 设置状态(1:启用;2:停用)
     *
     * @param taocanStatus 状态(1:启用;2:停用)
     */
    public void setTaocanStatus(String taocanStatus) {
        this.taocanStatus = taocanStatus;
    }

    /**
     * 获取套餐服务说明
     *
     * @return TAOCAN_SERVICE - 套餐服务说明
     */
    public String getTaocanService() {
        return taocanService;
    }

    /**
     * 设置套餐服务说明
     *
     * @param taocanService 套餐服务说明
     */
    public void setTaocanService(String taocanService) {
        this.taocanService = taocanService;
    }

    /**
     * 获取备注
     *
     * @return TAOCAN_REMARK - 备注
     */
    public String getTaocanRemark() {
        return taocanRemark;
    }

    /**
     * 设置备注
     *
     * @param taocanRemark 备注
     */
    public void setTaocanRemark(String taocanRemark) {
        this.taocanRemark = taocanRemark;
    }

    /**
     * 获取省份代码
     *
     * @return RPCODE - 省份代码
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

    /**
     * 获取套餐时长
     *
     * @return TCDURATION - 套餐时长
     */
    public String getTcduration() {
        return tcduration;
    }

    /**
     * 设置套餐时长
     *
     * @param tcduration 套餐时长
     */
    public void setTcduration(String tcduration) {
        this.tcduration = tcduration;
    }

    /**
     * 获取套餐标记，默认为NULL-->(1:否认套餐；后续编号依次递增)
     *
     * @return TC_FLAG - 套餐标记，默认为NULL-->(1:否认套餐；后续编号依次递增)
     */
    public String getTcFlag() {
        return tcFlag;
    }

    /**
     * 设置套餐标记，默认为NULL-->(1:否认套餐；后续编号依次递增)
     *
     * @param tcFlag 套餐标记，默认为NULL-->(1:否认套餐；后续编号依次递增)
     */
    public void setTcFlag(String tcFlag) {
        this.tcFlag = tcFlag;
    }

    @Override
    public String toString() {
        return "UserTaocan{" +
                "tcid=" + tcid +
                ", taocanName='" + taocanName + '\'' +
                ", taocanPrice=" + taocanPrice +
                ", rpcode='" + rpcode + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}