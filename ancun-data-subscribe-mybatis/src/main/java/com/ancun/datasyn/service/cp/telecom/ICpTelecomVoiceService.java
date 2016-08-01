package com.ancun.datasyn.service.cp.telecom;

import com.ancun.datasyn.pojo.voice.VoiceInput;

/**
 * cp电信录音接口类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:24
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICpTelecomVoiceService {
    /**
     * cp电信录音队列
     */
    public void insertCpTelecomVoiceInfoQ(VoiceInput input);
}
