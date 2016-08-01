package com.ancun.datasubscribe.dtsclient;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import com.aliyun.drc.client.message.DataMessage;
import com.aliyun.drc.clusterclient.ClusterListener;
import com.aliyun.drc.clusterclient.message.ClusterMessage;
import com.ancun.common.persistence.model.master.Consume;
import com.ancun.common.persistence.model.master.Subscribe;
import com.ancun.datasubscribe.constant.Constant;
import com.ancun.datasubscribe.eventbus.DataSubscribeEventBus;
import com.ancun.datasubscribe.eventbus.event.BaseEvent;
import com.ancun.datasubscribe.eventbus.event.DeleteEvent;
import com.ancun.datasubscribe.eventbus.event.InsertEvent;
import com.ancun.datasubscribe.eventbus.event.TableChangeDetail;
import com.ancun.datasubscribe.eventbus.event.UpdateEvent;
import com.ancun.datasubscribe.util.DTSFieldUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 默认订阅监听者listener
 * 订阅监听者listener（消费数据的功能通过类Listener来实现。
 * 初始化完ClusterClient，需要添加listener，
 * Listener要定义notify函数来接受订阅数据并进行数据消费。）
 *
 * @Created on 2016年02月02日
 * @author 静好
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class DefaultClusterListener extends ClusterListener {

    /** 日志操作类 */
    private static final Logger logger = LoggerFactory.getLogger(DefaultClusterListener.class);

    /** 订阅的通道信息 */
    private final Subscribe subscribe;

    /** 数据消费总线 */
    private final DataSubscribeEventBus eventBus;

    /** 定时汇报客户端 */
    private final ReportClient reportClient;

    /**
     * 初始化一个默认监听者
     *
     * @param subscribe	订阅的通道信息
     * @param eventBus	数据消费总线
     * @param reportClient	定时汇报客户端
     */
    public DefaultClusterListener(Subscribe subscribe, DataSubscribeEventBus eventBus, ReportClient reportClient) {
        this.subscribe = subscribe;
        this.eventBus = eventBus;
        this.reportClient = reportClient;
    }

    /**
     * 监听到数据变更时操作
     *
     * @param messages		数据变更信息列表
     * @throws Exception	异常
     */
    @Override
    public void notify(List<ClusterMessage> messages) throws Exception {

        // 定时汇报
        reportClient.report(subscribe.getDescription());

        // 解析消息并分发
        for (ClusterMessage message : messages) {

            // 如果是心跳测试信息则直接消费
            if (message.getRecord().getOpt() == DataMessage.Record.Type.HEARTBEAT) {
                //消费完数据后向DTS汇报ACK，必须调用
                message.ackAsConsumed();
            } else {

                // 字段变更类型
                DataMessage.Record.Type type = message.getRecord().getOpt();

                // 如果是删除，新增，更新操作
                if (type == DataMessage.Record.Type.DELETE ||
                        type == DataMessage.Record.Type.INSERT ||
                        type == DataMessage.Record.Type.UPDATE) {
                    // 解析消息
                    BaseEvent baseEvent = decodeMessage(subscribe, message);

                    // 交由消费总线处理
                    eventBus.post(baseEvent);
                }
                // 否则
                else {
                    message.ackAsConsumed();
                }
            }
        }

    }

    /**
     * 监听消费异常
     *
     * @param e	异常是咧
     */
    @Override
    public void noException(Exception e) {
        logger.info("消费订阅通道{}的数据时出现异常：", subscribe, e);
    }

    /**
     * 解析消息构建事件总线广播用参数
     *
     * @param subscribe     通道信息
     * @param message       消息实体
     * @return          事件总线用参数
     */
    private BaseEvent decodeMessage(Subscribe subscribe, ClusterMessage message) {

        // 消费记录信息
        Consume consume = creatConsume(message.getRecord());

        // 字段变更类型
        DataMessage.Record.Type type = message.getRecord().getOpt();

        // 字段内容
        List<DataMessage.Record.Field> fields = message.getRecord().getFieldList();

        BaseEvent event = null;

        // 删除事件
        if (type == DataMessage.Record.Type.DELETE) {
            event = new DeleteEvent();
        }
        // 新增事件
        else if (type == DataMessage.Record.Type.INSERT){
            event = new InsertEvent();
        }
        // 更新事件
        else if (type == DataMessage.Record.Type.UPDATE){

            List<DataMessage.Record.Field> updateFields = Lists.newArrayList();

            // 取得奇数项数据
            for (int i = 0; i < fields.size(); i++) {
                if (i % 2 != 0) {
                    updateFields.add(fields.get(i));
                }
            }
            fields = updateFields;
            event = new UpdateEvent();
        }
        // 表变更详细信息
        TableChangeDetail detail = new TableChangeDetail(subscribe, message, consume, fields);

        // 取得表名
        String tableName = message.getRecord().getTablename();
        // 默认default
        if (Strings.isNullOrEmpty(tableName)) {
            tableName = Constant.TABLE_NAME_DEFAULT;
        }

        // 业务名称
        String bizNo = DTSFieldUtil.getFieldValue(fields, Constant.BIZNO_TITLE);
        detail.getConsume().setBizNo(bizNo);

        // 用户类型
        String userType = DTSFieldUtil.getFieldValue(fields,
                Constant.USER_TYPE_TITLE, Constant.USER_TYPE_DEFAULT);

        // 设置为企业账户
        if (isEnt(tableName)) {
            userType = Constant.USER_TYPE_ENTERPRISES;
        }
        // 设置为个人账户
        else if (isPerson(tableName)) {
            userType = Constant.USER_TYPE_PERSONAL;
        }
        detail.getConsume().setUserType(userType);

        // 数据是否准备完全
        String fullPackage = DTSFieldUtil.getFieldValue(fields,
                Constant.FULL_PACKAGE_TITLE, Constant.FULL_PACKAGE_DEFAULT);

        // 操作唯一标识
        String uniqueNo = DTSFieldUtil.getFieldValue(fields, Constant.UNIQUENO_TITLE,
                UUID.randomUUID().toString());

        return event.addTableChange(detail)
                .setFullPackage(Boolean.valueOf(fullPackage))
                .setTableName(tableName)
                .addExt(Constant.UNIQUENO_TITLE, uniqueNo)
                .addExt(Constant.BIZNO_TITLE, bizNo)
                .addExt(Constant.USER_TYPE_TITLE, userType);
    }

    /**
     * 根据推送消息记录构建消费记录信息
     *
     * @param record    推送消息记录
     * @return          消费记录信息
     */
    private Consume creatConsume(DataMessage.Record record) {

        Consume consume = new Consume();

        consume.setRecordeId(record.getId());

        consume.setPushCount(1);

        consume.setServerId(record.getServerId());

        consume.setCheckpoint(record.getCheckpoint());

        consume.setTimestamp(record.getTimestamp());

        consume.setDbName(record.getDbname());

        consume.setTableName(record.getTablename());

        consume.setActType(record.getOpt().value());

        consume.setCreateTime(new Date());

        consume.setUpdateTime(new Date());

        return consume;
    }

    /**
     * 判断是否个人
     *
     * @param tableName 表名
     * @return          true，false
     */
    private boolean isPerson(String tableName) {
        return Objects.equal(tableName.toLowerCase(), Constant.T_BIZ_PERSON_INFO.toLowerCase());
    }

    /**
     * 判断是否企业
     *
     * @param tableName 表名
     * @return          true，false
     */
    private boolean isEnt(String tableName) {
        return Objects.equal(tableName.toLowerCase(), Constant.T_BIZ_ENT_INFO.toLowerCase());
    }

}
