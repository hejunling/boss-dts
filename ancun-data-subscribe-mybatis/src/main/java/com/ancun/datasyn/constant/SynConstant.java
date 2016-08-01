package com.ancun.datasyn.constant;

/**
 * 数据同步相关 常量
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/5/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class SynConstant {

    /**
     * 同步模块 用户
     */
    public static final String SYN_USER = "user";

    /**
     * 同步模块 录音
     */
    public static final String SYN_VOICE = "voice";

    /**
     * 同步模块 套餐
     */
    public static final String SYN_COMBO = "combo";

    /**
     * 同步模块 生命周期
     */
    public static final String SYN_LIFECIRLE = "lifecircle";

    // 用户类型(1:个人;2:企业)
    public static final String USER_TYPE_PERSONAL = "1";
    public static final String USER_TYPE_ENTERPRISES = "2";

    // 帐号类型(1:主帐号;2:子帐号,3:个人账号)
    public static final String ACCOUNTTYPE_MAIN = "1";
    public static final String ACCOUNTTYPE_SUB = "2";
    public static final String ACCOUNTTYPE_PERSONAL = "3";

    // 开通/退订(1:开通,2:退订)
    public static final String USER_STATE_OPEN = "1";
    public static final String USER_STATE_CANCEL = "2";


    //业务编号
    // CP联通
    public static final String BIZ_LT_CP = "LT-CP";

    public static final String BIZ_DX_CP = "DX-CP";

    // 上海音证宝
    public static final String BIZ_DX_SHANGHAI = "DX-SHANGHAI";

    // 省份编号
    public static final String ALL_PROVICE = "000000";

    // 上海rpcode
    public static final String SHANGHAI_RPCODE = "310000";

    /**
     * updatetime 缺省时间（insert时 updatetime ='0000-00-00 00:00:00'）
     */
    public static final String DEFAULT_DATE = "0000-00-00 00:00:00";

}
