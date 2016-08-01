package com.ancun.datasyn.cache.impl;

import com.ancun.common.persistence.mapper.master.BizProviceMapper;
import com.ancun.common.persistence.model.master.BizProvice;
import com.ancun.datasyn.cache.ICacheConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 基础数据缓存实现
 * @author zkai
 * @version 1.0
 * @Created on 2016/05/16
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Configuration
// 标注启动了缓存
@EnableCaching
@Service
public class CacheConfigServiceImpl implements ICacheConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ICacheConfigService.class);


    @Resource
    private BizProviceMapper bizProviceMapper;


    /**
     * 缓存所有业务编号对应关系信息
     * @return
     */
    @Cacheable(value = "bizProviceListAllCache")
    @Override
    public List<BizProvice> queryAllBizProvice() {
        logger.info("------------------->加载一次!");
        return bizProviceMapper.selectAll();
    }
}
