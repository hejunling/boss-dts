package com.ancun.boss.pojo;

import com.ancun.core.domain.request.PageModel;
import com.ancun.core.page.Page;
import com.ancun.utils.StringUtil;

/**
 * 分页基础POJO
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/22
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BossPagePojo extends PageModel {

    /**
     * 权限KEY
     */
    private String accesskey;

    /**
     * 用户编号
     */
    private String userno;
    /**
     * 排序方式
     */
    private String ordersort;
    /**
     * 每页记录数
     */
    private String pagesize;

    /**
     * 页码
     */
    private String currentpage;

    public String getOrdersort() {
        return ordersort;
    }

    public void setOrdersort(String ordersort) {
        //默认为倒序
        if (ordersort == null || ordersort.length() == 0) {
            this.ordersort = "desc";
        }
        this.ordersort = ordersort;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        if (!StringUtil.isEmpty(pagesize)) {
            this.pagesize = pagesize;
        } else {
            this.pagesize = "10";//默认设置页面大小为10行
        }
        if (getPage() == null) {
            setPage(new Page());
        }
        getPage().setPagesize(Integer.valueOf(this.pagesize));
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        if (!StringUtil.isEmpty(currentpage)) {
            this.currentpage = currentpage;
        } else {
            this.currentpage = "1";//默认设置当前为第一页
        }
        if (getPage() == null) {
            setPage(new Page());
        }
        getPage().setCurrentpage(Integer.valueOf(this.currentpage));
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }
}
