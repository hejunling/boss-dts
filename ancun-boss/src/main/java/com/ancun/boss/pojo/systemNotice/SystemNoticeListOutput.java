package com.ancun.boss.pojo.systemNotice;

import com.ancun.boss.persistence.model.SystemNotice;
import com.ancun.core.page.Page;

import java.util.List;

/**
 * 消息提醒消列表输出类
 *
 * @Created on 2015年10月26日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class SystemNoticeListOutput {
    /**
     * 分页信息
     */
    private Page pageinfo;
    private List<SystemNotice> systemNoticeInfoList;

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }

    public List<SystemNotice> getSystemNoticeInfoList() {
        return systemNoticeInfoList;
    }

    public void setSystemNoticeInfoList(List<SystemNotice> systemNoticeInfoList) {
        this.systemNoticeInfoList = systemNoticeInfoList;
    }
}
