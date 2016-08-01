package com.ancun.boss.business.service.allprovince;

import org.springframework.cache.annotation.Cacheable;

import com.google.common.collect.HashBasedTable;

/**
 * 共通省份
 *
 * @Created on 2016年3月15日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IPublicProvinceService {

	/**
	 * 分省服务缓存
	 * @param rpCode
	 * @param type
	 * @return
	 */
	@Cacheable(value = { "provinceServiceCache" })
	HashBasedTable<String, String, IProvinceService> getProvinceService();
}
