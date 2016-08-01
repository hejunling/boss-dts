package com.ancun.boss.pojo.login;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 登陆信息请求输入封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/22
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class LoginInput {

    //用户名
    @NotNull(message=""+ MessageConstant.USER_NAME_MUST_BE_NOT_EMPTY)
    private String userName;

    //密码
    @NotNull(message=""+ MessageConstant.PASS_WORD_MUST_BE_NOT_EMPTY)
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
