package com.ancun.datasyn.service.master.impl;

import com.ancun.common.persistence.mapper.master.BizUserLifeCircleMapper;
import com.ancun.common.persistence.model.master.BizUserLifeCircle;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.userlife.UserLifeInput;
import com.ancun.datasyn.service.cp.telecom.ICpTelUserLifeService;
import com.ancun.datasyn.service.cp.unicom.ICpUnicomUserLifeService;
import com.ancun.datasyn.service.master.IUserLifeService;
import com.ancun.datasyn.service.provice.IProviceTelUserHistoryService;
import com.ancun.datasyn.service.provice.IProviceUnicomUserHistoryService;
import com.ancun.datasyn.service.sh.IShUserLifeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户生命周期接口实现类
 * User: zkai
 * Date: 2016/5/30
 * Time: 14:19
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class UserLifeServiceImpl implements IUserLifeService {
    private static final Logger log = LoggerFactory.getLogger(UserLifeServiceImpl.class);

    @Resource
    BizUserLifeCircleMapper bizUserLifeCircleMapper;

    @Resource
    ICpTelUserLifeService cpTelUserLifeService;

    @Resource
    ICpUnicomUserLifeService cpUnicomUserLifeService;

    @Resource
    IShUserLifeService shUserLifeService;

    @Resource
    IProviceTelUserHistoryService proviceTelUserHistoryService;

    @Resource
    IProviceUnicomUserHistoryService proviceUnicomUserHistoryService;

    /**
     * 将个业务信息插入到队列
     */
    public void insertUserLifeInfoQ(UserLifeInput input){
        // cp联通队列
        cpUnicomUserLifeService.insertCpUnicomUserLifeInfoQ(input);
        // cp电信队列
        cpTelUserLifeService.insertCpTelUserLifeInfoQ(input);
        // 上海电信队列
        shUserLifeService.insertShUserLifeInfoQ(input);
        // 电信分省
        proviceTelUserHistoryService.insertProviceUserHistoryInfoQ(input);
        // 联通电信
        proviceUnicomUserHistoryService.insertProviceUserHistoryInfoQ(input);
    }

    /**
     * 同步boss用户历史信息（插入）
     * @param bizUserLifeCircle
     */
    public void synUserHistory(BizUserLifeCircle bizUserLifeCircle, Date optenTime , Date cancelTime){

        BizUserLifeCircle bizUserLifeCircleOpen = setBizUserLifeCircle(bizUserLifeCircle,optenTime,SynConstant.USER_STATE_OPEN);

        BizUserLifeCircle bizUserLifeCircleCancel = setBizUserLifeCircle(bizUserLifeCircle,optenTime,SynConstant.USER_STATE_CANCEL);
        // 插入boss系统
        try {
            insertBossUserLifeInfo(bizUserLifeCircleOpen);
            insertBossUserLifeInfo(bizUserLifeCircleCancel);
        }catch (Exception insertE){
            log.error("boss 系统用户生命周期添加失败",insertE);
            throw new CustomException("boss 系统用户生命周期添加失败，bizComboInfo=" + bizUserLifeCircle+"错误原因="+insertE);
        }
    }

    /**
     * 插入用户生命周期表
     * @param bizUserLifeCircle
     */
    private void  insertBossUserLifeInfo(BizUserLifeCircle bizUserLifeCircle){
        bizUserLifeCircleMapper.insert(bizUserLifeCircle);
    }


    private BizUserLifeCircle setBizUserLifeCircle(BizUserLifeCircle objet, Date time , String status){
        BizUserLifeCircle bizUserLifeCircle = new BizUserLifeCircle();
        bizUserLifeCircle.setUserNo(objet.getUserNo()); // 业务账号
        bizUserLifeCircle.setUserType(objet.getUserType()); // 用户类型(1:个人;2:企业)
        bizUserLifeCircle.setEntNo(objet.getEntNo()); // 企业编号
        bizUserLifeCircle.setAccountType(objet.getAccountType()); // 账号类型(1:主账号;2:子账号)
        bizUserLifeCircle.setBizNo(objet.getBizNo()); // 业务编号
        bizUserLifeCircle.setTcid(objet.getTcid()); // 套餐ID
        bizUserLifeCircle.setOrgNo(objet.getOrgNo()); // 组织编号
        bizUserLifeCircle.setRpcode(objet.getRpcode()); // 省份代码
        bizUserLifeCircle.setCityCode(objet.getCityCode()); // 城市代码
        bizUserLifeCircle.setPhone(objet.getPhone()); // 电话号码
        bizUserLifeCircle.setRectip(objet.getRectip()); //IVR录音提示标志(0:否; 1:是)
        bizUserLifeCircle.setOpenCancel(status); // 开通/退订(1:开通,2:退订)
        bizUserLifeCircle.setSource(objet.getSource()); // 来源
        bizUserLifeCircle.setTimers(new Date()); // 时间
        bizUserLifeCircle.setCallerVoice(objet.getCallerVoice()); // 主叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCalledVoice(objet.getCalledVoice()); // 被叫提示音(0:关闭;1:开启)
        bizUserLifeCircle.setCallerRecord(objet.getCallerRecord()); // 主叫录音标记(0:否;1:是)
        bizUserLifeCircle.setCalledRecord(objet.getCalledVoice()); // 被叫录音标记(0:否;1:是)
        bizUserLifeCircle.setUpdateTime(time); // 更新(操作)时间
        bizUserLifeCircle.setRemark(objet.getRemark()); // 备注
        bizUserLifeCircle.setPhonetype(objet.getPhonetype()); // 号码类别(0:固话;1:手机)
        return bizUserLifeCircle;
    }

}
