package com.ancun.boss.service.phoneResp.task;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.ConfigConstant;
import com.ancun.boss.persistence.model.PhoneRepository;
import com.ancun.boss.persistence.model.PhoneSourceHelp;
import com.ancun.utils.taskbus.HandleTask;
import com.ancun.utils.taskbus.TaskBus;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
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
 * 号码池清洗任务--最后过滤任务
 *
 * @Created on 2015年12月17日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class PhoneRepLastFilterTask extends BaseTask {

    private static final Logger logger = LoggerFactory.getLogger(PhoneRepLastFilterTask.class);

    private static final String FILTER_OVER_TASK_DESCRIPTION    = "过滤结束任务";
    private static final String PAYPHONE_TASK_DESCRIPTION       = "付费号码任务";
    private static final int CURRENT_STATUS = BizRequestConstant.FILTER_PAYPHONE;

    /**
     * 将自己注册到任务总线中
     *
     * @param taskBus   任务总线
     */
    @Autowired
    public PhoneRepLastFilterTask(TaskBus taskBus){
        taskBus.register(this);
    }

    /**
     * 过滤结束任务
     * ① 号码状态全部置为过滤结束
     * ② 更新号码库
     * ③ 将号码移交给下一个任务
     *
     * @param phoneReps 需处理号码集
     * @return  下一个任务号码列表
     */
    @HandleTask(taskType = ConfigConstant.TASK_PHONE_FILTER, taskIndex = CURRENT_STATUS - 1, description = FILTER_OVER_TASK_DESCRIPTION)
    public List<PhoneRepository> filterOverTask(List<PhoneRepository> phoneReps) {

        logger.info("当前任务序号为[{}], 任务名为[{}]", CURRENT_STATUS - 1, FILTER_OVER_TASK_DESCRIPTION);

        // ① 将不属于黑名单号码的号码库对象的过滤层级置为下一个过滤层级
        List<PhoneRepository> filterOvers = Lists.transform(phoneReps, changeFilterOver());

        // ② 批量更新筛选结束号码
        updatePhoneRepBatch(filterOvers);

        // ③ 将号码移交给下一个任务
        return filterOvers;
    }

    /**
     * 付费号码任务
     * ① 查找出数据库已付费号码
     * ② 根据①的查询内容查出当前列表中需付费号码
     * ③ 根据②中的结果更新数据库③ 根据②中的结果更新数据库
     *
     * @param phoneReps 需处理号码集
     * @return  下一个任务号码列表
     */
    @HandleTask(taskType = ConfigConstant.TASK_PHONE_FILTER, taskIndex = CURRENT_STATUS, description = PAYPHONE_TASK_DESCRIPTION)
    public List<PhoneRepository> payPhone(List<PhoneRepository> phoneReps) {

        logger.info("当前任务序号为[{}], 任务名为[{}]", CURRENT_STATUS, PAYPHONE_TASK_DESCRIPTION);

        List<PhoneRepository> result = null;

        // ① 查询出非本次批次的所有批次列表
        List<PhoneSourceHelp> phoneSourceHelps = bizPhoneRepMapper.phoneSourceHelps(phoneReps.get(0).getPhoneSource());

        // ② 判断号码是否在指定批次中出现
        for (PhoneSourceHelp phoneSourceHelp : phoneSourceHelps) {
            // 不用付费号码列表
            List<PhoneRepository> notPayPhones = bizPhoneRepMapper.notPayPhoneReps(phoneSourceHelp.getPhoneSource());

            result = FluentIterable.from(phoneReps).transform(changePayPhone(notPayPhones)).toList();
        }

        // ③ 根据②中的结果更新数据库③ 根据②中的结果更新数据库
        updatePhoneRepBatch(result);

        return null;
    }

    /**
     * 将号码置为过滤结束
     *
     * @return  列表转换类{@link Function}
     */
    private Function<PhoneRepository, PhoneRepository> changeFilterOver(){
        return new Function<PhoneRepository, PhoneRepository>() {
            @Nullable
            @Override
            public PhoneRepository apply(PhoneRepository input) {

                // 号码过滤结束
                input = filterOver(input);

                return input;
            }
        };
    }

    /**
     * 将号码置为指定状态
     *
     * @param notPayphones 不用付费号码列表
     * @return              转换函数
     */
    private Function<PhoneRepository, PhoneRepository> changePayPhone(final List<PhoneRepository> notPayphones){
        return new Function<PhoneRepository, PhoneRepository>() {
            @Nullable
            @Override
            public PhoneRepository apply(PhoneRepository input) {

                if (Iterables.any(notPayphones, inNotPayphones(input.getPhone()))){
                    // 需要付费
                    input.setPayphone(BizRequestConstant.PAYPHONE_NO);
                }
                // 清洗完成
                input.setCleanStatus(BizRequestConstant.CLEAN_STATUS_COMPLETE);

                return input;
            }
        };
    }

    /**
     * 判断指定号码是不是非付费号码
     *
     * @param phone     指定号码
     * @return          判断函数
     */
    private Predicate<PhoneRepository> inNotPayphones(final String phone){
        return new Predicate<PhoneRepository>() {
            @Override
            public boolean apply(@Nullable PhoneRepository phoneRepository) {
                return Objects.equal(phone, phoneRepository.getPhone());
            }
        };
    }
}
