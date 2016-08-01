package com.ancun.boss.business.service;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.persistence.mapper.BizUserInfoMapper;
import com.ancun.boss.business.persistence.module.BizUserInfo;
import com.ancun.boss.business.persistence.module.BizUserInfoExample;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.util.StringUtils;
import com.ancun.core.exception.EduException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/25
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class BizBasicService {

    @Resource
    private BizUserInfoMapper bizUserInfoMapper;

    /**
     * 查询业务用户数据
     *
     * @param userNo
     * @param bizNo
     * @param entNo
     * @param checkStatus 是否检查状态
     * @return
     */
    public BizUserInfo selectBizUserInfo(String userNo, String bizNo, String entNo, boolean checkStatus) {

        BizUserInfoExample example = new BizUserInfoExample();
        BizUserInfoExample.Criteria c = example.createCriteria();
        c.andUserNoEqualTo(userNo);
        c.andBizNoEqualTo(bizNo);
        if (checkStatus) {
            c.andStatusEqualTo(Constant.USER_STATUS_1);
        }

        if (StringUtils.isNotEmpty(entNo)) {
            c.andEntNoEqualTo(entNo);
        }

        List<BizUserInfo> list = bizUserInfoMapper.selectByExample(example);

        if (null == list || list.size() != 1) {
            throw new EduException(MessageConstant.BIZ_USER_IS_NOT_ONLY);
        }

        return list.get(0);
    }

    /**
     * 不检查用户状态
     *
     * @param userNo
     * @param bizNo
     * @param entNo
     * @return
     */
    public BizUserInfo selectBizUserInfo(String userNo, String bizNo, String entNo) {
        return selectBizUserInfo(userNo, bizNo, entNo, false);
    }

    /**
     * 检查用户状态
     *
     * @param userNo
     * @param bizNo
     * @param entNo
     * @return
     */
    public BizUserInfo selectBizUserInfoNomal(String userNo, String bizNo, String entNo) {
        return selectBizUserInfo(userNo, bizNo, entNo, true);
    }

    /**
     * 校验用户是否存在
     *
     * @param userNo
     * @param bizNo
     * @param entNo
     */
    public void validateBizUserInfo(String userNo, String bizNo, String entNo) {

        BizUserInfo bizUserInfo = selectBizUserInfo(userNo, bizNo, entNo);
        if (null == bizUserInfo) {
            throw new EduException(MessageConstant.BIZ_USER_NOT_EXISTS);
        }
    }

    /**
     * 校验用户是否存在（状态为正常的）
     *
     * @param userNo
     * @param bizNo
     * @param entNo
     */
    public void validateBizUserInfoNomal(String userNo, String bizNo, String entNo) {
        BizUserInfo bizUserInfo = selectBizUserInfoNomal(userNo, bizNo, entNo);
        if (null == bizUserInfo) {
            throw new EduException(MessageConstant.BIZ_USER_NOT_EXISTS);
        }
    }
}
