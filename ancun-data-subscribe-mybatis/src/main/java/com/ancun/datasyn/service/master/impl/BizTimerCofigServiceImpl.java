package com.ancun.datasyn.service.master.impl;

import com.ancun.common.persistence.mapper.master.BizTimerConfigMapper;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.datasyn.service.master.IBizTimerCofigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/13
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class BizTimerCofigServiceImpl implements IBizTimerCofigService {

    public static final Logger logger = LoggerFactory.getLogger(BizTimerCofigServiceImpl.class);

    @Resource
    private BizTimerConfigMapper bizTimerConfigMapper;

    @Override
    public List<BizTimerConfig> selectAllBizTimerConfigs() {
        return bizTimerConfigMapper.selectAll();
    }

    @Override
    public BizTimerConfig selectByMoudle(String muoudle) {
        BizTimerConfig config = new BizTimerConfig();
        config.setMoudle(muoudle);

        return bizTimerConfigMapper.selectOne(config);
    }

    @Override
    public void updateBizTimerConfig(String moudle) {
        Example example = new Example(BizTimerConfig.class);
        example.createCriteria().andEqualTo("moudle", moudle);

        BizTimerConfig config = this.selectByMoudle(moudle);
        /**
         * 原时间设置为开始时间
         */
        config.setSynStartTimer(config.getSynEndTimer());
        config.setSynEndTimer(new Date());

        bizTimerConfigMapper.updateByExampleSelective(config, example);
    }
}
