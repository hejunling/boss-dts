package com.ancun.datadispense.listener.province;

import com.ancun.common.persistence.model.master.BizEntInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.conf.EmailConf;
import com.ancun.datadispense.service.province.DXProvinceBizEntInfoService;
import com.ancun.datadispense.service.province.LTProvinceBizEntInfoService;
import com.ancun.datadispense.util.CommonUtil;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datadispense.util.PojoUtil;
import com.ancun.datadispense.util.SendEmail;
import com.ancun.datasubscribe.constant.Constant;
import com.ancun.datasubscribe.eventbus.event.UpdateEvent;
import com.ancun.datasubscribe.eventbus.listener.BaseListener;
import com.ancun.datasubscribe.util.bizeventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * BIZ_ENT_INFO 数据变化消费
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/4/8
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class BizEntInfoListener extends BaseListener {

    private static final Logger logger = LoggerFactory.getLogger(BizEntInfoListener.class);

    @Resource
    private EmailConf emailConf;

    @Resource
    private DXProvinceBizEntInfoService dxProvinceBizEntInfoService;
    
    @Resource
    private LTProvinceBizEntInfoService ltProvinceBizEntInfoService;

    @Subscribe(tableName = BizConstants.T_BIZ_ENT_INFO, userType = Constant.USER_TYPE_DEFAULT)
    public void updateBizEntInfo(UpdateEvent event) throws CustomException {

        BizEntInfo bizEntInfo = PojoUtil.bizEntInfoTransform(event);
        String bizNo = bizEntInfo.getBizNo();
        try {

            if (CommonUtil.checkConfig(BizConstants.DX_PROVINCE_BIZNO, bizNo)) {
                // 分省电信的相关业务
                dxProvinceBizEntInfoService.updateBizEntInfo(bizEntInfo);

            } else if (CommonUtil.checkConfig(BizConstants.LT_PROVINCE_BIZNO, bizNo)) {
                //分省联通相关业务
            	ltProvinceBizEntInfoService.updateBizEntInfo(bizEntInfo);
            }
            logger.info("企业用户信息修改（BIZ_ENT_INFO）:bizEntInfo={}", bizEntInfo);
        } catch (CustomException e) {
            String message = MessageFormat.format("企业用户信息修改失败（BIZ_ENT_INFO）：bizEntInfo={0},原因：{1}", bizEntInfo,
                    e.getMessage());
            logger.info(message);
            SendEmail.send(emailConf, message);
            throw e;
        }
    }

}
