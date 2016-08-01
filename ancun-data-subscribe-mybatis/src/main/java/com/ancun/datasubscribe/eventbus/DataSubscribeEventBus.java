package com.ancun.datasubscribe.eventbus;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

import com.ancun.common.persistence.mapper.master.ConsumeMapper;
import com.ancun.common.persistence.model.master.Consume;
import com.ancun.datasubscribe.conf.Config;
import com.ancun.datasubscribe.constant.Constant;
import com.ancun.datasubscribe.eventbus.event.BaseEvent;
import com.ancun.datasubscribe.eventbus.event.TableChangeDetail;
import com.ancun.datasubscribe.util.bizeventbus.BizEventBus;
import com.ancun.datasubscribe.util.bizeventbus.DeadEvent;
import com.ancun.datasubscribe.util.bizeventbus.PostEvent;
import com.ancun.datasubscribe.util.bizeventbus.Subscribe;
import com.ancun.datasubscribe.util.bizeventbus.SubscriberExceptionContext;
import com.ancun.datasubscribe.util.bizeventbus.SubscriberExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import tk.mybatis.mapper.entity.Example;

/**
 * 数据消费总线
 *
 * @Created on 2016年01月04日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class DataSubscribeEventBus {

    private static final Logger log = LoggerFactory.getLogger(DataSubscribeEventBus.class);

    /** 执行事件线程池 */
    private final Executor executor;

    /** 业务事件总线 */
    private final BizEventBus eventBus;

    /** 消费记录DAO */
    @Resource
    private ConsumeMapper consumeMapper;

    /** 待发送队列 uniqueNo为Key， 待发送事件为值 */
    private ConcurrentMap<String, BaseEvent> waitForSendTable = Maps.newConcurrentMap();

    /**
     * 初始化数据订阅中心事件总线
     *
     * @param commonConfig  共通配置参数
     */
    @Autowired
    public DataSubscribeEventBus(Config commonConfig) {
        // 线程池
        executor = Executors.newFixedThreadPool(commonConfig.getThreadsForEventBus());

        // 业务事件总线实例
        this.eventBus = new BizEventBus(new SubscriberExceptionHandler() {
            @Override
            public void handleException(Throwable exception, SubscriberExceptionContext context) {
                throw Throwables.propagate(exception);
            }
        });

        // 监听死亡事件
        eventBus.register(new Object() {

            @SuppressWarnings("unused")
            @Subscribe(bizNo = BizEventBus.DEAD_EVENT, userType = BizEventBus.DEAD_EVENT)
            public void listen(DeadEvent event) {
                log.info("在[{}]类里的事件[{}]无处理函数！", event.getSource().getClass(), event.getEvent());
            }
        });
    }

    /**
     * 注册监听器
     *
     * @param object 监听器
     */
    public void register(Object object) {
        eventBus.register(object);
    }

    /**
     * 发送消息
     *
     * @param event 广播事件
     */
    public void post(final BaseEvent event) {

        log.info("待发送列表信息: {}", waitForSendTable);

        // 取得最新事件信息
        final BaseEvent waitForSend = getFromWaits(event);

        // 如果数据已经完整
        if (waitForSend.isFullPackage()) {
            // 异步广播事件
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // 开始消费
                    startConsume(waitForSend);

                    // 开始执行消费
                    Consume completeConsume = consuming(waitForSend);

                    // 消费结束
                    endConsume(waitForSend, completeConsume);
                }
            });
        }
        // 如果数据还未完整，放入队列中等待下次广播
        else {
            waitForSendTable.putIfAbsent(this.getRelationForBiz(waitForSend), waitForSend);
        }
    }

    /**
     * 取得该业务的关联字段
     *
     * @param event 广播事件主体
     * @return      关联字段
     */
    private String getRelationForBiz(BaseEvent event){
        return event.getExtMap().get(Constant.UNIQUENO_TITLE);
    }

    /**
     * 根据当前事件取得最新事件信息
     *
     * @param event 当前事件
     * @return  最新事件信息
     */
    private BaseEvent getFromWaits(BaseEvent event) {
        // 从待发送队列中取得并删除事件
        BaseEvent result = waitForSendTable.remove(this.getRelationForBiz(event));

        // 组合从table中取得事件和传入事件
        BaseEvent postEvent = combeTwoEvent(result, event);

        // 如果既有biz_user_info信息又有详细信息
        if (postEvent.getTableChangeCount() == 2) {
            postEvent.setTableName(Constant.T_BIZ_USER_INFO);
        }
        return postEvent;
    }

    /**
     * 组合两个事件
     *
     * @param firstEvent    事件1
     * @param secondEvent   事件2
     * @return              组合后事件
     */
    private BaseEvent combeTwoEvent(BaseEvent firstEvent, BaseEvent secondEvent) {

        // 如果第一个不为空，第二个为空
        if (firstEvent != null && secondEvent == null) {
            return firstEvent;
        }
        // 如果第一个为空，第二个不为空
        else if (firstEvent == null && secondEvent != null) {
            return secondEvent;
        }

        // 结果集
        BaseEvent result = null;
        BaseEvent ext = null;

        // 如果第一个bizNo属性有值
        if (!Strings.isNullOrEmpty(firstEvent.getExtMap().get(Constant.BIZNO_TITLE))) {
            result = firstEvent;
            ext = secondEvent;
        }
        // 如果第二个bizNo属性有值
        else if (!Strings.isNullOrEmpty(secondEvent.getExtMap().get(Constant.BIZNO_TITLE))) {
            result = secondEvent;
            ext = firstEvent;
        }
        else {
            throw new RuntimeException("业务不存在！");
        }

        result.addTableChangeList(ext.getTableChangeList());
        if (result.getTableChangeCount() >= 2) {
            result.setFullPackage(true);
        }

        return result;
    }

    /**
     * 构建更新条件
     *
     * @param consumeCondition  条件来源
     * @return
     */
    private Example updateConsumeExample(Consume consumeCondition){
        // 更新条件设定
        Example example = new Example(Consume.class);
        example.createCriteria().andEqualTo("recordeId", consumeCondition.getRecordeId())
                .andEqualTo("bizNo", consumeCondition.getBizNo());

        return example;
    }

    /**
     * 开始消费
     *
     * @param postEvent 事件
     */
    private void startConsume(final BaseEvent postEvent){

        for (TableChangeDetail detail : postEvent.getTableChangeList()) {
            Consume startConsume = detail.getConsume();

            // 更新条件设定
            Example example = this.updateConsumeExample(startConsume);

            try {
                // 根据当前record_id取得条数
                Consume condition = new Consume();
                condition.setRecordeId(startConsume.getRecordeId());
                int count = consumeMapper.selectCount(condition);

                // 如果记录不存在
                if (count < 0) {
                    // 开始消费记录
                    consumeMapper.insertSelective(startConsume);
                } else {
                    Consume consumeForUpdate = new Consume();
                    // 推送次数加1
                    consumeForUpdate.setPushCount(count + 1);
                    consumeForUpdate.setUpdateTime(new Date());
                    consumeMapper.updateByExampleSelective(consumeForUpdate, example);
                }
            } catch (Exception e) {
                log.error("消费开始，更新事件【{}】的消费记录时出现异常：", postEvent, e);
            }
        }
    }

    /**
     * 开始执行消费
     *
     * @param postEvent 广播事件
     * @return          消费结果
     */
    private Consume consuming(final BaseEvent postEvent){

        Consume completeConsume = null;

        try {
            log.info("广播事件信息：{}", postEvent);
            // 广播消息给指定功能进行消费
            // 交由消费总线处理
            PostEvent event = PostEvent.builde()
                    .setTableName(postEvent.getTableName())
//                    .setBizNo(baseEvent.getConsume().getBizNo())
                    .setUserType(postEvent.getExtMap().get(Constant.USER_TYPE_TITLE))
                    .setEvent(postEvent);
            eventBus.post(event);

            // 执行成功，报废该记录
            this.ackAsConsumed(postEvent);

            completeConsume = createConsume(Constant.ACK_AS_CONSUMED_SUCCESS, "");

        } catch (Exception e) {
            completeConsume = createConsume(Constant.ACK_AS_CONSUMED_FAILED, e.getMessage());
            log.error("广播事件时出现异常：", e);
        }

        return completeConsume;
    }

    /**
     * 根据Postevent中的信息向dts服务报废信息
     *
     * @param postEvent 事件
     */
    private void ackAsConsumed(final BaseEvent postEvent){
        for (TableChangeDetail detail : postEvent.getTableChangeList()) {
            detail.getDtsMessage().ackAsConsumed();
        }
    }

    /**
     * 消费结束
     * @param postEvent         广播事件
     * @param completeConsume   消费完状态
     */
    private void endConsume(final BaseEvent postEvent, Consume completeConsume){

        for (TableChangeDetail detail : postEvent.getTableChangeList()) {
            Consume startConsume = detail.getConsume();

            // 更新条件设定
            Example example = this.updateConsumeExample(startConsume);

            try {
                // 消费结束
                consumeMapper.updateByExampleSelective(completeConsume, example);
            } catch (Exception e) {
                log.error("消费结束，更新事件【{}】的消费记录时出现异常：", postEvent, e);
            }
        }
    }

    /**
     * 创建一个消费记录信息
     *
     * @param status    状态
     * @param remark    备注
     * @return          消费记录信息
     */
    private Consume createConsume(int status, String remark) {

        Consume consume = new Consume();

        consume.setStatus(status);

        consume.setRemark(remark);

        consume.setUpdateTime(new Date());

        return consume;
    }
}
