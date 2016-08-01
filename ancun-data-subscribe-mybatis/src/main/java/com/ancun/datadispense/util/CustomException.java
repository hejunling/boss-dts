package com.ancun.datadispense.util;

/**
 * 自定义异常类
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/4/13
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;
    private int number;

    final static String DEFAULT_MSG = "系统异常";

    public CustomException() {
        super(DEFAULT_MSG);
    }

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message,int number) {
        this.message = message;
        this.number = number;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
