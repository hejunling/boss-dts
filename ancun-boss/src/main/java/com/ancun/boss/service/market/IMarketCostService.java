package com.ancun.boss.service.market;

import java.util.List;

import com.ancun.boss.pojo.marketInfo.CostCalcInfo;
import com.ancun.boss.pojo.marketInfo.CostCalcInput;
import com.ancun.boss.pojo.marketInfo.ImportCostCaleInput;
import com.ancun.boss.pojo.marketInfo.QueryCostCalcListInput;


public interface IMarketCostService {
	
	public List<CostCalcInfo> queryCostCalcList(QueryCostCalcListInput input);
	
	public void addOrUpdateCostCalc(CostCalcInput input);
	
	public CostCalcInfo queryCostCaleInfo(String userno,Long id);
	
	public void deleteCostCale(CostCalcInput input);
	
	public String importCostCale(ImportCostCaleInput input);
	
}
