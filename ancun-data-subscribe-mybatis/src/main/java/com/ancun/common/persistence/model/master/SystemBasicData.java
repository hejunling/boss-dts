package com.ancun.common.persistence.model.master;

import javax.persistence.*;

@Table(name = "system_basic_data")
public class SystemBasicData {
    /**
     * 主键[自增长]
     */
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 模块(config,message)
     */
    @Column(name = "MODULE")
    private String module;

    /**
     * 配置键
     */
    @Column(name = "CONF_KEY")
    private String confKey;

    /**
     * 配置值
     */
    @Column(name = "CONF_VALUE")
    private String confValue;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 获取主键[自增长]
     *
     * @return ID - 主键[自增长]
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键[自增长]
     *
     * @param id 主键[自增长]
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取模块(config,message)
     *
     * @return MODULE - 模块(config,message)
     */
    public String getModule() {
        return module;
    }

    /**
     * 设置模块(config,message)
     *
     * @param module 模块(config,message)
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 获取配置键
     *
     * @return CONF_KEY - 配置键
     */
    public String getConfKey() {
        return confKey;
    }

    /**
     * 设置配置键
     *
     * @param confKey 配置键
     */
    public void setConfKey(String confKey) {
        this.confKey = confKey;
    }

    /**
     * 获取配置值
     *
     * @return CONF_VALUE - 配置值
     */
    public String getConfValue() {
        return confValue;
    }

    /**
     * 设置配置值
     *
     * @param confValue 配置值
     */
    public void setConfValue(String confValue) {
        this.confValue = confValue;
    }

    /**
     * 获取描述
     *
     * @return DESCRIPTION - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}