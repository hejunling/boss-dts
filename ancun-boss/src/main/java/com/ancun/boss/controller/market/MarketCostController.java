package com.ancun.boss.controller.market;

import com.ancun.boss.pojo.marketInfo.*;
import com.ancun.boss.service.market.IMarketCostService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.constant.ResponseConst;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 *
 * @Created on 2015年10月12日
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class MarketCostController {
	
	@Resource
    private IMarketCostService marketCostservice;
	
	
	/**
	 * 营销成本-查询
	 * 
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/queryCostCalcList", method = RequestMethod.POST)
	public RespBody<QueryCostCalcListOutput> queryCostCalcList(ReqBody<QueryCostCalcListInput> input) {
		
		List<CostCalcInfo> list= marketCostservice.queryCostCalcList(input.getContent());
		
		//返回对象组装
		QueryCostCalcListOutput outputMode = new QueryCostCalcListOutput();
		outputMode.setCostcalclist(list);
		
		
		// 设置分页信息
		if(input.getContent().getPage() != null){
			outputMode.setPageinfo(input.getContent().getPage());
		}
		
		RespBody<QueryCostCalcListOutput> output = new RespBody<QueryCostCalcListOutput>(outputMode);
		
		//记录不存在
		if(list == null || list.size() == 0){
			output.getInfo().setCode(ResponseConst.RECORD_NOT_EXISTED);
			output.getContent().setCostcalclist(null);
			return output;
		}
				
		
		return output;
	}
	
	
	 /**
     * 新增或修改 营销成本
     *
     * @param
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/addOrUpdateCostCalc", method = RequestMethod.POST)
    public RespBody<String> addOrUpdateCostCalc(ReqBody<CostCalcInput> input) {
    	CostCalcInput in = input.getContent();
    	marketCostservice.addOrUpdateCostCalc(in);
        return new RespBody<String>();
    }
    
    
    /**
	 * 营销成本-单个查询
	 * 
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/queryCostCaleInfo", method = RequestMethod.POST)
	public RespBody<CostCalcOutput> queryCostCaleInfo(ReqBody<CostCalcInput> input) {
		
		CostCalcInfo costCalc= marketCostservice.queryCostCaleInfo(input.getContent().getUserno(),input.getContent().getId());
		
		//返回对象组装
		CostCalcOutput outputMode = new CostCalcOutput();
		outputMode.setCostCalc(costCalc);
		
		RespBody<CostCalcOutput> output = new RespBody<CostCalcOutput>(outputMode);
		
		return output;
	}
	
	
	/**
	 * 营销成本-删除
	 * 
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/deleteCostCale", method = RequestMethod.POST)
	public RespBody<String> deleteCostCale(ReqBody<CostCalcInput> input) {
		
		marketCostservice.deleteCostCale(input.getContent());
		
		
		RespBody<String> output = new RespBody<String>();
		
		return output;
	}
	
	
	/**
	 * 营销成本-导入
	 * 
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/importCostCale", method = RequestMethod.POST)
	public RespBody<String> importCostCale(ReqBody<ImportCostCaleInput> input) {
		
		String result = marketCostservice.importCostCale(input.getContent());
		
		RespBody<String> output = new RespBody<String>(result);
		
		return output;
	}
	
	
	
}
