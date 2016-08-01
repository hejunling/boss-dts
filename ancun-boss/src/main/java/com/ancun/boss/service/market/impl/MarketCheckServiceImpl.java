package com.ancun.boss.service.market.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ancun.boss.constant.BasicConfigConstant;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.ConfigConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.BizMarketCheckMapper;
import com.ancun.boss.persistence.mapper.CalledVoiceMapper;
import com.ancun.boss.persistence.mapper.ImportFailedRecordMapper;
import com.ancun.boss.persistence.mapper.MarketCheckMapper;
import com.ancun.boss.persistence.mapper.SystemUserInfoMapper;
import com.ancun.boss.persistence.model.CalledVoice;
import com.ancun.boss.persistence.model.CalledVoiceExample;
import com.ancun.boss.persistence.model.ImportFailedRecord;
import com.ancun.boss.persistence.model.MarketCheck;
import com.ancun.boss.persistence.model.MarketCheckExample;
import com.ancun.boss.persistence.model.SystemBasicConfig;
import com.ancun.boss.persistence.model.SystemUserInfo;
import com.ancun.boss.persistence.model.SystemUserInfoExample;
import com.ancun.boss.pojo.marketInfo.MarketCheckInfoPojo;
import com.ancun.boss.pojo.marketInfo.MarketCheckInput;
import com.ancun.boss.pojo.marketInfo.MarketCheckListInput;
import com.ancun.boss.pojo.marketInfo.MarketCheckOutput;
import com.ancun.boss.pojo.marketInfo.MarketCheckQueryInput;
import com.ancun.boss.pojo.system.BasicConfigInput;
import com.ancun.boss.service.market.MarketCheckService;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.common.biz.accesskey.FileKey;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.core.exception.EduException;
import com.ancun.thirdparty.aliyun.oss.AliyunOSS;
import com.ancun.utils.AESUtils;
import com.ancun.utils.FileUtils;
import com.ancun.utils.StreamUtils;
import com.ancun.utils.StringUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class MarketCheckServiceImpl implements MarketCheckService{
	
	@Resource
	private MarketCheckMapper marketCheckMapper;
	
	@Resource
	private CalledVoiceMapper calledVoiceMapper;
	
	@Resource
	private BizMarketCheckMapper bizMarketCheckMapper;
	
	@Resource
	private IBasicConfigService basicConfigService;
	
	@Resource
	private ImportFailedRecordMapper importMapper;
	
	@Resource
	private SystemUserInfoMapper userInfoMapper;
	
    /** 存储BUCKET */
    private final String bucketName;
    /** 临时目录 */
    private final String tempPathName;
    /** 阿里云上accessId */
    private final String ossAccessId;
    /** 阿里云上accessKey */
    private final String ossAccessKey;
    
    /** 完整文件名中文件夹间分隔符 */
    private static final String SEPARATOR       = File.separator;
	
	
    /**
     * 初期化系统变量
     */
    @Autowired
    public MarketCheckServiceImpl(BaseDataServiceImpl baseDataService) {
        
        // 阿里云buckt
        this.bucketName = baseDataService.getConfigMessage(null, ConfigConstant.ALIYUNOSS_BUCKET_CLIENT);

        // 临时文件目录
        String tempPath = baseDataService.getConfigMessage(null, ConfigConstant.TEMP_FILE_PATH);

        // 去除末尾的"//"
        this.tempPathName = Joiner.on(SEPARATOR).skipNulls()
                .join(Splitter.on(SEPARATOR).trimResults().omitEmptyStrings().split(tempPath));

        // 阿里云accessid
        this.ossAccessId = baseDataService.getConfigMessage(null, ConfigConstant.ALIYUNOSS_ACCESSID);
        // 阿里云accesskey
        this.ossAccessKey = baseDataService.getConfigMessage(null, ConfigConstant.ALIYUNOSS_ACCESSKEY);
    }

	
    /**
     * 营销质检信息查询
     *
     * @param input
     * @return
     * @throws EduException
     */
	@Override
	public MarketCheckOutput getMarketCheckListinfo(MarketCheckQueryInput input) {

		MarketCheckOutput output = new MarketCheckOutput();

		List<MarketCheckInfoPojo> list = bizMarketCheckMapper.selectMakretCheckList(input);

		output.setMarketChcekList(list);
        output.setPageinfo(input.getPage());

		return output;
	}

    /**
     * 营销质检信息 新建/修改
     *
     * @param input
     * @return
     * @throws EduException
     */
	@Override
	public void addOrUpdateMarketCheckInfo(MarketCheckInput input) {
		
		if (input.getModifyflag().equals(BizRequestConstant.TO_ADD)) {
			// 新建校验
			validateMarketCheckInfoForInsert(input);
			
			MarketCheck market = new MarketCheck();
			market.setBusiness(input.getBusiness());
			market.setCalledTeam(input.getCalledTeam());
			market.setCheckNo(input.getCheckNo());
			market.setCheckResult(input.getCheckResult());
			market.setMarketNo(input.getMarketNo());
			market.setMarketTime(input.getMarketTime());
			market.setMarketWay(input.getMarketWay());
			market.setOperator("废弃");
			market.setPhoneNo(input.getPhoneNo());
			market.setRemark(input.getRemark());
			market.setSampling(input.getSampling());
			
			marketCheckMapper.insert(market);
			
		} else if (input.getModifyflag().equals(BizRequestConstant.TO_UPDATE)) {
			// 修改校验
			validateMarketCheckInfoForUpdate(input);
			
			MarketCheck market = new MarketCheck();
			market.setBusiness(input.getBusiness());
			market.setCalledTeam(input.getCalledTeam());
			market.setCheckNo(input.getCheckNo());
			market.setCheckResult(input.getCheckResult());
			market.setMarketNo(input.getMarketNo());
			market.setMarketTime(input.getMarketTime());
			market.setMarketWay(input.getMarketWay());
			market.setOperator("废弃");
			market.setPhoneNo(input.getPhoneNo());
			market.setRemark(input.getRemark());
			market.setSampling(input.getSampling());
			
			marketCheckMapper.updateByPrimaryKeySelective(market);
		}
	}
	
    /**
     * 营销质检详细
     *
     * @param input
     * @return
     * @throws EduException
     */
	public MarketCheckOutput getMarketCheckInfo(MarketCheckQueryInput input) throws EduException {
		
		MarketCheckInfoPojo market = new MarketCheckInfoPojo();
		MarketCheckOutput output = new MarketCheckOutput();
		
		market = bizMarketCheckMapper.selectMarketCheck(input);
		
		if (market == null) {
			throw new EduException(MessageConstant.USERNAME_HAS_EXISTS);
		}
		
		output.setMarketCheck(market);
		
		return output;
		
	}
	
    /**
     * 营销质检数据导入
     *
     * @param input
     * @return
     */
	@Override
	public String importMarketCheckInfo(MarketCheckListInput input) {
		
		List<MarketCheck> list = new ArrayList<MarketCheck>();
		String result = "";
		
		// 录入内容校验
		for (MarketCheck marketCheck:input.getMarketCheckList()) {
			
			if (validateMarketCheckInfoForBatchInsert(marketCheck, input.getUserno())){
				list.add(marketCheck);
			}
		}
		
		if (list.size() > 0) {
			for (MarketCheck market:list) {
				market.setOperator("废弃");
				marketCheckMapper.insert(market);
			}
		}
		
		result = "导入成功条数："+ list.size() + "  失败条数:" + (input.getMarketCheckList().size() - list.size());
		
		return result;
		
	}
	
    /**
     * 营销质检批量录入内容校验
     *
     * @param input
     * @return
     * @throws EduException
     */
    private boolean validateMarketCheckInfoForBatchInsert(MarketCheck input, String userno) {
    	
    	boolean flg = true;
    	
    	String errMsg = "电话号码为" + input.getPhoneNo() + "的数据:";
    	
    	if (StringUtil.isEmpty(input.getMarketTime())) {
    		errMsg += "营销时间为空。";
    		flg = false;
    	} else if (input.getMarketTime().length() > 32) {
    		errMsg += "营销时间长度超出。";
    		flg = false;
    	} else if (!timeFormatValid(input.getMarketTime())) {
    		errMsg += "营销时间格式错误。";
    		flg = false;
    	}
    	
    	if (StringUtil.isEmpty(input.getPhoneNo())) {
    		errMsg += "电话号码为空。";
    		flg = false;
    	} else if (input.getPhoneNo().length() > 16) {
    		errMsg += "电话号码长度超出。";
    		flg = false;
    	}
    	
    	if (!StringUtil.isEmpty(input.getPhoneNo()) && !input.getPhoneNo().matches("^(((1([3,4,5,7,8][0-9]))\\d{8})|(0\\d{2}-?\\d{8})|(0\\d{3}-?\\d{7,8}))$")) {
    		errMsg += "电话号码格式有误。";
    		flg = false;
    	}
    	
    	if (StringUtil.isEmpty(input.getMarketNo())) {
    		errMsg += "营销工号为空。";
    		flg = false;
    	} else if (input.getMarketNo().length() > 12) {
    		errMsg += "营销工号长度超出。";
    		flg = false;
    	} else {
	    	// 营销工号校验
	    	SystemUserInfoExample example = new SystemUserInfoExample();
	    	SystemUserInfoExample.Criteria c = example.createCriteria();

	    	c.andUsernoEqualTo(input.getMarketNo());
	    	c.andOrgnoEqualTo("3");
	    	c.andDeletedEqualTo("NO");
	    	List<SystemUserInfo> list = userInfoMapper.selectByExample(example);

	    	// 营销工号不存在时，提示错误
	    	if (list == null || list.size() == 0) {
	    		flg = false;
	    		errMsg += "营销工号不存在。";
	    	}
    	}
    	
    	if (StringUtil.isEmpty(input.getCheckNo())) {
    		errMsg += "质检工号为空。";
    		flg = false;
    	} else if (input.getCheckNo().length() > 12) {
    		errMsg += "质检工号长度超出。";
    		flg = false;
    	} else {
	    	// 质检工号校验
	    	SystemUserInfoExample example = new SystemUserInfoExample();
	    	SystemUserInfoExample.Criteria c = example.createCriteria();

	    	c.andUsernoEqualTo(input.getCheckNo());
	    	c.andOrgnoEqualTo("3");
	    	c.andDeletedEqualTo("NO");
	    	List<SystemUserInfo> list = userInfoMapper.selectByExample(example);

	    	// 质检工号不存在时，提示错误
	    	if (list == null || list.size() == 0) {
	    		flg = false;
	    		errMsg += "质检工号不存在。";
	    	}
    	}
    	
    	if (StringUtil.isEmpty(input.getCheckResult())) {
    		errMsg += "质检结果为空。";
    		flg = false;
    	} else if (input.getCheckResult().length() > 8) {
    		errMsg += "质检结果长度超出。";
    		flg = false;
    	}
    	
    	if (StringUtil.isEmpty(input.getSampling())) {
    		errMsg += "抽检人为空。";
    		flg = false;
    	} else if (input.getSampling().length() > 12) {
    		errMsg += "抽检人长度超出。";
    		flg = false;
    	}
    	
    	if (StringUtil.isEmpty(input.getBusiness())) {
    		errMsg += "业务为空。";
    		flg = false;
    	} else if (input.getBusiness().length() > 32) {
    		errMsg += "抽检人长度超出。";
    		flg = false;
    	}
    	
    	if (StringUtil.isEmpty(input.getMarketWay())) {
    		errMsg += "推广方式为空。";
    		flg = false;
    	} else if (input.getMarketWay().length() > 16) {
    		errMsg += "推广方式长度超出。";
    		flg = false;
    	}
    	
    	if (StringUtil.isEmpty(input.getCalledTeam())) {
    		errMsg += "外呼团队为空。";
    		flg = false;
    	} else if  (input.getCalledTeam().length() > 32) {
    		errMsg += "外呼团队长度超出。";
    		flg = false;
    	}
    	
    	MarketCheckExample example = new MarketCheckExample();
    	MarketCheckExample.Criteria c = example.createCriteria();
    	
    	if (!StringUtil.isEmpty(input.getPhoneNo())) {
        	c.andPhoneNoEqualTo(input.getPhoneNo());
        	List<MarketCheck> list = marketCheckMapper.selectByExample(example);
        	
        	// 登陆的电话号码已存在时，提示号码已存在
        	if (list != null && list.size() > 0) {
        		errMsg += "登陆的电话号码已存在。";
        		flg = false;
        	}
    	}
    	

    	BasicConfigInput basicConfigInput = new BasicConfigInput();
    	
    	if (!StringUtil.isEmpty(input.getBusiness())) {
        	// 业务列表取得
        	basicConfigInput.setMoudlecode(BasicConfigConstant.BIZ_NAME);
        	basicConfigInput.setUserno(userno);
        	List<SystemBasicConfig> businessList =  basicConfigService.queryListByMoudleCode(basicConfigInput);
        	
        	boolean businessFlg = false;
        	
        	// 校验业务名称，存在时替换为业务code 不存在时提示业务名称错误
        	for (SystemBasicConfig business:businessList) {
        		if (business.getName().equals(input.getBusiness())) {
        			input.setBusiness(business.getCode());
        			businessFlg = true;
        			break;
        		}
        	}
        	if (!businessFlg) {
        		errMsg += "业务名称不存在。";
        		flg = false;
        	}
    	}
    	
    	if (!StringUtil.isEmpty(input.getCalledTeam())) {
        	// 外呼团队列表取得
        	basicConfigInput.setMoudlecode(BasicConfigConstant.OUT_CALL_TEA);
        	List<SystemBasicConfig> calledTeamList =  basicConfigService.queryListByMoudleCode(basicConfigInput);
        	
        	boolean calledTeamFlg = false;
        	
        	// 校验外呼团队，存在时替换为业务code 不存在时提示外呼团队错误
        	for (SystemBasicConfig calledTeam:calledTeamList) {
        		if (calledTeam.getName().equals(input.getCalledTeam())) {
        			input.setCalledTeam(calledTeam.getCode());
        			calledTeamFlg = true;
        			break;
        		}
        	}
        	if (!calledTeamFlg) {
        		errMsg += "外呼团队不存在。";
        		flg = false;
        	}
    	}
    	
    	if (!StringUtil.isEmpty(input.getCheckResult())) {
        	// 质检结果列表取得
        	basicConfigInput.setMoudlecode(BasicConfigConstant.CHECK_RESULT);
        	List<SystemBasicConfig> checkList =  basicConfigService.queryListByMoudleCode(basicConfigInput);
        	
        	boolean checkFlg = false;
        	
        	// 质检结果名称，存在时替换为业务code 不存在时提示质检结果错误
        	for (SystemBasicConfig check:checkList) {
        		if (check.getName().equals(input.getCheckResult())) {
        			input.setCheckResult(check.getCode());
        			checkFlg = true;
        			break;
        		}
        	}
        	if (!checkFlg) {
        		errMsg += "质检结果不存在。";
        		flg = false;
        	}
    	}
    	
    	// 数据验证失败时，插入导入失败记录表
    	if (!flg) {
    		ImportFailedRecord record = new ImportFailedRecord();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		record.setMenuno("d3e09aef-79f6-4855-ba7e-82a336925688");
    		record.setMenuname("营销质检管理");
    		record.setFuncname("数据导入");
    		record.setImportTime(df.format(new Date()));
    		record.setFailedRemark(errMsg);
    		record.setUserno(userno);
    		
    		importMapper.insert(record);
    	}
    	
    	return flg;
    	
    }
	
    /**
     * 营销质检登陆内容校验
     *
     * @param input
     * @return
     * @throws EduException
     */
    private void validateMarketCheckInfoForInsert(MarketCheckInput input) {
    	
    	MarketCheckExample example = new MarketCheckExample();
    	MarketCheckExample.Criteria c = example.createCriteria();
    	
    	c.andPhoneNoEqualTo(input.getPhoneNo());
    	List<MarketCheck> list = marketCheckMapper.selectByExample(example);
    	
    	// 登陆的电话号码已存在时，提示号码已存在
    	if (list != null && list.size() > 0) {
    		throw new EduException(MessageConstant.PHONENO_HAS_EXISTS);
    	}
    }
    
    /**
     * 营销质检修改内容校验
     *
     * @param input
     * @return
     * @throws EduException
     */
    private void validateMarketCheckInfoForUpdate(MarketCheckInput input) throws EduException {
    	
    	MarketCheckExample example = new MarketCheckExample();
    	MarketCheckExample.Criteria c = example.createCriteria();
    	
    	c.andPhoneNoEqualTo(input.getPhoneNo());
    	List<MarketCheck> list = marketCheckMapper.selectByExample(example);
    	
    	// 修改的电话号码不存在时，提示号码不存在
    	if (list == null && list.size() < 1) {
    		throw new EduException(MessageConstant.PHONENO_NOT_EXISTS);
    	}
    }
	
	/**
	 * 录音下载
	 * 
	 * @param input 录音查询参数
	 * @return 录音列表
	 */
	public void downloadVoice(HttpServletResponse response, MarketCheckQueryInput input) {
		//1. 根据录音编号，从user_file表中 检查录音是否存在
		CalledVoiceExample example = new CalledVoiceExample();
		example.createCriteria().andMakretPhoneEqualTo(input.getPhoneNo());
		example.createCriteria().andMarketTimeEqualTo(input.getMarketTime().replace("-", ""));
		example.createCriteria().andCalledTeamEqualTo(input.getCalledTeam());
		example.createCriteria().andBusinessEqualTo(input.getBusiness());
		List<CalledVoice> vo = calledVoiceMapper.selectByExample(example);
		if (vo.size() < 1) {
			throw new EduException(MessageConstant.VOICE_FILE_NOT_EXIST);
		}
		
//		//设置省份编号(公证处后台使用，避免请求未传省份编号)
//		input.setProvinceCode(StringUtil.isBlank(input.getProvinceCode())
//				|| "ALL".equals(input.getProvinceCode()) ? file.getRpcode()
//				: input.getProvinceCode());
//		
//		//2. 从user_voice_info表中获取备案号
//		UserVoiceInfoExample example = new UserVoiceInfoExample();
//		example.createCriteria().andRecordnoEqualTo(input.getFileno());
//		List<UserVoiceInfo> list = userVoiceInfoMapper.selectByExample(example);
//		if(null == list || list.size() > 1){
//			throw new EduException(BussinessConstants.VOICE_FILE_NOT_FOUND);
//		}
//		String licenceNo = list.get(0).getLicenceno();
		
		//3. 从oss获取录音
		fileDownFromOss(input.getProvinceCode(), vo.get(0));
		//4. 解密录音
		decryptFile(input.getProvinceCode(), vo.get(0));
		//5. 返回录音文件流
		responseFile(response, input.getProvinceCode(), vo.get(0));
		//6. 插入用户录音日志表
	}
	
	private static final String ENCRYPTED_NAME = "encrypted_";
	/**
	 * 从oss下载数据
	 * @param rpcode
	 * @param userFile
	 */
	private void fileDownFromOss(String rpcode, CalledVoice userFile){
		//oss accessId、 accessKey、bucket
		String accessId = ossAccessId;
		String accessKey = ossAccessKey;
		String bucket = bucketName;
		//文件下载临时文件
		String tempPath = tempPathName;
		
		//文件保存后路径
		String tempfile = tempPath + "/" + ENCRYPTED_NAME + userFile.getVoiceFileName();
		
		// 生成文件
        File file = new File(tempfile);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
		
		//从oss中下载
		AliyunOSS oss = new AliyunOSS(accessId, accessKey);
		oss.getObjectToFile(bucket, userFile.getStorePath(), tempfile);
		
		//检查加密文件的md5值
//		String fileMd5 = MD5Utils.md5file(tempfile);
//		if(!fileMd5.equalsIgnoreCase(userFile.getPfCloudFileMd5())){
//			throw new EduException(BussinessConstants.FILE_FROM_OSS_MD5_NOT_VALID);
//		}
	}
	
	/**
	 * 解密文件
	 * @param rpcode
	 * @param userFile
	 */
	private void decryptFile(String rpcode, CalledVoice userFile){
		//文件下载临时文件
		String tempFile = tempPathName;
		//文件保存后路径
		String sourceFilePath = tempFile + "/" + ENCRYPTED_NAME + userFile.getVoiceFileName();
		String destFilePath = tempFile + "/" + userFile.getVoiceFileName();
		
		//
//		Long deleteTailLen = 0l;
//		deleteTailLen = userFile.getPfCloudFileSize() - userFile.getPfFileSize();
		
		//解密文件
		try {
	        AESUtils.decryptFile(FileKey.FILE_KEY, sourceFilePath, destFilePath, 0);
        } catch (Exception e) {
//	        throw new EduException(BussinessConstants.FILE_DECRYPT_FAILED, e.getMessage(), e);
        	throw new EduException();
        }
		//检查解密后的文件md5值
//		String fileMd5 = MD5Utils.md5file(destFilePath);
//		if(!fileMd5.equalsIgnoreCase(userFile.getPfFileMd5())){
////			throw new EduException(BussinessConstants.FILE_MD5_NOT_VALID);
//			throw new EduException();
//		}
	}
	
	/**
	 * 回传文件
	 * @param response
	 * @param rpcode
	 * @param licenceNo
	 * @param userFile
	 */
	private void responseFile(HttpServletResponse response, String rpcode, CalledVoice userFile){
		//文件下载临时文件
		String tempFile = tempPathName;
		String destFilePath = tempFile + "/" + userFile.getVoiceFileName();
		String sourceFilePath = tempFile + "/" + ENCRYPTED_NAME + userFile.getVoiceFileName();
		String fileName = userFile.getVoiceFileName();
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Encoding", "UTF-8");
//		response.setContentLength(userFile.getPfFileSize().intValue());
//		response.setHeader("filemd5", userFile.getPfFileMd5());
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		InputStream inputStream = null;
		OutputStream outputstream = null;
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		//输出流buffer
		int BUFFERSIZE = 8196;
		try {
			outputstream = response.getOutputStream();
			bufferedOutputStream = new BufferedOutputStream(outputstream, BUFFERSIZE);
			
			inputStream = new FileInputStream(destFilePath);
			bufferedInputStream = new BufferedInputStream(inputStream);
			byte[] buffer = new byte[BUFFERSIZE];
			int count = 0;
			while ((count = bufferedInputStream.read(buffer)) != -1) { // >0
				bufferedOutputStream.write(buffer, 0, count);
			}
			buffer = null;
			StreamUtils.flush(bufferedOutputStream);
			response.flushBuffer();
		} catch (Exception e) {
			throw new EduException(250012);
		} finally {
			StreamUtils.closeBufferedOutputStream(bufferedOutputStream);
			StreamUtils.closeOutputStream(outputstream);
			StreamUtils.closeBufferedInputStream(bufferedInputStream);
			StreamUtils.closeInputStream(inputStream);
			FileUtils.deleteFile(destFilePath);
			FileUtils.deleteFile(sourceFilePath);
		}
	}
	
	/**
	 * 时间格式校验
	 * 
	 */
	private boolean timeFormatValid(String time){
		boolean result = true;
		
		try{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	        dateFormat.parse(time); 
		} catch(Exception e){
			result = false;
		}
		
		return result;
	}

}
