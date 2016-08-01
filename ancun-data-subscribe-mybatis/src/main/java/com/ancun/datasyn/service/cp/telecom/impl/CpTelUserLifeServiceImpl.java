package com.ancun.datasyn.service.cp.telecom.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.cp.telecom.TelUserInfoHistoryMapper;
import com.ancun.common.persistence.model.cp.telecom.TelUserInfoHistory;
import com.ancun.common.persistence.model.master.BizUserLifeCircle;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.userlife.BossUserLifeInfo;
import com.ancun.datasyn.pojo.userlife.UserLifeInput;
import com.ancun.datasyn.service.cp.telecom.ICpTelUserLifeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * cp电信生命周期
 * User: zkai
 * Date: 2016/5/27
 * Time: 15:10
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "cpTelecom")
public class CpTelUserLifeServiceImpl implements ICpTelUserLifeService{

    private static final Logger log = LoggerFactory.getLogger(CpTelUserLifeServiceImpl.class);

    @Resource
    TelUserInfoHistoryMapper telUserInfoHistoryMapper;

    @Resource
    private Producer producer;


    /**
     * 插入cp电信用户生命周期列表
     */
    @Override
    public void insertCpTelUserLifeInfoQ(UserLifeInput input){
        // cp电信用户生命周期列表
        List<TelUserInfoHistory> telUserInfoHistoryList = getSynUserLifeInfo(input);
        checkList(telUserInfoHistoryList);
        log.info("需要同步cp联通用户生命周期列表 "+ telUserInfoHistoryList.size() + " 条");
        // 将信息插入到队列中
        insertUserLifeQueue(telUserInfoHistoryList);
    }

    /**
     * 取得需要同步的个人用户生命周期信息
     * @return
     */
    private List<TelUserInfoHistory> getSynUserLifeInfo(UserLifeInput input){
        Example example = new Example(TelUserInfoHistory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(TelUserInfoHistory.class).createCriteria().andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return telUserInfoHistoryMapper.selectByExample(example);
    }

    /**
     * 将信息插入到生命周期队列中
     * @param telUserInfoHistories
     */
    private void insertUserLifeQueue(List<TelUserInfoHistory> telUserInfoHistories){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (TelUserInfoHistory telUserInfoHistory: telUserInfoHistories) {
            BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
            BossUserLifeInfo bossUserLifeInfo = new BossUserLifeInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserLifeCircle = cpUserHistoryToBossUserLife(telUserInfoHistory);
            }catch (Exception e){
                log.debug("cp电信用户历史字段对应boss字段异常 = {},telUserInfoHistory = {}",e,telUserInfoHistory);
                errorInfo = "cp电信用户历史字段对应boss字段异常:"+ e.getMessage() + " telUserInfoHistory:"+telUserInfoHistory;
            }

            bossUserLifeInfo.setBizname("cp电信");
            bossUserLifeInfo.setUuid(uuid);
            bossUserLifeInfo.setOpenTime(telUserInfoHistory.getOpendatetime());
            bossUserLifeInfo.setCancelTime(telUserInfoHistory.getCanceldatetime());
            bossUserLifeInfo.setBizUserLifeCircle(bizUserLifeCircle);
            bossUserLifeInfo.setSynSize(telUserInfoHistories.size());
            bossUserLifeInfo.setSynTime(synTime);
            bossUserLifeInfo.setErrorInfo(errorInfo);
            this.producer.sendUserLifeQueue(bossUserLifeInfo);
        }
    }

    /**
     * cp 电信用户历史转boss生命周期
     * @param telUserInfoHistory
     * @return
     */
    public BizUserLifeCircle cpUserHistoryToBossUserLife(TelUserInfoHistory telUserInfoHistory){
        BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
        bizUserLifeCircle.setUserNo(telUserInfoHistory.getUserno()); // 业务账号
        bizUserLifeCircle.setUserType(StringUtils.isEmpty(telUserInfoHistory.getUsertype()) ? SynConstant.USER_TYPE_PERSONAL : telUserInfoHistory.getUsertype()); // 用户类型(1:个人;2:企业)
        bizUserLifeCircle.setEntNo(null); // 企业编号
        bizUserLifeCircle.setAccountType(SynConstant.ACCOUNTTYPE_PERSONAL); // 账号类型(1:主账号;2:子账号)
        bizUserLifeCircle.setBizNo(SynConstant.BIZ_DX_CP); // 业务编号
        bizUserLifeCircle.setTcid(null); // 套餐ID
        bizUserLifeCircle.setOrgNo(null); // 组织编号
        bizUserLifeCircle.setRpcode(SynConstant.ALL_PROVICE); // 省份代码
        bizUserLifeCircle.setCityCode(telUserInfoHistory.getCitycode()); // 城市代码
        bizUserLifeCircle.setPhone(telUserInfoHistory.getPhone()); // 电话号码
        bizUserLifeCircle.setRectip(telUserInfoHistory.getRectipflag()); //IVR录音提示标志(0:否; 1:是)
        bizUserLifeCircle.setOpenCancel(SynConstant.USER_STATE_CANCEL); // 开通/退订(1:开通,2:退订)
        bizUserLifeCircle.setSource(telUserInfoHistory.getIsignupsource()); // 来源
        bizUserLifeCircle.setTimers(new Date()); // 时间
        bizUserLifeCircle.setCallerVoice(null); // 主叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCalledVoice(null); // 被叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCallerRecord(null); // 主叫录音标记(0:否;1:是)
        bizUserLifeCircle.setCalledRecord(null); // 被叫录音标记(0:否;1:是)
        bizUserLifeCircle.setUpdateTime(telUserInfoHistory.getCanceldatetime()); // 更新(操作)时间
        bizUserLifeCircle.setRemark(null); // 备注
        bizUserLifeCircle.setPhonetype(telUserInfoHistory.getPhonetype()); // 号码类别(0:固话;1:手机)
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
