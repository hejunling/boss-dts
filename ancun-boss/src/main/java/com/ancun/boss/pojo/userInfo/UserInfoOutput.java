package com.ancun.boss.pojo.userInfo;

import com.ancun.boss.persistence.model.*;
import com.ancun.boss.pojo.BossBasePojo;

import java.util.List;

/**
 * 用户信息返回 封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/23
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class UserInfoOutput extends BossBasePojo {

    /**
     * 用户信息
     */
    private SystemUserInfo userinfo;
    /**
     * 用户角色信息
     */
    private List<SystemRoleInfo> rolelist;

    /**
     * 用户数据权限
     */
    private List<SystemBasicConfig> datapermissionlist;


    public SystemUserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(SystemUserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public List<SystemRoleInfo> getRolelist() {
        return rolelist;
    }

    public void setRolelist(List<SystemRoleInfo> rolelist) {
        this.rolelist = rolelist;
    }

    public List<SystemBasicConfig> getDatapermissionlist() {
        return datapermissionlist;
    }

    public void setDatapermissionlist(List<SystemBasicConfig> datapermissionlist) {
        this.datapermissionlist = datapermissionlist;
    }
}
