package com.ancun.boss.constant;

/**
 * 消息提示常量
 *
 * @author hjl
 * @时间 2015-9-22 上午9:29:46
 */
public class MessageConstant {

    /**
     * 访问ID为空
     */
    public static final int ACCESS_ID_IS_NULL = 200001;

    /**
     * 访问用户编号为空
     */
    public static final int USER_NO_IS_NULL = 200002;

    /**
     * 访问ACCESS ID 超时
     */
    public static final int ACCESS_ID_TIME_OUT = 200003;

    /**
     * 访问 ACCESS ID 错误，不匹配
     */
    public static final int ACCESS_ID_ERROR = 200004;

    //登录相关，by yck
    /**
     * 用户没有任何权限
     */
    public static final int NO_AUTHORITY = 200005;


    /**
     * 密码错误
     */
    public static final int PWD_ERROR = 200007;

    /**
     * 两次输入密码不相同
     */
    public static final int PWD_NOT_SAME = 200008;

    /**
     * 密码不符合规则
     */
    public static final int PWD_NOT_IN_RULE = 200009;


    /**
     * 工号或者密码错误
     */
    public static final int USER_NOT_EXIST = 200006;


    //    ----------------------- add by mif -------------------------------------------------------
    /**
     * 用户不存在或者状态不正常
     */
    public static final int REQUEST_USER_NOT_EXISTS_OR_ABNORMAL = 310000;

    /**
     * 用户名不能为空
     */
    public static final int USER_NAME_MUST_BE_NOT_EMPTY = 310001;

    /**
     * 密码不能为空
     */
    public static final int PASS_WORD_MUST_BE_NOT_EMPTY = 310002;

    /**
     * 用户名已存在
     */
    public static final int USERNAME_HAS_EXISTS = 310003;

    /**
     * 查询用户失败
     */
    public static final int QUERY_USER_INFO_FAILED = 310004;

    /**
     * 角色名已存在
     */
    public static final int ROLE_NAME_EXISTS = 310005;

    /**
     * 非超管请选择功能权限
     */
    public static final int PERMISSION_UNCHOSED = 310006;

    /**
     * 导入数据为空
     */
    public static final int IMPORT_DATA_ISENPTY = 310007;

    /**
     * 该邮箱已存在
     */
    public static final int EMAIL_HAS_USED = 310008;


    /**
     * 用户工号已存在
     */
    public static final int USERNO_HAS_EXISTS = 310009;

    /**
     * 基础配置数据为空,请联系管理员
     */
    public static final int BASIC_CONFIG_IS_NULL = 310010;

    /**
     * 系统暂无角色，请联系管理员
     */
    public static final int SYSTEM_ROLE_IS_NULL = 310011;

    /**
     * 系统暂无数据权限，请联系管理员
     */
    public static final int SYSTEM_DATA_PERMISSION_IS_NULL = 310012;

    /**
     * 用户工号不能为空
     */
    public static final int USER_NO_MUST_BE_NOT_EMPTY = 310013;

    /**
     * 部门不能为空
     */
    public static final int ORG_MUST_BE_NOT_EMPTY = 310014;

    /**
     * 常用邮箱不能为空
     */
    public static final int EMAIL_MUST_BE_NOT_EMPTY = 310015;

    /**
     * 邮箱格式错误
     */
    public static final int EMAIL_IS_WRONG = 310016;
    /**
     * 联系电话不能为空
     */
    public static final int CONTACT_PHONE_MUST_BE_NOT_EMPTY = 310017;
    /**
     * 联系电话格式错误
     */
    public static final int CONTACT_PHONE_FORMAT_ERROR = 310018;

    /**
     * 新增/修改标志不能为空
     */
    public static final int MODIFY_FLAG_MUST_BE_NOT_EMPTY = 310019;

    /**
     * 输入参数长度非法
     */
    public static final int INFO_LENGTH_ILLEGAL = 310020;

    /**
     * 角色名不能为空
     */
    public static final int ROLE_NAME_MUST_BE_NOT_EMPTY = 310021;

    /**
     * 请选择是否为超管
     */
    public static final int IS_SUPERMAN_MUST_BE_NOT_EMPTY = 310022;

    /**
     * 角色编码不能为空
     */
    public static final int ROLE_NO_MUST_BE_NOT_EMPTY = 310023;

    /**
     * 记录编号不能为空
     */
    public static final int ID_MUST_BE_NOT_EMPTY = 310024;

    /**
     * 外呼样本不能为空
     */
    public static final int CALLED_NUMBER_MUST_BE_NOT_EMPTY = 310025;
    /**
     * 接通数不能为空
     */
    public static final int CONNECT_NUMBER_MUST_BE_NOT_EMPTY = 310026;
    /**
     * 日成功量不能为空
     */
    public static final int SUCCESS_DAILY_MUST_BE_NOT_EMPTY = 310027;
    /**
     *客户来源（批次）不能为空
     */
    public static final int PHONE_SOURCE_MUST_BE_NOT_EMPTY_CODE = 310028;
    /**
     *请至少选择一个角色
     */
    public static final int ROLE_MUST_BE_NOT_EMPTY_CODE = 310029;


    //    ----------------------- add by xys -------------------------------------------------------
    /**
     * 业务名称不能为空
     */
    public static final int MARKETCHECK_BUSINESS_MUST_BE_NOT_EMPTY = 310040;

    /**
     * 外呼团队不能为空
     */
    public static final int MARKETCHECK_CALLEDTEAM_MUST_BE_NOT_EMPTY = 310041;

    /**
     * 营销时间不能为空
     */
    public static final int MARKETCHECK_MARKETTIME_MUST_BE_NOT_EMPTY = 310042;

    /**
     * 手机号码在文件名称中开始位置不能为空
     */
    public static final int MARKETCHECK_PHONESTARTPOS_MUST_BE_NOT_EMPTY = 310043;

    /**
     * 手机号码在文件名称中开始位置必须为数字
     */
    public static final int MARKETCHECK_PHONESTARTPOS_MUST_BE_DIGIT = 310044;

    /**
     * 文件名称不能为空
     */
    public static final int MARKETCHECK_FILENAME_MUST_BE__NOT_EMPTY = 310045;

    /**
     * 文件MD5不能为空
     */
    public static final int MARKETCHECK_FILEMD5_MUST_BE__NOT_EMPTY = 310046;

    /**
     * 文件MD5不一致
     */
    public static final int MARKETCHECK_FILEMD5_NOT_CONSISTENT = 310047;

    /**
     * 文件AES加密失败
     */
    public static final int FILE_AES_ENCRYPT_FAIL = 310048;

    /**
     * 文件发送到上传组件异常
     */
    public static final int POST_TO_UP2YUN_ERROR = 310049;


    /**
     * 查询呼入登记信息失败
     */
    public static final int QUERY_CALL_IN_RECORD_FAILED = 310050;

    /**
     * 查询投诉退订信息失败
     */
    public static final int QUERY_CANCEL_RECORD_FAILED = 310051;

    /**
     * 业务编号已存在
     */
    public static final int BUSINESS_HAS_EXISTS = 310052;

    /**
     * 业务编号不能为空
     */
    public static final int BUSINESS_MUST_BE_NOT_EMPTY = 310053;

    /**
     * 从指定位置开始截取的手机号码为空
     */
    public static final int MARKETCHECK_INTERCEPTED_PHONE_IS_EMPTY = 310054;

    /**
     * 从指定位置开始截取的手机号码少于11位
     */
    public static final int MARKETCHECK_INTERCEPTED_PHONE_LESSTHAN_11 = 310055;

    /**
     * 从指定位置开始截取的号码不是手机号码
     */
    public static final int MARKETCHECK_INTERCEPTED_PHONE_IS_NOT_PHONE = 310056;

    /** 批次不能为空 */
    public static final int PHONE_SOURCE_MUST_BE_NOT_EMPTY = 310057;

    /**
     * 营销成本日期不能为空
     */
    public static final int MARKETCOST_MONTH_MUST_BE_NOT_EMPTY = 310060;


    /**
     * 结款方式不能为空
     */
    public static final int JK_TYPE_MUST_BE_NOT_EMPTY = 310061;


    /**
     * 包席价格为空
     */
    public static final int SIT_PRICE_MUST_BE_NOT_EMPTY_CODE = 310062;



    /**
     * 坐席数不能为空
     */
    public static final int SIT_NUMBER_MUST_BE_NOT_EMPTY_CODE = 310063;


    /**
     * 成功价单价不能为空
     */
    public static final int SUC_PER_PRICE_MUST_BE_NOT_EMPTY_CODE = 310064;

    /**
     * 成功量不能为空
     */
    public static final int SUC_NUMBER_MUST_BE_NOT_EMPTY_CODE = 310065;


    /**
     * 坐席工资不能为空
     */
    public static final int EMP_PAY_MUST_BE_NOT_EMPTY_CODE = 310066;


    /**
     * 话费不能为空
     */
    public static final int BILL_MUST_BE_NOT_EMPTY_CODE = 310067;



    /**
     * 管理成本不能为空
     */
    public static final int MANAGE_PAY_MUST_BE_NOT_EMPTY_CODE = 310068;


    /**
     * 保险不能为空
     */
    public static final int INSURANCE_MUST_BE_NOT_EMPTY_CODE = 310069;

    /**
     * 水电费公式不能为空
     */
    public static final int WE_EXP_MUST_BE_NOT_EMPTY_CODE = 310070;

    /**
     * 网费公式不能为空
     */
    public static final int NET_EXP_MUST_BE_NOT_EMPTY_CODE = 310071;


    /**
     * 房租公式不能为空
     */
    public static final int RENT_EXP_MUST_BE_NOT_EMPTY_CODE = 310072;

    /**
     * 硬件设备不能为空
     */
    public static final int DEVICE_PAY_MUST_BE_NOT_EMPTY_CODE = 310073;


    /**
     * 总成本不能为空
     */
    public static final int TOTAL_COST_MUST_BE_NOT_EMPTY_CODE = 310074;


    /**
     * 成功件数不能为空
     */
    public static final int SUCCESS_NUMBER_MUST_BE_NOT_EMPTY_CODE = 310075;


    /**
     * 单件成本不能为空
     */
    public static final int AVG_COST_MUST_BE_NOT_EMPTY_CODE = 310076;

    /**
     * 管理成本公式不能为空
     */
    public static final int MANAGE_EXP_MUST_BE_NOT_EMPTY_CODE = 310077;

    /**
     * 水电费不能为空
     */
    public static final int WE_PAY_MUST_BE_NOT_EMPTY_CODE = 310078;

    /**
     * 网费不能为空
     */
    public static final int NET_PAY_MUST_BE_NOT_EMPTY_CODE = 310079;

    /**
     * 房租不能为空
     */
    public static final int RENT_MUST_BE_NOT_EMPTY_CODE = 310080;

    /**
     * 总计结果与计算结果不相等
     */
    public static final int TOTAL_IS_NOT_EQUAL = 310081;

    /**
     * 呼入质检列表不存在
     */
    public static final int CALL_QUALITY_LIST_NULL = 310082;

    /**
     * 呼入质检excel列表为空
     */
    public static final int CALL_QUALITY_IMPORT_LIST_NULL = 310083;

    /**
     * 用户工单不存在
     */
    public static final int WORK_ORDER_NOT_EXIST = 310090;

    /**
     * 单一工单编号下有多条用户工单
     */
    public static final int WORK_ORDER_EXIST_MORE = 310091;


    /**
     * 单件成本与计算单件成本不相等
     */
    public static final int AVG_COST_IS_NOT_EQUAL = 310092;

    // ----------chenb----------
    /**
     * 录音文件不存在
     */
    public static final int VOICE_FILE_NOT_EXIST = 320061;

    /**
     * 推广方式不能为空
     */
    public static final int MARKET_WAY_MUST_BE_NOT_EMPTY = 320062;

    /**
     * 营销工号不能为空
     */
    public static final int MARKET_NO_MUST_BE_NOT_EMPTY = 320063;

    /**
     * 质检工号不能为空
     */
    public static final int CHECK_NO_MUST_BE_NOT_EMPTY = 320064;

    /**
     * 质检结果不能为空
     */
    public static final int CHECK_RESULT_MUST_BE_NOT_EMPTY = 320065;

    /**
     * 外呼团队不能为空
     */
    public static final int CALLED_TEAM_MUST_BE_NOT_EMPTY = 320066;

    /**
     * 电话号码不能为空
     */
    public static final int PHONENO_MUST_BE_NOT_EMPTY = 320067;
    
    /**
     * 请求省份不能为空
     */
    public static final int PROVINCE_NOT_BE_EMPTY = 320074;

    /**
     * 营销时间不能为空
     */
    public static final int MARKET_TIME_MUST_BE_NOT_EMPTY = 320068;

    /**
     * 质检人不能为空
     */
    public static final int SAMPLING_MUST_BE_NOT_EMPTY = 320069;

    /**
     * 电话号码不存在
     */
    public static final int PHONENO_NOT_EXISTS = 320070;

    /**
     * 电话号码已存在
     */
    public static final int PHONENO_HAS_EXISTS = 320071;

    /** 电话号码格式非法  */
    public static final int PHONE_FORMAT_ILLEGAL = 320073;

    /** 业务名称长度超出  */
    public static final int BUSINESS_LENGTH_ILLEGAL = 320082;

    /** 推广方式长度超出  */
    public static final int MARKETWAY_LENGTH_ILLEGAL = 320083;

    /** 营销工号长度超出  */
    public static final int MARKETNO_LENGTH_ILLEGAL = 320084;

    /** 质检工号长度超出  */
    public static final int CHECKNO_LENGTH_ILLEGAL = 320085;

    /** 质检结果长度超出  */
    public static final int CHECKRESULT_LENGTH_ILLEGAL = 320086;

    /** 外呼团队长度超出  */
    public static final int CALLEDTEAM_LENGTH_ILLEGAL = 320087;

    /** 电话号码长度超出  */
    public static final int PHONENO_LENGTH_ILLEGAL = 320088;

    /** 营销时间长度超出  */
    public static final int MARKETTIME_LENGTH_ILLEGAL = 320089;

    /** 备注长度超出  */
    public static final int REMARK_LENGTH_ILLEGAL = 320090;

    /** 质检人长度超出  */
    public static final int SAMPLING_LENGTH_ILLEGAL = 320091;

    // ----------chenb----------

    /**
     * 查询导入失败详细信息失败
     */
    public static final int IMPORT_FAILED_RECORD_FAILED =320075;
    // ----------jzy----------
    /**系统提醒查询失败**/
    public static final int QUERY_SYSTEM_NOTICE_FAILED =  320080;

    /**
     * 获取时间不能为空
     */
    public static final int GET_TIME_MUST_BE_NOT_EMPTY = 320081;

    /**
     * 来电时间不能为空
     */
    public static final int CALL_TIME_MUST_BE_NOT_EMPTY = 320092;

    /**
     * 称呼不能为空
     */
    public static final int NAME_MUST_BE_NOT_EMPTY = 320093;

    /**
     * 性别不能为空
     */
    public static final int SEX_MUST_BE_NOT_EMPTY = 320094;

    /**
     * 有无录音选项不能为空
     */
    public static final int VOICE_MUST_BE_NOT_EMPTY = 320095;

    /**
     * 网站域名不能为空
     */
    public static final int DOMAIN_MUST_BE_NOT_EMPTY = 320099;

    /**
     * 上线日期不能为空
     */
    public static final int UPDATE_TIME_MUST_BE_NOT_EMPTY = 320100;

    //  add by zkai 2015-11-27
    /**
     * 被质检人不能为空
     */
    public static final int CHECKED_NO_MUST_BE_NOT_EMPTY = 320101;

    /**
     * 质检时间不能为空
     */
    public static final int CALL_QUALITY_TIME_MUST_BE_NOT_EMPTY = 320102;

    /**
     * 基准点不能为空
     */
    public static final int DATUM_POINT_NO_MUST_BE_NOT_EMPTY = 320103;

    /**
     * 服务态度不能为空
     */
    public static final int SERVICE_ATTITUDE_NO_MUST_BE_NOT_EMPTY = 320104;

    /**
     * 业务有效性不能为空
     */
    public static final int BUSSINESS_EFFICIENCY_NO_MUST_BE_NOT_EMPTY = 320105;

    /**
     * 规范操作不能为空
     */
    public static final int STANDARD_OPERATION_NO_MUST_BE_NOT_EMPTY = 320106;

    /**
     * 服务技巧不能为空
     */
    public static final int SERVICE_SKILL_NO_MUST_BE_NOT_EMPTY = 320107;

    /**
     * 总分不能为空
     */
    public static final int TOTALSCORE_NO_MUST_BE_NOT_EMPTY = 320108;

    /**
     * 不存在于鉴权白名单中
     */
    public static final int NOT_EXIST_IN_WHITE_USER = 320109;
    
    /**
     * 非后付费用户
     */
    public static final int NOT_POSTPAID = 321010;
    
    /**
     * 系统忙，请稍后再试
     */
    public static final int SYSTEM_IS_BUSY = 321011;
    
    /**
     * 不是电信
     */
    public static final int NOT_TELECOM = 321012;
    
    /**
     * 号码归属地去请求省份不相符
     */
    public static final int NOT_EQUAL_RPCODE = 321013;
    
    /**
     * 鉴权白名单信息不唯一
     */
    public static final int AUTH_INFO_IF_NOT_ONLY =321015;
    
    /**
     * 号码归属地信息为空
     */
    public static final int REGIONAREA_IS_NULL = 321014;
    
    //业务用户退订
    /**
     * 退费金额错误
     */
    public static final int BIZ_USER_CANCEL_REFUNDSMONEY = 330001;
    
    /**
     * 业务用户不唯一或不存在
     */
    public static final int BIZ_USER_IS_NOT_ONLY = 330002;

    /**
     * 业务数据表插入数据失败
     */
    public static final int BIZ_USER_INSERT_TABLE_ERROR = 330003;
    
    /**
     * 业务用户状态不正确
     */
    public static final int BIZ_USER_STATE_ERROR = 330004;
    
    /**
     * 业务用户状远程接口调用错误
     */
    public static final int BIZ_USER_INTERFACE_ERROR = 330005;
    
    // ---------------------业务用户开通------------------------
    
    /**
     * 用户开通失败
     */
    public static final int BIZUSER_OPEN_FAILED = 330006;
    
	/**号码已开通其他业务,请退订该业务后,再进行开通 */
	public static final int USER_ALREADY_OPEN = 330007;
	
	/**
	 * 企业编号不能为空
	 */
	public static final int USER_ENT_NO_IS_NULL = 330008;
	
	/**
	 * 套餐类型找不到
	 */
	public static final int BIZ_USER_TAOCAN_NOT_FOUND = 330009;
	
	/**
	 * 北京联通开通接口调用失败
	 */
	public static final  int BJUNICOM_OPEN_ERROR= 330010;
	
	/**
	 * 北京联通退订接口调用失败
	 */
	public static final  int BJUNICOM_CLOSE_ERROR= 330011;
	
	/** 注册来源不能为空 */
	public static final int SIGNUPSOURCE_MUST_BE_NOT_EMPTY = 330012;
	
	/**
	 * 号码鉴权错误
	 */
	public static final  int PHONE_AUTH_ERROR= 330013;
	
	/**
	 * 是否付费为必填值
	 */
	public static final  int ISREFUNDS_IS_NULL= 330021;

	//  -----------------------开通添加-----------------
	/**
	 * 业务用户编码格式错误
	 */
	public static final  int BIZUSERNO_FORMAT_ERROR= 330014;
	
	/**
	 * 省份编码格式错误
	 */
	public static final  int RPCODE_FORMAT_ERROR= 330015;
	
	/**
	 * 套餐编号不能为空
	 */
	public static final  int TCCODE_NOTNULL= 330016;
	
	/**
	 * 备注格式错误
	 */
	public static final  int REMARK_FORMAT_ERROR= 330017;
	
	/**
	 * 外呼来源格式错误
	 */
	public static final  int CALLSOURCE_FORMAT_ERROR= 330018;
	
	/**
	 * 运营商类型不能为空
	 */
	public static final  int TYPE_CODE_NOTNULL= 330019;
	
	/**
	 * 业务编号不能为空
	 */
	public static final  int BIZNO_NOTNULL= 330020;
	

	
	//------------上海远程调用异常******begin******------------
	/**
	 * 调用远程用户信息同步接口异常
	 */
	public static final int SH_POST_ERROR_133008 = 133008;
	
	/**
	 * 调用远程用户信息订购关系接口异常
	 */
	public static final int SH_POST_ERROR_133009 = 133009;
	
	/**
	 * 调用远程主叫录音提示接口异常
	 */
	public static final int SH_POST_ERROR_133011 = 133011;
	
	/**
	 * http请求异常
	 */
	public static final int SH_POST_ERROR_131018 = 131018;
	
	/**
	 * http返回状态码异常
	 */
	public static final int SH_POST_ERROR_131019 = 131019;
	
	/**
	 * 系统忙，请稍候再试
	 */
	public static final int SH_POST_ERROR_100002 = 100002;
	
	//------------上海远程调用异常*******end*******------------
	//-------------ADD BY HECHUAN----------------
	/**
	 * 数据库暂时没有相应的配置
	 */
	public static final int NO_CONFIG_OF_DB = 340000;

	/**
	 * 数据库配置重复
	 */
	public static final int MANY_CONFIG_OF_DB = 340001;

	/**
	 * 配置省份跟输入省份代码不一致，请检查
	 */
	public static final int NOT_THE_SAME_PROVNCE_CODE = 340002;

	/**
	 * 配置省份跟归属地服务查询得到的省份代码不一致，请检查
	 */
	public static final int NOT_THE_SAME_PROVNCE_CODE_OF_SERVICE = 340003;
	//-------------ADD BY HECHUAN----------------END


    //-------------ADD BY zkai----------------

    // 呼入信息不存在
    public static final int CALL_IN_RECORD_NULL = 330022;

    // 数据异常
    public static final int DATA_ERROR = 330023;


    // 套餐名称在该省份下已重复,请重新设置.
    public static final int TAOCAN_NAME_REPEATED = 330024;

    // 该省份下已有默认套餐,请重新设置.
    public static final  int TAOCAN_FLAG_REPEATED = 330025;

    // 数据添加失败
    public static final int DATA_ADD_FAILED = 330026;

    // 此套餐不存在.
    public static final  int TAOCAN_IS_NOT_FIND = 330027;

    // 数据修改失败
    public static final int DATA_MODIFY_FAILED = 330028;

    // 套餐时长异常，请重新设置
    public static final int MANAGER_TAOCAN_DURATION_IS_ERROE = 330029;

    // 该套餐正被使用，不允许删除
    public static final int TAOCAN_NOALLOW_DELETE = 330030;

    // 删除失败
    public static final int DATA_DELETE_FAILED = 330031 ;

    // 套餐价格异常，请重新设置
    public static final int TAOCAN_PRICE_IS_ERROR = 330032;

    // 存储空间异常，请重新设置
    public static final int MANAGER_TAOCAN_SPACE_IS_ERROE = 330033;

    // 用户类型不能为空
    public static final int BIZ_USER_TYPE_IS_NULL = 330034;

    // 固话类型不能为空
    public static final int PHONETYPE_NOTNULL = 330035;

    /** 发送计费开通请求异常 */
    public static final int FARE_REQUEST_ERROR = 330036;

    /** 是否短信登录不能为空 */
    public static final int SMS_LOGIN_IS_NULL = 330037;

    /** 个人用户详情不唯一 */
    public static final int BIZ_PERSON_USER_IS_NOT_ONLY = 330038;

    /** 企业用户详情不唯一 */
    public static final int BIZ_ENT_USER_IS_NOT_ONLY = 330039;

    // 套餐名称为空，请重新设置
    public static final int MANAGER_TAOCAN_NAME_IS_NULL = 330040;

    // 套餐类型为空，请重新设置
    public static final int MANAGER_TAOCAN_TYPE_IS_NULL = 330041;

    /** 开通数据中有用户编号为空 */
    public static final int BATCH_USER_NULL = 330042;

    /** 开通数据中有开通时间为空 */
    public static final int BATCH_OPENTIME_NULL = 330043;

    /** 开通数据中有重复数据 */
    public static final int BATCH_DATA_MULTIPLE = 330044;

    /** 业务用户不存在 */
    public static final int BIZ_USER_NOT_EXISTS = 330045;

    /** 子账号列表为空 */
    public static final int BIZ_ENT_ADD_SUB_ACCOUNTS_IS_NULL = 330046;
    
	/**
	 * 北京联通套餐修改接口调用失败
	 */
	public static final  int BJUNICOM_CHANGE_ERROR= 330047;
	
	/**
	 * 数据库内用户信息重复
	 */
	public static final  int USER_REPEAT_ERROR = 330048;

    /**
     * 老的业务编号不能为空
     */
    public static final  int OLD_BIZNO_NOTNULL = 330049;

}
