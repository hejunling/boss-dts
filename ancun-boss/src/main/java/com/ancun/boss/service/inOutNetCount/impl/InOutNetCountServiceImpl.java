package com.ancun.boss.service.inOutNetCount.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.persistence.biz.BizInOutNetDataMapper;
import com.ancun.boss.pojo.inOutNetCount.InOutNetCountOutput;
import com.ancun.boss.pojo.inOutNetCount.InOutNetQueryInput;
import com.ancun.boss.service.inOutNetCount.InOutNetCountService;
import com.ancun.utils.StringUtil;

/**
 * 在网离网统计实现类
 *
 * @Created on 2015年11月11日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class InOutNetCountServiceImpl implements InOutNetCountService{
	
	@Resource
	private BizInOutNetDataMapper mapper;
	
    /**
     * 在网离网统计数据查询
     *
     * @param input   查询条件
     * @return          统计数据
     */
	@Override
	public InOutNetCountOutput getInOutNetData(InOutNetQueryInput input) {
		InOutNetCountOutput output = new InOutNetCountOutput();
		
		if (!StringUtil.isEmpty(input.getEndDate())) {
			input.setEndDate(input.getEndDate() + "-01");
		}
		
		//在网数据取得
		output.setInNetList(mapper.queryInBarData(input));
		
		//离网数据取得
		output.setOutNetList(mapper.queryOutBarData(input));
		
		return output;
	}

}
