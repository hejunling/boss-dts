package com.ancun.datadispense.service.province;

import com.ancun.common.persistence.model.master.BizEntInfo;
import com.ancun.datadispense.util.CustomException;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/8
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface DXProvinceBizEntInfoService {

    /**
     * 修改企业用户信息
     *
     * @param bizEntInfo
     * @throws Exception
     */
    void updateBizEntInfo(BizEntInfo bizEntInfo) throws CustomException;
}
