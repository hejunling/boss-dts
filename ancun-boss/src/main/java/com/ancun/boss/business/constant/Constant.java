package com.ancun.boss.business.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务常量
 *
 * @Created on 2016年3月14日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class Constant {
	
	//套餐类型：1.主叫；2.被叫
	public static final String TYPE_CALLER = "Caller";
	public static final String TYPE_CALLED = "Called";
	
	//套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
	public static final String TC_TYPE_CALLER = "1";
	public static final String TC_TYPE_BIDIRECTION = "2";
	public static final String TC_TYPE_CALLED = "3";
	
	
	/**
	 * 电信:dx 联通:lt
	 */
	public static final String TYPE_OF_TEL_UNI = "dx|lt";
	
	/**
	 * 电信，联通
	 */
	public static final String PROVENCE_TYPE_DX = "DX";
	public static final String PROVENCE_TYPE_LT = "LT";
	
	/**
	 * 1:个人;2:企业
	 */
	public static final String USER_TYPE_PER = "1";
	public static final String USER_TYPE_ENT = "2";
	public static final String USER_TYPE_PER_ENT = "1|2";
	
	/**
	 * 1:主账号;2:子账号
	 */
	public static final String ACCOUNT_TYPE_MAIN = "1";
	public static final String ACCOUNT_TYPE_SUB = "2";
	
	public static final String INSOURCE_WEB = "web";
	public static final String INSOURCE_IVR = "ivr";

	/**
	 * 是否默认套餐 1:是
	 * @author zkai
	 * @Created on 2016年04月06日
	 */
	public static final String DEFAULT_TC_FLAG = "1";

	// 套餐状态   1.启用 2.停用
	public static final String TAOCAN_STATUS_1 = "1";
	public static final String TAOCAN_STATUS_2 = "2";

	// 号码状态   1.开通;2.停用;3.退订;4.体验。
	public static final String USER_STATUS_1 = "1";
	public static final String USER_STATUS_2 = "2";
	public static final String USER_STATUS_3 = "3";
	public static final String USER_STATUS_4 = "4";
	public static Map<String, String> USER_STATUS_MAP = new HashMap<String, String>();
	static {
		USER_STATUS_MAP.put(USER_STATUS_1, "开通");
		USER_STATUS_MAP.put(USER_STATUS_2, "停用");
		USER_STATUS_MAP.put(USER_STATUS_3, "退订");
		USER_STATUS_MAP.put(USER_STATUS_4, "体验");
	}

	//数据字典module  开通类型：OPENTYPE,省份：PROVINCE
	public static final String PROVINCES = "PROVINCE";
	public static final String OPENTYPE = "OPENTYPE";
	
	
	/**
	 * 注册来源(1:个人后台开通,2:IVR,3:ANDROID,4:IOS,5:地推,6:短信,7:彩信,8:企业后台开通)
	 */
	public static final String INSOURCE_1 = "1";
	public static final String INSOURCE_2 = "2";
	public static final String INSOURCE_3 = "3";
	public static final String INSOURCE_4 = "4";
	public static final String INSOURCE_5 = "5";
	public static final String INSOURCE_6 = "6";
	public static final String INSOURCE_7 = "7";
	public static final String INSOURCE_8 = "8";

	// 包含所有省份code
	public static final String ALL_PROVICE_RPCODE = "000000";


	/**  条件为1或者2的字段属性 标记 1:是；2：否  */
	public static final String MARK_1 = "1";
	public static final String MARK_2 = "2";
	public static final String MARK1OR2 = "1|2";

	/**  条件为0或者1的字段属性 标记 0:关闭；1:开启  */
	public static final String MARK_OFF = "0";
	public static final String MARK_ON = "1";
	public static final String MARK_ON_OR_OFF1 = "0|1";

	/** 是否全包 */
	public static final String FULL_PACKAGE_TRUE = "true";
	public static final String FULL_PACKAGE_FALSE = "false";

	// IVR录音提示标志(0:否; 1:是)
	public static final String RECTIP_TYPE_0 = "0";
	public static final String RECTIP_TYPE_1 = "1";

	// 操作（是否个人转企业。0：否；1：是）
	public static final String OPERATION_TYPE_0 = "0";
	public static final String OPERATION_TYPE_1 = "1";

	// 异常邮件接收人
	public static final String SEND_EMAIL_TO = "hechuang@ancun.com";
}
