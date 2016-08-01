package com.ancun.boss.business.pojo.dataDicInfo;

import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 数据字典信息请求实体pojo
 *
 * @Created on 2016-4-1
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class GetDictionaryInput extends BossPagePojo {

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

	private String module;//模块名称
	
	private String value;//值
	
	private String name;//名

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    public String getName() {
	    return name;
    }

    public void setName(String name) {
	    this.name = name;
    }
	
	
}
