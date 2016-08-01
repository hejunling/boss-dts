package com.ancun.boss.pojo.userInfo;

/**
 * 用户模糊搜索下拉框接口输出
 *
 * @Created on 2015年10月19日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class UserSelectOutput {

    /** 工号 */
    private String code;

    /** 显示名称 用户名称(用户部门) */
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
