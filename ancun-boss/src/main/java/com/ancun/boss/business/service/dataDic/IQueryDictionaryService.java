package com.ancun.boss.business.service.dataDic;


import com.ancun.boss.business.pojo.dataDicInfo.GetDictionaryInput;
import com.ancun.boss.business.pojo.dataDicInfo.GetDictionaryOutput;
import com.ancun.boss.persistence.model.DataDic;
import com.ancun.core.exception.EduException;

import java.util.HashMap;
import java.util.List;

/**
 * 数据字典数据相关service
 *
 * @Created on 2016-4-01
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IQueryDictionaryService {
	/**
	 * 组装数据字典信息
	 * @param input
	 * @return
	 */
	public GetDictionaryOutput getDic(GetDictionaryInput input);


	/**
	 * 获取数据字典map(key：module_value;value:name)
	 * @return
	 */
	public HashMap<String,String> getDicAsmap();

}
