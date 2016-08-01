package com.ancun.datasubscribe.dtsclient;

import com.aliyun.drc.clusterclient.ClusterListener;
import com.ancun.common.persistence.mapper.master.SubscribeMapper;
import com.ancun.common.persistence.model.master.Subscribe;
import com.ancun.datasubscribe.constant.Constant;
import com.ancun.datasubscribe.eventbus.DataSubscribeEventBus;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据订阅消费者客户端管理器
 *
 * @Created on 2016年01月04日
 * @author 静好
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class DTSClientManager implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(DTSClientManager.class);

	/** 运行环境取得 */
	@Value("${spring.profiles.active:test}")
	private String profile;

    /** 通道配置列表DAO */
	@Resource
	private SubscribeMapper bizSubscribeMapper;

	/** 数据消费总线 */
	@Resource
	private DataSubscribeEventBus eventBus;

	/** 定时汇报客户端 */
	@Resource
	private ReportClient reportClient;

    /** 数据订阅消费者客户端集合(以通道ID为KEY) */
	private Map<String, DTSClient> clientMap = Maps.newHashMap();

	/**
     * 将一个客户端注册到管理集合中
     *
     * @param subscribe 通道基础信息
     */
    public void registerClient(Subscribe subscribe) {
        // 启用的状态下才加入到集合
        if (subscribe.getStatus() == Constant.ENABLE
				// 运行环境一致
				&& Objects.equal(subscribe.getProfile(), profile)) {
			// 订阅监听者
			ClusterListener listener = new DefaultClusterListener(subscribe, eventBus, reportClient);
            // 新建数据订阅消费者客户端
            DTSClient service = new DTSClient(subscribe, listener);
            // 将客户端放入到集合中
			clientMap.put(subscribe.getGuid(), service);
        }
    }

    /**
     * 将通道配置信息表中的通道都注册到集合中
     */
	public void registerClientFromDB(){

        // 取得通道信息配置列表
		List<Subscribe> list = bizSubscribeMapper.selectAll();

        // 将通道配置信息列表中所有项注册到集合中
		for (final Subscribe subscribe : list) {
            this.registerClient(subscribe);
        }

	}

	/**
	 * 启动管理集合中的所有数据订阅消费者客户端
	 */
	public void startService(){

		for (DTSClient service : clientMap.values()) {
			service.startAsync();
		}

	}

	/**
	 * 在所有bean初始化后启动
	 *
	 * @param args			参数列表
	 * @throws Exception
     */
	@Override
	public void run(String... args) throws Exception {
//		log.info("正在扫描所有通道....");
//		this.registerClientFromDB();
//		this.startService();
//		log.info("已启动通道信息 ---> {}", this.clientMap);
	}
}
