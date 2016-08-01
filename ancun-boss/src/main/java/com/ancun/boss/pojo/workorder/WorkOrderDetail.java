package com.ancun.boss.pojo.workorder;

/**
 * 用户工单详细
 *
 * @Created on 2015年10月22日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class WorkOrderDetail {

    /** 记录Id */
    private Long id;

    /** 工单编号 */
    private String orderno;

    /** 用户名称 */
    private String username;

    /** 来电号码 */
    private String callPhone;

    /** 设备号 */
    private String equipPhone;

    /** 业务类型 */
    private String business;

    /** 工单类型 */
    private String orderType;

    /** 工单来源 */
    private String orderSource;

    /** 投诉来源 */
    private String complaintSource;

    /** 投诉类型 */
    private String complaintType;

    /** 投诉属性 */
    private String complaintProperty;

    /** 要求完成时间 */
    private String rctime;

    /** 服务请求Id */
    private String requestid;

    /** 工单来源人 */
    private String orderFrom;

    /** 工单内容 */
    private String content;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
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

    public String getRctime() {
        return rctime;
    }

    public void setRctime(String rctime) {
        this.rctime = rctime;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
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
}
