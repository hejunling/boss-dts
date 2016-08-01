package com.ancun.boss.market;

import java.io.File;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class Test {
	private static final String SEPARATOR = File.separator;
	public static void main(String[] args) {
		 String join = Joiner.on(SEPARATOR).skipNulls() .join(Splitter.on(SEPARATOR).trimResults().omitEmptyStrings().split("/data/file/boss.accore/"));
		 System.out.println(join);
		 
		 String join2 = Joiner.on(SEPARATOR).skipNulls().join(join, "hello.wav");
		 System.out.println(join2);
	}
}
