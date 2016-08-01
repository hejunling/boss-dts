package com.ancun.boss.business.utils.fptcsrvc;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.http.client.HttpUtils;
import com.ancun.http.enums.ContentType;
import com.ancun.test.api.BaseAPi;
import com.ancun.test.bean.ReqCommon;
import com.ancun.test.bean.ReqObject;
import com.ancun.utils.HmacSha1Utils;
import com.ancun.utils.MD5Utils;
import com.ancun.utils.XmlUtils;
import com.ancun.core.utils.JsonUtils;

public class BJFtpUtil{
	
	private static final Logger log = LoggerFactory.getLogger(BJFtpUtil.class);
	
	public static <T> String doService(String action, ContentType type, T content, String url, String accessKey){
		
		Map<String, String> header = new HashMap<String, String>();
		String requestUrl = url + "/" +action + "." + type.name().toLowerCase();

		ReqObject<T> obj = new ReqObject<T>();
		obj.setContent(content);
		ReqCommon common  = new ReqCommon();
		common.setAction(action);
		obj.setCommon(common);

		String requestJson = "";
		if(type == ContentType.JSON){
			header.put("format", "json");
			requestJson = JsonUtils.convertToJson(obj);
			requestJson = "{\"request\":"+requestJson+"}";
		}else{
			header.put("format", "xml");
			requestJson = XmlUtils.convertToXml(obj, content.getClass());
		}
		log.info(requestJson);
		try{
			String sign = HmacSha1Utils.signToString(MD5Utils.md5(requestJson).toLowerCase(), accessKey, "UTF-8");
			header.put("sign", URLEncoder.encode(sign, "UTF-8"));
			header.put("reqlength", String.valueOf(requestJson.length()));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		String response = HttpUtils.doApiPost(requestUrl, requestJson, header);
		log.info(response);
		return response;
	}

}
