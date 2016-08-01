package com.ancun.boss.pojo.callQualityInfo;

/**
 * 呼入质检质检年个人平均列表统计输出对象
 *
 * @Created on 2015-12-15
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CallQualityYearStatisticsInfo {

    // 被质检人工号
    private String checkedUserno;

    // 被质检人姓名
    private String checkedUsername;

    // 质检得分
    private String checkScore;

    // 年平均分
    private String yearAverage;

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

    public String getYearAverage() {
        return yearAverage;
    }

    public void setYearAverage(String yearAverage) {
        this.yearAverage = yearAverage;
    }
}
