package com.ancun.boss.business.service.bizcanceluser.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ancun.boss.persistence.mapper.SystemNoticeMapper;
import com.ancun.boss.persistence.model.SystemNotice;
import com.ancun.boss.persistence.model.SystemNoticeExample;
import com.ancun.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.constant.ImportFailedConstant;
import com.ancun.boss.business.persistence.mapper.BizComboInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoImportFailedMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoQueryMapper;
import com.ancun.boss.business.persistence.mapper.BizUserLifeCircleMapper;
import com.ancun.boss.business.persistence.module.BizComboInfo;
import com.ancun.boss.business.persistence.module.BizComboInfoExample;
import com.ancun.boss.business.persistence.module.BizUserInfo;
import com.ancun.boss.business.persistence.module.BizUserInfoExample;
import com.ancun.boss.business.persistence.module.BizUserInfoImportFailed;
import com.ancun.boss.business.persistence.module.BizUserInfoQuery;
import com.ancun.boss.business.persistence.module.BizUserLifeCircle;
import com.ancun.boss.business.pojo.allprovince.ProvinceServiceInput;
import com.ancun.boss.business.pojo.bizcanceluser.BizBatchCancelUserInput;
import com.ancun.boss.business.pojo.bizcanceluser.BizCancelUserInput;
import com.ancun.boss.business.pojo.bizcanceluser.CancelUserInfo;
import com.ancun.boss.business.service.allprovince.IProvinceService;
import com.ancun.boss.business.service.allprovince.IPublicProvinceService;
import com.ancun.boss.business.service.bizcanceluser.IBizCanlelUserService;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.service.auth.impl.AuthenticationUtil;
import com.ancun.core.exception.EduException;
import com.google.common.collect.HashBasedTable;

/**
 * 业务用户退订
 *
 * @Created on 2016年3月18日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class BizCanlelUserServiceImpl implements IBizCanlelUserService {
	private static final Logger log = LoggerFactory.getLogger(BizCanlelUserServiceImpl.class);
	
	@Resource
	private BizUserInfoMapper bizInfoMapper;
	
	@Resource
	private BizUserLifeCircleMapper bizLifeInfoMapper;
	
	@Resource
	private BizUserInfoQueryMapper bizInfoQueryMapper;
	
	@Resource
	private IPublicProvinceService publicProvinceService;
	
	@Resource
	private BizComboInfoMapper bizComboInfoMapper;
	
	@Resource
	private AuthenticationUtil authenticationUtil;
	
	@Resource
	private BizUserInfoImportFailedMapper bizUserInfoImportFailedMapper;

	@Resource
	private SystemNoticeMapper systemNoticeMapper;


	/* (non-Javadoc)
	 * @see com.ancun.boss.service.bizcanceluser.IBizCanlelUserService#bizCancelUser(com.ancun.boss.pojo.bizcanceluser.BizCancelUserInput)
	 */
	@Override
	public void bizCancelUser(BizCancelUserInput input) throws EduException {
		CancelUserInfo cancelUserInfo = new CancelUserInfo();
		cancelUserInfo.setBizuserno(input.getBizuserno());
		cancelUserInfo.setIsrefunds(input.getIsrefunds());
		cancelUserInfo.setRefundsmoney(input.getRefundsmoney());
		cancelUserInfo.setDescription(input.getDescription());
		cancelUserInfo.setEntno(input.getEntno());
		cancelUserInfo.setUsertype(input.getUsertype());
		cancelUserInfo.setAccounttype(input.getAccounttype());
		
		List<BizUserInfoImportFailed> list = updateCancelTaocan(cancelUserInfo, input.getProvinceCode(), input.getType(), input.getBizno());
		if(list != null && list.size()>0 && list.get(0) !=null && list.get(0).getId() != 0L) throw new EduException(list.get(0).getId().intValue());
	}

	/* (non-Javadoc)
	 * @see com.ancun.boss.service.bizcanceluser.IBizCanlelUserService#bizBatchCancelUser(com.ancun.boss.pojo.bizcanceluser.BizBatchCancelUserInput)
	 */
	@Override
	@Async
	public void bizBatchCancelUser(BizBatchCancelUserInput input)
	        throws EduException {

		/**
		 * add by zkai on 2016/04/28
		 * 开通开始添加到系统通知表中，备注为 “退订开始”
		 */
		// 导入前插入到系统通知表中
		Long noticeId = null;
		try{
			noticeId = insertNotice(input.getUserno()); // 通知编号
		}catch (Exception e){
			log.error("通知编号添加失败",e);
		}

		String rpCode = input.getProvinceCode();
		String type = input.getType();
		List<BizUserInfoImportFailed> list = new ArrayList<BizUserInfoImportFailed>();
		List<CancelUserInfo> canceluserlist = input.getCanceluserlist();
		for (CancelUserInfo cancelUserInfo : canceluserlist) {
			list.addAll(updateCancelTaocan(cancelUserInfo, rpCode, type, input.getBizno()));
        }
		for (BizUserInfoImportFailed errorInfo : list) {
			errorInfo.setId(null);
			bizUserInfoImportFailedMapper.insert(errorInfo);
        }

		/**
		 * add by zkai on 2016/04/28
		 * 开通开始添加到系统通知表中，备注为 “退订结束”
		 */
		try{
			updateNotice(noticeId);
		}catch (Exception e){
			log.error("通知编号为："+noticeId+"修改失败",e);
		}
	}
	
	/**
	 * 用户套餐退订 修改用户记录
	 * @param input
	 * @param rpCode 省份
	 * @param type 电信/联通
	 * @param bizno 业务编码（数据分发用）
	 * @return
	 */
	public List<BizUserInfoImportFailed> updateCancelTaocan(CancelUserInfo input, String rpCode, String type, String bizno)  {
		List<BizUserInfoImportFailed> list = new ArrayList<BizUserInfoImportFailed>();
		Date time = new Date();
		String bizUserNo = input.getBizuserno();
		BizUserInfoImportFailed errorInfo = new BizUserInfoImportFailed();
		errorInfo.setUserNo(bizUserNo);
		errorInfo.setBizNo(bizno);
		errorInfo.setEntNo(input.getEntno());
		errorInfo.setUserType(input.getUsertype());
		errorInfo.setAccountType(input.getAccounttype());
		errorInfo.setIntime(time);
		errorInfo.setInsource(Constant.INSOURCE_1);
		//-------------鉴权-------------
		
		try {
			if(!authenticationUtil.validCheck(input.getBizuserno(), rpCode,type, bizno)){
				errorInfo.setRemark(ImportFailedConstant.AUTHENTICATION_ERROE);
				errorInfo.setId(Long.valueOf(MessageConstant.PHONE_AUTH_ERROR));
				list.add(errorInfo);
				return list;
			}
        } catch (EduException e) {
        	errorInfo.setRemark(ImportFailedConstant.AUTHENTICATION_ERROE + e.getMessage());
        	errorInfo.setId(Long.valueOf(e.getMessage()));
        	list.add(errorInfo);
			return list;
        }
		
		//-------------判断是否退费-------------
		double money = input.getRefundsmoney();
		// 退费金额设置，是否退费
		if ((input.getIsrefunds() && money <= 0) || (!input.getIsrefunds() && money > 0)) {
				errorInfo.setRemark(ImportFailedConstant.BIZ_USER_CANCEL_REFUNDSMONEY);
				errorInfo.setId(Long.valueOf(MessageConstant.BIZ_USER_CANCEL_REFUNDSMONEY));
				list.add(errorInfo);
				return list;
		}
		
		BizUserInfoExample infoExample = new BizUserInfoExample();
		infoExample.createCriteria().andUserNoEqualTo(bizUserNo).andBizNoEqualTo(bizno).andUserTypeEqualTo(input.getUsertype());
		List<BizUserInfo> infiList = bizInfoMapper.selectByExample(infoExample);
		if(infiList == null || infiList.size() != 1){
			errorInfo.setRemark(ImportFailedConstant.BIZ_USER_NOT_ONLY);
			errorInfo.setId(Long.valueOf(MessageConstant.BIZ_USER_IS_NOT_ONLY));
			list.add(errorInfo);
			return list;
		}
		
		HashBasedTable<String, String, IProvinceService> table = publicProvinceService.getProvinceService();
		IProvinceService shService =  table.get(rpCode, type);
		
		//-------------判断企业用户个人用户-------------
		//个人用户和子账号做更新单个操作，企业主账号同时更新子账号
		if(input.getUsertype().equals(Constant.USER_TYPE_ENT) && (input.getAccounttype()!=null && input.getAccounttype().equals(Constant.ACCOUNT_TYPE_MAIN))){
			if(input.getEntno()==null || input.getEntno().equals("")){
				errorInfo.setRemark(ImportFailedConstant.USER_ENT_NO_IS_NULL);
				errorInfo.setId(Long.valueOf(MessageConstant.USER_ENT_NO_IS_NULL));
				list.add(errorInfo);
				return list;
			}
			
			infoExample.clear();
			infoExample.createCriteria().andUserTypeEqualTo(Constant.USER_TYPE_ENT).andEntNoEqualTo(input.getEntno()).andBizNoEqualTo(bizno).andStatusEqualTo(BizRequestConstant.OPEN);
			infiList = bizInfoMapper.selectByExample(infoExample);
			for (BizUserInfo bizUserInfo : infiList) {
				BizUserInfoImportFailed error = null;
				//-------------调用远程接口-------------
				if(shService != null)
					error = provinceService(bizUserNo, bizUserInfo, shService);
				
				//错误error为Null,则表示远程调用正常，再调用updateUser
				if(error == null)
					error = updateUser(input, bizUserInfo, rpCode, type, bizno);
				
				if(error != null)
					list.add(error);
            }
			
		}else{
			BizUserInfo info = infiList.get(0);
			if(info.getStatus().equals(BizRequestConstant.CANCEL_USER)){
				errorInfo.setRemark(ImportFailedConstant.BIZ_USER_ALREADY_CALCEL);
				errorInfo.setId(Long.valueOf(MessageConstant.BIZ_USER_STATE_ERROR));
				list.add(errorInfo);
				return list;
			}
			//-------------调用远程接口-------------
			errorInfo = null;
			if(shService != null)
				errorInfo = provinceService(bizUserNo, info, shService);
			
			//错误info为Null,则表示远程调用正常，再调用updateUser
			if(errorInfo == null) 
				errorInfo = updateUser(input, info, rpCode, type, bizno);
			
			if(errorInfo != null)
				list.add(errorInfo);
		}
		
		return list;
	}
	
	/**
	 * 调用远程
	 * @param bizUserNo
	 * @param info
	 * @param shService
	 * @return
	 */
	BizUserInfoImportFailed provinceService(String bizUserNo, BizUserInfo info, IProvinceService shService){
		BizUserInfoImportFailed errorInfo = getFailedBean(info);
		try {
			//----------------获取套餐类型-----------------------
			BizComboInfoExample comboExample = new BizComboInfoExample();
			comboExample.createCriteria().andTcidEqualTo(info.getTcid());
			List<BizComboInfo> comboList = bizComboInfoMapper.selectByExample(comboExample);
			if(comboList == null || comboList.size() == 0){
				errorInfo.setRemark(ImportFailedConstant.BIZ_USER_TAOCAN_NOT_FOUND);
				errorInfo.setId(Long.valueOf(MessageConstant.BIZ_USER_TAOCAN_NOT_FOUND));
				return errorInfo;
			}
			//调用远程
			ProvinceServiceInput provinceInput = new ProvinceServiceInput();
			provinceInput.setUserTel(bizUserNo);
			provinceInput.setType(comboList.get(0).getType());
			shService.cancel(provinceInput);
		} catch (Exception e) {
			errorInfo.setRemark(ImportFailedConstant.BIZ_USER_INTERFACE_ERROR);
			errorInfo.setId(Long.valueOf(MessageConstant.BIZ_USER_INTERFACE_ERROR));
			return errorInfo;
		}
		return null;
	}
	
	/**
	 * 操作业务用户相关的三张表
	 * @param input
	 * @param info
	 * @param rpCode
	 * @param type
	 * @param bizno
	 * @return
	 */
	BizUserInfoImportFailed updateUser(CancelUserInfo input, BizUserInfo info, String rpCode, String type, String bizno){
		BizUserInfoImportFailed errorInfo = null;
		String bizUserNo = input.getBizuserno();
		Date time = new Date();
		
		//-------------更新业务用户表-------------
		BizUserInfo userInfo = new BizUserInfo();
		userInfo.setOfftime(input.getCanceldate()==null ? time : input.getCanceldate());
		userInfo.setStatus(BizRequestConstant.CANCEL_USER);
		userInfo.setOffsource(BizRequestConstant.SOURCE);//退订来源
		BizUserInfoExample infoExample = new BizUserInfoExample();
		infoExample.createCriteria().andUserNoEqualTo(bizUserNo).andBizNoEqualTo(bizno);
		if(bizInfoMapper.updateByExampleSelective(userInfo, infoExample)!=1){
			errorInfo = getFailedBean(info);
			errorInfo.setRemark(ImportFailedConstant.BIZ_USER_INSERT_TABLE_ERROR);
			errorInfo.setId(Long.valueOf(MessageConstant.BIZ_USER_INSERT_TABLE_ERROR));
			return errorInfo;
		}
		//-------------插入业务用户周期表-------------
		BizUserLifeCircle userLife = new BizUserLifeCircle();
		userLife.setUserNo(bizUserNo);
		userLife.setEntNo(info.getEntNo());
		userLife.setAccountType(info.getAccountType());
		userLife.setTcid(info.getTcid());
		userLife.setOrgNo(info.getOrgNo());
		userLife.setRpcode(info.getRpcode());
		userLife.setCityCode(info.getCityCode());
		userLife.setPhone(info.getPhone());
		userLife.setRectip(info.getRectip());
		userLife.setOpenCancel(BizRequestConstant.CANCEL);
		userLife.setSource(BizRequestConstant.SOURCE);
		userLife.setTimers(time);
		userLife.setUserType(info.getUserType());
		userLife.setUpdateTime(time);
		userLife.setBizNo(bizno);
		userLife.setCalledRecord(info.getCalledRecord());
		userLife.setCalledVoice(info.getCalledVoice());
		userLife.setCallerRecord(info.getCallerRecord());
		userLife.setCallerVoice(info.getCallerVoice());
		userLife.setRemark(input.getDescription());
		userLife.setPhonetype(info.getPhonetype());
		userLife.setAccountType(info.getAccountType());
		userLife.setPhone(info.getPhone());
		if(bizLifeInfoMapper.insert(userLife) != 1){
			errorInfo = getFailedBean(info);
			errorInfo.setRemark(ImportFailedConstant.BIZ_USER_INSERT_TABLE_ERROR);
			errorInfo.setId(Long.valueOf(MessageConstant.BIZ_USER_INSERT_TABLE_ERROR));
			return errorInfo;
		}
		
		//-------------插入业务用户查询表-------------
		BizUserInfoQuery userQuery = new BizUserInfoQuery();
		userQuery.setUserNo(bizUserNo);
		userQuery.setUpdateTime(time);
		userQuery.setStatus(BizRequestConstant.CANCEL_USER);
		userQuery.setBizNo(bizno);
		if(bizInfoQueryMapper.insert(userQuery) != 1){
			errorInfo = getFailedBean(info);
			errorInfo.setRemark(ImportFailedConstant.BIZ_USER_INSERT_TABLE_ERROR);
			errorInfo.setId(Long.valueOf(MessageConstant.BIZ_USER_INSERT_TABLE_ERROR));
			return errorInfo;
		}

		return errorInfo;
		
	}
	
	BizUserInfoImportFailed getFailedBean(BizUserInfo info){
		BizUserInfoImportFailed errorInfo = new BizUserInfoImportFailed();
		errorInfo.setUserNo(info.getUserNo());
		errorInfo.setBizNo(info.getBizNo());
		errorInfo.setEntNo(info.getEntNo());
		errorInfo.setUserType(info.getUserType());
		errorInfo.setAccountType(info.getAccountType());
		errorInfo.setIntime(new Date());
		errorInfo.setInsource(Constant.INSOURCE_1);
		errorInfo.setPhone(info.getPhone());
		errorInfo.setTcid(info.getTcid());
		return errorInfo;
	}

	/**
	 * add by zkai 2016/04/28
	 * 插入系统通知表
	 * @param userno
	 */
	private Long insertNotice(String userno){
		SystemNotice systemNotice = new SystemNotice();
		systemNotice.setCommitTime(DateUtils.getCurrentDate());
		systemNotice.setTitle("个人用户批量退订");
		systemNotice.setType("Email");
		systemNotice.setContent("开导退订 。。。");
		systemNotice.setUserno(userno);
		systemNotice.setReadLabel("1");
		systemNoticeMapper.insert(systemNotice);
		return systemNotice.getId();
	}


	/**
	 * add by zkai on 2016/04/28
	 * 修改系统通知表
	 * @param noticeId
	 */
	private Long updateNotice(Long noticeId){
		SystemNoticeExample systemNoticeExample = new SystemNoticeExample();
		systemNoticeExample.createCriteria().andIdEqualTo(noticeId);
		SystemNotice systemNotice = new SystemNotice();
		systemNotice.setCommitTime(DateUtils.getCurrentDate());
		systemNotice.setContent("退订结束");
		systemNoticeMapper.updateByExampleSelective(systemNotice,systemNoticeExample);
		return systemNotice.getId();
	}
	
}
