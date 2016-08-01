package com.ancun.datasyn.service.cp.telecom.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.cp.telecom.TelUserVoiceInfoMapper;
import com.ancun.common.persistence.model.cp.telecom.TelUserVoiceInfo;
import com.ancun.common.persistence.model.master.BizUserVoiceInfo;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.voice.BossVoiceInfo;
import com.ancun.datasyn.pojo.voice.VoiceInput;
import com.ancun.datasyn.service.cp.telecom.ICpTelecomVoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * cp电信录音接口实现类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:25
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "cpTelecom")
public class CpTelecomVoiceServiceImpl implements ICpTelecomVoiceService {

    private static final Logger log = LoggerFactory.getLogger(CpTelecomVoiceServiceImpl.class);

    @Resource
    TelUserVoiceInfoMapper telUserVoiceInfoMapper;


    @Resource
    private Producer producer;

    /**
     * cp电信录音队列
     */
    public void insertCpTelecomVoiceInfoQ(VoiceInput input){

        // cp电信用户录音列表
        List<TelUserVoiceInfo> telUserVoiceInfos = getSynVoiceList(input);
        checkList(telUserVoiceInfos);
        log.info("需要同步cp电信用户录音列表 "+ telUserVoiceInfos.size() + " 条");
        // 将信息插入到队列中
        insertVoiceQueue(telUserVoiceInfos);
    }

    /**
     * 取得需要同步的个人用户录音信息
     * @return
     */
    private List<TelUserVoiceInfo> getSynVoiceList(VoiceInput input){
        Example example = new Example(TelUserVoiceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(TelUserVoiceInfo.class).createCriteria().andEqualTo("updateTime",SynConstant.DEFAULT_DATE));
        return telUserVoiceInfoMapper.selectByExample(example);
    }


    /**
     * cp电信录音字段 对应 boss字段
     * @param telUserVoiceInfo
     * @return
     */
    private BizUserVoiceInfo cpVoiceToBossVoice(TelUserVoiceInfo telUserVoiceInfo){
        BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
        bizUserVoiceInfo.setRecordno(telUserVoiceInfo.getRecordno()); // 文件记录编号
        bizUserVoiceInfo.setUserNo(telUserVoiceInfo.getIuserno()); // 业务用户编号
        bizUserVoiceInfo.setEntNo(null); // 企业编号
        bizUserVoiceInfo.setBizNo(SynConstant.BIZ_DX_CP); // 业务编号
        bizUserVoiceInfo.setRpcode(SynConstant.ALL_PROVICE); // 省份代码
        bizUserVoiceInfo.setCitycode(null); // 城市代码
        bizUserVoiceInfo.setOfilename(telUserVoiceInfo.getOfilename()); // 原文件名
        bizUserVoiceInfo.setRecordsource(telUserVoiceInfo.getRecordsource()); // 录音来源(预留)
        bizUserVoiceInfo.setCalltype(telUserVoiceInfo.getIcalltype()); // 通话类型(1:主叫;2:被叫）
        bizUserVoiceInfo.setCallerno(telUserVoiceInfo.getCallerno()); // 主叫号码
        bizUserVoiceInfo.setCalledno(telUserVoiceInfo.getCalledno()); // 被叫号码
        bizUserVoiceInfo.setBegintime(telUserVoiceInfo.getBegintime()); // 通话起始时间
        bizUserVoiceInfo.setEndtime(telUserVoiceInfo.getEndtime()); // 通话结束时间
        bizUserVoiceInfo.setDuration(telUserVoiceInfo.getDuration()); // 通话时长(秒)
        bizUserVoiceInfo.setFilesize(telUserVoiceInfo.getFilesize()); // 文件大小
        bizUserVoiceInfo.setLicenceno(telUserVoiceInfo.getLicenceno()); // 备案号
        bizUserVoiceInfo.setLicencedatetime(telUserVoiceInfo.getLicencedatetime()); // 备案时间
        bizUserVoiceInfo.setRecoderremark(telUserVoiceInfo.getRecoderremark()); // 录音说明
        bizUserVoiceInfo.setCpcertificateflg(telUserVoiceInfo.getCpcertificateflg()); // 是否申请公证(1:已申请;2:未申请)
        bizUserVoiceInfo.setCpcertificateapplytime(telUserVoiceInfo.getCpcertificateapplytime()); // 最新申请公证时间
        bizUserVoiceInfo.setCpdelflg(telUserVoiceInfo.getCpdelflg()); // 是否删除录音(1:已删除;2:未删除;3:已彻底删除)
        bizUserVoiceInfo.setDeldatetime(telUserVoiceInfo.getDeldatetime()); // 最新删除时间
        bizUserVoiceInfo.setCpgetflg(telUserVoiceInfo.getCpgetflg()); // 提取码是否有效(1:有效;2:无效)
        bizUserVoiceInfo.setAccesscode(telUserVoiceInfo.getAccesscode()); // 提取码
        bizUserVoiceInfo.setGetdatetime(telUserVoiceInfo.getGetdatetime()); // 最新提取时间
        bizUserVoiceInfo.setDuedatetime(telUserVoiceInfo.getDuedatetime()); // 到期时间
        bizUserVoiceInfo.setCallway(telUserVoiceInfo.getCallway()); // 拨打方式(预留)
        bizUserVoiceInfo.setIspay(null); // 是否付费(1:是;2:否)
        bizUserVoiceInfo.setVoicetype(null); // 录音类型（1：包月录音；2：体验录音；3：按次录音）
        return bizUserVoiceInfo;
    }


    /**
     * 将信息插入到录音队列中
     * @param telUserVoiceInfos
     */
    private void insertVoiceQueue(List<TelUserVoiceInfo> telUserVoiceInfos){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (TelUserVoiceInfo telUserVoiceInfo: telUserVoiceInfos) {
            BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
            BossVoiceInfo bossVoiceInfo = new BossVoiceInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserVoiceInfo = cpVoiceToBossVoice(telUserVoiceInfo);
            }catch (Exception e){
                log.debug("cp电信录音字段对应boss字段异常 = {},telUserVoiceInfo = {}",e,telUserVoiceInfo);
                errorInfo = "cp电信录音字段对应boss字段异常:"+ e.getMessage() + " telUserVoiceInfo:"+telUserVoiceInfo;
            }
            bossVoiceInfo.setBizname("cp电信");
            bossVoiceInfo.setUuid(uuid);
            bossVoiceInfo.setBizUserVoiceInfo(bizUserVoiceInfo);
            bossVoiceInfo.setSynSize(telUserVoiceInfos.size());
            bossVoiceInfo.setSynTime(synTime);
            bossVoiceInfo.setErrorInfo(errorInfo);
            this.producer.sendVoiceQueue(bossVoiceInfo);
        }
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
