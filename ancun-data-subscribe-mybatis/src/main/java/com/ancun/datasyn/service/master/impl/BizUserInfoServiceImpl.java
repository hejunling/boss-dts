package com.ancun.datasyn.service.master.impl;

import com.ancun.common.persistence.mapper.master.BizEntInfoMapper;
import com.ancun.common.persistence.mapper.master.BizPersonInfoMapper;
import com.ancun.common.persistence.mapper.master.BizUserInfoMapper;
import com.ancun.common.persistence.model.master.BizEntInfo;
import com.ancun.common.persistence.model.master.BizPersonInfo;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.service.master.IBizUserInfoService;
import com.ancun.utils.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/5/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class BizUserInfoServiceImpl implements IBizUserInfoService {

    public static final Logger logger = LoggerFactory.getLogger(BizUserInfoServiceImpl.class);

    @Resource
    private BizUserInfoMapper bizUserInfoMapper;

    @Resource
    private BizPersonInfoMapper bizPersonInfoMapper;

    @Resource
    private BizEntInfoMapper bizEntInfoMapper;

    @Override
    public List<BizUserInfo> selectBizUserInfoByBizNo(String bizNo) throws CustomException {
        return bizUserInfoMapper.selectBizUserInfoByBizNo(bizNo);
    }

    @Override
    public void consumerBizUserInfo(BizUserInfo bizUserInfo) throws Exception {

        //消费个人或企业详细信息
        updatePersonOrEntInfo(bizUserInfo);

        //非数据库对应字段，更新后设置为空
        bizUserInfo.setSex(null);
        bizUserInfo.setIdentify(null);

        bizUserInfo.setName(null);
        bizUserInfo.setRegNo(null);
        bizUserInfo.setAddress(null);

        Example example = new Example(BizUserInfo.class);
        example.createCriteria().andEqualTo("bizNo", bizUserInfo.getBizNo()).andEqualTo("userNo", bizUserInfo.getUserNo());

        BizUserInfo temp = new BizUserInfo();
        PropertyUtils.copyProperties(temp, bizUserInfo);
        temp.setUserNo(null);
        temp.setBizNo(null);

        int count = bizUserInfoMapper.updateByExampleSelective(temp, example);
        if (count > 1) {
            throw new CustomException("BOSS系统用户数据异常，bizUserInfo=" + bizUserInfo,count);
        } else if (count == 0) {
            // 未更新说明未新增内容
            bizUserInfoMapper.insertSelective(bizUserInfo);
        }

        logger.debug("用户消费,bizUserInfo={},是否新增:{}", bizUserInfo, count == 1);

    }

    /**
     * 消费个人或企业详细信息
     *
     * @param bizUserInfo
     */
    private void updatePersonOrEntInfo(BizUserInfo bizUserInfo) {
        String bizNo = bizUserInfo.getBizNo();
        String userNo = bizUserInfo.getUserNo();

        String entNo = bizUserInfo.getEntNo();

        if (bizUserInfo.getUserType().equals(SynConstant.USER_TYPE_PERSONAL)) {
            String sex = bizUserInfo.getSex();
            String identity = bizUserInfo.getIdentify();

            if (StringUtil.isEmpty(sex) && StringUtil.isEmpty(identity))
                return;


            BizPersonInfo bizPersonInfo = new BizPersonInfo();
            bizPersonInfo.setSex(sex);
            bizPersonInfo.setIdentify(identity);
            logger.debug("消费个人详情{}", bizPersonInfo);

            Example example = new Example(BizPersonInfo.class);
            example.createCriteria().andEqualTo("userNo", userNo).andEqualTo("bizNo", bizNo);

            int count = bizPersonInfoMapper.updateByExampleSelective(bizPersonInfo, example);

            bizPersonInfo.setUserNo(userNo);
            bizPersonInfo.setBizNo(bizNo);

            if (count > 1) {
                throw new CustomException("BOSS系统用户详情数据异常，bizPersonInfo=" + bizPersonInfo,count);
            } else if (count == 0) {
                // 未更新说明未新增内容
                bizPersonInfoMapper.insertSelective(bizPersonInfo);
            }

        } else {

            String name = bizUserInfo.getName();
            String address = bizUserInfo.getAddress();
            String regNo = bizUserInfo.getRegNo();

            if (StringUtil.isEmpty(name) && StringUtil.isEmpty(address) && StringUtil.isEmpty(regNo))
                return;

            BizEntInfo bizEntInfo = new BizEntInfo();
            bizEntInfo.setName(name);
            bizEntInfo.setAddress(address);
            bizEntInfo.setRegNo(regNo);
            logger.debug("消费企业详情{}", bizEntInfo);

            Example example = new Example(BizEntInfo.class);
            example.createCriteria().andEqualTo("bizNo", bizNo).andEqualTo("entNo", entNo);

            int count = bizEntInfoMapper.updateByExampleSelective(bizEntInfo, example);

            bizEntInfo.setBizNo(bizNo);
            bizEntInfo.setEntNo(entNo);
            if (count > 1) {
                throw new CustomException("BOSS系统用户详情数据异常，bizEntInfo=" + bizEntInfo,count);
            } else if (count == 0) {
                // 未更新说明未新增内容
                bizEntInfoMapper.insertSelective(bizEntInfo);
            }
        }
    }

}
