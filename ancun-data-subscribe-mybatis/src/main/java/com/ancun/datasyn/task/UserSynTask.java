package com.ancun.datasyn.task;

import com.ancun.common.persistence.model.cp.telecom.TelUserInfo;
import com.ancun.common.persistence.model.cp.unicom.UniUserInfo;
import com.ancun.common.persistence.model.dx.EntUserInfo;
import com.ancun.common.persistence.model.dx.UserInfo;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.common.persistence.model.sh.ShBizUserInfo;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.CacheConstant;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.user.BossUserInfo;
import com.ancun.datasyn.service.cp.telecom.ICpTelUserInfoService;
import com.ancun.datasyn.service.cp.unicom.ICpUnicomUserInfoService;
import com.ancun.datasyn.service.master.IBizUserInfoService;
import com.ancun.datasyn.service.provice.IDXUserinfoService;
import com.ancun.datasyn.service.provice.ILTUserinfoService;
import com.ancun.datasyn.service.sh.IShUserInfoService;
import com.ancun.datasyn.util.SynPojoUtil;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户同步
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/5/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class UserSynTask {

    public static final Logger logger = LoggerFactory.getLogger(UserSynTask.class);

    @Resource
    private Producer producer;

    @Resource
    private IDXUserinfoService dxUserinfoService;

    @Resource
    private IBizUserInfoService bizUserInfoService;

    @Resource
    private ILTUserinfoService ltUserinfoService;

    @Resource
    private ICpTelUserInfoService cpTelUserInfoService;

    @Resource
    private ICpUnicomUserInfoService cpUnicomUserInfoService;

    @Resource
    private IShUserInfoService shUserInfoService;

    /**
     * 用户数据生产
     *
     * @param config
     */
    public void send2Producer(BizTimerConfig config) {

        //分省电信用户生产
        dxUserInfoProducer(config);
        //分省联通用户生产
        ltUserInfoProducer(config);
        //总部电信用户生产
        cpDxUserInfoProducer(config);
        //总部联通用户生产
        cpLtUserInfoProducer(config);
        //上海用户生产
        shUserInfoProducer(config);

    }

    /**
     * 上海音证宝用户生产
     *
     * @param config
     */
    private void shUserInfoProducer(BizTimerConfig config) {
//        try {

            List<ShBizUserInfo> shBizUserInfoList = shUserInfoService.selectShUserInfo(config);

            checkList(shBizUserInfoList);

            logger.info("bizNo:{},上海音证宝查询到记录数{}", SynConstant.BIZ_DX_SHANGHAI, shBizUserInfoList.size());

            Date synTime = new Date(); // 同步时间
            UUID uuid = UUID.randomUUID();

            BossUserInfo bossUserInfo = new BossUserInfo();

            for (ShBizUserInfo shBizUserInfo : shBizUserInfoList) {

                BizUserInfo bizUserInfo = new BizUserInfo();
                String errorInfo = null;
                try {
                    bizUserInfo = SynPojoUtil.bizUserInfoTransform4Sh(shBizUserInfo);
                }catch (Exception e){
                    logger.error("上海音证宝字段对应boss字段异常 = {},shBizUserInfo = {}",e,shBizUserInfo);
                    errorInfo = "上海音证宝字段对应boss字段异常:"+ e.getMessage() + " shBizUserInfo:" + shBizUserInfo;
                }
                bossUserInfo.setBizname("上海音证宝");
                bossUserInfo.setUuid(uuid);
                bossUserInfo.setErrorInfo(errorInfo);
                bossUserInfo.setSynTime(synTime);
                bossUserInfo.setSynSize(shBizUserInfoList.size());
                bossUserInfo.setBizUserInfo(bizUserInfo);
                producer.send(bossUserInfo);
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }

    /**
     * 总部联通用户生产
     *
     * @param config
     */
    private void cpLtUserInfoProducer(BizTimerConfig config) {
        List<UniUserInfo> uniUserInfoList = cpUnicomUserInfoService.selectUniUserInfo(config);
        checkList(uniUserInfoList);

        logger.info("bizNo:{},总部联通用户查询到记录数{}", SynConstant.BIZ_DX_CP, uniUserInfoList.size());

        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        BossUserInfo bossUserInfo = new BossUserInfo();
        for (UniUserInfo uniUserInfo : uniUserInfoList) {
            BizUserInfo bizUserInfo = new BizUserInfo();
            String errorInfo = null;
            try {
                bizUserInfo = SynPojoUtil.bizUserInfoTransform4CPUnicom(uniUserInfo);
            }catch (Exception e){
                logger.error("总部联通用户字段对应boss字段异常 = {},uniUserInfo = {}",e,uniUserInfo);
                errorInfo = "总部联通用户字段对应boss字段异常:"+ e.getMessage() + " uniUserInfo:" + uniUserInfo;
            }
            bossUserInfo.setBizname("cp联通");
            bossUserInfo.setUuid(uuid);
            bossUserInfo.setErrorInfo(errorInfo);
            bossUserInfo.setSynTime(synTime);
            bossUserInfo.setSynSize(uniUserInfoList.size());
            bossUserInfo.setBizUserInfo(bizUserInfo);
            producer.send(bossUserInfo);
//            producer.send(SynPojoUtil.bizUserInfoTransform4CPUnicom(uniUserInfo));
        }
    }

    /**
     * 总部电信用户生产
     *
     * @param config
     */
    private void cpDxUserInfoProducer(BizTimerConfig config) {
        List<TelUserInfo> telUserInfoList = cpTelUserInfoService.selectTelUserInfo(config);

        checkList(telUserInfoList);

        logger.info("bizNo:{},总部电信用户查询到记录数{}", SynConstant.BIZ_DX_CP, telUserInfoList.size());

        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        BossUserInfo bossUserInfo = new BossUserInfo();

        for (TelUserInfo telUserInfo : telUserInfoList) {
            BizUserInfo bizUserInfo = new BizUserInfo();
            String errorInfo = null;
            try {
                bizUserInfo = SynPojoUtil.bizUserInfoTransform4CPTel(telUserInfo);
            }catch (Exception e){
                logger.error("总部电信用户字段对应boss字段异常 = {},telUserInfo = {}",e,telUserInfo);
                errorInfo = "总部电信用户字段对应boss字段异常:"+ e.getMessage() + " telUserInfo:" + telUserInfo;
            }
            bossUserInfo.setBizname("cp电信");
            bossUserInfo.setUuid(uuid);
            bossUserInfo.setErrorInfo(errorInfo);
            bossUserInfo.setSynTime(synTime);
            bossUserInfo.setSynSize(telUserInfoList.size());
            bossUserInfo.setBizUserInfo(bizUserInfo);
            producer.send(bossUserInfo);
//            producer.send(SynPojoUtil.bizUserInfoTransform4CPTel(telUserInfo));
        }

    }


    /**
     * 分省电信用户生产
     *
     * @param config
     */
    private void dxUserInfoProducer(BizTimerConfig config) {

        /**
         * 1.个人用户：查询有数据变化的省份
         */
        List<String> dxPersonRpcodes = dxUserinfoService.selectAllPersonRpcodes(config);
        if (null != dxPersonRpcodes && dxPersonRpcodes.size() > 0) {

            // 个人用户：查询有数据变化的用户数量
            int allPersonCount = dxUserinfoService.selectAllPersonCount(config);
            Date synTimePerson = new Date(); // 同步时间
            UUID uuid = UUID.randomUUID();

            for (String rpcode : dxPersonRpcodes) {

                if(StringUtil.isBlank(rpcode)){
                    continue;
                }

                List<UserInfo> dxUserInfoList = dxUserinfoService.selectUserInfo(config, rpcode);
                if (null == dxUserInfoList || dxUserInfoList.size() <= 0)
                    continue;

                logger.info("bizNo:{},分省电信个人用户查询到记录数{}", CacheConstant.DX_PROVINCE_MAP.get(rpcode), dxUserInfoList.size());

                BossUserInfo bossUserInfo = new BossUserInfo();
                for (UserInfo userInfo : dxUserInfoList) {
                    BizUserInfo bizUserInfo = new BizUserInfo();
                    String errorInfo = null;
                    try {
                        bizUserInfo = SynPojoUtil.bizUserInfoTransform4ProvincePerson(userInfo, CacheConstant.DX_PROVINCE_MAP);
                    }catch (Exception e){
                        logger.error("分省电信:"+ CacheConstant.DX_PROVINCE_MAP.get(rpcode)+" 个人用户 字段对应boss字段异常 = {},userInfo = {}",e,userInfo);
                        errorInfo = "分省电信:" + CacheConstant.DX_PROVINCE_MAP.get(rpcode) +" 个人用户 字段对应boss字段异常: "+ e.getMessage() + " userInfo: " + userInfo+"\n";;
                    }
                    bossUserInfo.setBizname("分省电信个人用户");
                    bossUserInfo.setUuid(uuid);
                    bossUserInfo.setErrorInfo(errorInfo);
                    bossUserInfo.setSynTime(synTimePerson);
                    bossUserInfo.setSynSize(allPersonCount);
                    bossUserInfo.setBizUserInfo(bizUserInfo);
                    producer.send(bossUserInfo);
//                    producer.send(SynPojoUtil.bizUserInfoTransform4ProvincePerson(userInfo, CacheConstant.DX_PROVINCE_MAP));
                }
            }
        }


        /**
         * 企业用户:查询有数据变化的省份
         */
        List<String> dxEntRpcodes = dxUserinfoService.selectAllEntRpcodes(config);
        if (null != dxEntRpcodes && dxEntRpcodes.size() > 0) {

            // 企业用户：查询有数据变化的用户数量
            int allEntCount = dxUserinfoService.selectAllEntCount(config);
            Date synTimeEnt = new Date(); // 同步时间
            UUID uuid = UUID.randomUUID();
            for (String rpcode : dxEntRpcodes) {

                if(StringUtil.isBlank(rpcode)){
                    continue;
                }

                List<EntUserInfo> dxEntUserInfoList = dxUserinfoService.selectEntUserInfo(config, rpcode);
                if (null == dxEntUserInfoList || dxEntUserInfoList.size() <= 0)
                    continue;
                logger.info("bizNo:{},分省电信企业用户查询到记录数{}", CacheConstant.DX_PROVINCE_MAP.get(rpcode), dxEntUserInfoList.size());

                BossUserInfo bossUserInfo = new BossUserInfo();

                for (EntUserInfo entUserInfo : dxEntUserInfoList) {
                    BizUserInfo bizUserInfo = new BizUserInfo();
                    String errorInfo = null;
                    try {
                        bizUserInfo = SynPojoUtil.bizUserInfoTransform4ProvinceEnt(entUserInfo, CacheConstant.DX_PROVINCE_MAP);
                    }catch (Exception e){
                        logger.error("分省电信:" + CacheConstant.DX_PROVINCE_MAP.get(rpcode) +" 企业用户 字段对应boss字段异常 = {},entUserInfo = {}",e,entUserInfo);
                        errorInfo = "分省电信: " + CacheConstant.DX_PROVINCE_MAP.get(rpcode) + " 企业用户 字段对应boss字段异常:"+ e.getMessage() + " entUserInfo:" + entUserInfo+"\n";
                    }
                    bossUserInfo.setBizname("分省电信企业用户");
                    bossUserInfo.setUuid(uuid);
                    bossUserInfo.setErrorInfo(errorInfo);
                    bossUserInfo.setSynTime(synTimeEnt);
                    bossUserInfo.setSynSize(allEntCount);
                    bossUserInfo.setBizUserInfo(bizUserInfo);
                    producer.send(bossUserInfo);
//                    producer.send(SynPojoUtil.bizUserInfoTransform4ProvinceEnt(entUserInfo, CacheConstant.DX_PROVINCE_MAP));
                }
            }
        }
    }


    /**
     * 分省联通用户生产
     *
     * @param config
     */
    private void ltUserInfoProducer(BizTimerConfig config) {

        /**
         * 1.个人用户：查询有数据变化的省份
         */
        List<String> dxPersonRpcodes = ltUserinfoService.selectAllPersonRpcodes(config);
        if (null != dxPersonRpcodes && dxPersonRpcodes.size() > 0) {

            // 个人用户：查询有数据变化的用户数量
            int allPersonCount = ltUserinfoService.selectAllPersonCount(config);
            Date synTime = new Date(); // 同步时间
            UUID uuid = UUID.randomUUID();
            for (String rpcode : dxPersonRpcodes) {

                if(StringUtil.isBlank(rpcode)){
                    continue;
                }

                List<UserInfo> dxUserInfoList = ltUserinfoService.selectUserInfo(config, rpcode);
                if (null == dxUserInfoList || dxUserInfoList.size() <= 0)
                    continue;

                logger.info("bizNo:{},分省联通个人用户查询到记录数{}", CacheConstant.LT_PROVINCE_MAP.get(rpcode), dxUserInfoList.size());

                BossUserInfo bossUserInfo = new BossUserInfo();

                for (UserInfo userInfo : dxUserInfoList) {
                    BizUserInfo bizUserInfo = new BizUserInfo();
                    String errorInfo = null;
                    try {
                        bizUserInfo = SynPojoUtil.bizUserInfoTransform4ProvincePerson(userInfo, CacheConstant.LT_PROVINCE_MAP);
                    }catch (Exception e){
                        logger.error("分省联通:"+ CacheConstant.LT_PROVINCE_MAP.get(rpcode)+" 个人用户 字段对应boss字段异常 = {},userInfo = {}",e,userInfo);
                        errorInfo = "分省联通:" +CacheConstant.LT_PROVINCE_MAP.get(rpcode)+" 个人用户 字段对应boss字段异常:"+ e.getMessage() + " userInfo:" + userInfo +"\n";
                    }
                    bossUserInfo.setBizname("分省联通个人用户");
                    bossUserInfo.setUuid(uuid);
                    bossUserInfo.setErrorInfo(errorInfo);
                    bossUserInfo.setSynTime(synTime);
                    bossUserInfo.setSynSize(allPersonCount);
                    bossUserInfo.setBizUserInfo(bizUserInfo);
                    producer.send(bossUserInfo);
//                    producer.send(SynPojoUtil.bizUserInfoTransform4ProvincePerson(userInfo, CacheConstant.LT_PROVINCE_MAP));
                }

            }
        }


        /**
         * 2.企业用户:查询有数据变化的省份
         */
        List<String> dxEntRpcodes = ltUserinfoService.selectAllEntRpcodes(config);
        if (null != dxEntRpcodes && dxEntRpcodes.size() > 0) {

            /// 企业用户：查询有数据变化的用户数量
            int allEntCount = ltUserinfoService.selectAllEntCount(config);
            Date synTime = new Date(); // 同步时间
            UUID uuid = UUID.randomUUID();
            for (String rpcode : dxEntRpcodes) {

                if(StringUtil.isBlank(rpcode)){
                    continue;
                }

                List<EntUserInfo> dxEntUserInfoList = ltUserinfoService.selectEntUserInfo(config, rpcode);
                if (null == dxEntUserInfoList || dxEntUserInfoList.size() <= 0)
                    continue;

                logger.info("bizNo:{},分省联通企业用户查询到记录数{}", CacheConstant.LT_PROVINCE_MAP.get(rpcode), dxEntUserInfoList.size());

                BossUserInfo bossUserInfo = new BossUserInfo();
                for (EntUserInfo entUserInfo : dxEntUserInfoList) {
                    BizUserInfo bizUserInfo = new BizUserInfo();
                    String errorInfo = null;
                    try {
                        bizUserInfo = SynPojoUtil.bizUserInfoTransform4ProvinceEnt(entUserInfo, CacheConstant.LT_PROVINCE_MAP);
                    }catch (Exception e){
                        logger.error("分省联通:"+ CacheConstant.LT_PROVINCE_MAP.get(rpcode) + " 企业用户 字段对应boss字段异常 = {},entUserInfo = {}",e,entUserInfo);
                        errorInfo = "分省联通:"+ CacheConstant.LT_PROVINCE_MAP.get(rpcode) + "企业用户 字段对应boss字段异常:"+ e.getMessage() + " entUserInfo:" + entUserInfo+"\n";
                    }
                    bossUserInfo.setBizname("分省联通企业用户");
                    bossUserInfo.setUuid(uuid);
                    bossUserInfo.setErrorInfo(errorInfo);
                    bossUserInfo.setSynTime(synTime);
                    bossUserInfo.setSynSize(allEntCount);
                    bossUserInfo.setBizUserInfo(bizUserInfo);
                    producer.send(bossUserInfo);
//                    producer.send(SynPojoUtil.bizUserInfoTransform4ProvinceEnt(entUserInfo, CacheConstant.LT_PROVINCE_MAP));
                }
            }
        }
    }


    /**
     * 检查用户数量
     *
     * @param list
     */
    private void checkList(List list) {
        if (null == list || list.size() <= 0) {
            return;
        }
    }
}
