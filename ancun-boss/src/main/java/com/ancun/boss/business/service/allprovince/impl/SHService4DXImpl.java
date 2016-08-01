package com.ancun.boss.business.service.allprovince.impl;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.persistence.biz.BizGetSequenceMapper;
import com.ancun.boss.business.pojo.allprovince.ProvinceServiceInput;
import com.ancun.boss.business.pojo.bizcanceluser.ActionTypeEnum;
import com.ancun.boss.business.service.allprovince.IProvinceService;
import com.ancun.boss.business.utils.AccountUtil;
import com.ancun.boss.business.utils.fptcsrvc.FptcsrvcInfoSyncUtil;
import com.ancun.common.biz.email.SendEmail;
import com.ancun.core.exception.EduException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "SHService")
public class SHService4DXImpl implements IProvinceService {
	
	private static final Logger loger = LoggerFactory.getLogger(IProvinceService.class);
	
	@Resource
	private BizGetSequenceMapper getSequenceMapper;

	@Override
	public void open(ProvinceServiceInput provinceInput) {
		//主角录音
		if(Constant.TC_TYPE_CALLER.equals(provinceInput.getType())){
			openCaller(provinceInput.getUserTel());
		}
		//双向录音
		else if(Constant.TC_TYPE_BIDIRECTION.equals(provinceInput.getType())){
			openCallerAndCalled(provinceInput.getUserTel());
		}
	}

	@Override
	public void cancel(ProvinceServiceInput provinceInput) {
		//主角录音
		if(Constant.TC_TYPE_CALLER.equals(provinceInput.getType())){
			cancelCaller(provinceInput.getUserTel());
		}
		//双向录音
		else if(Constant.TC_TYPE_BIDIRECTION.equals(provinceInput.getType())){
			cancelCallerAndCalled(provinceInput.getUserTel());
		}
	}

	@Override
	public void postChangeRequest(String userno, String changeTcType, String oldTcID, String newTcID, boolean rollback) {

	}

	/**
	 * 调用上海户套餐修改接口
	 * @param provinceInput
	 * add by zkai on 2015/05/06
	 */
	@Override
	public boolean postChangeRequest(ProvinceServiceInput provinceInput) {

		String title = "上海调用远程接口异常";
		String content = "套餐修改，逻辑：1.退订原套餐；2.开通新套餐。如果异常，请按逻辑调用远程接口 \n " ;
		SendEmail sendEmail = new SendEmail();

		// 老套餐信息
		ProvinceServiceInput cancelInfo = new ProvinceServiceInput();
		cancelInfo.setType(provinceInput.getType()); // 老的套餐
		cancelInfo.setUserTel(provinceInput.getUserTel()); // 用户号码
		// 先退订原套餐
		try {
			cancel(cancelInfo);
			loger.debug("退订原套餐成功...下面开始重新开通新套餐");
		}catch (Exception e){
			loger.error("退订原套餐，失败：",e);
			content += "退订原套餐失败，原套餐信息：套餐类型-"+provinceInput.getType()+" 用户号码-"+provinceInput.getUserTel()+" \n "+"失败套餐开通需要信息：新套餐类型-"+provinceInput.getChangeTcType()+" 用户号码-"+provinceInput.getUserTel();
			sendEmail.send(Constant.SEND_EMAIL_TO, title, content, null, true);
			return false;
		}


		// 新套餐信息
		ProvinceServiceInput openInfo = new ProvinceServiceInput();
		openInfo.setType(provinceInput.getChangeTcType()); // 新套餐
		openInfo.setUserTel(provinceInput.getUserTel()); // 用户号码
		// 再开通新套餐
		try{
			open(openInfo);
			loger.debug("重新开通新套餐成功...");
		}catch (Exception e){
			loger.error("退订原套餐，开通新套餐失败：",e);
			content += "退订原套餐，开通新套餐失败 \n "+"失败套餐开通需要信息：新套餐编号-"+provinceInput.getChangeTcType()+" 用户号码-"+provinceInput.getUserTel();
			sendEmail.send( Constant.SEND_EMAIL_TO , title, content, null, true);
			return false;
//			loger.error("退订原套餐，开通新套餐失败...下面需要回滚原套餐");
//
//			try {
//				open(cancelInfo);
//				loger.error("回滚原套餐成功...");
//			}catch(EduException ex) {
//				loger.error("回滚原套餐时也异常",ex);
//			}
		}
		return true;

	}


	/**
	 * 开通主叫套餐
	 * @param userTel ：用户电话号码 
	 * @throws EduException
	 */
	void openCaller(String userTel) throws EduException{
		FptcsrvcInfoSyncUtil.userInfoSync(userTel);
		FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Open, userTel, true);
		try {
			AccountUtil.accountOpen(userTel, Constant.TYPE_CALLER);
			loger.debug("开通主叫套餐成功...");
		}catch(EduException e) {
			loger.error("openCaller开通主叫套餐异常...下面需要回滚订购关系");
			try {
				FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Unsubscribe, userTel, true);
				loger.error("openCaller回滚订购关系成功...");
			}catch(EduException ex) {
				loger.error("回滚订购关系时也异常");
			}
			throw new EduException(e.getCode());
		}
	}
	
	
	/**
	 * 开通双向套餐
	 * @param userTel ：用户电话号码 
	 * @throws EduException
	 */
	void openCallerAndCalled(String userTel) throws EduException {
		FptcsrvcInfoSyncUtil.userInfoSync(userTel);
		FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Open, userTel, true);
		
		try {
			FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Open, userTel, false);
		}catch(EduException e) {
			loger.error("openCallerAndCalled调用被叫订购关系时异常..需将之前主叫订购关系的关系退掉....");
			try {
				FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Unsubscribe, userTel, true);
				loger.debug("openCallerAndCalled退订主叫订购关系的关系正常");
			}catch(EduException ex) {
				loger.error("openCallerAndCalled退订主叫订购关系的关系异常....");
			}
			throw new EduException(e.getCode());
		}
		
		
		try {
			AccountUtil.accountOpen(userTel, Constant.TYPE_CALLER);
		}catch(EduException e) {
			loger.error("开通双向套餐：AccountUtil.accountOpen.CALLER异常，需要回滚主叫和被叫订购关系...");
			rollbackOpenCancel(userTel);
			throw new EduException(e.getCode());
		}
		
		
		try {
			AccountUtil.accountOpen(userTel, Constant.TYPE_CALLED);
		}catch(EduException e) {
			loger.error("开通双向套餐：AccountUtil.accountOpen.CALLED异常，需要回滚主叫和被叫订购关系及CALLER...");
			try {
				AccountUtil.accountStop(userTel, Constant.TYPE_CALLER);
				loger.debug("回滚CALLER成功....");
			}catch(EduException ex) {
				loger.error("回滚CALLER异常....");
			}
			
			rollbackOpenCancel(userTel);
			throw new EduException(e.getCode());
		}
		
	}
	
	/**
	 * 主叫退订
	 * @param userTel ：用户电话号码
	 * @throws EduException
	 */
	void cancelCaller(String userTel) throws EduException {
		FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Unsubscribe, userTel, true);
		try {
			AccountUtil.accountStop(userTel, Constant.TYPE_CALLER);
		}catch(EduException e) {
			loger.error("主叫退订异常...需要恢复主叫订购关系");
			try {
				FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Open, userTel, true);
				loger.debug("恢复主叫订购关系成功..");
			}catch(EduException ex) {
				loger.error("恢复主叫订购关系异常..");
			}
			throw new EduException(e.getCode());
		}
	}
	
	/**
	 * 双向退订
	 * @param userTel ：用户电话号码
	 * @throws EduException
	 */
	void cancelCallerAndCalled(String userTel) throws EduException{
		FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Unsubscribe, userTel, true);
		
		try {
			FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Unsubscribe, userTel, false);
		}catch(EduException e) {
			loger.error("主叫退订正常，被叫退订异常..需将主叫开通..保持一致..");
			try {
				FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Open, userTel, true);
				loger.debug("主叫开通成功....");
			}catch(EduException ex) {
				loger.error("主叫开通异常...");
			}
			throw new EduException(e.getCode());
		}
		
		try {
			AccountUtil.accountStop(userTel, Constant.TYPE_CALLER);
		}catch(EduException e) {
			loger.error("主叫录音退订失败...需回滚主叫，被叫订购..");
			rollbackCancel(userTel);
			throw new EduException(e.getCode());
		}
		
		try {
			AccountUtil.accountStop(userTel, Constant.TYPE_CALLED);
		}catch(EduException e) {
			loger.error("退订双向套餐：AccountUtil.accountStop.CALLED异常，需要回滚主叫和被叫退订关系及CALLER...");
			try {
				AccountUtil.accountOpen(userTel, Constant.TYPE_CALLER);
				loger.debug("回滚开通CALLER成功....");
			}catch(EduException ex) {
				loger.error("回滚开通CALLER异常....");
			}
			
			rollbackCancel(userTel);
			throw new EduException(e.getCode());
		}
	}
	
	/**
	 * 被叫开通
	 * @param userTel ：用户电话号码 
	 * @throws EduException
	 */
	void openCalled(String userTel) throws EduException{
		FptcsrvcInfoSyncUtil.userInfoSync(userTel);
		FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Open, userTel, false);
		try {
			AccountUtil.accountOpen(userTel, Constant.TYPE_CALLED);
			loger.debug("被叫开通成功...");
		}catch(EduException e) {
			loger.error("openCalled被叫开通异常...下面需要回滚订购关系");
			try {
				FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Unsubscribe, userTel, false);
				loger.error("openCaller回滚订购关系成功...");
			}catch(EduException ex) {
				loger.error("回滚订购关系时也异常");
			}
			throw new EduException(e.getCode());
		}
	}
	
	/**
	 * 被叫退订
	 * @param userTel ：用户电话号码
	 * @throws EduException
	 */
	void cancelCalled(String userTel) throws EduException {
		FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Unsubscribe, userTel, false);
		try {
			AccountUtil.accountStop(userTel, Constant.TYPE_CALLED);
		}catch(EduException e) {
			loger.error("被叫退订异常...需要恢复被叫订购关系");
			try {
				FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Open, userTel, false);
				loger.debug("恢复被叫订购关系成功..");
			}catch(EduException ex) {
				loger.error("恢复被叫订购关系异常..");
			}
			throw new EduException(e.getCode());
		}
	}
	
	/**
	 * 回滚主叫和被叫
	 * @param userTel
	 */
	void rollbackOpenCancel(String userTel) {
		try {
			FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Unsubscribe, userTel, true);
			loger.debug("主叫订购回滚成功...");
		}catch(EduException ex) {
			loger.error("主叫订购回滚失败...");
		}
		try {
			FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Unsubscribe, userTel, false);
			loger.debug("被叫订购回滚成功...");
		}catch(EduException ex) {
			loger.error("被叫订购回滚失败...");
		}
	}
	
	/**
	 * 退订时异常回滚
	 * @param userTel
	 */
	void rollbackCancel(String userTel) {
		try {
			FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Open, userTel, true);
			loger.debug( "主叫退订回滚成功[主叫开通]...");
		}catch(EduException ex) {
			loger.error("主叫退订回滚失败[主叫开通]...");
		}
		try {
			FptcsrvcInfoSyncUtil.srvcSubscribe(ActionTypeEnum.Open, userTel, false);
			loger.debug("被叫退订回滚成功[被叫开通]...");
		}catch(EduException ex) {
			loger.error("被叫退订回滚失败[被叫开通]...");
		}
	}

}
