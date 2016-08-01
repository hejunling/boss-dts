package com.ancun.boss.pojo.workorder;

import com.ancun.core.constant.ResponseConst;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * 工单处理output
 *
 * @Created on 2015年10月27日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class WorkOrderDealOutput {

    /** 详细表信息ID */
    private String id;

    /** 部门名称 */
    private String orgName;

    /** 用户名称 */
    private String userName;

    /** 处理内容 */
    private String handleContent;

    /** 处理时间 */
    private Date handleTime;

    /** 工单状态 */
    private String status;

    /** 处理结果 */
    private int dealResult = ResponseConst.SUCCESS;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHandleContent() {
        return handleContent;
    }

    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDealResult() {
        return dealResult;
    }

    public void setDealResult(int dealResult) {
        this.dealResult = dealResult;
    }
}
