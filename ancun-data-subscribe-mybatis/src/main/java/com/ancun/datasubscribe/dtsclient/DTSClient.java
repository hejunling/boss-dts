package com.ancun.datasubscribe.dtsclient;

import com.aliyun.drc.client.message.DataMessage;
import com.aliyun.drc.clusterclient.ClusterClient;
import com.aliyun.drc.clusterclient.ClusterListener;
import com.aliyun.drc.clusterclient.DefaultClusterClient;
import com.aliyun.drc.clusterclient.RegionContext;
import com.aliyun.drc.clusterclient.message.ClusterMessage;
import com.ancun.common.persistence.model.master.Subscribe;
import com.ancun.datasubscribe.constant.Constant;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 数据订阅消费者客户端
 * 从DTS服务订阅数据并进行数据消费。
 *
 * @Created on 2016年01月04日
 * @author 静好
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class DTSClient extends AbstractExecutionThreadService{

	/** 日志操作类 */
	private static final Logger logger = LoggerFactory.getLogger(DTSClient.class);

	/** 订阅的通道信息 */
	private final Subscribe subscribe;

	/** 数据订阅服务(DTS)SDK客户端 */
	private final ClusterClient client;

	/** 停止信号 */
	private boolean stopFlg = false;

	/**
	 * 初始化一个数据订阅服务(DTS)SDK客户端
	 *
	 * @param subscribe	订阅的通道信息
	 * @param listener	订阅监听者listener
     */
	public DTSClient(Subscribe subscribe, ClusterListener listener) {

		// 通道订阅者初始化
		this.subscribe = subscribe;

		// 创建一个context，RegionContext 主要用于保存设置安全认证信息及访问网络模式设置
		RegionContext context = new RegionContext();

		// 运行SDK的服务器是否使用公网IP连接DTS
		context.setUsePublicIp( subscribe.getUsePublicIp() == Constant.USE_PUBLIC_IP);
		context.setAccessKey(subscribe.getAccessKey());
		context.setSecret(subscribe.getAccessSecret());

		// 创建消费者（接受增量数据等操作都是通过类ClusterClient来完成的）
		client = new DefaultClusterClient(context);

		// 添加监听者
		client.addConcurrentListener(listener);

		// 添加service监听者
		this.addServiceListerner();

	}

	/**
	 * 线程启动前初始化操作
	 *
	 * @throws Exception	相关异常
     */
	@Override
	protected void startUp() throws Exception{
		client.askForGUID(subscribe.getGuid());
	}

	/**
	 * 运行逻辑
	 *
	 * @throws Exception	相关异常
     */
	@Override
	protected void run() throws Exception{
		// 启动时不允许停止
		stopFlg = false;
		// 启动客户端
		client.start();
	}

	/**
	 * 关闭操作
	 *
	 * @throws Exception	相关异常
     */
	@Override
	protected void shutDown() throws Exception{
		// 第一次不停止
		if (stopFlg) {
			client.stop();
		}
		// 第一次关闭后允许停止
		stopFlg = true;

	}

	/**
	 * 添加服务监听器
	 */
	protected void addServiceListerner(){
		//服务状态转换时调用
		this.addListener(new Listener() {

			@Override
			public void starting(){
				logger.info("数据订阅消费者客户端开始启动, {}", subscribe);
			}

			@Override
			public void running(){
				logger.info("数据订阅消费者客户端运行中, {}", subscribe);
			}

			//State from
			@Override
			public void stopping(State from){
				logger.info("数据订阅消费者客户端关闭中, {}", subscribe);
			}

			@Override
			public void terminated(State from){
//				logger.info("服务终止");
			}

			@Override
			public void failed(State from,Throwable failure){
				logger.info("订阅数据客户端发生异常, {}", subscribe, failure.getCause());
			}

		},MoreExecutors.directExecutor());
	}

	public static void main(String[] args) throws Exception {
		final Subscribe subscribe = new Subscribe();
		subscribe.setId(new Long(1));
		subscribe.setAccessKey("qOFnq2v3T5aRsX3W");
		subscribe.setAccessSecret("mqcVK61JaxSKZCwQHf6a8ZngYpV1Gx");
		subscribe.setUsePublicIp(1);
		subscribe.setGuid("dts_rds707r54o0s3d459588_J90");
		subscribe.setStatus(0);
		subscribe.setDescription("boss系统日志增量通道");

		DTSClient client = new DTSClient(subscribe, new ClusterListener() {
			@Override
			public void notify(List<ClusterMessage> messages) throws Exception {
//                 logger.info("listener的messages的条数{}", messages.size());
				for (ClusterMessage message : messages) {
					if (message.getRecord().getOpt() != DataMessage.Record.Type.HEARTBEAT) {

						logger.info("当前记录的线程ID：{}", message.getRecord().getThreadId());

						logger.info("当前记录的TraceId：{}", message.getRecord().getTraceId());

						// 打印订阅到的数据
						logger.info("{},订阅到的数据 : {} : {} : {}",
								subscribe,
								message.getRecord(),
								message.getRecord().getTablename(),
								message.getRecord().getOpt());

					}
					//消费完数据后向DTS汇报ACK，必须调用
					message.ackAsConsumed();
				}
			}

			@Override
			public void noException(Exception e) {
				logger.info("订阅数据出现异常, {}", subscribe, e);
			}
		});

		client.startAsync();

		Thread.sleep(100);

		client.stopAsync();
		client.stopAsync();
	}

}
