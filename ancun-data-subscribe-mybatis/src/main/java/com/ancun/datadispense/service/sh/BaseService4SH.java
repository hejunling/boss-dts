package com.ancun.datadispense.service.sh;

import com.ancun.common.persistence.mapper.sh.*;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.common.persistence.model.sh.*;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.util.CustomException;
import com.ancun.utils.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 查询服务
 *
 * @author lwk
 * @version 1.0
 * @Created on 2016年3月21日
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BaseService4SH {

    private final static Logger log = LoggerFactory.getLogger(BaseService4SH.class);

    @Resource
    private ShAccountInfoMapper accountInfoMapper;

    @Resource
    private ShUserVoiceInfoMapper shUserVoiceInfoMapper;

    @Resource
    private ShUserFileMapper shUserFileMapper;

    @Resource
    private ShUserInfoMapper shUserInfoMapper;

    @Resource
    private ShAccountInfoHistoryMapper accountInfoHistoryMapper;

    @Resource
    private ShUserInfoHistoryMapper shUserInfoHistoryMapper;

    @Resource
    private ShUserFileHistoryMapper shUserFileHistoryMapper;

    @Resource
    private ShUserVoiceInfoHistoryMapper shUserVoiceInfoHistoryMapper;

    @Resource
    private OtherMapper otherMapper;

    @Resource
    private DataSourceTransactionManager txManager;

    /**
     * 查询用户状态
     *
     * @param userNo  企业子账号
     * @param userTel 企业主账号
     * @param isEnt  是否企业
     * @return
     */
    protected String selectUserStatus(String userNo, String userTel, boolean isEnt) throws CustomException {
        Example example = new Example(ShAccountInfo.class);
        if (isEnt)
//            example.createCriteria().andEqualTo("userTel", userTel).andNotEqualTo("accounttype", BizConstants.ACCOUNTTYPE_PERSONAL);
            example.createCriteria().andEqualTo("userTel", userTel);
        else
            example.createCriteria().andEqualTo("userTel", userTel).andEqualTo("accounttype", BizConstants.ACCOUNTTYPE_PERSONAL);
        List<ShAccountInfo> list = accountInfoMapper.selectByExample(example);
        log.info("查询个人用户状态,userNo={},userTel={}", userNo, userTel);
        if (list.isEmpty() || null == list.get(0)) return null;
        else return list.get(0).getUserstatus();
    }


    /**
     * 退订并迁移历史数据
     *
     * @param userNo
     * @param userTel
     * @param isMain
     * @throws Exception
     */
    protected void cancelUserInfos(String userNo, String userTel, boolean isMain) throws CustomException {
        // 手动加入事物管理
        TransactionStatus transOut = null;
        try {
            DefaultTransactionDefinition defOut = new DefaultTransactionDefinition();
            defOut.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            transOut = txManager.getTransaction(defOut);

            //修改个人用户状态及退订时间
            if (isMain || userNo.equals(userTel))
                updateUserInfo(userNo);
            //修改Account表
            updateAccountInfo(userNo, userTel);

            removceUserFiles2History(userNo, userTel);

            removceUserVoices2History(userNo, userTel);

            txManager.commit(transOut);
        } catch (Exception e) {
            txManager.rollback(transOut);
            throw new CustomException("退订用户数据库异常！已回滚。");
        }
    }

    /**
     * 个人转企业
     *
     * @param bizUserInfo
     * @throws Exception
     */
    protected void person2Ent(BizUserInfo bizUserInfo) throws CustomException {
        String userNo = bizUserInfo.getEntNo();
        String userTel = bizUserInfo.getUserNo();
        //修改个人用户
        deleteUserinfo(userTel);

        //修改Account表
        deleteAccountinfo(userTel, userTel);


        //如果是主账号。录音不处理，会自动转过去，如果是子账号，需要修改录音所有者
        if (!bizUserInfo.getAccountType().equals(BizConstants.ACCOUNTTYPE_MAIN)) {
            updateUserFilesOwner(userNo, userTel);
            updateVoivesOwner(userNo, userTel);
        }
    }

    /**
     * 重新开通用户迁移历史数据
     *
     * @param userNo
     * @throws Exception
     */
    protected void reOpenAllUserInfos(String userNo, String userTel) throws CustomException {

        //迁移个人用户信息
        remove2UserInfoHistory(userNo);
        remove2AccountInfoHistory(userNo, userTel);

    }

    /**
     * 删除个人用户录音信息
     *
     * @param userNo
     * @param userTel
     */
    protected void deleteUserVoices(String userNo, String userTel) {
        Example example = new Example(ShUserVoiceInfo.class);
        example.createCriteria().andEqualTo("accountno", userTel).andEqualTo("iuserno", userNo).andNotEqualTo("cpdelflg", 3);
        int deleteNum = shUserVoiceInfoMapper.deleteByExample(example);
        log.info("删除个人用户录音信息{}条,userNo={},userTel={}", deleteNum, userNo, userTel);

    }

    /**
     * 删除个人用户文件信息
     *
     * @param userNo
     * @param userTel
     */
    protected void deleteUserFiles(String userNo, String userTel) {
        Example example = new Example(ShUserFile.class);
        example.createCriteria().andEqualTo("pfAccountNo", userTel).andNotEqualTo("pfStatus", 3).andEqualTo("pfNotaryUserNo", userNo);
        int deleteNum = shUserFileMapper.deleteByExample(example);
        log.info("删除个人用户文件信息{}条,userNo={},userTel={}", deleteNum, userNo, userTel);
    }

    /**
     * 删除USER_INFO信息
     *
     * @param userNo
     */
    protected void deleteUserinfo(String userNo) {
        ShUserInfo record = new ShUserInfo();
        record.setUserno(userNo);
        int deleteNum = shUserInfoMapper.delete(record);
        log.info("删除USER_INFO信息{}条,userNo={}", deleteNum, userNo);

    }

    /**
     * 删除ACCOUNT_INFO信息
     *
     * @param userNo
     * @param userTel
     */
    protected void deleteAccountinfo(String userNo, String userTel) {
        ShAccountInfo record = new ShAccountInfo();
        record.setUserNo(userNo);
        record.setUserTel(userTel);
        int deleteNum = accountInfoMapper.delete(record);
        log.info("删除ACCOUNT_INFO信息信息{}条,userNo={},userTel={}", deleteNum, userNo, userTel);

    }

    /**
     * 用户录音信息迁移至历史
     *
     * @param userNo
     * @param userTel
     * @throws Exception
     */
    private void removceUserVoices2History(String userNo, String userTel) throws CustomException {
        ShUserVoiceInfo record = new ShUserVoiceInfo();
        record.setAccountno(userTel);
        record.setIuserno(userNo);
        List<ShUserVoiceInfo> list = shUserVoiceInfoMapper.select(record);
        for (ShUserVoiceInfo userVoiceInfo : list) {
            ShUserVoiceInfoHistory bean = new ShUserVoiceInfoHistory();
            try {
                PropertyUtils.copyProperties(bean, userVoiceInfo);
            } catch (Exception e) {
                throw new CustomException("Bean转换异常");
            }
            if (shUserVoiceInfoHistoryMapper.insert(bean) != 1)
                log.info("用户录音信息迁移至历史史表,迁移失败Recordno={}", bean.getRecordno());
        }
        deleteUserVoices(userNo, userTel);
    }

    /**
     * 用户录音文件信息迁移至历史表
     *
     * @param userNo
     * @param userTel
     * @throws Exception
     */
    private void removceUserFiles2History(String userNo, String userTel) throws CustomException {
        ShUserFile record = new ShUserFile();
        record.setPfNotaryUserNo(userTel);
        record.setPfAccountNo(userNo);
        List<ShUserFile> list = shUserFileMapper.select(record);
        for (ShUserFile userFile : list) {
            ShUserFileHistory bean = new ShUserFileHistory();
            try {
                PropertyUtils.copyProperties(bean, userFile);
            } catch (Exception e) {
                throw new CustomException("Bean转换异常");
            }
            if (shUserFileHistoryMapper.insert(bean) != 1)
                log.info("用户录音文件信息迁移至历史表,迁移失败PfAccountNo={}", bean.getPfAccountNo());
        }
        deleteUserFiles(userNo, userTel);
    }

    /**
     * 修改账户状态
     *
     * @param userNo 个人账号
     * @param userTel 省份
     * @throws Exception
     */
    protected void updateAccountInfo(String userNo, String userTel) throws CustomException {
        Example example = new Example(ShAccountInfo.class);
        ShAccountInfo record = new ShAccountInfo();
        record.setUserstatus(BizConstants.USERSTATUS_CANCEL);
        record.setCanceldatetime(new Date());
        example.createCriteria().andEqualTo("userTel", userTel).andEqualTo("userNo", userNo);
        accountInfoMapper.updateByExampleSelective(record, example);
        log.info("修改用户状态,userNo={},userTel={}", userNo, userTel);
    }

    /**
     * 修改用户状态
     *
     * @param userNo 个人账号
     * @throws Exception
     */
    protected void updateUserInfo(String userNo) throws CustomException {
        Example example = new Example(ShUserInfo.class);
        ShUserInfo record = new ShUserInfo();
        record.setOpenflag(BizConstants.USER_OPENFLG_NO);
        example.createCriteria().andEqualTo("userno", userNo);
        shUserInfoMapper.updateByExampleSelective(record, example);
        log.info("修改用户状态,userNo={}", userNo);
    }

    /**
     * 修改录音所有者
     */
    void updateVoivesOwner(String userNo, String userTel) {
        Example example = new Example(ShUserVoiceInfo.class);
        example.createCriteria().andEqualTo("accountno", userTel).andEqualTo("iuserno", userTel);
        List<ShUserVoiceInfo> list = shUserVoiceInfoMapper.selectByExample(example);
        for (ShUserVoiceInfo shUserVoiceInfo : list) {
            shUserVoiceInfo.setIuserno(userNo);
            shUserVoiceInfoMapper.insertSelective(shUserVoiceInfo);
        }
        shUserVoiceInfoMapper.deleteByExample(example);
        int updateNum = null == list ? 0 : list.size();
        log.info("上海个人转子账号用户录音信息迁移{}条,迁移userTel={}", updateNum, userTel);
    }

    /**
     * 修改文件所有者
     */
    void updateUserFilesOwner(String userNo, String userTel) {
        Example example = new Example(ShUserFile.class);
        example.createCriteria().andEqualTo("pfAccountNo", userTel).andEqualTo("pfNotaryUserNo", userTel);
        List<ShUserFile> list = shUserFileMapper.selectByExample(example);
        for (ShUserFile userFile : list) {
            userFile.setPfNotaryUserNo(userNo);
            shUserFileMapper.insertSelective(userFile);
        }
        shUserFileMapper.deleteByExample(example);
        int updateNum = null == list ? 0 : list.size();
        log.info("上海个人转子账号用户文件信息迁移{}条,迁移userTel={}", updateNum, userTel);
    }

    /**
     * 修改个人用户
     *
     * @param bizUserInfo
     */
    protected void updateAccountInfo(BizUserInfo bizUserInfo) {
        Example example = new Example(ShAccountInfo.class);
        ShAccountInfo record = new ShAccountInfo();
//    	record.setUserNo(bizUserInfo.getEntNo());
        record.setAccounttype(bizUserInfo.getAccountType());
//    	record.setAreacode();
        record.setCalledflag(bizUserInfo.getCalledflag());
        record.setCalledRectipflag(bizUserInfo.getCalledVoice());
        record.setCallerflag(bizUserInfo.getCallerflag());
//    	record.setCanceldatetime();
        record.setCitycode(bizUserInfo.getCityCode());
        record.setContactphone(bizUserInfo.getPhone());
//    	record.setDefaultpwdflag(BizConstants.getd);
        record.setEmail(bizUserInfo.getEmail());
        record.setIsrefund(bizUserInfo.getIsrefund());
//    	record.setOpendatetime(bizUserInfo.geto);
        record.setOpenSeq(getOpenSeq(bizUserInfo.getUserNo()));
        record.setOrgNo(bizUserInfo.getOrgNo());
        record.setPassword(bizUserInfo.getPasswd());
        record.setPhonetype(bizUserInfo.getPhonetype());
//    	record.setRecordno();
        record.setRectipflag(bizUserInfo.getCallerVoice());
        record.setRefundamount(bizUserInfo.getRefundamount());
        record.setRefundremark(bizUserInfo.getRefundremark());
        record.setRemark(bizUserInfo.getRemark());
        record.setRpcode(bizUserInfo.getRpcode());
        record.setSmsLogin(bizUserInfo.getSmsLogin());
        record.setTaocanid(bizUserInfo.getTcid());
        record.setUserstatus(bizUserInfo.getStatus());
//    	record.setUserTel(bizUserInfo.getUserNo());
        example.createCriteria().andEqualTo("userTel", bizUserInfo.getUserNo()).
                andEqualTo("userNo", bizUserInfo.getEntNo()).andEqualTo("userstatus", bizUserInfo.getStatus());
        accountInfoMapper.updateByExampleSelective(record, example);
        log.info("修改个人用户:USERNO={},USER_TEL={}", bizUserInfo.getEntNo(), bizUserInfo.getUserNo());

    }

    /**
     * 修改个人用户
     *
     * @param bizUserInfo
     */
    protected void updateUserInfo(BizUserInfo bizUserInfo) {
        Example example = new Example(ShUserInfo.class);
        ShUserInfo record = new ShUserInfo();
//    	record.setAccesscodeActive(BizConstants.ACCESSCODE_ACTIVE);
//    	record.setAddress(bizUserInfo.get);
//    	record.setAllowmaxstore(bizUserInfo.geta);
        record.setBusinesstype(bizUserInfo.getBusinesstype());
        record.setCalledflagBk(bizUserInfo.getCalledflag());
        record.setCallerflagBk(bizUserInfo.getCallerflag());
//    	record.setContactname(bizUserInfo.getco);
        record.setContactphone(bizUserInfo.getPhone());
//    	record.setCreatetime(bizUserInfo.getcr);
        record.setFax(bizUserInfo.getFax());
//    	record.setImportTime(bizUserInfo.geti);
        record.setIsignupsource(bizUserInfo.getInsource());
//    	record.setOpenmonmum();
        record.setOpenSeq(getOpenSeq(bizUserInfo.getUserNo()));
        record.setOrgNo(bizUserInfo.getOrgNo());
        record.setRectipflag(bizUserInfo.getCallerVoice());
        record.setRemark(bizUserInfo.getRemark());
//    	record.setSignupip(bizUserInfo.gets);
//    	record.setSignuptime(bizUserInfo.gets);
        record.setTaocanid(bizUserInfo.getTcid());
//    	record.setUsedingstore();
        record.setUsername(bizUserInfo.getUserNo());
        record.setUsertype(bizUserInfo.getUserType());
        //Boss的status为1:主账号;2:子账号，而userinfo表中0:否，1:是
        record.setOpenflag(bizUserInfo.getStatus() == BizConstants.USERSTATUS_NORMAL ? BizConstants.USERSTATUS_NORMAL : "0");


        example.createCriteria().andEqualTo("userno", bizUserInfo.getEntNo());
        shUserInfoMapper.updateByExampleSelective(record, example);
        log.info("修改个人用户:USER_TEL={}", bizUserInfo.getUserNo());
    }

    /**
     * 用户信息迁移至历史表
     *
     * @param userNo 个人账号
     * @throws Exception
     */
    private void remove2AccountInfoHistory(String userNo, String userTel) throws CustomException {
        ShAccountInfo record = new ShAccountInfo();
        record.setUserNo(userNo);
        record.setUserTel(userTel);
        List<ShAccountInfo> list = accountInfoMapper.select(record);
        for (ShAccountInfo accountInfo : list) {
            ShAccountInfoHistory bean = new ShAccountInfoHistory();
            try {
                PropertyUtils.copyProperties(bean, accountInfo);
            } catch (Exception e) {
                throw new CustomException("Bean转换异常");
            }
            if (accountInfoHistoryMapper.insert(bean) != 1)
                log.info("用户信息迁移至历史表AccountInfoHistory,迁移失败userTel={}", bean.getUserTel());
        }
        deleteAccountinfo(userNo, userTel);
    }

    /**
     * 用户信息迁移至历史表
     *
     * @param userNo 个人账号
     * @throws Exception
     */
    private void remove2UserInfoHistory(String userNo) throws CustomException {
        ShUserInfo record = new ShUserInfo();
        record.setUserno(userNo);
        List<ShUserInfo> list = shUserInfoMapper.select(record);
        for (ShUserInfo userInfo : list) {
            ShUserInfoHistory bean = new ShUserInfoHistory();
            try {
                PropertyUtils.copyProperties(bean, userInfo);
            } catch (Exception e) {
                throw new CustomException("Bean转换异常");
            }
            if (shUserInfoHistoryMapper.insert(bean) != 1)
                log.info("用户信息迁移至历史表UserInfoHistory,迁移失败userNo={}", bean.getUserno());
        }
        deleteUserinfo(userNo);
    }

    /**
     * 根据套餐获取套餐类型
     *
     * @param tcid
     * @return
     * @throws Exception
     */
    protected String selectTcType(Long tcid) throws CustomException {
        if (otherMapper.selectTcType(tcid) == null)
            throw new CustomException("用户套餐类型错误,tcid:" + tcid);
        return otherMapper.selectTcType(tcid).toString();
    }

    /**
     * 设置OPEN_SEQ
     *
     * @param userTel
     * @return
     */
    protected Long getOpenSeq(String userTel) {
        return otherMapper.getOpenSeq(userTel) == null ? 0 : Long.valueOf(otherMapper.getOpenSeq(userTel).toString()) + 1L;
    }

    /**
     * 新增一条个人用户信息 account_info
     *
     * @param bizUserInfo 业务用户
     */
    protected void insertAccountUser(BizUserInfo bizUserInfo) {
        ShAccountInfo record = new ShAccountInfo();
        //个人账户的话，设置userNo和userTel相同
        if (null != bizUserInfo.getUserType() && bizUserInfo.getUserType().equals(BizConstants.USER_TYPE_PERSONAL)
                && StringUtil.isEmpty(bizUserInfo.getEntNo()))
            record.setUserNo(bizUserInfo.getUserNo());
        else record.setUserNo(bizUserInfo.getEntNo());
        record.setAccounttype(bizUserInfo.getAccountType());
//    	record.setAreacode();
        record.setCalledflag(bizUserInfo.getCalledflag());
        record.setCalledRectipflag(bizUserInfo.getCalledVoice());
        record.setCallerflag(bizUserInfo.getCallerflag());
//    	record.setCanceldatetime();
        record.setCitycode(bizUserInfo.getCityCode());
        record.setContactphone(bizUserInfo.getPhone());
        record.setDefaultpwdflag(BizConstants.USER_PASSWORD_TIPS_NO);
        record.setEmail(bizUserInfo.getEmail());
        record.setIsrefund(bizUserInfo.getIsrefund());
        record.setOpendatetime(new Date());
        record.setOpenSeq(getOpenSeq(bizUserInfo.getUserNo()));
        record.setOrgNo(bizUserInfo.getOrgNo());
        record.setPassword(bizUserInfo.getPasswd());
        record.setPhonetype(bizUserInfo.getPhonetype());
//    	record.setRecordno();
        record.setRectipflag(bizUserInfo.getCallerVoice());
        record.setRefundamount(bizUserInfo.getRefundamount());
        record.setRefundremark(bizUserInfo.getRefundremark());
        record.setRemark(bizUserInfo.getRemark());
        record.setRpcode(bizUserInfo.getRpcode());
        record.setSmsLogin(bizUserInfo.getSmsLogin());
        record.setTaocanid(bizUserInfo.getTcid());
        record.setUserstatus(bizUserInfo.getStatus());
        record.setUserTel(bizUserInfo.getUserNo());

        int i = accountInfoMapper.insert(record);
        log.info("新增一条AccountInfo用户信息EntNo={},UserNo={},insert={}", bizUserInfo.getEntNo(), bizUserInfo.getUserNo(), i);
    }

    /**
     * 新增一条个人用户信息 user_info
     *
     * @param bizUserInfo 业务用户
     */
    protected void insertUserinfo(BizUserInfo bizUserInfo) {
        ShUserInfo record = new ShUserInfo();
        Date time = new Date();
        //个人账户的话，设置userNo和userTel相同
        if (null != bizUserInfo.getUserType() && bizUserInfo.getUserType().equals(BizConstants.USER_TYPE_PERSONAL)
                && StringUtil.isEmpty(bizUserInfo.getEntNo()))
            record.setUserno(bizUserInfo.getUserNo());
        else record.setUserno(bizUserInfo.getEntNo());
        record.setAccesscodeActive(BizConstants.ACCESSCODE_ACTIVE);
//    	record.setAddress(bizUserInfo.get);
//    	record.setAllowmaxstore(bizUserInfo.geta);
        record.setBusinesstype(bizUserInfo.getBusinesstype());
        record.setCalledflagBk(bizUserInfo.getCalledflag());
        record.setCallerflagBk(bizUserInfo.getCallerflag());
//    	record.setContactname(bizUserInfo.getco);
        record.setContactphone(bizUserInfo.getPhone());
        record.setCreatetime(time);
        record.setFax(bizUserInfo.getFax());
        record.setImportTime(time);
        record.setIsignupsource(StringUtil.isEmpty(bizUserInfo.getInsource()) ? String.valueOf(BizConstants.BLACKGROUD_OPEN) : bizUserInfo.getInsource());
        record.setOpenflag(BizConstants.USER_OPENFLG_OK);
//    	record.setOpenmonmum();
        record.setOpenSeq(getOpenSeq(bizUserInfo.getUserNo()));
        record.setOrgNo(bizUserInfo.getOrgNo());
        record.setRectipflag(bizUserInfo.getCallerVoice());
        record.setRemark(bizUserInfo.getRemark());
//    	record.setSignupip(bizUserInfo.gets);
        record.setSignuptime(time);
        record.setTaocanid(bizUserInfo.getTcid());
//    	record.setUsedingstore();
        record.setUsername(bizUserInfo.getUserNo());
        record.setUsertype(bizUserInfo.getUserType());

        int i = shUserInfoMapper.insert(record);
        log.info("新增一条Userinfo用户信息EntNo={},UserNo={},insert={}", bizUserInfo.getEntNo(), bizUserInfo.getUserNo(), i);
    }
}
