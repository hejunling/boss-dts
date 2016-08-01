package com.ancun.datadispense.service.sh.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.sh.BaseService4SH;
import com.ancun.datadispense.service.sh.IPersonalUserService4SH;
import com.ancun.datadispense.util.CustomException;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 上海个人用户
 *
 * @Created on 2016年3月21日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class PersonalUserService4SHImpl extends BaseService4SH implements IPersonalUserService4SH {

    private static final Logger logger = LoggerFactory.getLogger(PersonalUserService4SHImpl.class);

    @Override
    @TargetDataSource(name = "shV2")
    public void openPersonalUser(BizUserInfo bizUserInfo) throws CustomException {

    	//个人账户，如果企业编号为Null,则设置为UserTel
    	bizUserInfo.setEntNo(StringUtil.isEmpty(bizUserInfo.getEntNo())?bizUserInfo.getUserNo():bizUserInfo.getEntNo());
    	//Boss表中AccountType可为空，如果为空，个人设置为1
    	bizUserInfo.setAccountType(bizUserInfo.getAccountType()==null?BizConstants.ACCOUNTTYPE_PERSONAL:bizUserInfo.getAccountType());
        String userNo = bizUserInfo.getUserNo();
        String rpCode = bizUserInfo.getRpcode();

        /**2、检查个人用户状态
         *  2.1、为空则说明未注册,新增一条记录
         *  2.2、不为空这说明已注册:为非正常状态抛出异常，非正常状态则重新开通
         */
        String personalUserStatus = selectUserStatus(userNo, userNo, false);
        if (StringUtil.isNotEmpty(personalUserStatus)) {
            if (personalUserStatus.equals(BizConstants.USERSTATUS_NORMAL)) {
                logger.info("账号{},省份{}，个人账号开通失败：用户已注册", userNo, rpCode);
                throw new CustomException("个人账号开通失败：用户已注册");
            } else {
            	reOpenAllUserInfos(userNo,userNo);
            }
        } 
        insertUserinfo(bizUserInfo);
        insertAccountUser(bizUserInfo);
    }

    @Override
    @TargetDataSource(name = "shV2")
    public void UpdatePersonalUser(BizUserInfo bizUserInfo) throws CustomException {

        /**1、检查业务数据库个人用户状态
         *  1.1、为空则说明未注册，抛出异常
         *  1.2、不为空这说明已注册:为非正常状态重新开通，正常状态则后续操作
         */
        String userNo = bizUserInfo.getUserNo();
        String rpCode = bizUserInfo.getRpcode();

        String personalUserStatus = selectUserStatus(userNo, userNo, false);
        if (StringUtil.isEmpty(personalUserStatus)) {
            logger.info("账号={},省份={},业务数据库用户状态={},个人账号退订失败：用户未注册", userNo, rpCode, personalUserStatus);
            throw new CustomException("个人账号退订失败：用户未注册");
        } else {

            /**
             * 1.2、修改个人用户信息
             *  1.2.1、 BOSS用户状态为非正常状态的视为退订操作：修改用户信息并迁移个人用户信息、录音文件、录音信息至历史表
             *  1.2.2、 BOSS用户状态为正常状态的视为更新操作：修改个人用户表
             */
        	
        	if(!personalUserStatus.equals(BizConstants.USERSTATUS_NORMAL)){
        		if (!bizUserInfo.getStatus().equals(BizConstants.USERSTATUS_NORMAL)){
    				throw new CustomException("该用户已退订");
    			}else{
    				reOpenAllUserInfos(userNo,userNo);
                	bizUserInfo.setStatus(BizConstants.USERSTATUS_NORMAL);
                	insertUserinfo(bizUserInfo);
                	insertAccountUser(bizUserInfo);
    			}
        	}else{
        		if (!bizUserInfo.getStatus().equals(BizConstants.USERSTATUS_NORMAL)){
        			cancelUserInfos(userNo,userNo,false);
        		}else{
        			updateUserInfo(bizUserInfo);
                	updateAccountInfo(bizUserInfo);
        		}
        	}
        	
        }
    }
}
