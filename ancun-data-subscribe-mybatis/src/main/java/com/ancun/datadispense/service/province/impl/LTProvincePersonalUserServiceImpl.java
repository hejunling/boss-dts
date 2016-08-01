package com.ancun.datadispense.service.province.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.dx.*;
import com.ancun.common.persistence.model.dx.*;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.province.LTProvincePersonalUserService;
import com.ancun.datadispense.service.province.ProvincesBaseService;
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
 * @author chenb
 * @version 1.0
 * @Created on 2016/3/9
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "ltProvince")
public class LTProvincePersonalUserServiceImpl implements LTProvincePersonalUserService{
    private static final Logger logger = LoggerFactory.getLogger(LTProvincePersonalUserServiceImpl.class);


    @Resource
    private ProvincesBaseService provincesBaseService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserInfoHistoryMapper userInfoHistoryMapper;

    @Resource
    private UserFileMapper userFileMapper;

    @Resource
    private UserFileHistoryMapper userFileHistoryMapper;

    @Resource
    private UserVoiceInfoMapper userVoiceInfoMapper;

    @Resource
    private UserVoiceInfoHistoryMapper userVoiceInfoHistoryMapper;

    @Resource
    private DataSourceTransactionManager txManager;


    @Override
    public void openPersonalUser(BizUserInfo bizUserInfo) throws CustomException {

        String userNo = bizUserInfo.getUserNo();
        String rpCode = bizUserInfo.getRpcode();
        /**
         * 1、检查企业用户状态,如已注册企业账户切正常状态：抛出异常
         */
        EntUserInfo entUserInfo = provincesBaseService.selectEntUserInfo(null, userNo, rpCode);
        if (null != entUserInfo && entUserInfo.getUserstatus().equals(BizConstants.USERSTATUS_NORMAL)) {
            logger.debug("个人账号开通失败：账号已注册为企业账号，bizUserInfo={}", bizUserInfo);
            throw new CustomException("个人账号开通失败：账号已注册为企业账号");
        }

        /**2、检查个人用户状态
         *  2.1、为空则说明未注册,新增一条记录
         *  2.2、不为空这说明已注册:为正常状态抛出异常，非正常状态则重新开通
         */
        UserInfo userInfo = provincesBaseService.selectPersonalUserInfo(userNo);
        if (null != userInfo) {
            if (userInfo.getUserstatus().equals(BizConstants.USERSTATUS_NORMAL)) {
                logger.debug("个人账号开通失败：用户已注册，bizUserInfo={}", bizUserInfo);
                throw new CustomException("个人账号开通失败：用户已开通");
            } else {
                logger.debug("用户重新开通,bizUserInfo={}", bizUserInfo);
                reopenUserInfo(bizUserInfo);
            }
        } else {
            logger.debug("个人用户开通,bizUserInfo={}", bizUserInfo);
            insertPersonalUser(bizUserInfo);
        }
    }

    
    /**
     * 已退订退订用户重新开通
     *
     * @param bizUserInfo
     */
    private void reopenUserInfo(BizUserInfo bizUserInfo) {

        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("userno", bizUserInfo.getUserNo());

        UserInfo userInfo = new UserInfo();
        transformUserInfo(bizUserInfo, userInfo);
        userInfo.setUserno(null);
        userInfo.setCreatetime(null);

        userInfoMapper.updateByExampleSelective(userInfo, example);
    }


    /**
     * 个人用户对象转换
     *
     * @param bizUserInfo
     * @param userInfo
     */
    private void transformUserInfo(BizUserInfo bizUserInfo, UserInfo userInfo) {

        userInfo.setUserno(bizUserInfo.getUserNo());
        userInfo.setPassword(bizUserInfo.getPasswd());
        userInfo.setUsertype(bizUserInfo.getUserType());
        userInfo.setPhonetype(bizUserInfo.getPhonetype());
        userInfo.setPhone(bizUserInfo.getPhone());
        userInfo.setRpcode(bizUserInfo.getRpcode());
        userInfo.setCitycode(bizUserInfo.getCityCode());
        userInfo.setPhone(bizUserInfo.getPhone());
        userInfo.setCreatetime(new Date());
        userInfo.setUserstatus(bizUserInfo.getStatus());
        userInfo.setRectipflag(bizUserInfo.getRectip());
        userInfo.setDefaultpwdflag(bizUserInfo.getDefaultpwdflag());
        userInfo.setIsignupsource(bizUserInfo.getInsource());
        userInfo.setSignuptime(bizUserInfo.getIntime());
        userInfo.setTaocanid(bizUserInfo.getTcid());
        userInfo.setOpenflag(BizConstants.USERSTATUS_NORMAL);
        userInfo.setOpendatetime(bizUserInfo.getIntime());
        userInfo.setBusinesstype(bizUserInfo.getBusinesstype());
        userInfo.setRemark(bizUserInfo.getRemark());
        userInfo.setCallervoice(bizUserInfo.getCallerVoice());
        userInfo.setCalledvoice(bizUserInfo.getCalledVoice());
        userInfo.setCallerrecordvoice(bizUserInfo.getCallerRecord());
        userInfo.setCalledrecordvoice(bizUserInfo.getCalledRecord());
        userInfo.setSex(bizUserInfo.getSex());
        userInfo.setCertificatenum(bizUserInfo.getIdentify());
    }


    /**
     * 新增一条个人用户信息
     *
     * @param bizUserInfo 业务用户
     */
    private void insertPersonalUser(BizUserInfo bizUserInfo) {

        UserInfo userInfo = new UserInfo();

        transformUserInfo(bizUserInfo, userInfo);

        userInfoMapper.insertSelective(userInfo);
    }


    @Override
    public void UpdatePersonalUser(BizUserInfo bizUserInfo) throws CustomException {

        /**1、检查业务数据库个人用户状态
         *  1.1、为空则说明未注册，抛出异常
         *  1.2、不为空这说明已注册:为非正常状态重新开通，正常状态则后续操作
         */
        String userNo = bizUserInfo.getUserNo();
        String rpCode = bizUserInfo.getRpcode();

        UserInfo personalUser = provincesBaseService.selectPersonalUserInfo(userNo);
        if (null == personalUser) {
            logger.debug("个人账号退订失败：用户未注册,bizUserInfo={}", bizUserInfo);
            throw new CustomException("个人账号退订失败：用户未注册");
        } else {
            /**
             * 2.个人用户修改
             *  2.1、如果业务数据库用户状态为退订状态：
             *     ① BOSS系统用户为退订状态（退订操作）：抛出异常（已退订）
             *     ② BOSS系统用户为开通状态：重新开通
             */
            if (personalUser.getUserstatus().equals(BizConstants.USERSTATUS_CANCEL)) {
                if (bizUserInfo.getStatus().equals(BizConstants.USERSTATUS_CANCEL)) {
                    logger.debug("个人账号退订失败：业务系统中该个人账号已退订，bizUserInfo={}", bizUserInfo);
                    throw new CustomException("个人账号退订失败：业务系统中该个人账号已退订");
                } else {
                    logger.debug("个人账号重新开通：bizUserInfo={}", bizUserInfo);
                    reopenUserInfo(bizUserInfo);
                }
            } else {
                /**
                 * 2.2、修改个人用户信息
                 *  1.2.1、 BOSS用户状态为退订状态的视为退订操作：修改用户信息并迁移个人用户信息、录音文件、录音信息至历史表
                 *  1.2.2、 BOSS用户状态为非退订状态的视为更新操作：修改个人用户表
                 */
                if (bizUserInfo.getStatus().equals(BizConstants.USERSTATUS_CANCEL)) {

                    logger.debug("个人账号退订：bizUserInfo={}", bizUserInfo);
                    // 手动加入事物管理
                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    TransactionStatus status = txManager.getTransaction(def);
                    try {
                        //修改个人用户状态及退订时间
                        updateUserInfo(userNo, rpCode);
                        cancelPersonalInfos(personalUser);
                        txManager.commit(status);
                    } catch (Exception e) {
                        txManager.rollback(status);
                        e.printStackTrace();
                        throw new CustomException("事物处理异常!");
                    }

                } else {
                    logger.debug("个人账号修改：bizUserInfo={}", bizUserInfo);
                    updatePersonalUserInfo(bizUserInfo);
                }
            }
        }
    }

    /**
     * 退订并迁移历史数据
     *
     * @param userInfo
     */

    protected void cancelPersonalInfos(UserInfo userInfo) throws CustomException {


        //迁移个人用户信息、录音文件、录音信息至历史表
        remove2UserInfoHistory(userInfo);

        removceUserFiles2History(userInfo.getUserno(), userInfo.getRpcode());

        removceUserVoices2History(userInfo.getUserno(), userInfo.getRpcode());


    }


    /**
     * 用户录音信息迁移至历史并删除
     *
     * @param userNo
     * @param rpCode
     * @throws Exception
     */
    private void removceUserVoices2History(String userNo, String rpCode) throws CustomException {

        UserVoiceInfo userVoiceInfo = new UserVoiceInfo();
        userVoiceInfo.setIuserno(userNo);
        userVoiceInfo.setRpcode(rpCode);

        List<UserVoiceInfo> voiceList = userVoiceInfoMapper.select(userVoiceInfo);

        if (null == voiceList || voiceList.size() <= 0) {
            return;
        }
        logger.debug("用户录音信息迁移至历史并删除,userNo={},rpcode={},录音文件个数={}", userNo, rpCode, voiceList.size());

        for (UserVoiceInfo voice : voiceList) {
            UserVoiceInfoHistory history = new UserVoiceInfoHistory();

            try {
                PropertyUtils.copyProperties(history, voice);
            } catch (Exception e) {
                throw new CustomException("对象复制异常");
            }

            userVoiceInfoHistoryMapper.insertSelective(history);

            userVoiceInfoMapper.delete(voice);
        }

    }

    /**
     * 用户信息迁移至历史表
     *
     * @param userInfo
     * @throws Exception
     */
    private void remove2UserInfoHistory(UserInfo userInfo) throws CustomException {

        UserInfoHistory history = new UserInfoHistory();

        try {
            PropertyUtils.copyProperties(history, userInfo);
        } catch (Exception e) {
            throw new CustomException("对象复制异常");
        }
        history.setCpid(null);
        history.setCanceldatetime(null == userInfo.getCanceldatetime() ? new Date() : userInfo.getCanceldatetime());

        userInfoHistoryMapper.insertSelective(history);
    }

    /**
     * 用户录音文件信息迁移至历史表
     *
     * @param userNo
     * @param rpCode
     * @throws Exception
     */
    private void removceUserFiles2History(String userNo, String rpCode) throws CustomException {

        UserFile userFile = new UserFile();
        userFile.setPfNotaryUserNo(userNo);
        userFile.setRpcode(rpCode);

        List<UserFile> fileList = userFileMapper.select(userFile);

        if (null == fileList || fileList.size() <= 0) {
            return;
        }
        logger.debug("用户录音文件信息迁移至历史表,userNo={},rpcode={},录音文件个数={}", userNo, rpCode, fileList.size());

        for (UserFile file : fileList) {
            UserFileHistory history = new UserFileHistory();

            try {
                PropertyUtils.copyProperties(history, file);
            } catch (Exception e) {
                throw new CustomException("对象复制异常");
            }


            userFileHistoryMapper.insertSelective(history);
            userFileMapper.delete(file);
        }
    }

    /**
     * 修改用户状态
     *
     * @param userNo 个人账号
     * @param rpCode 省份
     * @throws Exception
     */
    protected void updateUserInfo(String userNo, String rpCode) {

        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("userno", userNo).andEqualTo("rpcode", rpCode);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserstatus(BizConstants.USERSTATUS_CANCEL);
        userInfo.setCanceldatetime(new Date());

        userInfoMapper.updateByExampleSelective(userInfo, example);
    }


    /**
     * 修改个人用户
     *
     * @param bizUserInfo
     */
    private void updatePersonalUserInfo(BizUserInfo bizUserInfo) {

        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("userno", bizUserInfo.getUserNo()).
                andEqualTo("rpcode", bizUserInfo.getRpcode());

        UserInfo userInfo = new UserInfo();

        transformUserInfo(bizUserInfo, userInfo);
        userInfo.setUserno(null);
        userInfo.setCreatetime(null);

        userInfoMapper.updateByExampleSelective(userInfo, example);
    }
}
