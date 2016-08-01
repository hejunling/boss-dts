package com.ancun.boss.business.service.bizuser.impl;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.persistence.mapper.*;
import com.ancun.boss.business.persistence.module.*;
import com.ancun.boss.business.pojo.bizuser.BizUserDetaiOutput;
import com.ancun.boss.business.pojo.bizuser.BizUserInfoInput;
import com.ancun.boss.business.pojo.bizuser.BizUserLifeCircleListOutput;
import com.ancun.boss.business.service.BizBasicService;
import com.ancun.boss.business.service.bizuser.IBizUserDetailService;
import com.ancun.boss.business.service.dataDic.IQueryDictionaryService;
import com.ancun.boss.business.service.dataDic.IUserTaocanService;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.util.StringUtils;
import com.ancun.core.exception.EduException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2016/4/19
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class BizUserDetailServiceImpl extends BizBasicService implements IBizUserDetailService {


    @Resource
    private BizUserLifeCircleMapper bizUserLifeCircleMapper;

    @Resource
    private BizPersonInfoMapper bizPersonInfoMapper;

    @Resource
    private BizEntInfoMapper bizEntInfoMapper;

    @Resource
    private IUserTaocanService userTaocanService;

    @Resource
    private IQueryDictionaryService qeryDictionaryService;

    @Override
    public BizUserDetaiOutput querybizUserDetail(BizUserInfoInput input) throws EduException {

        BizUserDetaiOutput out = new BizUserDetaiOutput();


        BizUserInfo bizUserInfo = super.selectBizUserInfo(input.getUserNum(), input.getBizNo(), null);

        // 获取套餐信息
        HashMap<String, String> taocaoMap = userTaocanService.getTaocanAsmap();

        if (bizUserInfo.getTcid() != null){
            bizUserInfo.setTaocanName(taocaoMap.get(bizUserInfo.getTcid().toString()));
        }


        // 获取数据字典MAP
        HashMap<String, String> dicMap = qeryDictionaryService.getDicAsmap();
        bizUserInfo.setIsignupSource(dicMap.get(Constant.OPENTYPE + "_" + bizUserInfo.getInsource()) + "");

        out.setBizUserInfo(bizUserInfo);
        //个人用户信息
        if (Constant.USER_TYPE_PER.equals(bizUserInfo.getUserType())) {
            out.setBizPersonInfo(selectPersonInfo(bizUserInfo.getUserNo()));
        }
        // 企业用户信息
        if (Constant.USER_TYPE_ENT.equals(bizUserInfo.getUserType())
                && StringUtils.isNotEmpty(bizUserInfo.getEntNo())) {
            out.setBizEntInfo(selectEntInfo(bizUserInfo.getEntNo()));
        }

        return out;
    }

    /**
     * 查询企业用户信息
     *
     * @param entNo
     * @return
     */
    private BizEntInfo selectEntInfo(String entNo) {
        BizEntInfoExample example = new BizEntInfoExample();
        example.createCriteria().andEntNoEqualTo((entNo));

        List<BizEntInfo> list = bizEntInfoMapper.selectByExample(example);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询个人用户详情
     *
     * @param userNo
     * @return
     */
    private BizPersonInfo selectPersonInfo(String userNo) {
        BizPersonInfoExample example = new BizPersonInfoExample();
        example.createCriteria().andUserNoEqualTo((userNo));

        List<BizPersonInfo> list = bizPersonInfoMapper.selectByExample(example);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;

    }

    @Override
    public BizUserLifeCircleListOutput queryBizUserLifeCircle(BizUserInfoInput input) throws EduException {

        BizUserLifeCircleExample example = new BizUserLifeCircleExample();
        BizUserLifeCircleExample.Criteria criteria = example.createCriteria();
        criteria.andBizNoEqualTo(input.getBizNo()).andUserNoEqualTo(input.getUserNum());
        example.setOrderByClause("UPDATE_TIME DESC");

        /**
         * 企业用户生命周期只查询当前企业的
         * 个人用户生命周期只查人个人的
         */
        if (BizRequestConstant.ENT_USER.equals(input.getUserType()) && StringUtils.isNotEmpty(input.getEntNo())) {

            criteria.andEntNoEqualTo(input.getEntNo());
        }

        List<BizUserLifeCircle> lifeCircleList = bizUserLifeCircleMapper.selectByExample(example);
        if (null == lifeCircleList || lifeCircleList.size() <= 0) {
            return new BizUserLifeCircleListOutput();
        }


        // 获取套餐信息
        HashMap<String, String> taocaoMap = userTaocanService.getTaocanAsmap();
        /**
         * 设置套餐名称
         */
        for (BizUserLifeCircle bizUserLifeCircle : lifeCircleList) {
            bizUserLifeCircle.setTcName(taocaoMap.get(bizUserLifeCircle.getTcid().toString()));
        }

        return new BizUserLifeCircleListOutput(lifeCircleList);
    }


}
