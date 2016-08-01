package com.ancun.boss.business.service.bizopenuser.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ancun.boss.persistence.mapper.SystemNoticeMapper;
import com.ancun.boss.persistence.model.SystemNotice;
import com.ancun.boss.persistence.model.SystemNoticeExample;
import com.ancun.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ancun.boss.business.constant.ImportFailedConstant;
import com.ancun.boss.business.persistence.mapper.BizComboInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizEntInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizPersonInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoImportFailedMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoQueryMapper;
import com.ancun.boss.business.persistence.mapper.BizUserLifeCircleMapper;
import com.ancun.boss.business.persistence.module.BizComboInfo;
import com.ancun.boss.business.persistence.module.BizComboInfoExample;
import com.ancun.boss.business.persistence.module.BizEntInfo;
import com.ancun.boss.business.persistence.module.BizEntInfoExample;
import com.ancun.boss.business.persistence.module.BizPersonInfo;
import com.ancun.boss.business.persistence.module.BizPersonInfoExample;
import com.ancun.boss.business.persistence.module.BizUserInfo;
import com.ancun.boss.business.persistence.module.BizUserInfoExample;
import com.ancun.boss.business.persistence.module.BizUserInfoImportFailed;
import com.ancun.boss.business.persistence.module.BizUserInfoQuery;
import com.ancun.boss.business.persistence.module.BizUserLifeCircle;
import com.ancun.boss.business.pojo.allprovince.ProvinceServiceInput;
import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserBatchInput;
import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserInfo;
import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserInput;
import com.ancun.boss.business.service.allprovince.IProvinceService;
import com.ancun.boss.business.service.allprovince.IPublicProvinceService;
import com.ancun.boss.business.service.bizopenuser.IBizOpenUserService;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.service.auth.impl.AuthenticationUtil;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.common.biz.email.SendEmail;
import com.ancun.common.biz.pojo.common.RegionAreaInfo;
import com.ancun.common.biz.regionarea.RegionAreaInfoBiz;
import com.ancun.core.exception.EduException;
import com.ancun.utils.PhoneCallCheck;
import com.ancun.utils.StringUtil;
import com.google.common.collect.HashBasedTable;

@Service
public class BizOpenUserServiceImpl implements IBizOpenUserService{
	
	private static final Logger log = LoggerFactory.getLogger(BizOpenUserServiceImpl.class);
	
    /** 提醒标题 */
    private static final String NOTICE_TITLE = "用户开通相关";
    
	public static final String NeedOpen = "NeedOpen";
	
	@Resource
	private BizUserInfoMapper bizUserInfoMapper;
	
	@Resource
	private BizUserLifeCircleMapper bizUserLifeMapper;
	
	@Resource
	private BizUserInfoQueryMapper bizUserQueryMapper;
	
	@Resource
	private RegionAreaInfoBiz regionAreaInfoBiz;
	
	@Resource
	private BizComboInfoMapper comboMapper;
	
	@Resource
	private IPublicProvinceService publicProvinceService;
	
	@Resource
    private DataSourceTransactionManager txManager;
	
	@Resource
	private AuthenticationUtil authUtil;
	
	@Resource
	private BaseDataServiceImpl baseDataService;
	
	@Resource
	private BizEntInfoMapper bizEntInfoMapper;
	
	@Resource
	private BizUserInfoImportFailedMapper failedMapper;
	
	@Resource
	private BizPersonInfoMapper bizPersonInfoMapper;

	@Resource
	private SystemNoticeMapper systemNoticeMapper;
	
    /** 发送邮件 */
    @Resource
    private SendEmail sendEmail; 
    
	/* (non-Javadoc)
	 * 单个用户开通
	 */
	@Override
	public void openUserSingle(OpenBizUserInput input) {
		String rpcode = null; // 省份信息
		String cityCode = null; // 城市信息
		String phoneType = null; // 号码类型

		// 鉴权
		if (!authUtil.validCheck(input.getBizuserno(), input.getRpcode(), input.getType_code(),input.getBizNo())){
			
			throw new EduException(MessageConstant.PHONE_AUTH_ERROR);
		}
		
		BizUserInfoExample userExample = new BizUserInfoExample();
		BizUserInfoExample.Criteria userCriteria = userExample.createCriteria();
		userCriteria.andUserNoEqualTo(input.getBizuserno());
		
		BizComboInfoExample comboExample = new BizComboInfoExample();
		BizComboInfoExample.Criteria c = comboExample.createCriteria();
		c.andTcidEqualTo(input.getTaocanid());
		
		List<BizComboInfo> comboList = comboMapper.selectByExample(comboExample);
		
		if (comboList.size() == 0) {
			throw new EduException(MessageConstant.TAOCAN_IS_NOT_FIND);
		}
		
		
		List<BizUserInfo> list = bizUserInfoMapper.selectByExample(userExample);
		
		if (list.size() > 1) {
			throw new EduException(MessageConstant.USER_REPEAT_ERROR);
		}
		
		if ((list == null || list.size() == 0) || ((list != null && list.size() > 0) && !list.get(0).getStatus().equals(BizRequestConstant.OPENED_USER)) 
				|| (list.get(0).getStatus().equals(BizRequestConstant.OPENED_USER) && list.get(0).getUserType().equals(BizRequestConstant.PERSONAL_USER) && input.getUserType().equals(BizRequestConstant.ENT_USER))) {
			// 调用远程接口
			HashBasedTable<String, String, IProvinceService> table = publicProvinceService.getProvinceService();
			IProvinceService ftpService =  table.get(input.getRpcode(), input.getType_code());
			if (ftpService != null) {
				ProvinceServiceInput provinceInput = new ProvinceServiceInput();
				provinceInput.setUserno(input.getBizuserno());
				provinceInput.setUserTel(input.getBizuserno());
				provinceInput.setTcID(input.getTaocanid().toString());
				provinceInput.setUserType(input.getUserType());
				provinceInput.setOpenTime(input.getOpendatetime());
				if (input.getUserType().equals(BizRequestConstant.ENT_USER)) {
					provinceInput.setEntUser(input.getBizuserno());
				}
				
				provinceInput.setType(comboList.get(0).getType());
				ftpService.open(provinceInput);
			}

		} else {
			throw new EduException(MessageConstant.REQUEST_USER_NOT_EXISTS_OR_ABNORMAL);
		}

		//判断号码状态，固话、手机
		if(PhoneCallCheck.checkMobile(input.getBizuserno())){
			//给用户信息对象组装上号码类别（手机）
			phoneType = BizRequestConstant.USER_PHONETYPE_PHONE;
		}else{
			//给用户组装号码类别信息（固话）
			phoneType = BizRequestConstant.USER_PHONETYPE_TEL;
		}

		// 取得号码省份信息
		try {
			//根据用户号码获取号码归属地信息Model，并给用户组装上省份编号、城市编号、区号等信息
			RegionAreaInfo regionAreaInfo = getRegionAreaInfo(input.getBizuserno());

			if (regionAreaInfo != null) {
				rpcode = regionAreaInfo.getProvinceCode();
				cityCode = regionAreaInfo.getCityCode();
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		
		// 业务用户表中都没有数据，开通
		if (list == null || list.size() == 0) {

			// 开通用户
			openUser(input,rpcode,cityCode,phoneType);
			
			if (input.getUserType().equals(BizRequestConstant.ENT_USER)) {
				
				// 企业信息不为空
				if (!input.isEntEmpty()) {
					// 添加企业信息
					addEntInfo(input);
				}
			} else if (input.getUserType().equals(BizRequestConstant.PERSONAL_USER)) {
				// 个人信息不为空
				if (!input.isPersonalEmpty()) {
					addPersonInfo(input);
				}
			}
			
			
			// 用户生命周期表
			addLifeCircle(input,rpcode,cityCode,phoneType);
			
			// 用户生命周期表
			addQuery(input);
			
			
		} else if(list != null && list.size() > 0) {
			
			BizUserInfo user = list.get(0);
			
			if (user.getStatus().equals(BizRequestConstant.OPENED_USER) && !(user.getUserType().equals(BizRequestConstant.PERSONAL_USER)&&input.getUserType().equals(BizRequestConstant.ENT_USER))) {
				// 在业务用户表中已开通
				throw new EduException(MessageConstant.USER_ALREADY_OPEN);
			} else if (user.getStatus().equals(BizRequestConstant.CANCEL_USER) || (user.getUserType().equals(BizRequestConstant.PERSONAL_USER)&&input.getUserType().equals(BizRequestConstant.ENT_USER))) {
				
				updateUser(input, user,rpcode,cityCode,phoneType);
				
				// 企业信息不为空
				if (input.getUserType().equals(BizRequestConstant.ENT_USER) ) {
					
					if (!input.isEntEmpty()) {
						List<BizEntInfo> entInfos = getEntInfo(input.getBizuserno());
						
						if (entInfos.size() == 0) {
							// 添加企业信息
							addEntInfo(input);
						} else {
							// 修改企业信息
							updateEntInfo(input);
						}
					}

				}else if (input.getUserType().equals(BizRequestConstant.PERSONAL_USER)){
					
					if (!input.isPersonalEmpty()) {
						List<BizPersonInfo> personInfos = getPersonInfo(input.getBizuserno());
						if (personInfos.size() == 0) {
							addPersonInfo(input);
						} else {
							updatePersonInfo(input);
						}
					}
				}
				 
				// 用户生命周期表
				addLifeCircle(input,rpcode,cityCode,phoneType);
				
				// 用户生命周期表
				addQuery(input);
				
			} else {
				throw new EduException(MessageConstant.REQUEST_USER_NOT_EXISTS_OR_ABNORMAL);
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * 个人版批量导入开通
	 */
	@Override
	@Async
	public void openUserBatch(OpenBizUserBatchInput input) {

		/**
		 * add by zkai on 2016/04/28
		 * 开通开始添加到系统通知表中，备注为 “开通开始”
		 */
		// 导入前插入到系统通知表中
		Long noticeId = null;
		try{
			noticeId = insertNotice(input.getUserno()); // 通知编号
		}catch (Exception e){
			log.error("通知信息添加失败",e);
		}

		String rpcode = input.getRpcode();
		Long tcid = input.getTaocanid();
		String isig = input.getIsignupsource();
		String bizno = input.getBizno();
		String phoneType = input.getPhoneType();
		String typeCode = input.getType_code();
		String flag = null;
		
		BizComboInfoExample comboExample = new BizComboInfoExample();
		BizComboInfoExample.Criteria c = comboExample.createCriteria();
		c.andTcidEqualTo(input.getTaocanid());
		
		List<BizComboInfo> comboList = comboMapper.selectByExample(comboExample);
		
		List<BizUserInfoImportFailed> errorList = new ArrayList<BizUserInfoImportFailed>();
		List<OpenBizUserInfo> userList = input.getUserlist();
		int reallyOpen = 0;// 真正开通数量
		Map<String,String> map = new HashMap<String,String>();//存储所有开通userno
		Map<String,String> inMap = new HashMap<String,String>();//存储数据库中已有的userno
		Map<String,String> oMap = new HashMap<String,String>();//存储数据库中已有的phone
		List<String> userNoList = new ArrayList<String>();
		List<String> userNoListMore = new ArrayList<String>();
		int size = userList.size();
		log.warn("-------批量开通,导入数据为：size = " +size + ",省份："+rpcode + ",套餐："+tcid + "，来源："+isig + ",begin....");
		if(size<1000){//如果导入数据小于1000条，则可以一次in
			for (OpenBizUserInfo openUserInfo : userList) {
				userNoList.add(openUserInfo.getBizuserno());
			}
		}else{//如果大于1000条，业务规定数据不允许大于2000
			for (int i = 0; i < 1000; i++) {
				OpenBizUserInfo openUserInfo = userList.get(i);
				userNoList.add(openUserInfo.getBizuserno());
			}
			for (int i = 1000; i < size; i++) {
				OpenBizUserInfo openUserInfo = userList.get(i);
				userNoListMore.add(openUserInfo.getBizuserno());
			}
		}
		
		//---------------对所有要开通用户进行分类---------------------------
		//查询个人数据库
		List<BizUserInfo> list = QueryUserStatusList(userNoList,userNoListMore);
		List<BizUserInfo> olist = QueryPhoneUsedList(userNoList,userNoListMore);
		
		log.warn("-------批量开通的list.size = " + list.size()+",olist.size = " + olist.size());
		//将个人数据库的已存在的数据缓存
		for (BizUserInfo userInfo : list) {
			//原则上数据库中userno不能重复，如果重复则此方法存在问题
			inMap.put(userInfo.getUserNo(), userInfo.getStatus());
        }
		//遍历数据库已有的phone，存入map
		//将个人数据库的已存在的手机号缓存
		for (BizUserInfo userInfo : olist) {
			//将联系电话存入map中
			oMap.put(userInfo.getPhone(), userInfo.getUserNo());
		}
		for (String userno : userNoList) {
			if (inMap.get(userno) == null) {
				map.put(userno, NeedOpen);
			} else if (inMap.get(userno).equals(BizRequestConstant.OPEN_STATUS)) {// 代表该用户已经开通
				map.put(userno, BizRequestConstant.OPEN_STATUS);
			} else if (inMap.get(userno).equals(BizRequestConstant.CANCEL_USER)) {// 代表该用户处于“退订”状态
				map.put(userno, BizRequestConstant.CANCEL_USER);
			}
        }
		
		//------------------对分类结果进行入库或更新处理----------------------
		for (OpenBizUserInfo openUserInfo : userList) {
			
			flag = map.get(openUserInfo.getBizuserno());
			
			BizUserInfoImportFailed errorInfo = openBizUser(openUserInfo,rpcode,tcid,isig,bizno,phoneType,typeCode,flag,comboList.get(0).getType());
			
			if (errorInfo != null) {
				errorList.add(errorInfo);
			} else {
				reallyOpen++;
			}
		}
		
		for (BizUserInfoImportFailed error : errorList) {
			failedMapper.insert(error);
		}
		
		log.warn("-------真正开通数量为："+reallyOpen);

		/**
		 * add by zkai on 2016/04/28
		 * 开通开始添加到系统通知表中，备注为 “开通结束”
		 */
		try{
			updateNotice(noticeId);
		}catch (Exception e){
			log.error("通知编号为："+noticeId+"修改失败",e);
		}

		
	}
	
    /**
     * 开通用户
     *
     * @param info
     * @param rpcode
     * @param tcid
     * @param isig
     * @param bizno
     * @param phoneType
     * @param typeCode
     * @param flag
     * @return
     */
	private BizUserInfoImportFailed openBizUser(OpenBizUserInfo info, String rpcode, Long tcid, String isig, String bizno,
			String phoneType, String typeCode, String flag, String type) {
		String cityCode = null;
		BizUserInfoImportFailed errorInfo = getFailedBean(info,tcid,isig,bizno);
		try {
			
			String errorMsg = null;
			
			// 校验参数
			errorMsg = validBean(info);
			if (errorMsg != null ) {
				errorInfo.setRemark(errorMsg);
				return errorInfo;
			}
			
			// 鉴权
			errorMsg = validCheck(info.getBizuserno(), rpcode, typeCode,bizno);
			
			if (errorMsg != null) {
				errorInfo.setRemark(errorMsg);
				return errorInfo;
			}


			// 调用远程接口
			errorMsg = callProvinceService(info.getBizuserno(), rpcode, typeCode, tcid.toString(), info.getOpendatetime(),type);
			
			if (errorMsg != null) {
				errorInfo.setRemark(errorMsg);
				return errorInfo;
			}

			// 取得号码省份信息
			try {
				//根据用户号码获取号码归属地信息Model，并给用户组装上省份编号、城市编号、区号等信息
				RegionAreaInfo regionAreaInfo = getRegionAreaInfo(info.getBizuserno());

				if (regionAreaInfo != null) {
					rpcode = regionAreaInfo.getProvinceCode();
					cityCode = regionAreaInfo.getCityCode();
				}
			}catch (Exception e){
				log.error(e.getMessage(),e);
			}

			// 开通用户
			// 需要更新数据
			if (flag.equals(BizRequestConstant.CANCEL_USER)) {
				
				errorMsg = updateUserBatch(info, rpcode, tcid, isig, bizno, phoneType,cityCode);
				// 更新业务用户信息
				if (errorMsg == null) {
					
					List<BizPersonInfo> personInfos = getPersonInfo(info.getBizuserno());
					
					// 用户生命周期表
					errorMsg = addLifeCircleBatch(info, rpcode, tcid, isig, bizno, phoneType,cityCode);
					
					// 用户查询表
					errorMsg +=  addQueryBatch(info, bizno);
					
					if (!errorMsg.equals("")) {
						errorInfo.setRemark(errorMsg);
						return errorInfo;
					}
				} else {
					errorInfo.setRemark(errorMsg);
					return errorInfo;
				}

			} else if (flag.equals(NeedOpen)) {
				
				// 插入业务用户信息
				errorMsg = openUserInfoBatch(info, rpcode, tcid, isig, bizno, phoneType,cityCode);
				
				if (errorMsg == null) {
					
					// 用户生命周期表
					errorMsg = addLifeCircleBatch(info, rpcode, tcid, isig, bizno, phoneType,cityCode);
					
					// 用户查询表
					errorMsg += addQueryBatch(info, bizno);
					
					if (!errorMsg.equals("")) {
						errorInfo.setRemark(errorMsg);
						return errorInfo;
					}
				} else {
					errorInfo.setRemark(errorMsg);
					return errorInfo;
				}

				
			} else {
				errorInfo.setRemark(ImportFailedConstant.USER_STATUS_ERROR);
				return errorInfo;
			}
			
		} catch (Exception e) {
			errorInfo.setRemark(ImportFailedConstant.OPEN_USER_ERROR);
			return errorInfo;
		}
		
		return null;
	}
	
	
    /**
     * 用户鉴权
     *
     * @param userno
     * @param rpcode
     * @param typeCode
     * @param bizno
     * @return
     */
	private String validCheck(String userno, String rpcode, String typeCode, String bizno) {
		
		try {
			// 鉴权
			if (!authUtil.validCheck(userno, rpcode, typeCode, bizno)){
				
				return ImportFailedConstant.AUTHENTICATION_ERROE;
			}
		} catch(Exception e) {
			return ImportFailedConstant.AUTHENTICATION_ERROE;
		}
		
		return null;

	}

    /**
	 * 调用远程服务
	 * @param userno
	 * @param rpcode
	 * @param typecode
	 * @param tcid
	 * @param openTime
	 * @param type
     * @return
     */
	private String callProvinceService(String userno, String rpcode, String typecode, String tcid, Date openTime,String type){
		try {
			// 调用远程接口
			HashBasedTable<String, String, IProvinceService> table = publicProvinceService.getProvinceService();
			IProvinceService ftpService =  table.get(rpcode, typecode);
			if (ftpService != null) {
				ProvinceServiceInput provinceInput = new ProvinceServiceInput();
				provinceInput.setUserno(userno);
				provinceInput.setTcID(tcid);
				provinceInput.setUserType(BizRequestConstant.PERSONAL_USER);
				provinceInput.setOpenTime(openTime);
				provinceInput.setType(type);
				provinceInput.setUserTel(userno);
				ftpService.open(provinceInput);
			}
			
		} catch(Exception e) {
			return ImportFailedConstant.BIZ_USER_INTERFACE_ERROR;
		}
		
		return null;
	}


	/**
	 * 插入业务用户表
	 * @param input 用户开通对象
	 * @param rpcode 省份信息
	 * @param cityCode 城市信息
	 * @param phoneType 号码类型（手机，固话）
     */
	private void openUser(OpenBizUserInput input,String rpcode,String cityCode,String phoneType){
		
		BizUserInfo record = new BizUserInfo();
		
		try { 
			String userno = input.getBizuserno();
			record.setUserNo(userno);
			
			//-------------调用归属地服务---------
//			getUserCodeInfo(record, input.getRpcode());

			record.setRpcode(rpcode);		//城市编号
			record.setCityCode(cityCode);		//区号
			record.setPhonetype(phoneType); // 号码类型

			//密码
			record.setPasswd("");
			//套餐id
			record.setTcid(input.getTaocanid());
			//开通时间
			record.setIntime(input.getOpendatetime());
			//电话号码
			record.setPhone(input.getPhone());
			//用户类型
			record.setUserType(input.getUserType());
			//状态
			record.setStatus(BizRequestConstant.OPENED_USER);
			
			record.setBizNo(input.getBizNo());
			
			record.setInsource(input.getIsignupsource());
			
			record.setEntNo(input.getBizuserno());
			
			if (input.getUserType().equals(BizRequestConstant.ENT_USER)) {
				
				record.setAccountType(BizRequestConstant.MAIN_ACCOUNT);
				
				// -------------套餐类型取得-------------
				BizComboInfoExample comboExample = new BizComboInfoExample();
				BizComboInfoExample.Criteria c = comboExample.createCriteria();
				c.andTcidEqualTo(input.getTaocanid());
				
				List<BizComboInfo> comboList = comboMapper.selectByExample(comboExample);
				
				if (isEmpty(comboList)) {
					throw new EduException(MessageConstant.BIZ_USER_TAOCAN_NOT_FOUND);
				}
				String tcType = comboList.get(0).getType();
				if (BizRequestConstant.TC_TYPE_1.equals(tcType)) {
					// 主叫提示音
					record.setCallerVoice(StringUtil.isEmpty(input.getCallervoice())?BizRequestConstant.RECTIP_FLAG_NO : input
							.getCallervoice());
					// 被叫提示音
					record.setCalledVoice(BizRequestConstant.RECTIP_FLAG_NO);
					// 主叫录音默认开启
					record.setCallerRecord(BizRequestConstant.MARK_YES);
					// 被叫录音默认关闭
					record.setCalledRecord(BizRequestConstant.MARK_NO);
				} else if (BizRequestConstant.TC_TYPE_2.equals(tcType)) {
					// 主叫提示音
					record.setCallerVoice(StringUtil.isEmpty(input.getCallervoice())?BizRequestConstant.RECTIP_FLAG_NO : input
							.getCallervoice());
					// 被叫提示音
					record.setCalledVoice(StringUtil.isEmpty(input.getCalledvoice())?BizRequestConstant.RECTIP_FLAG_NO : input
							.getCallervoice());
					// 主叫录音默认开启
					record.setCallerRecord(BizRequestConstant.MARK_YES);
					// 被叫录音默认关闭
					record.setCalledRecord(BizRequestConstant.MARK_YES);
				}
				
				
			} else {
				record.setAccountType(BizRequestConstant.PERSONAL_ACCOUNT);
			}
			
			record.setFax(input.getFax());
			record.setRemark(input.getRemark());
			record.setDefaultpwdflag(BizRequestConstant.DEFAULT_PWD_FLAG_N);
			
			// 是否全包
			OpenBizUserInput setInfo = input.setInfo();
			record.setFullpackage(setInfo.getFullPackage());
			
			bizUserInfoMapper.insert(record);
		} catch (Exception e) {
			// 发送邮件
			sendMail(subBizContent("业务用户表插入失败:",record));
			
			throw new EduException(MessageConstant.BIZUSER_OPEN_FAILED);
		}

		
	}
	
    /**
     * 插入业务用户表
     *
     * @param input
     * @return
     */
	// update by zkai on 2016/05/05 传入参数修改：添加 rpcode，cityCode，phoneType
	private String openUserInfoBatch(OpenBizUserInfo input, String rpcode,Long tcid,String isig, String bizno, String phoneType,String cityCode){
		
		BizUserInfo record = new BizUserInfo();
		
		try { 
			String userno = input.getBizuserno();
			
			record.setInsource(isig);
			record.setUserNo(userno);
			record.setRpcode(rpcode);
			record.setCityCode(cityCode);
			record.setPasswd("");
			record.setPhone(userno);
			record.setStatus(BizRequestConstant.OPENED_USER);
			record.setUserType(BizRequestConstant.PERSONAL_USER);
			record.setPasswd("");
			record.setBizNo(bizno);
			record.setEntNo(userno);
			record.setAccountType(BizRequestConstant.PERSONAL_ACCOUNT);
			record.setPhonetype(phoneType);
			record.setRectip("0");
			record.setCallerVoice("0");
			record.setCalledVoice("0");
			record.setCallerRecord("0");
			record.setCalledRecord("0");
			record.setDefaultpwdflag("0");
			
			record.setTcid(tcid);
			record.setIntime(input.getOpendatetime());
			
			bizUserInfoMapper.insert(record);
			
		} catch (Exception e) {
			
			return ImportFailedConstant.INSERT_BIZ_USER_FAILED;
		}
		return null;

		
	}
	
    /**
     * 更新业务用户表
     *
     * @param input
     * @return
     */
	// update by zkai on 2016/05/05 传入参数修改：添加 rpcode，cityCode，phoneType
	private void updateUser(OpenBizUserInput input, BizUserInfo user,String rpcode,String cityCode,String phoneType) {
		
		BizUserInfoExample userExample = new BizUserInfoExample();
		BizUserInfoExample.Criteria userCriteria = userExample.createCriteria();
		userCriteria.andUserNoEqualTo(input.getBizuserno());
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		
		TransactionStatus transactionUser = txManager.getTransaction(def);
		
		BizUserInfo record = new BizUserInfo();
		

		try {
			
			record.setStatus(BizRequestConstant.OPENED_USER);
			record.setUserType(input.getUserType());
			record.setPasswd("");
			record.setBizNo(input.getBizNo());
			record.setInsource(input.getIsignupsource());
			record.setEntNo(input.getBizuserno());

			record.setCityCode(cityCode);
			record.setPhonetype(phoneType);
			record.setRpcode(rpcode);
			record.setOfftime(null);

			if (input.getUserType().equals(BizRequestConstant.ENT_USER)) {
				record.setAccountType(BizRequestConstant.MAIN_ACCOUNT);
				record.setFax(input.getFax());
				
				// -------------套餐类型取得-------------
				BizComboInfoExample comboExample = new BizComboInfoExample();
				BizComboInfoExample.Criteria c = comboExample.createCriteria();
				c.andTcidEqualTo(input.getTaocanid());
				
				List<BizComboInfo> comboList = comboMapper.selectByExample(comboExample);
				
				if (isEmpty(comboList)) {
					throw new EduException(MessageConstant.BIZ_USER_TAOCAN_NOT_FOUND);
				}
				String tcType = comboList.get(0).getType();
				if (BizRequestConstant.TC_TYPE_1.equals(tcType)) {
					// 主叫提示音
					record.setCallerVoice(StringUtil.isEmpty(input.getCallervoice())?BizRequestConstant.RECTIP_FLAG_NO : input
							.getCallervoice());
					// 被叫提示音
					record.setCalledVoice(BizRequestConstant.RECTIP_FLAG_NO);
					// 主叫录音默认开启
					record.setCallerRecord(BizRequestConstant.MARK_YES);
					// 被叫录音默认关闭
					record.setCalledRecord(BizRequestConstant.MARK_NO);
				} else if (BizRequestConstant.TC_TYPE_2.equals(tcType)) {
					// 主叫提示音
					record.setCallerVoice(StringUtil.isEmpty(input.getCallervoice())?BizRequestConstant.RECTIP_FLAG_NO : input
							.getCallervoice());
					// 被叫提示音
					record.setCalledVoice(StringUtil.isEmpty(input.getCalledvoice())?BizRequestConstant.RECTIP_FLAG_NO : input
							.getCallervoice());
					// 主叫录音默认开启
					record.setCallerRecord(BizRequestConstant.MARK_YES);
					// 被叫录音默认关闭
					record.setCalledRecord(BizRequestConstant.MARK_YES);
				}
			} else {
				
				record.setAccountType(BizRequestConstant.PERSONAL_ACCOUNT);
				
				// 主叫提示音
				record.setCallerVoice("");
				// 被叫提示音
				record.setCalledVoice("");
				// 主叫录音默认开启
				record.setCallerRecord("");
				// 被叫录音默认关闭
				record.setCalledRecord("");
			}
			
			// 个人转企业标识
			if (input.getUserType().equals(BizRequestConstant.ENT_USER) && user.getUserType().equals(BizRequestConstant.PERSONAL_USER)){
				record.setOperation(BizRequestConstant.OPERATION_YES);
			} else {
				record.setOperation(BizRequestConstant.OPERATION_NO);
			}
			
			record.setTcid(input.getTaocanid());
			record.setIntime(input.getOpendatetime());
			record.setRemark(input.getRemark());
			
			// 是否全包
			OpenBizUserInput setInfo = input.setInfo();
			record.setFullpackage(setInfo.getFullPackage());
			
			int result = bizUserInfoMapper.updateByExampleSelective(record, userExample);
			if (result == 1) {
				txManager.commit(transactionUser);
			} else {
				// 回滚事务
				txManager.rollback(transactionUser);
				// 发送邮件
				sendMail(subBizContent("业务用户表更新失败:",record));
				throw new EduException(MessageConstant.BIZUSER_OPEN_FAILED);
			}
		} catch (Exception e){
			// 回滚事务
			txManager.rollback(transactionUser);
			// 发送邮件
			sendMail(subBizContent("业务用户表更新失败:",record));
			throw new EduException(MessageConstant.BIZUSER_OPEN_FAILED);
		}
		
		
	}

    /**
     * 更新业务用户表
     *
     * @param input
     * @return
     */
	private String updateUserBatch(OpenBizUserInfo input, String rpcode,Long tcid,String isig, String bizno, String phoneType,String citycode) {
		
		BizUserInfoExample userExample = new BizUserInfoExample();
		BizUserInfoExample.Criteria userCriteria = userExample.createCriteria();
		userCriteria.andUserNoEqualTo(input.getBizuserno());
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		
		TransactionStatus transactionUser = txManager.getTransaction(def);
		BizUserInfo record = new BizUserInfo();
		

		try {
			
			record.setStatus(BizRequestConstant.OPENED_USER);
			record.setUserType(BizRequestConstant.PERSONAL_USER);
			record.setPasswd("");
			record.setBizNo(bizno);
			record.setEntNo(input.getBizuserno());
			record.setAccountType(BizRequestConstant.PERSONAL_ACCOUNT);
			record.setPhonetype(phoneType);
			record.setRpcode(rpcode);
			record.setCityCode(citycode);
			record.setRectip("0");
			record.setCallerVoice("0");
			record.setCalledVoice("0");
			record.setCallerRecord("0");
			record.setCalledRecord("0");
			record.setDefaultpwdflag("0");
			
			record.setTcid(tcid);
			record.setIntime(input.getOpendatetime());
			
			int result = bizUserInfoMapper.updateByExampleSelective(record, userExample);
			if (result == 1) {
				txManager.commit(transactionUser);
			} else {
				// 回滚事务
				txManager.rollback(transactionUser);
				// 发送邮件
				return ImportFailedConstant.UPDATE_BIZ_USER_FAILED;
			}
		} catch (Exception e){
			// 回滚事务
			txManager.rollback(transactionUser);
			// 发送邮件
			sendMail(subBizContent("业务用户表更新失败:",record));
			return ImportFailedConstant.UPDATE_BIZ_USER_FAILED;
		}
		
		return null;
		
	}

    /**
	 * 插入用户周期
	 * @param input
	 * @param rpcode  省份信息
	 * @param cityCode 区号
	 * @param phoneType 号码类型
     */
	// update by zkai on 2016/05/05 添加输入字段 rpcode，cityCode, phoneType
	private void addLifeCircle(OpenBizUserInput input,String rpcode,String cityCode,String phoneType) {
		
		BizUserLifeCircle circle = new BizUserLifeCircle();
		
		try {
			
			
			circle.setUserNo(input.getBizuserno());
			circle.setUserType(input.getUserType());
			circle.setEntNo(input.getBizuserno());
			if (input.getUserType().equals(BizRequestConstant.ENT_USER)) {
				circle.setAccountType(BizRequestConstant.MAIN_ACCOUNT);
			} else {
				circle.setAccountType(BizRequestConstant.PERSONAL_ACCOUNT);
			}
			circle.setBizNo(input.getBizNo());
			circle.setTcid(input.getTaocanid());
			circle.setSource(input.getIsignupsource());
			
			//-------------调用归属地服务---------
//			getUserCodeInfo4Circle(circle, input.getRpcode());
			circle.setCityCode(cityCode); // 区号
			circle.setRpcode(rpcode); // 省份编号
			circle.setPhonetype(phoneType); // 号码类型（手机，固话）
			
			circle.setOpenCancel(BizRequestConstant.OPEN_STATUS);
			circle.setPhone(input.getPhone());
			circle.setRemark(input.getRemark());
			circle.setTimers(input.getOpendatetime());
			circle.setUpdateTime(input.getOpendatetime());
			
			bizUserLifeMapper.insert(circle);
						
		} catch  (Exception e) {
			// 插入失败,邮件通知
			sendMail(subCircleContent("用户周期表插入失败:",circle));
		}

		
	}
	
    /**
     * 插入用户周期
     *
     * @param input
     * @return
     */
	private String addLifeCircleBatch(OpenBizUserInfo input, String rpcode,Long tcid,String isig, String bizno, String phoneType,String cityCode) {
		
		BizUserLifeCircle circle = new BizUserLifeCircle();
		
		try {
			
			circle.setUserNo(input.getBizuserno());
			circle.setUserType(BizRequestConstant.PERSONAL_USER);
			circle.setBizNo(bizno);
			circle.setAccountType(BizRequestConstant.PERSONAL_ACCOUNT);
			circle.setEntNo(input.getBizuserno());
			circle.setTcid(tcid);
			circle.setSource(isig);
			circle.setPhonetype(phoneType);
			circle.setRectip("0");
			circle.setCallerVoice("0");
			circle.setCalledVoice("0");
			circle.setCallerRecord("0");
			circle.setCalledRecord("0");
			circle.setRpcode(rpcode);
			circle.setCityCode(cityCode);
			
			circle.setOpenCancel(BizRequestConstant.OPEN_STATUS);
			circle.setPhone(input.getBizuserno());
			circle.setTimers(input.getOpendatetime());
			circle.setUpdateTime(input.getOpendatetime());
			
			bizUserLifeMapper.insert(circle);
			
		} catch  (Exception e) {
			// 插入失败
			return ImportFailedConstant.INSERT_BIZ_LIFE_FAILED;
			
		}
		return "";
		
	}
	
	 /**
     * 插入查询
     *
     * @param input
     * @return
     */
	private void addQuery(OpenBizUserInput input){
		
		// 插入用户查询表
		BizUserInfoQuery query = new BizUserInfoQuery();
		
		try{
			
			query.setUserNo(input.getBizuserno());
			query.setUpdateTime(input.getOpendatetime());
			query.setBizNo(input.getBizNo());
			query.setStatus(BizRequestConstant.OPEN_STATUS);
			
			bizUserQueryMapper.insert(query);
		} catch(Exception e){
			// 插入失败,邮件通知
			sendMail(subQueryContent("业务用户查询表插入失败:",query));
		}
	}
	
	 /**
     * 插入查询
     *
     * @param input
     * @return
     */
	private String addQueryBatch(OpenBizUserInfo input, String bizno){
		// 插入用户查询表
		BizUserInfoQuery query = new BizUserInfoQuery();
		
		try{
			
			query.setUserNo(input.getBizuserno());
			query.setUpdateTime(input.getOpendatetime());
			query.setBizNo(bizno);
			query.setStatus(BizRequestConstant.OPEN_STATUS);
			
			bizUserQueryMapper.insert(query);
		} catch(Exception e){
			// 插入失败,邮件通知
			return ImportFailedConstant.INSERT_BIZ_QUERY_FAILED;
		}
		
		return "";
	}
	
	 /**
     * 插入企业信息
     *
     * @param input
     * @return
     */
	private void addEntInfo(OpenBizUserInput input){
		
		BizEntInfo entInfo = new BizEntInfo();
		try {
			
			OpenBizUserInput setInfo = input.setInfo();
			entInfo.setName(setInfo.getEntName());
			entInfo.setAddress(setInfo.getEntAddress());
			entInfo.setEntNo(setInfo.getBizuserno());
			entInfo.setBizNo(setInfo.getBizNo());
			entInfo.setRegNo(setInfo.getEntRegNo());
			entInfo.setUniqueno(setInfo.getUniqueNo());
			entInfo.setFullpackage(setInfo.getFullPackage());

			bizEntInfoMapper.insert(entInfo);
		} catch(Exception e) {
			sendMail(subEntInfoContent("插入企业信息表失败:",entInfo)); 
		}

	}
	
	 /**
     * 更新企业信息
     *
     * @param input
     * @return
     */
	private void updateEntInfo(OpenBizUserInput input) {
		BizEntInfo entInfo = new BizEntInfo();
		try {
			
			BizEntInfoExample example = new BizEntInfoExample();
			example.createCriteria().andEntNoEqualTo(input.getBizuserno());
			
			OpenBizUserInput setInfo = input.setInfo();
			entInfo.setName(setInfo.getEntName());
			entInfo.setAddress(setInfo.getEntAddress());
			entInfo.setBizNo(setInfo.getBizNo());
			entInfo.setRegNo(setInfo.getEntRegNo());
			entInfo.setUniqueno(setInfo.getUniqueNo());
			entInfo.setFullpackage(setInfo.getFullPackage());
			
			bizEntInfoMapper.updateByExampleSelective(entInfo, example);
			
		} catch(Exception e) {
			sendMail(subEntInfoContent("更新企业信息表失败:",entInfo)); 
		}
	}
	
	/**
	 * 新增用户信息
	 * 
	 * @param input
	 */
	private void addPersonInfo(OpenBizUserInput input) {
		BizPersonInfo personInfo = new BizPersonInfo();
		
		try {
			OpenBizUserInput setInfo = input.setInfo();
			personInfo.setBizNo(setInfo.getBizNo());
			personInfo.setFullpackage(setInfo.getFullPackage());
			personInfo.setIdentify(setInfo.getIdentify());
			personInfo.setSex(setInfo.getSex());
			personInfo.setUniqueno(setInfo.getUniqueNo());
			personInfo.setUserNo(setInfo.getBizuserno());
			
			bizPersonInfoMapper.insert(personInfo);
			
		} catch(Exception e) {
			sendMail(subPerSonInfoContent("新增个人信息表失败:",personInfo)); 
		}
	}
	
	/**
	 * 更新用户信息
	 * 
	 * @param input
	 */
	private void updatePersonInfo(OpenBizUserInput input){
		BizPersonInfo personInfo = new BizPersonInfo();
		
		try{
			OpenBizUserInput setInfo = input.setInfo();
			personInfo.setBizNo(setInfo.getBizNo());
			personInfo.setFullpackage(setInfo.getFullPackage());
			personInfo.setIdentify(setInfo.getIdentify());
			personInfo.setSex(setInfo.getSex());
			personInfo.setUniqueno(setInfo.getUniqueNo());
			
			BizPersonInfoExample example = new BizPersonInfoExample();
			example.createCriteria().andUserNoEqualTo(setInfo.getBizuserno());
			
			bizPersonInfoMapper.updateByExampleSelective(personInfo, example);
		} catch(Exception e) {
			sendMail(subPerSonInfoContent("更新个人信息表失败:",personInfo)); 
		}
	}
	
	 /**
     * 取得企业信息
     *
     * @param entno
     * @return
     */
	private List<BizEntInfo> getEntInfo(String entno){
		
		BizEntInfoExample example = new BizEntInfoExample();
		example.createCriteria().andEntNoEqualTo(entno);
		
		return bizEntInfoMapper.selectByExample(example);
	}
	
	 /**
     * 取得个人信息
     *
     * @param userno
     * @return
     */
	private List<BizPersonInfo> getPersonInfo(String userno){
		BizPersonInfoExample example = new BizPersonInfoExample();
		example.createCriteria().andUserNoEqualTo(userno);
		
		return bizPersonInfoMapper.selectByExample(example);
	}
	

	/**
	 * 查询个人用户数据库中是否有数据
	 * @param userNoList
	 * @param userNoListMore
	 * @return
	 */
	private List<BizUserInfo> QueryUserStatusList(List<String> userNoList,List<String> userNoListMore){
		BizUserInfoExample example = new BizUserInfoExample();
		example.createCriteria().andUserNoIn(userNoList);
		if(userNoListMore.size() > 0)
			example.or(example.createCriteria().andUserNoIn(userNoListMore));
		List<BizUserInfo> list = bizUserInfoMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 查询个人用户数据库中电话号码是否被使用
	 * @param phoneList
	 * @param phoneListMore
	 * @return
	 */
	private List<BizUserInfo> QueryPhoneUsedList(List<String> phoneList,List<String> phoneListMore){
		BizUserInfoExample example = new BizUserInfoExample();
		example.createCriteria().andPhoneIn(phoneList).andStatusEqualTo(BizRequestConstant.OPEN_STATUS);
		if(phoneListMore.size() > 0)
			example.or(example.createCriteria().andPhoneIn(phoneListMore));
		List<BizUserInfo> list = bizUserInfoMapper.selectByExample(example);
		return list;
	}


	/**
	 * update by zkai  on 2016/05/05
	 * 方法弃用
	 */
	// -----------------------------begin-------------------------------------------
//	/**
//	 * 组装用户省份编号、城市编号、区号等信息
//	 * @param ui
//	 */
//	public void getUserCodeInfo(BizUserInfo ui,String provinceCode) {
//		//判断号码状态，固话、手机
//		if(PhoneCallCheck.checkMobile(ui.getUserNo())){
//			//给用户信息对象组装上号码类别（手机）
//			ui.setPhonetype(BizRequestConstant.USER_PHONETYPE_PHONE);
//		}else{
//			//给用户组装号码类别信息（固话）
//			ui.setPhonetype(BizRequestConstant.USER_PHONETYPE_TEL);
//		}
//
//		try{
//			//根据用户号码获取号码归属地信息Model，并给用户组装上省份编号、城市编号、区号等信息
//			RegionAreaInfo regionAreaInfo = getRegionAreaInfo(ui.getUserNo());
//
//			if(regionAreaInfo!=null){
//				ui.setRpcode(regionAreaInfo.getProvinceCode());		//城市编号
//				ui.setCityCode(regionAreaInfo.getCityCode());		//区号
//			}else {
//				ui.setRpcode(provinceCode);
//			}
//
//		}catch (Exception e) {
//			log.error(e.getMessage(),e);			//输出错误日志
//
//				ui.setRpcode(provinceCode);
//		}
//	}
//
//	/**
//	 * 组装用户省份编号、城市编号、区号等信息
//	 * @param ui
//	 */
//	public void getUserCodeInfo4Circle(BizUserLifeCircle ui,String provinceCode) {
//		//判断号码状态，固话、手机
//		if(PhoneCallCheck.checkMobile(ui.getUserNo())){
//			//给用户信息对象组装上号码类别（手机）
//			ui.setPhonetype(BizRequestConstant.USER_PHONETYPE_PHONE);
//		}else{
//			//给用户组装号码类别信息（固话）
//			ui.setPhonetype(BizRequestConstant.USER_PHONETYPE_TEL);
//		}
//
//		try{
//			//根据用户号码获取号码归属地信息Model，并给用户组装上省份编号、城市编号、区号等信息
//			RegionAreaInfo regionAreaInfo = getRegionAreaInfo(ui.getUserNo());
//
//			if(regionAreaInfo!=null){
//				ui.setRpcode(regionAreaInfo.getProvinceCode());		//城市编号
//				ui.setCityCode(regionAreaInfo.getCityCode());		//区号
//			}else {
//				ui.setRpcode(provinceCode);		//城市编号
//			}
//
//			// 原逻辑
////			if (StringUtil.isBlank(ui.getRpcode())
////					|| !provinceCode.equals(ui.getRpcode())) {
////				//用户省份编号为空或者请求的省份编号和用户的省份编号不一致，则将用户的省份编号设为请求的省份编号
////				ui.setRpcode(provinceCode);
////			}
//		}catch (Exception e) {
//			log.error(e.getMessage(),e);			//输出错误日志
//			ui.setRpcode(provinceCode);		//城市编号
////			if (StringUtil.isBlank(ui.getRpcode())
////					|| !provinceCode.equals(ui.getRpcode())) {
////				//用户省份编号为空或者请求的省份编号和用户的省份编号不一致，则将用户的省份编号设为请求的省份编号
////				ui.setRpcode(provinceCode);
////			}
//		}
//	}

	// -----------------------------end-------------------------------------------
	
	/**
	 * 根据主叫号码取得归属地信息
	 *
	 * @param callfrom
	 * @return
	 */
	public RegionAreaInfo getRegionAreaInfo(String callfrom) {
		return regionAreaInfoBiz.getRegionAreaInfo(callfrom);
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}
	
	/**
	 * 验证参数
	 * @param bean
	 */
	public String validBean(OpenBizUserInfo bean){
		
		//验证Userno
		if(StringUtil.isEmpty(bean.getBizuserno()) || bean.getBizuserno().length() > 32)
			return ImportFailedConstant.BIZ_USER_FORMAT_ERROR;
		//验证Opendatetime
		if(null == bean.getOpendatetime() || bean.getOpendatetime().toString().trim() == "")
			return ImportFailedConstant.OPENTIME_FORMAT_ERROR;
		
		return null;
	}
	
	/**
	 * 发送邮件
	 *
	 * @param content
	 * @return
	 */
	private void sendMail(String content){
		
		String noticeAddress = baseDataService.getConfigMessage("ALL", BizRequestConstant.NOTICE_MAIL_ADDRESS);
		sendEmail.send(noticeAddress, NOTICE_TITLE, content, null, true);
		
	}
	
	/**
	 * 业务用户邮件内容拼接
	 *
	 * @param title
	 * @param input
	 * @return
	 */
	private String subBizContent(String title,BizUserInfo input){
		String message = title;
		message += "业务用户id=" + input.getUserNo();
		message += ",用户类型=" + input.getUserType();
		message += ",企业编号=" + input.getEntNo();
		message += ",账号类型=" + input.getAccountType();
		message += ",业务编号=" + input.getBizNo();
		message += ",联系电话=" + input.getPhone();
		message += ",套餐ID=" + input.getTcid();
		message += ",用户状态=" + input.getStatus();
		message += ",省份编码=" + input.getRpcode();
		message += ",开通时间=" + input.getIntime();
		message += ",主叫提示音=" + input.getCallerVoice();
		message += ",被叫提示音=" + input.getCalledVoice();
		message += ",主叫录音标记=" + input.getCallerRecord();
		message += ",被叫录音标记=" + input.getCalledRecord();
		message += ",号码类别=" + input.getPhonetype();
		return message;
	}
	
	/**
	 * 业务用户周期邮件内容拼接
	 *
	 * @param title
	 * @param input
	 * @return
	 */
	private String subCircleContent(String title,BizUserLifeCircle input){
		String message = title;
		message += "业务用户id=" + input.getUserNo();
		message += ",用户类型=" + input.getUserType();
		message += ",企业编号=" + input.getEntNo();
		message += ",账号类型=" + input.getAccountType();
		message += ",业务编号=" + input.getBizNo();
		message += ",联系电话=" + input.getPhone();
		message += ",套餐ID=" + input.getTcid();
		message += ",省份编码=" + input.getRpcode();
		message += ",主叫提示音=" + input.getCallerVoice();
		message += ",被叫提示音=" + input.getCalledVoice();
		message += ",主叫录音标记=" + input.getCallerRecord();
		message += ",被叫录音标记=" + input.getCalledRecord();
		message += ",号码类别=" + input.getPhonetype();
		message += ",备注=" + input.getRemark();
		message += ",来源=" + input.getSource();
		message += ",开通/退订=" + input.getOpenCancel();
		return message;
	}
	
	/**
	 * 业务用户查询邮件内容拼接
	 *
	 * @param title
	 * @param input
	 * @return
	 */
	private String subQueryContent(String title, BizUserInfoQuery input){
		
		String message = title;
		message += "业务用户id=" + input.getUserNo();
		message += ",主叫提示音=" + input.getCallerVoice();
		message += ",被叫提示音=" + input.getCalledVoice();
		message += ",主叫录音标记=" + input.getCallerRecord();
		message += ",被叫录音标记=" + input.getCalledRecord();
		message += ",更新时间=" + input.getUpdateTime();
		message += ",业务编号=" + input.getBizNo();
		message += ",状态=" + input.getStatus();
		
		return message;
	}
	
	/**
	 * 企业信息邮件内容拼接
	 *
	 * @param title
	 * @param info
	 * @return
	 */
	private String subEntInfoContent(String title, BizEntInfo info){
		String message = title;
		message += "企业编号=" + info.getEntNo();
		message += ",公司名=" + info.getName();
		message += ",公司地址=" + info.getAddress();
		message += ",业务编号=" + info.getBizNo();
		message += ",工商注册号 =" + info.getRegNo();
		
		return message;
	}
	
	
	/**
	 * 个人信息邮件内容拼接
	 *
	 * @param title
	 * @param info
	 * @return
	 */
	private String subPerSonInfoContent(String title, BizPersonInfo info){
		String message = title;
		message += "业务账号=" + info.getUserNo();
		message += ",性别=" + info.getSex();
		message += ",身份证号=" + info.getIdentify();
		message += ",业务编号=" + info.getBizNo();
		
		return message;
	}
	
	/**
	 * 导入失败记录添加
	 *
	 * @param info
	 * @param tcid
	 * @param isig
	 * @param bizno
	 * @return
	 */
	private BizUserInfoImportFailed getFailedBean(OpenBizUserInfo info, Long tcid, String isig, String bizno){
		BizUserInfoImportFailed record = new BizUserInfoImportFailed();
		
		record.setUserNo(info.getBizuserno());
		record.setUserType(BizRequestConstant.PERSONAL_USER);
		record.setIntime(info.getOpendatetime());
		record.setTcid(tcid);
		record.setInsource(isig);
		record.setBizNo(bizno);
		
		return record;
	}

	/**
	 * add by zkai 2016/04/28
	 * 插入系统通知表
	 * @param userno
     */
	private Long insertNotice(String userno){
		SystemNotice systemNotice = new SystemNotice();
		systemNotice.setCommitTime(DateUtils.getCurrentDate());
		systemNotice.setTitle("个人用户批量开通");
		systemNotice.setType("Email");
		systemNotice.setContent("开始导入 。。。");
		systemNotice.setUserno(userno);
		systemNotice.setReadLabel("1");
		systemNoticeMapper.insert(systemNotice);
		return systemNotice.getId();
	}


	/**
	 * add by zkai on 2016/04/28
	 * 修改系统通知表
	 * @param noticeId
	 */
	private Long updateNotice(Long noticeId){
		SystemNoticeExample systemNoticeExample = new SystemNoticeExample();
		systemNoticeExample.createCriteria().andIdEqualTo(noticeId);
		SystemNotice systemNotice = new SystemNotice();
		systemNotice.setCommitTime(DateUtils.getCurrentDate());
		systemNotice.setContent("导入结束");
		systemNoticeMapper.updateByExampleSelective(systemNotice,systemNoticeExample);
		return systemNotice.getId();
	}
}
