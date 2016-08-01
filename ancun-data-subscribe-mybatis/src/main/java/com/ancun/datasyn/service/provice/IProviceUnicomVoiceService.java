package com.ancun.datasyn.service.provice;

import com.ancun.datasyn.pojo.voice.VoiceInput;

/**
 * 分省电信录音接口类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:24
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IProviceUnicomVoiceService {
    /**
     * 插入分省电信录音队列
     */
    public void insertProviceVoiceInfoQ(VoiceInput input);
}
