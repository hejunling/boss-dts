package com.ancun.boss.business.utils.fptcsrvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ancun.boss.business.persistence.biz.BizGetSequenceMapper;
import com.ancun.core.exception.EduException;
import com.ancun.core.spring.SpringContextUtil;

/**
 * 
 *
 * @Created on 2016年3月14日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class FtpcsrvcConstant {
	
	/**
	 * 发起端系统编号，由服务端分配
	 */
	public final static String SRCSYSTEMNO = "02000001";
	/**
	 * 消息签名，取值Value=MD5(StreamingNo +SrcSystemNo+UserNumber+Key)，
	 * Key由平台为应用分配
	 */
	public final static String KEY = "245IZ1gSvQ056K9c";
	/**
	 * 订单编号，格式定为“y12345678”-->y固定+末尾8位数字
	 */
	public final static String OrderId_Prefix = "y";
	/**
	 *	0：MSISDN
	 *	1：PHS
	 *	2：PSTN
	 *	3：宽带帐号
	 *	4：IPTV帐号
	 *	5：VNET帐号：仅在此账号为付费账号时使用
	 */
	public final static int UserIDType = 2;
	/**
	 * 格式化时间
	 */
	public static DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 返回成功的结果code
	 */
	public final static int RESULT_OK = 0;
	/**
	 *	业务类型：
	 *	0：纯增值销售品（音证宝）
	 *	1：增值捆绑套餐
	 *	2：（传统+增值）捆绑套餐
	 */
	public final static int SrvcType = 0;
	/**
	 * 销售品ID:y13500000000000000001代表音证宝业务--主叫
	 *        y13500000000000000001
	 */
	public final static String SrvcID_CALLER = "y13500000000000000001";
	/**
	 * 增值产品ID:y13500000000000000001代表音证宝业务--主叫
	 */
	public final static String VProductID_CALLER = "y13500000000000000001";
	
	/**
	 * 销售品ID:y13500000000000000001代表音证宝业务--被叫
	 */
	public final static String SrvcID_CALLED = "y13500000000000000002";
	/**
	 * 增值产品ID:y13500000000000000001代表音证宝业务--被叫
	 */
	public final static String VProductID_CALLED = "y13500000000000000002";
	
	public final static String DEFAULT_USERNAME = "安存";
	
	/**
	 * 获取订单编号
	 * @return
	 * @throws EduException
	 */
	public String getOrderId() throws EduException{
		/**
		 * 获取不重复的数字
		 */
		BizGetSequenceMapper getSequenceMapper = (BizGetSequenceMapper) SpringContextUtil.getBean("bizGetSequenceMapper");
		int order = getSequenceMapper.getSequence();
		/**
		 * 格式化数字为8位
		 */
		String orderId = String.format("%1$08d", order);
		/**
		 * 为订单编号加上前缀
		 */
		orderId = OrderId_Prefix + orderId;
		
		return orderId;
	}
}
