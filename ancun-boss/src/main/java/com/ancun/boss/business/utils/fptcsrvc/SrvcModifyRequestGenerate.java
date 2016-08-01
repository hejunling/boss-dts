package com.ancun.boss.business.utils.fptcsrvc;

import java.util.Date;

import com.ancun.core.exception.EduException;
import com.ancun.utils.MD5Utils;
import com.sanss.ngn.fptcsrvcinfosync.Property;
import com.sanss.ngn.fptcsrvcinfosync.SrvcModifyRequest;

public class SrvcModifyRequestGenerate {
	/**
	 * 提示音
	 * @param phone
	 * @param tip
	 * @return
	 * @throws EduException
	 */
	public static SrvcModifyRequest generate(String phone, RecordTipEnum tip,boolean isCaller) throws EduException{
		SrvcModifyRequest request = new SrvcModifyRequest();
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
		 * 消息签名，取值Value=MD5(StreamingNo+SrcSystemNo+UserNumber+ActionType+Key)，Key由平台为应用分配
		 */
		request.setSignature(MD5Utils.md5(streamingNo+FtpcsrvcConstant.SRCSYSTEMNO+phone+
				(isCaller?FtpcsrvcConstant.SrvcID_CALLER:FtpcsrvcConstant.SrvcID_CALLED)
				+FtpcsrvcConstant.KEY));
		/**
		 * 订购编号
		 */
		request.setOrderId(streamingNo);
		request.setUserNumber(phone);
		request.setSrvcID((isCaller?FtpcsrvcConstant.SrvcID_CALLER:FtpcsrvcConstant.SrvcID_CALLED));
		request.setTimeStamp(FtpcsrvcConstant.sdf.format(new Date()));
		Property property = new Property();
		property.setName("recordType");
		property.setValue(tip.getValue());
		request.getPropertyList().add(property);
		return request;
	}
	
}
