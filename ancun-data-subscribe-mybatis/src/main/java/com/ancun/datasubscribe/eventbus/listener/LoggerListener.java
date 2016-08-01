package com.ancun.datasubscribe.eventbus.listener;

import com.ancun.datasubscribe.constant.Constant;
import com.ancun.datasubscribe.eventbus.event.DeleteEvent;
import com.ancun.datasubscribe.eventbus.event.InsertEvent;
import com.ancun.datasubscribe.eventbus.event.UpdateEvent;
import com.ancun.datasubscribe.util.bizeventbus.Subscribe;
import org.springframework.stereotype.Component;

/**
 * 日志监听器，将监听到事件输出到日志
 *
 * @Created on 2016年03月04日
 * @author 静好
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class LoggerListener extends BaseListener {

    /**
     * 监听到删除事件
     *
     * @param event 事件实体
     */
    @Subscribe(tableName = Constant.TABLE_NAME_DEFAULT, bizNo = Constant.BIZNO_DEFAULT, userType = Constant.USER_TYPE_DEFAULT)
    public void deleteAction(DeleteEvent event) {
        logger.info("取得事件为：{}", event);
    }

    /**
     * 监听到新增事件
     *
     * @param event 事件实体
     */
    @Subscribe(tableName = Constant.TABLE_NAME_DEFAULT, bizNo = Constant.BIZNO_DEFAULT, userType = Constant.USER_TYPE_DEFAULT)
    public void insertAction(InsertEvent event) {
        logger.info("取得事件为：{}", event);
    }

    /**
     * 监听到更新事件
     *
     * @param event 事件实体
     */
    @Subscribe(tableName = Constant.TABLE_NAME_DEFAULT, bizNo = Constant.BIZNO_DEFAULT, userType = Constant.USER_TYPE_DEFAULT)
    public void updateAction(UpdateEvent event) {
        logger.info("取得事件为：{}", event);
    }

}
