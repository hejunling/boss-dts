package com.ancun.boss.service.system;

import com.ancun.boss.persistence.model.DataDic;
import com.ancun.boss.persistence.model.SystemBasicConfig;
import com.ancun.core.exception.EduException;

import java.util.List;

/**
 * 基础数据 缓存
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/10/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface ICacheConfigService {
    /**
     * 查询所有基础数据 放于缓存中
     *
     * @return
     * @throws EduException
     */
    public List<SystemBasicConfig> queryAllBasicConfigs() throws EduException;

    /**
     * add by zkai on 2016/04/22
     * 查询所有datadic数据 并加入缓存
     * @return
     * @throws EduException
     */
    public List<DataDic> queryAllDateDic();
}
