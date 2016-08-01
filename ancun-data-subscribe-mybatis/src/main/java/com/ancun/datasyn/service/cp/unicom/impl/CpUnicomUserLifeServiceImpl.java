package com.ancun.datasyn.service.cp.unicom.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.cp.unicom.UniUserInfoHistoryMapper;
import com.ancun.common.persistence.model.cp.unicom.UniUserInfoHistory;
import com.ancun.common.persistence.model.master.BizUserLifeCircle;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.userlife.BossUserLifeInfo;
import com.ancun.datasyn.pojo.userlife.UserLifeInput;
import com.ancun.datasyn.service.cp.unicom.ICpUnicomUserLifeService;
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
 * cp联通生命周期
 * User: zkai
 * Date: 2016/5/27
 * Time: 15:10
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "cpUnicom")
public class CpUnicomUserLifeServiceImpl implements ICpUnicomUserLifeService {

    private static final Logger log = LoggerFactory.getLogger(CpUnicomUserLifeServiceImpl.class);

    @Resource
    UniUserInfoHistoryMapper uniUserInfoHistoryMapper;

    @Resource
    private Producer producer;


    /**
     * 插入cp联通用户生命周期列表
     */
    @Override
    public void insertCpUnicomUserLifeInfoQ(UserLifeInput input){
        // cp电信用户生命周期列表
        List<UniUserInfoHistory> uniUserInfoHistoryList = getSynUserLifeInfo(input);
        checkList(uniUserInfoHistoryList);
        log.info("需要同步cp联通用户生命周期列表 "+ uniUserInfoHistoryList.size() + " 条");
        // 将信息插入到队列中
        insertUserLifeQueue(uniUserInfoHistoryList);
    }

    /**
     * 取得需要同步的个人用户生命周期信息
     * @return
     */
    private List<UniUserInfoHistory> getSynUserLifeInfo(UserLifeInput input){
        Example example = new Example(UniUserInfoHistory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(UniUserInfoHistory.class).createCriteria().andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return uniUserInfoHistoryMapper.selectByExample(example);
    }

    /**
     * 将信息插入到生命周期队列中
     * @param uniUserInfoHistoryList
     */
    private void insertUserLifeQueue(List<UniUserInfoHistory> uniUserInfoHistoryList){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (UniUserInfoHistory uniUserInfoHistory: uniUserInfoHistoryList) {
            BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
            BossUserLifeInfo bossUserLifeInfo = new BossUserLifeInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserLifeCircle = cpUserHistoryToBossUserLife(uniUserInfoHistory);
            }catch (Exception e){
                log.debug("cp联通用户历史字段对应boss字段异常 = {},uniUserInfoHistory = {}",e,uniUserInfoHistory);
                errorInfo = "cp联通用户历史字段对应boss字段异常:"+ e.getMessage() + " uniUserInfoHistory:"+uniUserInfoHistory;
            }

            bossUserLifeInfo.setBizname("cp联通");
            bossUserLifeInfo.setUuid(uuid);
            bossUserLifeInfo.setOpenTime(uniUserInfoHistory.getOpendatetime());
            bossUserLifeInfo.setCancelTime(uniUserInfoHistory.getCanceldatetime());
            bossUserLifeInfo.setBizUserLifeCircle(bizUserLifeCircle);
            bossUserLifeInfo.setSynSize(uniUserInfoHistoryList.size());
            bossUserLifeInfo.setSynTime(synTime);
            bossUserLifeInfo.setErrorInfo(errorInfo);
            this.producer.sendUserLifeQueue(bossUserLifeInfo);
        }
    }

    /**
     * cp 联通用户历史转boss生命周期
     * @param uniUserInfoHistory
     * @return
     */
    public BizUserLifeCircle cpUserHistoryToBossUserLife(UniUserInfoHistory uniUserInfoHistory){
        BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
        bizUserLifeCircle.setUserNo(uniUserInfoHistory.getUserno()); // 业务账号
        bizUserLifeCircle.setUserType(StringUtils.isEmpty(uniUserInfoHistory.getUsertype()) ? SynConstant.USER_TYPE_PERSONAL : uniUserInfoHistory.getUsertype()); // 用户类型(1:个人;2:企业)
        bizUserLifeCircle.setEntNo(null); // 企业编号
        bizUserLifeCircle.setAccountType(SynConstant.ACCOUNTTYPE_PERSONAL); // 账号类型(1:主账号;2:子账号)
        bizUserLifeCircle.setBizNo(SynConstant.BIZ_LT_CP); // 业务编号
        bizUserLifeCircle.setTcid(null); // 套餐ID
        bizUserLifeCircle.setOrgNo(null); // 组织编号
        bizUserLifeCircle.setRpcode(SynConstant.ALL_PROVICE); // 省份代码
        bizUserLifeCircle.setCityCode(uniUserInfoHistory.getCitycode()); // 城市代码
        bizUserLifeCircle.setPhone(uniUserInfoHistory.getPhone()); // 电话号码
        bizUserLifeCircle.setRectip(uniUserInfoHistory.getRectipflag()); //IVR录音提示标志(0:否; 1:是)
        bizUserLifeCircle.setOpenCancel(SynConstant.USER_STATE_CANCEL); // 开通/退订(1:开通,2:退订)
        bizUserLifeCircle.setSource(uniUserInfoHistory.getIsignupsource()); // 来源
        bizUserLifeCircle.setTimers(new Date()); // 时间
        bizUserLifeCircle.setCallerVoice(null); // 主叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCalledVoice(null); // 被叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCallerRecord(null); // 主叫录音标记(0:否;1:是)
        bizUserLifeCircle.setCalledRecord(null); // 被叫录音标记(0:否;1:是)
        bizUserLifeCircle.setUpdateTime(uniUserInfoHistory.getCanceldatetime()); // 更新(操作)时间
        bizUserLifeCircle.setRemark(uniUserInfoHistory.getRemark()); // 备注
        bizUserLifeCircle.setPhonetype(uniUserInfoHistory.getPhonetype()); // 号码类别(0:固话;1:手机)
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
