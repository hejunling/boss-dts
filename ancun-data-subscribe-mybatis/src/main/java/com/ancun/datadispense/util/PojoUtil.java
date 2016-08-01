package com.ancun.datadispense.util;

import com.aliyun.drc.client.message.DataMessage;
import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.common.persistence.model.master.BizEntInfo;
import com.ancun.common.persistence.model.master.BizPersonInfo;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datasubscribe.eventbus.event.BaseEvent;
import com.ancun.datasubscribe.eventbus.event.TableChangeDetail;
import com.ancun.datasubscribe.util.DTSFieldUtil;
import com.ancun.utils.DateUtil;
import com.ancun.utils.StringUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * POJO帮助类
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/3/15
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class PojoUtil {

    /**
     * 业务用户转化
     *
     * @param event
     * @return
     */
    public static BizUserInfo bizUserInfoTransform(BaseEvent event) throws CustomException {

        List<TableChangeDetail> tableChangeDetailList = event.getTableChangeList();

        BizUserInfo bizUserInfo = new BizUserInfo();

        List<DataMessage.Record.Field> fields = null;
        if (null == tableChangeDetailList || tableChangeDetailList.size() <= 0) {
            throw new CustomException("记录不存在");
        }

        for (TableChangeDetail detail : tableChangeDetailList) {
            if (null == detail.getConsume()) {
                continue;
            }
            //biz_user_info表 数据获取
            if (detail.getConsume().getTableName().equals(BizConstants.T_BIZ_USER_INFO)) {
                fields = detail.getFields();
                if (null == fields) {
                    continue;
                }

                bizUserInfo.setId(getLong(fields, "ID"));
                bizUserInfo.setUserNo(getString(fields, "USER_NO"));
                bizUserInfo.setPasswd(getString(fields, "PASSWD"));
                bizUserInfo.setUserType(getString(fields, "USER_TYPE"));
                bizUserInfo.setEntNo(getString(fields, "ENT_NO"));
                bizUserInfo.setAccountType(getString(fields, "ACCOUNT_TYPE"));
                bizUserInfo.setBizNo(getString(fields, "BIZ_NO"));
                bizUserInfo.setTcid(getLong(fields, "TCID"));
                bizUserInfo.setOrgNo(getString(fields, "ORG_NO"));
                bizUserInfo.setStatus(getString(fields, "STATUS"));
                bizUserInfo.setRpcode(getString(fields, "RPCODE"));
                bizUserInfo.setCityCode(getString(fields, "CITY_CODE"));
                bizUserInfo.setPhone(getString(fields, "PHONE"));
                bizUserInfo.setRectip(getString(fields, "RECTIP"));
                bizUserInfo.setIntime(getDate(fields, "INTIME"));
                bizUserInfo.setInsource(getString(fields, "INSOURCE"));
                bizUserInfo.setOffsource(getString(fields, "OFFSOURCE"));
                bizUserInfo.setOfftime(getDate(fields, "OFFTIME"));
                bizUserInfo.setCallerVoice(getString(fields, "CALLER_VOICE"));
                bizUserInfo.setCalledVoice(getString(fields, "CALLED_VOICE"));
                bizUserInfo.setCallerRecord(getString(fields, "CALLER_RECORD"));
                bizUserInfo.setCalledRecord(getString(fields, "CALLED_RECORD"));
                bizUserInfo.setPhonetype(getString(fields, "PHONETYPE"));
                bizUserInfo.setPhonetype(getString(fields, "PHONETYPE"));
                bizUserInfo.setDefaultpwdflag(getString(fields, "DEFAULTPWDFLAG"));
                bizUserInfo.setIsrefund(getString(fields, "ISREFUND"));
                bizUserInfo.setRefundamount(getDouble(fields, "REFUNDAMOUNT"));
                bizUserInfo.setRefundremark(getString(fields, "REFUNDREMARK"));
                bizUserInfo.setSmsLogin(getString(fields, "SMS_LOGIN"));
                bizUserInfo.setEmail(getString(fields, "EMAIL"));
                bizUserInfo.setBusinesstype(getString(fields, "BUSINESSTYPE"));
                bizUserInfo.setCallerflag(getString(fields, "CALLERFLAG"));
                bizUserInfo.setCalledflag(getString(fields, "CALLEDFLAG"));
                bizUserInfo.setRemark(getString(fields, "REMARK"));
                bizUserInfo.setFax(getString(fields, "FAX"));
                bizUserInfo.setOperation(getString(fields, "OPERATION"));
            }
            //biz_person_info表 数据获取
            if (detail.getConsume().getTableName().equals(BizConstants.T_BIZ_PERSON_INFO)) {
                fields = detail.getFields();
                if (null == fields) {
                    continue;
                }
                bizUserInfo.setSex(getString(fields, "SEX"));
                bizUserInfo.setIdentify(getString(fields, "IDENTIFY"));
            }
            //biz_ent_info表 数据获取
            if (detail.getConsume().getTableName().equals(BizConstants.T_BIZ_ENT_INFO)) {
                fields = detail.getFields();
                if (null == fields) {
                    continue;
                }
                bizUserInfo.setRegNo(getString(fields, "REG_NO"));
                bizUserInfo.setName(getString(fields, "NAME"));
                bizUserInfo.setAddress(getString(fields, "ADDRESS"));
            }
        }

        return bizUserInfo;
    }

    /**
     * 企业用户信息转换
     *
     * @param event
     * @return
     * @throws Exception
     */
    public static BizEntInfo bizEntInfoTransform(BaseEvent event) throws CustomException {
        BizEntInfo bizEntInfo = new BizEntInfo();

        List<TableChangeDetail> tableChangeDetailList = event.getTableChangeList();

        checkOneCount(tableChangeDetailList);

        List<DataMessage.Record.Field> fields = tableChangeDetailList.get(0).getFields();
        bizEntInfo.setEntNo(getString(fields, "ENT_NO"));
        bizEntInfo.setRegNo(getString(fields, "REG_NO"));
        bizEntInfo.setName(getString(fields, "NAME"));
        bizEntInfo.setAddress(getString(fields, "ADDRESS"));
        bizEntInfo.setBizNo(getString(fields, "BIZ_NO"));

        return bizEntInfo;
    }


    /**
     * 个人用户信息转换
     *
     * @param event
     * @return
     * @throws Exception
     */
    public static BizPersonInfo bizPersonInfoTransform(BaseEvent event) throws CustomException {
        BizPersonInfo bizPersonInfo = new BizPersonInfo();

        List<TableChangeDetail> tableChangeDetailList = event.getTableChangeList();

        checkOneCount(tableChangeDetailList);

        List<DataMessage.Record.Field> fields = tableChangeDetailList.get(0).getFields();
        bizPersonInfo.setUserNo(getString(fields, "USER_NO"));
        bizPersonInfo.setSex(getString(fields, "SEX"));
        bizPersonInfo.setIdentify(getString(fields, "IDENTIFY"));
        bizPersonInfo.setBizNo(getString(fields, "BIZ_NO"));

        return bizPersonInfo;
    }


    /**
     * 套餐表信息转换
     *
     * @param event
     * @return
     * @throws Exception
     */
    public static BizComboInfo bizComboInfoTransform(BaseEvent event) throws CustomException {
        BizComboInfo bizComboInfo = new BizComboInfo();

        List<TableChangeDetail> tableChangeDetailList = event.getTableChangeList();

        checkOneCount(tableChangeDetailList);

        List<DataMessage.Record.Field> fields = tableChangeDetailList.get(0).getFields();
        bizComboInfo.setId(getLong(fields, "ID"));
        bizComboInfo.setTcid(getLong(fields, "TCID"));
        bizComboInfo.setName(getString(fields, "NAME"));
        bizComboInfo.setPrice(getBigDecimal(fields, "PRICE"));
        bizComboInfo.setType(getString(fields, "TYPE"));
        bizComboInfo.setCreateTime(getDate(fields, "CREATE_TIME"));
        bizComboInfo.setFinishTime(getDate(fields, "FINISH_TIME"));
        bizComboInfo.setSpace(getLong(fields, "SPACE"));
        bizComboInfo.setBizNo(getString(fields, "BIZ_NO"));
        bizComboInfo.setStatus(getString(fields, "STATUS"));
        bizComboInfo.setRemark(getString(fields, "REMARK"));
        bizComboInfo.setDuration(getString(fields, "DURATION"));
        bizComboInfo.setCategory(getString(fields, "CATEGORY"));
        bizComboInfo.setDefaultTaocan(getString(fields, "DEFAULT_TAOCAN"));
        bizComboInfo.setRpcode(getString(fields, "RPCODE"));

        return bizComboInfo;
    }

    /**
     * 检查记录条数是否等于1
     *
     * @param tableChangeDetailList
     * @throws Exception
     */
    private static void checkOneCount(List<TableChangeDetail> tableChangeDetailList) throws CustomException {
        if (null == tableChangeDetailList || tableChangeDetailList.size() <= 0) {
            throw new CustomException("记录不存在");
        }
        if (tableChangeDetailList.size() != 1) {
            throw new CustomException("记录条数不等1");
        }
    }

    /**
     * 获取 String类型属性
     *
     * @param fields
     * @param column
     * @return
     */
    private static String getString(List<DataMessage.Record.Field> fields, String column) {
        return DTSFieldUtil.getFieldValue(fields, column, "");
    }

    /**
     * 获取 Long 类型属性
     *
     * @param fields
     * @param column
     * @return
     */
    private static Long getLong(List<DataMessage.Record.Field> fields, String column) {
        return StringUtil.nullToLongZero(DTSFieldUtil.getFieldValue(fields, column, ""));
    }

    /**
     * 获取 Double 类型属性
     *
     * @param fields
     * @param column
     * @return
     */
    private static Double getDouble(List<DataMessage.Record.Field> fields, String column) {
        return StringUtil.nullToDoubleZero(DTSFieldUtil.getFieldValue(fields, column, ""));
    }

    /**
     * 获取Date 类型属性
     *
     * @param fields
     * @param column
     * @return
     */
    private static Date getDate(List<DataMessage.Record.Field> fields, String column) {
        String date = DTSFieldUtil.getFieldValue(fields, column, "");
        if (StringUtil.isNotEmpty(date)) {
            return DateUtil.strToDate(date, DateUtil.DEFAULT_FORMAT_PAGE);
        }
        return null;
    }

    /**
     * 获取 BigDecimal 类型属性
     *
     * @param fields
     * @param column
     * @return
     */
    private static BigDecimal getBigDecimal(List<DataMessage.Record.Field> fields, String column) {
        String bigStr = DTSFieldUtil.getFieldValue(fields, column, "");
        if (StringUtil.isNotEmpty(bigStr)) {
            return new BigDecimal(bigStr);
        }
        return null;
    }
}
