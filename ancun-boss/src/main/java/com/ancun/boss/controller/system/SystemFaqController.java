package com.ancun.boss.controller.system;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInput;
import com.ancun.boss.pojo.businessKnowledge.FaqDetailOutput;
import com.ancun.boss.pojo.businessKnowledge.SystemFaqQuestionOutput;
import com.ancun.boss.service.system.SystemFaqService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;

/**
 * 知识点FAQ问题新增,修改,删除
 * @author chenyanshuang
 * @Created on 2016-6-2
 *
 */
@RestController
public class SystemFaqController extends BaseController{

	@Resource
	private SystemFaqService systemFaqService;
	
	/**
     * 业务知识点单个详情
     *
     * @param inputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/systemDetailFaq", method = RequestMethod.POST)
    public RespBody<FaqDetailOutput> queryFaqInfo(ReqBody<BusinessKnowledgeGetInput> inputReqBody) throws EduException {

    	BusinessKnowledgeGetInput input = inputReqBody.getContent();
    	FaqDetailOutput systemFaq = systemFaqService.queryFaqQuestionInfo(input);

        return new RespBody<FaqDetailOutput>(systemFaq);
    }
	
	/**
     * 业务知识点问题单个详情
     *
     * @param inputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/systemFaqDetail", method = RequestMethod.POST)
    public RespBody<SystemFaqQuestionOutput> queryBusinessInfo(ReqBody<BusinessKnowledgeGetInput> inputReqBody) throws EduException {

    	BusinessKnowledgeGetInput input = inputReqBody.getContent();
    	SystemFaqQuestionOutput systemFaq = (systemFaqService.queryFaqInfo(input.getBusiness(),input.getId()));

        return new RespBody<SystemFaqQuestionOutput>(systemFaq);
    }
	
	/**
	 * 新增或修改知识点faq
	 * @param infoInputReqBody
	 * @return
	 * @throws EduException
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
	@RequestMapping(value = "/addOrUpdateSystemFaq", method = RequestMethod.POST)
	public RespBody<String> addOrUpdateSystemFaq(ReqBody<BusinessKnowledgeGetInput> infoInputReqBody) throws EduException {
		systemFaqService.addOrUpdateFaqInfo(infoInputReqBody.getContent());
		return new RespBody<String>();
	}
    
    /**
     * 删除(逻辑上)业务知识点
     * 
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/faqDelete", method = RequestMethod.POST)
    public RespBody<String> deleteBusinessInfo(ReqBody<BusinessKnowledgeGetInput> infoInputReqBody) throws EduException {
    	systemFaqService.deleteFaqInfo(infoInputReqBody.getContent().getId());
        return new RespBody<String>();
    }

}
