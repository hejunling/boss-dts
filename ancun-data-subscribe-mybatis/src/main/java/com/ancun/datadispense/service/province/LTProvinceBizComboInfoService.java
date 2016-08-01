package com.ancun.datadispense.service.province;

import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.datadispense.util.CustomException;

/**
 * 分省联通 套餐修改
 *
 * @author chenb
 * @version 1.0
 * @Created on 2016/4/12
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface LTProvinceBizComboInfoService {
	   /**
     * 新增套餐
     *
     * @param bizComboInfo
     * @throws Exception
     */
    void insertUserTaocan(BizComboInfo bizComboInfo) throws CustomException;

    /**
     * 修改套餐
     *
     * @param bizComboInfo
     * @throws Exception
     */
    void updateUserTaocan(BizComboInfo bizComboInfo) throws CustomException;

    /**
     * 删除套餐
     *
     * @param bizComboInfo
     * @throws Exception
     */
    void deleteUserTaocan(BizComboInfo bizComboInfo) throws CustomException;
}
