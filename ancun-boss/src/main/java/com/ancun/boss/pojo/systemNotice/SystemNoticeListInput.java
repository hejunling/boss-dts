package com.ancun.boss.pojo.systemNotice;

import com.ancun.boss.pojo.BossPagePojo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 消息提醒消列表输入类
 *
 * @Created on 2015年10月26日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class SystemNoticeListInput  extends BossPagePojo {

    // 类型(EMAIL,SMS)
    private String type;

    // 读取(1：未读;2：已读)
    private String readLabel;
    
    //提交时间(开始)
    private Date commitTimeStart;
    
    //提交时间(结束)
    private Date commitTimeEnd;
    
    //用户编号
    private String userno;
    
    public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public Date getCommitTimeStart() {
		return commitTimeStart;
	}

	public void setCommitTimeStart(Date commitTimeStart) {
		this.commitTimeStart = commitTimeStart;
	}

	public Date getCommitTimeEnd() {
		return commitTimeEnd;
	}

	public void setCommitTimeEnd(Date commitTimeEnd) {
		this.commitTimeEnd = commitTimeEnd;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public String getReadLabel() {
		return readLabel;
	}

	public void setReadLabel(String readLabel) {
		this.readLabel = readLabel;
	}


}
