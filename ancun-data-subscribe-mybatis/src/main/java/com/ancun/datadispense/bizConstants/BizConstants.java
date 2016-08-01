package com.ancun.datadispense.bizConstants;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/3/8
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BizConstants {

    // 号码类别(0:固话;1:手机)
    public static final String USER_PHONETYPE_FIXED_TELEPHONE = "0";
    public static final String USER_PHONETYPE_CELL_PHONE = "1";

    // 用户状态(1:正常 2:冻结3:注销4:体验)
    public static final String USERSTATUS_NORMAL = "1";
    public static final String USERSTATUS_FROZEN = "2";
    public static final String USERSTATUS_CANCEL = "3";
    public static final String USERSTATUS_TRY = "4";

    // 用户类型(1:个人;2:企业)
    public static final String USER_TYPE_PERSONAL = "1";
    public static final String USER_TYPE_ENTERPRISES = "2";

    // 初始密码设置标志-->(0:否; 1:是)
    public static final String USER_PASSWORD_TIPS_NO = "0";
    public static final String USER_PASSWORD_TIPS_OK = "1";

    // 帐号类型(1:主帐号;2:子帐号,3:个人账号)
    public static final String ACCOUNTTYPE_MAIN = "1";
    public static final String ACCOUNTTYPE_SUB = "2";
    public static final String ACCOUNTTYPE_PERSONAL = "3";

    // 套餐类型(1：主叫；2：双向；3：接入号)
    public static final String TC_TYPE_1 = "1";
    public static final String TC_TYPE_2 = "2";
    public static final String TC_TYPE_3 = "3";

    // 标记(1:是；0：否)
    public static final String MARK_YES = "1";
    public static final String MARK_NO = "0";

    //提取码有效期（小时）:默认72小时
    public static final Long ACCESSCODE_ACTIVE = 72l;
    
    //后台 开通,外呼开通
    public static final int BLACKGROUD_OPEN = 1;

    // 退费(1:退房；2：不退费)
    public static final String REFUND_YES = "1";
    public static final String REFUND_NO = "2";
    
	/** 退订并退费 */
	public static final String CANCEL_BIZ_REFUNDS = "1";
	/** 退订不退费 */
	public static final String CANCEL_BIZ_NOT_REFUNDS = "0";

	//开通状态-->(0:否; 1:是)
	public static final String USER_OPENFLG_NO= "0";
	public static final String USER_OPENFLG_OK = "1";

    //编码方式
    public static final String ENCODING_GBK = "GBK";
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ENCODING_ISO88591 = "ISO-8859-1";
    public static final String CHARSETNAME_DEFAULT = ENCODING_UTF8;
    
    // 业务类型
    public static final String DEFAULT_BUSINESSTYPE = "8";

    /** ------------------------------数据消费相关配置---------------------------------*/

    /** 业务CODE */
    /** CP电信 */
    public static final String BIZ_CODE_CPDX = "DX-CP";

    /** 安存语录及其它 */
    public static final String BIZ_CODE_ACYLJQT = "ACYL-ACYLJQT";
    /** 运营商业务 */
    public static final String BIZ_CODE_YYSYW = "YYSYW-YYSYW";
    /** 铁证通 */
    public static final String BIZ_CODE_TZT = "ACYL-TZT";
    /** 言而有信 */
    public static final String BIZ_CODE_YEYX = "ACYL-YEYX";
    /** 电信 */
    public static final String BIZ_CODE_DX = "YYSYW-DX";
    /** 联通 */
    public static final String BIZ_CODE_LT = "YYSYW-LT";

    /** 数据消费数据库表信息 */
    /** 业务用户表 */
    public static final String T_BIZ_USER_INFO = "biz_user_info";
    /** 个人用户表 */
    public static final String T_BIZ_PERSON_INFO = "biz_person_info";
    /** 企业信息表 */
    public static final String T_BIZ_ENT_INFO = "biz_ent_info";
    /** 套餐表 */
    public static final String T_BIZ_COMBO_INFO = "biz_combo_info";

    /** 汇工作省份 */
    public static final String V100000 = "V100000";

    /** 分省电信相关业务CONE */
    public static final String DX_PROVINCE_BIZNO = "DX-YUNNAN;DX-GUANGXI;DX-CHENGDU;DX-QINGHAI;DX-HEBEI;DX-JILIN;DX-FUJIANG;DX-HAINAN;DX-GUIZHOU";
    
    /** 上海音证宝*/
    public static final String SH_PROVINCE_BIZNO = "DX-SHANGHAI";
    
    /** CP联通*/
    public static final String CP_UNICOM_BIZNO = "LT-CP";

    /** 联通*/
    public static final String LT_PROVINCE_BIZNO = "LT-BEIJING;LT-TIANJIN;LT-HEBEI;LT-JILIN;LT-JIANGSU";
    
    /** 默认密码*/
    public static final String DEFAULT_PASSWORD = "123456";
    
    /** CPTelecom套餐名称*/
    public static final String CP_TELECOM_TAOCAN_NAME = "包月套餐";
    
    /** 操作（是否个人转企业。0：否；1：是）*/
    public static final String BIZ_OPERATION_TRUE = "1";
    
    public static final String IMPORTFLAG_NO = "0";
    
}
