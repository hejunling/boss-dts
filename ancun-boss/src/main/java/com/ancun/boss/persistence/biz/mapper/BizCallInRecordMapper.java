package com.ancun.boss.persistence.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.boss.persistence.model.CallInRecord;
import com.ancun.boss.persistence.model.CallInRecordExample;
import com.ancun.boss.pojo.callInfo.CallInRecordBusinessOutput;
import com.ancun.boss.pojo.callInfo.CallInRecordInput;

/**
 * 呼入登记查询
 *
 * @Created on 2015年10月29日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizCallInRecordMapper {
	
	/**
	 * 呼入登记列表查询
	 * @param input
	 * @return
	 */
	List<CallInRecordBusinessOutput> queryCallInRecordList(CallInRecordInput input);
	
	/**
	 * 呼入登记详情
	 * @param id
	 * @return
	 */
	CallInRecordBusinessOutput queryCallInRecordinfo(Long id);
	
	/**
	 * 呼入登记修改(允许cancelId修改为null)
	 * @param record
	 * @param example
	 * @return
	 */
	int updateCallInRecordinfo(@Param("record") CallInRecord record, @Param("example") CallInRecordExample example);
}
