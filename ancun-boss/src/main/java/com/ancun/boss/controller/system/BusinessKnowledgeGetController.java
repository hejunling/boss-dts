package com.ancun.boss.controller.system;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeDetailFaqInput;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeDetailFaqOutput;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetInput;
import com.ancun.boss.pojo.businessKnowledge.BusinessKnowledgeGetOutput;
import com.ancun.boss.service.system.BusinessKnowledgeGetService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;


/**
 * 知识点查询
 * 
 * @author chenyanshuang
 *
 */
@RestController
public class BusinessKnowledgeGetController extends BaseController{
	@Resource
	private BusinessKnowledgeGetService businessKnowledgeGetService;
	@AccessToken(access = AccessType.WEB,checkAccess=true)
	@RequestMapping(value = "/businessKnowledgeGet", method = RequestMethod.POST)
	public RespBody<BusinessKnowledgeGetOutput> businessInfoGet(ReqBody<BusinessKnowledgeGetInput> input) {
		BusinessKnowledgeGetInput inputContent = input.getContent();
		BusinessKnowledgeGetOutput output = businessKnowledgeGetService.getBusinessKnowledgeGet(inputContent);
		return new RespBody<BusinessKnowledgeGetOutput>(output);
	}
	
	/**
     * 业务知识点单个详情
     *
     * @param inputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/businessKnowledgeDetailFaq", method = RequestMethod.POST)
    public RespBody<BusinessKnowledgeDetailFaqOutput> queryBusinessInfo(ReqBody<BusinessKnowledgeGetInput> inputReqBody) throws EduException {

    	BusinessKnowledgeGetInput input = inputReqBody.getContent();
    	BusinessKnowledgeDetailFaqOutput systemFaq = (businessKnowledgeGetService.queryBusinessInfo(input.getBusiness(),input.getQuestion()));

        return new RespBody<BusinessKnowledgeDetailFaqOutput>(systemFaq);
    }
    
    

    /**
     * 新增或修改业务知识点
     *
     * @param infoInputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/businessKnowledgeAddOrUpdate", method = RequestMethod.POST)
    public RespBody<String> addOrUpdateBusinessInfo(ReqBody<BusinessKnowledgeDetailFaqInput> infoInputReqBody) throws EduException {
    	businessKnowledgeGetService.addOrUpdateBusinessInfo(infoInputReqBody.getContent());
        return new RespBody<String>();
    }
    
    /**
     * 删除(逻辑上)业务知识点
     * 
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/businessKnowledgeDelete", method = RequestMethod.POST)
    public RespBody<String> deleteBusinessInfo(ReqBody<BusinessKnowledgeGetInput> infoInputReqBody) throws EduException {
    	businessKnowledgeGetService.deleteBusinessInfo(infoInputReqBody.getContent().getBusiness());
        return new RespBody<String>();
    }
    
    
}
