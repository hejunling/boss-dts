package com.ancun.boss.pojo.businessKnowledge;

import com.ancun.boss.pojo.BossPagePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class FaqDetailOutput extends BossPagePojo {

    private BusinessKnowledgeGetInfo detailinfo;

    private FaqGetInfo faqinfo;
    
    public BusinessKnowledgeGetInfo getDetailinfo() {
        return detailinfo;
    }

    public void setDetailinfo(BusinessKnowledgeGetInfo detailinfo) {
        this.detailinfo = detailinfo;
    }

	public FaqGetInfo getFaqinfo() {
		return faqinfo;
	}

	public void setFaqinfo(FaqGetInfo faqinfo) {
		this.faqinfo = faqinfo;
	}

}
