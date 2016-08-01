package com.ancun.boss.pojo.userInfo;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossPagePojo;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用户信息请求封装POJO类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/18
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class UserInfoInput extends BossPagePojo {

    /**
     * 用户工号
     */
    @NotEmpty(message = MessageConstant.USER_NO_MUST_BE_NOT_EMPTY + "")
    private String jobno;


    /**
     * 用户名称
     */
    @NotEmpty(message = MessageConstant.USER_NAME_MUST_BE_NOT_EMPTY + "")
    private String username;

    /**
     * 部门
     */

    @NotEmpty(message = MessageConstant.ORG_MUST_BE_NOT_EMPTY + "")
    private String orgno;

    /**
     * 职务
     */
    private String job;


    /**
     * 邮箱地址
     */
    @NotEmpty(message = MessageConstant.EMAIL_MUST_BE_NOT_EMPTY + "")
    @Email(message = MessageConstant.EMAIL_IS_WRONG + "")
    private String email;

    /**
     * 联系电话
     */
    @NotEmpty(message = MessageConstant.CONTACT_PHONE_MUST_BE_NOT_EMPTY + "")
    private String contactphone;


    /**
     * 新增修改标志（1:新增；2:修改）
     */
    @NotEmpty(message = MessageConstant.MODIFY_FLAG_MUST_BE_NOT_EMPTY + "")
    private String modifyflag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据权限（YES/NO）
     */
    private String datapermission;

    /**
     * 数据权限ids（多个以”，“分隔）
     */
    private String basicnos;

    /**
     * 角色ids(多个以”，“分隔)
     */
    private String rolenos;

    public String getJobno() {
        return jobno;
    }

    public void setJobno(String jobno) {
        this.jobno = jobno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrgno() {
        return orgno;
    }

    public void setOrgno(String orgno) {
        this.orgno = orgno;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public String getModifyflag() {
        return modifyflag;
    }

    public void setModifyflag(String modifyflag) {
        this.modifyflag = modifyflag;
    }

    public String getDatapermission() {
        return datapermission;
    }

    public void setDatapermission(String datapermission) {
        this.datapermission = datapermission;
    }

    public String getBasicnos() {
        return basicnos;
    }

    public void setBasicnos(String basicnos) {
        this.basicnos = basicnos;
    }

    public String getRolenos() {
        return rolenos;
    }

    public void setRolenos(String rolenos) {
        this.rolenos = rolenos;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    private String ownerno;

	public String getOwnerno() {
		return ownerno;
	}

	public void setOwnerno(String ownerno) {
		this.ownerno = ownerno;
	}
    
}
