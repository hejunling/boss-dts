package com.ancun.boss.business.service.bizuserinfoImport;

import com.ancun.boss.business.pojo.bizuserinfoimport.SelectBizUserInfoImportInput;
import com.ancun.boss.business.pojo.bizuserinfoimport.SelectBizUserInfoImportOutput;


public interface IBizUserInfoImportService {

	/**
	 * 用户导入失败列表查询
	 * @param input
	 * @return
     */
	public SelectBizUserInfoImportOutput selectBizUserInfoImportList(SelectBizUserInfoImportInput input) ;

}
