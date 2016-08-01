package com.ancun.boss.business.timer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import com.ancun.boss.business.persistence.mapper.BizUserInfoQueryMapper;
import com.ancun.boss.util.ThreadLocalUtil;

/**
 * BizUserInfoQuery表，数据去重定时器
 * 
 * @author Yck
 * @time Dec 2, 2015
 */
public class BizUserInfoQueryDeduplicationTimer {

	@Resource
	private BizUserInfoQueryMapper queryMapper;

	// 不要在这里设置静态变量，并且读取PropertiesUtils.getConfig()，否则会报错。
	// 方法不在做要求，但是为了兼容旧版定时器，方法还是run
	public void run() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

		ThreadLocalUtil.setContent("去重定时器启动。"
				+ df.format(System.currentTimeMillis()));

		int i = queryMapper.deduplicationById();
		System.out.print("执行：" + new Date());
		ThreadLocalUtil.setContent("去重定时器关闭。"
				+ df.format(System.currentTimeMillis()));
		ThreadLocalUtil.setContent("此次运行共删除：" + i + "条数据");

	}

}