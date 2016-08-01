package com.ancun.boss.service.system;

import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeDetailFaqInput;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeDetailFaqOutput;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInput;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetOutput;
import com.ancun.core.exception.EduException;



public interface BusinessKnowledgeGetService {
	/**
	 * 列表
	 */
	 public BusinessKnowledgeGetOutput getBusinessKnowledgeGet(BusinessKnowledgeGetInput input)throws EduException;
	/**
	 * 详细
	 * @param id
	 * @return
	 * @throws EduException
	 */
	 public BusinessKnowledgeDetailFaqOutput queryBusinessInfo(String business,String type) throws EduException;
	 /**
	  * 新增修改
	  * @param content
	  * @throws EduException
	  */
	 public void addOrUpdateBusinessInfo(BusinessKnowledgeDetailFaqInput content)throws EduException;

	 /**
	  * 删除
	  * @param business
	  * @throws EduException
	  */
	 public void deleteBusinessInfo(String business)throws EduException;
}
