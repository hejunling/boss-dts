package com.ancun.boss.service.system;

import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInput;
import com.ancun.boss.pojo.businessKnowledge.FaqDetailOutput;
import com.ancun.boss.pojo.businessKnowledge.SystemFaqQuestionOutput;
import com.ancun.core.exception.EduException;

/**
 * 知识点FAQ问题新增,修改,删除业务
 * @author 静好
 *
 */
public interface SystemFaqService {
	
	/**
	 * 详细
	 * @param id
	 * @return
	 * @throws EduException
	 */
	 public FaqDetailOutput queryFaqQuestionInfo(BusinessKnowledgeGetInput input) throws EduException;
	
	/**
	  * 新增修改
	  * @param content
	  * @throws EduException
	  */
	 public void addOrUpdateFaqInfo(BusinessKnowledgeGetInput input)throws EduException;

	 /**
	  * 删除
	  * @param business
	  * @throws EduException
	  */
	 public void deleteFaqInfo(Long id)throws EduException;
	 
	 /**
	 * 单个问题详细
	 * @param id
	 * @return
	 * @throws EduException
	 */
	 public SystemFaqQuestionOutput queryFaqInfo(String business,Long id) throws EduException;

}
