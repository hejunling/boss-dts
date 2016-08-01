package com.ancun.boss.pojo.callQualityInfo;

import com.ancun.boss.pojo.BossBasePojo;

import java.util.List;

/**
 * 呼入质检质检列表统计输出列表对象
 *
 * @Created on 2015-12-04
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

public class CallQualityStatisticsListOutput<T> extends BossBasePojo {
    private List<T> exportList;

    public List<T> getExportList() {
        return exportList;
    }

    public void setExportList(List<T> exportList) {
        this.exportList = exportList;
    }
}
