package com.ancun.sample.activemq;

import java.util.concurrent.ConcurrentMap;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

public class HashMapTest {
	public static void main(String[] args) {
		ConcurrentMap<String, String> newConcurrentMap = Maps.<String,String>newConcurrentMap();
		newConcurrentMap.putIfAbsent("key", "hello");
		newConcurrentMap.putIfAbsent("key", "hellox");
		newConcurrentMap.putIfAbsent("key", "helloxx");
		newConcurrentMap.put("key", "hello");
		newConcurrentMap.put("key", "hellox");
		newConcurrentMap.put("key", "helloxx");
		
		
		System.out.println(newConcurrentMap);
		
	}
}
