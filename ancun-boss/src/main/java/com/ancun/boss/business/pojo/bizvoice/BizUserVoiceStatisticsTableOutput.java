package com.ancun.boss.business.pojo.bizvoice;

import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 用户录音列表统计输出列表对象（月，半年，年，季度数量统计)
 * User: zkai
 * Date: 2016/6/23
 * Time: 14:16
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BizUserVoiceStatisticsTableOutput {
    /**
     * 分页信息
     */
    private Page pageinfo;
    private List<BizUserVocieStatisticsTableInfo> bizUserVocieStatisticsTableInfos ;

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }

    public List<BizUserVocieStatisticsTableInfo> getBizUserVocieStatisticsTableInfos() {
        return bizUserVocieStatisticsTableInfos;
    }

    public void setBizUserVocieStatisticsTableInfos(List<BizUserVocieStatisticsTableInfo> bizUserVocieStatisticsTableInfos) {
        this.bizUserVocieStatisticsTableInfos = bizUserVocieStatisticsTableInfos;
    }
}
