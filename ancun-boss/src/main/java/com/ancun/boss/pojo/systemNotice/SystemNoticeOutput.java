package com.ancun.boss.pojo.systemNotice;

import java.util.Date;

/**
 * 消息提醒详情输出类
 *
 * @Created on 2015年11月3日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class SystemNoticeOutput {
	//系统提醒ID
	private Long id;
	//消息提醒标题
	private String title;	
	//消息提醒提交时间
	private Date commitTime;
	//消息提醒类型
	private String type;
	//消息提醒内容
	private String content;
	//读取状态
	private String readLabel;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReadLabel() {
		return readLabel;
	}
	public void setReadLabel(String readLabel) {
		this.readLabel = readLabel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
		
}
