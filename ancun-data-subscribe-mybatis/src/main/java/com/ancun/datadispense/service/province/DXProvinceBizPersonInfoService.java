package com.ancun.datadispense.service.province;

import com.ancun.common.persistence.model.master.BizPersonInfo;
import com.ancun.datadispense.util.CustomException;

/**
 * 修改个人用户信息
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/4/8
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface DXProvinceBizPersonInfoService {

    /**
     * 修改个人用户信息
     *
     * @param personInfo
     * @throws Exception
     */
    void updateBizPersonInfo(BizPersonInfo personInfo) throws CustomException;
}
