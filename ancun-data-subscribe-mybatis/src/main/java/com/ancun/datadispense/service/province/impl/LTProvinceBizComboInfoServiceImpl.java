package com.ancun.datadispense.service.province.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.dx.UserTaocanMapper;
import com.ancun.common.persistence.model.dx.UserTaocan;
import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.datadispense.bizConstants.BizConstants;
import com.ancun.datadispense.service.province.LTProvinceBizComboInfoService;
import com.ancun.datadispense.util.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author chenb
 * @version 1.0
 * @Created on 2016/4/12
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "ltProvince")
public class LTProvinceBizComboInfoServiceImpl implements LTProvinceBizComboInfoService {
    public static final Logger logger = LoggerFactory.getLogger(LTProvinceBizComboInfoServiceImpl.class);

    @Resource
    private UserTaocanMapper userTaocanMapper;


    @Override
    public void insertUserTaocan(BizComboInfo bizComboInfo) throws CustomException {
        /**
         * 1.验证该套餐是否已存在
         */
        UserTaocan usertaocan = userTaocanMapper.selectByPrimaryKey(bizComboInfo.getTcid());
        if (null != usertaocan) {
            throw new CustomException("套餐ID已存在");
        }

        /**
         * 2.插入套餐
         */
        UserTaocan taocan = transformUserTaocan(bizComboInfo);
        taocan.setTaocanOrder(getOrder(bizComboInfo));

        logger.debug("新增电信分省套餐，bizComboInfo={}", bizComboInfo);
        userTaocanMapper.insertSelective(taocan);
    }


    @Override
    public void updateUserTaocan(BizComboInfo bizComboInfo) throws CustomException {
        /**
         * 1.验证该套餐是否已存在
         */
        UserTaocan usertaocan = userTaocanMapper.selectByPrimaryKey(bizComboInfo.getTcid());
        if (null == usertaocan) {
            throw new CustomException("套餐不存在");
        }


        /**
         * 2.修改套餐
         */

        UserTaocan taocan = transformUserTaocan(bizComboInfo);

        logger.debug("修改电信分省套餐，bizComboInfo={}", bizComboInfo);
        userTaocanMapper.updateByPrimaryKeySelective(taocan);
    }

    /**
     * 套餐组装
     *
     * @param bizComboInfo
     * @return
     */
    private UserTaocan transformUserTaocan(BizComboInfo bizComboInfo) {

        UserTaocan taocan = new UserTaocan();
        taocan.setTcid(bizComboInfo.getTcid());
        taocan.setTaocanName(bizComboInfo.getName());
        taocan.setTaocanPrice(bizComboInfo.getPrice());
        taocan.setTaocanType(bizComboInfo.getType());
        taocan.setTaocanCreatetime(bizComboInfo.getCreateTime());
        taocan.setTaocanFinishtime(bizComboInfo.getFinishTime());
        taocan.setStoreSpace(bizComboInfo.getSpace().toString());
        taocan.setTaocanStatus(bizComboInfo.getStatus());
        taocan.setTaocanRemark(bizComboInfo.getRemark());
        taocan.setRpcode(bizComboInfo.getRpcode());
        taocan.setTcduration(bizComboInfo.getDuration());
        taocan.setTcFlag(bizComboInfo.getDefaultTaocan());

        return taocan;
    }

    /**
     * 获取排序
     *
     * @param bizComboInfo
     * @return
     */
    private int getOrder(BizComboInfo bizComboInfo) {

        Example example = new Example(UserTaocan.class);
        example.createCriteria().andEqualTo("rpcode", bizComboInfo.getRpcode())
                .andEqualTo("taocanStatus", BizConstants.MARK_YES);

        return userTaocanMapper.selectCountByExample(example) + 1;

    }

    @Override
    public void deleteUserTaocan(BizComboInfo bizComboInfo) throws CustomException {

        /**
         * 1.验证该套餐是否已存在
         */
        UserTaocan usertaocan = userTaocanMapper.selectByPrimaryKey(bizComboInfo.getTcid());
        if (null == usertaocan) {
            throw new CustomException("删除失败：套餐不存在");
        }

        /**
         * 2.删除套餐
         */
        logger.debug("删除电信分省套餐，bizComboInfo={}", bizComboInfo);
        userTaocanMapper.deleteByPrimaryKey(bizComboInfo.getTcid());
    }
}
