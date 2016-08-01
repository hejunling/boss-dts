package com.ancun.boss.business.service.bizuser.impl;

import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.pojo.bizuser.UserOpenCancelInfo;
import com.ancun.boss.business.pojo.bizuser.UserOpenCancelStatisticInfo;
import com.ancun.boss.business.pojo.bizuser.UserOpenCancelStatisticInput;
import com.ancun.boss.business.pojo.bizuser.UserOpenCancelStatisticOutput;
import com.ancun.boss.business.service.bizuser.IUserOpenCancelStatisticService;
import com.ancun.boss.business.service.dataDic.IQueryDictionaryService;
import com.ancun.boss.persistence.biz.BizUserInfoBizMapper;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.utils.DateUtil;
import com.ancun.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户开通退订统计接口实现类
 * User: zkai
 * Date: 2016/6/16
 * Time: 15:06
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class UserOpenCancelStatisticServiceImpl implements IUserOpenCancelStatisticService {
    @Resource
    private BizUserInfoBizMapper bizUserInfoBizMapper;

    @Resource
    private IBasicConfigService basicConfigService;

    @Resource
    private IQueryDictionaryService queryDictionaryService;

    /**
     * 取得用户开通退订统计信息
     * @param input
     * @return
     */
    @Override
    public UserOpenCancelStatisticOutput getUserOpenCancelStatistic(UserOpenCancelStatisticInput input) {
        UserOpenCancelStatisticOutput out = new UserOpenCancelStatisticOutput();

        // 用户状态
        String status = input.getStatus();

        HashMap<String,String> dicmap =  queryDictionaryService.getDicAsmap(); // data_dic map

        List<UserOpenCancelInfo> userOpenCancelInfoList = bizUserInfoBizMapper.userOpenCancelStatistic(input);
        List<UserOpenCancelStatisticInfo> userOpenCancelStatisticInfos = new ArrayList<UserOpenCancelStatisticInfo>();
        for (UserOpenCancelInfo userOpenCancelInfo: userOpenCancelInfoList) {
            UserOpenCancelStatisticInfo userOpenCancelStatisticInfo = new UserOpenCancelStatisticInfo();
            // 统计出的数量
            userOpenCancelStatisticInfo.setNumber(userOpenCancelInfo.getNumber());
            // 业务名称
            userOpenCancelStatisticInfo.setBizname(basicConfigService.getBizInfoByCoed(userOpenCancelInfo.getBizno()).getName());

            // 状态
            userOpenCancelStatisticInfo.setStatusname(Constant.USER_STATUS_MAP.get(userOpenCancelInfo.getStatus()));

            // 如果用户状态“开通”设置来源的值
            if(StringUtil.equals(status,Constant.USER_STATUS_1)){
                userOpenCancelStatisticInfo.setSourcename(dicmap.get(Constant.OPENTYPE+"_"+userOpenCancelInfo.getInsource()));

                // 如果用户状态“开通”且查询条件中的来源不为空，设置时间
                if(StringUtil.isNotBlank(input.getSource())){
                    userOpenCancelStatisticInfo.setTime(DateUtil.convertDateToString(userOpenCancelInfo.getIntime()));
                }

            }

            // 如果用户状态“退订”设置来源的值
            if(StringUtil.equals(status,Constant.USER_STATUS_3)){
                userOpenCancelStatisticInfo.setSourcename(basicConfigService.getBizInfoByCoed(userOpenCancelInfo.getOffsource()).getName());

                // 如果用户状态“退订”且查询条件中的来源不为空，设置时间
                if(StringUtil.isNotBlank(input.getSource())){
                    userOpenCancelStatisticInfo.setTime(DateUtil.convertDateToString(userOpenCancelInfo.getOfftime()));
                }

            }
            // 统计开始时间
            userOpenCancelStatisticInfo.setStartime(DateUtil.convertDateToString(input.getStartime()));

            // 统计结束时间
            userOpenCancelStatisticInfo.setEndtime(DateUtil.convertDateToString(input.getEndtime()));
            userOpenCancelStatisticInfos.add(userOpenCancelStatisticInfo);
        }
        out.setUserOpenCancelStatisticInfoList(userOpenCancelStatisticInfos);
        out.setPageinfo(input.getPage());
        return out;
    }

}
