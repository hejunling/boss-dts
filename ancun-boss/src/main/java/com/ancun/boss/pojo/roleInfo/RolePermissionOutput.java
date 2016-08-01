package com.ancun.boss.pojo.roleInfo;

import com.ancun.boss.persistence.model.SystemMenuInfo;

import java.util.List;

/**
 * 角色 菜单权限
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/10/12
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class RolePermissionOutput {

    /**
     * 菜单列表
     */
    public List<SystemMenuInfo> menuinfolist;

    public List<SystemMenuInfo> getMenuinfolist() {
        return menuinfolist;
    }

    public void setMenuinfolist(List<SystemMenuInfo> menuinfolist) {
        this.menuinfolist = menuinfolist;
    }
}