package com.ancun.datasyn.common.impl;

import com.ancun.common.persistence.mapper.master.BizSynRecordMapper;
import com.ancun.common.persistence.model.master.BizSynRecord;
import com.ancun.datasyn.common.IBizSynRecordService;
import com.ancun.datasyn.pojo.voice.VoiceInput;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 业务同步结果记录表接口类
 * User: zkai
 * Date: 2016/5/23
 * Time: 11:23
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class BizSynRecordServiceImpl implements IBizSynRecordService {
    @Resource
    private BizSynRecordMapper bizSynRecordMapper;
    /**
     * 插入业务同步结果记录表
     */
    public void insert(BizSynRecord bizSynRecord){
        bizSynRecordMapper.insert(bizSynRecord);
    }
}
