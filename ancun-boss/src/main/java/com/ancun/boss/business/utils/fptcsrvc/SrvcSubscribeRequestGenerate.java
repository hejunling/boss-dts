package com.ancun.boss.business.utils.fptcsrvc;

import java.util.Date;

import com.ancun.boss.business.pojo.bizcanceluser.ActionTypeEnum;
import com.ancun.core.exception.EduException;
import com.ancun.utils.MD5Utils;
import com.sanss.ngn.fptcsrvcinfosync.SrvcInfo;
import com.sanss.ngn.fptcsrvcinfosync.SrvcSubscribeRequest;
import com.sanss.ngn.fptcsrvcinfosync.VSubProdInfo;

public class SrvcSubscribeRequestGenerate {
	public static SrvcSubscribeRequest generate(ActionTypeEnum actionType, String phone, boolean isCaller) throws EduException{
		SrvcSubscribeRequest request = new SrvcSubscribeRequest();
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
		request.setSignature(MD5Utils.md5(streamingNo+FtpcsrvcConstant.SRCSYSTEMNO+phone+actionType.getValue()+FtpcsrvcConstant.KEY));
		/**
		 * 操作：0：订购  1：退订
		 */
		request.setActionType(actionType.getValue());
		/**
		 * 订购编号
		 */
		request.setOrderId(streamingNo);
		/**
		 * 用户号码，带区号021
		 */
		request.setUserNumber(phone);
		/**
		 *	0：MSISDN
		 *	1：PHS
		 *	2：PSTN
		 *	3：宽带帐号
		 *	4：IPTV帐号
		 *	5：VNET帐号：仅在此账号为付费账号时使用
		 */
		request.setUserIDType(FtpcsrvcConstant.UserIDType);
		/**
		 * 业务列表, 说明：全部退订的时候，该字段为空
		 */
		request.setSrvcInfo(getSrvcInfo(isCaller));
		/**
		 * 时间戳：yyyyMMddHHmss，其中HH取值为00-23，时区为东八区
		 */
		request.setTimeStamp(FtpcsrvcConstant.sdf.format(new Date()));
		
		return request;
	}
	
	/**
	 * 业务列表 ,说明：全部退订的时候，该字段为空
	 * @param isCaller 是否主叫
	 * @return
	 */
	private static SrvcInfo getSrvcInfo(boolean isCaller){
		SrvcInfo info = new SrvcInfo();
		/**
		 * 业务类型：0：纯增值销售品（音证宝）1：增值捆绑套餐2：（传统+增值）捆绑套餐
		 */
		info.setSrvcType(FtpcsrvcConstant.SrvcType);
		/**
		 * 销售品ID
		 */
		info.setSrvcID(isCaller?FtpcsrvcConstant.SrvcID_CALLER:FtpcsrvcConstant.SrvcID_CALLED);
		
		/**
		 * 生效时间，yyyyMMddHHmmss，其中HH取值为00-23，时区为东八区
		 */
		info.setEffDate("");
		/**
		 * 失效时间，yyyyMMddHHmmss，其中HH取值为00-23，时区为东八区
		 */
		info.setExpDate("");
		/**
		 * 订购类型：0：正常订购 1：免费体验
		 */
		info.setIsExperience(0);
		/**
		 * 订购方式：0：非批量受理  1：批量受理
		 */
		info.setSubscribeType(0);
		/**
		 * 增值产品列表 0：表示订购/退订该销售品下所有增值产品
		 */
		VSubProdInfo vInfo = new VSubProdInfo();
		/**
		 * 增值产品ID
		 */
		vInfo.setVProductID(isCaller?FtpcsrvcConstant.VProductID_CALLER:FtpcsrvcConstant.VProductID_CALLED);
		/**
		 * 生效时间，yyyyMMddHHmmss，其中HH取值为00-23，时区为东八区
		 */
		vInfo.setEffDate("");
		/**
		 * 失效时间，yyyyMMddHHmmss，其中HH取值为00-23，时区为东八区
		 */
		vInfo.setExpDate("");
		
		info.getVSubProdInfo().add(vInfo);
		
		return info;
	}
}
