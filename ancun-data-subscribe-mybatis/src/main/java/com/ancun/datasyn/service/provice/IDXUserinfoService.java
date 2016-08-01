package com.ancun.datasyn.service.provice;

import com.ancun.common.persistence.model.dx.EntUserInfo;
import com.ancun.common.persistence.model.dx.UserInfo;
import com.ancun.common.persistence.model.master.BizTimerConfig;

import java.util.List;

/**
 * 分省电信用户同步
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/5/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IDXUserinfoService {


    /**
     * 查询个人所有RPCODES
     */
    List<String> selectAllPersonRpcodes(BizTimerConfig bizTimerConfig);


    /**
     * 查询企业用户所有rpcode
     * @return
     */
    List<String> selectAllEntRpcodes(BizTimerConfig bizTimerConfig);

    /**
     * 查询个人用户
     *
     * @param bizTimerConfig
     * @param rpcode
     * @return
     */
    List<UserInfo> selectUserInfo(BizTimerConfig bizTimerConfig, String rpcode);

    /**
     * 查询企业用户
     *
     * @param bizTimerConfig
     * @param rpcode
     * @return
     */
    List<EntUserInfo> selectEntUserInfo(BizTimerConfig bizTimerConfig, String rpcode);

    /**
     * 查询企业用户数量
     * @return
     */
    public int selectAllEntCount(BizTimerConfig bizTimerConfig);

    /**
     * 查询个人用户数量
     * @return
     */
    public int selectAllPersonCount(BizTimerConfig bizTimerConfig);
}
