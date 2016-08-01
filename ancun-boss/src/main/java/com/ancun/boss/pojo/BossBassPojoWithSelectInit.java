package com.ancun.boss.pojo;

import java.util.List;

/**
 * 基础请求体(页面需要初始化下拉框)
 *
 * @Created on 2015年10月22日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BossBassPojoWithSelectInit extends BossBasePojo {

    /** 模块code列表 */
    private List<String> moudlecodes;

    /** 编辑类型 */
    private String action;

    public List<String> getMoudlecodes() {
        return moudlecodes;
    }

    public void setMoudlecodes(List<String> moudlecodes) {
        this.moudlecodes = moudlecodes;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
