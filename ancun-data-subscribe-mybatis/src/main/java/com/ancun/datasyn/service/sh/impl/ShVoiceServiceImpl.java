package com.ancun.datasyn.service.sh.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.sh.ShUserVoiceInfoMapper;
import com.ancun.common.persistence.model.master.BizUserVoiceInfo;
import com.ancun.common.persistence.model.sh.ShUserVoiceInfo;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.voice.BossVoiceInfo;
import com.ancun.datasyn.pojo.voice.VoiceInput;
import com.ancun.datasyn.service.sh.IShVoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 上海音证宝录音接口实现类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:25
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "shV2")
public class ShVoiceServiceImpl implements IShVoiceService {

    private static final Logger log = LoggerFactory.getLogger(ShVoiceServiceImpl.class);

    @Resource
    ShUserVoiceInfoMapper shUserVoiceInfoMapper;

    @Resource
    private Producer producer;

    /**
     * 插入上海音证宝录音队列
     */
    public void insertShVoiceInfoQ(VoiceInput input){
        // 个人用户录音列表
        List<ShUserVoiceInfo> shUserVoiceInfoList = getSynVoiceList(input);
        checkList(shUserVoiceInfoList);
        log.info("需要同步上海音证宝录音列表 "+ shUserVoiceInfoList.size() + " 条");
        // 将企业用户信息插入到队列中
        insertUserVoiceQueue(shUserVoiceInfoList);

    }

    /**
     * 取得需要同步的个人用户录音信息
     * @return
     */
    private List<ShUserVoiceInfo> getSynVoiceList(VoiceInput input){
        Example example = new Example(ShUserVoiceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(ShUserVoiceInfo.class).createCriteria().andEqualTo("updateTime",SynConstant.DEFAULT_DATE));
        return shUserVoiceInfoMapper.selectByExample(example);
    }

    /**
     * 分省个人录音字段 对应 boss字段
     * @param shUserVoiceInfo
     * @return
     */
    private BizUserVoiceInfo shVoiceToBossVoice(ShUserVoiceInfo shUserVoiceInfo){
        BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
        bizUserVoiceInfo.setRecordno(shUserVoiceInfo.getRecordno()); // 文件记录编号
        bizUserVoiceInfo.setUserNo(shUserVoiceInfo.getIuserno()); // 业务用户编号
        bizUserVoiceInfo.setEntNo(shUserVoiceInfo.getAccountno()); // 企业编号
        bizUserVoiceInfo.setBizNo(SynConstant.BIZ_DX_SHANGHAI); // 业务编号
        bizUserVoiceInfo.setRpcode(SynConstant.SHANGHAI_RPCODE); // 省份代码
        bizUserVoiceInfo.setCitycode(null); // 城市代码
        bizUserVoiceInfo.setOfilename(shUserVoiceInfo.getOfilename()); // 原文件名
        bizUserVoiceInfo.setRecordsource(shUserVoiceInfo.getRecordsource()); // 录音来源(预留)
        bizUserVoiceInfo.setCalltype(shUserVoiceInfo.getIcalltype()); // 通话类型(1:主叫;2:被叫）
        bizUserVoiceInfo.setCallerno(shUserVoiceInfo.getCallerno()); // 主叫号码
        bizUserVoiceInfo.setCalledno(shUserVoiceInfo.getCalledno()); // 被叫号码
        bizUserVoiceInfo.setBegintime(shUserVoiceInfo.getBegintime()); // 通话起始时间
        bizUserVoiceInfo.setEndtime(shUserVoiceInfo.getEndtime()); // 通话结束时间
        bizUserVoiceInfo.setDuration(shUserVoiceInfo.getDuration()); // 通话时长(秒)
        bizUserVoiceInfo.setFilesize(shUserVoiceInfo.getFilesize()); // 文件大小
        bizUserVoiceInfo.setLicenceno(shUserVoiceInfo.getLicenceno()); // 备案号
        bizUserVoiceInfo.setLicencedatetime(shUserVoiceInfo.getLicencedatetime()); // 备案时间
        bizUserVoiceInfo.setRecoderremark(shUserVoiceInfo.getRecoderremark()); // 录音说明
        bizUserVoiceInfo.setCpcertificateflg(shUserVoiceInfo.getCpcertificateflg()); // 是否申请公证(1:已申请;2:未申请)
        bizUserVoiceInfo.setCpcertificateapplytime(shUserVoiceInfo.getCpcertificateapplytime()); // 最新申请公证时间
        bizUserVoiceInfo.setCpdelflg(shUserVoiceInfo.getCpdelflg()); // 是否删除录音(1:已删除;2:未删除;3:已彻底删除)
        bizUserVoiceInfo.setDeldatetime(shUserVoiceInfo.getDeldatetime()); // 最新删除时间
        bizUserVoiceInfo.setCpgetflg(shUserVoiceInfo.getCpgetflg()); // 提取码是否有效(1:有效;2:无效)
        bizUserVoiceInfo.setAccesscode(shUserVoiceInfo.getAccesscode()); // 提取码
        bizUserVoiceInfo.setGetdatetime(shUserVoiceInfo.getGetdatetime()); // 最新提取时间
        bizUserVoiceInfo.setDuedatetime(shUserVoiceInfo.getDuedatetime()); // 到期时间
        bizUserVoiceInfo.setCallway(shUserVoiceInfo.getCallway()); // 拨打方式(预留)
        bizUserVoiceInfo.setIspay(shUserVoiceInfo.getIspay()); // 是否付费(1:是;2:否)
        bizUserVoiceInfo.setVoicetype(shUserVoiceInfo.getVoicetype()); // 录音类型（1：包月录音；2：体验录音；3：按次录音）
        return bizUserVoiceInfo;
    }



    /**
     * 将信息插入到个人队列中
     * @param shUserVoiceInfos
     */
    private void insertUserVoiceQueue(List<ShUserVoiceInfo> shUserVoiceInfos){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (ShUserVoiceInfo shUserVoiceInfo: shUserVoiceInfos) {
            BizUserVoiceInfo bizUserVoiceInfo = new BizUserVoiceInfo();
            BossVoiceInfo bossVoiceInfo = new BossVoiceInfo();
            String errorInfo = null ; // 错误信息
            try {
                bizUserVoiceInfo = shVoiceToBossVoice(shUserVoiceInfo);
            }catch (Exception e){
                log.debug("上海音证宝录音字段对应boss字段异常 = {},shUserVoiceInfo = {}",e,shUserVoiceInfo);
                errorInfo = "上海音证宝录音字段对应boss字段异常:"+ e.getMessage() + " shUserVoiceInfo:"+shUserVoiceInfo.toString();
            }
            bossVoiceInfo.setBizname("上海音证宝");
            bossVoiceInfo.setUuid(uuid);
            bossVoiceInfo.setBizUserVoiceInfo(bizUserVoiceInfo);
            bossVoiceInfo.setSynSize(shUserVoiceInfos.size());
            bossVoiceInfo.setSynTime(synTime);
            bossVoiceInfo.setErrorInfo(errorInfo);
            this.producer.sendVoiceQueue(bossVoiceInfo);
//            this.producer.sendVoiceQueue(bizUserVoiceInfo);
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
