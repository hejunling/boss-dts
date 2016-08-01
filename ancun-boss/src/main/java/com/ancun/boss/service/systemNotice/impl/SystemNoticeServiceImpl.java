package com.ancun.boss.service.systemNotice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.persistence.biz.mapper.BizSystemNoticeMapper;
import com.ancun.boss.persistence.mapper.SystemNoticeMapper;
import com.ancun.boss.persistence.model.SystemNotice;
import com.ancun.boss.pojo.systemNotice.SystemNoticeListInput;
import com.ancun.boss.pojo.systemNotice.SystemNoticeListOutput;
import com.ancun.boss.pojo.systemNotice.SystemNoticeOutput;
import com.ancun.boss.service.systemNotice.ISystemNoticeService;

/**
 * 系统提醒实现
 *
 * @Created on 2015年11月25日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class SystemNoticeServiceImpl implements ISystemNoticeService {

	@Resource
	private SystemNoticeMapper systemNoticeMapper;
	
	@Resource
	private BizSystemNoticeMapper bizSystemNoticeMapper;

	/**
	 * 消息提醒-全部查询
	 *
	 * @param input
	 * @return
	 * @throws com.ancun.core.exception.EduException
	 */
	@Override
	public SystemNoticeListOutput getSystemNoticeListinfo(SystemNoticeListInput input) {

		SystemNoticeListOutput output = new SystemNoticeListOutput();
//		SystemNoticeExample example = new SystemNoticeExample();
//		SystemNoticeExample.Criteria c = example.createCriteria();
//
//		// 设置分页
//		Page pageinfo = new Page();
//		pageinfo.setCurrentpage(Integer.valueOf(input.getCurrentpage()));
//		pageinfo.setPagesize(Integer.valueOf(input.getPagesize()));
//
//		// 类型不为空
//		if (StringUtils.isNotEmpty(input.getType())) {
//			c.andTypeEqualTo(input.getType());
//		}
//		// 读取不为空(1：未读;2：已读)
//		if (StringUtils.isNotEmpty(input.getReadLabel())) {
//			c.andReadLabelEqualTo(input.getReadLabel());
//		}
//		// 用户编号
//		if (StringUtils.isNotEmpty(input.getUserno())) {
//			c.andUsernoEqualTo(input.getUserno());
//		}
//		// 时间段 
//		if (StringUtils.isNotEmpty(DateUtil.convertDateToString(input.getCommitTimeStart()))
//				&& StringUtils.isNotEmpty(DateUtil.convertDateToString(input.getCommitTimeEnd()))) {
//			c.andCommitTimeGreaterThan(input.getCommitTimeStart());
//			c.andCommitTimeLessThan(input.getCommitTimeEnd());
//		} else if (StringUtils.isNotEmpty(DateUtil.convertDateToString(input.getCommitTimeStart()))) {
//			c.andCommitTimeGreaterThan(input.getCommitTimeStart());
//		} else if (StringUtils.isNotEmpty(DateUtil.convertDateToString(input.getCommitTimeEnd()))) {
//			c.andCommitTimeLessThan(input.getCommitTimeEnd());
//		}
//
//		example.setPage(pageinfo);
//
//		example.setOrderByClause("COMMIT_TIME DESC");
//		List<SystemNotice> list = systemNoticeMapper.selectByExample(example);
		
		List<SystemNotice> list = bizSystemNoticeMapper.getSystemNoticeListinfo(input);

		output.setSystemNoticeInfoList(list);
		output.setPageinfo(input.getPage());

		return output;
	}

	/**
	 * 消息提醒-详情
	 */
	@Override
	public SystemNoticeOutput getSystemNoticeinfo(Long id) {
		
		SystemNoticeOutput output = bizSystemNoticeMapper.getSystemNoticeinfo(id);
		return output;
	}
	
	/**
	 * 消息提醒-读取状态更改
	 */
	@Override
	public void updateSystemNoticeinfo(Long id) {

		bizSystemNoticeMapper.updateSystemNoticeinfo(id);
	}
}
