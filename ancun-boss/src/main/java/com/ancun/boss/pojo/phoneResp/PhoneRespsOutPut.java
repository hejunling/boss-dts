package com.ancun.boss.pojo.phoneResp;

import com.ancun.core.page.Page;

import java.util.List;

/**
 * 号码库列表outPut参数
 *
 * @Created on 2015年11月13日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class PhoneRespsOutPut {

    /** 号码库列表 */
    private List<PhoneRepInfo> phoneRepInfos;

    /** 分页信息 */
    private Page pageinfo;

    public List<PhoneRepInfo> getPhoneRepInfos() {
        return phoneRepInfos;
    }

    public void setPhoneRepInfos(List<PhoneRepInfo> phoneRepInfos) {
        this.phoneRepInfos = phoneRepInfos;
    }

    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }
}
