package com.ancun.datasyn.service.master;

import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.util.CustomException;

import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IBizUserInfoService {

    /**
     * 查询BOSS系统中用户信息
     *
     * @param bizNo 业务编码
     * @return
     * @throws CustomException
     */
    List<BizUserInfo> selectBizUserInfoByBizNo(String bizNo) throws CustomException;

    /**
     * 消费用户信息
     * 先更新用户 若更新记录数大于1，用户数据异常，若等于0，则便是无数据，做新增操作
     *
     * @param bizUserInfo
     * @throws CustomException
     */
    void consumerBizUserInfo(BizUserInfo bizUserInfo) throws Exception;
}
