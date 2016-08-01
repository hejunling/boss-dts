package com.ancun.boss.pojo.userInfo;

import com.ancun.boss.pojo.BossBasePojo;

/**
 * 用户模糊搜索下拉框接口输入参数
 *
 * @Created on 2015年10月19日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class UserSelectInput  extends BossBasePojo{

    /** 用户名称中的一个关键字 */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
