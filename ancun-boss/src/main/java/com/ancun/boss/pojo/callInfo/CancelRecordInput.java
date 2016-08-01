package com.ancun.boss.pojo.callInfo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;

/**
 * 投诉退订请求信息封住POJO类
 * @author cys1993
 *
 */
@XmlAccessorType(value=XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class CancelRecordInput extends BossPagePojo{
	/**
	 * 编号
	 */
	private Long id;
	
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
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 开通时间
	 */
	private Date openTime;
	
	/**
	 * 退订时间
	 */
	private Date cancelTime;
	
	/**
	 * 来电时间
	 */
	private Date callTime;
	
	/**
	 * 退订/退费
	 */
	private String cancelReqire;
	
	/**
	 * 调整金额
	 */
	private Double refundMoney;
	
	/**
	 * 退费时间
	 */
	private Date refundTime;
	
	/**
	 * 有无录音
	 */
	private String voice;
	
	/**
	 * 投诉来源
	 */
	private String source;
	
	/**
	 * 工单编号
	 */
	private Long orderNo;
	
	/**
	 * 需要调整月份
	 */
	private String adjustMonths;
	
	/**
	 * 调整金额
	 */
	private Double adjustMoney;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 业务
	 */
	private String business;
	
	/**
     * 新增修改标志（1:新增；2:修改）
     */
    private String modifyflag;
    
    /**
     * 来电开始时间
     */
    private Date callTimeStart;
    
    
    /**
     * 来电结束时间
     */
    private Date callTimeEnd;
    
    /**
     * 退订开始时间
     */
    private Date cancelTimeStart;
    
    /**
     * 退订结束时间
     */
    private Date cancelTimeEnd;
    
    /**
     * 业务名称
     * @return
     */
      
    private String businessName;
    
    /**
     * 退订退费
     */
    private String cancelReqireName;
    
    /**
     * 投诉来源
     */
    private String sourceName;
    
    /**
     * 客服工号
     */
    private String userNo;

	/**
     * 客服姓名
     */
    private String userName;
    
    /**
     * 投诉ID
     */
    private Long cancelId;
    
    /**
     * 呼入登记ID
     */
    private Long callInId;

	public String getCancelReqireName() {
		return cancelReqireName;
	}

	public void setCancelReqireName(String cancelReqireName) {
		this.cancelReqireName = cancelReqireName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Date getCallTimeStart() {
		return callTimeStart;
	}

	public void setCallTimeStart(Date callTimeStart) {
		this.callTimeStart = callTimeStart;
	}

	public Date getCallTimeEnd() {
		return callTimeEnd;
	}

	public void setCallTimeEnd(Date callTimeEnd) {
		this.callTimeEnd = callTimeEnd;
	}

	public Date getCancelTimeStart() {
		return cancelTimeStart;
	}

	public void setCancelTimeStart(Date cancelTimeStart) {
		this.cancelTimeStart = cancelTimeStart;
	}

	public Date getCancelTimeEnd() {
		return cancelTimeEnd;
	}

	public void setCancelTimeEnd(Date cancelTimeEnd) {
		this.cancelTimeEnd = cancelTimeEnd;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}



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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public String getCancelReqire() {
		return cancelReqire;
	}

	public void setCancelReqire(String cancelReqire) {
		this.cancelReqire = cancelReqire;
	}

	public Double getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(Double refundMoney) {
		this.refundMoney = refundMoney;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getAdjustMonths() {
		return adjustMonths;
	}

	public void setAdjustMonths(String adjustMonths) {
		this.adjustMonths = adjustMonths;
	}

	public Double getAdjustMoney() {
		return adjustMoney;
	}

	public void setAdjustMoney(Double adjustMoney) {
		this.adjustMoney = adjustMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}
	
    public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public Long getCancelId() {
		return cancelId;
	}

	public void setCancelId(Long cancelId) {
		this.cancelId = cancelId;
	}
	
    
	public Long getCallInId() {
		return callInId;
	}

	public void setCallInId(Long callInId) {
		this.callInId = callInId;
	}

}
