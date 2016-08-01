package com.ancun.boss.pojo.phoneResp;

import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.pojo.BossBasePojo;
import com.ancun.validate.annotation.RangeLength;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 号码池清洗input参数
 *
 * @Created on 2015年11月05日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class PhoneRepInput extends BossBasePojo {

    /** 业务类型 */
	@NotBlank(message= MessageConstant.BUSINESS_MUST_BE_NOT_EMPTY + "")
    private String business;

    /** 获取时间 */
	@NotBlank(message=MessageConstant.GET_TIME_MUST_BE_NOT_EMPTY + "")
    private String gettime;

    /** 号码列表 */
    private List<String> phones;

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getGettime() {
        return gettime;
    }

    public void setGettime(String gettime) {
        this.gettime = gettime;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
