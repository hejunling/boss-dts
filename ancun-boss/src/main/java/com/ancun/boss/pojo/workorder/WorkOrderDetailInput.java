package com.ancun.boss.pojo.workorder;

import com.ancun.boss.pojo.BossBassPojoWithSelectInit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用户工单详细请求体
 *
 * @Created on 2015年10月22日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class WorkOrderDetailInput extends BossBassPojoWithSelectInit {

    /** 工单编号 */
    private String orderno;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}
