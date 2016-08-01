package com.ancun.boss.pojo.callQualityInfo;


import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import org.hibernate.validator.constraints.NotBlank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * 呼入质检请求封装类
 *
 * @author zkai
 * @version 1.0
 * @Created on 2015/10/15
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class CallQualityInput extends BossBasePojo {

    // 唯一键
    private String id;

    // 质检员工号
    @NotBlank(message=MessageConstant.CHECK_NO_MUST_BE_NOT_EMPTY+"")
    private String checkUserno;


    // 被质检人工号
    @NotBlank(message=MessageConstant.CHECKED_NO_MUST_BE_NOT_EMPTY+"")
    private String checkedUserno;

    /**
     * （格式为yyyy-MM-dd）
     */
    // 质检时间
    @NotBlank(message= MessageConstant.CALL_QUALITY_TIME_MUST_BE_NOT_EMPTY+"")
    private String checkTime;

    // 基准点
    @NotBlank(message= MessageConstant.DATUM_POINT_NO_MUST_BE_NOT_EMPTY+"")
    private String datumPoint;

    // 服务态度
    @NotBlank(message= MessageConstant.SERVICE_ATTITUDE_NO_MUST_BE_NOT_EMPTY+"")
    private String serviceAttitude;

    // 业务有效性
    @NotBlank(message= MessageConstant.BUSSINESS_EFFICIENCY_NO_MUST_BE_NOT_EMPTY+"")
    private String businessEfficiency;

    // 规范操作
    @NotBlank(message= MessageConstant.STANDARD_OPERATION_NO_MUST_BE_NOT_EMPTY+"")
    private String standardOperation;

    // 服务技巧
    @NotBlank(message= MessageConstant.SERVICE_SKILL_NO_MUST_BE_NOT_EMPTY+"")
    private String serviceSkills;

    // 总分
    @NotBlank(message= MessageConstant.TOTALSCORE_NO_MUST_BE_NOT_EMPTY+"")
    private String totalScore;

    // 备注
    private String remark;

    // 呼入登记ID
    private String callRecordNo;

    // 咨询问题
    @NotBlank(message="咨询问题为空")
    private String askQuestion;

    /**
     * add by zkai on 2016/04/08
     */
    //---------------------------
    // 来电时间
    private Date telTime;
    // 来电号码
    private String telNo;
    // -------------------------

    public String getId() {
        return id;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public Date getTelTime() {

        return telTime;
    }

    public void setTelTime(Date telTime) {
        this.telTime = telTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckUserno() {
        return checkUserno;
    }

    public void setCheckUserno(String checkUserno) {
        this.checkUserno = checkUserno;
    }

    public String getCheckedUserno() {
        return checkedUserno;
    }

    public void setCheckedUserno(String checkedUserno) {
        this.checkedUserno = checkedUserno;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getDatumPoint() {
        return datumPoint;
    }

    public void setDatumPoint(String datumPoint) {
        this.datumPoint = datumPoint;
    }

    public String getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(String serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public String getBusinessEfficiency() {
        return businessEfficiency;
    }

    public void setBusinessEfficiency(String businessEfficiency) {
        this.businessEfficiency = businessEfficiency;
    }

    public String getStandardOperation() {
        return standardOperation;
    }

    public void setStandardOperation(String standardOperation) {
        this.standardOperation = standardOperation;
    }

    public String getServiceSkills() {
        return serviceSkills;
    }

    public void setServiceSkills(String serviceSkills) {
        this.serviceSkills = serviceSkills;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCallRecordNo() {
        return callRecordNo;
    }

    public void setCallRecordNo(String callRecordNo) {
        this.callRecordNo = callRecordNo;
    }

    public String getAskQuestion() {
        return askQuestion;
    }

    public void setAskQuestion(String askQuestion) {
        this.askQuestion = askQuestion;
    }
}
