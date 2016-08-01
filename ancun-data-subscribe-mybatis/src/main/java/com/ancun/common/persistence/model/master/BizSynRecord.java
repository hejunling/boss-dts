package com.ancun.common.persistence.model.master;

import java.util.Date;
import javax.persistence.*;

@Table(name = "BIZ_SYN_RECORD")
public class BizSynRecord {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    private Integer id;

    /**
     * 模块(user,voice,combo)
     */
    @Column(name = "MOUDLE")
    private String moudle;

    /**
     * 同步开始时间
     */
    @Column(name = "SYN_START_TIMER")
    private Date synStartTimer;

    /**
     * 同步结束时间
     */
    @Column(name = "SYN_END_TIMER")
    private Date synEndTimer;

    /**
     * 省份代码
     */
    @Column(name = "RPCODE")
    private String rpcode;

    /**
     * 城市代码
     */
    @Column(name = "CITYCODE")
    private String citycode;

    /**
     * 同步数量
     */
    @Column(name = "SYN_COUNT")
    private Integer synCount;

    /**
     * 同步成功数量(失败的发邮件)
     */
    @Column(name = "SYN_OK_COUNT")
    private Integer synOkCount;


    /**
     * 同步成功数量(失败的发邮件)
     */
    @Column(name = "BIZ_NAME")
    private String bizName;
    /**
     * 获取主键ID
     *
     * @return ID - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取模块(user,voice,combo)
     *
     * @return MOUDLE - 模块(user,voice,combo)
     */
    public String getMoudle() {
        return moudle;
    }

    /**
     * 设置模块(user,voice,combo)
     *
     * @param moudle 模块(user,voice,combo)
     */
    public void setMoudle(String moudle) {
        this.moudle = moudle;
    }

    /**
     * 获取同步开始时间
     *
     * @return SYN_START_TIMER - 同步开始时间
     */
    public Date getSynStartTimer() {
        return synStartTimer;
    }

    /**
     * 设置同步开始时间
     *
     * @param synStartTimer 同步开始时间
     */
    public void setSynStartTimer(Date synStartTimer) {
        this.synStartTimer = synStartTimer;
    }

    /**
     * 获取同步结束时间
     *
     * @return SYN_END_TIMER - 同步结束时间
     */
    public Date getSynEndTimer() {
        return synEndTimer;
    }

    /**
     * 设置同步结束时间
     *
     * @param synEndTimer 同步结束时间
     */
    public void setSynEndTimer(Date synEndTimer) {
        this.synEndTimer = synEndTimer;
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
     * 获取城市代码
     *
     * @return CITYCODE - 城市代码
     */
    public String getCitycode() {
        return citycode;
    }

    /**
     * 设置城市代码
     *
     * @param citycode 城市代码
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    /**
     * 获取同步数量
     *
     * @return SYN_COUNT - 同步数量
     */
    public Integer getSynCount() {
        return synCount;
    }

    /**
     * 设置同步数量
     *
     * @param synCount 同步数量
     */
    public void setSynCount(Integer synCount) {
        this.synCount = synCount;
    }

    /**
     * 获取同步成功数量(失败的发邮件)
     *
     * @return SYN_OK_COUNT - 同步成功数量(失败的发邮件)
     */
    public Integer getSynOkCount() {
        return synOkCount;
    }

    /**
     * 设置同步成功数量(失败的发邮件)
     *
     * @param synOkCount 同步成功数量(失败的发邮件)
     */
    public void setSynOkCount(Integer synOkCount) {
        this.synOkCount = synOkCount;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    @Override
    public String toString() {
        return "BizSynRecord{" +
                "id=" + id +
                ", moudle='" + moudle + '\'' +
                ", synStartTimer=" + synStartTimer +
                ", synEndTimer=" + synEndTimer +
                ", rpcode='" + rpcode + '\'' +
                ", citycode='" + citycode + '\'' +
                ", synCount=" + synCount +
                ", synOkCount=" + synOkCount +
                ", bizName=" + bizName +
                '}';
    }
}