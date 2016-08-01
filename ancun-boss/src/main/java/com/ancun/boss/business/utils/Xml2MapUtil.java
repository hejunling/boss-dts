package com.ancun.boss.business.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ancun.core.exception.EduException;

/**
 * xml辅助类
 *
 * @Created on 2016年3月14日
 * @author lwk
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class Xml2MapUtil {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws DocumentException {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><info><code>100000</code><msg>成功</msg><serversion>2.4.23</serversion></info><content><mainfo><accessid>a6d4b7eed15a7b6ae534509e7d7e172d</accessid><accesskey>MzBmNjczMTIwOWU2NDAyMGUxYzg5ZDUwZTgxZjVjMTg=</accesskey><userid>ancun001</userid><department /><name>test</name><sex>1</sex><email>ddd@dd.com</email><mobile>13425652515</mobile><phone>123456781</phone><address>地址12囧</address><postcode>123456</postcode><fax>123123123</fax><custservphone>057156932929</custservphone><pwdmodifyflag>1</pwdmodifyflag><lloginip>192.168.0.53</lloginip><llogintime>2014-01-10 10:37:14</llogintime></mainfo></content></response>";

		Map map = convert(xmlStr);
		System.out.println(map);
	}

	public static Map<String, Object> convert(String xmlStr)
			throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlStr);
		return Dom2Map(doc);
	}

	@SuppressWarnings("rawtypes")
	private static Map<String, Object> Dom2Map(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			// System.out.println(e.getName());
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}
	/**
	 * 将xml字符串转为map
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 * 
	 * @parm：
	 * @return Map<String,Object>
	 * @exception  
	 * @author csj
	 */
	public static Map<String, Object> convertToMap(String xmlStr)
			throws DocumentException,EduException {
		Document doc = DocumentHelper.parseText(xmlStr);
		return DomToMap(doc);
	}
	/**
	 * 将Document字符串转为map
	 * @param doc
	 * @return
	 * 
	 * @parm：
	 * @return Map<String,Object>
	 * @exception  
	 * @author csj
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, Object> DomToMap(Document doc) throws EduException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		root.getName();
		root.attributes();
		root.getStringValue();
		if(!"result".equals(root.getName())){
			throw new EduException();
		}else{
			List list = root.attributes();
			String codeStr = null;
			for(int i=0;i<list.size();i++){
				Attribute code = (Attribute)root.attributes().get(i);
				if("code".equals(code.getQName().getName())){
					codeStr = code.getValue();
				}
			}
			/**
			 * 如果code为0，表示成功返回。非0表示失败，目前定义的失败code有：
			 * 1：请求消息的参数格式不正确
			 * 2：令牌认证失败
			 * 3：无效的客户端ip地址
			 * 4：流量控制
			 * 5：系统错误
			 * 其它暂未定义
			 */

			if("0".equals(codeStr)){
				
			}else if("1".equals(codeStr)){
				throw new EduException(132001);
			}else if("2".equals(codeStr)){
				throw new EduException(132002);
			}else if("3".equals(codeStr)){
				throw new EduException(132003);
			}else if("4".equals(codeStr)){
				throw new EduException(132004);
			}else if("5".equals(codeStr)){
				throw new EduException(132005);
			}else{
				throw new EduException(132006);
			}
			
		}
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}
}
