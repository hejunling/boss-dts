package com.ancun.boss.pojo.workorder;

import com.ancun.boss.pojo.BossBasePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 工单转交input
 *
 * @Created on 2015年10月16日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class TransformWorkOrderInfoInput extends BossBasePojo {

    /** 工单详细ID */
    private Long id;

    /** 工单编号 */
    private String orderno;

    /** 工单下一个处理人用户编号 */
    private String handleUserno;

    /** 处理回复内容 */
    private String handleContent;

    /** 工单状态 */
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getHandleUserno() {
        return handleUserno;
    }

    public void setHandleUserno(String handleUserno) {
        this.handleUserno = handleUserno;
    }

    public String getHandleContent() {
        return handleContent;
    }

    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
