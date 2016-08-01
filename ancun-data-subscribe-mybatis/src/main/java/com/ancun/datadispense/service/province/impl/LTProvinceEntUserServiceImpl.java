package com.ancun.datadispense.service.province.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.dx.*;
import com.ancun.common.persistence.model.dx.*;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.province.LTProvinceEntUserService;
import com.ancun.datadispense.service.province.ProvincesBaseService;
import com.ancun.datadispense.util.CustomException;
import com.ancun.utils.StringUtil;
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
public class LTProvinceEntUserServiceImpl implements LTProvinceEntUserService {
    private static final Logger logger = LoggerFactory.getLogger(LTProvinceEntUserServiceImpl.class);

    @Resource
    private ProvincesBaseService provincesBaseService;

    @Resource
    private EntUserInfoMapper entUserInfoMapper;

    @Resource
    private EntUserInfoHistoryMapper entUserInfoHistoryMapper;

    @Resource
    private UserFileMapper userFileMapper;

    @Resource
    private EntUserFileMapper entUserFileMapper;

    @Resource
    private UserFileHistoryMapper userFileHistoryMapper;

    @Resource
    private UserVoiceInfoMapper userVoiceInfoMapper;

    @Resource
    private UserVoiceInfoHistoryMapper userVoiceInfoHistoryMapper;

    @Resource
    private EntUserVoiceInfoMapper entUserVoiceInfoMapper;

    @Resource
    private EntUserVoiceInfoHistoryMapper entUserVoiceInfoHistoryMapper;

    @Resource
    private UserInfoHistoryMapper userInfoHistoryMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private RecordPermisstionMapper recordPermisstionMapper;

    @Resource
    private RecordOrgMapper recordOrgMapper;

    @Resource
    private RecordRoleMapper recordRoleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private EntUserFileHistoryMapper entUserFileHistoryMapper;

    @Resource
    private DataSourceTransactionManager txManager;

    @Resource
    private UserAccountLogMapper userAccountLogMapper;

    @Override
    public void openEntUser(BizUserInfo bizUserInfo) throws CustomException {

        //子账号
        String userTel = bizUserInfo.getUserNo();
        String rpCode = bizUserInfo.getRpcode();


        /**
         * 1、判断是否已开通个人用户：开通且正常状态抛出异常
         */
        UserInfo userInfo = provincesBaseService.selectPersonalUserInfo(userTel);

        if (null != userInfo && BizConstants.USERSTATUS_NORMAL.equals(userInfo.getUserstatus())) {
            throw new CustomException("企业用户开通失败：该号码已开通为个人用户");
        }

        EntUserInfo entUserInfo = provincesBaseService.selectEntUserInfo(null, userTel, rpCode);

        /**
         * 2、判断企业用户状态
         *  2.1、为空则表示未注册,新增数据
         *  2.2、不为空则表示已注册：退订状态则重新开通，正常状态则抛出异常(用户已开通)
         */
        if (null == entUserInfo) {
            logger.debug("企业用户表新增记录：bizUserInfo={}", bizUserInfo);
            insertEntUserInfo(bizUserInfo);
        } else {
            if (BizConstants.USERSTATUS_NORMAL.equals(entUserInfo.getUserstatus())) {
                logger.debug("企业账号开通失败：用户已开通,bizUserInfo={}", bizUserInfo);
                throw new CustomException("企业账号开通失败：用户已开通");
            } else {
                logger.debug("重新开通企业账号：bizUserInfo={}", bizUserInfo);
                reOnpenEntUser(bizUserInfo);
            }
        }
    }

    /**
     * 个人用户迁移至企业账户
     *
     * @param entNo
     * @param rpCode
     * @param userInfo
     */
    private void personal2EntUser(String entNo, String rpCode, UserInfo userInfo) throws CustomException {
        /**
         * 1、文件迁移:
         *  1.1、个人用户文件迁移至企业用户文件表和历史表，并删除
         *  1.2、个人用户录音摘要迁移至企业用户录音摘要表和历史表，并删除
         */
        removceUserFile(entNo, userInfo.getUserno(), rpCode);
        removceUserVoice(entNo, userInfo.getUserno(), rpCode);

        /**
         * 2、用户信息修改：
         *   2.1、个人用户信息迁移至历史表：
         *   2.2、个人用户状态修改
         */
        removeUserInfo2History(userInfo);
        updateUserInfo(userInfo);
    }

    /**
     * 个人用户状态修改
     *
     * @param userInfo
     */
    private void updateUserInfo(UserInfo userInfo) {
        Example exmaple = new Example(UserInfo.class);
        exmaple.createCriteria().andEqualTo("userno", userInfo.getUserno()).andEqualTo("rpcode", userInfo.getRpcode());

        UserInfo temp = new UserInfo();
        temp.setUserstatus(BizConstants.USERSTATUS_CANCEL);
        temp.setCanceldatetime(new Date());

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            userInfoMapper.updateByExampleSelective(temp, exmaple);
            txManager.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            txManager.rollback(status);
            throw new CustomException("个人用户状态修改异常!");
        }
    }

    /**
     * 个人用户信息迁移至历史表：
     *
     * @param userInfo
     */
    private void removeUserInfo2History(UserInfo userInfo) throws CustomException {

        UserInfoHistory history = new UserInfoHistory();
        try {
            PropertyUtils.copyProperties(history, userInfo);
        } catch (Exception e) {
            throw new CustomException("对象复制异常");
        }

        history.setCpid(null);
        history.setCanceldatetime(null == userInfo.getCanceldatetime() ? new Date() : userInfo.getCanceldatetime());
        history.setIsrefund(BizConstants.REFUND_NO);// 不退费

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        try {

            userInfoHistoryMapper.insertSelective(history);
            txManager.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            txManager.rollback(status);
            throw new CustomException("个人用户信息迁移至历史表异常！");
        }
    }

    /**
     * 个人用户录音摘要迁移至企业用户录音摘要表和历史表，并删除
     *
     * @param entNo
     * @param userTel
     * @param rpCode
     */
    private void removceUserVoice(String entNo, String userTel, String rpCode) throws CustomException {
        UserVoiceInfo userVoiceInfo = new UserVoiceInfo();
        userVoiceInfo.setIuserno(userTel);
        List<UserVoiceInfo> voiceInfoList = userVoiceInfoMapper.select(userVoiceInfo);
        if (null == voiceInfoList || voiceInfoList.size() <= 0) {
            return;
        }

        logger.debug("个人用户录音摘要迁移至企业用户录音摘要表和历史表，并删除,个人用户文件个数={}", voiceInfoList.size());

        for (UserVoiceInfo temp : voiceInfoList) {

            EntUserVoiceInfo entVoiceInfo = new EntUserVoiceInfo();
            try {
                PropertyUtils.copyProperties(entVoiceInfo, temp);
            } catch (Exception e) {
                throw new CustomException("对象复制异常");
            }
            entVoiceInfo.setCpid(null);
            entVoiceInfo.setIuserno(entNo);
            entVoiceInfo.setAccountno(userTel);
            entVoiceInfo.setRpcode(rpCode);
            entUserVoiceInfoMapper.insertSelective(entVoiceInfo);

            UserVoiceInfoHistory history = new UserVoiceInfoHistory();
            try {
                PropertyUtils.copyProperties(history, temp);
            } catch (Exception e) {
                throw new CustomException("对象复制异常");
            }
            history.setCpid(null);

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = txManager.getTransaction(def);
            try {
                userVoiceInfoHistoryMapper.insertSelective(history);


                userVoiceInfoMapper.delete(temp);
                txManager.commit(status);
            } catch (Exception e) {
                e.printStackTrace();
                txManager.rollback(status);
                throw new CustomException("个人用户录音摘要迁移至企业用户录音摘要表和历史表异常！");
            }
        }
    }

    /**
     * 个人用户文件迁移至企业用户文件表和历史表，并删除
     *
     * @param entNo
     * @param userTel
     * @param rpCode
     */

    private void removceUserFile(String entNo, String userTel, String rpCode) throws CustomException {
        UserFile userFile = new UserFile();
        userFile.setPfNotaryUserNo(userTel);
        List<UserFile> userFileList = userFileMapper.select(userFile);

        if (null == userFileList || userFileList.size() <= 0) {
            return;
        }

        logger.debug("个人用户文件迁移至企业用户文件表和历史表，并删除,个人用户文件个数={}", userFileList.size());
        for (UserFile temp : userFileList) {

            EntUserFile entUserFile = new EntUserFile();
            try {
                PropertyUtils.copyProperties(entUserFile, temp);
            } catch (Exception e) {
                throw new CustomException("对象复制异常");
            }
            entUserFile.setPfNotaryUserNo(entNo);
            entUserFile.setPfAccountNo(userTel);
            entUserFile.setRpcode(rpCode);
            entUserFile.setPfRecordNo(temp.getPfRecordNo());
            entUserFile.setFileCreateTime(new Date());
            entUserFileMapper.insertSelective(entUserFile);


            UserFileHistory history = new UserFileHistory();
            try {
                PropertyUtils.copyProperties(history, temp);
            } catch (Exception e) {
                throw new CustomException("对象复制异常");
            }

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = txManager.getTransaction(def);
            try {
                userFileHistoryMapper.insertSelective(history);
                userFileMapper.delete(temp);
                txManager.commit(status);
            } catch (Exception e) {
                e.printStackTrace();
                txManager.rollback(status);
                throw new CustomException("个人用户文件迁移至企业用户文件表和历史表异常!");
            }
        }
    }


    /**
     * 重新开通企业账号
     *
     * @param bizUserInfo
     */

    private void reOnpenEntUser(BizUserInfo bizUserInfo) {

        EntUserInfo entUserInfo = new EntUserInfo();

        transformEntUserInfo(bizUserInfo, entUserInfo);
        entUserInfo.setCanceldatetime(null);

        entUserInfoMapper.updateSelective(entUserInfo);
    }

    /**
     * BizUserInfo 转换  entUserInfo
     *
     * @param bizUserInfo
     * @param entUserInfo
     */
    private void transformEntUserInfo(BizUserInfo bizUserInfo, EntUserInfo entUserInfo) {

        entUserInfo.setUserno(bizUserInfo.getEntNo());
        entUserInfo.setUserTel(bizUserInfo.getUserNo());
        entUserInfo.setPassword(bizUserInfo.getPasswd());
        entUserInfo.setAccounttype(bizUserInfo.getAccountType());
        entUserInfo.setUserstatus(bizUserInfo.getStatus());
        entUserInfo.setContactphone(bizUserInfo.getPhone());
        entUserInfo.setRemark(bizUserInfo.getRemark());
        entUserInfo.setRectipflag(bizUserInfo.getRectip());
        entUserInfo.setCallerflag(bizUserInfo.getCallerflag());
        entUserInfo.setCalledflag(bizUserInfo.getCalledflag());
        entUserInfo.setIsignupsource(bizUserInfo.getInsource());
        entUserInfo.setSignuptime(bizUserInfo.getIntime());
        entUserInfo.setAccesscodeActive(BizConstants.ACCESSCODE_ACTIVE);
        entUserInfo.setPhonetype(bizUserInfo.getPhonetype());
        entUserInfo.setRpcode(bizUserInfo.getRpcode());
        entUserInfo.setCitycode(bizUserInfo.getCityCode());
        entUserInfo.setOrgNo(StringUtil.isEmpty(bizUserInfo.getOrgNo()) ? null : bizUserInfo.getOrgNo());
        entUserInfo.setDefaultpwdflag(bizUserInfo.getDefaultpwdflag());
        entUserInfo.setOpendatetime(bizUserInfo.getIntime());
        entUserInfo.setCanceldatetime(bizUserInfo.getOfftime());
        entUserInfo.setIsrefund(bizUserInfo.getIsrefund());
        entUserInfo.setRefundamount(bizUserInfo.getRefundamount());
        entUserInfo.setRefundremark(bizUserInfo.getRefundremark());
        entUserInfo.setSmsLogin(bizUserInfo.getSmsLogin());
        entUserInfo.setTaocanid(bizUserInfo.getTcid());
        entUserInfo.setEmail(bizUserInfo.getEmail());
        entUserInfo.setCallervoice(bizUserInfo.getCallerVoice());
        entUserInfo.setCalledvoice(bizUserInfo.getCalledVoice());
        entUserInfo.setCallerrecordvoice(bizUserInfo.getCallerRecord());
        entUserInfo.setCalledrecordvoice(bizUserInfo.getCalledRecord());

        entUserInfo.setAddress(bizUserInfo.getAddress());
        entUserInfo.setUsername(bizUserInfo.getName());
        entUserInfo.setCertificatenum(bizUserInfo.getRegNo());

    }

    /**
     * 个人用户转企业用户插入账号日志
     *
     * @param entNo
     * @param userTel
     * @param accountType
     */
    private void insertAccoutLog(String entNo, String userTel, String accountType) {

        UserAccountLog log = new UserAccountLog();
        log.setUserNo(entNo);
        log.setUserTel(userTel);
        log.setChangeDate(new Date());
        log.setOldType(BizConstants.ACCOUNTTYPE_PERSONAL);
        log.setNewType(accountType);
        userAccountLogMapper.insertSelective(log);
        logger.debug("个人用户转企业用户插入账号日志，userTel={},entNo={}", userTel, entNo);
    }

    /**
     * 新增一条企业用户数据
     *
     * @param bizUserInfo
     */
    private void insertEntUserInfo(BizUserInfo bizUserInfo) {

        EntUserInfo entUserInfo = new EntUserInfo();

        transformEntUserInfo(bizUserInfo, entUserInfo);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            entUserInfoMapper.insertSelective(entUserInfo);
            txManager.commit(status);
        } catch (Exception e) {
            txManager.rollback(status);
            e.printStackTrace();
            throw new CustomException("新增一条企业用户异常!");
        }
    }


    @Override
    public void updateEntUser(BizUserInfo bizUserInfo) throws CustomException {

        //子账号
        String userTel = bizUserInfo.getUserNo();
        //主账号
        String entNo = bizUserInfo.getEntNo();
        String rpCode = bizUserInfo.getRpcode();

        String accountType = bizUserInfo.getAccountType();

        String operation = bizUserInfo.getOperation();

        String bizUserStatus = bizUserInfo.getStatus();

        if (StringUtil.isNotEmpty(operation) && BizConstants.MARK_YES.equals(bizUserInfo.getOperation()) && bizUserStatus.equals(BizConstants.USERSTATUS_NORMAL)) {
            /**
             * 1、个人用户转企业用户
             *   1.1、业务数据库个人用户表不存在该用户或非正常状态：抛出异常
             *   1.2、业务数据库个人用户表存在该用户：个人转企业操作
             */
            UserInfo userInfo = provincesBaseService.selectPersonalUserInfo(userTel);

            if (null == userInfo || !BizConstants.USERSTATUS_NORMAL.equals(userInfo.getUserstatus())) {
                throw new CustomException("个人用户转企业用户失败：业务数据库中个人账号未开通或状态异常");
            } else {
//                // 手动加入事物管理
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                TransactionStatus status = txManager.getTransaction(def);
                try {
                    // 个人用户转换为企业用户
                    logger.debug("个人用户转企业用户：bizUserInfo={}", bizUserInfo);
                    personal2EntUser(entNo, rpCode, userInfo);

                    insertAccoutLog(entNo, userTel, bizUserInfo.getAccountType());

                    insertEntUserInfo(bizUserInfo);
                    txManager.commit(status);
                } catch (Exception e) {
                    e.printStackTrace();
                    txManager.rollback(status);
                    throw new CustomException("个人用户转企业用户事物处理异常!");
                }
            }
        } else {
            /**
             * 2.企业用户修改
             *  2.1、业务数据库中企业用户表是否存在该用户,不存在或者已退订状态：抛出异常;存在后续操作
             */
            EntUserInfo entUserInfo = provincesBaseService.selectEntUserInfo(entNo, userTel, rpCode);

            if (null == entUserInfo) {
                throw new CustomException("企业用户修改失败：业务数据库中该企业用户不存在");
            } else {
                /**
                 * 2.2、如果业务系统用户状态为退订状态
                 *  ① BOSS系统用户为退订状态（退订操作）：抛出异常
                 *  ② BOSS系统用户为开通状态：重新开通
                 */
                if (BizConstants.USERSTATUS_CANCEL.equals(entUserInfo.getUserstatus())) {
                    if (BizConstants.USERSTATUS_CANCEL.equals(bizUserStatus)) {
                        logger.debug("企业账号退订失败：业务系统中该企业用户已退订,bizUserInfo={}", bizUserInfo);
                        throw new CustomException("企业账号退订失败：业务系统中该企业用户已退订");
                    } else {
                        logger.debug("企业用户重新开通：bizUserInfo={}", bizUserInfo);
                        reOnpenEntUser(bizUserInfo);
                    }
                } else {
                    /**
                     * 2.3、如果业务系统用户状态非退订状态
                     *  ① BOSS系统用户为退订状态：退订操作
                     *  ② BOSS系统用户为其他状态：修改企业账号
                     */
                    if (BizConstants.USERSTATUS_CANCEL.equals(bizUserStatus)) {
                        logger.debug("企业用户退订：bizUserInfo={}", bizUserInfo);
                        // 是否主账号
                        Boolean isMain = accountType.equals(BizConstants.ACCOUNTTYPE_MAIN) && userTel.equals(entNo);

                        // 手动加入事物管理
                        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                        TransactionStatus status = txManager.getTransaction(def);
                        try {
                            cancelEntUserInfo(entNo, userTel, rpCode, isMain);
                            removeEntUser2History(entUserInfo);

                            txManager.commit(status);
                        } catch (Exception e) {
                            txManager.rollback(status);
                            e.printStackTrace();
                            throw new CustomException("事物处理异常!");
                        }
                    } else {
                        logger.debug("企业用户修改：bizUserInfo={}", bizUserInfo);
                        updateEntUserInfo(bizUserInfo);
                    }
                }
            }
        }
    }

    /**
     * 退订用户
     *
     * @param entNo
     * @param userTel
     * @param rpCode
     * @param isMain
     */
    private void cancelEntUserInfo(String entNo, String userTel, String rpCode, Boolean isMain) throws CustomException {
        /**
         * 1、删除用户组织、权限等信息
         *  1.1、主账号：删除主账号组织、组织权限、角色、角色权限
         */
        if (isMain) {
            deleteMainOrgAndRole(entNo);
        }

        /**
         * 2、迁移文件数据并删除
         * 2.1、录音信息迁移至历史表后删除
         * 2.2、录音文件迁移至历史表后删除
         */
        removeEntUserFile2History(entNo, userTel, rpCode);

        removeEntUserVoice2History(entNo, userTel, rpCode);


    }

    /**
     * 企业用户信息迁移至历史表
     *
     * @param entUserInfo
     */
    private void removeEntUser2History(EntUserInfo entUserInfo) throws CustomException {

        EntUserInfoHistory history = new EntUserInfoHistory();
        try {
            PropertyUtils.copyProperties(history, entUserInfo);
        } catch (Exception e) {
            throw new CustomException("对象复制异常");
        }
        history.setUiid(null);
        entUserInfoHistoryMapper.insertSelective(history);

        updateEntUser2Cannel(entUserInfo);
    }

    /**
     * 修改用户状态为退订
     *
     * @param temp
     */
    private void updateEntUser2Cannel(EntUserInfo temp) {

        Example example = new Example(EntUserInfo.class);
        example.createCriteria().andEqualTo("userno", temp.getUserno()).
                andEqualTo("userTel", temp.getUserTel()).
                andEqualTo("rpcode", temp.getRpcode());

        EntUserInfo entUserInfo = new EntUserInfo();
        entUserInfo.setUserstatus(BizConstants.USERSTATUS_CANCEL);
        entUserInfo.setCanceldatetime(new Date());
        entUserInfo.setOrgNo(null);

        entUserInfoMapper.updateByExampleSelective(entUserInfo, example);
    }

    /**
     * 企业用户信息修改
     *
     * @param bizUserInfo
     */
    private void updateEntUserInfo(BizUserInfo bizUserInfo) {

        Example example = new Example(EntUserInfo.class);
        example.createCriteria().andEqualTo("userno", bizUserInfo.getEntNo()).
                andEqualTo("userTel", bizUserInfo.getUserNo()).andEqualTo("rpcode", bizUserInfo.getRpcode());

        EntUserInfo entUserInfo = new EntUserInfo();
        transformEntUserInfo(bizUserInfo, entUserInfo);
        entUserInfo.setUserno(null);
        entUserInfo.setUserTel(null);
        entUserInfo.setRpcode(null);

        entUserInfoMapper.updateByExampleSelective(entUserInfo, example);
    }


    /**
     * 删除用户组织、权限信息
     *
     * @param userNo
     */
    private void deleteMainOrgAndRole(String userNo) {
        deleteRecordPermisstion(userNo);

        deleteOrg(userNo);

        deleteRolePermission(userNo);

        deleteRole(userNo);
    }

    /**
     * 删除用户角色信息
     *
     * @param userNo
     */
    private void deleteRole(String userNo) {
        Example example = new Example(RecordRole.class);
        example.createCriteria().andEqualTo("userNo", userNo);

        recordRoleMapper.deleteByExample(example);
    }

    /**
     * 删除用户角色权限
     *
     * @param userNo
     */
    private void deleteRolePermission(String userNo) {
        rolePermissionMapper.deleteRolePermissionByUserNo(userNo);
    }

    /**
     * 删除用户组织权限信息
     *
     * @param userNo
     */
    private void deleteRecordPermisstion(String userNo) {
        Example example = new Example(RecordPermisstion.class);
        example.createCriteria().andEqualTo("userNo", userNo);

        recordPermisstionMapper.deleteByExample(example);
    }

    /**
     * 删除用户组织信息
     *
     * @param userNo
     */
    private void deleteOrg(String userNo) {
        Example example = new Example(RecordOrg.class);
        example.createCriteria().andEqualTo("userNo", userNo);

        recordOrgMapper.deleteByExample(example);
    }

    /**
     * 企业用户录音信息迁移至历史表并删除
     *
     * @param entNo
     * @param userTel
     * @param rpCode
     */
    private void removeEntUserVoice2History(String entNo, String userTel, String rpCode) throws CustomException {

        EntUserVoiceInfo entUserVoiceInfo = new EntUserVoiceInfo();
        entUserVoiceInfo.setIuserno(entNo);
        entUserVoiceInfo.setAccountno(userTel);
        entUserVoiceInfo.setRpcode(rpCode);

        List<EntUserVoiceInfo> voiceInfoList = entUserVoiceInfoMapper.select(entUserVoiceInfo);
        if (null == voiceInfoList || voiceInfoList.size() <= 0) {
            return;
        }

        for (EntUserVoiceInfo voiceInfo : voiceInfoList) {

            EntUserVoiceInfoHistory history = new EntUserVoiceInfoHistory();
            try {
                PropertyUtils.copyProperties(history, voiceInfo);
            } catch (Exception e) {
                throw new CustomException("对象复制异常");
            }

            history.setCpid(null);

            entUserVoiceInfoHistoryMapper.insertSelective(history);

            entUserVoiceInfoMapper.delete(voiceInfo);
        }

    }


    /**
     * 企业用户文件信息迁移至历史表并删除
     *
     * @param entNo
     * @param userTel
     * @param rpCode
     */
    private void removeEntUserFile2History(String entNo, String userTel, String rpCode) throws CustomException {

        EntUserFile entUserFile = new EntUserFile();
        entUserFile.setRpcode(rpCode);
        entUserFile.setPfAccountNo(userTel);
        entUserFile.setPfNotaryUserNo(entNo);


        List<EntUserFile> entUserFileList = entUserFileMapper.select(entUserFile);
        if (null == entUserFileList && entUserFileList.size() <= 0) {
            return;
        }

        for (EntUserFile userFile : entUserFileList) {

            EntUserFileHistory history = new EntUserFileHistory();
            try {
                PropertyUtils.copyProperties(history, userFile);
            } catch (Exception e) {
                throw new CustomException("对象复制异常");
            }

            entUserFileHistoryMapper.insertSelective(history);

            entUserFileMapper.delete(userFile);
        }
    }
}
