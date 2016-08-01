package com.ancun.boss.business.service.bizuser.impl;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.persistence.mapper.BizEntInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizPersonInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoQueryMapper;
import com.ancun.boss.business.persistence.module.*;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoInput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoUpdateInput;
import com.ancun.boss.business.service.BizBasicService;
import com.ancun.boss.business.service.allprovince.IProvinceService;
import com.ancun.boss.business.service.allprovince.IPublicProvinceService;
import com.ancun.boss.business.service.bizuser.IBizUserInfoUpdateService;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.core.exception.EduException;
import com.ancun.utils.DESUtils;
import com.ancun.utils.DateUtils;
import com.ancun.utils.MD5Utils;
import com.ancun.utils.StringUtil;
import com.google.common.collect.HashBasedTable;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

public class BizUserInfoUpdateServiceImpl extends BizBasicService implements IBizUserInfoUpdateService {

    @Resource
    private BizUserInfoMapper bizUserInfoMapper;

    @Resource
    private BizPersonInfoMapper bizPersonInfoMapper;

    @Resource
    private BizEntInfoMapper bizEntInfoMapper;

    @Resource
    private DataSourceTransactionManager txManager;

    @Resource
    private BizUserInfoQueryMapper bizUserInfoQueryMapper;

    @Resource
    private IPublicProvinceService publicProvinceService;


    @Override
    public void updateBizUserInfo(BizUserInfoUpdateInput infoUpDateInput) throws EduException {
        String userNo = infoUpDateInput.getUserNum();
        String entNo = infoUpDateInput.getEntNo();
        String bizNo = infoUpDateInput.getBizNo();

        if (!Constant.USER_STATUS_1.equals(infoUpDateInput.getStatus())) {
            throw new EduException(MessageConstant.BIZ_USER_STATE_ERROR);
        }

        /**
         * 1.校验用户
         */
        BizUserInfo record = super.selectBizUserInfoNomal(userNo, bizNo, entNo);
        if (null == record) {
            throw new EduException(MessageConstant.BIZ_USER_NOT_EXISTS);
        }
        /**
         * 2.设置BizUserInfo
         */
        BizUserInfo bizUserInfo = new BizUserInfo();
        setBizUserInfo(infoUpDateInput, bizUserInfo);


        /**
         * 3.关联表操作：（请求内容中包含个人或企业详情）
         * 3.1、不包含：直接更新biz_user_info
         * 3.2、包含：设置uniqueno，fullpackage为 'false';
         *     3.2.1、先查询是否存在个人或企业详情；存在则更新，不存在直接插入
         */
        String userType = infoUpDateInput.getUserType();

        String uniqueno = UUID.randomUUID().toString();

        //个人用户信息
        if (null != infoUpDateInput.getBizPersonInfo() && Constant.USER_TYPE_PER.equals(userType)) {

            bizUserInfo.setFullpackage(Constant.FULL_PACKAGE_FALSE);
            bizUserInfo.setUniqueno(uniqueno);

            BizPersonInfo personInfo = selectBizPerson(userNo);
            if (null != personInfo) {
                updateBizPersonInfo(userNo, uniqueno, infoUpDateInput.getBizPersonInfo());
            } else {
                insertBizPeronInfo(userNo, uniqueno, infoUpDateInput.getBizPersonInfo());
            }
        }
        //企业用户信息
        if (null != infoUpDateInput.getBizEntInfo() && Constant.USER_TYPE_ENT.equals(userType)) {
            bizUserInfo.setFullpackage(Constant.FULL_PACKAGE_FALSE);
            bizUserInfo.setUniqueno(uniqueno);

            if (StringUtil.isEmpty(entNo)) {
                throw new EduException(MessageConstant.USER_ENT_NO_IS_NULL);
            }

            BizEntInfo entInfo = selectBizEntInfo(entNo, bizNo);

            if (null != entInfo) {
                updateBizEntInfo(entNo, uniqueno, bizNo, infoUpDateInput.getBizEntInfo());
            } else {
                insertBizEntInfo(entNo, uniqueno, bizNo, infoUpDateInput.getBizEntInfo());
            }
        }


        /**
         * 4.更新业务用户表
         */
        BizUserInfoExample example = new BizUserInfoExample();
        example.createCriteria().andUserNoEqualTo(userNo).andBizNoEqualTo(bizNo);

        //涉及 分表分库 手动控制事物
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = txManager.getTransaction(def);
        try {
            bizUserInfoMapper.updateByExampleSelective(bizUserInfo, example);

            txManager.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
            txManager.rollback(transactionStatus);
        }


        /**
         * 5。如果原tcid 与当前tcid不等
         * 调用套餐变更调用远程接口
         */
        Long oldTcId = record.getTcid();
        Long newTcId = infoUpDateInput.getTcId();
        if (oldTcId != newTcId) {
            HashBasedTable<String, String, IProvinceService> table = publicProvinceService.getProvinceService();
            IProvinceService ftpService = table.get(record.getRpcode(), infoUpDateInput.getUserType());
            if (null != ftpService) {
                ftpService.postChangeRequest(userNo, userType, oldTcId.toString(), newTcId.toString(), false);
            }
        }


        /**
         * 6.插入业务用户查询表
         */
        bizUserInfo.setUserNo(userNo);
        bizUserInfo.setBizNo(bizNo);
        insertBizUserInfoQuery(bizUserInfo);

    }


    /**
     * 设置BizUserInfo
     *
     * @param infoUpDateInput
     * @param bizUserInfo
     */
    private void setBizUserInfo(BizUserInfoUpdateInput infoUpDateInput, BizUserInfo bizUserInfo) {
        bizUserInfo.setTcid(infoUpDateInput.getTcId());
        bizUserInfo.setPhone(infoUpDateInput.getPhone());
        bizUserInfo.setInsource(infoUpDateInput.getInsource());
        bizUserInfo.setRemark(infoUpDateInput.getRemark());
        bizUserInfo.setSmsLogin(infoUpDateInput.getSmsLogin());
        bizUserInfo.setEmail(infoUpDateInput.getEmail());
        bizUserInfo.setCallerVoice(infoUpDateInput.getCallerVoice());
        bizUserInfo.setCalledVoice(infoUpDateInput.getCalledVoice());
        bizUserInfo.setFax(infoUpDateInput.getFax());
        bizUserInfo.setRectip(infoUpDateInput.getRectip());
        bizUserInfo.setStatus(infoUpDateInput.getStatus());
        bizUserInfo.setFullpackage(Constant.FULL_PACKAGE_TRUE);

        String tcType = infoUpDateInput.getTcType().trim();

        if (Constant.TC_TYPE_CALLER.equals(tcType)) {
            // 主叫录音
            bizUserInfo.setCallerflag(Constant.MARK_ON);
            bizUserInfo.setCalledflag(Constant.MARK_OFF);
            bizUserInfo.setCallerRecord(Constant.MARK_ON);// 主叫录音默认开启
            bizUserInfo.setCalledRecord(Constant.MARK_OFF);// 被叫录音默认关闭
        } else if (Constant.TC_TYPE_BIDIRECTION.equals(tcType)) {
            // 双向录音
            bizUserInfo.setCallerflag(Constant.MARK_ON);
            bizUserInfo.setCalledflag(Constant.MARK_ON);
            bizUserInfo.setCallerRecord(Constant.MARK_ON);// 主叫录音默认开启
            bizUserInfo.setCalledRecord(Constant.MARK_ON);// 被叫录音默认开启
        } else {
            bizUserInfo.setCallerflag(Constant.MARK_ON);
        }
    }

    /**
     * 插入用户查询
     *
     * @param bizUserInfo
     */
    private void insertBizUserInfoQuery(BizUserInfo bizUserInfo) {
        BizUserInfoQuery query = new BizUserInfoQuery();
        query.setUserNo(bizUserInfo.getUserNo());
        query.setUpdateTime(DateUtils.getCurrentDate());
        query.setBizNo(bizUserInfo.getBizNo());
        query.setStatus(bizUserInfo.getStatus());
        query.setCallerVoice(bizUserInfo.getCallerVoice());
        query.setCalledVoice(bizUserInfo.getCalledVoice());
        query.setCallerRecord(bizUserInfo.getCallerRecord());
        query.setCalledRecord(bizUserInfo.getCalledRecord());
        bizUserInfoQueryMapper.insertSelective(query);
    }

    @Override
    public void bizUserCleanPwd(BizUserInfoInput bizUserInfoInput) throws EduException {
        String userNo = bizUserInfoInput.getUserNum();
        String bizNo = bizUserInfoInput.getBizNo();

        /**
         * 用户检查
         */
        super.validateBizUserInfo(userNo, bizNo, null);

        BizUserInfoExample example = new BizUserInfoExample();
        example.createCriteria().andBizNoEqualTo(bizNo).andUserNoEqualTo(userNo);

        BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setPasswd(DESUtils.encrypt(
                MD5Utils.md5(BizRequestConstant.INIT_PWD), null));

        bizUserInfoMapper.updateByExampleSelective(bizUserInfo, example);
    }

    /**
     * 新增企业用户信息
     *
     * @param entNo
     * @param uniqueno
     * @param bizNo
     * @param bizEntInfo
     */
    private void insertBizEntInfo(String entNo, String uniqueno, String bizNo, BizEntInfo bizEntInfo) {
        bizEntInfo.setEntNo(entNo);
        bizEntInfo.setUniqueno(uniqueno);
        bizEntInfo.setFullpackage(Constant.FULL_PACKAGE_FALSE);
        bizEntInfo.setBizNo(bizNo);

        bizEntInfoMapper.insertSelective(bizEntInfo);
    }


    /**
     * 修改企业用户信息
     *
     * @param entNo
     * @param uniqueno
     * @param bizNo
     * @param bizEntInfo
     */
    private void updateBizEntInfo(String entNo, String uniqueno, String bizNo, BizEntInfo bizEntInfo) {
        BizEntInfoExample e = new BizEntInfoExample();
        e.createCriteria().andBizNoEqualTo(bizNo).andEntNoEqualTo(entNo);

        bizEntInfo.setUniqueno(uniqueno);
        bizEntInfo.setFullpackage(Constant.FULL_PACKAGE_FALSE);

        bizEntInfoMapper.updateByExampleSelective(bizEntInfo, e);
    }

    /**
     * 查询企业用户详情
     * 带上bizNo 区分汇工作
     *
     * @param entNo
     * @param bizNo
     * @return
     */
    private BizEntInfo selectBizEntInfo(String entNo, String bizNo) {
        BizEntInfoExample example = new BizEntInfoExample();
        example.createCriteria().andEntNoEqualTo(entNo).andBizNoEqualTo(bizNo);

        List<BizEntInfo> list = bizEntInfoMapper.selectByExample(example);
        if (null == list || list.size() <= 0) {
            return null;
        }

        if (list.size() != 1) {
            throw new EduException(MessageConstant.BIZ_ENT_USER_IS_NOT_ONLY);
        }
        return list.get(0);
    }

    /**
     * 新增 biz_person_info
     *
     * @param userNo
     * @param uniqueno
     * @param bizPersonInfo
     */
    private void insertBizPeronInfo(String userNo, String uniqueno, BizPersonInfo bizPersonInfo) {
        bizPersonInfo.setUserNo(userNo);
        bizPersonInfo.setUniqueno(uniqueno);
        bizPersonInfo.setFullpackage(Constant.FULL_PACKAGE_FALSE);

        bizPersonInfoMapper.insertSelective(bizPersonInfo);
    }

    /**
     * 修改Biz_person_info
     *
     * @param userNo
     * @param uniqueno
     * @param bizPersonInfo
     */
    private void updateBizPersonInfo(String userNo, String uniqueno, BizPersonInfo bizPersonInfo) {
        BizPersonInfoExample example = new BizPersonInfoExample();
        example.createCriteria().andUserNoEqualTo(userNo);

        bizPersonInfo.setUniqueno(uniqueno);
        bizPersonInfo.setFullpackage(Constant.FULL_PACKAGE_FALSE);

        bizPersonInfoMapper.updateByExampleSelective(bizPersonInfo, example);
    }


    /**
     * 根据userno查询个人用户详情
     *
     * @param bizuserno
     * @return
     */
    private BizPersonInfo selectBizPerson(String bizuserno) {
        BizPersonInfoExample example = new BizPersonInfoExample();
        example.createCriteria().andUserNoEqualTo(bizuserno);

        List<BizPersonInfo> list = bizPersonInfoMapper.selectByExample(example);
        if (null == list || list.size() <= 0) {
            return null;
        }
        if (list.size() != 1) {
            throw new EduException(MessageConstant.BIZ_PERSON_USER_IS_NOT_ONLY);
        }
        return list.get(0);
    }

}
