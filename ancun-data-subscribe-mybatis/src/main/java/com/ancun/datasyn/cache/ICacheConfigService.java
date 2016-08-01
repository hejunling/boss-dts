package com.ancun.datasyn.cache;

import com.ancun.common.persistence.model.master.BizProvice;

import java.util.List;

/**
 * 基础数据 缓存
 *
 * @author zkai
 * @version 1.0
 * @Created on 2016/05/16
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICacheConfigService {

    /**
     * 查询所有biz-provice数据 并加入缓存
     * @return
     * @throws
     */
    public List<BizProvice> queryAllBizProvice();
}
