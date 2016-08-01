package com.ancun.boss;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

public class Hello {
	public static void main(String[] args) {
		String ipFilter = "";
		String ip = "192.168.0.173";
		if (("," + ipFilter + ",").indexOf("," + ip + ",") == -1) {
			System.out.println("-----------");
		}
		
		final List<String> telList = Lists.newArrayList("1","2","3","4");
		final List<String> phList = Lists.newArrayList("1","3","5","7");
		Collection<String> filter = Collections2.filter(telList, new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return !phList.contains(input);
			}
		});
		System.out.println(filter);
		
		Collection<String> filter1 = Collections2.filter(phList, new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return !telList.contains(input);
			}
		});
		System.out.println(filter1);
		
		Iterator<String> concat = Iterators.concat(filter.iterator(),filter1.iterator());
		while(concat.hasNext()) {
			String next = concat.next();
			System.out.println(next);
		}
	}
		
}
