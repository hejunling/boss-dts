package com.ancun.boss.persistence.biz.mapper;

import java.util.List;

import com.ancun.boss.persistence.model.SystemNotice;
import com.ancun.boss.pojo.systemNotice.SystemNoticeListInput;
import com.ancun.boss.pojo.systemNotice.SystemNoticeOutput;

/**
 * 系统提醒查询接口
 *
 * @Created on 2015年11月12日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizSystemNoticeMapper {
	
	/**
	 * 系统提醒查询列表
	 * @param input
	 * @return
	 */
	List<SystemNotice> getSystemNoticeListinfo(SystemNoticeListInput input);
	
	/**
	 * 系统提醒详情
	 * @param id
	 * @return
	 */
	SystemNoticeOutput getSystemNoticeinfo(Long id);
	
	/**
	 * 系统提醒-读取状态更改
	 * @param id
	 */
	void updateSystemNoticeinfo(Long id);
}
