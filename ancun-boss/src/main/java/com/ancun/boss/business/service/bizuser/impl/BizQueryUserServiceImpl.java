package com.ancun.boss.business.service.bizuser.impl;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.persistence.mapper.BizUserInfoMapper;
import com.ancun.boss.business.persistence.module.BizUserInfo;
import com.ancun.boss.business.persistence.module.BizUserInfoExample;
import com.ancun.boss.business.pojo.bizuser.*;
import com.ancun.boss.business.service.bizuser.IBizQueryUserService;
import com.ancun.boss.business.service.dataDic.IQueryDictionaryService;
import com.ancun.boss.business.service.dataDic.IUserTaocanService;
import com.ancun.core.page.Page;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BizQueryUserServiceImpl implements IBizQueryUserService {
	
	private static final Logger log = LoggerFactory.getLogger(BizQueryUserServiceImpl.class);
	
    /** 提醒标题 */
    private static final String NOTICE_TITLE = "用户开通相关";
    
	public static final String NeedOpen = "NeedOpen";
	
	@Resource
	private BizUserInfoMapper bizUserInfoMapper;

	@Resource
	private IQueryDictionaryService queryDictionaryService;

	@Resource
	private IUserTaocanService userTaocanService;


	@Override
	public BizUserInfoListOutput getBizUserInfoList(BizUserInfoQueryInput input) {

		BizUserInfoExample ex = new BizUserInfoExample();
		BizUserInfoExample.Criteria c = ex.createCriteria();

		// 用户号码 不支持模糊查询
		if (StringUtil.isNotEmpty(input.getUsernum())) {
			c.andUserNoEqualTo(input.getUsernum());
		}
		// 联系电话
		if (StringUtil.isNotEmpty(input.getPhone())) {
			c.andPhoneLike("%" + input.getPhone() + "%");
		}
		// 所属省份
//		if (StringUtil.isNotEmpty(input.getRpcode())) {
//			c.andRpcodeEqualTo(input.getRpcode());
//		}

		// 所属业务
		if (StringUtil.isNotEmpty(input.getBizno())) {
			c.andBizNoEqualTo(input.getBizno());
		}
		// 用户类型
		if (StringUtil.isNotEmpty(input.getUsertype())) {
			c.andUserTypeEqualTo(input.getUsertype());
		}
//		if (StringUtil.isNotEmpty(input.getRpcode())) {
//			String[] split = input.getRpcode().split(";");
//			if (null != split && split.length == 1) {
//				// 一个选中情况
//				c.andRpcodeEqualTo(split[0]);
//			} else {
//				// 未选择时
//				List<String> rpCodeList = Arrays.asList(split);
//				c.andRpcodeIn(rpCodeList);
//			}
//		} else {
//			return new BizUserInfoListOutput();// 数据权限为空时
//		}
		// 套餐ID
		if (null != input.getTaocanid() && 0 != input.getTaocanid()) {
			c.andTcidEqualTo(input.getTaocanid());
		}
		// 号码状态
		if (StringUtil.isNotEmpty(input.getUserstatus())) {
			c.andStatusEqualTo(input.getUserstatus());
			// } else {
			// c.andUserstatusEqualTo(Constants.USER_STATUS_1);// 默认开通
		}
		// 开通类型
		if (StringUtil.isNotEmpty(input.getIsignupsource())) {
			c.andInsourceEqualTo(input.getIsignupsource());
		}
		// 开通开始时间
		if (null != input.getOpenstarttime()) {
			c.andIntimeGreaterThanOrEqualTo(input.getOpenstarttime());
		}
		// 开通结束时间
		if (null != input.getOpenendtime()) {
			c.andIntimeLessThanOrEqualTo(input.getOpenendtime());
		}
		// 退订开始时间
		if (null != input.getCancelstarttime()) {
			c.andOfftimeGreaterThanOrEqualTo(input.getCancelstarttime());
		}
		// 退订结束时间
		if (null != input.getCancelendtime()) {
			c.andOfftimeLessThanOrEqualTo(input.getCancelendtime());
		}
		//
		// 分页信息
		Page page = new Page();
		if (StringUtil.isNotBlank(input.getCurrentpage())) {
			page.setCurrentpage(Integer.valueOf(input.getCurrentpage()));
			page.setPagesize(Integer.valueOf(input.getPagesize()));
			ex.setPage(page);
		}

		ex.setOrderByClause("INTIME  DESC ");

		List<BizUserInfo> bizUserInfoList = bizUserInfoMapper.selectByExample(ex);

		// 获取数据字典MAP
		HashMap<String, String> dicMap = queryDictionaryService.getDicAsmap();

		// 获取套餐信息
		HashMap<String, String> taocaoMap = userTaocanService.getTaocanAsmap();

		List<BizUserInfoOutput> bizUserOutputList = new ArrayList<BizUserInfoOutput>();

		for (int i = 0; i <  bizUserInfoList.size(); i++) {
			BizUserInfo bizUserInfo = bizUserInfoList.get(i);
			BizUserInfoOutput bizUserInfoOutput = new BizUserInfoOutput();
			bizUserInfoOutput.setUsernum(bizUserInfo.getUserNo());

			// 用户状态
			bizUserInfoOutput.setUserstatus(Constant.USER_STATUS_MAP.get(bizUserInfo.getStatus()));

			// 开通类型 Modele_value获取
			String insource = null ;
			if(StringUtil.isNotBlank(bizUserInfo.getInsource())){
				insource = dicMap.get(Constant.OPENTYPE + "_" + bizUserInfo.getInsource());
			}
			bizUserInfoOutput.setIsignupsource(insource);

			bizUserInfoOutput.setOpentime(bizUserInfo.getIntime());
			bizUserInfoOutput
					.setCanceltime(bizUserInfo.getOfftime());
			// 省份
			bizUserInfoOutput.setRpname(dicMap.get(Constant.PROVINCES + "_" + bizUserInfo.getRpcode()));

			String taocanName = null;
			String tcType = null;
			if(bizUserInfo.getTcid() != null){
				taocanName = taocaoMap.get(bizUserInfo.getTcid().toString());
				tcType = taocaoMap.get(bizUserInfo.getTcid().toString()+"tcType");
			}
			bizUserInfoOutput.setTaocanname(taocanName);// 套餐名称

			bizUserInfoOutput.setRpcode(bizUserInfo.getRpcode()); // 省份编号
			bizUserInfoOutput.setTaocantype(tcType); // 套餐类型
			bizUserInfoOutput.setPhone(bizUserInfo.getPhone()); // 电话号码

			bizUserInfoOutput.setId(String.valueOf(bizUserInfo.getId())); // 主键
			bizUserInfoOutput.setEntno(bizUserInfo.getEntNo()); // 企业编号
			bizUserInfoOutput.setUsertype(bizUserInfo.getUserType()); // 用户类型(1:个人;2:企业)
			bizUserInfoOutput.setAccounttype(bizUserInfo.getAccountType()); // 账号类型(1:主账号;2:子账号;3:个人账号)
			bizUserInfoOutput.setBizno(bizUserInfo.getBizNo()); // 业务编号
			bizUserOutputList.add(bizUserInfoOutput);
		}

		// 返回内容实体
		BizUserInfoListOutput output = new BizUserInfoListOutput();
		output.setBizuserInfolist(bizUserOutputList);
		output.setPageinfo(page);
		return output;
	}

	/**
	 * 根据主键得到用户信息
	 * @param input
	 * @return
	 */
	public BizUserInfoKeyOutput selectUserInfoByKey(BizUserInfoKeyInput input){
		BizUserInfoKeyOutput output = new BizUserInfoKeyOutput();

		BizUserInfo bizUserInfo = bizUserInfoMapper.selectByPrimaryKey(Long.valueOf(input.getId()));
		output.setBizUserInfo(bizUserInfo);
		return output;
	}

}
