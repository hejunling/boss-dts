package com.ancun.boss.pojo.businessKnowledge;

import com.ancun.boss.pojo.BossBasePojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlRootElement(name = "content")
public class BusinessKnowledgeDetailFaqInput extends BossBasePojo {

    List<BusinessKnowledgeGetInput> inputs;

    public List<BusinessKnowledgeGetInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<BusinessKnowledgeGetInput> inputs) {
        this.inputs = inputs;
    }

}
