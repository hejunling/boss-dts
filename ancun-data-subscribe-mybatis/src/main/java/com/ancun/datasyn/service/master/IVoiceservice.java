package com.ancun.datasyn.service.master;

import com.ancun.common.persistence.model.master.BizUserVoiceInfo;
import com.ancun.datasyn.pojo.voice.VoiceInput;

/**
 * 录音接口
 * User: zkai
 * Date: 2016/5/16
 * Time: 20:39
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IVoiceservice {

    /**
     * 将个业务信息插入到队列
     */
    public void insertVoiceInfoQ(VoiceInput input);


    /**
     * 同步boss录音信息（插入）
     * @param bizUserVoiceInfo
     */
    public void synBossVoiceInfo(BizUserVoiceInfo bizUserVoiceInfo);
}
