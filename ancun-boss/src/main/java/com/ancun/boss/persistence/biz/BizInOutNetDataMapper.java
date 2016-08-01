package com.ancun.boss.persistence.biz;

import java.util.List;

import com.ancun.boss.pojo.inOutNetCount.InOutData;
import com.ancun.boss.pojo.inOutNetCount.InOutNetQueryInput;

/**
 * 在网离网数据查询
 *
 * @author chenb
 * @version 1.0
 * @Created on 2015/11/09
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizInOutNetDataMapper {
	
    /**
     * 在网数据查询
     *
     * @param input
     * @return
     */
	public List<InOutData> queryInBarData(InOutNetQueryInput input);
	
    /**
     * 离网数据查询
     *
     * @param input
     * @return
     */
	public List<InOutData> queryOutBarData(InOutNetQueryInput input);
}
