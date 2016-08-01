package com.ancun.boss.constant;

/**
 * 业务请求常量
 *
 * @author hjl
 * @时间 2015-9-22 上午9:23:26
 */
public class BizRequestConstant {

    /**
     * 请求ACCESS_INFO
     */
    public static final String REQ_ACCESS_INFO = "REQ_ACCESS_INFO";

//    ----------------------- add by mif -------------------------------------------------------
    /**
     * 是否 已删除
     */
    public static final String DELETE_YES = "YES";
    public static final String DELETE_NO = "NO";

    /**
     * 新增 / 修改标志
     */
    public static final String TO_ADD = "1";
    public static final String TO_UPDATE = "2";

    /**
     * 初始密码
     */
    public static final String INIT_PWD = "123456";
    /**
     * 运营商(YD/LT/DX)  移动/联通/电信
     */
    public static final String OPERATOR_DX = "DX";
    public static final String OPERATOR_YD = "YD";
    public static final String OPERATOR_LT = "LT";

    /**
     * 数据权限 默认moudlecode
     */
    public static final String MOUDLE_CODE_BIZ_NAME = "BIZ-NAME";

    /**
     * 是否有
     */
    public static final String HAS_YES = "YES";
    public static final String HAS_NO = "NO";


    /**
     * 结算方式： 包席价格*坐席数；成功件单价*成功量；员工工资+保险+其他各项
     */
    public static final String JKFS_BXJG_ZXS = "JKFS-001";
    public static final String JKFS_CGJDJ_CGL = "JKFS-002";
    public static final String JKFS_YGGZ_BX_QTGX = "JKFS-003";


    /**
     * 未完成 / 完成
     */
    public static final String UNCOMPLETE   = "1";
    public static final String COMPLETE     = "2";

    /**
     * 类型(1:业务介绍;2:常见问题FAQ)
     */
    public static final String TO_DETAIL="1";
    public static final String TO_FAQ="2";

    /** 号码池过滤层级(号码状态) */
    public static final int FILTER_LEVEL_INNER_PHONE    = 0;
    public static final int FILTER_LEVEL_EXIST          = 1;
    public static final int FILTER_LEVEL_IS_BLACK       = 2;
    public static final int FILTER_LEVEL_OPEN_ALREADY   = 3;
    public static final int FILTER_PAYPHONE             = Integer.MAX_VALUE;

    /** 号码池是否过滤完成 */
    public static final String PAYPHONE_YES             = "YES";
    public static final String PAYPHONE_NO              = "NO";

    /** 付费号码 */
    public static final String FILTER_OVER_COMPLETE     = "YES";
    public static final String FILTER_OVER_UNCOMPLETE   = "NO";

    /** 清洗状态 */
    public static final String CLEAN_STATUS_CLEANING             = "1";
    public static final String CLEAN_STATUS_PAYPHONE_CLEANING   = "2";
    public static final String CLEAN_STATUS_COMPLETE             = "3";

    /** 消息类型 */
    public static final String NOTICE_TYPE_ALL          = "ALL";
    public static final String NOTICE_TYPE_EMAIL        = "EMAIL";
    public static final String NOTICE_TYPE_SMS          = "SMS";

    /** 消息读取标志 */
    public static final String NOTICE_UNREAD            = "1";
    public static final String NOTICE_READED            = "2";

    /** 退订**/
    public static final String CANCEL_REQIRE_TD         = "TD-003";
    /** 退费**/
    public static final String CANCEL_REQIRE_TF         = "TD-001";


    /**NO*/
    public static final String NO="NO";
    /**YES*/
    public static final String YES="YES";

    /**
     * 呼入质检平均分
     * add by zkai
     */
    public static final String MONTH_AVERAGE="1"; // 月平均分
    public static final String QUARTER_AVERAGE="2"; // 季度平均分
    public static final String HALF_YEAR_AVERAGE="3"; // 半年平均分
    public static final String YEAR_AVERAGE="4"; //年平均分
    
    /**
     * 号码类别(0:固话;1:手机)
     */
  	public static final String USER_PHONETYPE_PHONE = "1";
  	public static final String USER_PHONETYPE_TEL = "0";
  	 
 	/**
 	 * 白名单类型(1.出账白名单;2.鉴权白名单)
 	 */
 	public static final String CZ_WHITE_LIST = "1";
 	public static final String JQ_WHITE_LIST = "2";
 	
 	/**
 	 * 鉴权结果(1.鉴权合法;2.鉴权不合法)
 	 */
 	public static final String AUTH_USER_TRUE = "1";
 	public static final String AUTH_USER_FALSE = "2";
 	
 	/**
 	 * 鉴权来源(1.IVR;2.后台)
 	 */
 	public static final String AUTH_SOURCE_IVR= "1";
 	public static final String AUTH_SOURCE_WEB= "2";
 	
 	/**
 	 * 上海区号
 	 */
 	public static final String SH_EREACODE = "021";
 	
 	/**
 	 * 常量类
 	 */
 	public static final String UNKNOWN = "unknown";
    
    /**user_type*/
    public static final String PERSONAL_USER = "1";
    /**user_type*/
    public static final String ENT_USER = "2";
    
    /**account_type*/
    public static final String MAIN_ACCOUNT = "1";
    /**account_type*/
    public static final String SUB_ACCOUNT = "2";
    /**account_type*/
    public static final String PERSONAL_ACCOUNT = "3";
    
    /**user_status(1:正常;2:暂停;3:注销)*/
    public static final String OPENED_USER = "1";
    /**user_status(1:正常;2:暂停;3:注销)*/
    public static final String PAUSE_USER = "2";
    /**user_status(1:正常;2:暂停;3:注销)*/
    public static final String CANCEL_USER = "3";
    /**open_cancel(1:开通,2:退订)*/
    public static final String OPEN_STATUS = "1";
    /**open_cancel(1:开通,2:退订)*/
    public static final String CANCEL_STATUS = "2";
    
    /**
     * 业务用户，开通退订
     */
    public static final String OPEN = "1";
    public static final String CANCEL = "2";

    /**
     * 呼入类型(1:呼入登记，2:投诉退订)
     */
    public static final String CALL_IN_RECORD = "1";
    public static final String CANCEL_RECORD = "2";
    
    
    /**
     * 业务开通退订来源
     */
    public static final String SOURCE = "web";
    
    /** 自动开通的省份 */
	public static final String AUTO_OPEN_PROVINCES = "AUTO_OPEN_PROVINCES";
	// 广西
    public static final String PROVINCES_GUANGXI = "450000";
    // 贵州
    public static final String PROVINCES_GUIZHOU = "520000";
    // 江苏
    public static final String PROVINCES_JIANGSU = "320000";
    // 福建
    public static final String PROVINCES_FUJIAN = "350000";
    // 上海
    public static final String PROVINCES_SHANGHAI = "310000";
    
    // 北京
    public static final String PROVINCES_BEIJING = "110000";
    
    // CP联通
    public static final String PROVINCES_CP_UNICOM = "000000";
    
	/** 套餐类型，主叫 */
	public static final String TC_TYPE_1 = "1";
	/** 套餐类型，双向 */
	public static final String TC_TYPE_2 = "2";
	/** 套餐类型，接入号 */
	public static final String TC_TYPE_3 = "3";
	
	public static final String MARK_YES = "1";
	
	public static final String MARK_NO = "0";
	
	//录音提示(0：提示;1：不提示；)
	public static final String RECTIP_FLAG_NO="0";
	public static final String RECTIP_FLAG_YES="1";
	
	// 业务用户插入失败提醒邮箱
	public static final String NOTICE_MAIL_ADDRESS ="410002";
	
	/**
	 * 初始密码设置标志-->(0:否; 1:是)
	 */
	public static final String DEFAULT_PWD_FLAG_Y = "1";
	public static final String DEFAULT_PWD_FLAG_N = "0";
	
	/** 福建前置机地址 */
	public static final String FUJIAN_FRONT_URI = "FUJIAN_FRONT_URI";
	/** 前置机 ACCESSID */
	public static final String FUJIAN_FRONT_ACCESSID = "FUJIAN_FRONT_ACCESSID";
	/** 前置机 ACCESSKEY */
	public static final String FUJIAN_FRONT_ACCESSKEY = "FUJIAN_FRONT_ACCESSKEY";
	/** 请求次数 超出发送邮件 */
	public static final String FUJIAN_REQUEST_TIMES = "FUJIAN_REQUEST_TIMES";
	
	/** 福建邮件发送开关(1,打开；0：关闭) */
	public static final String FUJIAN_EAMIL_SEND_FLAG = "FUJIAN_EAMIL_SEND_FLAG";
	/** 邮件收件人 */
	public static final String FUJIAN_EMAIL_SEND_TO = "FUJIAN_EMAIL_SEND_TO";
	/** 福建同步前置机失败主题 */
	public static final String FUJIAN_EMAIL_SUBJECT = "FUJIAN_EMAIL_SUBJECT";
	
	/**
	 * 福建短信发送开关 1：打开；0：关闭
	 */
	public static final String FUJIAN_EAMIL_SEND_YES = "1";
	public static final String FUJIAN_EAMIL_SEND_NO = "0";
	
	/****
	 * 操作（是否个人转企业。0：否；1：是）
	 */
	public static final String OPERATION_YES = "1";
	public static final String OPERATION_NO = "0";
	
    // 计费请求
    /** 计费开通 */
    public static final String FARE_OPEN = "FAREOPEN";
    /** 计费取消 */
    public static final String FARE_CANCEL = "FARECANCEL";
    
	/**
	 * add by chenb 2015-12-25
	 * 北京联通接口URL,ACCESSID,ACCESSKEY
	 */
	public static final String CSP_URL = "CSP_URL";
	public static final String CSP_ACCESSID = "CSP_ACCESSID";
	public static final String CSP_ACCESSKEY = "CSP_ACCESSKEY";
	
	/** 是否是全包-TRUE */
	public static final String FULL_PACKAGE_TRUE = "true";
	/** 是否是全包-FALSE */
	public static final String FULL_PACKAGE_FALSE = "false";

    // add by zkai on 2016/06/12
    // ------------------------------begin-----------------------
    /** 超级管理员 */
    public static final String SUPERMAN_YES ="YES";
    public static final String SUPERMAN_NO ="NO";
    /** 客服经理 */
    public static final String CUSTOM_SERVICE_MANAGER ="d058d872-0051-4259-abe1-c97c53efe9b2";
    /** 数据权限 */
    public static final String DATA_ACCESS_ALL ="ALL"; // 能看所有
    public static final String DATA_ACCESS_PART ="PART"; // 查看部分
    // ------------------------end--------------------------
}
