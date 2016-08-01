package com.ancun.boss.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.core.constant.Constants;

public class StringUtils {
	
	/** 日志 */
    private static Logger logger = LoggerFactory.getLogger(YulucnUtil.class);

    public static boolean nullToBoolean(String str) {

    	if (str == null || str.trim().length() == 0) {
        	return false;
        }

        return Boolean.valueOf(str.trim());
    }
    
    public static boolean checkConfig(String config, String checkData){

		return checkData.indexOf(";") == -1 && (";" + config + ";").indexOf(";" + checkData + ";") >= 0;
	}
    
    public static String nullToStrTrim(String str) {

    	if (str == null) {
        	str = "";
        }

        return str.trim();
    }
    
    public static boolean isEmpty(String str) {

    	return ((str == null) || (str.trim().length() == 0));
    }
    
    public static boolean isNotEmpty(String str) {

    	return ((str != null) && (str.trim().length() > 0));
    }
    
    public static String nullToUnknown(String str) {

        if (isEmpty(str)) {
        	str = BizRequestConstant.UNKNOWN;
        }

        return str.trim();
    }
    
    public static String decode(String str) {

		return decode(str, Constants.CHARSETNAME_DEFAULT);
	}
    
    public static String decode(String str, String enc) {

		String strDecode = "";

		try {
			if (str != null)
				strDecode = URLDecoder.decode(str, enc);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}

		return strDecode;
	}

    
	
}