package com.ancun.boss.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.model.SystemUserInfo;
import com.ancun.boss.pojo.userInfo.UserEditPwdInput;
import com.ancun.boss.service.system.IUserInfoService;
import com.ancun.boss.util.StringUnicodeUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import com.ancun.utils.DESUtils;
import com.ancun.utils.MD5Utils;

@RestController
public class IndividualUserController extends BaseController {
	@Resource
	private IUserInfoService userInfoService;

	@AccessToken(access = AccessType.WEB, checkAccess = true)
	@RequestMapping(value = "/updateIndividualUserPwd", method = RequestMethod.POST)
	public void updateIndividualUserPwd(ReqBody<UserEditPwdInput> reqBody)
			throws EduException {
		UserEditPwdInput pwd = reqBody.getContent();

		SystemUserInfo systemUserInfo = new SystemUserInfo();
		systemUserInfo.setUserno(pwd.getUserno().trim());
		systemUserInfo.setPwd(pwd.getOldPwd().trim());

		userInfoService.selectSingleSystemUserInfo(systemUserInfo);
		
		if (!pwd.getNewPwd().trim().equals(pwd.getNewPwd2().trim())) {
			throw new EduException(MessageConstant.PWD_NOT_SAME);
		}
		if (pwd.getNewPwd().trim().length() < 6) {
			throw new EduException(MessageConstant.PWD_NOT_IN_RULE);
		}
		if (!StringUnicodeUtil.isContainDigit(pwd.getNewPwd().trim())
				|| !StringUnicodeUtil.isContainUpperCase(pwd.getNewPwd().trim())
				|| !StringUnicodeUtil.isContainLowCase(pwd.getNewPwd().trim())) 
		{
			throw new EduException(MessageConstant.PWD_NOT_IN_RULE);
		}

		systemUserInfo.setPwd(DESUtils.encrypt(MD5Utils.md5(pwd.getNewPwd().trim()), null));
		userInfoService.updateIndividualUserPwd(systemUserInfo);
	}

}
