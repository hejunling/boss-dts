package com.ancun.datasyn.service.master.impl;

import com.ancun.common.persistence.mapper.master.BizUserVoiceInfoMapper;
import com.ancun.common.persistence.model.master.BizUserVoiceInfo;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datasyn.pojo.voice.VoiceInput;
import com.ancun.datasyn.service.cp.telecom.ICpTelecomVoiceService;
import com.ancun.datasyn.service.cp.unicom.ICpUnicomVoiceService;
import com.ancun.datasyn.service.master.IVoiceservice;
import com.ancun.datasyn.service.provice.IProviceTelecomVoiceService;
import com.ancun.datasyn.service.provice.IProviceUnicomVoiceService;
import com.ancun.datasyn.service.sh.IShVoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * 录音接口实现类
 * User: zkai
 * Date: 2016/5/19
 * Time: 16:02
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class VoiceServiceImpl implements IVoiceservice {
    private static final Logger log = LoggerFactory.getLogger(VoiceServiceImpl.class);

    @Resource
    private BizUserVoiceInfoMapper bizUserVoiceInfoMapper;

    @Resource
    private IProviceTelecomVoiceService proviceTelecomVoiceService;

    @Resource
    private IProviceUnicomVoiceService proviceUnicomVoiceService;

    @Resource
    private ICpTelecomVoiceService cpTelecomVoiceService;

    @Resource
    private ICpUnicomVoiceService cpUnicomVoiceService;

    @Resource
    private IShVoiceService shVoiceService;


    /**
     * 将个业务信息插入到队列
     */
    public void insertVoiceInfoQ(VoiceInput input){
        // cp电信队列
        cpTelecomVoiceService.insertCpTelecomVoiceInfoQ(input);
        // cp联通队列
        cpUnicomVoiceService.insertUnicomVoiceInfoQ(input);
        // 上海电信队列
        shVoiceService.insertShVoiceInfoQ(input);
        // 电信分省
        proviceTelecomVoiceService.insertProviceVoiceInfoQ(input);
        // 联通电信
        proviceUnicomVoiceService.insertProviceVoiceInfoQ(input);
    }


    /**
     * 同步录音信息（插入,修改）
     * @param bizUserVoiceInfo
     */
    public void synBossVoiceInfo(BizUserVoiceInfo bizUserVoiceInfo) {
        // 修改录音信息
        int updateNumber = 0;
        try {
            updateNumber = updateBossVoiceInfo(bizUserVoiceInfo);
        }catch (Exception updateE){
            log.debug("boss 系统录音数据修改失败",updateE);
            log.debug("失败对象 ：bizUserVoiceInfo=" + bizUserVoiceInfo);
            throw new CustomException("boss 系统录音数据修改失败，bizUserVoiceInfo=" + bizUserVoiceInfo+"错误原因="+updateE);
        }
        if (updateNumber > 1) {
            log.debug("boss BOSS系统录音数据异常，bizUserVoiceInfo=" + bizUserVoiceInfo);
            throw new CustomException("BOSS系统录音数据异常，录音编号不唯一bizUserVoiceInfo=" + bizUserVoiceInfo,updateNumber);
        } else if (updateNumber == 0){
            // 插入boss系统
            try {
                insertBossVoiceInfo(bizUserVoiceInfo);
            }catch (Exception insertE){
                log.debug("boss 系统录音数据添加失败",insertE);
                log.debug("失败对象 ：bizUserVoiceInfo=" + bizUserVoiceInfo);
                throw new CustomException("boss 系统录音数据添加失败，bizUserVoiceInfo=" + bizUserVoiceInfo+"错误原因="+insertE);
            }

        }


    }


    /**
     * 添加boss录音信息
     * @param bizUserVoiceInfo
     */
    private void insertBossVoiceInfo(BizUserVoiceInfo bizUserVoiceInfo){
        bizUserVoiceInfoMapper.insert(bizUserVoiceInfo);
    }

    /**
     * 添加boss录音信息
     * @param bizUserVoiceInfo
     */
    private int updateBossVoiceInfo(BizUserVoiceInfo bizUserVoiceInfo){
        Example example = new Example(BizUserVoiceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bizNo",bizUserVoiceInfo.getBizNo());
        criteria.andEqualTo("recordno",bizUserVoiceInfo.getRecordno());
        criteria.andEqualTo("userNo",bizUserVoiceInfo.getUserNo());
        BizUserVoiceInfo updateInfo = new BizUserVoiceInfo();
        updateInfo.setRpcode(bizUserVoiceInfo.getRpcode()); // 省份代码
        updateInfo.setCitycode(bizUserVoiceInfo.getCitycode()); // 城市代码
        updateInfo.setOfilename(bizUserVoiceInfo.getOfilename()); // 原文件名
        updateInfo.setRecordsource(bizUserVoiceInfo.getRecordsource()); // 录音来源(预留)
        updateInfo.setCalltype(bizUserVoiceInfo.getCalltype()); // 通话类型(1:主叫;2:被叫）
        updateInfo.setCallerno(bizUserVoiceInfo.getCallerno()); // 主叫号码
        updateInfo.setCalledno(bizUserVoiceInfo.getCalledno()); // 被叫号码
        updateInfo.setBegintime(bizUserVoiceInfo.getBegintime()); // 通话起始时间
        updateInfo.setEndtime(bizUserVoiceInfo.getEndtime()); // 通话结束时间
        updateInfo.setDuration(bizUserVoiceInfo.getDuration()); // 通话时长(秒)
        updateInfo.setFilesize(bizUserVoiceInfo.getFilesize()); // 文件大小
        updateInfo.setLicenceno(bizUserVoiceInfo.getLicenceno()); // 备案号
        updateInfo.setLicencedatetime(bizUserVoiceInfo.getLicencedatetime()); // 备案时间
        updateInfo.setRecoderremark(bizUserVoiceInfo.getRecoderremark()); // 录音说明
        updateInfo.setCpcertificateflg(bizUserVoiceInfo.getCpcertificateflg()); // 是否申请公证(1:已申请;2:未申请)
        updateInfo.setCpcertificateapplytime(bizUserVoiceInfo.getCpcertificateapplytime()); // 最新申请公证时间
        updateInfo.setCpdelflg(bizUserVoiceInfo.getCpdelflg()); // 是否删除录音(1:已删除;2:未删除;3:已彻底删除)
        updateInfo.setDeldatetime(bizUserVoiceInfo.getDeldatetime()); // 最新删除时间
        updateInfo.setCpgetflg(bizUserVoiceInfo.getCpgetflg()); // 提取码是否有效(1:有效;2:无效)
        updateInfo.setAccesscode(bizUserVoiceInfo.getAccesscode()); // 提取码
        updateInfo.setGetdatetime(bizUserVoiceInfo.getGetdatetime()); // 最新提取时间
        updateInfo.setDuedatetime(bizUserVoiceInfo.getDuedatetime()); // 到期时间
        updateInfo.setCallway(bizUserVoiceInfo.getCallway()); // 拨打方式(预留)
        updateInfo.setIspay(bizUserVoiceInfo.getIspay()); // 是否付费(1:是;2:否)
        updateInfo.setVoicetype(bizUserVoiceInfo.getVoicetype()); // 录音类型（1：包月录音；2：体验录音；3：按次录音）
        return  bizUserVoiceInfoMapper.updateByExampleSelective(updateInfo,example);
    }
}
