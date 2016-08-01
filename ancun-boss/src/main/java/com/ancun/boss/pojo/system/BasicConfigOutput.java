package com.ancun.boss.pojo.system;

import com.ancun.boss.persistence.model.SystemBasicConfig;
import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 基础数据请求输出类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/29
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BasicConfigOutput {

    private List<SystemBasicConfig> basicconfiglist;

    private Page pageinfo;

    public BasicConfigOutput() {
    }

    public BasicConfigOutput(List<SystemBasicConfig> basicconfiglist) {
        this.basicconfiglist = basicconfiglist;
    }

    public List<SystemBasicConfig> getBasicconfiglist() {
        return basicconfiglist;
    }

    public void setBasicconfiglist(List<SystemBasicConfig> basicconfiglist) {
        this.basicconfiglist = basicconfiglist;
    }

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }
}
