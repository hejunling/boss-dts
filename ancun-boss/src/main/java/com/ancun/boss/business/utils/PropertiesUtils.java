package com.ancun.boss.business.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 配置文件读取
 *
 * @Created on 2016年3月21日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class PropertiesUtils {

	protected  static final String BUNDLE_NAME = "properties.";

    private static final String BUNDLE_NAME_CONFIG = BUNDLE_NAME + "config";
    
	private static ResourceBundle RESOURCE_BUNDLE_CONFIG = ResourceBundle.getBundle(BUNDLE_NAME_CONFIG);

	public static void clearCache() {
		ResourceBundle.clearCache();
	}

	public static String getString(ResourceBundle resourceBundle, String key) {

		if(resourceBundle == null) {
			return null;  //未知
		}

		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return null;  //未知
		}
	}
	
	public static String getConfig(String key) {
		return getString(RESOURCE_BUNDLE_CONFIG, key);
	}

	public static Integer getIntegerConfig(String key) {
		return Integer.parseInt(getString(RESOURCE_BUNDLE_CONFIG, key));
	}
	
	public static Long getLongConfig(String key) {
		return Long.parseLong(getString(RESOURCE_BUNDLE_CONFIG, key));
	}
	
	public static Boolean getBooleanConfig(String key){
		return Boolean.parseBoolean(getString(RESOURCE_BUNDLE_CONFIG, key));
	}
	
}
