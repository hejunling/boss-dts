package com.ancun.datadispense.util;

public class CommonUtil {
	/**
     * 检验是是否存在字符串中，分号分隔
     * @param config
     * @param checkData
     * @return
     */
    public static boolean checkConfig(String config, String checkData){

        return checkData.indexOf(";") == -1 && (";" + config + ";").indexOf(";" + checkData + ";") >= 0;
    }
}
