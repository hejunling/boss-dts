package com.ancun.boss.pojo.workorder;

import com.ancun.boss.persistence.model.SystemBasicConfig;

import java.util.List;
import java.util.Map;

/**
 * 用户工单详细返回体
 *
 * @Created on 2015年10月22日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class WorkOrderDetailOutput {

    /** 用户工单详细 */
    private WorkOrderDetail info;

    /** 下拉框列表 */
    private Map<String, List<SystemBasicConfig>> selects;

    public WorkOrderDetail getInfo() {
        return info;
    }

    public void setInfo(WorkOrderDetail info) {
        this.info = info;
    }

    public Map<String, List<SystemBasicConfig>> getSelects() {
        return selects;
    }

    public void setSelects(Map<String, List<SystemBasicConfig>> selects) {
        this.selects = selects;
    }
}
