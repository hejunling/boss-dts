package com.ancun.boss.service.importfailed.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.BizImportFailedRecordMapper;
import com.ancun.boss.persistence.mapper.ImportFailedRecordMapper;
import com.ancun.boss.persistence.model.ImportFailedRecord;
import com.ancun.boss.pojo.importfailed.ImportFailedRecordGetInfo;
import com.ancun.boss.pojo.importfailed.ImportFailedRecordGetInput;
import com.ancun.boss.pojo.importfailed.ImportFailedRecordGetOutput;
import com.ancun.boss.service.importfailed.ImportFailedRecordGetService;
import com.ancun.core.exception.EduException;

/**
 * 导入失败列表实现类
 * @author cys1993
 *
 */

public class ImportFailedRecordGetServiceImpl implements ImportFailedRecordGetService{
	
	@Resource
	private BizImportFailedRecordMapper bizImportFailedRecordMapper;
	
	@Resource
	private ImportFailedRecordMapper importFailedRecordMapper;
	
	/**
	 * 查询
	 */
	@Override
	public ImportFailedRecordGetOutput getImportFailedRecordGet(
			ImportFailedRecordGetInput input) throws EduException {
		List<ImportFailedRecordGetInfo> importfailedrecordlist = bizImportFailedRecordMapper.queryImportFailedList(input);
		ImportFailedRecordGetOutput output = new ImportFailedRecordGetOutput();
        output.setImportfailedrecordlist(importfailedrecordlist);
        output.setPageinfo(input.getPage());
        return output;
	}
	
	/**
	 * 详情
	 */
	@Override
	public ImportFailedRecord queryImportFailedRecordInfo(Long id)
			throws EduException {
		ImportFailedRecord importfailedrecord=importFailedRecordMapper.selectByPrimaryKey(id);
		ImportFailedRecord output = new ImportFailedRecord();
        try {
            PropertyUtils.copyProperties(output, importfailedrecord);
        } catch (Exception e) {
            throw new EduException(MessageConstant.IMPORT_FAILED_RECORD_FAILED);
        }
        return output;
	}
	
	
	
	

}
