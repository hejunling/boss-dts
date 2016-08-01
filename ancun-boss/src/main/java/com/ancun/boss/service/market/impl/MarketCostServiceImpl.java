package com.ancun.boss.service.market.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.CostCalcBizMapper;
import com.ancun.boss.persistence.mapper.CostCalcMapper;
import com.ancun.boss.persistence.mapper.ImportFailedRecordMapper;
import com.ancun.boss.persistence.model.CostCalc;
import com.ancun.boss.persistence.model.ImportFailedRecord;
import com.ancun.boss.pojo.marketInfo.CostCalcInfo;
import com.ancun.boss.pojo.marketInfo.CostCalcInput;
import com.ancun.boss.pojo.marketInfo.ImportCostCaleInput;
import com.ancun.boss.pojo.marketInfo.QueryCostCalcListInput;
import com.ancun.boss.service.market.IMarketCostService;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.core.constant.ResponseConst;
import com.ancun.core.exception.EduException;

/**
 * 营销成本管理传逻辑实现类
 *
 * @Created on 2015年10月12日
 * @author lyh
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class MarketCostServiceImpl implements IMarketCostService{
	@Resource
	private CostCalcMapper costCalcMapper;
	
	@Resource
	private CostCalcBizMapper costCalcBizMapper;
	
	@Resource
	private ImportFailedRecordMapper importFailedRecordMapper;
	
	@Resource
	private BaseDataServiceImpl baseDataService;
	
	
	@Override
    public List<CostCalcInfo> queryCostCalcList(QueryCostCalcListInput input) {
		// 缺省排序方式
		if(StringUtil.isBlank(input.getOrdersort())){
			input.setOrdersort("desc");
		}
		
		List<CostCalcInfo> list = costCalcBizMapper.queryCostCalcList(input);
		
		return list;
    }


	@Override
    public void addOrUpdateCostCalc(CostCalcInput input) {
		//创建设置对象
		CostCalc cost = setCostCalc(input);
		if(input.getId()==null){
			//新增
			costCalcMapper.insertSelective(cost);
		}else{
			//修改
			//判断是否存在该记录
			CostCalc costCalc = costCalcMapper.selectByPrimaryKey(input.getId());
			if(costCalc==null){
				throw new EduException(ResponseConst.RECORD_NOT_EXISTED);
			}
			//更新
			costCalcMapper.updateByPrimaryKeySelective(cost);
			
		}
	    
    }


	private CostCalc setCostCalc(CostCalcInput input) {
		CostCalc cost = new CostCalc();
		String avgCost = null;
		if(StringUtils.isNotBlank(input.getAvgcost())){
			double   avg   =   Double.parseDouble(input.getAvgcost());  
			BigDecimal   b   =   new   BigDecimal(avg);  
			avgCost   =   String.valueOf(b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());  
		}
		cost.setAvgCost(avgCost);
		cost.setBill(input.getBill());
		cost.setBusiness(input.getBusiness());
		cost.setCalledTeam(input.getCalledteam());
		cost.setDevicePay(input.getDevicepay());
		cost.setEmpPay(input.getEmppay());
		cost.setId(input.getId());
		cost.setInsurance(input.getInsurance());
		cost.setManageExp(input.getManageexp());
		cost.setManagePay(input.getManagepay());
		cost.setMonth(input.getMonth());
		cost.setNetExp(input.getNetexp());
		cost.setNetPay(input.getNetpay());
		cost.setOperator(input.getOperator());
		cost.setRent(input.getRent());
		cost.setRentExp(input.getRentexp());
		cost.setSitNumber(input.getSitnumber());
		cost.setSitPrice(input.getSitprice());
		cost.setSuccessNumber(input.getSuccessnumber());
		cost.setSucNumber(input.getSucnumber());
		cost.setSucPerPrice(input.getSucperprice());
		cost.setTotalCost(input.getTotalcost());
		cost.setType(input.getType());
		cost.setWeExp(input.getWeexp());
		cost.setWePay(input.getWepay());
	    return cost;
    }

	/*单个查询*/
	@Override
    public CostCalcInfo queryCostCaleInfo(String userno,Long id) {
		//判断id是否为空
		if(id==null){
			throw new EduException(MessageConstant.ACCESS_ID_IS_NULL);
		}
		//查询
		QueryCostCalcListInput input = new QueryCostCalcListInput();
		input.setId(id);
		input.setUserno(userno);
		List<CostCalcInfo> list = costCalcBizMapper.queryCostCalcList(input);
		
		//判断查询结果
		if(list==null){
			throw new EduException(ResponseConst.RECORD_NOT_EXISTED);
		}
		
	    return list.get(0);
    }

	//单个删除
	@Override
    public void deleteCostCale(CostCalcInput input) {
		//判断id是否为空
		if(input.getId()==null){
			throw new EduException(MessageConstant.ACCESS_ID_IS_NULL);
		}
		
		//查询
		CostCalc costCalc = costCalcMapper.selectByPrimaryKey(input.getId());
		
		//判断查询结果
		if(costCalc==null){
			throw new EduException(ResponseConst.RECORD_NOT_EXISTED);
		}
		
		//删除
		costCalcMapper.deleteByPrimaryKey(input.getId());
    }


	@Override
    public String importCostCale(ImportCostCaleInput input) {
		
		//判断公共参数
		
		//业务参数不能为空
		if(StringUtils.isBlank(input.getBusiness())){
			throw new EduException(MessageConstant.BUSINESS_MUST_BE_NOT_EMPTY);
		}
		
		//外呼团队不能为空
		if(StringUtils.isBlank(input.getCalledteam())){
			throw new EduException(MessageConstant.MARKETCHECK_CALLEDTEAM_MUST_BE_NOT_EMPTY);
		}
		
		//日期不能为空
		if(StringUtils.isBlank(input.getMonth())){
			throw new EduException(MessageConstant.MARKETCOST_MONTH_MUST_BE_NOT_EMPTY);
		}
		
		//结款方式不能为空
		if(StringUtils.isBlank(input.getType())){
			throw new EduException(MessageConstant.JK_TYPE_MUST_BE_NOT_EMPTY);
		}
		List<CostCalc> insertList = new ArrayList<CostCalc>();
		List<CostCalcInput> infos = input.getCostcalelist();
		//印证每条记录的每个字段是否合法
		for(int i=0;i<infos.size();i++){
			CostCalcInput inCostCalc = infos.get(i);
			//设置每条记录的公共参数
			inCostCalc.setBusiness(input.getBusiness());
			inCostCalc.setCalledteam(input.getCalledteam());
			inCostCalc.setMonth(input.getMonth());
			inCostCalc.setType(input.getType());
			
			boolean right = validateMarketCostInfo(inCostCalc,input);
			
			if(right){
				//创建设置对象
				CostCalc cost = setCostCalc(inCostCalc);
				insertList.add(cost);
			}
			
		}
		if (insertList.size() > 0) {
			for (CostCalc cost:insertList) {
				costCalcMapper.insert(cost);
			}
		}
		String result = "";
		if(infos.size() - insertList.size()>0){
			result = "导入成功条数："+ insertList.size() + "  失败条数：" + (infos.size() - insertList.size());
		}
	    return result;
    }


	private boolean validateMarketCostInfo(CostCalcInput inCostCalc,ImportCostCaleInput input) {
		boolean right = true;
		StringBuilder msg = new StringBuilder();
		msg.append(input.getBusinessname()).append("-").append(input.getCalledteamname()).append("-");
		msg.append(input.getTypename()).append("-").append(input.getMonth()).append("-第").append(inCostCalc.getXslindex());
		msg.append("行：");
		//判断某种结款方式下 每条记录的参数是否正确
		if(BizRequestConstant.JKFS_BXJG_ZXS.equals(inCostCalc.getType())){
			
			//包席价为空
			if(StringUtils.isBlank(inCostCalc.getSitprice())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.SIT_PRICE_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//坐席数为空
			if(StringUtils.isBlank(inCostCalc.getSitnumber())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.SIT_NUMBER_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			
		}else if(BizRequestConstant.JKFS_CGJDJ_CGL.equals(inCostCalc.getType())){
			//成功件单价为空
			if(StringUtils.isBlank(inCostCalc.getSucperprice())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.SUC_PER_PRICE_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//成功量为空
			if(StringUtils.isBlank(inCostCalc.getSucnumber())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.SUC_NUMBER_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			
		}else{
			//坐席工资为空
			if(StringUtils.isBlank(inCostCalc.getEmppay())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.EMP_PAY_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//话费为空
			if(StringUtils.isBlank(inCostCalc.getBill())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.BILL_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//管理成本为空
			if(StringUtils.isBlank(inCostCalc.getManagepay())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.MANAGE_PAY_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//管理成本公式为空
			if(StringUtils.isBlank(inCostCalc.getManageexp())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.MANAGE_EXP_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//保险为空
			if(StringUtils.isBlank(inCostCalc.getInsurance())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.INSURANCE_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//水电费公式为空
			if(StringUtils.isBlank(inCostCalc.getWeexp())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.WE_EXP_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//水电费为空
			if(StringUtils.isBlank(inCostCalc.getWepay())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.WE_EXP_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			//网费公式为空
			if(StringUtils.isBlank(inCostCalc.getNetexp())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.NET_EXP_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//网费为空
			if(StringUtils.isBlank(inCostCalc.getNetpay())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.NET_PAY_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//房租公式为空
			if(StringUtils.isBlank(inCostCalc.getRentexp())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.RENT_EXP_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//房租为空
			if(StringUtils.isBlank(inCostCalc.getRent())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.RENT_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//硬件设备为空
			if(StringUtils.isBlank(inCostCalc.getDevicepay())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.DEVICE_PAY_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			
			//成功件数为空
			if(StringUtils.isBlank(inCostCalc.getSuccessnumber())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.SUCCESS_NUMBER_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
			
			//单件成本为空
			if(StringUtils.isBlank(inCostCalc.getAvgcost())){
				right = false;
				msg.append(baseDataService.getBussinessMessage(MessageConstant.AVG_COST_MUST_BE_NOT_EMPTY_CODE+""));
				msg.append(";");
			}
		}
		//总成本为空
		if(StringUtils.isBlank(inCostCalc.getTotalcost())){
			right = false;
			msg.append(baseDataService.getBussinessMessage(MessageConstant.TOTAL_COST_MUST_BE_NOT_EMPTY_CODE+""));
			msg.append(";");
		}else{
			//总成本不为空  计算总成本是否正确
			double total = 0;
			double totalcost = 0;
			double sigleCost = 0;
			
			//检查所有字段都不为空  再检查总计结果
			if(right){
				if(BizRequestConstant.JKFS_BXJG_ZXS.equals(inCostCalc.getType())){
					//包席价格*坐席数
		            total = (double)(Double.parseDouble(inCostCalc.getSitprice())*Double.parseDouble(inCostCalc.getSitnumber()));
				}else if(BizRequestConstant.JKFS_CGJDJ_CGL.equals(inCostCalc.getType())){
					total = (double)(Double.parseDouble(inCostCalc.getSucperprice())*Double.parseDouble(inCostCalc.getSucnumber()));
				}else{
					total = (double)(Double.parseDouble(inCostCalc.getEmppay())+Double.parseDouble(inCostCalc.getBill())+Double.parseDouble(inCostCalc.getManagepay())+
							Double.parseDouble(inCostCalc.getInsurance())+Double.parseDouble(inCostCalc.getWepay())+Double.parseDouble(inCostCalc.getNetpay())+
							Double.parseDouble(inCostCalc.getRent())+Double.parseDouble(inCostCalc.getDevicepay()));
					
					//结款方式为员工工资-保险-其他各项时 判断计算的单件成本与导入单件成本相减误差是否在1以内
					sigleCost = total/Double.parseDouble(inCostCalc.getSuccessnumber());
					double  sub =  sigleCost-Double.parseDouble(inCostCalc.getAvgcost());
					if(sub <=-1 || sub>=1){
						right = false;
						msg.append(baseDataService.getBussinessMessage(MessageConstant.AVG_COST_IS_NOT_EQUAL+""));
						msg.append(";");
					}
				}
				totalcost = Double.parseDouble(inCostCalc.getTotalcost());
				//判断计算结果是否与表格中总计结果是否相等
				if(total!=totalcost){
					right = false;
					msg.append(baseDataService.getBussinessMessage(MessageConstant.TOTAL_IS_NOT_EQUAL+""));
					msg.append(";");
				}
				
			}
		}
		if(!right){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ImportFailedRecord fail = new ImportFailedRecord();
			fail.setFuncname("数据导入");
			fail.setMenuname("营销成本管理");
			fail.setMenuno("67550a0e-3e05-444a-9ce6-8f8c43975719");
			fail.setImportTime(df.format(new Date()));
			fail.setUserno(input.getUserno());
			fail.setFailedRemark(msg.toString());
			importFailedRecordMapper.insertSelective(fail);
		}
	    return right;
    }


	

}
