package com.ancun.boss.business.controller.ivr;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.persistence.module.BizComboInfo;
import com.ancun.boss.business.persistence.module.BizUserInfo;
import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserInput;
import com.ancun.boss.business.pojo.ivr.BizUnifiedIvrQueryInput;
import com.ancun.boss.business.service.bizopenuser.IBizOpenUserService;
import com.ancun.boss.business.service.ivr.IBizUserInfoService;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.boss.util.ContainerUtil;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;

@RestController
public class BizUnifiedIvrQueryController {

	@Resource
	private IBizUserInfoService bizUserInfoService;

	@Resource
	private IBasicConfigService basicConfigService;

	@Resource
	private BaseDataServiceImpl baseDataServiceImpl;
	
	
	@Resource
	private IBizOpenUserService bizUserService;	
	
	@AccessToken(access = AccessType.IVR, checkAccess = false)
	@RequestMapping(value = "/ylcnuserinfoGet", method = RequestMethod.POST)
	public RespBody<Map<String, String>> BizUnifiedIvrQuery(
			ReqBody<BizUnifiedIvrQueryInput> req) {

		BizUserInfo bizUser = input2bizUserInfo(req.getContent());
		
		String autoOpenProvinces = baseDataServiceImpl.getConfigMessage("ALL",
				BizRequestConstant.AUTO_OPEN_PROVINCES);

		if(autoOpenProvinces==null||autoOpenProvinces.equals(""))
		{
			throw new EduException();
		}
		
		if (autoOpenProvinces.contains(bizUser.getRpcode())) {
			bizUser = autoOpenProvince(bizUser);
		} else {
			bizUser = commonProvince(bizUser);
		}

		RespBody<Map<String, String>> respBody = new RespBody<Map<String, String>>();
		if (bizUser != null) {
			Map<String, String> returnMap = bizUserInfo2Map(bizUser);
			respBody.setContent(returnMap);
		} else {
			//respBody.setContent(null);
			throw new EduException(MessageConstant.REQUEST_USER_NOT_EXISTS_OR_ABNORMAL);
		}
		return respBody;

	}

	private BizUserInfo commonProvince(BizUserInfo bizUser) {
		List<BizUserInfo> list = bizUserInfoService.selectSingle(bizUser.getUserNo());
		if (ContainerUtil.isSingle(list)) {
			BizUserInfo user = list.get(0);
			return user;
		}
		return null;
	}

	private BizUserInfo autoOpenProvince(BizUserInfo bizUser) {
		List<BizUserInfo> list = bizUserInfoService.selectSingle(bizUser.getUserNo());
		if (ContainerUtil.isSingle(list)) {
			BizUserInfo user = list.get(0);
			return user;
		}else{
			
			List<BizComboInfo> comboList= bizUserInfoService.selectDefaultComboByRpcode(bizUser.getRpcode());			
			if(ContainerUtil.isSingle(comboList)){
				bizUser.setTcid(comboList.get(0).getTcid());				
				bizUserService.openUserSingle(user2OpenUser(bizUser));				
				//添加没有返回内容，所以再查一遍好了。此次一定有数据，所以不再判断。
				BizUserInfo user = bizUserInfoService.selectSingle(bizUser.getUserNo()).get(0);
				return user;
			}			
			return null;
		}
	}

	private Map<String, String> bizUserInfo2Map(BizUserInfo user) {
		Map<String, String> returnMap = new HashMap<String, String>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 普通字段，尽量以字母顺序排列
		returnMap.put("allowMaxStore", "0");
		returnMap.put("citycode", user.getCityCode());
		returnMap.put("defaultpwdflag", "0");
		returnMap.put("ownerno", user.getUserNo());
		returnMap.put("phone", user.getPhone());
		returnMap.put("phonetype", user.getPhonetype());
		returnMap.put("rectipflag", user.getRectip());
		returnMap.put("rpcode", user.getRpcode());
		returnMap.put("signuptime", df.format(user.getIntime()));
		returnMap.put("tcId", user.getTcid().toString());
		returnMap.put("usedingStore", "0");
		returnMap.put("userStatus", user.getStatus());
		returnMap.put("usertype", user.getAccountType());
		returnMap.put("zskttime", df.format(user.getIntime()));

		// 特殊字段，需要再度计算的。尽量也以字母顺序排列
		List<BizComboInfo> comboList= bizUserInfoService.selecComboTypeById(user.getTcid());			
		if(ContainerUtil.isSingle(comboList)){
			returnMap.put("tcType", comboList.get(0).getType());
		}else{
			throw new EduException();
		}

		return returnMap;
	}
	
	private BizUserInfo input2bizUserInfo(BizUnifiedIvrQueryInput input) {
		BizUserInfo user = new BizUserInfo();
		
		if(input.getOwnerno()==null)
		{
			throw new EduException(MessageConstant.USER_NO_IS_NULL);
		}
		user.setUserNo(input.getOwnerno());
		if(input.getProvinceCode()==null)
		{
			throw new EduException(MessageConstant.PROVINCE_NOT_BE_EMPTY);
		}	
		user.setRpcode(input.getProvinceCode().toString());
		
//		user.setTypeCode(input.getType_code().toString());
		if(input.getBizNo()==null)
		{
			throw new EduException(MessageConstant.BIZNO_NOTNULL);
		}
		user.setBizNo(input.getBizNo());
		
		return user;
	}

	private OpenBizUserInput user2OpenUser (BizUserInfo user){
		OpenBizUserInput input = new OpenBizUserInput();
		
		input.setUserno(user.getUserNo());
		input.setBizuserno(user.getUserNo());
		input.setTaocanid(user.getTcid());
		input.setOpendatetime(new Date());
		
		//此处都用于开通个人用户，所以和两个变量相同。
		input.setPhone(user.getUserNo());
		input.setUserType(BizRequestConstant.PERSONAL_USER);
//		input.setType_code(user.getTypeCode());
		input.setBizNo(user.getBizNo());
		input.setRpcode(user.getRpcode());
		input.setIsignupsource(Constant.INSOURCE_IVR);
				
		return input;
	}
}
