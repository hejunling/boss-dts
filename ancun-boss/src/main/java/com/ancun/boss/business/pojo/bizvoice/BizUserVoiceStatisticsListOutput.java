package com.ancun.boss.business.pojo.bizvoice;

import java.util.List;

/**
 * 用户录音列表统计输出列表对象（月，半年，年，季度数量统计)
 *
 * @Created on 2016-06-23
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

public class BizUserVoiceStatisticsListOutput<T> {
    private List<T> exportList;

    public List<T> getExportList() {
        return exportList;
    }

    public void setExportList(List<T> exportList) {
        this.exportList = exportList;
    }
}
