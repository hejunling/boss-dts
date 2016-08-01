package com.ancun.boss.service.customer.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.mapper.BizCancelRecordMapper;
import com.ancun.boss.persistence.mapper.CallInRecordMapper;
import com.ancun.boss.persistence.mapper.CancelRecordMapper;
import com.ancun.boss.persistence.model.CallInRecord;
import com.ancun.boss.persistence.model.CallInRecordExample;
import com.ancun.boss.persistence.model.CancelRecordExample;
import com.ancun.boss.pojo.callInfo.CancelRecordBusinessOutput;
import com.ancun.boss.pojo.callInfo.CancelRecordInput;
import com.ancun.boss.pojo.callInfo.CancelRecordOutput;
import com.ancun.boss.service.customer.ICancelRecordService;
import com.ancun.core.exception.EduException;
import com.ancun.utils.PhoneCallCheck;
/**
 * 投诉退订业务实现类
 * @author cys1993
 *
 */
@Service
public class CancelRecordServiceImpl implements ICancelRecordService{
	
	@Resource
	private CancelRecordMapper cancelRecordMapper;
	
	@Resource
	private CallInRecordMapper callInRecordMapper;
	
	@Resource
	private BizCancelRecordMapper bizCancelRecordMapper;

	@Override
	public CancelRecordOutput queryCancelRecordList(CancelRecordInput input)
			throws EduException {
		
		List<CancelRecordBusinessOutput> cancelrecordlist = bizCancelRecordMapper.queryCancelRecordList(input);

		CancelRecordOutput output = new CancelRecordOutput();
        output.setCancelrecordlist(cancelrecordlist);
        output.setPageinfo(input.getPage());
        return output;
	}
	/**
	 * 来电号码格式验证
	 * @param phone
	 * @throws EduException
	 */
	public static void checkPhoneAll(String phone) throws EduException {

		if( !PhoneCallCheck.checkPhoneWithoutHyphen(phone) && !PhoneCallCheck.checkMobile(phone)) {
    		throw new EduException(MessageConstant.PHONE_FORMAT_ILLEGAL);
		}
	}

	/**
	 * 详细查询
	 */
	@Override
	public CancelRecordBusinessOutput queryCancelRecordInfo(Long id)
			throws EduException {
		CancelRecordBusinessOutput cancelrecord=bizCancelRecordMapper.selectByPrimaryKey(id);
		CancelRecordBusinessOutput output = new CancelRecordBusinessOutput();
        try {
            PropertyUtils.copyProperties(output, cancelrecord);
        } catch (Exception e) {
            throw new EduException(MessageConstant.QUERY_CANCEL_RECORD_FAILED);
        }
        return output;
	}

	/**
	 * 新增修改
	 */
	@Override
	public void addOrUpdateCancelInfo(CancelRecordInput input)
			throws EduException {
		
		// 为空校验
        validateCancelInfo(input);
        //电话号码格式校验
      	checkPhoneAll(input.getCallphone());
		CancelRecordBusinessOutput cancelrecord = new CancelRecordBusinessOutput();
		CallInRecord callinrecord = new CallInRecord();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		cancelrecord.setBusiness(input.getBusiness());
		cancelrecord.setUserNo(input.getUserno());

		cancelrecord.setAdjustMoney(input.getAdjustMoney());
		cancelrecord.setAdjustMonths(input.getAdjustMonths());
		cancelrecord.setCallphone(input.getCallphone());
		cancelrecord.setCallTime(input.getCallTime());
		cancelrecord.setCancelReqire(input.getCancelReqire());
		cancelrecord.setCancelTime(input.getCancelTime());
		cancelrecord.setName(input.getName());
		cancelrecord.setOpenTime(input.getOpenTime());
		cancelrecord.setOrderNo(input.getOrderNo());
		cancelrecord.setRefundMoney(input.getRefundMoney());
		cancelrecord.setRefundTime(input.getRefundTime());
		cancelrecord.setRemark(input.getRemark());
		cancelrecord.setSex(input.getSex());
		cancelrecord.setSource(input.getSource());
		cancelrecord.setVoice(input.getVoice());
		cancelrecord.setBusinessName(input.getBusinessName());
		
		callinrecord.setBusiness(cancelrecord.getBusiness());
		callinrecord.setUserNo(cancelrecord.getUserNo());
		callinrecord.setCallphone(cancelrecord.getCallphone());
		callinrecord.setCalltime(simpleDateFormat.format(cancelrecord.getCallTime()));
		callinrecord.setName(cancelrecord.getName());
		callinrecord.setRemark(cancelrecord.getRemark());
		callinrecord.setSex(cancelrecord.getSex());
		callinrecord.setCreateTime(cancelrecord.getCancelTime());
		callinrecord.setCallType(BizRequestConstant.CANCEL_RECORD);
		
		if(input.getModifyflag().equals(BizRequestConstant.TO_ADD)){
//			cancelrecord.setId(input.getId());
			cancelrecord.setUserName(input.getUserName());
			cancelrecord.setCreateTime(new Date());
			bizCancelRecordMapper.insertSelective(cancelrecord);			
			callinrecord.setCancelId(cancelrecord.getId());		//投诉退订的id主键
			//投诉退订表新增的同时，呼入登记同时进行新增操作
			callInRecordMapper.insertSelective(callinrecord);
		} else {
			//根据是否存在cancelId(存在 :呼入登记表进行更新操作，不存在进行插入操作)
			if(input.getCancelId() == null && input.getCallInId() != null) {
	            cancelrecord.setCreateTime(new Date());
				bizCancelRecordMapper.insertSelective(cancelrecord);			
				callinrecord.setCancelId(cancelrecord.getId());		//投诉退订的id主键
				
				//投诉退订表新增的同时，呼入登记同时进行修改操作
	            CallInRecordExample callInRecordExample = new CallInRecordExample();
	            CallInRecordExample.Criteria criteria = callInRecordExample.createCriteria();
	            criteria.andIdEqualTo(input.getCallInId());
	            callInRecordMapper.updateByExampleSelective(callinrecord, callInRecordExample);
			} else {
				CancelRecordExample example = new CancelRecordExample();
	        	CancelRecordExample.Criteria c = example.createCriteria();
	        	if(input.getCancelId() != null){
		            c.andIdEqualTo(input.getCancelId());
	        	} else {
	        		c.andIdEqualTo(input.getId());
	        	}

	            bizCancelRecordMapper.updateByExampleSelective(cancelrecord, example);
	            
	            //投诉退订表新增的同时，呼入登记同时进行修改操作
	            CallInRecordExample callInRecordExample = new CallInRecordExample();
	            CallInRecordExample.Criteria criteria = callInRecordExample.createCriteria();
	        	if(input.getCallInId() != null){
	        		criteria.andIdEqualTo(input.getCallInId());
	        	} else {
	        		criteria.andCancelIdEqualTo(input.getId());
	        	}
	            callInRecordMapper.updateByExampleSelective(callinrecord, callInRecordExample);		
			}

		}
	}
	
	/**
	 * 校验内容
	 */
	private void validateCancelInfo(CancelRecordInput input){
		//电话号码不能为空
		if(StringUtils.isBlank(input.getCallphone())){
			throw new EduException(MessageConstant.PHONENO_MUST_BE_NOT_EMPTY);
		}
		//来电时间不能为空
		if(input.getCallTime()==null){
			throw new EduException(MessageConstant.CALL_TIME_MUST_BE_NOT_EMPTY);
		}
		//称呼不能为空
		if(StringUtils.isBlank(input.getName())){
			throw new EduException(MessageConstant.NAME_MUST_BE_NOT_EMPTY);
		}
		//性别不能为空
		if(StringUtils.isBlank(input.getSex())){
			throw new EduException(MessageConstant.SEX_MUST_BE_NOT_EMPTY);
		}
		//业务名称不能为空
		if(StringUtils.isBlank(input.getBusiness())){
			throw new EduException(MessageConstant.MARKETCHECK_BUSINESS_MUST_BE_NOT_EMPTY);
		}
		//有无录音不能为空
		if(StringUtils.isBlank(input.getVoice())){
			throw new EduException(MessageConstant.VOICE_MUST_BE_NOT_EMPTY);
		}
	}

	/**
	 * 投诉退订删除
	 */
	@Override
	public void deleteCancelInfo(Long id) throws EduException {

        cancelRecordMapper.deleteByPrimaryKey(id);
        
        //如果是投诉退订，也对投诉退订相应的数据进行删除
        CallInRecordExample example = new CallInRecordExample();
        CallInRecordExample.Criteria criteria = example.createCriteria();
        criteria.andCancelIdEqualTo(id);
        callInRecordMapper.deleteByExample(example);
	}

}