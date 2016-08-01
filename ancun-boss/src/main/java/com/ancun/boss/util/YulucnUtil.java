/**
 * YulucnUtil.java
 *
 * @Title:
 * @Description:
 * @Copyright:���ݰ�������Ƽ����޹�˾ Copyright (c) 2013
 * @Company:���ݰ�������Ƽ����޹�˾
 * @Created on 2013-10-24 ����11:07:10
 * @author lijianhang
 * @version 1.0
 */

/**
 * 
 */
package com.ancun.boss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.core.exception.EduException;
import com.ancun.utils.MD5Utils;

/**
 * @author lijianhang
 * 变更履历，上海电信音证宝 付费用户鉴权  
 * 2014/11/6 从accore迁移过来。
 */
public class YulucnUtil {
	
	/** 日志 */
    private static Logger logger = LoggerFactory.getLogger(YulucnUtil.class);

	private static String SHYZBPHONECHECK_URL = "http://61.152.224.114:8989/QueryPhone/in";
	private static String SHYZBPHONECHECK_KEY = "company@97Result(*)";
	private static String SHYZBPHONECHECK_MERID = "3004";
	private static String SHYZBPHONECHECK_PROID = "1001";
	private static String SHYZBPHONECHECK_CODE_SUCCESS = "0000";
	private static String SHYZBPHONECHECK_CODE_PHONENOERROR = "0003";
	private static String SHYZBPHONECHECK_CODE_NOTTELECOM = "0007";
	private static String SHYZBPHONECHECK_CODE_NOTPOSTPAID = "0008";
	private static String SHYZBPHONECHECK_EXCEPTPHONES = "18968127360;13381802721;13067867153;56009367";

	public static void checkShyzbphone(String phone) throws EduException {

		if (StringUtils.checkConfig(SHYZBPHONECHECK_EXCEPTPHONES, phone)) {
			return;
		}

		StringBuffer paras = new StringBuffer();
		paras.append("auth=" + phone);
		paras.append("&merID=" + SHYZBPHONECHECK_MERID);
		paras.append("&proID=" + SHYZBPHONECHECK_PROID);
		paras.append("&sign="
				+ MD5Utils.md5(
						phone + SHYZBPHONECHECK_MERID + SHYZBPHONECHECK_KEY)
						.toUpperCase());

		for (int i = 0; i < 3; i++) {
			String result = "";
			try {
				result = StringUtils.nullToStrTrim(RichUtils.postURLReturn(
						SHYZBPHONECHECK_URL, paras.toString(), 5000, 5000, 
						"UTF-8", ""));
			} catch (Exception e) {
				logger.error(StringUtils.nullToStrTrim(e.getMessage()));
			}

			String code = result.split(" ")[result.split(" ").length - 1];
			if (code.equals(SHYZBPHONECHECK_CODE_SUCCESS)) {
				return;
			}

			logger.error( phone + "->" + code);
			if (code.equals(SHYZBPHONECHECK_CODE_PHONENOERROR)) {
				throw new EduException(MessageConstant.PHONE_FORMAT_ILLEGAL);
			}
			if (code.equals(SHYZBPHONECHECK_CODE_NOTTELECOM)) {
				throw new EduException(MessageConstant.NOT_TELECOM);
			}
			if (code.equals(SHYZBPHONECHECK_CODE_NOTPOSTPAID)) {
				throw new EduException(MessageConstant.NOT_POSTPAID);
			}
		}

		throw new EduException(MessageConstant.SYSTEM_IS_BUSY);
	}

}
