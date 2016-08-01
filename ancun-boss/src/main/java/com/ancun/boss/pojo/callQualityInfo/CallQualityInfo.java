package com.ancun.boss.pojo.callQualityInfo;

import com.ancun.boss.pojo.BossBasePojo;

import java.util.Date;

/**
 * 呼入质检查询输出消息
 */
public class CallQualityInfo extends BossBasePojo {

    //　主键
    private int id;

    //  质检员工号
    private String checkUserno;

    // 被质检人
    private String checkedUserno;

    // 质检员姓名
    private String checkUsername;

    // 被质检人姓名
    private String checkedUsername;

    // 质检时间
    private Date checkTime;

    // 基准点
    private String datumPoint;

    // 服务态度
    private String serviceAttitude;

    // 业务有效性
    private String businessEfficiency;

    // 规范操作
    private String standardOperation;

    // 服务技巧
    private String serviceSkills;

    // 总分
    private String totalScore;

    // 备注
    private String remark;

    // 咨询问题
    private String askQuestion;

    // 来电时间
    private Date telTime;

    // 来电号码
    private String telNo;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
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


    public String getAskQuestion() {
        return askQuestion;
    }

    public void setAskQuestion(String askQuestion) {
        this.askQuestion = askQuestion;
    }

    public String getCheckedUsername() {
        return checkedUsername;
    }

    public void setCheckedUsername(String checkedUsername) {
        this.checkedUsername = checkedUsername;
    }

    public String getCheckUsername() {
        return checkUsername;
    }

    public void setCheckUsername(String checkUsername) {
        this.checkUsername = checkUsername;
    }

    public Date getTelTime() {
        return telTime;
    }

    public void setTelTime(Date telTime) {
        this.telTime = telTime;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
}
