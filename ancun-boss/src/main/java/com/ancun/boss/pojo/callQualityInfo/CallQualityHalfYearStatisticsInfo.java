package com.ancun.boss.pojo.callQualityInfo;

/**
 * 呼入质检质检半年个人平均列表统计输出对象
 *
 * @Created on 2015-12-14
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CallQualityHalfYearStatisticsInfo {
    // 被质检人工号
    private String checkedUserno;

    // 被质检人姓名
    private String checkedUsername;

    // 质检得分
    private String checkScore;

    // 上半年平均分
    private String firstHalfYear;

    // 下半年平均分
    private String secondHalfYear;

    public String getCheckedUserno() {
        return checkedUserno;
    }

    public void setCheckedUserno(String checkedUserno) {
        this.checkedUserno = checkedUserno;
    }

    public String getCheckedUsername() {
        return checkedUsername;
    }

    public void setCheckedUsername(String checkedUsername) {
        this.checkedUsername = checkedUsername;
    }

    public String getCheckScore() {
        return checkScore;
    }

    public void setCheckScore(String checkScore) {
        this.checkScore = checkScore;
    }

    public String getFirstHalfYear() {
        return firstHalfYear;
    }

    public void setFirstHalfYear(String firstHalfYear) {
        this.firstHalfYear = firstHalfYear;
    }

    public String getSecondHalfYear() {
        return secondHalfYear;
    }

    public void setSecondHalfYear(String secondHalfYear) {
        this.secondHalfYear = secondHalfYear;
    }
}
