package com.ancun.common.persistence.model.master;

import com.google.common.base.MoreObjects;

import javax.persistence.*;

public class Subscribe {
    /**
     * 主键[自增长]
     */
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 阿里云ACCESSKEY
     */
    @Column(name = "ACCESS_KEY")
    private String accessKey;

    /**
     * 阿里云ACCESSSECRET
     */
    @Column(name = "ACCESS_SECRET")
    private String accessSecret;

    /**
     * 是否使用公网IP（0不使用，1使用）
     */
    @Column(name = "USE_PUBLIC_IP")
    private Integer usePublicIp;

    /**
     * 订阅通道ID
     */
    @Column(name = "GUID")
    private String guid;

    /**
     * 状态（0启用，1停用）
     */
    @Column(name = "STATUS")
    private Integer status;

    /**
     * 运行环境指定（dev：开发环境，test：测试环境）
     */
    @Column(name = "PROFILE")
    private String profile;

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
     * 获取阿里云ACCESSKEY
     *
     * @return ACCESS_KEY - 阿里云ACCESSKEY
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * 设置阿里云ACCESSKEY
     *
     * @param accessKey 阿里云ACCESSKEY
     */
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    /**
     * 获取阿里云ACCESSSECRET
     *
     * @return ACCESS_SECRET - 阿里云ACCESSSECRET
     */
    public String getAccessSecret() {
        return accessSecret;
    }

    /**
     * 设置阿里云ACCESSSECRET
     *
     * @param accessSecret 阿里云ACCESSSECRET
     */
    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    /**
     * 获取是否使用公网IP（0不使用，1使用）
     *
     * @return USE_PUBLIC_IP - 是否使用公网IP（0不使用，1使用）
     */
    public Integer getUsePublicIp() {
        return usePublicIp;
    }

    /**
     * 设置是否使用公网IP（0不使用，1使用）
     *
     * @param usePublicIp 是否使用公网IP（0不使用，1使用）
     */
    public void setUsePublicIp(Integer usePublicIp) {
        this.usePublicIp = usePublicIp;
    }

    /**
     * 获取订阅通道ID
     *
     * @return GUID - 订阅通道ID
     */
    public String getGuid() {
        return guid;
    }

    /**
     * 设置订阅通道ID
     *
     * @param guid 订阅通道ID
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * 获取状态（0启用，1停用）
     *
     * @return STATUS - 状态（0启用，1停用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态（0启用，1停用）
     *
     * @param status 状态（0启用，1停用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取运行环境指定（dev：开发环境，test：测试环境）
     *
     * @return PROFILE - 运行环境指定（dev：开发环境，test：测试环境）
     */
    public String getProfile() {
        return profile;
    }

    /**
     * 设置运行环境指定（dev：开发环境，test：测试环境）
     *
     * @param profile 运行环境指定（dev：开发环境，test：测试环境）
     */
    public void setProfile(String profile) {
        this.profile = profile;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("DTS监听通道信息[SUBSCRIBE]")
                .add("id", id)
                .add("accessKey", accessKey)
                .add("accessSecret", accessSecret)
                .add("usePublicIp", usePublicIp)
                .add("guid", guid)
                .add("status", status)
                .add("profile", profile)
                .add("description", description)
                .toString();
    }
}