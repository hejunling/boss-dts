package com.ancun.common.persistence.model.master;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "BIZ_COMBO_INFO")
public class BizComboInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 套餐ID
     */
    @Column(name = "TCID")
    private Long tcid;

    /**
     * 套餐名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 价格(月/元)
     */
    @Column(name = "PRICE")
    private BigDecimal price;

    /**
     * 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 停用时间
     */
    @Column(name = "FINISH_TIME")
    private Date finishTime;

    /**
     * 存储空间
     */
    @Column(name = "SPACE")
    private Long space;

    /**
     * 业务编号
     */
    @Column(name = "BIZ_NO")
    private String bizNo;

    /**
     * 状态(1:启用;2:停用)1
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 存储时间
     */
    @Column(name = "DURATION")
    private String duration;

    /**
     * 套餐类别(1:存储空间;2:存储时间)
     */
    @Column(name = "CATEGORY")
    private String category;

    /**
     * 套餐标记默认为NULL(1:默认套餐)
     */
    @Column(name = "DEFAULT_TAOCAN")
    private String defaultTaocan;

    /**
     * 省份代码
     */
    @Column(name = "RPCODE")
    private String rpcode;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return NAME - 套餐名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置套餐名称
     *
     * @param name 套餐名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取价格(月/元)
     *
     * @return PRICE - 价格(月/元)
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格(月/元)
     *
     * @param price 价格(月/元)
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     *
     * @return TYPE - 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     */
    public String getType() {
        return type;
    }

    /**
     * 设置套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     *
     * @param type 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取停用时间
     *
     * @return FINISH_TIME - 停用时间
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * 设置停用时间
     *
     * @param finishTime 停用时间
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 获取存储空间
     *
     * @return SPACE - 存储空间
     */
    public Long getSpace() {
        return space;
    }

    /**
     * 设置存储空间
     *
     * @param space 存储空间
     */
    public void setSpace(Long space) {
        this.space = space;
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

    /**
     * 获取状态(1:启用;2:停用)1
     *
     * @return STATUS - 状态(1:启用;2:停用)1
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态(1:启用;2:停用)1
     *
     * @param status 状态(1:启用;2:停用)1
     */
    public void setStatus(String status) {
        this.status = status;
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
     * 获取存储时间
     *
     * @return DURATION - 存储时间
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 设置存储时间
     *
     * @param duration 存储时间
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * 获取套餐类别(1:存储空间;2:存储时间)
     *
     * @return CATEGORY - 套餐类别(1:存储空间;2:存储时间)
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置套餐类别(1:存储空间;2:存储时间)
     *
     * @param category 套餐类别(1:存储空间;2:存储时间)
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取套餐标记默认为NULL(1:默认套餐)
     *
     * @return DEFAULT_TAOCAN - 套餐标记默认为NULL(1:默认套餐)
     */
    public String getDefaultTaocan() {
        return defaultTaocan;
    }

    /**
     * 设置套餐标记默认为NULL(1:默认套餐)
     *
     * @param defaultTaocan 套餐标记默认为NULL(1:默认套餐)
     */
    public void setDefaultTaocan(String defaultTaocan) {
        this.defaultTaocan = defaultTaocan;
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

    @Override
    public String toString() {
        return "BizComboInfo{" +
                "id=" + id +
                ", tcid=" + tcid +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", rpcode='" + rpcode + '\'' +
                '}';
    }
}