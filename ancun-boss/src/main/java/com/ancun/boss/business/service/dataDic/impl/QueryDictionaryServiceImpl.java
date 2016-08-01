package com.ancun.boss.business.service.dataDic.impl;


import com.ancun.boss.business.pojo.dataDicInfo.GetDictionaryInput;
import com.ancun.boss.business.pojo.dataDicInfo.GetDictionaryOutput;
import com.ancun.boss.business.service.dataDic.IQueryDictionaryService;
import com.ancun.boss.persistence.mapper.DataDicMapper;
import com.ancun.boss.persistence.model.DataDic;
import com.ancun.boss.persistence.model.DataDicExample;
import com.ancun.boss.service.system.ICacheConfigService;
import com.ancun.core.page.Page;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 数据字典数据相关service实现类
 *
 * @Created on 2016-4-6
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class QueryDictionaryServiceImpl implements IQueryDictionaryService {
	private static final Logger logger = LoggerFactory.getLogger(QueryDictionaryServiceImpl.class);
	
	@Resource
	private DataDicMapper dataDicMapper;

	@Resource
	private ICacheConfigService iCacheConfigService;
	
	/**
	 * 组装数据字典信息
	 * @param input
	 * @return
	 */
	@Override
	public GetDictionaryOutput getDic(GetDictionaryInput input) {
		return  getDicList(input);
	}


	/**
	 * 根据数据字典值获取bean 有分页信息
	 * @param input
	 * @return
	 */
	private  GetDictionaryOutput getDicList(GetDictionaryInput  input) {
		DataDicExample example = new DataDicExample();

		// 设置分页
		Page pageinfo = new Page();
		if((input.getCurrentpage() !=null) && (input.getCurrentpage() != "")){

			pageinfo.setCurrentpage(Integer.valueOf(input.getCurrentpage()));
			pageinfo.setPagesize(Integer.valueOf(input.getPagesize()));
			example.setPage(pageinfo);
		}


		DataDicExample.Criteria c = example.createCriteria();
		//模块
		if(!StringUtil.isBlank(input.getModule())){
			c.andModuleEqualTo(input.getModule());
		}
		//属性名
		if (!StringUtil.isBlank(input.getName())) {
			c.andConfNameLike("%"+input.getName()+"%");
		}
		//属性值
		if(!StringUtil.isBlank(input.getValue())){
			c.andConfValueEqualTo(input.getValue());
		}

		List<DataDic> datalist = dataDicMapper.selectByExample(example);
//		if (null == datalist || datalist.size() == 0) {
//			throw new EduException(ResponseConst.RECORD_NOT_EXISTED);
//		}

		GetDictionaryOutput output = new GetDictionaryOutput();
		output.setDatalist(datalist);
		output.setPageinfo(pageinfo);
		return output;
	}
	
	/**
	 * 获取数据字典map(key：module_value;value:name)
	 * @return
	 */
	@Override
    public HashMap<String,String> getDicAsmap() {
		
		HashMap<String,String> map = new HashMap<String,String>();

		//从缓存中获取数据
		List<DataDic> list = iCacheConfigService.queryAllDateDic();
		//将list中的数据转存到map里
		for(DataDic dic:list){
			StringBuffer sb = new StringBuffer();
			sb.append(dic.getModule()).append("_").append(dic.getConfValue());
			map.put(sb.toString(), dic.getConfName());
		}
		
	    return map;
    }



}
