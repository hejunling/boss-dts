package com.ancun.boss.pojo.businessKnowledge;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 返回实体POJO
 *
 * @author cys1993
 */
@XStreamAlias(value = "businessinfo")
@JsonRootName(value = "businessinfo")
public class BusinessKnowledgeGetInfo {

    private Long id;
    private String business;
    private String domain;
    private String updatetime;
    private String bizservicer;
    private String bizintroduce;
    private String icon;
    private String question;
    private String answer;
    private String remark;
    private String businessName;
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBizintroduce() {
        return bizintroduce;
    }

    public void setBizintroduce(String bizintroduce) {
        this.bizintroduce = bizintroduce;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getBizservicer() {
        return bizservicer;
    }

    public void setBizservicer(String bizservicer) {
        this.bizservicer = bizservicer;
    }


}
