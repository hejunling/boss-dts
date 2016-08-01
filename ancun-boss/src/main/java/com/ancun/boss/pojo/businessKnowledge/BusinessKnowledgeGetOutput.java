package com.ancun.boss.pojo.businessKnowledge;

import com.ancun.boss.pojo.BossPagePojo;
import com.ancun.core.page.Page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BusinessKnowledgeGetOutput extends BossPagePojo {
    private Page pageinfo;
    private List<BusinessKnowledgeGetInfo> businesslist;


    public Page getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(Page pageinfo) {
        this.pageinfo = pageinfo;
    }

    public List<BusinessKnowledgeGetInfo> getBusinesslist() {
        return businesslist;
    }

    public void setBusinesslist(List<BusinessKnowledgeGetInfo> businesslist) {
        this.businesslist = businesslist;
    }

}
