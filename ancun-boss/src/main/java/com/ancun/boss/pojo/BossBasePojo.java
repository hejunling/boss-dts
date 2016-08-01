package com.ancun.boss.pojo;

/**
 * 基础POJO
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/22
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BossBasePojo {
    // 权限ID
    private String accessid;

    //权限KEY
    private String accesskey;
    
    // 用户编号
    private String userno;

    public String getAccessid() {
        return accessid;
    }

    public void setAccessid(String accessid) {
        this.accessid = accessid;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}
    
}
