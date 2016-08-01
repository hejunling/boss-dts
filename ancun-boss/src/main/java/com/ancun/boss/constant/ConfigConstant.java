package com.ancun.boss.constant;

/**
 * 配置
 *
 * @Created on 2015年10月10日
 * @author xieyushi
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class ConfigConstant {

    /** 阿里云buckt */
    public static final String ALIYUNOSS_BUCKET_CLIENT = "ALIYUNOSS_BUCKET_CLIENT";

    /** 阿里云accessid */
    public static final String ALIYUNOSS_ACCESSID = "ALIYUNOSS_ACCESSID";

    /** 阿里云accesskey */
    public static final String ALIYUNOSS_ACCESSKEY = "ALIYUNOSS_ACCESSKEY";

    /** 临时目录 */
    public static final String TEMP_FILE_PATH = "TEMP_FILE_PATH";

    /** 上传组件url */
    public static final String UPLOAD_URI = "UPLOAD_URI";

    /** 通知方式 */
    public static final String NOTICE_TYPE = "NOTICE_TYPE";

    /** 工单完成通知地址 */
    public static final String WORK_ORDER_COMPLETE_NOTICE_ADDRESS = "WORK_ORDER_COMPLETE_NOTICE_ADDRESS";

    /** 号码池过滤任务 */
    public static final String TASK_PHONE_FILTER = "TASK_PHONE_FILTER";

    /** 号码格式校验 */
    public static final String PHONE_CHECK = "^(((1([3,4,5,7,8][0-9]))\\d{8})|(0\\d{2}-?\\d{8})|(0\\d{3}-?\\d{7,8}))$";
}
