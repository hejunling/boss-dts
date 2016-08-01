package com.ancun.boss.pojo.phoneResp;

import com.ancun.boss.persistence.model.SystemBasicConfig;

import java.util.List;

/**
 * 号码分配outnput参数
 *
 * @Created on 2015年12月21日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class PhoneDividPageOutput {

    /** 外呼团队 */
    private List<SystemBasicConfig> outCallTeams;

    /** 批次 */
    private List<String> phoneSources;

    public List<SystemBasicConfig> getOutCallTeams() {
        return outCallTeams;
    }

    public void setOutCallTeams(List<SystemBasicConfig> outCallTeams) {
        this.outCallTeams = outCallTeams;
    }

    public List<String> getPhoneSources() {
        return phoneSources;
    }

    public void setPhoneSources(List<String> phoneSources) {
        this.phoneSources = phoneSources;
    }
}
