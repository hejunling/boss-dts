package com.ancun.boss.service.customer.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.mapper.BizCallInRecordMapper;
import com.ancun.boss.persistence.mapper.CallInRecordMapper;
import com.ancun.boss.persistence.mapper.CancelRecordMapper;
import com.ancun.boss.persistence.mapper.SystemBasicConfigMapper;
import com.ancun.boss.persistence.model.CallInRecord;
import com.ancun.boss.persistence.model.CallInRecordExample;
import com.ancun.boss.pojo.callInfo.CallInRecordBusinessOutput;
import com.ancun.boss.pojo.callInfo.CallInRecordInput;
import com.ancun.boss.pojo.callInfo.CallInRecordOutput;
import com.ancun.boss.service.customer.ICallInRecordService;
import com.ancun.core.exception.EduException;
import com.ancun.utils.PhoneCallCheck;

/**
 * 呼入登记业务实现类
 * @author cys
 *
 */
@Service
public class CallInRecordServiceImpl implements ICallInRecordService{
	
	@Resource
	private CallInRecordMapper callInRecordMapper;
	
	@Resource
	private CancelRecordMapper cancelRecordMapper;
	
	@Resource
	private SystemBasicConfigMapper systemBasicConfigMapper;
	
	@Resource
	private BizCallInRecordMapper bizCallInRecordMapper;
	
	/**
	 * 查询呼入登记列表
	 */
	@Override
	public CallInRecordOutput queryCallInRecordList(CallInRecordInput input)
			throws EduException {
	
        List<CallInRecordBusinessOutput> list = bizCallInRecordMapper.queryCallInRecordList(input);
        	             
        CallInRecordOutput output = new CallInRecordOutput();
        output.setCallinrecordlist(list);
        output.setPageinfo(input.getPage());

        return output;       
	}
	
	/**
	 * 呼入登记管理根据id查询用户信息
	 */
	@Override
	public CallInRecordBusinessOutput queryCallInRecordInfo(Long id)
			throws EduException {    
		
		CallInRecordBusinessOutput output = bizCallInRecordMapper.queryCallInRecordinfo(id);
        return output;
	}
	
	/**
	 * 呼入登记管理新增修改
	 */
	@Override
	public void addOrUpdateCallInfo(CallInRecordInput input)
			throws EduException {
		validateCallInfo(input);
        //电话号码格式校验
      	checkPhoneAll(input.getCallphone());
		CallInRecord callinrecord = new CallInRecord();
		
		callinrecord.setBusiness(input.getBusiness());
		callinrecord.setUserNo(input.getUserno());	
		callinrecord.setCallBack(input.getCallback());
		callinrecord.setCallphone(input.getCallphone());
		callinrecord.setCalltime(input.getCallTime());
		callinrecord.setDuration(input.getDuration());
		callinrecord.setName(input.getName());
		callinrecord.setQuestion(input.getQuestion());
		callinrecord.setRemark(input.getRemark());
		callinrecord.setSex(input.getSex());
		callinrecord.setVisitSituation(input.getVisitsituation());
		callinrecord.setCallType(input.getCallType());
				
        if (input.getModifyflag().equals(BizRequestConstant.TO_ADD)) {
            //新增
    		callinrecord.setUserName(input.getUsername());
        	callinrecord.setId(input.getId());
        	callinrecord.setCreateTime(new Date());
        	callInRecordMapper.insertSelective(callinrecord);

        } else {
        	CallInRecordExample example = new CallInRecordExample();
        	CallInRecordExample.Criteria c = example.createCriteria();
        	
            if (input.getCallType().equals(BizRequestConstant.CALL_IN_RECORD) && input.getCancelId() != null ) {
            	cancelRecordMapper.deleteByPrimaryKey(input.getCancelId());
            	callinrecord.setCheckId(null);
            }
                //修改
                c.andIdEqualTo(input.getId());
                bizCallInRecordMapper.updateCallInRecordinfo(callinrecord, example);
        }		
	}

	/**
	 * 呼入登记信息删除
	 */
	@Override
	public void deleteCallinInfo(CallInRecordInput input) throws EduException {

        callInRecordMapper.deleteByPrimaryKey(input.getId());
        
        //如果是投诉退订，也对投诉退订相应的数据进行删除
        if(input.getCancelId() != null) {
            	cancelRecordMapper.deleteByPrimaryKey(input.getCancelId());  
        }     
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
	 * 校验内容
	 */
	private void validateCallInfo(CallInRecordInput input){
		//电话号码不能为空
		if(StringUtils.isBlank(input.getCallphone())){
			throw new EduException(MessageConstant.PHONENO_MUST_BE_NOT_EMPTY);
		}
		//来电时间不能为空
		if(StringUtils.isBlank(input.getCallTime())){
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
	}

}
