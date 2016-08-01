package com.ancun.boss.pojo.roleInfo;

import com.ancun.boss.persistence.model.SystemRoleInfo;
import com.ancun.boss.pojo.BossPagePojo;
import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 角色管理请求响应封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class RoleInfoListOutput {

    private List<SystemRoleInfo> roleinfolist;

    private Page pageinfo;

    public List<SystemRoleInfo> getRoleinfolist() {
        return roleinfolist;
    }

    public void setRoleinfolist(List<SystemRoleInfo> roleinfolist) {
        this.roleinfolist = roleinfolist;
    }

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }
}
