package com.ancun.boss.pojo.importfailed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ancun.boss.pojo.BossPagePojo;
/**
 * 导入失败列表请求信息封装POJO类
 * @author cys1993
 *
 */
@XmlAccessorType(value=XmlAccessType.PROPERTY)
@XmlRootElement(name="content")
public class ImportFailedRecordGetInput extends BossPagePojo{
	
	/**
	 * 编号
	 */
	Long id;
	
	/**
	 * 菜单编号
	 */
	private String menuno;
	
	/**
	 * 菜单名称
	 */
	private String menuname;
	
	/**
	 * 功能名称
	 */
	private String funcname;
	
	/**
	 * 导入时间
	 */
	private String importTime;
	
	/**
	 * 错误原因
	 */
	private String failedRemark;
	
	/**
	 * 导入人工号
	 */
	private String userno;
	
	private String importTimeStart;
	
	private String importTimeEnd;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImportTimeStart() {
		return importTimeStart;
	}

	public void setImportTimeStart(String importTimeStart) {
		this.importTimeStart = importTimeStart;
	}

	public String getImportTimeEnd() {
		return importTimeEnd;
	}

	public void setImportTimeEnd(String importTimeEnd) {
		this.importTimeEnd = importTimeEnd;
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
