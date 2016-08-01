package com.ancun.boss.pojo.system;

import com.ancun.boss.pojo.BossPagePojo;

import java.util.List;

/**
 * 基础数据 请求封装类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/29
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BasicConfigInput extends BossPagePojo  {
    /**
     * 编号
     */
    private Integer basicno;
    /**
     * 模块code
     */
    private String moudlecode;
    
    private String name;

    /** 模块code列表 */
    private List<String> moudlecodes;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoudlecode() {
        return moudlecode;
    }

    public void setMoudlecode(String moudlecode) {
        this.moudlecode = moudlecode;
    }

    public List<String> getMoudlecodes() {
        return moudlecodes;
    }

    public void setMoudlecodes(List<String> moudlecodes) {
        this.moudlecodes = moudlecodes;
    }

    public Integer getBasicno() {
        return basicno;
    }

    public void setBasicno(Integer basicno) {
        this.basicno = basicno;
    }
}
