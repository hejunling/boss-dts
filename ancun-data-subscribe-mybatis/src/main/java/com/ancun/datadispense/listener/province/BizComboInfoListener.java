package com.ancun.datadispense.listener.province;

import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.conf.EmailConf;
import com.ancun.datadispense.service.province.DXProvinceBizComboInfoService;
import com.ancun.datadispense.service.province.LTProvinceBizComboInfoService;
import com.ancun.datadispense.util.CommonUtil;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datadispense.util.PojoUtil;
import com.ancun.datadispense.util.SendEmail;
import com.ancun.datasubscribe.constant.Constant;
import com.ancun.datasubscribe.eventbus.event.DeleteEvent;
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
 * 套餐表 BIZ_COMBO_INFO 数消费
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/4/12
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class BizComboInfoListener extends BaseListener {

    private static final Logger logger = LoggerFactory.getLogger(BizComboInfoListener.class);

    @Resource
    private EmailConf emailConf;

    @Resource
    private DXProvinceBizComboInfoService dxProvinceBizComboInfoService;
    
    @Resource
    private LTProvinceBizComboInfoService ltProvinceBizComboInfoService;

    /**
     * 新增套餐
     *
     * @param event
     * @throws Exception
     */
    @Subscribe(tableName = BizConstants.T_BIZ_COMBO_INFO,userType = Constant.USER_TYPE_DEFAULT)
    public void insertBizComboInfo(InsertEvent event) throws CustomException {

        BizComboInfo bizComboInfo = PojoUtil.bizComboInfoTransform(event);
        String bizNo = bizComboInfo.getBizNo();
        try {

            if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                // 分省电信的相关业务
                dxProvinceBizComboInfoService.insertUserTaocan(bizComboInfo);

            } else if (CommonUtil.checkConfig(BizConstants.LT_PROVINCE_BIZNO, bizNo)) {
                //分省联通相关业务
            	ltProvinceBizComboInfoService.insertUserTaocan(bizComboInfo);
            }
            logger.info("套餐新增（BIZ_COMBO_INFO）:bizEntInfo={}", bizComboInfo);
        } catch (CustomException e) {
            String message = MessageFormat.format("套餐新增失败（BIZ_COMBO_INFO）：bizEntInfo={0},原因={1}", bizComboInfo, e.getMessage());
            logger.info(message);
            SendEmail.send(emailConf, message);
            throw e;
        }
    }

    /**
     * 套餐修改
     *
     * @param event
     * @throws Exception
     */
    @Subscribe(tableName = BizConstants.T_BIZ_COMBO_INFO, userType = Constant.USER_TYPE_DEFAULT)
    public void updateBizComboInfo(UpdateEvent event) throws CustomException {

        BizComboInfo bizComboInfo = PojoUtil.bizComboInfoTransform(event);
        String bizNo = bizComboInfo.getBizNo();
        try {

            if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                // 分省电信的相关业务
                dxProvinceBizComboInfoService.updateUserTaocan(bizComboInfo);
            } else if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                //分省联通相关业务
            	ltProvinceBizComboInfoService.updateUserTaocan(bizComboInfo);
            }
            logger.info("套餐修改（BIZ_COMBO_INFO）:bizEntInfo={}", bizComboInfo);
        } catch (CustomException e) {
            String message = MessageFormat.format("套餐修改失败（BIZ_COMBO_INFO）：bizEntInfo={0},原因={1}", bizComboInfo, e.getMessage());
            logger.info(message);
            SendEmail.send(emailConf, message);
            throw e;
        }
    }

    /**
     * 套餐删除
     *
     * @param event
     * @throws Exception
     */
    @Subscribe(tableName = BizConstants.T_BIZ_COMBO_INFO,userType = Constant.USER_TYPE_DEFAULT)
    public void deleteBizComboInfo(DeleteEvent event) throws CustomException {

        BizComboInfo bizComboInfo = PojoUtil.bizComboInfoTransform(event);
        String bizNo = bizComboInfo.getBizNo();
        try {

            if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                // 分省电信的相关业务
                dxProvinceBizComboInfoService.deleteUserTaocan(bizComboInfo);
            } else if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                //分省联通相关业务
            	ltProvinceBizComboInfoService.deleteUserTaocan(bizComboInfo);
            }
            logger.info("套餐删除（BIZ_COMBO_INFO）:bizEntInfo={}", bizComboInfo);
        } catch (CustomException e) {
            String message = MessageFormat.format("套餐删除失败（BIZ_COMBO_INFO）：bizEntInfo={0},原因={1}", bizComboInfo, e.getMessage());
            logger.info(message);
            SendEmail.send(emailConf, message);
            throw e;
        }
    }
}
