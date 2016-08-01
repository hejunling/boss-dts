package com.ancun.boss.business.controller.bizopenuser;

import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserBatchInput;
import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserInfo;
import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserInput;
import com.ancun.boss.business.service.bizopenuser.IBizOpenUserService;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import com.ancun.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BizOpenUserController {
	@Resource
	private IBizOpenUserService bizUserService;
	
	
    /**
     * 用户开通
     *
     * @param inputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/addBizUser", method = RequestMethod.POST)
    public RespBody<String> addBizUser(ReqBody<OpenBizUserInput> inputReqBody) throws
            EduException {
    	OpenBizUserInput input = inputReqBody.getContent();
    	bizUserService.openUserSingle(input);
    	
        return new RespBody<String>(null);
    }
    
    /**
     * 个人用户批量开通
     *
     * @param inputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/addBizUserBatch", method = RequestMethod.POST)
    public RespBody<String> addBizUserBatch(ReqBody<OpenBizUserBatchInput> inputReqBody) throws
            EduException {
    	OpenBizUserBatchInput input = inputReqBody.getContent();
    	vaildUserList(input.getUserlist());
    	bizUserService.openUserBatch(input);
    	
        return new RespBody<String>(null);
    }
    
    private void vaildUserList(List<OpenBizUserInfo> userlist){
    	
    	for (OpenBizUserInfo user : userlist) {

    		if (StringUtil.isEmpty(user.getBizuserno())) {
    			throw new EduException(MessageConstant.BATCH_USER_NULL);
    		}
    		if (user.getOpendatetime() == null) {
    			throw new EduException(MessageConstant.BATCH_OPENTIME_NULL);
    		}
    		
    		int count = 0;
    		for(OpenBizUserInfo u : userlist){
    			if (u.getBizuserno().equals(user.getBizuserno())){
    				count ++;
    			}
    		}
    		if (count > 1) {
    			throw new EduException(MessageConstant.BATCH_DATA_MULTIPLE);
    		}
    	}
    }
}
