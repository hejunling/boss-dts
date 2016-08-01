package com.ancun.boss.pojo.businessKnowledge;

import com.ancun.boss.pojo.BossPagePojo;
import com.ancun.core.page.Page;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

@XStreamAlias(value = "businessinfo")
@JsonRootName(value = "businessinfo")
public class FaqGetInfo extends BossPagePojo {

    private List<SystemFaqQuestionOutput> faqlist;
    
    private Page pageinfo;

	public List<SystemFaqQuestionOutput> getFaqlist() {
		return faqlist;
	}

	public void setFaqlist(List<SystemFaqQuestionOutput> faqlist) {
		this.faqlist = faqlist;
	}

	public Page getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Page pageinfo) {
		this.pageinfo = pageinfo;
	}
    
}
