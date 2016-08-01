package com.ancun.boss.pojo.userInfo;

import com.ancun.boss.persistence.model.SystemUserInfo;
import com.ancun.boss.pojo.BossPagePojo;
import com.ancun.core.domain.request.PageModel;
import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 用户列表返回封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/18
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class UserInfoListOutput  {

    /**
     * 用户信息列表
     */
    private List<UserInfoPojo> userinfolist;

    private Page pageinfo;

    public List<UserInfoPojo> getUserinfolist() {
        return userinfolist;
    }

    public void setUserinfolist(List<UserInfoPojo> userinfolist) {
        this.userinfolist = userinfolist;
    }

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }
}
