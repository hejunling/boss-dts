package com.ancun.boss.pojo.userInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 用户列表 响应封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/29
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XStreamAlias("userinfo")
public class UserInfoPojo {

    /**
     * 工号
     */
    private String userno;

    /**
     * 姓名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 简拼
     */
    private String spell;

    /**
     * 职务
     */
    private String job;

    /**
     * 部门名称
     */
    private String orgname;

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
