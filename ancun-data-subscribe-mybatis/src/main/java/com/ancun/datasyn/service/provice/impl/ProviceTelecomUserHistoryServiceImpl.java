package com.ancun.datasyn.service.provice.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.dx.EntUserInfoHistoryMapper;
import com.ancun.common.persistence.mapper.dx.UserInfoHistoryMapper;
import com.ancun.common.persistence.model.dx.EntUserInfoHistory;
import com.ancun.common.persistence.model.dx.UserInfoHistory;
import com.ancun.common.persistence.model.master.BizUserLifeCircle;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.CacheConstant;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.userlife.BossUserLifeInfo;
import com.ancun.datasyn.pojo.userlife.UserLifeInput;
import com.ancun.datasyn.service.provice.IProviceTelUserHistoryService;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 分省电信用户历史接口实现类
 * User: zkai
 * Date: 2016/5/30
 * Time: 19:25
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "dxProvince")
public class ProviceTelecomUserHistoryServiceImpl implements IProviceTelUserHistoryService {

    private static final Logger log = LoggerFactory.getLogger(ProviceTelecomUserHistoryServiceImpl.class);

    @Resource
    UserInfoHistoryMapper userInfoHistoryMapper;

    @Resource
    EntUserInfoHistoryMapper entUserInfoHistoryMapper;

    @Resource
    private Producer producer;

    /**
     * 插入分省电信用户历史队列
     */
    public void insertProviceUserHistoryInfoQ(UserLifeInput input){
        // 个人用户历史列表
        List<UserInfoHistory> userInfoHistories = getSynPesonUserHistoryList(input);

        log.info("需要同步分省电信个人用户历史信息 "+ userInfoHistories.size() + " 条");
        // 将个人用户信息插入到队列中
        insertUserInfoHistoryQueue(userInfoHistories);

        // 企业用户历史列表
        List<EntUserInfoHistory> entUserInfoHistories = getSynEntnUserHistoryList(input);
        log.info("需要同步分省电信企业用户历史列表 "+ entUserInfoHistories.size() + " 条");
        // 将企业用户历史信息插入到队列中
        insertEntUserInfoHistoryQueue(entUserInfoHistories);
    }

    /**
     * 取得需要同步的个人用户历史信息
     * @return
     */
    private List<UserInfoHistory> getSynPesonUserHistoryList(UserLifeInput input){
        Example example = new Example(UserInfoHistory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(UserInfoHistory.class).createCriteria().andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return userInfoHistoryMapper.selectByExample(example);
    }

    /**
     * 取得需要同步的企业用户历史信息
     * @return
     */
    private List<EntUserInfoHistory> getSynEntnUserHistoryList(UserLifeInput input){
        Example example = new Example(EntUserInfoHistory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(EntUserInfoHistory.class).createCriteria().andEqualTo("updateTime",SynConstant.DEFAULT_DATE));
        return entUserInfoHistoryMapper.selectByExample(example);
    }

    /**
     * 分省个人用户历史字段 对应 boss字段
     * @param userInfoHistory
     * @return
     */
    private BizUserLifeCircle proviceUserHistoryToBoss(UserInfoHistory userInfoHistory){
        BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
        bizUserLifeCircle.setUserNo(userInfoHistory.getUserno()); // 业务账号
        bizUserLifeCircle.setUserType(StringUtils.isEmpty(userInfoHistory.getUsertype()) ? SynConstant.USER_TYPE_PERSONAL : userInfoHistory.getUsertype()); // 用户类型(1:个人;2:企业)
        bizUserLifeCircle.setEntNo(null); // 企业编号
        bizUserLifeCircle.setAccountType(SynConstant.ACCOUNTTYPE_PERSONAL); // 账号类型(1:主账号;2:子账号)

        String bizno = CacheConstant.DX_PROVINCE_MAP.get(userInfoHistory.getRpcode());
        if(StringUtil.isNotBlank(bizno)){
            bizUserLifeCircle.setBizNo( bizno); // 业务编号
        }else {
            throw new CustomException("PROVICE_BIZ 中rpcode对应的Bizno不存在");
        }
        bizUserLifeCircle.setTcid(userInfoHistory.getTaocanid()); // 套餐ID
        bizUserLifeCircle.setOrgNo(null); // 组织编号
        bizUserLifeCircle.setRpcode(userInfoHistory.getRpcode()); // 省份代码
        bizUserLifeCircle.setCityCode(userInfoHistory.getCitycode()); // 城市代码
        bizUserLifeCircle.setPhone(userInfoHistory.getPhone()); // 电话号码
        bizUserLifeCircle.setRectip(userInfoHistory.getRectipflag()); //IVR录音提示标志(0:否; 1:是)
        bizUserLifeCircle.setOpenCancel(userInfoHistory.getUserstatus()); // 开通/退订(1:开通,2:退订)
        bizUserLifeCircle.setSource(userInfoHistory.getIsignupsource()); // 来源
        bizUserLifeCircle.setTimers(new Date()); // 时间
        bizUserLifeCircle.setCallerVoice(userInfoHistory.getCallervoice()); // 主叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCalledVoice(userInfoHistory.getCalledvoice()); // 被叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCallerRecord(userInfoHistory.getCallerrecordvoice()); // 主叫录音标记(0:否;1:是)
        bizUserLifeCircle.setCalledRecord(userInfoHistory.getCalledrecordvoice()); // 被叫录音标记(0:否;1:是)
        bizUserLifeCircle.setUpdateTime(userInfoHistory.getCanceldatetime()); // 更新(操作)时间
        bizUserLifeCircle.setRemark(userInfoHistory.getRemark()); // 备注
        bizUserLifeCircle.setPhonetype(userInfoHistory.getPhonetype()); // 号码类别(0:固话;1:手机)
        return bizUserLifeCircle;
    }

    /**
     * 分省企业用户历史 对应 boss字段
     * @param entUserInfoHistory
     * @return
     */
    private BizUserLifeCircle proviceEntUserHistoryToBoss(EntUserInfoHistory entUserInfoHistory){
        BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
        bizUserLifeCircle.setUserNo(entUserInfoHistory.getUserno()); // 业务账号
        bizUserLifeCircle.setUserType(SynConstant.USER_TYPE_ENTERPRISES); // 用户类型(1:个人;2:企业)
        bizUserLifeCircle.setEntNo(entUserInfoHistory.getUserTel()); // 企业编号
        bizUserLifeCircle.setAccountType(entUserInfoHistory.getAccounttype()); // 账号类型(1:主账号;2:子账号)

        String bizno = CacheConstant.DX_PROVINCE_MAP.get(entUserInfoHistory.getRpcode());
        if(StringUtil.isNotBlank(bizno)){
            bizUserLifeCircle.setBizNo( bizno); // 业务编号
        }else {
            throw new CustomException("PROVICE_BIZ 中rpcode对应的Bizno不存在");
        }
        bizUserLifeCircle.setTcid(entUserInfoHistory.getTaocanid()); // 套餐ID
        bizUserLifeCircle.setOrgNo(entUserInfoHistory.getOrgNo()); // 组织编号
        bizUserLifeCircle.setRpcode(entUserInfoHistory.getRpcode()); // 省份代码
        bizUserLifeCircle.setCityCode(entUserInfoHistory.getCitycode()); // 城市代码
        bizUserLifeCircle.setPhone(entUserInfoHistory.getContactphone()); // 电话号码
        bizUserLifeCircle.setRectip(entUserInfoHistory.getRectipflag()); //IVR录音提示标志(0:否; 1:是)
        bizUserLifeCircle.setOpenCancel(entUserInfoHistory.getUserstatus()); // 开通/退订(1:开通,2:退订)
        bizUserLifeCircle.setSource(entUserInfoHistory.getIsignupsource()); // 来源
        bizUserLifeCircle.setTimers(new Date()); // 时间
        bizUserLifeCircle.setCallerVoice(entUserInfoHistory.getCallervoice()); // 主叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCalledVoice(entUserInfoHistory.getCalledvoice()); // 被叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCallerRecord(entUserInfoHistory.getCallerrecordvoice()); // 主叫录音标记(0:否;1:是)
        bizUserLifeCircle.setCalledRecord(entUserInfoHistory.getCalledrecordvoice()); // 被叫录音标记(0:否;1:是)
        bizUserLifeCircle.setUpdateTime(entUserInfoHistory.getCanceldatetime()); // 更新(操作)时间
        bizUserLifeCircle.setRemark(entUserInfoHistory.getRemark()); // 备注
        bizUserLifeCircle.setPhonetype(entUserInfoHistory.getPhonetype()); // 号码类别(0:固话;1:手机)
        return bizUserLifeCircle;
    }


    /**
     * 将信息插入到个人队列中
     * @param userInfoHistoryList
     */
    private void insertUserInfoHistoryQueue(List<UserInfoHistory> userInfoHistoryList){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (UserInfoHistory userInfoHistory: userInfoHistoryList) {
            BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
            BossUserLifeInfo bossUserLifeInfo = new BossUserLifeInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserLifeCircle = proviceUserHistoryToBoss(userInfoHistory);
            }catch (Exception e){
                log.debug("电信分省个人用户历史字段对应boss字段异常 = {},userInfoHistory = {}",e,userInfoHistory);
                errorInfo = "电信分省个人用户历史对应boss字段异常:"+ e.getMessage() + " userInfoHistory:"+userInfoHistory;
            }
            bossUserLifeInfo.setOpenTime(userInfoHistory.getOpendatetime());
            bossUserLifeInfo.setCancelTime(userInfoHistory.getCanceldatetime());
            bossUserLifeInfo.setBizname("分省电信个人");
            bossUserLifeInfo.setUuid(uuid);
            bossUserLifeInfo.setBizUserLifeCircle(bizUserLifeCircle);
            bossUserLifeInfo.setSynSize(userInfoHistoryList.size());
            bossUserLifeInfo.setSynTime(synTime);
            bossUserLifeInfo.setErrorInfo(errorInfo);
            this.producer.sendUserLifeQueue(bossUserLifeInfo);
        }
    }

    /**
     * 将信息插入到企业队列中
     * @param entUserInfoHistories
     */
    private void insertEntUserInfoHistoryQueue(List<EntUserInfoHistory> entUserInfoHistories){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (EntUserInfoHistory entUserInfoHistory: entUserInfoHistories) {
            BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
            BossUserLifeInfo bossUserLifeInfo = new BossUserLifeInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserLifeCircle = proviceEntUserHistoryToBoss(entUserInfoHistory);
            }catch (Exception e){
                log.debug("电信分省企业用户历史字段对应boss字段异常 = {},entUserInfoHistory = {}",e,entUserInfoHistory);
                errorInfo = "电信分省企业用户历史字段对应boss字段异常:"+ e.getMessage() + " entUserInfoHistory:"+entUserInfoHistory;
            }
            bossUserLifeInfo.setOpenTime(entUserInfoHistory.getOpendatetime());
            bossUserLifeInfo.setCancelTime(entUserInfoHistory.getCanceldatetime());
            bossUserLifeInfo.setBizname("分省电信企业");
            bossUserLifeInfo.setUuid(uuid);
            bossUserLifeInfo.setBizUserLifeCircle(bizUserLifeCircle);
            bossUserLifeInfo.setSynSize(entUserInfoHistories.size());
            bossUserLifeInfo.setSynTime(synTime);
            bossUserLifeInfo.setErrorInfo(errorInfo);
            this.producer.sendUserLifeQueue(bossUserLifeInfo);
//            this.producer.sendVoiceQueue(bizUserVoiceInfo);
        }
    }


}
