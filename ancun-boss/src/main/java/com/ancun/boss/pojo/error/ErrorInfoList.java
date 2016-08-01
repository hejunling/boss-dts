package com.ancun.boss.pojo.error;

import java.util.List;

/**
 * 导入 错误信息响应封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class ErrorInfoList {
    private List<ErrorInfo> errorinfos;

    public List<ErrorInfo> getErrorinfos() {
        return errorinfos;
    }

    public void setErrorinfos(List<ErrorInfo> errorinfos) {
        this.errorinfos = errorinfos;
    }

}
