package com.ancun.boss.persistence.biz.mapper;

import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInfo;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInput;
import com.ancun.core.exception.EduException;

import java.util.List;
import java.util.Map;
/**
 * 业务知识点相关
 */
public interface BusinessGetMapper {
	/**
	 * 列表查询
	 * @param input
	 * @return
	 */
	List<BusinessKnowledgeGetInfo> getBusinessList(BusinessKnowledgeGetInput input);
	/**
	 * 删除
	 * @param business
	 * @throws EduException
	 */
	void deleteBusinessInfo(String business)throws EduException;
	
	/**
	 * 详细基础信息
	 * @param business
	 * @return
	 */
	BusinessKnowledgeGetInfo selectByPrimaryKey(String business);
	/**
	 * 详细faq信息
	 * @param params
	 * @return
	 */
	List<BusinessKnowledgeGetInfo> selectByFaqKey(Map<String, Object> params);

}
