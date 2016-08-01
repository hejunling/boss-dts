package com.ancun.boss.business.service.bizuser.impl;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.constant.ImportFailedConstant;
import com.ancun.boss.business.persistence.mapper.*;
import com.ancun.boss.business.persistence.module.*;
import com.ancun.boss.business.pojo.allprovince.ProvinceServiceInput;
import com.ancun.boss.business.pojo.bizuser.*;
import com.ancun.boss.business.service.allprovince.IProvinceService;
import com.ancun.boss.business.service.allprovince.IPublicProvinceService;
import com.ancun.boss.business.service.bizopenuser.IBizOpenUserService;
import com.ancun.boss.business.service.bizuser.IBizEntSubAccountService;
import com.ancun.boss.business.service.dataDic.IQueryDictionaryService;
import com.ancun.boss.business.service.dataDic.IUserTaocanService;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.service.auth.impl.AuthenticationUtil;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.common.biz.pojo.common.RegionAreaInfo;
import com.ancun.common.biz.regionarea.RegionAreaInfoBiz;
import com.ancun.core.exception.EduException;
import com.ancun.core.page.Page;
import com.ancun.utils.DateUtil;
import com.ancun.utils.PhoneCallCheck;
import com.ancun.utils.StringUtil;
import com.google.common.collect.HashBasedTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 企业用户子账号相关service
 * User: zkai
 * Date: 2016/4/25
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 * Time: 21:24
 */

@Service
public class BizEntSubAccountServiceImpl implements IBizEntSubAccountService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BizEntSubAccountServiceImpl.class);

    @Resource
    private AuthenticationUtil authUtil;

    @Resource
    private BizUserInfoMapper bizUserInfoMapper;

    @Resource
    private IBizOpenUserService bizOpenUserService;

    @Resource
    private BizComboInfoMapper comboMapper;

    @Resource
    private BizUserLifeCircleMapper bizUserLifeMapper;

    @Resource
    private BizUserInfoQueryMapper bizUserQueryMapper;

    @Resource
    private BizUserInfoImportFailedMapper bizUserInfoImportFailedMapper;

    @Resource
    private DataSourceTransactionManager txManager;

    @Resource
    private IPublicProvinceService publicProvinceService;

    @Resource
    private BaseDataServiceImpl baseDataService;

    @Resource
    private IQueryDictionaryService queryDictionaryService;

    @Resource
    private IUserTaocanService userTaocanService;

    @Resource
    private RegionAreaInfoBiz regionAreaInfoBiz;



    /**
     * 企业用户添加子账号
     *
     * @param input
     * @return
     * @throws EduException
     */
    public BizEntAddSubAccountOutPut bizEntAddSubAccount(BizEntAddSubAccountInput input){

        String rpcode = null; // 省份信息
        String cityCode = null; // 城市信息
        String phoneType = null; // 号码类型

        BizEntAddSubAccountOutPut outPut = new BizEntAddSubAccountOutPut();
        StringBuffer failAccounts = new StringBuffer(); // 添加失败子账号:原因 ";"分割
        StrBuilder successAccounts = new StrBuilder(); // 添加成功账号，用";" 分割
        successAccounts.append("成功账号：");
        Boolean authBoolean = true ; // 鉴权成功：失败

        List<BizUserInfo> bizUserInfoList = null ; //用户列表
        BizUserInfo bizUserInfo = null ; // 用户信息
        String userState = null; // 用户状态
        String userType = null; // 用户类型(1:个人;2:企业)
        String accountType = null; // 账号类型(1:主账号;2:子账号;3:个人账号)


        String entno = input.getEntno(); // 主账号
        String bizno = input.getBizno(); // 业务编号
        Long taocanid = input.getTaocanid(); // 套餐编号
        String accounts = input.getAccounts(); // 子账号开通列表
        String typecode = input.getTypecode(); // 运营商类型
        String[] accountArrs = accounts.split(",");

        //  错误信息对象
        BizUserInfoImportFailed errorInfo = new BizUserInfoImportFailed();
        errorInfo.setEntNo(entno);
        errorInfo.setUserType(Constant.USER_TYPE_ENT);
        errorInfo.setAccountType(Constant.ACCOUNT_TYPE_SUB);
        errorInfo.setBizNo(bizno);
        errorInfo.setTcid(taocanid);
        errorInfo.setIntime(input.getOpendatetime());
        errorInfo.setInsource(Constant.INSOURCE_8);

        BizUserInfo entBizUserInfo = null; // 查询到的企业用户信息
        try{
            entBizUserInfo = vailEntUserInfo(input);
        }catch (Exception e){
            errorInfo.setRemark(entno + ":账号异常："+baseDataService.getBussinessMessage(e.getMessage())+";");
            errorInfo.setUserNo(entno);
            inserErrorInfo(errorInfo);
            failAccounts.append(entno + ":账号异常："+baseDataService.getBussinessMessage(e.getMessage())+";");
            outPut.setFailaccounts(entno + ":账号异常："+baseDataService.getBussinessMessage(e.getMessage())+";");
            return outPut;
        }

        if(!StringUtils.equals(String.valueOf(entBizUserInfo.getBizNo()),String.valueOf(bizno))){
            errorInfo.setRemark(bizno + ":业务编号异常，输入套餐和企业业务编号不一致;");
            errorInfo.setUserNo(entno);
            inserErrorInfo(errorInfo);
            failAccounts.append(bizno + ":业务编号异常，输入套餐和企业业务编号不一致;");
            outPut.setFailaccounts(bizno + ":业务编号异常，输入套餐和企业业务编号不一致;");
            return outPut;
        }

        // -------------套餐类型取得-------------
        BizComboInfoExample comboExample = new BizComboInfoExample();
        BizComboInfoExample.Criteria c = comboExample.createCriteria();
        c.andTcidEqualTo(taocanid);
        c.andBizNoEqualTo(bizno);

        List<BizComboInfo> comboList = comboMapper.selectByExample(comboExample);

        if (comboList.size() != 1) {
            errorInfo.setRemark(taocanid + ":该套餐类型找不到;");
            errorInfo.setUserNo(entno);
            inserErrorInfo(errorInfo);
            failAccounts.append(taocanid + ":该套餐类型找不到;");
            outPut.setFailaccounts(taocanid + ":该套餐类型找不到;");
            return outPut;
        }
        String tcType = comboList.get(0).getType();
        CallerInfo callerInfo = new CallerInfo();
        if (BizRequestConstant.TC_TYPE_1.equals(tcType)) {
            // 主叫提示音
            callerInfo.setCallerVoice(StringUtil.isEmpty(input.getCallervoice())?BizRequestConstant.RECTIP_FLAG_NO : input
                    .getCallervoice());
            // 被叫提示音
            callerInfo.setCalledVoice(BizRequestConstant.RECTIP_FLAG_NO);
            // 主叫录音默认开启
            callerInfo.setCallerRecord(BizRequestConstant.MARK_YES);
            // 被叫录音默认关闭
            callerInfo.setCalledRecord(BizRequestConstant.MARK_NO);
        } else if (BizRequestConstant.TC_TYPE_2.equals(tcType)) {
            // 主叫提示音
            callerInfo.setCallerVoice(StringUtil.isEmpty(input.getCallervoice())?BizRequestConstant.RECTIP_FLAG_NO : input
                    .getCallervoice());
            // 被叫提示音
            callerInfo.setCalledVoice(StringUtil.isEmpty(input.getCalledvoice())?BizRequestConstant.RECTIP_FLAG_NO : input
                    .getCallervoice());
            // 主叫录音默认开启
            callerInfo.setCallerRecord(BizRequestConstant.MARK_YES);
            // 被叫录音默认关闭
            callerInfo.setCalledRecord(BizRequestConstant.MARK_YES);
        }

        // 循环子账号
        if (accountArrs != null && accountArrs.length > 0) {
            for (String account : accountArrs) {

                errorInfo.setUserNo(account);
                errorInfo.setPhone(account);

                // 号码格式是否合法
                if (!PhoneCallCheck.checkCompany(account)) {
                    errorInfo.setRemark(account + ":该子帐号格式不合法;");
                    inserErrorInfo(errorInfo);
                    failAccounts.append(account + ":该子帐号格式不合法;");
                    continue;
                }

                // 鉴权
                try{
                    authBoolean =  authUtil.validCheck(account, input.getRpcode(), typecode,bizno);
                }catch (Exception e){
                    log.error("子账号鉴权失败：",e);
                    errorInfo.setRemark(account + " 该子帐号鉴权失败："+baseDataService.getBussinessMessage(e.getMessage())+";");
                    inserErrorInfo(errorInfo);
                     failAccounts.append(account + " 该子帐号鉴权失败："+baseDataService.getBussinessMessage(e.getMessage())+";");
                    continue;
                }

                //判断号码状态，固话、手机
                if(PhoneCallCheck.checkMobile(account)){
                    //给用户信息对象组装上号码类别（手机）
                    phoneType = BizRequestConstant.USER_PHONETYPE_PHONE;
                }else{
                    //给用户组装号码类别信息（固话）
                    phoneType = BizRequestConstant.USER_PHONETYPE_TEL;
                }

                // 取得号码省份信息
                try {
                    //根据用户号码获取号码归属地信息Model，并给用户组装上省份编号、城市编号、区号等信息
                    RegionAreaInfo regionAreaInfo = getRegionAreaInfo(account);

                    if (regionAreaInfo != null) {
                        rpcode = regionAreaInfo.getProvinceCode();
                        cityCode = regionAreaInfo.getCityCode();
                    }
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }

                // 鉴权成功
                if(authBoolean){

                    BizUserInfoExample userExample = new BizUserInfoExample();
                    BizUserInfoExample.Criteria userCriteria = userExample.createCriteria();
                    userCriteria.andUserNoEqualTo(account);
                    userCriteria.andBizNoEqualTo(bizno);

                    bizUserInfoList= bizUserInfoMapper.selectByExample(userExample);

                    // 用户数据异常
                    if (bizUserInfoList != null && bizUserInfoList.size() > 1) {
                        failAccounts.append(account + ":用户存在且数量不为一;");
                    }
                    // 用户存在且数量为1
                    else if(bizUserInfoList.size() == 1){
                        bizUserInfo = bizUserInfoList.get(0);

                        // 判断状态是否正常
                        userState = bizUserInfo.getStatus();

                        // 如果用户状态为开通
                        if(StringUtils.equals(userState,Constant.USER_STATUS_1) ){
                            userType = bizUserInfo.getUserType();

                            // 如果用户类型为个人用户，做个人用户转企业用户操作
                            if(StringUtils.equals(userType,Constant.USER_TYPE_PER)){


                                // 调用远程套餐修改接口
                                if(!postChangeRequest( account, input.getRpcode(), input.getTypecode(), "3", bizUserInfo.getTcid(),
                                            input.getTaocanid(),input.getOpendatetime(),tcType, false)){
                                    errorInfo.setRemark("个人用户："+account+"转企业用户:"+account+" 失败，调用远程接口退订失败;");
                                    inserErrorInfo(errorInfo);
                                    failAccounts.append("个人用户："+account+"转企业用户:"+account+" 失败，调用远程接口退订失败;");
                                    continue;
                                }

                                // 个人用户转企业用户操作
                                try{
                                    personToEntSubAccount(account,input,callerInfo,rpcode,cityCode,phoneType);
                                    String remark = "个人用户："+account+"转企业用户:"+account+"企业号为："+bizno+";";
                                    addLifeCircle(input,account,callerInfo,remark,rpcode,cityCode,phoneType);
                                    successAccounts.append(account+";");
                                }catch (Exception e){
                                    log.error("个人用户转企业异常：",e);
                                    errorInfo.setRemark("个人用户："+account+"转企业用户:"+account+" 失败；");
                                    inserErrorInfo(errorInfo);
                                    failAccounts.append("个人用户："+account+"转企业用户:"+account+" 失败；");
                                }


                            }

                            // 如果是企业用户，判断用户类型是否是账号
                            if(StringUtils.equals(userType,Constant.USER_TYPE_ENT)){
                                accountType = bizUserInfo.getAccountType();

                                // 主账号
                                if(StringUtils.equals(accountType,Constant.ACCOUNT_TYPE_MAIN)){

                                    errorInfo.setRemark(account + ":该帐号已存为企业主帐号;");
                                    errorInfo.setPhone(account);
                                    inserErrorInfo(errorInfo);
                                    failAccounts.append(account + ":该帐号已存为企业主帐号;");
                                }else {

                                    errorInfo.setRemark(account + ":该帐号已存为企业子帐号;;");
                                    errorInfo.setPhone(account);
                                    inserErrorInfo(errorInfo);
                                    failAccounts.append(account + ":该帐号已存为企业子帐号;");
                                }
                            }
                        }else {
                            // 用户状态为：退订，停用，体验 重新开通用户（修改用户状态 status）

                            // 调用远程开通接口
                            try{
                                openService(account, input.getRpcode(),typecode, String.valueOf(taocanid), input.getOpendatetime());
                            }catch (Exception e){
                                errorInfo.setRemark("用户："+account+" 重新开通失败，调用远程开通接口失败;");
                                inserErrorInfo(errorInfo);
                                failAccounts.append("用户："+account+" 重新开通失败，调用远程开通接口失败;");
                                continue;
                            }

                            // 重新开通
                            try{
                                reOpeningUser(account,input,callerInfo,rpcode,cityCode,phoneType);
                                String remark = "用户："+account+"重新开通;";
                                // 生命周期表
                                addLifeCircle(input,account,callerInfo,remark,rpcode,cityCode,phoneType);
                                successAccounts.append(account+";");
                            }catch (Exception e){
                                log.error("用户："+account+"重新开通失败;",e);
                                errorInfo.setRemark("用户："+account+"重新开通失败；");
                                inserErrorInfo(errorInfo);
                                failAccounts.append("用户："+account+"重新开通失败；");
                            }
                        }

                    }else {
                        // 添加数据库

                         // 调用远程开通接口
                        try{
                            openService(account, input.getRpcode(),typecode, String.valueOf(taocanid), input.getOpendatetime());
                        }catch (Exception e){
                            errorInfo.setRemark("用户："+account+" 开通失败，调用远程开通接口失败;");
                            inserErrorInfo(errorInfo);
                            failAccounts.append("用户："+account+" 开通失败，调用远程开通接口失败;");
                            continue;
                        }

                        try {
                            // 添加用户信息表
                            insertBizuserInfo(input,account,callerInfo,rpcode,cityCode,phoneType);

                            // 用户生命周期表
                            String remark = "企业用户:"+entno+"开通子账号："+account;
                            addLifeCircle(input,account,callerInfo,remark,rpcode,cityCode,phoneType);

                            // 插入查询表
                            addQuery(input,account);

                            successAccounts.append(account+";");
                        } catch (Exception e) {
                            log.error("插入失败",e);
                            errorInfo.setRemark(account+" 插入失败："+baseDataService.getBussinessMessage(e.getMessage()));
                            errorInfo.setPhone(account);
                            inserErrorInfo(errorInfo);
                            failAccounts.append(account + "插入失败:"+baseDataService.getBussinessMessage(e.getMessage()));
                        }

                    }

                }else {
                    errorInfo.setRemark(account + ":该账号鉴权失败;");
                    inserErrorInfo(errorInfo);
                    failAccounts.append(account + "该账号鉴权失败;");
                }


            }
        }
        outPut.setFailaccounts(String.valueOf(failAccounts));
        outPut.setSuccessaccounts(String.valueOf(successAccounts));
        return outPut;
    }

    /**
     * 用户开通（添加用户信息表）
     * @param input
     * @param account 号码
     * @param callerInfo
     * @param cityCode 区号
     * @param phoneType 号码类别（手机，固话）
     * @param rpcode 省份信息
     */
    // update by zkai on 2016/05/05 传入参数修改：添加 rpcode，cityCode，phoneType
    private void insertBizuserInfo(BizEntAddSubAccountInput input,String account,CallerInfo callerInfo,String rpcode,String cityCode,String phoneType){

        BizUserInfo bizUserInfo = new BizUserInfo();

        try {
            bizUserInfo.setUserNo(account);
            bizUserInfo.setEntNo(input.getEntno());
            bizUserInfo.setBizNo(input.getBizno());

            /**
             * update by zkai on 2016/05/05
             * 不调用归属地服务改为参数传入（多次调用归属第服务影响性能）
             */

            // 原逻辑
             //-------------调用归属地服务---------
//            bizOpenUserService.getUserCodeInfo(bizUserInfo, input.getRpcode());

            // 现逻辑
            bizUserInfo.setCityCode(cityCode);
            bizUserInfo.setPhonetype(phoneType);
            bizUserInfo.setRpcode(rpcode);

            //密码
            bizUserInfo.setPasswd("");
            //套餐id
            bizUserInfo.setTcid(input.getTaocanid());
            //开通时间
            bizUserInfo.setIntime(input.getOpendatetime());

            // IVR录音提示标志(0:否; 1:是)
            bizUserInfo.setRectip(Constant.RECTIP_TYPE_0);

            //用户类型
            bizUserInfo.setUserType(Constant.USER_TYPE_ENT);
            //状态
            bizUserInfo.setStatus(BizRequestConstant.OPENED_USER);

            // 企业后台开通
            bizUserInfo.setInsource(Constant.INSOURCE_8);

            // 用户类型为子账号
            bizUserInfo.setAccountType(BizRequestConstant.SUB_ACCOUNT);

            // 联系电话
            bizUserInfo.setPhone(account);

            bizUserInfo.setCallerVoice(callerInfo.getCallerVoice());
            bizUserInfo.setCalledVoice(callerInfo.getCalledVoice());
            bizUserInfo.setCallerRecord(callerInfo.getCallerRecord());
            bizUserInfo.setCalledRecord(callerInfo.getCalledRecord());
            bizUserInfo.setFullpackage("true");

            bizUserInfo.setDefaultpwdflag(BizRequestConstant.DEFAULT_PWD_FLAG_N);

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus transactionStatus = txManager.getTransaction(def);
            try {
                bizUserInfoMapper.insert(bizUserInfo);
                txManager.commit(transactionStatus);
            }catch (Exception e){

                txManager.rollback(transactionStatus);
            }

        } catch (Exception e) {
            log.error("用户开通异常",e);
            // 用户开通失败
            throw new EduException(MessageConstant.BIZUSER_OPEN_FAILED);
        }
    }


    /**
     * 插入用户周期
     * @param input
     * @param account 号码
     * @param callerInfo
     * @param cityCode 区号
     * @param phoneType 号码类别（手机，固话）
     * @param rpcode 省份信息
     */
    // update by zkai on 2016/05/05 传入参数修改：添加 rpcode，cityCode，phoneType
    private void addLifeCircle(BizEntAddSubAccountInput input,String account,CallerInfo callerInfo,String remark,String rpcode,String cityCode,String phoneType) {

        BizUserLifeCircle circle = new BizUserLifeCircle();

        // 事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = txManager.getTransaction(def);

        try {
            circle.setUserNo(account);
            circle.setUserType(Constant.USER_TYPE_ENT);
            circle.setEntNo(input.getEntno());

            circle.setAccountType(BizRequestConstant.SUB_ACCOUNT);

            circle.setBizNo(input.getBizno());
            circle.setTcid(input.getTaocanid());
            circle.setSource(Constant.INSOURCE_8);

            /**
             * update by zkai on 2016/05/05
             * 不调用归属地服务改为参数传入（多次调用归属第服务影响性能）
             */

            // 原逻辑
             //-------------调用归属地服务---------
//            bizOpenUserService.getUserCodeInfo4Circle(circle, input.getRpcode());

            // 现逻辑
            circle.setCityCode(cityCode);
            circle.setPhonetype(phoneType);
            circle.setRpcode(rpcode);


            circle.setOpenCancel(BizRequestConstant.OPEN_STATUS);
            circle.setPhone(account);
            circle.setRectip(Constant.RECTIP_TYPE_0);
            circle.setSource(Constant.INSOURCE_8);

            circle.setTimers(input.getOpendatetime());
            circle.setUpdateTime(input.getOpendatetime());


            circle.setCallerVoice(callerInfo.getCallerVoice());
            circle.setCalledVoice(callerInfo.getCalledVoice());
            circle.setCallerRecord(callerInfo.getCallerRecord());
            circle.setCalledRecord(callerInfo.getCalledRecord());
            circle.setRemark(remark);

            bizUserLifeMapper.insert(circle);
            txManager.commit(transactionStatus);
        } catch  (Exception e) {
            log.error("插入用户周期异常",e);
            txManager.rollback(transactionStatus);
            throw new EduException(ImportFailedConstant.INSERT_BIZ_LIFE_FAILED,e);
        }

    }

    /**
     * 插入查询表
     *
     * @param input
     * @return
     */
    private void addQuery(BizEntAddSubAccountInput input,String account){

        // 插入用户查询表
        BizUserInfoQuery query = new BizUserInfoQuery();

        // 事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = txManager.getTransaction(def);
        try{

            query.setUserNo(account);
            query.setUpdateTime(input.getOpendatetime());
            query.setBizNo(input.getBizno());
            query.setStatus(BizRequestConstant.OPEN_STATUS);


            bizUserQueryMapper.insert(query);
            txManager.commit(transactionStatus);
        } catch(Exception e){
            log.error("插入用户查询表",e);
            txManager.rollback(transactionStatus);
            throw new EduException(ImportFailedConstant.INSERT_BIZ_QUERY_FAILED);
        }
    }

    /**
     * 用户导入错误表中：插入失败信息
     */
    private void inserErrorInfo(BizUserInfoImportFailed info){
        bizUserInfoImportFailedMapper.insert(info);
    }


    /**
     * 个人用户转企业用户子账号
     * @param account 号码
     * @param input
     * @param callerInfo 主被叫信息
     * @param rpcode 省份信息
     * @param cityCode 城市信息
     * @param phoneType 电话类型
     */
    // update by zkai on 2016/05/05 传入参数修改：添加 rpcode，cityCode，phoneType
    private void personToEntSubAccount(String account,BizEntAddSubAccountInput input,CallerInfo callerInfo,String rpcode,String cityCode,String phoneType){
        DefaultTransactionDefinition Def = new DefaultTransactionDefinition();
        Def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = txManager.getTransaction(Def);
        try{
            BizUserInfoExample example = new BizUserInfoExample();
            BizUserInfoExample.Criteria criteria = example.createCriteria();
            criteria.andUserNoEqualTo(account);
            criteria.andBizNoEqualTo(input.getBizno());

            BizUserInfo bizUserInfo = new BizUserInfo();
            bizUserInfo.setPasswd("");
            bizUserInfo.setUserType(Constant.USER_TYPE_ENT);
            bizUserInfo.setEntNo(input.getEntno());
            bizUserInfo.setTcid(input.getTaocanid());
            bizUserInfo.setAccountType(Constant.ACCOUNT_TYPE_SUB);
            bizUserInfo.setCalledRecord(callerInfo.getCalledRecord());
            bizUserInfo.setCallerRecord(callerInfo.getCallerRecord());
            bizUserInfo.setCalledVoice(callerInfo.getCalledVoice());
            bizUserInfo.setCallerVoice(callerInfo.getCalledVoice());
            bizUserInfo.setOfftime(null);
            bizUserInfo.setFullpackage("true");

            /**
             * update by zkai on 2016/05/05
             * 不调用归属地服务改为参数传入（多次调用归属第服务影响性能）
             */

           // 原逻辑
//            //-------------调用归属地服务---------
//            // 调用归属地服务是加入userno
//            bizOpenUserService.getUserCodeInfo(bizUserInfo, input.getRpcode());

            // 现逻辑
            bizUserInfo.setCityCode(cityCode);
            bizUserInfo.setPhonetype(phoneType);
            bizUserInfo.setRpcode(rpcode);


            bizUserInfo.setOperation(Constant.OPERATION_TYPE_1);
            bizUserInfo.setFullpackage("true");

            // 修改所userno设置为空，分库字段不能修改
            bizUserInfo.setUserNo(null);
            bizUserInfoMapper.updateByExampleSelective(bizUserInfo,example);
            txManager.commit(transactionStatus);
        }catch (Exception e){
            log.error("个人用户转企业用户子账号异常：",e);
            txManager.rollback(transactionStatus);
            throw new EduException(e);
        }

    }


    /**
     * 用户状态为：退订，停用，体验 重新开通用户（修改用户状态 status）
     * @param account 号码
     * @param input
     * @param callerInfo 主被叫信息
     * @param rpcode 省份信息
     * @param cityCode 城市信息
     * @param phoneType 电话类型
     */
    // update by zkai on 2016/05/05 传入参数修改：添加 rpcode，cityCode，phoneType
    private void reOpeningUser(String account,BizEntAddSubAccountInput input,CallerInfo callerInfo,String rpcode,String cityCode,String phoneType){

        // 事务
        DefaultTransactionDefinition Def = new DefaultTransactionDefinition();
        Def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = txManager.getTransaction(Def);

        try {
            BizUserInfoExample example = new BizUserInfoExample();
            BizUserInfoExample.Criteria criteria = example.createCriteria();
            criteria.andUserNoEqualTo(account);
            criteria.andBizNoEqualTo(input.getBizno());

            BizUserInfo bizUserInfo = new BizUserInfo();
            bizUserInfo.setPasswd("");
            bizUserInfo.setUserType(Constant.USER_TYPE_ENT);
            bizUserInfo.setAccountType(Constant.ACCOUNT_TYPE_SUB);
            bizUserInfo.setEntNo(input.getEntno());
            bizUserInfo.setTcid(input.getTaocanid());
            bizUserInfo.setCalledRecord(callerInfo.getCalledRecord());
            bizUserInfo.setCallerRecord(callerInfo.getCallerRecord());
            bizUserInfo.setCalledVoice(callerInfo.getCalledVoice());
            bizUserInfo.setCallerVoice(callerInfo.getCalledVoice());
            bizUserInfo.setStatus(Constant.TAOCAN_STATUS_1);
            bizUserInfo.setIntime(input.getOpendatetime());
            bizUserInfo.setFullpackage("true");


            /**
             * update by zkai on 2016/05/05
             * 不调用归属地服务改为参数传入（多次调用归属第服务影响性能）
             */

            // 原逻辑
//            //-------------调用归属地服务---------
//            // 调用归属地服务是加入userno
//            bizUserInfo.setUserNo(account);
//            bizOpenUserService.getUserCodeInfo(bizUserInfo, input.getRpcode());

            // 现逻辑
            bizUserInfo.setCityCode(cityCode);
            bizUserInfo.setPhonetype(phoneType);
            bizUserInfo.setRpcode(rpcode);

            bizUserInfo.setOperation(Constant.OPERATION_TYPE_0);
            bizUserInfo.setFullpackage("true");

            // 修改所userno设置为空，分库字段不能修改
            bizUserInfo.setUserNo(null);
            bizUserInfoMapper.updateByExampleSelective(bizUserInfo,example);
            txManager.commit(transactionStatus);
        }catch (Exception e){
            log.error(" 用户状态为：退订，停用，体验 重新开通用户异常：",e);
            txManager.rollback(transactionStatus);
            throw new EduException(e);
        }

    }

    /**
     * 验证企业用户信息
     * @param input
     * @return
     */
    public BizUserInfo vailEntUserInfo(BizEntAddSubAccountInput input){
        BizUserInfoExample bizUserInfoExample = new BizUserInfoExample();
        BizUserInfoExample.Criteria criteria = bizUserInfoExample.createCriteria();
        criteria.andEntNoEqualTo(input.getEntno()); // 企业账号
        criteria.andBizNoEqualTo(input.getBizno()); // 业务编号
        criteria.andStatusEqualTo(Constant.USER_STATUS_1); // 状态开通
        criteria.andAccountTypeEqualTo(Constant.ACCOUNT_TYPE_MAIN); // 主账号
        criteria.andUserTypeEqualTo(Constant.USER_TYPE_ENT); // 企业用户
        List<BizUserInfo> bizUserInfoList = bizUserInfoMapper.selectByExample(bizUserInfoExample);
        if(bizUserInfoList.size() <= 0){
            throw  new EduException(MessageConstant.BIZ_USER_NOT_EXISTS);
        }
        else if(bizUserInfoList.size() != 1){
            throw  new EduException(MessageConstant.BIZ_ENT_USER_IS_NOT_ONLY);
        }

        return bizUserInfoList.get(0);
    }

    /**
     * 调用远程服务
     * 个人用户转企业用户套餐修改
     * @param userno 用户编号
     * @param rpcode 省份编号
     * @param typecode  LT,DX
     * @param changeTcType 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
     * @param oldTcID 老套餐
     * @param newTcID 新套餐
     * @param openTime 开通时间
     * @param type 套餐类型（1:主叫录音;2:双向录音;3:接入号录音）
     * @param rollback 是否回滚（true：回滚 ，false：不回滚）
     * @return
     */
    private boolean postChangeRequest(String userno, String rpcode, String typecode, String changeTcType, Long oldTcID, Long newTcID, Date openTime, String type, boolean rollback){
        boolean bool = true;
        try {
            // 调用远程退订接口
            HashBasedTable<String, String, IProvinceService> table = publicProvinceService.getProvinceService();
            IProvinceService ftpService =  table.get(rpcode, typecode);
            if (ftpService != null) {
                ProvinceServiceInput provinceInput = new ProvinceServiceInput();
                provinceInput.setUserno(userno); // 用户编号
                provinceInput.setUserTel(userno); // 联系电话
                provinceInput.setChangeTcType(changeTcType); // 1:个人版、2:企业版、3：个人转企业
                provinceInput.setOldTc(String.valueOf(oldTcID)); // 老套餐
                provinceInput.setNewTc(String.valueOf(newTcID)); // 新套餐
                provinceInput.setRollback(rollback); // 是否回滚（默认false，不用设值）

                SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
                String time=dateformat1.format(openTime);
                provinceInput.setOpenTime(DateUtil.convertStringToDate(time)); // 开通时间

                provinceInput.setType(type); // 套餐类型（通过传入套餐编号取得，接入号不会调用远程接口）
                bool = ftpService.postChangeRequest(provinceInput);
            }

        } catch(Exception e) {
            log.error("调用远程套餐修改服务异常",e);
            bool = false;
        }
        return bool;
    }

    /**
     * 调用远程服务
     * 用户开通远程服务
     * @param userno
     * @param rpcode
     * @return
     */
    private String openService(String userno, String rpcode, String typecode, String tcid, Date openTime){
        try {
            // 调用远程开通接口
            HashBasedTable<String, String, IProvinceService> table = publicProvinceService.getProvinceService();
            IProvinceService ftpService =  table.get(rpcode, typecode);
            if (ftpService != null) {
                ProvinceServiceInput provinceInput = new ProvinceServiceInput();
                provinceInput.setUserno(userno);
                provinceInput.setTcID(tcid);
                provinceInput.setUserType(BizRequestConstant.PERSONAL_USER);
                provinceInput.setOpenTime(openTime);
                ftpService.open(provinceInput);
            }

        } catch(Exception e) {
            log.error("调用远程开通服务异常",e);
            return ImportFailedConstant.BIZ_USER_INTERFACE_ERROR;
        }
        return null;
    }


    /**
     * 企业用户子账号列表
     */
    public BizEntSubAccountListOutPut bizEntSubAccountList(BizEntSubAccountListInput input){
        BizUserInfoExample example = new BizUserInfoExample();
        BizUserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andBizNoEqualTo(input.getBizno());
        criteria.andEntNoEqualTo(input.getEntno());
        criteria.andAccountTypeEqualTo(Constant.ACCOUNT_TYPE_SUB);
        // 分页信息
        Page page = new Page();
        if (StringUtil.isNotBlank(input.getCurrentpage())) {
            page.setCurrentpage(Integer.valueOf(input.getCurrentpage()));
            page.setPagesize(Integer.valueOf(input.getPagesize()));
            example.setPage(page);
        }

        example.setOrderByClause("INTIME  DESC ");
        List<BizUserInfo> bizUserInfoList = bizUserInfoMapper.selectByExample(example);

        HashMap<String, String> dicMap = queryDictionaryService.getDicAsmap();

        // 获取套餐信息
        HashMap<String, String> taocaoMap = userTaocanService.getTaocanAsmap();

        List<BizUserInfoOutput> bizUserOutputList = new ArrayList<BizUserInfoOutput>();

        for (BizUserInfo info: bizUserInfoList
             ) {
            BizUserInfoOutput bizUserInfoOutput = new BizUserInfoOutput();
            bizUserInfoOutput.setUsernum(info.getUserNo());

            // 用户状态
            bizUserInfoOutput.setUserstatus(Constant.USER_STATUS_MAP.get(info.getStatus()));

            // 开通类型 Modele_value获取
            bizUserInfoOutput.setIsignupsource(dicMap.get(Constant.OPENTYPE + "_" + info.getInsource()) + "");

            bizUserInfoOutput.setOpentime(info.getIntime());
            bizUserInfoOutput
                    .setCanceltime(info.getOfftime());
            // 省份
            bizUserInfoOutput.setRpname(dicMap.get(Constant.PROVINCES + "_" + info.getRpcode()));

            bizUserInfoOutput.setTaocanname(taocaoMap.get(info.getTcid().toString()));// 套餐名称

            bizUserInfoOutput.setRpcode(info.getRpcode()); // 省份编号
            bizUserInfoOutput.setTaocantype(taocaoMap.get(info.getTcid().toString()+"tcType")); // 套餐类型
            bizUserInfoOutput.setPhone(info.getPhone()); // 电话号码

            bizUserInfoOutput.setId(String.valueOf(info.getId())); // 主键
            bizUserInfoOutput.setEntno(info.getEntNo()); // 企业编号
            bizUserInfoOutput.setUsertype(info.getUserType()); // 用户类型(1:个人;2:企业)
            bizUserInfoOutput.setAccounttype(info.getAccountType()); // 账号类型(1:主账号;2:子账号;3:个人账号)
            bizUserInfoOutput.setBizno(info.getBizNo()); // 业务编号
            bizUserOutputList.add(bizUserInfoOutput);

        }

        BizEntSubAccountListOutPut out = new BizEntSubAccountListOutPut();
        out.setBizEntSubAccountList(bizUserOutputList);
        out.setPageinfo(page);
        return out;
    }

    /**
     * 根据主叫号码取得归属地信息
     *
     * @param callfrom
     * @return
     */
    public RegionAreaInfo getRegionAreaInfo(String callfrom) {
        return regionAreaInfoBiz.getRegionAreaInfo(callfrom);
    }


    /**
     * 主被叫对象
     */
    public class CallerInfo{
        private String  callerVoice; // 主叫提示音(0:关闭;1:开启)
        private String  calledVoice; // 被叫提示音(0:关闭;1:开启)
        private String  callerRecord; // 主叫录音标记(0:否;1:是)
        private String  calledRecord; // 被叫录音标记(0:否;1:是)

        public String getCallerVoice() {
            return callerVoice;
        }

        public void setCallerVoice(String callerVoice) {
            this.callerVoice = callerVoice;
        }

        public String getCalledVoice() {
            return calledVoice;
        }

        public void setCalledVoice(String calledVoice) {
            this.calledVoice = calledVoice;
        }

        public String getCallerRecord() {
            return callerRecord;
        }

        public void setCallerRecord(String callerRecord) {
            this.callerRecord = callerRecord;
        }

        public String getCalledRecord() {
            return calledRecord;
        }

        public void setCalledRecord(String calledRecord) {
            this.calledRecord = calledRecord;
        }
    }



}
