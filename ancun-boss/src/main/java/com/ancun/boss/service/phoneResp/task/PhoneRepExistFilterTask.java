package com.ancun.boss.service.phoneResp.task;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.ConfigConstant;
import com.ancun.boss.persistence.model.PhoneRepository;
import com.ancun.utils.taskbus.HandleTask;
import com.ancun.utils.taskbus.TaskBus;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 号码池清洗任务--重复号码过滤任务
 *
 * @Created on 2015年11月05日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class PhoneRepExistFilterTask extends BaseTask {

    private static final Logger logger = LoggerFactory.getLogger(PhoneRepExistFilterTask.class);

    private static final String EXIST_TASK_DESCRIPTION  = "重复号码过滤任务";
    private static final int CURRENT_STATUS = BizRequestConstant.FILTER_LEVEL_EXIST;

    /**
     * 将自己注册到任务总线中
     *
     * @param taskBus   任务总线
     */
    @Autowired
    public PhoneRepExistFilterTask(TaskBus taskBus){
        taskBus.register(this);
    }

    /**
     * 号码重复过滤任务
     * ① 按照号码排序号码列表
     * ② 与前一个号码一致的则为重复号码,修改过滤状态
     * ③ 更新操作
     *  Ⅰ 筛选出重复号码更新数据库
     *  Ⅱ 筛选出非重复号码流给下一个过滤任务
     *
     * @param phoneReps 需处理号码集
     * @return  下一个任务号码列表
     */
    @HandleTask(taskType = ConfigConstant.TASK_PHONE_FILTER, taskIndex = CURRENT_STATUS, description = EXIST_TASK_DESCRIPTION)
    public List<PhoneRepository> existFilterTask(List<PhoneRepository> phoneReps) {

        logger.info("当前任务序号为[{}], 任务名为[{}]", CURRENT_STATUS, EXIST_TASK_DESCRIPTION);

        // ① 按照号码排序号码列表
        List<PhoneRepository> afterOrders = FluentIterable.from(phoneReps).toSortedList(sortByPhone());

        // ② 与前一个号码一致的则为重复号码,修改过滤状态
        PhoneRepository befor = new PhoneRepository();
        for (PhoneRepository current : afterOrders) {
            // 如果与前一个一致，则是重复号码
            if (Objects.equal(current.getPhone(), befor.getPhone())) {
                current = setCurrentStatus(current, CURRENT_STATUS);
            }
            befor = current;
        }

        // ③ 更新操作 Ⅰ 筛选出重复号码更新数据库
        List<PhoneRepository> dupPhones = FluentIterable.from(afterOrders).filter(currentFilterLevel(CURRENT_STATUS)).toList();
        updatePhoneRepBatch(dupPhones);

        // ③ 更新操作 Ⅱ 筛选出非重复号码流给下一个过滤任务
        return nextFilterLists(afterOrders, CURRENT_STATUS);
    }

    /**
     * 按照电话号码字符串字典排序
     *
     * @return 排序规则
     */
    private Ordering<PhoneRepository> sortByPhone(){
        return Ordering.usingToString().nullsFirst().onResultOf(new Function<PhoneRepository, String>() {
            @Nullable
            @Override
            public String apply(@Nullable PhoneRepository input) {
                return input.getPhone();
            }
        });
    }

}
