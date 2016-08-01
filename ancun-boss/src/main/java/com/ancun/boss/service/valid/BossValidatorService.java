package com.ancun.boss.service.valid;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.common.biz.accesskey.AccessBiz;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.common.biz.persistence.model.Accessinfo;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.annotation.SignType;
import com.ancun.core.constant.RequestConst;
import com.ancun.core.constant.ResponseConst;
import com.ancun.core.exception.EduException;
import com.ancun.core.spring.IValidatorService;
import com.ancun.core.spring.ValidatorBean;
import com.ancun.utils.HmacSha1Utils;
import com.ancun.utils.MD5Utils;

/**
 * BOSS系统校验SERVICE
 * 
 * @author hjl
 *
 * @时间 2015-9-18 下午5:19:48
 */
@Service
public class BossValidatorService implements IValidatorService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/** ip限制的Key */
	private final String IPKEY = "ip_web_boss";
	
	/**默认省份，全部*/
	private final String DEFAULT_PROVINCECODE = "ALL";
	
	/** 文件上传 */
	private static final String FILE_UPLOAD_YES = "1";
	
	@Autowired
	AccessBiz ac;
	
	/* (non-Javadoc)
	 * @see com.ancun.core.spring.IValidatorService#doRequestTypeValid(java.lang.Object, org.springframework.validation.Errors, com.ancun.core.spring.ValidatorBean)
	 */
    @Override
    public void doRequestTypeValid(Object target, Errors errors, ValidatorBean bean) {
    	// 获取请求的request
    			HttpServletRequest request = bean.getRequest();
    			// 获取注解中指定的签名加密方式
    			SignType signType = bean.getSignType();
    			// 获取请求request的签名
    			String sign = bean.getSign();
    			// 获取请求request的内容
    			String content = bean.getContent();
    			// 获取请求request的编码格式
    			String encoding = bean.getEncoding();
    			AccessType[] accessTypes = bean.getAccessTypes();
    			if(null == accessTypes) {
    				/**
    				 * 接口对应AccessType不支持
    				 */
    				throw new EduException(ResponseConst.REQUEST_TYPE_NOT_SUPPORT);
    			}
    			AccessType checkAccessType = AccessType.checkAccessType(accessTypes[0].name(), AccessType.values());
    			if(null == checkAccessType || "".equals(checkAccessType.name())){
    				throw new EduException(ResponseConst.REQUEST_TYPE_NOT_SUPPORT);
    			}
    			// 获取是否要进行ACCESSID,KEY检查
    			boolean checkAccess = bean.isCheckAccess();
    			
    			String fileupload = request.getHeader(RequestConst.FILE_UPLOAD);
    			
    			if(checkAccess) {
    				String accessId ="";
    				String userno = "";
    				if(FILE_UPLOAD_YES.equals(fileupload)) {
    					// 取得请求request里的accessid
    					accessId = request.getHeader("accessid");
    					// 设置用户编号
    					userno = request.getHeader("userno");
        			}else {
        				BeanWrapper jim = new BeanWrapperImpl(target);
        				// 取得请求request里的accessid
        				accessId = (String) jim.getPropertyValue("accessid");
        				// 设置用户编号
        				userno =  (String)jim.getPropertyValue("userno");
        			}
    				log.info("reuqest accessId == " + accessId +",reuqest userno == "+userno);
    				if(StringUtils.isBlank(accessId)) {
    					throw new EduException(MessageConstant.ACCESS_ID_IS_NULL);
    				}
    				
    				if(StringUtils.isBlank(userno)) {
						throw new EduException(MessageConstant.USER_NO_IS_NULL);
					}
    				
    				boolean timeout = ac.isTimeout(accessId);
    				log.info("accessid is timeout " + timeout + ",if timeout programe should delete this accessid");
    				if(timeout) {
    					ac.deleteAccessInfo(accessId);
    					throw new EduException(MessageConstant.ACCESS_ID_TIME_OUT);
    				}
    				
    				Accessinfo accessInfo = ac.getAccessInfo(accessId);
    				if(null == accessInfo || !StringUtils.equals(accessInfo.getUserno(), userno)) {
    					throw new EduException(MessageConstant.ACCESS_ID_ERROR);
    				}
    				// 更新ACCESSID 过期时间
    				ac.updateInvalidateTime(accessId);
    				
    				request.setAttribute(RequestConst.USER_NO,userno);
    				request.setAttribute(RequestConst.REQUEST_ACCESS_ID, accessId);
    				request.setAttribute(BizRequestConstant.REQ_ACCESS_INFO, accessInfo);
    				
    				doSignCheck(signType,accessInfo.getAccesskey(),sign,content,encoding);
    			}

    }

	/* (non-Javadoc)
	 * @see com.ancun.core.spring.IValidatorService#doIpLimitValid(java.lang.Object, org.springframework.validation.Errors, com.ancun.core.spring.ValidatorBean)
	 */
    @Override
    public void doIpLimitValid(Object target, Errors errors, ValidatorBean bean) {
    	try {
			BaseDataServiceImpl baseDataServiceImpl = bean.getBaseDataServiceImpl();
			String ip = bean.getIp();

			//从db中获取ip过滤规则
			String ipFilter = baseDataServiceImpl.getIpLimitMessage(DEFAULT_PROVINCECODE, IPKEY);

			//白名单过滤
			log.debug("请求IP:" + ip);

			if (StringUtils.isNotEmpty(ipFilter)) {
				if (("," + ipFilter + ",").indexOf("," + ip + ",") == -1) {
					throw new EduException(ResponseConst.IP_DENIED);
				}
			}

			log.debug("白名单认证成功");
		} catch (BeansException e) {
			log.error("从BeanWrapper中获取provinceCode、type失败" + e.getMessage());
		}
    }
    /**
	 * 签名
	 * @param signType 签名方式
	 * @param backstageType 后台类型
	 * @param provinceCode 省份编号
	 * @param sign 签名
	 * @param content 请求体
	 * @param encoding 编码方式
	 * @return
	 */
	private void doSignCheck(SignType signType, String accessKey, String sign, String content, String encoding) {

		// 验证MD5加密的签名
		if (SignType.MD5 == signType) {
			this.md5Sign(content, sign);
		}
		// 验证HMACSHA加密的签名
		else {
			this.hmacShaSign(accessKey, sign, content, encoding);
		}

	}

	/**
	 * 验证MD5加密的签名
	 *
	 * @param content
	 * @param sign
	 */
	private void md5Sign(String content, String sign) {
		String encryptContent = MD5Utils.md5(content).toLowerCase();
		if(!sign.equals(encryptContent)){
			throw new EduException(ResponseConst.SIGN_NAME_NOT_MATCH);
		}
	}

	/**
	 * 验证HMACSHA加密的签名
	 *
	 * @param backstageType
	 * @param provinceCode
	 * @param sign
	 * @param content
	 * @param encoding
	 * @throws EduException
	 */
	private void hmacShaSign(String accessKey, String sign, String content, String encoding) {

		boolean bool = HmacSha1Utils.signCheck(MD5Utils.md5(content).toLowerCase(), accessKey, sign, encoding, 110019);
		if(!bool){
			throw new EduException(ResponseConst.SIGN_NAME_NOT_MATCH);
		}
	}
}
