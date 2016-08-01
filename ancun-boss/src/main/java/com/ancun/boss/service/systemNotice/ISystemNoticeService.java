package com.ancun.boss.service.systemNotice;

import com.ancun.boss.pojo.systemNotice.SystemNoticeListInput;
import com.ancun.boss.pojo.systemNotice.SystemNoticeListOutput;
import com.ancun.boss.pojo.systemNotice.SystemNoticeOutput;


/**
 * 消息提醒逻辑接口
 *
 * @Created on 2015年10月26日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ISystemNoticeService {

    /**
     * 消息提醒-全部查询
     */
    public SystemNoticeListOutput getSystemNoticeListinfo(SystemNoticeListInput input);
    
    /**
     * 消息提醒-详情
     * @param id
     * @return
     */
    public SystemNoticeOutput getSystemNoticeinfo(Long id);
    
    /**
     * 消息提醒-读取状态更改
     * @param id
     */
    public void updateSystemNoticeinfo(Long id);

}
