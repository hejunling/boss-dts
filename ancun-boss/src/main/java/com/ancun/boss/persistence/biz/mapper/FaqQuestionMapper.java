package com.ancun.boss.persistence.biz.mapper;

import java.util.List;

import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInput;
import com.ancun.boss.pojo.businessKnowledge.SystemFaqQuestionOutput;
import com.ancun.core.exception.EduException;

import org.apache.ibatis.annotations.Param;
/**
 * 业务知识点问题相关
 */
public interface FaqQuestionMapper {
	
	/**
	 * 详细问题信息
	 */
	SystemFaqQuestionOutput selectEachQuestionByFaqKey(@Param("business") String business, @Param("id") Long id);
	
	/**
	 * 逻辑删除问题
	 */
	void deleteFaqInfo(Long id)throws EduException;
	
	/**
	 * 详细faq信息
	 * @param params
	 * @return
	 */
	List<SystemFaqQuestionOutput> selectByFaqKeys(BusinessKnowledgeGetInput input);

}
