package com.ancun.datadispense.service.cp.unicom.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.cp.unicom.*;
import com.ancun.common.persistence.model.cp.unicom.*;
import com.ancun.common.persistence.model.dx.UserFile;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.bizConstants.UniSignupType;
import com.ancun.datadispense.service.cp.unicom.UnicomUserService;
import com.ancun.datadispense.util.CustomException;
import com.ancun.utils.DESUtils;
import com.ancun.utils.MD5Utils;
import com.ancun.utils.PhoneCallCheck;
import com.ancun.utils.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenb
 * @version 1.0
 * @Created on 2016/4/14
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@Service
@TargetDataSource(name = "cpUnicom")
public class UnicomUserServiceImpl implements UnicomUserService {

    private static final Logger logger = LoggerFactory.getLogger(UnicomUserServiceImpl.class);

    @Resource
    UniUserTaocanMapper taocanMapper;

    @Resource
    UniUserInfoMapper userMapper;

    @Resource
    UniUserVoiceInfoMapper uniUserVoiceMapper;

    @Resource
    UniUserFileMapper uniUserFileMapper;

    @Resource
    UniUserFileHistoryMapper uniUserFileHistoryMapper;

    @Resource
    UniUserVoiceInfoHistoryMapper uniUserVoiceInfoHistoryMapper;

    @Resource
    UniUserInfoHistoryMapper uniUserInfoHistoryMapper;

    @Resource
    private DataSourceTransactionManager txManager;

    /**
     * 运行环境取得
     */
    @Value("${spring.profiles.active:test}")
    private String profile;

    /**
     * 开通类型与套餐匹配表
     */
    private static Map<String, UniSignupType> defaultTcidWithOpenType = new HashMap<String, UniSignupType>();

    static {
        defaultTcidWithOpenType.put("3", UniSignupType.SMS);
        defaultTcidWithOpenType.put("4", UniSignupType.MMS);
    }

    /**
     * 用户新增
     *
     * @param input
     * @throws Exception
     */
    @Override
    public void openUser(BizUserInfo input) throws CustomException {

        // 取得开通类型枚举
//		UniOpenType openType = UniOpenType.getOpenType(Integer.valueOf(input.getInsource()));
        // 用户数据校验
        validUserInfo(input);

        // 取得套餐信息
        UniUserTaocan taocan = taocanMapper.selectByPrimaryKey(input.getTcid());
        if (taocan == null) {
            logger.info("账号{}，省份{}，业务编号{}，账号开通失败：业务数据库不存在该套餐", input.getUserNo(), input.getRpcode(), input.getBizNo());
            throw new CustomException("账号开通失败：业务数据库不存在该套餐：" + input.getTcid().toString());
        }

        List<UniUserInfo> users = getUserList(input.getUserNo());

        if (users.size() == 1) {

            UniUserInfo user = users.get(0);
            if (user.getUserstatus().equals(BizConstants.USERSTATUS_NORMAL)) {
                logger.info("账号{}，省份{}，业务编号{}，账号开通失败：用户已开通", input.getUserNo(), input.getRpcode(), input.getBizNo());
                throw new CustomException("账号开通失败：用户已开通");
            } else {
                logger.info("账号{}，省份{}，业务编号{}，用户重新开通", input.getUserNo(), input.getRpcode(), input.getBizNo());
                // 更新用户
                updateUserInfo(input, taocan, user);
            }
        } else if (users.size() == 0) {
            logger.info("账号{}，省份{}，业务编号{}，用户开通", input.getUserNo(), input.getRpcode(), input.getBizNo());
            // 新增用户
            inserUserInfo(input, taocan);
        } else {
            logger.info("账号{}，省份{}，业务编号{}，账号开通失败：数据库内数据异常", input.getUserNo(), input.getRpcode(), input.getBizNo());
            throw new CustomException("账号开通失败：数据库内数据异常");
        }

    }

    /**
     * 新增一条个人用户信息
     *
     * @param input  业务用户
     * @param taocan
     * @throws Exception
     */
    private void inserUserInfo(BizUserInfo input, UniUserTaocan taocan) throws CustomException {

        try {
            // 插入用户表
            UniUserInfo info = prepareUserForInsert(input);
            info = prepareCommonForUserInfo(info, input);

            info.setUserno(input.getUserNo());
            info.setTcid(taocan.getTcid());
            info.setTaocanname(taocan.getTaocanName());

            userMapper.insertSelective(info);
        } catch (CustomException e) {
            logger.info("账号{}，省份{}，业务编号{}，账号开通失败：插入数据时发生异常", input.getUserNo(), input.getRpcode(), input.getBizNo());

            throw new CustomException("账号开通失败：插入数据时发生异常");
        }


    }


    /**
     * 已退订退订用户重新开通
     *
     * @param input  业务用户
     * @param taocan
     * @param info
     * @throws Exception
     */
    private void updateUserInfo(BizUserInfo input, UniUserTaocan taocan, UniUserInfo info) throws CustomException {

        try {

            info = prepareCommonForUserInfo(info, input);

            info.setUserno(null);
            info.setTcid(taocan.getTcid());
            info.setTaocanname(taocan.getTaocanName());
            info.setCanceldatetime(null);
            info.setUserstatus(input.getStatus());

            userMapper.updateByPrimaryKeySelective(info);
        } catch (CustomException e) {
            logger.info("账号{}，省份{}，业务编号{}，账号开通失败：更新数据时发生异常", input.getUserNo(), input.getRpcode(), input.getBizNo());

            throw new CustomException("账号开通失败：更新数据时发生异常");
        }

    }

    /**
     * 用户数据校验
     *
     * @param input
     */
    private void validUserInfo(BizUserInfo input) throws CustomException {

        // 数据校验
        // 判断开通类型与套餐类型是否一致
        if (!checkOpenTypeWithTcid(input.getInsource(), input.getTcid().toString())) {
            logger.info("账号{}，省份{}，业务编号{}，账号开通失败：开通方式必须与套餐要一致，例如：短信开通只能选择短信套餐", input.getUserNo(), input.getRpcode(), input.getBizNo());
            throw new CustomException("账号开通失败：开通方式必须与套餐要一致，例如：短信开通只能选择短信套餐 ");
        }

        // 判断联系号码是否为手机号
        if (StringUtil.isNotEmpty(input.getPhone())
                && !PhoneCallCheck.checkCaller(input.getPhone())) {
            logger.info("账号{}，省份{}，业务编号{}，账号开通失败：联系号码必须为手机号", input.getUserNo(), input.getRpcode(), input.getBizNo());
            throw new CustomException("账号开通失败：联系号码必须为手机号");
        }

        // 开通时间不能为空
        if (StringUtil.isEmpty(input.getIntime().toString())) {
            logger.info("账号{}，省份{}，业务编号{}，账号开通失败：开通时间不能为空", input.getUserNo(), input.getRpcode(), input.getBizNo());
            throw new CustomException("账号开通失败：开通时间不能为空");
        }

    }

    /**
     * 判断开通类型跟开通套餐是否一致
     *
     * @param insource 开通类型
     * @param tcid     资费套餐
     * @return true一致 false不一致
     */
    private boolean checkOpenTypeWithTcid(String insource, String tcid) {
        // 如果规定的开通类型与传入的开通类型一致
        if (defaultTcidWithOpenType.get(tcid).getText().equals(insource)) {
            return true;
        }

        return false;
    }

    /**
     * 准备用户相关信息用于新增
     *
     * @param userinfo
     * @return
     */
    private UniUserInfo prepareUserForInsert(BizUserInfo userinfo) {
        UniUserInfo user = new UniUserInfo();

        //判断号码状态，固话、手机
        user.setPhonetype(userinfo.getUserType());

        //根据用户号码获取号码归属地信息Model，并给用户组装上省份编号、城市编号、区号等信息
        user.setAreacode(user.getAreacode());
        user.setRpcode(userinfo.getRpcode());
        user.setCitycode(userinfo.getCityCode());

        // 创建时间
        user.setCreatetime(userinfo.getIntime());

        // 注册时间
        user.setSignuptime(userinfo.getIntime());

        // 用户状态
        user.setUserstatus(BizConstants.USERSTATUS_NORMAL);

        // 业务类型
        user.setBusinesstype(BizConstants.DEFAULT_BUSINESSTYPE);
        return user;
    }


    /**
     * 设置共通属性到用户信息上
     *
     * @param user
     * @param userinfo
     * @return
     */
    private UniUserInfo prepareCommonForUserInfo(UniUserInfo user, BizUserInfo userinfo) {

        // 电话号码
        user.setUserno(userinfo.getUserNo());
        // 用户密码
        user.setPassword(DESUtils.encryptOldStructure(MD5Utils.md5(BizConstants.DEFAULT_PASSWORD), profile));
        //user.setPassword(userinfo.getPasswd());
        // 开通时间
        user.setOpendatetime(userinfo.getIntime());
        // 联系电话
        if (StringUtil.isEmpty(userinfo.getPhone())) {
            // 没有联系电话时使用电话号码
            user.setPhone(userinfo.getUserNo());
        } else {
            // 若果存在则直接使用
            user.setPhone(userinfo.getPhone());
        }
        // 备注
        user.setRemark(userinfo.getRemark());

        // 开通类型
        user.setIsignupsource(userinfo.getInsource());
        user.setImportflag(BizConstants.IMPORTFLAG_NO);

        return user;
    }

    /**
     * 用户修改
     *
     * @param input
     */
    @Override
    public void updateUser(BizUserInfo input) throws CustomException {

        List<UniUserInfo> users = getUserList(input.getUserNo());

        if (users.size() == 0) {
            throw new CustomException("用户未注册");
        } else if (users.size() != 1) {
            throw new CustomException("用户不唯一");
        }

        UniUserInfo user = users.get(0);

        if (input.getStatus().equals(BizConstants.USERSTATUS_NORMAL)) {
            if (user.getUserstatus().equals(BizConstants.USERSTATUS_NORMAL)) {
                // 更新用户信息
                updateUserDetail(input);
            } else if (user.getUserstatus().equals(BizConstants.USERSTATUS_CANCEL)) {
                // 重新开通用户
                reopenUser(input, user);
            } else {
                throw new CustomException("用户状态异常");
            }

        } else if (input.getStatus().equals(BizConstants.USERSTATUS_CANCEL)) {
            // 退订用户
            cancelUser(input, user);
        } else {
            throw new CustomException("用户状态异常");
        }
    }

    /**
     * 用户信息取得
     *
     * @param userno
     */
    private List<UniUserInfo> getUserList(String userno) {

        Example example = new Example(UniUserInfo.class);
        example.createCriteria().andEqualTo("userno", userno);

        return userMapper.selectByExample(example);
    }

    /**
     * 已退订退订用户重新开通
     *
     * @param input
     * @param user
     * @throws Exception
     */
    private void reopenUser(BizUserInfo input, UniUserInfo user) throws CustomException {

        // 用户数据校验
        validUserInfo(input);

        // 取得套餐信息
        UniUserTaocan taocan = taocanMapper.selectByPrimaryKey(input.getTcid());
        if (taocan == null) {
            logger.info("账号{}，省份{}，业务编号{}，账号开通失败：业务数据库不存在该套餐", input.getUserNo(), input.getRpcode(), input.getBizNo());
            throw new CustomException("账号开通失败：业务数据库不存在该套餐：" + input.getTcid().toString());
        }

        logger.info("账号{}，省份{}，业务编号{}，用户重新开通", input.getUserNo(), input.getRpcode(), input.getBizNo());
        // 更新用户
        updateUserInfo(input, taocan, user);

    }


    /**
     * 退订用户
     *
     * @param input
     * @param user
     * @throws Exception
     */
    private void cancelUser(BizUserInfo input, UniUserInfo user) throws CustomException {

        if (StringUtil.isEmpty(input.getIsrefund())) {
            throw new CustomException("没有设置是否退费");
        }

        // 退费金额设置，是否退费(0:不退费；1：退费)
        if (input.getIsrefund().equals(BizConstants.REFUND_YES)) {
            if (input.getRefundamount() == null) {
                throw new CustomException("没有设置退费金额");
            }

            if (input.getRefundamount() <= 0) {
                throw new CustomException("设置了退费，退费金额小于或等于零");
            }
        } else if (!input.getIsrefund().equals(BizConstants.REFUND_NO)) {
            throw new CustomException("退费参数设置有误，非1和2值。");
        }

        if (!user.getUserstatus().equals(BizConstants.USERSTATUS_NORMAL)) {
            throw new CustomException("用户为非正常状态");
        }

        String isrefund = "";
        // 转化退费字段(1：退费;2:不退费=>0:不退费；1：退费)
        if (input.getIsrefund().equals(BizConstants.REFUND_YES)) {
            isrefund = BizConstants.CANCEL_BIZ_REFUNDS;
        } else if (input.getIsrefund().equals(BizConstants.REFUND_NO)) {
            isrefund = BizConstants.CANCEL_BIZ_NOT_REFUNDS;
        }

        // 复制历史对象
        UniUserInfoHistory infoHistory = new UniUserInfoHistory();
        try {
            PropertyUtils.copyProperties(infoHistory, user);
            infoHistory.setCpid(null);
        } catch (Exception e) {
            throw new CustomException("系统处理异常");
        }

        //======================================== 数据库操作 ======================================//
        // 手动加入事物管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            // 清空用户文件信息
            deleteVoiceInfoAndFileBy(input.getUserNo());

            // 4.在历史表插入一条记录
            infoHistory.setIsrefund(isrefund);
            infoHistory.setRefundamount(input.getRefundamount().toString());
            infoHistory.setRefundremark(input.getRefundremark());
            infoHistory.setCanceldatetime(new Date());

            uniUserInfoHistoryMapper.insert(infoHistory);

            // 5.修改用户表状态
            UniUserInfo userInfo = new UniUserInfo();
            userInfo.setUserstatus(BizConstants.USERSTATUS_CANCEL);
            userInfo.setCanceldatetime(new Date());

            Example example = new Example(UniUserInfo.class);
            example.createCriteria().andEqualTo("userno", input.getUserNo());

            userMapper.updateByExampleSelective(userInfo, example);

            txManager.commit(status);
        } catch (Exception e) {
            txManager.rollback(status);
            e.printStackTrace();
            throw new CustomException("事物处理异常!");
        }

    }

    /**
     * 更新用户信息
     *
     * @param input
     */
    private void updateUserDetail(BizUserInfo input) {

        Example example = new Example(UniUserInfo.class);
        example.createCriteria().andEqualTo("userno", input.getUserNo());

        UniUserInfo user = Frombiz2User(input);
        user.setUserno(null);

        int updateNum = userMapper.updateByExampleSelective(user, example);

        logger.info("CPUnicom用户修改,更新了{}条数据", updateNum);

    }

    /**
     * Bean转换
     *
     * @param bizUserInfo
     * @return
     */
    UniUserInfo Frombiz2User(BizUserInfo bizUserInfo) {
        UniUserInfo bean = new UniUserInfo();
        Date time = new Date();
        bean.setUserno(bizUserInfo.getUserNo());
        bean.setPassword(DESUtils.encryptOldStructure(MD5Utils.md5(BizConstants.DEFAULT_PASSWORD), profile));
        bean.setUsertype(bizUserInfo.getUserType());
        bean.setPhonetype(bizUserInfo.getPhonetype());
        bean.setRpcode(bizUserInfo.getRpcode());
        bean.setCitycode(bizUserInfo.getCityCode());
        bean.setPhone(bizUserInfo.getPhone());
        bean.setCreatetime(time);
        bean.setUserstatus(bizUserInfo.getStatus());
        bean.setRectipflag(bizUserInfo.getCallerVoice());
        bean.setDefaultpwdflag(bizUserInfo.getDefaultpwdflag());
        bean.setOpenflag(BizConstants.USERSTATUS_NORMAL);

        // 取得套餐信息
        UniUserTaocan taocan = taocanMapper.selectByPrimaryKey(bizUserInfo.getTcid());
        if (taocan == null) {
            logger.info("账号{}，省份{}，业务编号{}，账号更新失败：业务数据库不存在该套餐", bizUserInfo.getUserNo(), bizUserInfo.getRpcode(), bizUserInfo.getBizNo());
            throw new CustomException("账号开通失败：业务数据库不存在该套餐：" + bizUserInfo.getTcid().toString());
        }
        bean.setTaocanname(taocan.getTaocanName());
        bean.setTcid(bizUserInfo.getTcid());
        bean.setOpendatetime(time);
        bean.setSignuptime(time);
        bean.setBusinesstype(bizUserInfo.getBusinesstype());
        bean.setRemark(bizUserInfo.getRemark());
        return bean;
    }

    /**
     * 根据用户编号，删除对应语音文件和语音信息
     *
     * @throws Exception
     */
    private void deleteVoiceInfoAndFileBy(String userno) throws CustomException {

        // 1.查询出用户的录音信息
        List<UniUserVoiceInfo> voiceInfos = findVoiceInfoByUserNo(userno);

        if (voiceInfos == null) {
            return;
        }

        // 2.查询出录音文件信息
        for (UniUserVoiceInfo voice : voiceInfos) {
            UniUserFile userFile = findVoiceFileByRecordNo(voice.getRecordno());

            // 3.删除录音文件信息
            deleteVoiceFile(userFile);

            // 4. 删除录音信息
            deleteVoiceInfo(voice);
        }
    }

    /**
     * 根据用户编号，查询该用户所有的语音信息
     */
    private List<UniUserVoiceInfo> findVoiceInfoByUserNo(String userno) {
        Example example = new Example(UniUserVoiceInfo.class);
        example.createCriteria().andEqualTo("iuserno", userno);

        return uniUserVoiceMapper.selectByExample(example);
    }


    /**
     * 根据语音信息编号，查询对应的语音文件
     */
    private UniUserFile findVoiceFileByRecordNo(String recordNo) {
        Example example = new Example(UniUserFile.class);
        example.createCriteria().andEqualTo("pfRecordNo", recordNo);

        List<UniUserFile> files = uniUserFileMapper.selectByExample(example);

        return CollectionUtils.isEmpty(files) ? null : files.get(0);
    }


    /**
     * 删除指定的语音文件
     *
     * @throws Exception
     */
    private void deleteVoiceFile(UniUserFile userFile) throws CustomException {

        if (userFile == null) {
            return;
        }

        UniUserFileHistory fileHistory = new UniUserFileHistory();

        try {
            PropertyUtils.copyProperties(fileHistory, userFile);
            // 添加历史文件
            uniUserFileHistoryMapper.insert(fileHistory);

            Example example = new Example(UserFile.class);
            example.createCriteria().andEqualTo("pfNotaryUserNo", userFile.getPfNotaryUserNo());
            uniUserFileMapper.deleteByExample(example);

        } catch (Exception e) {
            throw new CustomException("删除语音文件时发生异常");
        }
    }

    /**
     * 删除指定的语音信息
     *
     * @throws Exception
     */
    private void deleteVoiceInfo(UniUserVoiceInfo userVoiceInfo) throws CustomException {
        UniUserVoiceInfoHistory userVoiceInfoHistory = new UniUserVoiceInfoHistory();

        try {
            PropertyUtils.copyProperties(userVoiceInfoHistory, userVoiceInfo);
            userVoiceInfoHistory.setCpid(null);
            // 添加历史文件
            uniUserVoiceInfoHistoryMapper.insert(userVoiceInfoHistory);

            // 删除老文件
            Example example = new Example(UniUserVoiceInfo.class);
            example.createCriteria().andEqualTo("iuserno", userVoiceInfo.getIuserno());

            uniUserVoiceMapper.deleteByExample(example);

        } catch (Exception e) {
            throw new CustomException("删除语音信息时发生异常");
        }

    }
}
