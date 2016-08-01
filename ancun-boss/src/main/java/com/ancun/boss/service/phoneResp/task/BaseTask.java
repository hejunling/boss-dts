package com.ancun.boss.service.phoneResp.task;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.persistence.biz.mapper.BizPhoneRepMapper;
import com.ancun.boss.persistence.model.PhoneRepository;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;

/**
 * 号码池清洗任务共通函数部
 *
 * @Created on 2015年11月06日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BaseTask {

    /** 自定义号码池清洗Mapper */
    @Resource
    protected BizPhoneRepMapper bizPhoneRepMapper;

    /**
     * 将号码的过滤状态改为指定状态
     *
     * @param current   当前号码
     * @return          改变状态后号码
     */
    protected PhoneRepository setCurrentStatus(PhoneRepository current, int currentStatus) {
        current.setStatus(String.valueOf(currentStatus));
        current.setFilterOver(BizRequestConstant.FILTER_OVER_COMPLETE);
        current.setCleanStatus(BizRequestConstant.CLEAN_STATUS_COMPLETE);
        return current;
    }

    /**
     * 过滤结束
     *
     * @param current   当前号码
     * @return          过滤结束状态号码
     */
    protected PhoneRepository filterOver(PhoneRepository current){
        current.setStatus(String.valueOf(BizRequestConstant.FILTER_LEVEL_INNER_PHONE));
        current.setFilterOver(BizRequestConstant.FILTER_OVER_COMPLETE);
        current.setPayphone(BizRequestConstant.PAYPHONE_NO);
        current.setCleanStatus(BizRequestConstant.CLEAN_STATUS_PAYPHONE_CLEANING);
        return current;
    }

    /**
     * 判断号码库对象的过滤层级与指定指定过滤层级{@code currentFiltLevel}是否一致
     *
     * @param currentFiltLevel 指定过滤层级
     * @return  true(一致)/false(不一致)
     */
    protected Predicate<PhoneRepository> currentFilterLevel(final int currentFiltLevel){
        return new Predicate<PhoneRepository>() {
            @Override
            public boolean apply(@Nullable PhoneRepository input) {
                return Objects.equal(input.getStatus(), String.valueOf(currentFiltLevel));
            }
        };
    }

    /**
     * 批量更新号码库表
     *
     * @param phoneRepositories 号码列表
     */
    protected void updatePhoneRepBatch(List<PhoneRepository> phoneRepositories) {
        if (phoneRepositories != null && phoneRepositories.size() > 0) {
            bizPhoneRepMapper.updatePhoneRepBatch(phoneRepositories);
        }
    }

    /**
     * 根据当前过滤状态从当前过滤层级的号码列表取得下一个过滤层级号码列表
     *
     * @param currentFilterLists    当前过滤层级的号码列表
     * @param currentStatus         当前过滤状态
     * @return                      下一个过滤层级号码列表
     */
    protected List<PhoneRepository> nextFilterLists(List<PhoneRepository> currentFilterLists, int currentStatus) {
        // 3.返回非黑名单号码列表
        List<PhoneRepository> results = FluentIterable.from(currentFilterLists)
                .filter(Predicates.not(currentFilterLevel(currentStatus)))
                .toList();

        return (results != null && results.size() > 0) ? results : null;
    }
}
