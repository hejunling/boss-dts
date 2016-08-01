package com.ancun.boss.persistence.biz;

import java.util.List;
import com.ancun.boss.pojo.importfailed.ImportFailedRecordGetInfo;
import com.ancun.boss.pojo.importfailed.ImportFailedRecordGetInput;

/**
 * 导入失败列表查询
 * @author cys1993
 *
 */
public interface BizImportFailedRecordMapper {
	/**
	 * 列表
	 */
	List<ImportFailedRecordGetInfo> queryImportFailedList(ImportFailedRecordGetInput input);

}
