package com.ancun.boss.service.system.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.persistence.biz.mapper.BusinessGetMapper;
import com.ancun.boss.persistence.biz.mapper.FaqQuestionMapper;
import com.ancun.boss.persistence.mapper.SystemFaqMapper;
import com.ancun.boss.persistence.model.SystemFaq;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInput;
import com.ancun.boss.pojo.businessKnowledge.FaqDetailOutput;
import com.ancun.boss.pojo.businessKnowledge.FaqGetInfo;
import com.ancun.boss.pojo.businessKnowledge.SystemFaqQuestionOutput;
import com.ancun.boss.service.system.SystemFaqService;
import com.ancun.core.exception.EduException;

/**
 * 知识点FAQ问题新增,修改,删除
 * @author 静好
 *
 */
@Service
public class SystemFaqServiceImpl implements SystemFaqService{
	
	@Resource
	private BusinessGetMapper businessGetMapper;
	
	@Resource
    private SystemFaqMapper systemFaqMapper;
	
	@Resource
	private FaqQuestionMapper faqQuestionMapper;
	
	@Override
	public FaqDetailOutput queryFaqQuestionInfo(BusinessKnowledgeGetInput input) throws EduException {
		
		FaqDetailOutput output = new FaqDetailOutput();
		output.setDetailinfo(businessGetMapper.selectByPrimaryKey(input.getBusiness()));
		FaqGetInfo info = new FaqGetInfo();
		info.setFaqlist(faqQuestionMapper.selectByFaqKeys(input));
		info.setPageinfo(input.getPage());
		output.setFaqinfo(info);
		return output;
	}
	
	@Override
	public void addOrUpdateFaqInfo(BusinessKnowledgeGetInput input)
			throws EduException {
		
			SystemFaq systemFaq = new SystemFaq();
			systemFaq.setId(input.getId());
	        systemFaq.setAnswer(input.getAnswer());
	        systemFaq.setQuestion(input.getQuestion());

	        if (input.getModifyflag().equals(BizRequestConstant.TO_ADD)) {
	            //新增
	        	systemFaq.setId(input.getId());
	            //未删除
	        	systemFaq.setDeleted(BizRequestConstant.DELETE_NO); 
	        	systemFaq.setBusiness(input.getBusiness());
	        	systemFaq.setType(BizRequestConstant.TO_FAQ);
	            systemFaqMapper.insertSelective(systemFaq);
	        } else {
	            //修改
	            systemFaqMapper.updateByPrimaryKeySelective(systemFaq);
	        }
	}
	
	@Override
	public void deleteFaqInfo(Long id) throws EduException {

	    SystemFaq systemFaq = new SystemFaq();
	    systemFaq.setDeleted(BizRequestConstant.DELETE_YES);

	    //逻辑删除
	    faqQuestionMapper.deleteFaqInfo(id);
		
	}

	@Override
	public SystemFaqQuestionOutput queryFaqInfo(String business,Long id) throws EduException {
		
		SystemFaqQuestionOutput output = faqQuestionMapper.selectEachQuestionByFaqKey(business, id);
		
		return output;
	}
}
