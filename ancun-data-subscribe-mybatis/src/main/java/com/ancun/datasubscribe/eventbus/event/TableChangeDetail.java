package com.ancun.datasubscribe.eventbus.event;

import com.google.common.base.MoreObjects;

import com.aliyun.drc.client.message.DataMessage;
import com.aliyun.drc.clusterclient.message.ClusterMessage;
import com.ancun.common.persistence.model.master.Consume;
import com.ancun.common.persistence.model.master.Subscribe;

import java.util.List;

/**
 * 表变更详细
 *
 * @Created on 2016年04月06日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class TableChangeDetail {

    /** 通道信息 */
    private Subscribe subscribe;

    /** DTS推送消息 */
    private ClusterMessage dtsMessage;

    /** 消费记录 */
    private Consume consume;

    /** 变更内容集合 */
    private List<DataMessage.Record.Field> fields;

    public TableChangeDetail(Subscribe subscribe, ClusterMessage dtsMessage, Consume consume, List<DataMessage.Record.Field> fields) {
        this.subscribe = subscribe;
        this.dtsMessage = dtsMessage;
        this.consume = consume;
        this.fields = fields;
    }

    public Subscribe getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public ClusterMessage getDtsMessage() {
        return dtsMessage;
    }

    public void setDtsMessage(ClusterMessage dtsMessage) {
        this.dtsMessage = dtsMessage;
    }

    public Consume getConsume() {
        return consume;
    }

    public void setConsume(Consume consume) {
        this.consume = consume;
    }

    public List<DataMessage.Record.Field> getFields() {
        return fields;
    }

    public void setFields(List<DataMessage.Record.Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("表变更详细[TableChangeDetail]")
                .add("subscribe", subscribe)
                .add("dtsMessage", dtsMessage)
                .add("consume", consume)
                .add("fields", fields)
                .toString();
    }

}
