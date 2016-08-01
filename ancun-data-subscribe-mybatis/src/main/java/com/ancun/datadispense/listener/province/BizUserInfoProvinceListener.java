package com.ancun.datadispense.listener.province;

import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.conf.EmailConf;
import com.ancun.datadispense.service.cp.telecom.ICpTelecomService;
import com.ancun.datadispense.service.cp.unicom.UnicomUserService;
import com.ancun.datadispense.service.province.DXProvinceEntUserService;
import com.ancun.datadispense.service.province.DXProvincePersonalUserService;
import com.ancun.datadispense.service.province.LTProvinceEntUserService;
import com.ancun.datadispense.service.province.LTProvincePersonalUserService;
import com.ancun.datadispense.service.sh.IEntUserService4SH;
import com.ancun.datadispense.service.sh.IPersonalUserService4SH;
import com.ancun.datadispense.util.CommonUtil;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datadispense.util.PojoUtil;
import com.ancun.datadispense.util.SendEmail;
import com.ancun.datasubscribe.eventbus.event.InsertEvent;
import com.ancun.datasubscribe.eventbus.event.UpdateEvent;
import com.ancun.datasubscribe.eventbus.listener.BaseListener;
import com.ancun.datasubscribe.util.bizeventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * BIZ_User_Info 表数据变化监听消费
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/3/29
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class BizUserInfoProvinceListener extends BaseListener {

    private static final Logger logger = LoggerFactory.getLogger(BizUserInfoProvinceListener.class);

    @Resource
    private EmailConf emailConf;

    @Resource
    private DXProvincePersonalUserService dxProvincePersonalUserService;

    @Resource
    private DXProvinceEntUserService dxProvinceEntUserService;
    
    @Resource
    private LTProvincePersonalUserService ltProvincePersonalUserService;

    @Resource
    private LTProvinceEntUserService ltProvinceEntUserService;
    
    @Resource
    private UnicomUserService unicomUserService;

    @Resource
    private IEntUserService4SH entUserService4SH;
    
    @Resource
    private IPersonalUserService4SH personalUserService4SH;
    
    @Resource
    private ICpTelecomService cpTelecomService;

    /**
     * 个人用户开通
     *
     * @param event
     */
    @Subscribe(tableName = BizConstants.T_BIZ_USER_INFO, userType = BizConstants.USER_TYPE_PERSONAL)
    public void openPersonalUser(InsertEvent event) throws CustomException {
        BizUserInfo bizUserInfo = PojoUtil.bizUserInfoTransform(event);
        String bizNo = bizUserInfo.getBizNo();
        try {

            if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                // 分省电信的相关业务
                dxProvincePersonalUserService.openPersonalUser(bizUserInfo);

            } else if (CommonUtil.checkConfig(BizConstants.LT_PROVINCE_BIZNO, bizNo)) {
                //分省联通相关业务
            	ltProvincePersonalUserService.openPersonalUser(bizUserInfo);
            } else if (CommonUtil.checkConfig(BizConstants.SH_PROVINCE_BIZNO, bizNo)) {
            	//上海音证宝
            	personalUserService4SH.openPersonalUser(bizUserInfo);
            }else if (CommonUtil.checkConfig(BizConstants.BIZ_CODE_CPDX, bizNo)) {
            	//CP电信
            	cpTelecomService.openUser(bizUserInfo);
            }else if (CommonUtil.checkConfig(BizConstants.CP_UNICOM_BIZNO, bizNo)) {
            	// CP联通
            	unicomUserService.openUser(bizUserInfo);
            	
            }
            logger.info("个人用户开通:bizUserInfo={}", bizUserInfo);
        } catch (CustomException e) {
            String message = MessageFormat.format("个人用户开通失败：bizUserInfo={0},原因：{1}", bizUserInfo, e.getMessage());
            logger.info(message);
            SendEmail.send(emailConf, message);
            throw e;
        }
    }


    /**
     * 个人用户修改
     *
     * @param event
     */
    @Subscribe(tableName = BizConstants.T_BIZ_USER_INFO, userType = BizConstants.USER_TYPE_PERSONAL)
    public void UpdatePersonalUser(UpdateEvent event) throws CustomException {

        /**
         * 用户转换
         */
        BizUserInfo bizUserInfo = PojoUtil.bizUserInfoTransform(event);
        String bizNo = bizUserInfo.getBizNo();
        try {
            if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                // 分省电信的相关业务
                dxProvincePersonalUserService.UpdatePersonalUser(bizUserInfo);
            } else if (CommonUtil.checkConfig(BizConstants.LT_PROVINCE_BIZNO, bizNo)) {
                //分省联通相关业务
            	ltProvincePersonalUserService.UpdatePersonalUser(bizUserInfo);
            } else if (CommonUtil.checkConfig(BizConstants.SH_PROVINCE_BIZNO, bizNo)) {
            	//上海音证宝
            	personalUserService4SH.UpdatePersonalUser(bizUserInfo);
            }else if (CommonUtil.checkConfig(BizConstants.BIZ_CODE_CPDX, bizNo)) {
            	//CP电信
            	cpTelecomService.updateUser(bizUserInfo);
            } else if (CommonUtil.checkConfig(BizConstants.CP_UNICOM_BIZNO, bizNo)) {
            	// cp联通相关业务
            	unicomUserService.updateUser(bizUserInfo);
            }
            logger.info("个人用户修改:bizUserInfo={},(userStauts != '1' 时为退订操作)", bizUserInfo);
        } catch (CustomException e) {
            String message = MessageFormat.format("个人用修改失败：bizUserInfo={0}(userStauts != '1' 时为退订操作),原因：{1}", bizUserInfo, e.getMessage());
            logger.info(message);
            SendEmail.send(emailConf, message);
            throw e;
        }
    }

    /**
     * 开通企业用户
     *
     * @param event
     * @throws Exception
     */
    @Subscribe(tableName = BizConstants.T_BIZ_USER_INFO, userType = BizConstants.USER_TYPE_ENTERPRISES)
    public void openEntUser(InsertEvent event) throws CustomException {

        /**
         * 用户转换
         */
        BizUserInfo bizUserInfo = PojoUtil.bizUserInfoTransform(event);
        String bizNo = bizUserInfo.getBizNo();
        try {
            if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                // 分省电信的相关业务
                dxProvinceEntUserService.openEntUser(bizUserInfo);

            } else if (CommonUtil.checkConfig(BizConstants.LT_PROVINCE_BIZNO, bizNo)) {
                //分省联通相关业务
            	ltProvinceEntUserService.openEntUser(bizUserInfo);
            } else if (CommonUtil.checkConfig(BizConstants.SH_PROVINCE_BIZNO, bizNo)) {
            	//上海音证宝
            	entUserService4SH.openEntUser(bizUserInfo);
            }
            logger.info("企业用户开通:bizUserInfo={}", bizUserInfo);
        } catch (CustomException e) {
            String message = MessageFormat.format("企业用户开通失败：bizUserInfo={0},原因：{1}", bizUserInfo, e.getMessage());
            logger.info(message);
            SendEmail.send(emailConf, message);
            throw e;
        }
    }


    /**
     * 企业用户修改
     *
     * @param event
     * @throws Exception
     */
    @Subscribe(tableName = BizConstants.T_BIZ_USER_INFO, userType = BizConstants.USER_TYPE_ENTERPRISES)
    public void updateEntUser(UpdateEvent event) throws CustomException {
        /**
         * 用户转换
         */
        BizUserInfo bizUserInfo = PojoUtil.bizUserInfoTransform(event);
        String bizNo = bizUserInfo.getBizNo();
        try {
            if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                // 分省电信的相关业务
                dxProvinceEntUserService.updateEntUser(bizUserInfo);

            }else if (CommonUtil.checkConfig(BizConstants.LT_PROVINCE_BIZNO, bizNo)) {
                //分省联通相关业务
            	ltProvinceEntUserService.updateEntUser(bizUserInfo);
            } else if (CommonUtil.checkConfig(BizConstants.SH_PROVINCE_BIZNO, bizNo)) {
            	//上海音证宝
            	entUserService4SH.updateEntUser(bizUserInfo);
            }
            logger.info("企业用户修改:bizUserInfo={},(userStauts != '1' 时为退订操作)", bizUserInfo);
        } catch (CustomException e) {
            String message = MessageFormat.format("企业用户修改失败：bizUserInfo={0},(userStauts != '1' 时为退订操作),原因：{1}", bizUserInfo, e.getMessage());
            logger.info(message);
            SendEmail.send(emailConf, message);
            throw e;
        }
    }

}
