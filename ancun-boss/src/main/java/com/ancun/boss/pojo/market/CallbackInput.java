package com.ancun.boss.pojo.market;

import com.ancun.boss.pojo.BossBasePojo;

/**
 * 用户录音上传回调接口input
 *
 * @Created on 2015年9月28日
 * @author xieyushi
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CallbackInput extends BossBasePojo {

    /** 外呼团队 */
    private String calledTeam;

    /** 业务名称 */
    private String business;

    /** 营销时间 */
    private String marketTime;

    /** 被营销电话 */
    private String marketPhone;

    /** 回调结果编码 */
    private int callbackCode;

    /** 回调结果文本信息 */
    private String callbackResult;

    /** 文件名 */
    private String fileName;

    public String getCalledTeam() {
        return calledTeam;
    }

    public void setCalledTeam(String calledTeam) {
        this.calledTeam = calledTeam;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getMarketTime() {
        return marketTime;
    }

    public void setMarketTime(String marketTime) {
        this.marketTime = marketTime;
    }

    public String getMarketPhone() {
        return marketPhone;
    }

    public void setMarketPhone(String marketPhone) {
        this.marketPhone = marketPhone;
    }

    public int getCallbackCode() {
        return callbackCode;
    }

    public void setCallbackCode(int callbackCode) {
        this.callbackCode = callbackCode;
    }

    public String getCallbackResult() {
        return callbackResult;
    }

    public void setCallbackResult(String callbackResult) {
        this.callbackResult = callbackResult;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
