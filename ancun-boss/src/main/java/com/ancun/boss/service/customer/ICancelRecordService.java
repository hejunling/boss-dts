package com.ancun.boss.service.customer;

import com.ancun.boss.pojo.callInfo.CancelRecordBusinessOutput;
import com.ancun.boss.pojo.callInfo.CancelRecordInput;
import com.ancun.boss.pojo.callInfo.CancelRecordOutput;
import com.ancun.core.exception.EduException;

/**
 * 投诉退订管理
 * @author cys1993
 *
 */
public interface ICancelRecordService {

	/**
	 * 查询投诉退订列表
	 * @param input
	 * @return
	 */
	public CancelRecordOutput queryCancelRecordList(CancelRecordInput input) throws EduException;

	/**
	 * 投诉退订信息详细查询
	 * @param id
	 * @return
	 * @throws EduException
	 */
	public CancelRecordBusinessOutput queryCancelRecordInfo(Long id) throws EduException;

	/**
	 * 投诉退订新增修改
	 * @param input
	 * @throws EduException
	 */
	public void addOrUpdateCancelInfo(CancelRecordInput input) throws EduException;

	/**
	 * 投诉退订删除
	 * @param id
	 * @throws EduException
	 */
	public void deleteCancelInfo(Long id) throws EduException;

}
