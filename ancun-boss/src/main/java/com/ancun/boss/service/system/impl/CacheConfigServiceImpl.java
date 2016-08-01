package com.ancun.boss.service.system.impl;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.persistence.mapper.DataDicMapper;
import com.ancun.boss.persistence.mapper.SystemBasicConfigMapper;
import com.ancun.boss.persistence.model.DataDic;
import com.ancun.boss.persistence.model.DataDicExample;
import com.ancun.boss.persistence.model.SystemBasicConfig;
import com.ancun.boss.persistence.model.SystemBasicConfigExample;
import com.ancun.boss.service.system.ICacheConfigService;
import com.ancun.core.exception.EduException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 基础数据缓存实现
 * @author mif
 * @version 1.0
 * @Created on 2015/10/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class CacheConfigServiceImpl implements ICacheConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ICacheConfigService.class);

    @Resource
    private SystemBasicConfigMapper systemBasicConfigMapper;

    @Resource
    private DataDicMapper dataDicMapper;

    @Override
    @Cacheable(value = "basicConfigCache")
    public List<SystemBasicConfig> queryAllBasicConfigs() throws EduException {

        SystemBasicConfigExample example = new SystemBasicConfigExample();
        SystemBasicConfigExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        /*update by zkai on 2016/05/11*/
        // ---------------------------
//        criteria.andPnoIsNotNull();
        // ---------------------------
        logger.debug("------------------->加载一次!");
        return systemBasicConfigMapper.selectByExample(example);
    }

    /**
     * add by zkai on 2016/04/22
     * 查询所有datadic数据 并加入缓存
     * @return
     * @throws EduException
     */
    @Cacheable(value = "dataDicListAllCache")
    public List<DataDic> queryAllDateDic() throws EduException {

        List<DataDic> list = dataDicMapper.selectByExample(new DataDicExample());
        logger.debug("------------------->加载一次!");
        return list;
    }
}
