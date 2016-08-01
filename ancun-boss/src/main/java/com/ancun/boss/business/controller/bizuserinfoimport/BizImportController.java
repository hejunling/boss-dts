package com.ancun.boss.business.controller.bizuserinfoimport;

import com.ancun.boss.business.pojo.bizuserinfoimport.SelectBizUserInfoImportInput;
import com.ancun.boss.business.pojo.bizuserinfoimport.SelectBizUserInfoImportOutput;
import com.ancun.boss.business.service.bizuserinfoImport.IBizUserInfoImportService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BizImportController {
	@Resource
	private IBizUserInfoImportService bizUserInfoImportService;
	
	
    /**
     * 查询业务用户信息导入错误信息
     *
     * @param inputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/selectBizUserInfoImportFailedInfo", method = RequestMethod.POST)
    public RespBody<SelectBizUserInfoImportOutput> selectBizUserInfoImportList(ReqBody<SelectBizUserInfoImportInput> inputReqBody) throws
            EduException {
        SelectBizUserInfoImportInput input = inputReqBody.getContent();
        SelectBizUserInfoImportOutput out = bizUserInfoImportService.selectBizUserInfoImportList(input);
    	
        return new RespBody<SelectBizUserInfoImportOutput>(out);
    }

}
