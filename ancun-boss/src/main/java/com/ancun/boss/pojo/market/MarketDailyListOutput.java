package com.ancun.boss.pojo.market;

import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 营销日报 请求响应封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class MarketDailyListOutput {

    /**
     * 营销日报列表
     */
    private List<MarketDailyOutPut> marketdailylist;

    /**
     * 分页信息
     */
    private Page pageinfo;


    public List<MarketDailyOutPut> getMarketdailylist() {
        return marketdailylist;
    }

    public void setMarketdailylist(List<MarketDailyOutPut> marketdailylist) {
        this.marketdailylist = marketdailylist;
    }

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }
}
