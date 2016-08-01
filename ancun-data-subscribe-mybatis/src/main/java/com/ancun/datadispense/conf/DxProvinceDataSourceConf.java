package com.ancun.datadispense.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.ancun.datasubscribe.conf.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 分省电信数据源
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/3/21
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
//@Configuration
//@ConfigurationProperties(prefix = "dxProvinceDataSource")
//@Import({Config.class})
public class DxProvinceDataSourceConf {

    /**
     * 驱动类
     */
    private String driverClass;

    /**
     * 连接url
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 最大连接数
     */
    private int maxActive;

    /**
     * 初始化大小
     */
    private int initialSize;

    /**
     * 最小空闲数
     */
    private int minIdle;

    /**
     * maxWait获取连接等待超时的时间
     */
    private long maxWait;

    /**
     * timeBetweenEvictionRunsMillis间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    private long timeBetweenEvictionRunsMillis;

    /**
     * minEvictableIdleTimeMillis一个连接在池中最小空闲的时间，单位是毫秒
     */
    private long minEvictableIdleTimeMillis;

    /**
     * 检测语句
     */
    private String validationQuery;

    private boolean testWhileIdle;

    private boolean testOnBorrow;

    private boolean testOnReturn;

    /**
     * 共通配置类
     */
    @Resource
    private Config config;

    /**
     * 数据源连接池配置
     *
     * @return 数据源连接池
     */
    @Bean(name = "dxProvinceDataSource", destroyMethod = "close", initMethod = "init")
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setDriverClassName(driverClass);
        datasource.setUsername(username);
        String password = config.getBizPassword(this.password);
        datasource.setPassword(password);
        datasource.setMaxActive(maxActive);
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);

        return datasource;
    }

    /**
     * 分省电信jdbcTemplate
     *
     * @return
     */
    @Bean(name = "dxProvincejdbcTemplate")
    public JdbcTemplate dxProvincejdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    /**
     * 事物管理
     *
     * @return
     */
    @Bean(name = "dxProvinceTx")
    public PlatformTransactionManager dxProvinceTx() {
        return new DataSourceTransactionManager(dataSource());
    }


    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }
}
