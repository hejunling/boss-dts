package com.ancun.boss.pojo.callQualityInfo;


import com.ancun.boss.pojo.BossBasePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 呼入质检质检列表导入输入对象
 *
 * @Created on 2015-10-19
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class CallQualityImportInput extends BossBasePojo {
    private List<CallQualityInput> callQualityInfoList;

    public List<CallQualityInput> getCallQualityInfoList() {
        return callQualityInfoList;
    }

    public void setCallQualityInfoList(List<CallQualityInput> callQualityInfoList) {
        this.callQualityInfoList = callQualityInfoList;
    }
}
