package com.ancun.boss.business.pojo.bizuser;

import com.ancun.boss.business.persistence.module.BizUserLifeCircle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 用户生命周期返回信息封装
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/4/19
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizUserLifeCircleListOutput {

    private List<BizUserLifeCircle> userLifeCircleList;

    public BizUserLifeCircleListOutput() {
    }

    public BizUserLifeCircleListOutput(List<BizUserLifeCircle> userLifeCircleList) {
        this.userLifeCircleList = userLifeCircleList;
    }

    public List<BizUserLifeCircle> getUserLifeCircleList() {
        return userLifeCircleList;
    }

    public void setUserLifeCircleList(List<BizUserLifeCircle> userLifeCircleList) {
        this.userLifeCircleList = userLifeCircleList;
    }
}
