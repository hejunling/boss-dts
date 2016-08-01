package com.ancun.boss.pojo.businessKnowledge;

import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BusinessKnowledgeDetailFaqOutput extends BossPagePojo {

    private BusinessKnowledgeGetInfo detailinfo;

    private List<BusinessKnowledgeGetInfo> faqlist;

    public BusinessKnowledgeGetInfo getDetailinfo() {
        return detailinfo;
    }

    public void setDetailinfo(BusinessKnowledgeGetInfo detailinfo) {
        this.detailinfo = detailinfo;
    }

    public List<BusinessKnowledgeGetInfo> getFaqlist() {
        return faqlist;
    }

    public void setFaqlist(List<BusinessKnowledgeGetInfo> faqlist) {
        this.faqlist = faqlist;
    }
}
