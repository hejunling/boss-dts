package com.ancun.boss.pojo.importfailed;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;
import com.ancun.core.page.Page;

/**
 * 导入失败列表返回封装类
 * @author cys1993
 *
 */
@XmlAccessorType(value=XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class ImportFailedRecordGetOutput extends BossPagePojo{
	
	/**
	 * 导入失败列表
	 */
	private List<ImportFailedRecordGetInfo> importfailedrecordlist;
	
	/**
	 * 分页信息
	 */
	private Page pageinfo;

	public List<ImportFailedRecordGetInfo> getImportfailedrecordlist() {
		return importfailedrecordlist;
	}

	public void setImportfailedrecordlist(
			List<ImportFailedRecordGetInfo> importfailedrecordlist) {
		this.importfailedrecordlist = importfailedrecordlist;
	}

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}
	
	

}
