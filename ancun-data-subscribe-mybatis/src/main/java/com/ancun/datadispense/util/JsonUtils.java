package com.ancun.datadispense.util;

import com.ancun.utils.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

/**
 * 封装内容体头部帮助类
 *
 * @Created on 2015年5月28日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class JsonUtils {

	private static Gson gson = new Gson();

	/**
	 * 构建请求内容体json
	 *
	 * @param action
	 * @param content
	 * @return
	 */
	public static String toJson(String action, Object content) {

		// 请求内容体头部
		ReqCommon common = new ReqCommon();
		// common.setAction("regionarea");
		common.setAction(action);
		common.setReqtime(TimeUtils.getSysTime());

		// 请求内容体构建
		ReqBody<Object> reqBody = new ReqBody<Object>(common, content);

		// 请求体->json
		JsonElement je = gson.toJsonTree(reqBody);
		JsonObject jo = new JsonObject();
		jo.add("request", je);

		return jo.toString();
	}

	/**
	 * 解析json得到结果
	 *
	 * @param json
	 * @param typeOfT
	 * @param <T>
	 * @return
	 */
	public static <T> T fromJson(String json, Type typeOfT) {

		// 如果json为空则直接返回空
		if (json == null) {
			return null;
		}

		// 创建一个JsonParser
		JsonParser parser = new JsonParser();
		JsonElement jsonEl = parser.parse(json);
		JsonObject jsonObject = jsonEl.getAsJsonObject();

		// 解析json生成对象
		return gson.fromJson(jsonObject.getAsJsonObject("response"), typeOfT);
	}
}
