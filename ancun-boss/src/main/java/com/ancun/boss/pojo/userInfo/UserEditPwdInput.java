package com.ancun.boss.pojo.userInfo;

import com.ancun.boss.pojo.BossPagePojo;
import com.ancun.core.domain.request.BasePojo;
import com.ancun.core.domain.request.PageModel;
import com.ancun.core.page.Page;
import com.ancun.utils.StringUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 修改密码请求封装POJO类
 *
 * @author yck
 * @version 1.0
 * @Created on 2015/9/18
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class UserEditPwdInput extends BossPagePojo {

	private String oldPwd;
	
	private String newPwd;
	
	private String newPwd2;

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getNewPwd2() {
		return newPwd2;
	}

	public void setNewPwd2(String newPwd2) {
		this.newPwd2 = newPwd2;
	}
	
}
