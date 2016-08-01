package com.ancun.boss.pojo.phoneResp;

import com.ancun.boss.pojo.Option;

import java.util.List;

/**
 * 获取时间选择项outPut参数
 *
 * @Created on 2015年12月24日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class GetTimeOptionsOutput {

    /** 选择项列表 */
    private List<Option> options;

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
