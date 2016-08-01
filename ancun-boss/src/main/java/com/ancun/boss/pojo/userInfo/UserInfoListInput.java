package com.ancun.boss.pojo.userInfo;

import com.ancun.boss.pojo.BossPagePojo;

/** 用户列表查询 请求封装类
 * @author mif
 * @version 1.0
 * @Created on 2015/10/27
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class UserInfoListInput extends BossPagePojo {

    /**
     * 查询工号
     */
    private String jobno;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 部门
     */
    private String orgno;

    /**
     * 职务
     */
    private String job;

    public String getJobno() {
        return jobno;
    }

    public void setJobno(String jobno) {
        this.jobno = jobno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrgno() {
        return orgno;
    }

    public void setOrgno(String orgno) {
        this.orgno = orgno;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
