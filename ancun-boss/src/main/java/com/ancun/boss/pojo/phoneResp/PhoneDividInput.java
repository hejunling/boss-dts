package com.ancun.boss.pojo.phoneResp;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.google.common.base.Joiner;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 号码分配input参数
 *
 * @Created on 2015年12月18日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class PhoneDividInput extends BossBasePojo {

    /** 批次 */
    @NotBlank(message=""+ MessageConstant.MARKETCHECK_BUSINESS_MUST_BE_NOT_EMPTY)
    private String business;

    /** 获取时间 */
    @NotBlank(message=""+ MessageConstant.GET_TIME_MUST_BE_NOT_EMPTY)
    private String getTime;

    /** 分配营销团队信息 */
    private List<Team> teams;

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public static class Team {
        String dividtono;
        String dividtoname;
        Date dividtime = new Date();
        long dividnumber;

        public String getDividtono() {
            return dividtono;
        }

        public void setDividtono(String dividtono) {
            this.dividtono = dividtono;
        }

        public String getDividtoname() {
            return dividtoname;
        }

        public void setDividtoname(String dividtoname) {
            this.dividtoname = dividtoname;
        }

        public Date getDividtime() {
            return dividtime;
        }

        public void setDividtime(Date dividtime) {
            this.dividtime = dividtime;
        }

        public long getDividnumber() {
            return dividnumber;
        }

        public void setDividnumber(long dividnumber) {
            this.dividnumber = dividnumber;
        }

        @Override
        public String toString() {
            return Joiner.on("").join(
                    "{团队编号: ",
                    dividtono,
                    ", 团队名称: ",
                    dividtoname,
                    ", 分配时间: ",
                    dividtime,
                    "}"
                    ).toString();
        }
    }
}
