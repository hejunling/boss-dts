package com.ancun.boss.persistence.biz;

import java.util.List;

import com.ancun.boss.pojo.marketInfo.MarketCheckInfoPojo;
import com.ancun.boss.pojo.marketInfo.MarketCheckQueryInput;

/**
 * 查询营销质检
 *
 * @author chenb
 * @version 1.0
 * @Created on 2015/10/13
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizMarketCheckMapper {
	
    /**
     * 营销质检List查询
     *
     * @param input
     * @return
     */
	List<MarketCheckInfoPojo> selectMakretCheckList(MarketCheckQueryInput input);
	
    /**
     * 营销质检详细查询
     *
     * @param input
     * @return
     */
	MarketCheckInfoPojo selectMarketCheck(MarketCheckQueryInput input);

}
