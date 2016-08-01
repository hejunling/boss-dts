package com.ancun.boss.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.mapper.BusinessGetMapper;
import com.ancun.boss.persistence.mapper.SystemFaqMapper;
import com.ancun.boss.persistence.model.SystemFaq;
import com.ancun.boss.persistence.model.SystemFaqExample;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeDetailFaqInput;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeDetailFaqOutput;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInfo;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInput;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetOutput;
import com.ancun.boss.service.system.BusinessKnowledgeGetService;
import com.ancun.core.exception.EduException;


public class BusinessKnowledgeGetServiceImpl implements BusinessKnowledgeGetService{
	@Resource
	private BusinessGetMapper businessGetMapper;
	
	@Resource
    private SystemFaqMapper systemFaqMapper;

	@Override
	public BusinessKnowledgeGetOutput getBusinessKnowledgeGet(BusinessKnowledgeGetInput input) {
		
	
		List<BusinessKnowledgeGetInfo> businesslist = businessGetMapper.getBusinessList(input);
		BusinessKnowledgeGetOutput out = new BusinessKnowledgeGetOutput();
		out.setBusinesslist(businesslist);
		out.setPageinfo(input.getPage());
		return out;
	}
	@Override
	public BusinessKnowledgeDetailFaqOutput queryBusinessInfo(String business,String question) throws EduException {
		//map
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("business", business);
		params.put("question", question);	
		BusinessKnowledgeDetailFaqOutput output = new BusinessKnowledgeDetailFaqOutput();
		output.setDetailinfo(businessGetMapper.selectByPrimaryKey(business));
		output.setFaqlist(businessGetMapper.selectByFaqKey(params));		
		return output;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addOrUpdateBusinessInfo(BusinessKnowledgeDetailFaqInput content) throws EduException{
		
//		int i = 0;
		for (BusinessKnowledgeGetInput input : content.getInputs()){
			// 为空校验
//	        validateSystemInfo(input);
//			if (i == 2) {
//				throw new EduException("事务测试");
//			}
//			i++;
			
			SystemFaq systemFaq = new SystemFaq();
			systemFaq.setId(input.getId());
	        systemFaq.setAnswer(input.getAnswer());
	        systemFaq.setBizintroduce(input.getBizintroduce());
	        systemFaq.setBizservicer(input.getBizservicer());;
	        systemFaq.setDomain(input.getDomain());
	        systemFaq.setIcon(input.getIcon());
	        systemFaq.setQuestion(input.getQuestion());
	        systemFaq.setRemark(input.getRemark());
	        systemFaq.setType(input.getType());
	        systemFaq.setUpdateDate(input.getUpdatetime());
	        systemFaq.setBusiness(input.getBusiness());

	        if (input.getModifyflag().equals(BizRequestConstant.TO_ADD)) {
	        	//检验用户业务知识点是否已存在
	        	if(input.getType().equals(BizRequestConstant.TO_DETAIL)){
	        		validateBusinessName(input.getBusiness(), BizRequestConstant.TO_DETAIL);
	        	}
	            //新增
	        	systemFaq.setId(input.getId());
	            //未删除
	        	systemFaq.setDeleted(BizRequestConstant.DELETE_NO); 
	            systemFaqMapper.insertSelective(systemFaq);
	        } else {
	            //修改
	            systemFaqMapper.updateByPrimaryKeySelective(systemFaq);
	        }
		}
	}
	
	/**
	 * 校验内容
	 */
//	private void validateSystemInfo(BusinessKnowledgeGetInput input){
//		//业务名称不能为空
//		if(StringUtils.isBlank(input.getBusiness())){
//			throw new EduException(MessageConstant.MARKETCHECK_BUSINESS_MUST_BE_NOT_EMPTY);
//		}
//		//网站域名不能为空
//		if(StringUtils.isBlank(input.getDomain())){
//			throw new EduException(MessageConstant.DOMAIN_MUST_BE_NOT_EMPTY);
//		}
//		//上线日期不能为空
//		if(StringUtils.isBlank(input.getUpdatetime())){
//			throw new EduException(MessageConstant.UPDATE_TIME_MUST_BE_NOT_EMPTY);
//		}
//	}
	
	/**
     * 校验业务 是否存在
     *
     * @param userName
     * @return
     * @throws EduException
     */
    private void validateBusinessName(String business,String type) throws EduException {
        SystemFaqExample example = new SystemFaqExample();
        SystemFaqExample.Criteria c = example.createCriteria();

        c.andBusinessEqualTo(business);
        c.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        
        c.andBusinessEqualTo(business).andTypeEqualTo(type);
        List<SystemFaq> businessList = systemFaqMapper.selectByExample(example);
        if (businessList != null && businessList.size() >= 1) {
            throw new EduException(MessageConstant.BUSINESS_HAS_EXISTS);
        }
    }
    
    /**
     * 删除(逻辑)
     */
	@Override
	public void deleteBusinessInfo(String business) throws EduException {
		SystemFaqExample example = new SystemFaqExample();
		SystemFaqExample.Criteria c = example.createCriteria();
	    c.andBusinessEqualTo(business);    

	    SystemFaq systemFaq = new SystemFaq();
	    systemFaq.setDeleted(BizRequestConstant.DELETE_YES);

	        //1.逻辑删除
//	        systemFaqMapper.updateByExampleSelective(systemFaq, example);
	    businessGetMapper.deleteBusinessInfo(business);
		
	}
}
