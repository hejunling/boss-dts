package com.ancun.datasyn.service.cp.unicom;

import com.ancun.datasyn.pojo.voice.VoiceInput;

/**
 * cp联通录音接口类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:24
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICpUnicomVoiceService {
    /**
     * 插入分省联通录音队列
     */
    public void insertUnicomVoiceInfoQ(VoiceInput input);
}
