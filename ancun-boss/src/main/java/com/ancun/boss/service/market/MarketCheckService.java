package com.ancun.boss.service.market;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ancun.boss.pojo.marketInfo.MarketCheckInput;
import com.ancun.boss.pojo.marketInfo.MarketCheckListInput;
import com.ancun.boss.pojo.marketInfo.MarketCheckOutput;
import com.ancun.boss.pojo.marketInfo.MarketCheckQueryInput;

/**
 * 营销质检业务逻辑接口
 *
 * @Created on 2015年9月21日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface MarketCheckService {
	
	/**
	 * 营销质检查询
	 */
	MarketCheckOutput getMarketCheckListinfo(MarketCheckQueryInput input);
	
	/**
	 * 营销质检新建/更新
	 */
	void addOrUpdateMarketCheckInfo(MarketCheckInput input);
	
	/**
	 * 营销质检详细
	 */
	MarketCheckOutput getMarketCheckInfo(MarketCheckQueryInput input);
	
	/**
	 * 营销质检数据导入
	 */
	String importMarketCheckInfo(MarketCheckListInput input);
	
	/**
	 * 营销质检录音试听
	 */
	void downloadVoice(HttpServletResponse response, MarketCheckQueryInput input);
	
}
