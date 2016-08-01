package com.ancun.boss.service.inOutNetCount;

import com.ancun.boss.pojo.inOutNetCount.InOutNetCountOutput;
import com.ancun.boss.pojo.inOutNetCount.InOutNetQueryInput;

/**
 * 在网离网统计接口
 *
 * @Created on 2015年11月11日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface InOutNetCountService {
	
    /**
     * 在网离网统计数据查询
     *
     * @param input   查询条件
     * @return          统计数据
     */
	public InOutNetCountOutput getInOutNetData(InOutNetQueryInput input);
}
