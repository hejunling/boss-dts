package com.ancun.boss.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUnicodeUtil {
	public static boolean isEnglish(String charaString) {         
		return charaString.matches("^[a-zA-Z]*");     
	}
	
	public static boolean isContainUpperCase(String charaString){
		for(Character c:charaString.toCharArray()){
			if(Character.isUpperCase(c))
			return true;
		}
		return false;
	}
	
	public static boolean isContainLowCase(String charaString){
		for(Character c:charaString.toCharArray()){
			if(Character.isLowerCase(c))
			return true;
		}
		return false;
	}
	
	public static boolean isContainDigit(String charaString){
		for(Character c:charaString.toCharArray()){
			if(Character.isDigit(c))
			return true;
		}
		return false;
	}
	
	public static boolean isContainChinese(String str) {// 检测是否包含中文        
		String regEx = "[\\u4E00-\\u9FA5]+";         
		Pattern p = Pattern.compile(regEx);         
		Matcher m = p.matcher(str);        
		if (m.find()) {            
			return true;         
		} else {            
			return false;        
		}     
	}
}
