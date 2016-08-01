package com.ancun.common.persistence.model.master;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "BIZ_TIMER_CONFIG")
public class BizTimerConfig {
    /**
     * 基础数据编号
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
     * 间隔时长（分钟）
     */
    @Column(name = "INTERVAL_MINUTE")
    private Integer intervalMinute;

    /**
     * 获取基础数据编号
     *
     * @return ID - 基础数据编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置基础数据编号
     *
     * @param id 基础数据编号
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
     * 获取间隔时长（分钟）
     *
     * @return INTERVAL_MINUTE - 间隔时长（分钟）
     */
    public Integer getIntervalMinute() {
        return intervalMinute;
    }

    /**
     * 设置间隔时长（分钟）
     *
     * @param intervalMinute 间隔时长（分钟）
     */
    public void setIntervalMinute(Integer intervalMinute) {
        this.intervalMinute = intervalMinute;
    }
}