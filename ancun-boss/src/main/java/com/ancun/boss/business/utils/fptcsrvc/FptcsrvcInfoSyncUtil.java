package com.ancun.boss.business.utils.fptcsrvc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.boss.business.pojo.bizcanceluser.ActionTypeEnum;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.core.exception.EduException;
import com.sanss.ngn.fptcsrvcinfosync.FptcSrvcInfoSync;
import com.sanss.ngn.fptcsrvcinfosync.FptcSrvcInfoSync_Service;
import com.sanss.ngn.fptcsrvcinfosync.ResultInfo;
import com.sanss.ngn.fptcsrvcinfosync.SrvcModifyRequest;
import com.sanss.ngn.fptcsrvcinfosync.SrvcModifyResponse;
import com.sanss.ngn.fptcsrvcinfosync.SrvcSubscribeRequest;
import com.sanss.ngn.fptcsrvcinfosync.SrvcSubscribeResponse;
import com.sanss.ngn.fptcsrvcinfosync.UserInfoSyncRequest;
import com.sanss.ngn.fptcsrvcinfosync.UserInfoSyncResponse;

/**
 * 能力快速提供平台业务开通接口定义
 * 用户信息同步、业务订购
 * @author tom.tang
 */
public class FptcsrvcInfoSyncUtil {
	
	private static final Logger loger = LoggerFactory.getLogger(FptcsrvcInfoSyncUtil.class);
	/**
	 * 用户信息同步
	 * @param phone 
	 * 			电话号码， 需带区号
	 */
	public static void userInfoSync(String phone) throws EduException{
		FptcSrvcInfoSync fpt = new FptcSrvcInfoSync_Service().getFptcSrvcInfoSyncSOAP();
		UserInfoSyncRequest request = UserInfoSyncRequestGenerate.generate(phone);
		/**
		 * 打印请求参数
		 */
		loger.debug("上海userInfoSync请求参数,StreamingNo{};SrcSystemNo:{};Signature:{};OrderId:{};UserIDType:{};UserNumber:{};TimeStamp:{}",request.getStreamingNo(),request.getSrcSystemNo(),request.getSignature(),request.getOrderId(),request.getUserIDType(),request.getUserNumber(),request.getTimeStamp());
		
		/**
		 * 执行用户信息同步，并返回调用结果
		 */
		UserInfoSyncResponse resp = fpt.userInfoSync(request);
		/**
		 * 处理返回结果: 如果resultCode不为0，抛出异常，否则表示执行成功
		 */
		int resultCode = resp.getResultCode();
		if(FtpcsrvcConstant.RESULT_OK != resultCode){
			String errMsg = "ResultCode:"+resultCode+" Description:"+resp.getDescription();
			loger.debug("上海远程调用处理返回结果:{}", errMsg);
			throw new EduException(MessageConstant.SH_POST_ERROR_133008);
		}
		loger.debug("上海userInfoSync远用户信息同步成功,ResultCode:{},Description:{}", resultCode,resp.getDescription());
	}
	
	/**
	 * 业务订购
	 * @param actionType
	 * 			操作类型
	 * @param phone
	 * 			电话号码， 需带区号
	 * @param isCaller
	 * 			是否主叫
	 * @throws EduException
	 */
	public static void srvcSubscribe(ActionTypeEnum actionType, String phone, boolean isCaller) throws EduException{
		FptcSrvcInfoSync fpt = new FptcSrvcInfoSync_Service().getFptcSrvcInfoSyncSOAP();
		SrvcSubscribeRequest request = SrvcSubscribeRequestGenerate.generate(actionType, phone, isCaller);
		/**
		 * 打印请求参数
		 */
		loger.debug("上海srvcSubscribe请求参数,StreamingNo{};SrcSystemNo:{};Signature:{};ActionType:{};OrderId:{};UserIDType:{};UserNumber:{};TimeStamp:{}",request.getStreamingNo(),request.getSrcSystemNo(),request.getSignature(),request.getActionType(),request.getOrderId(),request.getUserIDType(),request.getUserNumber(),request.getTimeStamp());
		
		/**
		 * 处理，并返回结果
		 */
		SrvcSubscribeResponse resp = null;
		try{
			resp = fpt.srvcSubscribe(request);
		}catch(Exception e){
			throw new EduException(MessageConstant.SH_POST_ERROR_100002);
		}
//		SrvcSubscribeResponse resp = fpt.srvcSubscribe(request);
		/**
		 * 处理返回结果: 如果resultCode不为0，抛出异常，否则表示执行成功
		 */
		int resultCode = resp.getResultCode();
		
		List<ResultInfo> results = resp.getResultInfo();
		for(ResultInfo result : results){
			loger.debug("上海VSOP-->opResult:{}, desc:{}", result.getOPResult(),result.getOPDesc());
		}
		
		if(FtpcsrvcConstant.RESULT_OK != resultCode && resultCode != 10401 && resultCode != 10402){
			String errMsg = "ResultCode:"+resultCode+" Description:"+resp.getDescription();
			loger.debug("上海srvcSubscribe远程调用处理返回结果:{}", errMsg);
			throw new EduException(MessageConstant.SH_POST_ERROR_133009);
		}
		loger.debug("上海业务订购成功,ResultCode:{}，Description:{}", resultCode,resp.getDescription());
	}
	
	/**
	 * 1.3	业务设置
	 * 主叫提示音
	 */
	public static void SrvcModify(String phone, RecordTipEnum tip) throws EduException{
		FptcSrvcInfoSync fpt = new FptcSrvcInfoSync_Service().getFptcSrvcInfoSyncSOAP();
		
		SrvcModifyRequest request =SrvcModifyRequestGenerate.generate(phone, tip,true);
		/**
		 * 打印请求参数
		 */
		loger.debug("上海SrvcModify请求参数,StreamingNo{};SrcSystemNo:{};Signature:{};UserNumber:{};SrvcID:{};recordTip:{};TimeStamp:{}",request.getStreamingNo(),request.getSrcSystemNo(),request.getSignature(),request.getUserNumber(),request.getSrvcID(),request.getTimeStamp(),tip.getValue());
		
		
		SrvcModifyResponse resp = fpt.srvcModify(request);
		int resultCode = resp.getResultCode();
		
		if(FtpcsrvcConstant.RESULT_OK != resultCode){
			String errMsg = "ResultCode:"+resultCode+" Description:"+resp.getDescription();
			loger.debug("上海远程调用处理返回结果:{}", errMsg);
			throw new EduException(MessageConstant.SH_POST_ERROR_133011);
		}
		loger.debug("上海SrvcModify业务设置成功,ResultCode:{}，Description:{}", resultCode,resp.getDescription());
	}
	
	/**
	 * 1.4	业务设置
	 * 提示音分为主叫提示音和被叫提示音
	 */
	public static void SrvcModify(String phone, RecordTipEnum tip,boolean isCaller) throws EduException{
		FptcSrvcInfoSync fpt = new FptcSrvcInfoSync_Service().getFptcSrvcInfoSyncSOAP();
		
		SrvcModifyRequest request =SrvcModifyRequestGenerate.generate(phone, tip,isCaller);
		/**
		 * 打印请求参数
		 */
		loger.debug("上海SrvcModify请求参数,StreamingNo{};SrcSystemNo:{};Signature:{};UserNumber:{};SrvcID:{};recordTip:{};TimeStamp:{}",request.getStreamingNo(),request.getSrcSystemNo(),request.getSignature(),request.getUserNumber(),request.getSrvcID(),request.getTimeStamp(),tip.getValue());
		
		
		SrvcModifyResponse resp = fpt.srvcModify(request);
		int resultCode = resp.getResultCode();
		
		if(FtpcsrvcConstant.RESULT_OK != resultCode){
			String errMsg = "ResultCode:"+resultCode+" Description:"+resp.getDescription();
			loger.debug("上海SrvcModify远程调用SrvcModify处理返回结果:{}", errMsg);
			throw new EduException(MessageConstant.SH_POST_ERROR_133011);
		}
		loger.debug("上海SrvcModify业务设置成功,ResultCode:{}，Description:{}", resultCode,resp.getDescription());
	}
	
	
	
	public static void main(String[] args) throws Exception{
		//02162402429
//		userInfoSync("02162586256");
//		srvcSubscribe(ActionTypeEnum.Unsubscribe, "02162586256", true);
//		srvcSubscribe(ActionTypeEnum.Unsubscribe, "02162402494", false);
//		System.out.println(MD5Utils.md5("111111"));
		
//		SrvcModify("02162586256", RecordTipEnum.NO);
	}
}
