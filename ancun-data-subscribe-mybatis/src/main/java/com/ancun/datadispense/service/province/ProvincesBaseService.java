package com.ancun.datadispense.service.province;

import com.ancun.common.persistence.mapper.dx.EntUserInfoMapper;
import com.ancun.common.persistence.mapper.dx.UserInfoMapper;
import com.ancun.common.persistence.model.dx.EntUserInfo;
import com.ancun.common.persistence.model.dx.UserInfo;
import com.ancun.datadispense.util.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/3/10
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class ProvincesBaseService {
    private final static Logger log = LoggerFactory.getLogger(ProvincesBaseService.class);

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private EntUserInfoMapper entUserInfoMapper;

    /**
     * 查询个人用户
     *
     * @param userNo 账号
     * @return
     */
    public UserInfo selectPersonalUserInfo(String userNo) throws CustomException {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserno(userNo);
        List<UserInfo> userInfoList = userInfoMapper.select(userInfo);

        if (null != userInfoList && userInfoList.size() != 0) {
            if (userInfoList.size() == 1) {
                return userInfoList.get(0);
            } else {
                log.info("业务数据库：用户数量信息异常");
                throw new CustomException("业务数据库：个人用户数量信息异常");
            }
        }
        return null;
    }

    /**
     * 查询企业用户
     * 同一个企业用户只能存在一个分省，汇工作除外
     *
     * @param userNo  企业子账号
     * @param uesrTel 企业主账号
     * @param rpCode  省份
     * @return
     */
    public EntUserInfo selectEntUserInfo(String userNo, String uesrTel, String rpCode) throws CustomException {

        List<EntUserInfo> userInfoList = entUserInfoMapper.selectEntUserInfos(userNo, uesrTel, rpCode);

        if (null != userInfoList && userInfoList.size() != 0) {
            if (userInfoList.size() == 1) {
                return userInfoList.get(0);
            } else {
                log.info("业务数据库：用户数量信息异常");
                throw new CustomException("业务数据库：企业用户数量信息异常");
            }
        }
        return null;
    }

}
