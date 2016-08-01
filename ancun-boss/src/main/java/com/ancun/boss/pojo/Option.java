package com.ancun.boss.pojo;

/**
 * 下拉框选择项
 *
 * @Created on 2015年12月24日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class Option {

    /** 选择项的值 */
    private String id;

    /** 选择项的label */
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
