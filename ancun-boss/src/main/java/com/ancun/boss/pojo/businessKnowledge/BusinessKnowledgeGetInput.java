package com.ancun.boss.pojo.businessKnowledge;

import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 请求参数
 *
 * @author cys1993
 */

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BusinessKnowledgeGetInput extends BossPagePojo {

    private Long id;
    private String bizintroduce;
    private String icon;
    private String type;
    private String question;
    private String answer;
    private String remark;
    private String business;
    private String domain;
    private String updatetime;
    private String bizservicer;
    private String ordersort;
    /**
     * 新增修改标志（1:新增；2:修改）
     */
    private String modifyflag;


    public String getModifyflag() {
        return modifyflag;
    }

    public void setModifyflag(String modifyflag) {
        this.modifyflag = modifyflag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getOrdersort() {
        return ordersort;
    }

    public void setOrdersort(String ordersort) {
        this.ordersort = ordersort;
    }


}
