/**
 * RichUtils.java
 *
 * @Title:
 * @Description:
 * @Copyright:���ݰ�������Ƽ����޹�˾ Copyright (c) 2011
 * @Company:���ݰ�������Ƽ����޹�˾
 * @Created on Aug 18, 2011 2:44:56 PM
 * @author lijianhang
 * @version 1.0
 */

package com.ancun.boss.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * xiongqingliang 2014/11/6 从accore迁移过来。
 */
public class RichUtils {
	
	/** 日志 */
    private static Logger logger = LoggerFactory.getLogger(YulucnUtil.class);

	public static String postURLReturn(String urlStr, String paras, int connecttimeout, int readtimeout, String charset, String split) 
			throws Exception {

		HttpURLConnection uRLConnection = (HttpURLConnection)new URL(urlStr).openConnection();
		if(connecttimeout > 0) {
			uRLConnection.setConnectTimeout(connecttimeout);
		}
		if(readtimeout > 0) {
			uRLConnection.setReadTimeout(readtimeout);
		}
		uRLConnection.setDoOutput(true);

		OutputStreamWriter writer = null;
		if(StringUtils.isEmpty(charset)) {
			writer = new OutputStreamWriter(uRLConnection.getOutputStream());
		} else {
			writer = new OutputStreamWriter(uRLConnection.getOutputStream(), charset);
		}

		writer.write(paras);
		writer.flush();

		BufferedReader reader = null;
		if(StringUtils.isEmpty(charset)) {
			reader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream()));
		} else {
			reader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream(), charset));
		}

		StringBuffer stringBuffer = new StringBuffer();
		String temp = "";
		while ((temp = reader.readLine()) != null) {
			stringBuffer.append(temp).append(split);
		}
        writer.close();
        reader.close();

        return StringUtils.nullToStrTrim(stringBuffer.toString());
	}

}
