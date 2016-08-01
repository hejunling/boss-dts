package com.ancun.datasubscribe.constant;

/**
 * 共通常量配置
 *
 * @Created on 2016年01月04日
 * @author 静好
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface Constant {

    /** 是否使用公网IP（0不使用，1使用） */
    int NO_USE_PUBLIC_IP = 0;
    int USE_PUBLIC_IP = 1;

    /** 数据订阅消费者客户端状态（0启用，1停用） */
    int ENABLE = 0;
    int DISABLE = 1;

    // 消费状态
    /** 消费中 */
    int ACK_AS_CONSUMEDING = 0;
    /** 消费成功 */
    int ACK_AS_CONSUMED_SUCCESS = 1;
    /** 消费失败 */
    int ACK_AS_CONSUMED_FAILED = 2;

    // 用户类型(1:个人;2:企业)
    String USER_TYPE_PERSONAL = "1";
    String USER_TYPE_ENTERPRISES = "2";

    /** 业务用户表 */
    String T_BIZ_USER_INFO = "biz_user_info";
    /** 个人用户表 */
    String T_BIZ_PERSON_INFO = "BIZ_PERSON_INFO";
    /** 企业信息表 */
    String T_BIZ_ENT_INFO = "BIZ_ENT_INFO";
    /** 数据库中唯一标识字段名 */
    String UNIQUENO_TITLE = "UNIQUENO";
    /** 数据库中业务编号字段名 */
    String BIZNO_TITLE = "BIZ_NO";
    /** 数据库中用户类型字段名 */
    String USER_TYPE_TITLE = "USER_TYPE";
    /** 数据库中全包字段名 */
    String FULL_PACKAGE_TITLE = "FULLPACKAGE";
    /** 默认表名(demo) */
    String TABLE_NAME_DEFAULT = "default";
    /** 默认业务名(demo) */
    String BIZNO_DEFAULT = "default";
    /** 默认用户类型(demo) */
    String USER_TYPE_DEFAULT = "default";
    /** 默认全包 */
    String FULL_PACKAGE_DEFAULT = "TRUE";

}
