package com.ancun.boss.business.service.allprovince.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.service.allprovince.IProvinceService;
import com.ancun.boss.business.service.allprovince.IPublicProvinceService;
import com.ancun.boss.constant.BizRequestConstant;
import com.google.common.collect.HashBasedTable;

/**
 * 
 *
 * @Created on 2016年3月15日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class PublicProvinceServiceImpl implements IPublicProvinceService {
	
	@Resource(name="SHService")
	private IProvinceService SHService;
	
	@Resource(name="BJService")
	private IProvinceService BJService;
	
	@Resource(name="UniService")
	private IProvinceService UniService;
	

	@Override
    public HashBasedTable<String, String, IProvinceService> getProvinceService() {
		HashBasedTable<String, String, IProvinceService> table = HashBasedTable.create();
		
		table.put(BizRequestConstant.PROVINCES_SHANGHAI, Constant.PROVENCE_TYPE_DX, SHService);
		table.put(BizRequestConstant.PROVINCES_BEIJING, Constant.PROVENCE_TYPE_LT, BJService);
		table.put(BizRequestConstant.PROVINCES_CP_UNICOM, Constant.PROVENCE_TYPE_LT, UniService);
		
	    return table;
    }

}
