package com.ancun.boss.util;

import java.util.List;

/**除了String、Date等容器的处理。主要是Map和List*/
public class ContainerUtil {
	
	public static boolean isNotEmptyList(List list){
		if(list!=null&&list.size()!=0)
		{
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isSingle(List list){
		if(list!=null&&list.size()==1)
		{
			return true;
		}else{
			return false;
		}
	}

}
