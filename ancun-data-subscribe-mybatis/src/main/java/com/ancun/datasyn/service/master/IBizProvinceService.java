package com.ancun.datasyn.service.master;

import com.ancun.common.persistence.model.master.BizProvice;

import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/18
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IBizProvinceService {

    /**
     * 查询所有辅助表数据
     *
     * @return
     */
    List<BizProvice> selectAllBizProvince();
}
