package com.ancun.boss.service.customer;

import com.ancun.boss.pojo.callInfo.CallInRecordBusinessOutput;
import com.ancun.boss.pojo.callInfo.CallInRecordInput;
import com.ancun.boss.pojo.callInfo.CallInRecordOutput;
import com.ancun.core.exception.EduException;

/**
 * 呼入登记管理
 * 
 * @author cys1993
 *
 */
public interface ICallInRecordService {

	/**
	 * 查询呼入登记列表
	 * @param input
	 * @return
	 */
	public CallInRecordOutput queryCallInRecordList(CallInRecordInput input) throws EduException;

	/**
	 * 呼入登记详情信息
	 * @param userno
	 * @return
	 */
	public CallInRecordBusinessOutput queryCallInRecordInfo(Long id) throws EduException;

	/**
	 * 呼入登记信息新增修改
	 * @param content
	 */
	public void addOrUpdateCallInfo(CallInRecordInput input) throws EduException;


	/**
	 * 呼入登记信息删除
	 * @param id
	 * @throws EduException
	 */
	public void deleteCallinInfo(CallInRecordInput input) throws EduException;

}
