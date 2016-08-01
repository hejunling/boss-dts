package com.ancun.datadispense.service.sh.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.sh.*;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.common.persistence.model.sh.*;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.sh.BaseService4SH;
import com.ancun.datadispense.service.sh.IEntUserService4SH;
import com.ancun.datadispense.util.CustomException;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 上海企业用户
 *
 * @author lwk
 * @version 1.0
 * @Created on 2016年3月21日
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class EntUserService4SHImpl extends BaseService4SH implements IEntUserService4SH {

    @Resource
    private ShUserAccountLogMapper shUserAccountLogMapper;

    @Resource
    private ShRecordRoleMapper shRecordRoleMapper;

    @Resource
    private ShRolePermisstionMapper shRolePermisstionMapper;

    @Resource
    private ShRecordPermisstionMapper shRecordPermisstionMapper;

    @Resource
    private ShRecordOrgMapper shRecordOrgMapper;

    private static final Logger logger = LoggerFactory.getLogger(EntUserService4SHImpl.class);

    @Override
    @TargetDataSource(name = "shV2")
    public void openEntUser(BizUserInfo bizUserInfo) throws CustomException {

        //附件暂时丢失----------------------------------------------------------------------------------------------------------------------------

        //子账号
        String userTel = bizUserInfo.getUserNo();
        //主账号
        String entNo = bizUserInfo.getEntNo();
        String rpCode = bizUserInfo.getRpcode();

        /**
         * 1、判断是否已开通个人用户：开通且正常状态抛出异常
         */

        String personalStatus = selectUserStatus(userTel, userTel, false);
        if (StringUtil.isNotEmpty(personalStatus) && personalStatus.equals(BizConstants.USERSTATUS_NORMAL)) {
            throw new CustomException("企业用户开通失败：该号码已开通为个人用户");
        }


        /**
         * 2、判断企业用户状态
         *  2.1、为空则表示未注册,新增数据
         *  2.2、不为空则表示已注册：退订状态则重新开通，正常状态则抛出异常(用户已开通)
         */
        String entUserStatus = selectUserStatus(entNo, userTel, true);
        if (StringUtil.isEmpty(entUserStatus)) {
            insertUserinfo(bizUserInfo);
            insertAccountUser(bizUserInfo);
        } else {
            if (entUserStatus.equals(BizConstants.USERSTATUS_NORMAL)) {
                logger.info("主账号{},子账号{},省份{}，企业账号开通失败：用户已开通", entNo, userTel, rpCode);
                throw new CustomException("企业账号开通失败：用户已开通");
            }
        }

    }


    /**
     * 企业用户退订
     * 1.判断企业用户信息
     * 2.企业用户数据迁移一份到历史数据
     * ①如果是主账号退订 同时迁移子账号数据
     * ②如果是子账号退订 只迁移子账号数据
     * 3.企业用户信息更新
     *
     * @throws Exception
     */
    @Override
    @TargetDataSource(name = "shV2")
    public void updateEntUser(BizUserInfo bizUserInfo) throws CustomException {

        //子账号
        String userTel = bizUserInfo.getUserNo();
        //主账号
        String entNo = bizUserInfo.getEntNo();
        String rpCode = bizUserInfo.getRpcode();

        String accountType = bizUserInfo.getAccountType();

        String operation = bizUserInfo.getOperation();

        String bizUserStatus = bizUserInfo.getStatus();

        if (StringUtil.isNotEmpty(operation) && BizConstants.MARK_YES.equals(operation) && bizUserStatus.equals(BizConstants.USERSTATUS_NORMAL)) {
            /**
             * 1、个人用户转企业用户
             *   1.1、业务数据库个人用户表不存在该用户或非正常状态：抛出异常
             *   1.2、业务数据库个人用户表存在该用户：个人转企业操作
             */
            String personalStatus = selectUserStatus(userTel, userTel, false);
            if (StringUtil.isEmpty(personalStatus) || !personalStatus.equals(BizConstants.USERSTATUS_NORMAL)) {
                throw new CustomException("个人用户转企业用户失败：业务数据库中个人账号未开通或状态异常");
            } else {
                logger.info("个人用户转企业用户，bizUserInfo={}", bizUserInfo);
                // 个人用户录音迁移至历史数据并删除原数据.修改个人用户状态及退订时间
                person2Ent(bizUserInfo);

                insertUserinfo(bizUserInfo);
                insertAccountUser(bizUserInfo);

                // 插入日志
                insertAccoutLog(entNo, userTel, bizUserInfo.getAccountType(), personalStatus);
            }
        } else {
            /**
             * 2.企业用户修改
             *  2.1、业务数据库中企业用户表是否存在该用户,不存在或者已退订状态：抛出异常;存在后续操作
             */

            String entUserStatus = selectUserStatus(entNo, userTel, true);
            if (StringUtil.isEmpty(entUserStatus)) {
                logger.info("主账号{},子账号{},省份{}，企业账号退订失败：业务系统中该企业用户不存在", entNo, userTel, rpCode);
                throw new CustomException("企业账号退订失败：业务系统中该企业用户不存在");
            } else {
                /**
                 * 2.2、如果业务系统用户状态为退订状态
                 *  ① BOSS系统用户为退订状态（退订操作）：抛出异常
                 *  ② BOSS系统用户为开通状态：重新开通
                 */
                if (BizConstants.USERSTATUS_CANCEL.equals(entUserStatus)) {
                    if (BizConstants.USERSTATUS_CANCEL.equals(bizUserStatus)) {
                        logger.debug("企业账号退订失败：业务系统中该企业用户已退订,bizUserInfo={}", bizUserInfo);
                        throw new CustomException("企业账号退订失败：业务系统中该企业用户已退订");
                    } else {
                        logger.debug("企业用户重新开通：bizUserInfo={}", bizUserInfo);
                        reOpenAllUserInfos(entNo, userTel);
                    }
                } else {
                    /**
                     * 2.3、如果业务系统用户状态非退订状态
                     *  ① BOSS系统用户为退订状态：退订操作
                     *  ② BOSS系统用户为其他状态：修改企业账号
                     */
                    if (BizConstants.USERSTATUS_CANCEL.equals(bizUserStatus)) {

                        logger.debug("企业用户退订：bizUserInfo={}", bizUserInfo);
                        boolean isMain = false;
                        if (accountType.equals(BizConstants.ACCOUNTTYPE_MAIN)) {
                            deleteMainOrgAndRole(entNo);
                            isMain = true;
                        } else deleteMainOrgPermission(userTel);
                        cancelUserInfos(entNo, userTel, isMain);

                    } else {
                        updateUserInfo(bizUserInfo);
                        updateAccountInfo(bizUserInfo);
                    }
                }
            }
        }
    }


    /**
     * 插入账号日志
     *
     * @param entNo
     * @param userTel
     * @param accountType
     */
    private void insertAccoutLog(String entNo, String userTel, String accountType, String oldType) {
        ShUserAccountLog bean = new ShUserAccountLog();
        bean.setUserNo(entNo);
        bean.setUserTel(userTel);
        bean.setChangeDate(new Date());
        bean.setOldType(oldType);
        bean.setNewType(accountType);
        shUserAccountLogMapper.insert(bean);
    }


    /**
     * 删除用户组织、权限信息
     *
     * @param userNo
     */
    private void deleteMainOrgAndRole(String userNo) {
        deleteMainOrgPermission(userNo);

        deleteMainOrg(userNo);

        deleteMainRolePermission(userNo);

        deleteMainRole(userNo);
    }

    /**
     * 删除用户角色信息
     *
     * @param userNo
     */
    private void deleteMainRole(String userNo) {
        ShRecordRole record = new ShRecordRole();
        record.setUserNo(userNo);
        shRecordRoleMapper.delete(record);
        logger.info("删除用户角色信息,,userNo={}", userNo);
    }

    /**
     * 删除用户角色权限
     *
     * @param userNo
     */
    private void deleteMainRolePermission(String userNo) {
        ShRecordRole role = new ShRecordRole();
        role.setUserNo(userNo);
        List<ShRecordRole> list = shRecordRoleMapper.select(role);
        if (!list.isEmpty() && null != list.get(0)) {
            ShRolePermisstion record = new ShRolePermisstion();
            record.setPermisstionId(list.get(0).getId());
            int deleteNUm = shRolePermisstionMapper.delete(record);
            logger.info("删除用户角色权限{}条,userNo={}", deleteNUm, userNo);
        }

    }

    /**
     * 删除用户组织权限信息
     *
     * @param userNo
     */
    private void deleteMainOrgPermission(String userNo) {
        ShRecordPermisstion record = new ShRecordPermisstion();
        record.setUserNo(userNo);
        int deleteNum = shRecordPermisstionMapper.delete(record);
        logger.info("删除用户组织权限信息{}条,userNo={}", deleteNum, userNo);
    }

    /**
     * 删除用户组织信息
     *
     * @param userNo
     */
    private void deleteMainOrg(String userNo) {
        ShRecordOrg record = new ShRecordOrg();
        record.setUserNo(userNo);
        int deleteNum = shRecordOrgMapper.delete(record);
        logger.info("删除用户组织信息{}条,userNo={}", deleteNum, userNo);
    }

}
