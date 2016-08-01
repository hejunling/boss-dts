package com.ancun.datasubscribe.util;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import com.aliyun.drc.client.message.ByteString;
import com.aliyun.drc.client.message.DataMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Nullable;

/**
 * 事件总线监听注册器(参照guava 19.0的EventBus)
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016年03月11日
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class DTSFieldUtil {

    /**
     * 日志操作类
     */
    private static final Logger logger = LoggerFactory.getLogger(DTSFieldUtil.class);

    /**
     * 从指定的数据列表中取得指定字段的值
     *
     * @param fields    指定列表
     * @param fieldTile 指定字段
     * @return 指定的值
     */
    public static String getFieldValue(List<DataMessage.Record.Field> fields, final String fieldTile) {
        return getFieldValue(fields, fieldTile, null);
    }

    /**
     * 从指定的数据列表中取得指定字段的值
     *
     * @param fields    指定列表
     * @param fieldTile 指定字段
     * @param defaultValue 默认值
     * @return 指定的值
     */
    public static String getFieldValue(List<DataMessage.Record.Field> fields, final String fieldTile, String defaultValue) {

        // 取得指定field
        DataMessage.Record.Field field = Iterables.find(fields, new Predicate<DataMessage.Record.Field>() {
            @Override
            public boolean apply(@Nullable DataMessage.Record.Field input) {
                return Objects.equal(input.getFieldname().toUpperCase(), fieldTile.toUpperCase());
            }
        }, null);

        // 指定默认值
        String result = defaultValue;

        if (field != null) {
            ByteString value = field.getValue();
            if (value != null) {
                String encoding = field.getEncoding();
                try {
                    result = value.toString(encoding);
                } catch (UnsupportedEncodingException e) {
                    logger.error("取得指定字段值时出错:", e);
                }
            }
        }

        return result;
    }

}
