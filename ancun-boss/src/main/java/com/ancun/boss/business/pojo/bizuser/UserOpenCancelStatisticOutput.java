package com.ancun.boss.business.pojo.bizuser;

import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 用户开通退订统计输出类
 * User: zkai
 * Date: 2016/6/16
 * Time: 16:36
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class UserOpenCancelStatisticOutput {
    /**
     * 分页信息
     */
    private Page pageinfo;
    private List<UserOpenCancelStatisticInfo> userOpenCancelStatisticInfoList ;

    public List<UserOpenCancelStatisticInfo> getUserOpenCancelStatisticInfoList() {
        return userOpenCancelStatisticInfoList;
    }

    public void setUserOpenCancelStatisticInfoList(List<UserOpenCancelStatisticInfo> userOpenCancelStatisticInfoList) {
        this.userOpenCancelStatisticInfoList = userOpenCancelStatisticInfoList;
    }

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }
}
