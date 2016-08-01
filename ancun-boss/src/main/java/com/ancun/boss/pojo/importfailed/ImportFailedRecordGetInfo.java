package com.ancun.boss.pojo.importfailed;

import com.ancun.boss.pojo.BossBasePojo;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 返回实体
 * @author cys1993
 *
 */
 @XStreamAlias(value="importfailedrecordinfo")
 @JsonRootName(value="importfailedrecordinfo")
public class ImportFailedRecordGetInfo extends BossBasePojo{
	 private Long id;
	 private String menuno;
	 private String menuname;
	 private String funcname;
	 private String importTime;
	 private String failedRemark;
	 private String userno;
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMenuno() {
		return menuno;
	}
	public void setMenuno(String menuno) {
		this.menuno = menuno;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getFuncname() {
		return funcname;
	}
	public void setFuncname(String funcname) {
		this.funcname = funcname;
	}
	public String getImportTime() {
		return importTime;
	}
	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}
	public String getFailedRemark() {
		return failedRemark;
	}
	public void setFailedRemark(String failedRemark) {
		this.failedRemark = failedRemark;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	
	
	 

}
