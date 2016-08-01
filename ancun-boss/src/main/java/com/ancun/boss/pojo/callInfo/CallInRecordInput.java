package com.ancun.boss.pojo.callInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 呼入登记信息请求封装POJO类
 * @author cys
 *
 */
@XmlAccessorType(value=XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class CallInRecordInput extends BossPagePojo{
	
	/**
	 * 编号id
	 */
	private Long id;
	
	/**
	 * 客服工号
	 */
	private String userno;
	
	/**
	 * 客服姓名
	 */
	private String username;
	
	/**
	 * 来电时间开始时间(日期+时间)
	 */
	private String calltimestart;
	
	/**
	 * 来电时间结束时间(日期+时间)
	 */
	private String calltimeend;

	/**
	 * 来电时间(日期+时间)
	 */
	private String callTime;
	
	/**
	 * 来电号码
	 */
	private String callphone;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 称呼
	 */
	private String name;
	
	/**
	 * 来电时长
	 */
	private String duration;
	
	/**
	 * 业务(项目名称)
	 */
	private String business;
	
	/**
	 * 问题
	 */
	private String question;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 是否回拨
	 */
	private String callback;
	
	/**
	 * 回访情况
	 */
	private String visitsituation;
	
	/**
     * 新增修改标志（1:新增；2:修改）
     */
    private String modifyflag;
    
    /**
     * 呼入类型(1:呼入登记，2:投诉退订)
     */
    private String callType;
    
    /**
     * 质检状态(1:未质检，2:已质检)
     */
    private String checkStatus;
    
    /**
     * 投诉退订ID
     */
    private Long cancelId;
    
    /**
     * 质检ID
     */
    private Long checkId;
	

	public String getModifyflag() {
		return modifyflag;
	}

	public void setModifyflag(String modifyflag) {
		this.modifyflag = modifyflag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCallphone() {
		return callphone;
	}

	public void setCallphone(String callphone) {
		this.callphone = callphone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getVisitsituation() {
		return visitsituation;
	}

	public void setVisitsituation(String visitsituation) {
		this.visitsituation = visitsituation;
	}
	
	public String getCalltimestart() {
		return calltimestart;
	}

	public void setCalltimestart(String calltimestart) {
		this.calltimestart = calltimestart;
	}

	public String getCalltimeend() {
		return calltimeend;
	}

	public void setCalltimeend(String calltimeend) {
		this.calltimeend = calltimeend;
	}
	
	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	
	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public Long getCancelId() {
		return cancelId;
	}

	public void setCancelId(Long cancelId) {
		this.cancelId = cancelId;
	}
	
	public Long getCheckId() {
		return checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}
	
}
