package com.ancun.boss.service.phoneResp.task;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.ConfigConstant;
import com.ancun.boss.persistence.biz.mapper.BizPhoneRepMapper;
import com.ancun.boss.persistence.model.BlackUserInfo;
import com.ancun.boss.persistence.model.PhoneRepository;
import com.ancun.utils.taskbus.HandleTask;
import com.ancun.utils.taskbus.TaskBus;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 号码池清洗任务--黑名单过滤
 *
 * @Created on 2015年11月06日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class PhoneRepBlackFilterTask extends BaseTask {

    private static final Logger logger = LoggerFactory.getLogger(PhoneRepBlackFilterTask.class);

    private static final String BLACK_TASK_DESCRIPTION  = "黑名单号码过滤任务";
    private static final int CURRENT_STATUS = BizRequestConstant.FILTER_LEVEL_IS_BLACK;

    /**
     * 将自己注册到任务总线中
     *
     * @param taskBus   任务总线
     */
    @Autowired
    public PhoneRepBlackFilterTask(TaskBus taskBus, BizPhoneRepMapper bizPhoneRepMapper){
        this.bizPhoneRepMapper = bizPhoneRepMapper;
        // 载入黑名单缓存
        this.bizPhoneRepMapper.blackUserInfos();
        // 注册到任务总线
        taskBus.register(this);
    }

    /**
     * 黑名单过滤任务
     * 1 遍历号码池信息
     *  ① 将属于黑名单号码的号码库对象的过滤层级置为黑名单过滤层级
     *  ② 将不属于黑名单号码的号码库对象的过滤层级置为下一个过滤层级
     * 2 批量更新黑名单号码列表
     * 3.返回非黑名单号码列表
     *
     * @param phoneReps 需处理号码集
     * @return  下一个任务号码列表
     */
    @HandleTask(taskType = ConfigConstant.TASK_PHONE_FILTER, taskIndex = CURRENT_STATUS, description = BLACK_TASK_DESCRIPTION)
    public List<PhoneRepository> blackFilterTask(List<PhoneRepository> phoneReps) {

        logger.info("当前任务序号为[{}], 任务名为[{}]", CURRENT_STATUS, BLACK_TASK_DESCRIPTION);

        // ② 将不属于黑名单号码的号码库对象的过滤层级置为下一个过滤层级
        List<PhoneRepository> afterFilters = Lists.transform(phoneReps, changeNotBlackUser());

        // 2 将号码池信息列表批量新增
        List<PhoneRepository> blackUsers = FluentIterable.from(afterFilters).filter(currentFilterLevel(CURRENT_STATUS)).toList();
        updatePhoneRepBatch(blackUsers);

        // 3.返回非黑名单号码列表
        return nextFilterLists(afterFilters, CURRENT_STATUS);
    }

    /**
     * 将号码置为黑名单号码
     *
     * @return  列表转换类{@link Function}
     */
    private Function<PhoneRepository, PhoneRepository> changeNotBlackUser(){
        return new Function<PhoneRepository, PhoneRepository>() {
            @Nullable
            @Override
            public PhoneRepository apply(PhoneRepository input) {

                // 黑名单列表
                List<BlackUserInfo> blackUserInfos = bizPhoneRepMapper.blackUserInfos();

                // 黑名单用户
                if (Iterables.any(blackUserInfos, isBlackUserInfo(input.getPhone()))) {
                    // 设置为黑名单过滤层级
                    input = setCurrentStatus(input, CURRENT_STATUS);
                }

                return input;
            }
        };
    }

    /**
     * 判断一个号码是否为黑名单
     *
     * @param phone 号码
     * @return      true(黑名单号码)/false(非黑名单号码)
     */
    private Predicate<BlackUserInfo> isBlackUserInfo(final String phone){
        return new Predicate<BlackUserInfo>() {
                    @Override
                    public boolean apply(@Nullable BlackUserInfo input) {
                        return Objects.equal(input.getPhone(), phone);
                    }
                };
    }

}
