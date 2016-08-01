package com.ancun.datasyn.service.provice.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.dx.EntUserVoiceInfoMapper;
import com.ancun.common.persistence.mapper.dx.UserVoiceInfoMapper;
import com.ancun.common.persistence.model.dx.EntUserVoiceInfo;
import com.ancun.common.persistence.model.dx.UserVoiceInfo;
import com.ancun.common.persistence.model.master.BizUserVoiceInfo;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.CacheConstant;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.voice.BossVoiceInfo;
import com.ancun.datasyn.pojo.voice.VoiceInput;
import com.ancun.datasyn.service.provice.IProviceTelecomVoiceService;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 分省电信录音接口实现类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:25
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "dxProvince")
public class ProviceTelecomVoiceServiceImpl implements IProviceTelecomVoiceService {

    private static final Logger log = LoggerFactory.getLogger(ProviceTelecomVoiceServiceImpl.class);

    @Resource
    UserVoiceInfoMapper userVoiceInfoMapper;

    @Resource
    EntUserVoiceInfoMapper entUserVoiceInfoMapper;

    @Resource
    private Producer producer;

    /**
     * 插入分省电信录音队列
     */
    public void insertProviceVoiceInfoQ(VoiceInput input){
        // 个人用户录音列表
        List<UserVoiceInfo> userVoiceInfoList = getSynPesonVoiceList(input);

        log.info("需要同步分省电信个人用户录音列表 "+ userVoiceInfoList.size() + " 条");
        // 将个人用户信息插入到队列中
        insertUserVoiceQueue(userVoiceInfoList);

        // 企业用户录音列表
        List<EntUserVoiceInfo> entUserVoiceInfos = getSynEntVoiceList(input);
        log.info("需要同步分省电信企业用户录音列表 "+ entUserVoiceInfos.size() + " 条");
        // 将企业用户信息插入到队列中
        insertEntUserVoiceQueue(entUserVoiceInfos);
    }

    /**
     * 取得需要同步的个人用户录音信息
     * @return
     */
    private List<UserVoiceInfo> getSynPesonVoiceList(VoiceInput input){
        Example example = new Example(UserVoiceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(UserVoiceInfo.class).createCriteria().andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return userVoiceInfoMapper.selectByExample(example);
    }

    /**
     * 取得需要同步的企业用户录音信息
     * @return
     */
    private List<EntUserVoiceInfo> getSynEntVoiceList(VoiceInput input){
        Example example = new Example(EntUserVoiceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(EntUserVoiceInfo.class).createCriteria().andEqualTo("updateTime",SynConstant.DEFAULT_DATE));
        return entUserVoiceInfoMapper.selectByExample(example);
    }

    /**
     * 分省个人录音字段 对应 boss字段
     * @param userVoiceInfo
     * @return
     */
    private BizUserVoiceInfo uniVoiceToBossVoice(UserVoiceInfo userVoiceInfo){
        BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
        bizUserVoiceInfo.setRecordno(userVoiceInfo.getRecordno()); // 文件记录编号
        bizUserVoiceInfo.setUserNo(userVoiceInfo.getIuserno()); // 业务用户编号
        bizUserVoiceInfo.setEntNo(null); // 企业编号
        String bizno = CacheConstant.DX_PROVINCE_MAP.get(userVoiceInfo.getRpcode());
        if(StringUtil.isNotBlank(bizno)){
            bizUserVoiceInfo.setBizNo( bizno); // 业务编号
        }else {
            throw new CustomException("PROVICE_BIZ 中rpcode对应的Bizno不存在");
        }
        bizUserVoiceInfo.setRpcode(userVoiceInfo.getRpcode()); // 省份代码
        bizUserVoiceInfo.setCitycode(userVoiceInfo.getCitycode()); // 城市代码
        bizUserVoiceInfo.setOfilename(userVoiceInfo.getOfilename()); // 原文件名
        bizUserVoiceInfo.setRecordsource(userVoiceInfo.getRecordsource()); // 录音来源(预留)
        bizUserVoiceInfo.setCalltype(userVoiceInfo.getIcalltype()); // 通话类型(1:主叫;2:被叫）
        bizUserVoiceInfo.setCallerno(userVoiceInfo.getCallerno()); // 主叫号码
        bizUserVoiceInfo.setCalledno(userVoiceInfo.getCalledno()); // 被叫号码
        bizUserVoiceInfo.setBegintime(userVoiceInfo.getBegintime()); // 通话起始时间
        bizUserVoiceInfo.setEndtime(userVoiceInfo.getEndtime()); // 通话结束时间
        bizUserVoiceInfo.setDuration(userVoiceInfo.getDuration()); // 通话时长(秒)
        bizUserVoiceInfo.setFilesize(userVoiceInfo.getFilesize()); // 文件大小
        bizUserVoiceInfo.setLicenceno(userVoiceInfo.getLicenceno()); // 备案号
        bizUserVoiceInfo.setLicencedatetime(userVoiceInfo.getLicencedatetime()); // 备案时间
        bizUserVoiceInfo.setRecoderremark(userVoiceInfo.getRecoderremark()); // 录音说明
        bizUserVoiceInfo.setCpcertificateflg(userVoiceInfo.getCpcertificateflg()); // 是否申请公证(1:已申请;2:未申请)
        bizUserVoiceInfo.setCpcertificateapplytime(userVoiceInfo.getCpcertificateapplytime()); // 最新申请公证时间
        bizUserVoiceInfo.setCpdelflg(userVoiceInfo.getCpdelflg()); // 是否删除录音(1:已删除;2:未删除;3:已彻底删除)
        bizUserVoiceInfo.setDeldatetime(userVoiceInfo.getDeldatetime()); // 最新删除时间
        bizUserVoiceInfo.setCpgetflg(userVoiceInfo.getCpgetflg()); // 提取码是否有效(1:有效;2:无效)
        bizUserVoiceInfo.setAccesscode(userVoiceInfo.getAccesscode()); // 提取码
        bizUserVoiceInfo.setGetdatetime(userVoiceInfo.getGetdatetime()); // 最新提取时间
        bizUserVoiceInfo.setDuedatetime(userVoiceInfo.getDuedatetime()); // 到期时间
        bizUserVoiceInfo.setCallway(userVoiceInfo.getCallway()); // 拨打方式(预留)
        bizUserVoiceInfo.setIspay(null); // 是否付费(1:是;2:否)
        bizUserVoiceInfo.setVoicetype(null); // 录音类型（1：包月录音；2：体验录音；3：按次录音）
        return bizUserVoiceInfo;
    }

    /**
     * 分省企业录音字段 对应 boss字段
     * @param entUserVoiceInfo
     * @return
     */
    private BizUserVoiceInfo uniEntVoiceToBossVoice(EntUserVoiceInfo entUserVoiceInfo){
        BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
        bizUserVoiceInfo.setRecordno(entUserVoiceInfo.getRecordno()); // 文件记录编号
        bizUserVoiceInfo.setUserNo(entUserVoiceInfo.getIuserno()); // 业务用户编号
        bizUserVoiceInfo.setEntNo(entUserVoiceInfo.getAccountno()); // 企业编号
        String bizno = CacheConstant.DX_PROVINCE_MAP.get(entUserVoiceInfo.getRpcode());
        if(StringUtil.isNotBlank(bizno)){
            bizUserVoiceInfo.setBizNo( bizno); // 业务编号
        }else {
            throw new CustomException("PROVICE_BIZ 中rpcode对应的Bizno不存在");
        }
        bizUserVoiceInfo.setRpcode(entUserVoiceInfo.getRpcode()); // 省份代码
        bizUserVoiceInfo.setCitycode(entUserVoiceInfo.getCitycode()); // 城市代码
        bizUserVoiceInfo.setOfilename(entUserVoiceInfo.getOfilename()); // 原文件名
        bizUserVoiceInfo.setRecordsource(entUserVoiceInfo.getRecordsource()); // 录音来源(预留)
        bizUserVoiceInfo.setCalltype(entUserVoiceInfo.getIcalltype()); // 通话类型(1:主叫;2:被叫）
        bizUserVoiceInfo.setCallerno(entUserVoiceInfo.getCallerno()); // 主叫号码
        bizUserVoiceInfo.setCalledno(entUserVoiceInfo.getCalledno()); // 被叫号码
        bizUserVoiceInfo.setBegintime(entUserVoiceInfo.getBegintime()); // 通话起始时间
        bizUserVoiceInfo.setEndtime(entUserVoiceInfo.getEndtime()); // 通话结束时间
        bizUserVoiceInfo.setDuration(entUserVoiceInfo.getDuration()); // 通话时长(秒)
        bizUserVoiceInfo.setFilesize(entUserVoiceInfo.getFilesize()); // 文件大小
        bizUserVoiceInfo.setLicenceno(entUserVoiceInfo.getLicenceno()); // 备案号
        bizUserVoiceInfo.setLicencedatetime(entUserVoiceInfo.getLicencedatetime()); // 备案时间
        bizUserVoiceInfo.setRecoderremark(entUserVoiceInfo.getRecoderremark()); // 录音说明
        bizUserVoiceInfo.setCpcertificateflg(entUserVoiceInfo.getCpcertificateflg()); // 是否申请公证(1:已申请;2:未申请)
        bizUserVoiceInfo.setCpcertificateapplytime(entUserVoiceInfo.getCpcertificateapplytime()); // 最新申请公证时间
        bizUserVoiceInfo.setCpdelflg(entUserVoiceInfo.getCpdelflg()); // 是否删除录音(1:已删除;2:未删除;3:已彻底删除)
        bizUserVoiceInfo.setDeldatetime(entUserVoiceInfo.getDeldatetime()); // 最新删除时间
        bizUserVoiceInfo.setCpgetflg(entUserVoiceInfo.getCpgetflg()); // 提取码是否有效(1:有效;2:无效)
        bizUserVoiceInfo.setAccesscode(entUserVoiceInfo.getAccesscode()); // 提取码
        bizUserVoiceInfo.setGetdatetime(entUserVoiceInfo.getGetdatetime()); // 最新提取时间
        bizUserVoiceInfo.setDuedatetime(entUserVoiceInfo.getDuedatetime()); // 到期时间
        bizUserVoiceInfo.setCallway(entUserVoiceInfo.getCallway()); // 拨打方式(预留)
        bizUserVoiceInfo.setIspay(null); // 是否付费(1:是;2:否)
        bizUserVoiceInfo.setVoicetype(null); // 录音类型（1：包月录音；2：体验录音；3：按次录音）
        return bizUserVoiceInfo;
    }


    /**
     * 将信息插入到个人队列中
     * @param userVoiceInfoList
     */
    private void insertUserVoiceQueue(List<UserVoiceInfo> userVoiceInfoList){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (UserVoiceInfo userVoiceInfo: userVoiceInfoList) {
            BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
            BossVoiceInfo bossVoiceInfo = new BossVoiceInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserVoiceInfo = uniVoiceToBossVoice(userVoiceInfo);
            }catch (Exception e){
                log.debug("电信分省个人录音字段对应boss字段异常 = {},userVoiceInfo = {}",e,userVoiceInfo);
                errorInfo = "电信分省个人录音字段对应boss字段异常:"+ e.getMessage() + " userVoiceInfo:"+userVoiceInfo;
            }
            bossVoiceInfo.setBizname("分省电信个人");
            bossVoiceInfo.setUuid(uuid);
            bossVoiceInfo.setBizUserVoiceInfo(bizUserVoiceInfo);
            bossVoiceInfo.setSynSize(userVoiceInfoList.size());
            bossVoiceInfo.setSynTime(synTime);
            bossVoiceInfo.setErrorInfo(errorInfo);
            this.producer.sendVoiceQueue(bossVoiceInfo);
        }
    }

    /**
     * 将信息插入到企业队列中
     * @param entUserVoiceInfos
     */
    private void insertEntUserVoiceQueue(List<EntUserVoiceInfo> entUserVoiceInfos){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (EntUserVoiceInfo entUserVoiceInfo: entUserVoiceInfos) {
            BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
            BossVoiceInfo bossVoiceInfo = new BossVoiceInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserVoiceInfo = uniEntVoiceToBossVoice(entUserVoiceInfo);
            }catch (Exception e){
                log.debug("电信分省企业录音字段对应boss字段异常 = {},entUserVoiceInfo = {}",e,entUserVoiceInfo);
                errorInfo = "电信分省企业录音字段对应boss字段异常:"+ e.getMessage() + " entUserVoiceInfo:"+entUserVoiceInfo.toString();
            }
            bossVoiceInfo.setBizname("分省电信企业");
            bossVoiceInfo.setUuid(uuid);
            bossVoiceInfo.setBizUserVoiceInfo(bizUserVoiceInfo);
            bossVoiceInfo.setSynSize(entUserVoiceInfos.size());
            bossVoiceInfo.setSynTime(synTime);
            bossVoiceInfo.setErrorInfo(errorInfo);
            this.producer.sendVoiceQueue(bossVoiceInfo);
//            this.producer.sendVoiceQueue(bizUserVoiceInfo);
        }
    }


}
