package com.ancun.boss.business.service.bizuserinfoImport.impl;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.persistence.mapper.BizUserInfoImportFailedMapper;
import com.ancun.boss.business.persistence.module.BizUserInfoImportFailed;
import com.ancun.boss.business.persistence.module.BizUserInfoImportFailedExample;
import com.ancun.boss.business.pojo.bizuserinfoimport.BizUserInfoImportInfo;
import com.ancun.boss.business.pojo.bizuserinfoimport.SelectBizUserInfoImportInput;
import com.ancun.boss.business.pojo.bizuserinfoimport.SelectBizUserInfoImportOutput;
import com.ancun.boss.business.service.bizuserinfoImport.IBizUserInfoImportService;
import com.ancun.boss.business.service.dataDic.IQueryDictionaryService;
import com.ancun.boss.business.service.dataDic.IUserTaocanService;
import com.ancun.core.page.Page;
import com.ancun.utils.DateUtil;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BizUserInfoImportServiceImpl implements IBizUserInfoImportService {

	@Resource
	private BizUserInfoImportFailedMapper bizUserInfoImportFailedMapper;

	@Resource
	private IQueryDictionaryService queryDictionaryService;

	@Resource
	private IUserTaocanService taocanService;

	private static final Logger log = LoggerFactory.getLogger(BizUserInfoImportServiceImpl.class);

	/**
	 * 用户导入失败列表查询
	 * @param input
	 * @return
     */
	@Override
	public SelectBizUserInfoImportOutput selectBizUserInfoImportList(SelectBizUserInfoImportInput input) {

		// 获取数据字典MAP
		HashMap<String, String> dicMap = queryDictionaryService.getDicAsmap();

		// 获取套餐信息
		HashMap<String, String> taocaoMap = taocanService.getTaocanAsmap();

		// 查询信息
		BizUserInfoImportFailedExample example = new BizUserInfoImportFailedExample();
		BizUserInfoImportFailedExample.Criteria criteria = example.createCriteria();
		// 用户编号不为空
		if(StringUtil.isNotEmpty(input.getUserNum())){
			criteria.andUserNoEqualTo(input.getUserNum());
		}
		// 账号类型(1:主账号;2:子账号)
		if(StringUtil.isNotEmpty(input.getAccountType())){
			criteria.andAccountTypeEqualTo(input.getAccountType());
		}
		// 用户类型(1:个人;2:企业)
		if(StringUtil.isNotEmpty(input.getUserType())){
			criteria.andUserTypeEqualTo(input.getUserType());
		}
		// 注册来源
		if(StringUtil.isNotEmpty(input.getInsource())){
			criteria.andInsourceEqualTo(input.getInsource());
		}
		// 导入开始时间
		if(StringUtil.isNotEmpty(DateUtil.convertDateToString(input.getBeginTime()))){
			criteria.andIntimeGreaterThanOrEqualTo((input.getBeginTime()));
		}
		// 导入结束时间
		if(StringUtil.isNotEmpty(DateUtil.convertDateToString(input.getEndTime()))){
			criteria.andIntimeLessThanOrEqualTo((input.getEndTime()));
		}

		// 分页信息
		Page page = new Page();
		if (StringUtil.isNotBlank(input.getCurrentpage())) {
			page.setCurrentpage(Integer.valueOf(input.getCurrentpage()));
			page.setPagesize(Integer.valueOf(input.getPagesize()));
			example.setPage(page);
		}
		example.setOrderByClause("INTIME DESC");
		List<BizUserInfoImportFailed> bizUserInfoImportFaileds = bizUserInfoImportFailedMapper.selectByExample(example);


		List<BizUserInfoImportInfo> bizUserInfoImportFailedList = new  ArrayList<BizUserInfoImportInfo>();
		for (BizUserInfoImportFailed info: bizUserInfoImportFaileds) {
			BizUserInfoImportInfo bizUserInfoImportInfo = new BizUserInfoImportInfo();
			if(StringUtil.isNotEmpty(info.getInsource())){
				bizUserInfoImportInfo.setInsourceName(dicMap.get(Constant.OPENTYPE + "_" + info.getInsource()));
			}else {
				bizUserInfoImportInfo.setInsourceName(null);
			}
			bizUserInfoImportInfo.setId(info.getId());
			bizUserInfoImportInfo.setInsource(info.getInsource());
			bizUserInfoImportInfo.setAccountType(info.getAccountType());
			bizUserInfoImportInfo.setBizNo(info.getBizNo());
			bizUserInfoImportInfo.setEntNo(info.getEntNo());
			bizUserInfoImportInfo.setIntime(info.getIntime());
			bizUserInfoImportInfo.setPhone(info.getPhone());
			bizUserInfoImportInfo.setUserType(info.getUserType());
			bizUserInfoImportInfo.setRemark(info.getRemark());
			bizUserInfoImportInfo.setTcid(info.getTcid());
			if(info.getTcid() != null){
				String taocanname = taocaoMap.get(info.getTcid().toString());
				bizUserInfoImportInfo.setTaocanName(taocanname);
			}

			bizUserInfoImportInfo.setUserNo(info.getUserNo());
			bizUserInfoImportFailedList.add(bizUserInfoImportInfo);
		}
		SelectBizUserInfoImportOutput out = new SelectBizUserInfoImportOutput();
		out.setBizUserInfoImportInfoList(bizUserInfoImportFailedList);
		out.setPageinfo(page);
		return out;
	}
}
