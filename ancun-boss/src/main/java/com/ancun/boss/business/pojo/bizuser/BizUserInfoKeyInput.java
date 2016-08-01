package com.ancun.boss.business.pojo.bizuser;

import com.ancun.boss.pojo.BossPagePojo;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * 用户列表查询输入类
 * User: zkai
 * Date: 2016/4/7
 * Time: 16:44
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizUserInfoKeyInput extends BossPagePojo {
    /**
     * 主键
     */
    @NotNull
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
