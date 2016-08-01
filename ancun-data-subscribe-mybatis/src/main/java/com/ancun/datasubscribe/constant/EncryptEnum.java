package com.ancun.datasubscribe.constant;

/**
 * 加密类型枚举
 *
 * @Created on 2015年12月15日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public enum EncryptEnum {
    NORMAL, DES, AES;

    /**
     * 取得加密枚举
     *
     * @param name 枚举名
     * @return  加密枚举
     */
    public static EncryptEnum getEncrypt(String name){
        EncryptEnum result = null;

        try {
            result = EncryptEnum.valueOf(name.toUpperCase());
        } catch (Exception e) {
            result = EncryptEnum.NORMAL;
        }

        return result;
    }
}
