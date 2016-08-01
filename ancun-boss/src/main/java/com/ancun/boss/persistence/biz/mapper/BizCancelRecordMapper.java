package com.ancun.boss.persistence.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.ancun.boss.persistence.model.CancelRecordExample;
import com.ancun.boss.pojo.callInfo.CancelRecordBusinessOutput;
import com.ancun.boss.pojo.callInfo.CancelRecordInput;

public interface BizCancelRecordMapper {
	/**
	 * 列表
	 */
	List<CancelRecordBusinessOutput> queryCancelRecordList(CancelRecordInput input);
	
	/**
	 * 详细
	 */
	CancelRecordBusinessOutput selectByPrimaryKey(Long id);
	
	/**
	 * 新增
	 */
	void insertSelective(CancelRecordBusinessOutput cancelrecord);
	
	/**
	 * 修改
	 */
	void updateByExampleSelective(@Param("record") CancelRecordBusinessOutput record, @Param("example") CancelRecordExample example);

}
