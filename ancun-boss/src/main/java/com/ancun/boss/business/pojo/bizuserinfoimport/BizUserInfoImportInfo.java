package com.ancun.boss.business.pojo.bizuserinfoimport;

import com.ancun.boss.business.persistence.module.BizUserInfoImportFailed;

/**
 * 业务用户导入失败信息表.
 * User: zkai
 * Date: 2016/4/19
 * Time: 14:09
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

public class BizUserInfoImportInfo  extends BizUserInfoImportFailed {

    private String insourceName;

    private String taocanName;

    public String getTaocanName() {
        return taocanName;
    }

    public void setTaocanName(String taocanName) {
        this.taocanName = taocanName;
    }

    public String getInsourceName() {
        return insourceName;
    }

    public void setInsourceName(String insourceName) {
        this.insourceName = insourceName;
    }
}
