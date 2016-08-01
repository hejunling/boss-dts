package com.ancun.boss.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ancun.boss.persistence.model.DetailLog;
import com.ancun.core.constant.ResponseConst;

/**
 * 线程操作内容
 * @author hjl
 *
 * @时间 2015-9-22 上午10:55:42
 */
public class ThreadLocalUtil {
	
	private static final ThreadLocal<DetailLog> detailLog = new ThreadLocal<DetailLog>();
	
	
	/**
	 * 用于登录之后的操作
	 * @param content
	 */
	public static void setContent(String content) {
		detailLog.set(createDetailLog("",content));
	}
	
	/**
	 * 登录之前的操作
	 * @param userno
	 * @param content
	 */
	public static void setContent(String userno,String content) {
		detailLog.set(createDetailLog(userno,content));
	}
	
	private static DetailLog createDetailLog(String userno,String content) {
		DetailLog value = new DetailLog();
		if(StringUtils.isNotBlank(userno)) {
			value.setUserno(userno);
		}
		value.setContent(content);
		value.setDealtime(new Date());
		value.setResultcode(String.valueOf(ResponseConst.SUCCESS));
		value.setResultremark("成功!");
		return value;
	}
	
	/**
	 * 获取操作内容
	 * @return
	 */
	public static DetailLog getContent() {
		return detailLog.get();
	}
}
