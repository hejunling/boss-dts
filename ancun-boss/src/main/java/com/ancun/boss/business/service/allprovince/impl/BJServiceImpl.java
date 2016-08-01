package com.ancun.boss.business.service.allprovince.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.ancun.boss.business.constant.Constant;
import com.ancun.common.biz.email.SendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ancun.boss.business.persistence.mapper.BizComboInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoMapper;
import com.ancun.boss.business.persistence.module.BizComboInfo;
import com.ancun.boss.business.persistence.module.BizComboInfoExample;
import com.ancun.boss.business.persistence.module.BizUserInfo;
import com.ancun.boss.business.persistence.module.BizUserInfoExample;
import com.ancun.boss.business.pojo.allprovince.ProvinceServiceInput;
import com.ancun.boss.business.pojo.allprovince.SubscriptionInput;
import com.ancun.boss.business.pojo.allprovince.TaocanChangeInput;
import com.ancun.boss.business.service.allprovince.IProvinceService;
import com.ancun.boss.business.utils.fptcsrvc.BJFtpUtil;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.core.constant.ResponseConst;
import com.ancun.core.domain.response.RespObject;
import com.ancun.core.exception.EduException;
import com.ancun.core.utils.JsonUtils;
import com.ancun.http.enums.ContentType;

/**
 * @author chenb
 * @version 1.0
 * @Created on 2016年3月15日
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service(value = "BJService")
public class BJServiceImpl implements IProvinceService {


    @Resource
    private BizUserInfoMapper userInfoMapper;

    @Resource
    private BizComboInfoMapper userTaocanMapper;

    @Resource
    private BaseDataServiceImpl baseDataService;

    private String accessKey;

    private String url;

    private String accessId;

    private Logger log = LoggerFactory.getLogger(BJServiceImpl.class);

    @Override
    public void open(ProvinceServiceInput input) {

        boolean p2eFlag = false;

        BizUserInfoExample example = new BizUserInfoExample();
        BizUserInfoExample.Criteria c = example.createCriteria();
        c.andUserNoEqualTo(input.getUserno());

        List<BizUserInfo> users = userInfoMapper.selectByExample(example);

        if (users.size() == 1) {
            BizUserInfo user = users.get(0);
            if (user.getStatus().equals(BizRequestConstant.OPENED_USER) && user.getUserType().equals(BizRequestConstant.PERSONAL_USER) && input.getUserType().equals(BizRequestConstant.ENT_USER)) {
                p2eFlag = true;
            }
        } else if (users.size() > 1) {
            throw new EduException(MessageConstant.USER_REPEAT_ERROR);
        }

        // 是否个人用户转企业用户
        if (p2eFlag) {
            // 发送套餐修改请求
            postChangeRequest(input.getUserno(), "3", users.get(0).getTcid().toString(), input.getTcID(), false);
        } else {
            // 发送套餐添加请求
            postAddRequest(input.getUserno(), input.getUserType(), input.getTcID(), input.getEntUser());
        }

    }

    @Override
    public void cancel(ProvinceServiceInput input) {
        postCancelRequest(input.getUserno(), input.getUserType(), input.getTcID(), input.getEntUser());
    }


    /**
     * 调用北京联通用户开通接口
     *
     * @param userno   用户号码
     * @param userType 用户类别
     * @param tcID     套餐ID
     * @author chenb
     * @date 2015-12-23
     */
    private void postAddRequest(String userno, String userType, String tcID, String mainUser) {

        try {

            this.url = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_URL);
            this.accessId = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_ACCESSID);
            this.accessKey = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_ACCESSKEY);
            SubscriptionInput input = new SubscriptionInput();
            input.setAccessid(accessId);
            input.setRequestType("2");
            input.setProvinceCode(BizRequestConstant.PROVINCES_BEIJING);
            input.setUserno(userno);
            input.setUserType(userType);

            if (checkTaocanPrice(userno, userType, tcID, mainUser)) {
                input.setTcType("0");
            } else {
                input.setTcType("1");
            }

            log.debug("北京联通url:" + this.url);
            log.debug("北京联通accessId:" + this.accessId);
            log.debug("北京联通accessKey:" + this.accessKey);

            String result = BJFtpUtil.doService("createSubScription", ContentType.JSON, input, url, accessKey);
            RespObject<String> response = JsonUtils.convertToResqObject(result, null);
            if (ResponseConst.SUCCESS != response.getInfo().getCode()) {
                throw new EduException(MessageConstant.BJUNICOM_OPEN_ERROR);
            }
        } catch (Exception e) {
            throw new EduException(MessageConstant.BJUNICOM_OPEN_ERROR);
        }
    }

    /**
     * 调用北京联通用户退订接口
     *
     * @param userno   用户号码
     * @param userType 用户类别
     * @param tcID     套餐ID
     * @author chenb
     * @date 2015-12-23
     */
    private void postCancelRequest(String userno, String userType, String tcID, String mainUser) {

        try {
            SubscriptionInput input = new SubscriptionInput();
            input.setAccessid(accessId);
            input.setRequestType("2");
            input.setProvinceCode(BizRequestConstant.PROVINCES_BEIJING);
            input.setUserno(userno);
            input.setUserType(userType);
            if (checkTaocanPrice(userno, userType, tcID, mainUser)) {
                input.setTcType("0");
            } else {
                input.setTcType("1");
            }

            this.url = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_URL);
            this.accessId = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_ACCESSID);
            this.accessKey = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_ACCESSKEY);

            log.debug("北京联通url:" + this.url);
            log.debug("北京联通accessId:" + this.accessId);
            log.debug("北京联通accessKey:" + this.accessKey);

            String result = BJFtpUtil.doService("withdrawSubscription", ContentType.JSON, input, url, accessKey);
            RespObject<String> response = JsonUtils.convertToResqObject(result, null);
            if (ResponseConst.SUCCESS != response.getInfo().getCode()) {
                throw new EduException(MessageConstant.BJUNICOM_CLOSE_ERROR);
            }
        } catch (Exception e) {
            throw new EduException(MessageConstant.BJUNICOM_CLOSE_ERROR);
        }
    }

    /**
     * 调用北京联通用户套餐修改接口
     *
     * @param userno   用户号码
     * @param userType 用户类别
     * @author chenb
     * @date 2016-3-7
     */

    @Override
    public void postChangeRequest(String userno, String changeTcType, String oldTcID, String newTcID, boolean rollback) {

        try {

            TaocanChangeInput input = new TaocanChangeInput();
            input.setAccessid(this.accessId);
            input.setRequestType("2");
            input.setProvinceCode(BizRequestConstant.PROVINCES_BEIJING);
            input.setUserno(userno);
            input.setChangeTcType(changeTcType);
            if (checkTaocanPrice("", "", oldTcID, "")) {
                input.setOldTcType("0");
            } else {
                input.setOldTcType("1");
            }
            if (checkTaocanPrice("", "", newTcID, "")) {
                input.setNewTcType("0");
            } else {
                input.setNewTcType("1");
            }
            input.setRollback(rollback);

            this.url = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_URL);
            this.accessId = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_ACCESSID);
            this.accessKey = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_ACCESSKEY);

            log.debug("北京联通url:" + this.url);
            log.debug("北京联通accessId:" + this.accessId);
            log.debug("北京联通accessKey:" + this.accessKey);

            String result = BJFtpUtil.doService("changeTaoCan", ContentType.JSON, input, url, accessKey);
            RespObject<String> response = JsonUtils.convertToResqObject(result, null);
            if (ResponseConst.SUCCESS != response.getInfo().getCode()) {
                throw new EduException(MessageConstant.BJUNICOM_CHANGE_ERROR);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new EduException(MessageConstant.BJUNICOM_CHANGE_ERROR);
        }
    }

    /**
     * 调用北京联通用户套餐修改接口
     * @param provinceInput
     * add by zkai on 2015/05/06
     */
    @Override
    public boolean postChangeRequest(ProvinceServiceInput provinceInput) {
        String title = "北京联通远程接口异常";
        String content = "套餐修改： " ;
        SendEmail sendEmail = new SendEmail();
        try {

            TaocanChangeInput input = new TaocanChangeInput();
            input.setAccessid(this.accessId);
            input.setRequestType("2");
            input.setProvinceCode(BizRequestConstant.PROVINCES_BEIJING);
            input.setUserno(provinceInput.getUserno());
            input.setChangeTcType(provinceInput.getChangeTcType());
            if (checkTaocanPrice("", "", provinceInput.getOldTc(), "")) {
                input.setOldTcType("0");
            } else {
                input.setOldTcType("1");
            }
            if (checkTaocanPrice("", "", provinceInput.getNewTc(), "")) {
                input.setNewTcType("0");
            } else {
                input.setNewTcType("1");
            }
            input.setRollback(provinceInput.isRollback());

            this.url = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_URL);
            this.accessId = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_ACCESSID);
            this.accessKey = baseDataService.getConfigMessage(BizRequestConstant.PROVINCES_BEIJING, BizRequestConstant.CSP_ACCESSKEY);

            log.debug("北京联通url:" + this.url);
            log.debug("北京联通accessId:" + this.accessId);
            log.debug("北京联通accessKey:" + this.accessKey);

            String result = BJFtpUtil.doService("changeTaoCan", ContentType.JSON, input, url, accessKey);
            RespObject<String> response = JsonUtils.convertToResqObject(result, null);
            if (ResponseConst.SUCCESS != response.getInfo().getCode()) {
                log.error("北京联通套餐修改接口调用失败");
                throw new EduException(MessageConstant.BJUNICOM_CHANGE_ERROR);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            content += "用户编号 = "+provinceInput.getUserno() + " 套餐改变类型="+provinceInput.getChangeTcType()+" (3 个人转企业) 老套餐= "
                            +provinceInput.getOldTc() +" 新套餐= "+provinceInput.getNewTc() +" 是否回滚="+provinceInput.isRollback();
            log.error("北京联通套餐修改失败",e);
            sendEmail.send( Constant.SEND_EMAIL_TO, title, content, null, true);
            return false;
        }
        log.debug("北京联通套餐修改成功");
        return true;
    }

    /**
     * 套餐信息取得
     *
     * @param userno    用户号码
     * @param userType  用户类别
     * @param taocangID
     * @author chenb
     * @date 2016-3-4
     */
    private boolean checkTaocanPrice(String userno, String userType, String taocangID, String mainUser) {

        Long tcid = 0l;
        try {

            if (taocangID.equals("")) {
                // 个人用户信息取得
                BizUserInfoExample example = new BizUserInfoExample();
                BizUserInfoExample.Criteria criteria = example.createCriteria();
                criteria.andUserNoEqualTo(userno);

                List<BizUserInfo> users = userInfoMapper.selectByExample(example);
                BizUserInfo user = users.get(0);
                tcid = user.getTcid();
            }

            List<BizComboInfo> taocans = null;
            BizComboInfoExample combo = new BizComboInfoExample();
            BizComboInfoExample.Criteria c = combo.createCriteria();

            if (taocangID.equals("")) {
                // 套餐信息取得
                c.andTcidEqualTo(Long.valueOf(tcid));
            } else {
                c.andTcidEqualTo(Long.valueOf(taocangID));
            }

            taocans = userTaocanMapper.selectByExample(combo);

            if (taocans.get(0).getPrice().compareTo(BigDecimal.ZERO) == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }


    }


}
