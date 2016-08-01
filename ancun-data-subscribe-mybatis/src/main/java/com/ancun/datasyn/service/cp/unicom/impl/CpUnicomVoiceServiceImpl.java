package com.ancun.datasyn.service.cp.unicom.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.cp.unicom.UniUserVoiceInfoMapper;
import com.ancun.common.persistence.model.cp.unicom.UniUserVoiceInfo;
import com.ancun.common.persistence.model.master.BizUserVoiceInfo;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.voice.BossVoiceInfo;
import com.ancun.datasyn.pojo.voice.VoiceInput;
import com.ancun.datasyn.service.cp.unicom.ICpUnicomVoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * cp联通录音接口实现类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:25
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "cpUnicom")
public class CpUnicomVoiceServiceImpl implements ICpUnicomVoiceService {

    private static final Logger log = LoggerFactory.getLogger(CpUnicomVoiceServiceImpl.class);

    @Resource
    UniUserVoiceInfoMapper uniUserVoiceInfoMapper;


    @Resource
    private Producer producer;

    /**
     * 插入cp联通录音队列
     */
    public void insertUnicomVoiceInfoQ(VoiceInput input){

        // cp联通用户录音列表
        List<UniUserVoiceInfo> uniUserVoiceInfoList = getSynVoiceList(input);
        checkList(uniUserVoiceInfoList);
        log.info("需要同步cp联通用户录音列表 "+ uniUserVoiceInfoList.size() + " 条");
        // 将信息插入到队列中
        insertVoiceQueue(uniUserVoiceInfoList);
    }

    /**
     * 取得需要同步的个人用户录音信息
     * @return
     */
    private List<UniUserVoiceInfo> getSynVoiceList(VoiceInput input){
        Example example = new Example(UniUserVoiceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(UniUserVoiceInfo.class).createCriteria().andEqualTo("updateTime",SynConstant.DEFAULT_DATE));
        return uniUserVoiceInfoMapper.selectByExample(example);
    }


    /**
     * cp联通录音字段 对应 boss字段
     * @param uniUserVoiceInfo
     * @return
     */
    private BizUserVoiceInfo cpVoiceToBossVoice(UniUserVoiceInfo uniUserVoiceInfo){
        BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
        bizUserVoiceInfo.setRecordno(uniUserVoiceInfo.getRecordno()); // 文件记录编号
        bizUserVoiceInfo.setUserNo(uniUserVoiceInfo.getIuserno()); // 业务用户编号
        bizUserVoiceInfo.setEntNo(null); // 企业编号
        bizUserVoiceInfo.setBizNo(SynConstant.BIZ_LT_CP); // 业务编号
        bizUserVoiceInfo.setRpcode(SynConstant.ALL_PROVICE); // 省份代码
        bizUserVoiceInfo.setCitycode(null); // 城市代码
        bizUserVoiceInfo.setOfilename(uniUserVoiceInfo.getOfilename()); // 原文件名
        bizUserVoiceInfo.setRecordsource(uniUserVoiceInfo.getRecordsource()); // 录音来源(预留)
        bizUserVoiceInfo.setCalltype(uniUserVoiceInfo.getIcalltype()); // 通话类型(1:主叫;2:被叫）
        bizUserVoiceInfo.setCallerno(uniUserVoiceInfo.getCallerno()); // 主叫号码
        bizUserVoiceInfo.setCalledno(uniUserVoiceInfo.getCalledno()); // 被叫号码
        bizUserVoiceInfo.setBegintime(uniUserVoiceInfo.getBegintime()); // 通话起始时间
        bizUserVoiceInfo.setEndtime(uniUserVoiceInfo.getEndtime()); // 通话结束时间
        bizUserVoiceInfo.setDuration(uniUserVoiceInfo.getDuration()); // 通话时长(秒)
        bizUserVoiceInfo.setFilesize(uniUserVoiceInfo.getFilesize()); // 文件大小
        bizUserVoiceInfo.setLicenceno(uniUserVoiceInfo.getLicenceno()); // 备案号
        bizUserVoiceInfo.setLicencedatetime(uniUserVoiceInfo.getLicencedatetime()); // 备案时间
        bizUserVoiceInfo.setRecoderremark(uniUserVoiceInfo.getRecoderremark()); // 录音说明
        bizUserVoiceInfo.setCpcertificateflg(uniUserVoiceInfo.getCpcertificateflg()); // 是否申请公证(1:已申请;2:未申请)
        bizUserVoiceInfo.setCpcertificateapplytime(uniUserVoiceInfo.getCpcertificateapplytime()); // 最新申请公证时间
        bizUserVoiceInfo.setCpdelflg(uniUserVoiceInfo.getCpdelflg()); // 是否删除录音(1:已删除;2:未删除;3:已彻底删除)
        bizUserVoiceInfo.setDeldatetime(uniUserVoiceInfo.getDeldatetime()); // 最新删除时间
        bizUserVoiceInfo.setCpgetflg(uniUserVoiceInfo.getCpgetflg()); // 提取码是否有效(1:有效;2:无效)
        bizUserVoiceInfo.setAccesscode(uniUserVoiceInfo.getAccesscode()); // 提取码
        bizUserVoiceInfo.setGetdatetime(uniUserVoiceInfo.getGetdatetime()); // 最新提取时间
        bizUserVoiceInfo.setDuedatetime(uniUserVoiceInfo.getDuedatetime()); // 到期时间
        bizUserVoiceInfo.setCallway(uniUserVoiceInfo.getCallway()); // 拨打方式(预留)
        bizUserVoiceInfo.setIspay(null); // 是否付费(1:是;2:否)
        bizUserVoiceInfo.setVoicetype(null); // 录音类型（1：包月录音；2：体验录音；3：按次录音）
        return bizUserVoiceInfo;
    }


    /**
     * 将信息插入到录音队列中
     * @param uniUserVoiceInfos
     */
    private void insertVoiceQueue(List<UniUserVoiceInfo> uniUserVoiceInfos){
        Date synTime = new Date(); // 同步时间
        for (UniUserVoiceInfo uniUserVoiceInfo: uniUserVoiceInfos) {
            BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
            BossVoiceInfo bossVoiceInfo = new BossVoiceInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserVoiceInfo = cpVoiceToBossVoice(uniUserVoiceInfo);
            }catch (Exception e){
                log.debug("cp联通录音字段对应boss字段异常 = {},uniUserVoiceInfo = {}",e,uniUserVoiceInfo);
                errorInfo = "cp联通录音字段对应boss字段异常:"+ e.getMessage() + " uniUserVoiceInfo:"+uniUserVoiceInfo;
            }
            bossVoiceInfo.setBizname("cp联通");
            bossVoiceInfo.setBizUserVoiceInfo(bizUserVoiceInfo);
            bossVoiceInfo.setSynSize(uniUserVoiceInfos.size());
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
