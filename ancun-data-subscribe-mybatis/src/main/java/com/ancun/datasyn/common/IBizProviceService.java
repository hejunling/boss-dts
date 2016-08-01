package com.ancun.datasyn.common;

import com.ancun.common.persistence.model.master.BizProvice;

import java.util.List;
import java.util.Map;

/**
 * 基础数据 缓存
 *
 * @author zkai
 * @version 1.0
 * @Created on 2016/05/16
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IBizProviceService {

    /**
     * 得到bizprovicemap
     * @return
     * @throws
     */
    public Map<String,String> getBizProviceMap();
}
