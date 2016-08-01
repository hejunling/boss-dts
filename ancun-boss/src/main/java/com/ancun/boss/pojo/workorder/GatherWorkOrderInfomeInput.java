package com.ancun.boss.pojo.workorder;

import com.ancun.boss.pojo.BossBasePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 工单服务-新增/修改input
 *
 * @Created on 2015年10月15日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class GatherWorkOrderInfomeInput extends BossBasePojo {

    /** 工单编号 */
    private String orderNo;

    /** 用户名称 */
    private String userName;

    /** 来电号码 */
    private String callPhone;

    /** 设备号码 */
    private String equipPhone;

    /** 工单类型（具体参考字典表） */
    private String orderType;

    /** 工单来源（EMAIL,QQ等数据字典） */
    private String orderSource;

    /** 投诉来源(如10000号，10086等) */
    private String complaintSource;

    /** 投诉类型 */
    private String complaintType;

    /** 投诉属性（一次投诉,二次投诉等数据字典） */
    private String complaintProperty;

    /** 要求完成时间 */
    private String rcTime;

    /** 服务请求ID */
    private String requestId;

    /** 工单来源人 */
    private String orderFrom;

    /** 工单内容 */
    private String content;

    /** 业务名称 */
    private String business;

    /** 变更类型 */
    private String action;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCallPhone() {
        return callPhone;
    }

    public void setCallPhone(String callPhone) {
        this.callPhone = callPhone;
    }

    public String getEquipPhone() {
        return equipPhone;
    }

    public void setEquipPhone(String equipPhone) {
        this.equipPhone = equipPhone;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getComplaintSource() {
        return complaintSource;
    }

    public void setComplaintSource(String complaintSource) {
        this.complaintSource = complaintSource;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintProperty() {
        return complaintProperty;
    }

    public void setComplaintProperty(String complaintProperty) {
        this.complaintProperty = complaintProperty;
    }

    public String getRcTime() {
        return rcTime;
    }

    public void setRcTime(String rcTime) {
        this.rcTime = rcTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
