package com.ancun.boss.business.utils.fptcsrvc;

import java.util.Date;

import com.ancun.core.exception.EduException;
import com.ancun.utils.MD5Utils;
import com.sanss.ngn.fptcsrvcinfosync.UserInfoSyncRequest;

public class UserInfoSyncRequestGenerate {
	public static UserInfoSyncRequest generate(String phone) throws EduException{
		UserInfoSyncRequest request = new UserInfoSyncRequest();
		/**
		 * 生成唯一标示数据
		 */
		String streamingNo = new FtpcsrvcConstant().getOrderId();
		/**
		 * 流水号
		 */
		request.setStreamingNo(streamingNo);
		/**
		 * 发起端系统编号，由服务端分配
		 */
		request.setSrcSystemNo(FtpcsrvcConstant.SRCSYSTEMNO);
		/**
		 * 消息签名，取值Value=MD5(StreamingNo +SrcSystemNo+UserNumber+Key)，Key由平台为应用分配
		 */
		request.setSignature(MD5Utils.md5(streamingNo+FtpcsrvcConstant.SRCSYSTEMNO+phone+FtpcsrvcConstant.KEY));
		/**
		 * 订购编号
		 */
		request.setOrderId(streamingNo);
		/**
		 *	0：MSISDN
		 *	1：PHS
		 *	2：PSTN
		 *	3：宽带帐号
		 *	4：IPTV帐号
		 *	5：VNET帐号：仅在此账号为付费账号时使用
		 */
		request.setUserIDType(FtpcsrvcConstant.UserIDType);
		
		request.setUserName(FtpcsrvcConstant.DEFAULT_USERNAME);
		/**
		 * 用户号码，带区号021
		 */
		request.setUserNumber(phone);
		/**
		 * 时间戳：yyyyMMddHHmss，其中HH取值为00-23，时区为东八区
		 */
		request.setTimeStamp(FtpcsrvcConstant.sdf.format(new Date()));
		
		return request;
	}
}
