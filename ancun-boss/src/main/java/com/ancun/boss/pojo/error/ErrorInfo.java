package com.ancun.boss.pojo.error;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 导入 错误信息封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XStreamAlias("errorinfo")
public class ErrorInfo {
    /**
     * 错误内容
     */
    private String message;
    /**
     * 错误code
     */
    private int errcode;

    /**
     * 失败原因
     */
    private String failreason;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getFailreason() {
        return failreason;
    }

    public void setFailreason(String failreason) {
        this.failreason = failreason;
    }
}
