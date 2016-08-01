package com.ancun.datadispense.bizConstants;

import com.ancun.datadispense.util.CustomException;
import com.ancun.utils.StringUtil;

public enum UniOpenType {
    /** 外呼彩信 */
    OUTBOUND_MMS(1, UniSignupType.MMS, UniImportFlg.YES, "外呼彩信"),
    /** 外呼短信 */
    OUTBOUND_SMS(2, UniSignupType.SMS, UniImportFlg.YES, "外呼短信"),
    /** 自主彩信 */
    SELF_MMS(3, UniSignupType.MMS, UniImportFlg.NO, "自主彩信"),
    /** 自主短信 */
    SELF_SMS(4, UniSignupType.SMS, UniImportFlg.NO, "自主短信");

    /** 类型 */
    private int type;

    /** 注册来源 */
    private UniSignupType isignupsource;

    /** 后台导入标记 */
    private UniImportFlg importflg;

    private String label;

    UniOpenType(int type, UniSignupType isignupsource, UniImportFlg importflg, String label) {
        this.type = type;
        this.isignupsource = isignupsource;
        this.importflg = importflg;
        this.label = label;
    }

    public int getType() {
        return type;
    }

    public UniSignupType getIsignupsource() {
        return isignupsource;
    }

    public UniImportFlg getImportflg() {
        return importflg;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 根据类型取得开通类型枚举
     *
     * @param type
     * @return
     * @throws Exception 
     */
    public static UniOpenType getOpenType(int type) throws CustomException {
        for (UniOpenType openType : UniOpenType.values()) {
            if (openType.getType() == type) {
                return openType;
            }
        }
        throw new CustomException("系统异常");
    }

    /**
     * 根据注册类型和后台插入flg取得开通类型枚举
     *
     * @param isignupsource
     * @param importflg
     * @return
     * @throws Exception 
     */
    public static UniOpenType getOpenType(String isignupsource, String importflg) throws CustomException {
        for (UniOpenType openType : UniOpenType.values()) {
            if (StringUtil.equals(openType.getIsignupsource().getText(), isignupsource) &&
                    StringUtil.equals(openType.getImportflg().getText(), importflg)) {
                return openType;
            }
        }
        throw new CustomException("系统异常");
    }
    
    /**
     * 取得开通方式
     *
     * @param isignupsource
     * @return
     */
    public static String getIsignupsource(String isignupsource){

        // 如果是Isignupsource是6以下的则全属于短信
        if (StringUtil.nullToIntZero(isignupsource) <= StringUtil.nullToIntZero(UniSignupType.SMS.getText())) {
            return UniSignupType.SMS.getText();
        }

        return isignupsource;
    }
}
