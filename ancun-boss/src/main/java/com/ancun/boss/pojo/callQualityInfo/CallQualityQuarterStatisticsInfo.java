package com.ancun.boss.pojo.callQualityInfo;

/**
 * 呼入质检质检季度个人平均列表统计输出对象
 *
 * @Created on 2015-12-14
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class CallQualityQuarterStatisticsInfo {
    // 被质检人工号
    private String checkedUserno;

    // 被质检人姓名
    private String checkedUsername;

    // 质检得分
    private String checkScore;

    // 一季度平均分
    private String oneQuarter;

    // 二季度平均分
    private String twoQuarter;

    // 三季度平均分
    private String threeQuarter;

    // 四季度平均分
    private String fourQuarter;

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

    public String getOneQuarter() {
        return oneQuarter;
    }

    public void setOneQuarter(String oneQuarter) {
        this.oneQuarter = oneQuarter;
    }

    public String getTwoQuarter() {
        return twoQuarter;
    }

    public void setTwoQuarter(String twoQuarter) {
        this.twoQuarter = twoQuarter;
    }

    public String getThreeQuarter() {
        return threeQuarter;
    }

    public void setThreeQuarter(String threeQuarter) {
        this.threeQuarter = threeQuarter;
    }

    public String getFourQuarter() {
        return fourQuarter;
    }

    public void setFourQuarter(String fourQuarter) {
        this.fourQuarter = fourQuarter;
    }
}
