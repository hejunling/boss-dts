package com.ancun.boss.business.pojo.bizuser;

import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 企业用户添加子账号输出类
 * User: zkai
 * Date: 2016/4/25
 * Time: 21:11
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizEntSubAccountListOutPut  {

    private List<BizUserInfoOutput> bizEntSubAccountList;

    // 分页信息
    private Page pageinfo;

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }

    public List<BizUserInfoOutput> getBizEntSubAccountList() {
        return bizEntSubAccountList;
    }

    public void setBizEntSubAccountList(List<BizUserInfoOutput> bizEntSubAccountList) {
        this.bizEntSubAccountList = bizEntSubAccountList;
    }
}
