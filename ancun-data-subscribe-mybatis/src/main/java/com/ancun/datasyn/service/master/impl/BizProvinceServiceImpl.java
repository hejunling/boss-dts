package com.ancun.datasyn.service.master.impl;

import com.ancun.common.persistence.mapper.master.BizProviceMapper;
import com.ancun.common.persistence.model.master.BizProvice;
import com.ancun.datasyn.service.master.IBizProvinceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/18
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class BizProvinceServiceImpl implements IBizProvinceService {

    @Resource
    private BizProviceMapper bizProviceMapper;

    @Override
    public List<BizProvice> selectAllBizProvince() {
        return bizProviceMapper.selectAll();
    }

}
