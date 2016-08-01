package com.ancun.boss.controller.system;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.persistence.model.ImportFailedRecord;
import com.ancun.boss.pojo.importfailed.ImportFailedRecordGetInput;
import com.ancun.boss.pojo.importfailed.ImportFailedRecordGetOutput;
import com.ancun.boss.service.importfailed.ImportFailedRecordGetService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;

/**
 * 导入失败管理
 * @author cys1993
 *
 */
@RestController
public class ImportFailedRecordController extends BaseController{
	@Resource
	private ImportFailedRecordGetService importFailedRecordGetService;
	
	/**
	 * 导入失败列表查询
	 * @param input
	 * @return
	 * @throws EduException
	 */
	@AccessToken(access = AccessType.WEB,checkAccess=true)
	@RequestMapping(value = "/importFailedRecordGet", method = RequestMethod.POST)
	public RespBody<ImportFailedRecordGetOutput> importFailedRecordGet(ReqBody<ImportFailedRecordGetInput> input) throws EduException{
		ImportFailedRecordGetInput inputContent = input.getContent();
		ImportFailedRecordGetOutput output = importFailedRecordGetService.getImportFailedRecordGet(inputContent);
		ThreadLocalUtil.setContent("我查询了导入失败列表内容");
		return new RespBody<ImportFailedRecordGetOutput>(output);
	}
	
	/**
	 * 导入失败详情查询
	 * @param inputReqBody
	 * @return
	 * @throws EduException
	 */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/importFailedRecordDetail", method = RequestMethod.POST)
    public RespBody<ImportFailedRecord> queryImportFailedRecordInfo(ReqBody<ImportFailedRecordGetInput> inputReqBody) throws EduException {

    	ImportFailedRecordGetInput input = inputReqBody.getContent();
    	ImportFailedRecord importfailedrecord = (importFailedRecordGetService.queryImportFailedRecordInfo(input.getId()));
    	ThreadLocalUtil.setContent("我查询了导入失败详细内容");
        return new RespBody<ImportFailedRecord>(importfailedrecord);
    }

}
