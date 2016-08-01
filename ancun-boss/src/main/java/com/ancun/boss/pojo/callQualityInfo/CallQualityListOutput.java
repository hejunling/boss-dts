package com.ancun.boss.pojo.callQualityInfo;

import com.ancun.core.page.Page;

import java.util.List;

/**
 * 呼入质检输出请求封装类
 *
 * @author zkai
 * @version 1.0
 * @Created on 2015/10/15
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CallQualityListOutput {
    /**
     * 营销质检列表
     */
    private List<CallQualityInfo> callQualityInfoList;



    /**
     * 分页信息
     */
    private Page pageinfo;

    public List<CallQualityInfo> getCallQualityInfoList() {
        return callQualityInfoList;
    }

    public void setCallQualityInfoList(List<CallQualityInfo> callQualityInfoList) {
        this.callQualityInfoList = callQualityInfoList;
    }

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }
}
