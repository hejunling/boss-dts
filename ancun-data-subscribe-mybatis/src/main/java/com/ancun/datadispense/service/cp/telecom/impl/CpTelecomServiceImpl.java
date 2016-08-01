package com.ancun.datadispense.service.cp.telecom.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.cp.telecom.*;
import com.ancun.common.persistence.model.cp.telecom.*;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.cp.telecom.ICpTelecomService;
import com.ancun.datadispense.util.CustomException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * CP电信开通退订
 *
 * @Created on 2016年4月11日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class CpTelecomServiceImpl implements ICpTelecomService {

    private final static Logger log = LoggerFactory.getLogger(CpTelecomServiceImpl.class);
	
	@Resource
	private TelUserInfoMapper telUserInfoMapper;
	
	@Resource
	private TelUserInfoHistoryMapper telUserInfoHistoryMapper;
	
	@Resource
	private TelUserFileMapper telUserFileMapper;
	
	@Resource
	private TelUserFileHistoryMapper telUserFileHistoryMapper;
	
	@Resource
	private TelUserVoiceInfoMapper telUserVoiceInfoMapper;
	
	@Resource
	private TelUserVoiceInfoHistoryMapper telUserVoiceInfoHistoryMapper;

    @Resource
    private DataSourceTransactionManager txManager;

	@Override
	@TargetDataSource(name = "cpTelecom")
	public void openUser(BizUserInfo bizUserInfo) throws CustomException {
		//不为个人用户
		if(!bizUserInfo.getUserType().equals(BizConstants.USER_TYPE_PERSONAL))
			throw new CustomException("CPTel开通：用户类型不正确");
		TelUserInfo bean = Frombiz2User(bizUserInfo);
		// 根据电话号码取得用户
		Example example = new Example(TelUserInfo.class);
		example.createCriteria().andEqualTo("userno",bizUserInfo.getUserNo());
		List<TelUserInfo> list = telUserInfoMapper.selectByExample(example);
		//多条或没数据
		if(null != list && list.size() > 1)
			throw new CustomException("CPTel修改：用户条数不正确");
		if(null != list && list.size() == 1){
			TelUserInfo info = list.get(0);
			// 用户是正常状态
			if(info.getUserstatus().equals(BizConstants.USERSTATUS_NORMAL))
				throw new CustomException("CPTel开通：用户已经开通");
			// 用户存在，且用户不正常
			else{
				bean.setCanceldatetime(null);
				reOpen(bean);
			}
		}
		//开通
		telUserInfoMapper.insertSelective(bean);
	}

	@Override
	@TargetDataSource(name = "cpTelecom")
	public void updateUser(BizUserInfo bizUserInfo) throws CustomException {
		//不为个人用户
		if(!bizUserInfo.getUserType().equals(BizConstants.USER_TYPE_PERSONAL))
			throw new CustomException("CPTel修改：用户类型不正确");
		TelUserInfo bean = Frombiz2User(bizUserInfo);
		// 根据电话号码取得用户
		Example example = new Example(TelUserInfo.class);
		example.createCriteria().andEqualTo("userno",bizUserInfo.getUserNo());
		List<TelUserInfo> list = telUserInfoMapper.selectByExample(example);
		//多条或没数据
		if(null == list || (null != list && list.size() > 1) || (null != list && list.size() == 0))
			throw new CustomException("CPTel修改：用户条数不正确");
		
		TelUserInfo info = list.get(0);
		if(!info.getUserstatus().equals(BizConstants.USERSTATUS_NORMAL)){
			if (!bizUserInfo.getStatus().equals(BizConstants.USERSTATUS_NORMAL)){
				throw new CustomException("该用户已退订");
			}else{
				reOpen(bean);
				bean.setUserstatus(BizConstants.USERSTATUS_NORMAL);
    			int reOpenNum = telUserInfoMapper.insertSelective(bean);
    			log.info("CPTelecom用户修改,重新开通了{}条数据", reOpenNum);
			}
		}else{
			if (!bizUserInfo.getStatus().equals(BizConstants.USERSTATUS_NORMAL)){
				cancelUserInfo(bean.getUserno());
			}else{
				bean.setUserno(null);
				int updateNum = telUserInfoMapper.updateByExampleSelective(bean, example);
            	log.info("CPTelecom用户修改,更新了{}条数据", updateNum);
			}
		}
	}
	
	/**
	 * 重新开通用户
	 * 
	 * @param bean
	 * @throws Exception
	 */
	void reOpen(TelUserInfo bean) throws CustomException{
		remove2UserInfoHistory(bean);
	}
	
	/**
     * 用户信息迁移至历史表
     *
     * @param userNo 个人账号
     * @throws Exception
     */
    private void remove2UserInfoHistory(TelUserInfo bean) throws CustomException {
		TelUserInfoHistory userinfo = new TelUserInfoHistory();
		try{
			PropertyUtils.copyProperties(userinfo, bean);
	    } catch (Exception e) {
	        throw new CustomException("Bean转换异常");
	    }
		if(telUserInfoHistoryMapper.insert(userinfo) !=1)
			log.info("CPTelecom用户信息迁移至历史表UserInfoHistory,迁移失败userNo={}", bean.getUserno());
        deleteUserinfo(bean.getUserno());
    }
    
    /**
     * 删除USER_INFO信息
     *
     * @param userNo
     * @param rpCode
     */
    protected void deleteUserinfo(String userNo) {
    	TelUserInfo record = new TelUserInfo();
    	record.setUserno(userNo);
    	telUserInfoMapper.delete(record);
    	log.info("CPTelecom删除删除USER_INFO信息,userNo={}", userNo);
    	
    }
    
    /**
     * 退订用户
     */
    void cancelUserInfo(String userno) throws CustomException {
    	// 手动加入事物管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        try{
	    	updateUserInfo(userno);
	
	    	removceUserFiles2History(userno);
	    	
	    	deleteUserFiles(userno);
	    	
	    	removceUserVoices2History(userno);
	    	
	    	deleteUserVoices(userno);
	    	
	    	txManager.commit(status);
        }catch(Exception e){
        	txManager.rollback(status);
            throw new CustomException("CpTelecom退订用户异常，已回滚！");
    	}
    }
    
    /**
     * 更新用户
     * @param userno
     */
    void updateUserInfo(String userno){
    	Example example = new Example(TelUserInfo.class);
    	TelUserInfo record = new TelUserInfo();
    	record.setUserstatus(BizConstants.USERSTATUS_CANCEL);
    	record.setCanceldatetime(new Date());
    	example.createCriteria().andEqualTo("userno", userno);
        telUserInfoMapper.updateByExampleSelective(record, example);
        log.info("CPTelecom修改用户状态,userNo={}", userno);
    }
    
    /**
     * 文件移到历史表
     * 
     * @param userno
     */
    void removceUserFiles2History(String userno) throws CustomException {
    	TelUserFile record = new TelUserFile();
    	record.setPfNotaryUserNo(userno);
    	List<TelUserFile> list = telUserFileMapper.select(record);
    	for (TelUserFile userFile : list) {
    		TelUserFileHistory bean = new TelUserFileHistory();
    		try{
	    		PropertyUtils.copyProperties(bean, userFile);
	    	} catch (Exception e) {
	            throw new CustomException("Bean转换异常");
	        }
    		if(telUserFileHistoryMapper.insert(bean) !=1)
    			log.info("CPTelecom用户录音文件信息迁移至历史表,迁移失败userno={}", userno);
        }
    }
    
    /**
     * 删除文件
     * @param userno
     */
    void deleteUserFiles(String userno){
    	Example example = new Example(TelUserFile.class);
        example.createCriteria().andEqualTo("pfNotaryUserNo", userno);
        telUserFileMapper.deleteByExample(example);
        log.info("CPTelecom删除个人用户文件信息,userno={}", userno);
    }
    
    /**
     * 录音移到历史表
     * @param userno
     */
    void removceUserVoices2History(String userno) throws CustomException {
    	TelUserVoiceInfo record = new TelUserVoiceInfo();
    	record.setIuserno(userno);
    	List<TelUserVoiceInfo> list = telUserVoiceInfoMapper.select(record);
    	for (TelUserVoiceInfo userVoiceInfo : list) {
    		TelUserVoiceInfoHistory bean = new TelUserVoiceInfoHistory();
    		try{
	    		PropertyUtils.copyProperties(bean, userVoiceInfo);
	    	} catch (Exception e) {
	            throw new CustomException("Bean转换异常");
	        }
    		if(telUserVoiceInfoHistoryMapper.insert(bean) !=1)
    			log.info("CPTelecom用户录音信息迁移至历史史表,迁移失败Recordno={}", bean.getRecordno());
        }
    }
    
    /**
     * 删除录音
     * @param userno
     */
    void deleteUserVoices(String userno){
    	Example example = new Example(TelUserVoiceInfo.class);
        example.createCriteria().andEqualTo("iuserno", userno);
        telUserVoiceInfoMapper.deleteByExample(example);
        log.info("CPTelecom删除个人用户录音信息,userNo={}", userno);
    }
	
	/**
	 * Bean转换
	 * @param bizUserInfo
	 * @return
	 */
	TelUserInfo Frombiz2User(BizUserInfo bizUserInfo){
		TelUserInfo bean = new TelUserInfo();
		Date time = new Date();
		bean.setUserno(bizUserInfo.getUserNo());
//		bean.setAllowmaxstore(bizUserInfo.geta);
//		bean.setAreacode(bizUserInfo.geta);
		bean.setBusinesstype(bizUserInfo.getBusinesstype());
//		bean.setCanceldatetime(bizUserInfo.getc);
		bean.setCitycode(bizUserInfo.getCityCode());
		bean.setCreatetime(time);
		bean.setDefaultpwdflag(bizUserInfo.getDefaultpwdflag());
		bean.setIsignupsource(String.valueOf(BizConstants.BLACKGROUD_OPEN));
//		bean.setLoginip(bizUserInfo.get);
//		bean.setLoginsource();
//		bean.setLogintime();
		bean.setOpendatetime(time);
		bean.setOpenflag(BizConstants.USERSTATUS_NORMAL);
//		bean.setOpenmonmum(BizConstants.CP_TELECOM_OPENMONMUM);
		bean.setPassword(bizUserInfo.getPasswd());
		bean.setPhone(bizUserInfo.getPhone());
		bean.setPhonetype(bizUserInfo.getPhonetype());
		bean.setRectipflag(bizUserInfo.getCallerVoice());
		bean.setRpcode(bizUserInfo.getRpcode());
//		bean.setSignupip(bizUserInfo.gets);
		bean.setSignuptime(time);
		bean.setTaocanname(BizConstants.CP_TELECOM_TAOCAN_NAME);
		bean.setUsedingstore(0D);
		bean.setUserstatus(bizUserInfo.getStatus());
		bean.setUsertype(bizUserInfo.getUserType());
		return bean;
	}

}
