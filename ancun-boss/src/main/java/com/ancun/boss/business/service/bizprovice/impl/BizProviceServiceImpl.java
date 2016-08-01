package com.ancun.boss.business.service.bizprovice.impl;

import com.ancun.boss.business.persistence.mapper.BizProviceMapper;
import com.ancun.boss.business.persistence.module.BizProvice;
import com.ancun.boss.business.persistence.module.BizProviceExample;
import com.ancun.boss.business.pojo.bizprovice.SelectBizProviceInput;
import com.ancun.boss.business.pojo.bizprovice.SelectBizProviceOutput;
import com.ancun.boss.business.service.bizprovice.IBizProviceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BizProviceServiceImpl implements IBizProviceService {
	@Resource
	BizProviceMapper bizProviceMapper;
	
	private static final Logger log = LoggerFactory.getLogger(BizProviceServiceImpl.class);

	/**
	 * 通过条件取业务省份关系信息
	 * @param input
	 * @return
	 */
	public SelectBizProviceOutput selectBizProvice(SelectBizProviceInput input) {
		String bizNo = input.getBizNo();
		String rpcode = input.getPrcode();
		BizProviceExample example = new BizProviceExample();
		BizProviceExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(bizNo)){
			criteria.andBizNoEqualTo(bizNo);
		}
		if(StringUtils.isNotEmpty(rpcode)){
			criteria.andRpcodeEqualTo(rpcode);
		}
		List<BizProvice> bizProviceList = bizProviceMapper.selectByExample(example);
		SelectBizProviceOutput output  = new SelectBizProviceOutput();
		output.setBizProvicelist(bizProviceList);
		return output;
	}

}
