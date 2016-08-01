package com.ancun.datasyn.service.sh.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.sh.ShAccountInfoHistoryMapper;
import com.ancun.common.persistence.model.master.BizUserLifeCircle;
import com.ancun.common.persistence.model.sh.ShBizUserInfoHistory;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.userlife.BossUserLifeInfo;
import com.ancun.datasyn.pojo.userlife.UserLifeInput;
import com.ancun.datasyn.service.sh.IShUserLifeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 上海音证宝生命周期
 * User: zkai
 * Date: 2016/5/27
 * Time: 15:10
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "shV2")
public class ShUserLifeServiceImpl implements IShUserLifeService {

    private static final Logger log = LoggerFactory.getLogger(ShUserLifeServiceImpl.class);

    @Resource
    ShAccountInfoHistoryMapper shAccountInfoHistoryMapper;

    @Resource
    private Producer producer;


    /**
     * 插入上海音证宝用户生命周期列表
     */
    @Override
    public void insertShUserLifeInfoQ(UserLifeInput input){
        // 上海音证宝用户生命周期列表
        List<ShBizUserInfoHistory> shBizUserInfoHistoryList = getSynUserLifeInfo(input);
        checkList(shBizUserInfoHistoryList);
        log.info("需要同步上海音证宝用户生命周期列表 "+ shBizUserInfoHistoryList.size() + " 条");
        // 将信息插入到队列中
        insertUserLifeQueue(shBizUserInfoHistoryList);
    }

    /**
     * 取得需要同步的个人用户生命周期信息
     * @return
     */
    private List<ShBizUserInfoHistory> getSynUserLifeInfo(UserLifeInput input){
        return shAccountInfoHistoryMapper.selectShBizUserInfoHistory(input);
    }

    /**
     * 将信息插入到生命周期队列中
     * @param shBizUserInfoHistoryList
     */
    private void insertUserLifeQueue(List<ShBizUserInfoHistory> shBizUserInfoHistoryList){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (ShBizUserInfoHistory shBizUserInfoHistory: shBizUserInfoHistoryList) {
            BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
            BossUserLifeInfo bossUserLifeInfo = new BossUserLifeInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserLifeCircle = shUserHistoryToBossUserLife(shBizUserInfoHistory);
            }catch (Exception e){
                log.debug("上海音证宝用户历史字段对应boss字段异常 = {},shBizUserInfoHistory = {}",e,shBizUserInfoHistory);
                errorInfo = "上海音证宝用户历史字段对应boss字段异常:"+ e.getMessage() + " shBizUserInfoHistory: "+ shBizUserInfoHistory;
            }
            bossUserLifeInfo.setOpenTime(shBizUserInfoHistory.getOpendatetime());
            bossUserLifeInfo.setCancelTime(shBizUserInfoHistory.getCanceldatetime());
            bossUserLifeInfo.setBizname("上海音证宝");
            bossUserLifeInfo.setUuid(uuid);
            bossUserLifeInfo.setBizUserLifeCircle(bizUserLifeCircle);
            bossUserLifeInfo.setSynSize(shBizUserInfoHistoryList.size());
            bossUserLifeInfo.setSynTime(synTime);
            bossUserLifeInfo.setErrorInfo(errorInfo);
            this.producer.sendUserLifeQueue(bossUserLifeInfo);
        }
    }

    /**
     * 上海音证宝用户历史转boss生命周期
     * @param shBizUserInfoHistory
     * @return
     */
    public BizUserLifeCircle shUserHistoryToBossUserLife(ShBizUserInfoHistory shBizUserInfoHistory){
        BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
        bizUserLifeCircle.setUserNo(shBizUserInfoHistory.getUserNo()); // 业务账号
        bizUserLifeCircle.setUserType(StringUtils.isEmpty(shBizUserInfoHistory.getUsertype()) ? SynConstant.USER_TYPE_PERSONAL : shBizUserInfoHistory.getUsertype()); // 用户类型(1:个人;2:企业)
        bizUserLifeCircle.setEntNo(shBizUserInfoHistory.getUserTel()); // 企业编号
        bizUserLifeCircle.setAccountType(shBizUserInfoHistory.getAccounttype()); // 账号类型(1:主账号;2:子账号)
        bizUserLifeCircle.setBizNo(SynConstant.BIZ_DX_SHANGHAI); // 业务编号
        bizUserLifeCircle.setTcid(shBizUserInfoHistory.getTaocanid()); // 套餐ID
        bizUserLifeCircle.setOrgNo(shBizUserInfoHistory.getOrgNo()); // 组织编号
        bizUserLifeCircle.setRpcode(SynConstant.SHANGHAI_RPCODE); // 省份代码
        bizUserLifeCircle.setCityCode(shBizUserInfoHistory.getCitycode()); // 城市代码
        bizUserLifeCircle.setPhone(shBizUserInfoHistory.getContactphone()); // 电话号码
        bizUserLifeCircle.setRectip(shBizUserInfoHistory.getRectipflag()); //IVR录音提示标志(0:否; 1:是)
        bizUserLifeCircle.setOpenCancel(shBizUserInfoHistory.getUserstatus()); // 开通/退订(1:开通,2:退订)
        bizUserLifeCircle.setSource(shBizUserInfoHistory.getIsignupsource()); // 来源
        bizUserLifeCircle.setTimers(new Date()); // 时间
        bizUserLifeCircle.setCallerVoice(null); // 主叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCalledVoice(null); // 被叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCallerRecord(null); // 主叫录音标记(0:否;1:是)
        bizUserLifeCircle.setCalledRecord(null); // 被叫录音标记(0:否;1:是)
        bizUserLifeCircle.setUpdateTime(shBizUserInfoHistory.getCanceldatetime()); // 更新(操作)时间
        bizUserLifeCircle.setRemark(shBizUserInfoHistory.getRemark()); // 备注
        bizUserLifeCircle.setPhonetype(shBizUserInfoHistory.getPhonetype()); // 号码类别(0:固话;1:手机)
        return bizUserLifeCircle;
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
