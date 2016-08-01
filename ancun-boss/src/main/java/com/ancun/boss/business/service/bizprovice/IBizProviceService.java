package com.ancun.boss.business.service.bizprovice;

import com.ancun.boss.business.pojo.bizprovice.SelectBizProviceInput;
import com.ancun.boss.business.pojo.bizprovice.SelectBizProviceOutput;


public interface IBizProviceService {

	/**
	 * 通过条件取业务省份关系信息
	 * @param input
	 * @return
     */
	public SelectBizProviceOutput selectBizProvice(SelectBizProviceInput input) ;

}
