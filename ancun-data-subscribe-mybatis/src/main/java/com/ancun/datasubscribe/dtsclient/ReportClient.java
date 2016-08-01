package com.ancun.datasubscribe.dtsclient;

import com.google.common.collect.Maps;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 定时汇报客户端
 *
 * @Created on 2016年03月08日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class ReportClient {

    /** 应用名称 */
    private final String appName;

    /** 监控线程池大小 */
    private final int threadsForReport;

    /** 报告地址 */
    private final String address;

    /** 两成汇报的间隔时间 */
    private final long period;

    /** 通知人地址 */
    private final String emails;

    /** 定时汇报线程池 */
    private final Executor reportService;

    /** httpclient */
    private final RestTemplate restTemplate;

    /** 汇报时间点 */
    private volatile long reportTime = 0;

    @Autowired
    public ReportClient(
            @Value("${info.app.name:数据订阅中心}") String appName,
            @Value("${report.threadsForReport:3}") int threadsForReport,
            @Value("${report.address:http://localhost:8080/health}")String address,
            @Value("${report.period:5}") long period,
            @Value("${report.emails:xieyushi@ancun.com}") String emails) {
        this.appName = appName;
        this.threadsForReport = threadsForReport;
        this.address = address;
        this.period = period;
        this.emails = emails;

        this.reportService = Executors.newFixedThreadPool(this.threadsForReport);
        this.restTemplate = new RestTemplate(httpRequestFactory());
    }

    /**
     * 向监控服务器汇报
     *
     * @param subscribeName 通道名称
     */
    public void report(final String subscribeName){

        // 设置汇报时间起始点
        if (reportTime == 0) {
            reportTime = System.currentTimeMillis();
        }

        // 当前时间点
        long currentTime = System.currentTimeMillis();

        // 每隔指定秒数发送一次汇报
        if (currentTime - reportTime == period * 1000) {
            // 汇报时间点置为当前时间点
            reportTime = currentTime;

            // 向监控服务器汇报当前状态
            this.reportService.execute(new Runnable() {
                @Override
                public void run() {

                    Map<String, String> reportParams = Maps.newHashMap();
                    // 项目名称(应用名称 + 通道)
                    String monitorName = appName + subscribeName;
                    reportParams.put("monitorName", monitorName);
                    // 项目说明
                    reportParams.put("monitorRemark", monitorName);
                    // 通知人邮件地址
                    reportParams.put("emails", emails);

                    restTemplate.getForObject(address, String.class, reportParams);
                }
            });
        }
    }

    public int getThreadsForReport() {
        return threadsForReport;
    }

    public String getAddress() {
        return address;
    }

    public long getPeriod() {
        return period;
    }

    private ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    private HttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        // Get the poolMaxTotal value from our application[-?].yml or default to 10 if not explicitly set
        connectionManager.setMaxTotal(this.threadsForReport);

        return HttpClientBuilder
                .create()
                .setConnectionManager(connectionManager)
                .build();
    }
}
