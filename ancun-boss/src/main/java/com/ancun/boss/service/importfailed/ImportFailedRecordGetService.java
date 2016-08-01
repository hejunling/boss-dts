package com.ancun.boss.service.importfailed;

import com.ancun.boss.persistence.model.ImportFailedRecord;
import com.ancun.boss.pojo.importfailed.ImportFailedRecordGetInput;
import com.ancun.boss.pojo.importfailed.ImportFailedRecordGetOutput;
import com.ancun.core.exception.EduException;
/**
 * 导入失败列表查询
 * @author cys1993
 *
 */
public interface ImportFailedRecordGetService {

	/**
	 * 导入失败列表查询
	 * @param inputContent
	 * @return
	 */
	public ImportFailedRecordGetOutput getImportFailedRecordGet(
			ImportFailedRecordGetInput input) throws EduException;
	
	/**
	 * 导入失败详情查询
	 * @param id
	 * @return
	 * @throws EduException
	 */
	public ImportFailedRecord queryImportFailedRecordInfo(Long id) throws EduException;

}
