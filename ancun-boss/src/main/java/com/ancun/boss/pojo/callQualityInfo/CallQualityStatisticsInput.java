package com.ancun.boss.pojo.callQualityInfo;

import com.ancun.boss.pojo.BossBasePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 呼入质检质检列表统计输入对象
 *
 * @Created on 2015-12-04
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class CallQualityStatisticsInput extends BossBasePojo {

    // 被质检人工号
    private String checkedUserno;

    // 年份
    private String year;

    // 统计类型(1:月平均分, 2:季度平均分，3:半年平均分，4：年平均分)
    private String exportType;

    // 部门
    private String department;

    public String getCheckedUserno() {
        return checkedUserno;
    }

    public void setCheckedUserno(String checkedUserno) {
        this.checkedUserno = checkedUserno;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
