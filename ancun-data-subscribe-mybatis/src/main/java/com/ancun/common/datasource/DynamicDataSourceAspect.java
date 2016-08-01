package com.ancun.common.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切换数据源Advice
 *
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年1月23日
 */
@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * 切入有{@link TargetDataSource}注解的类的方法
     * 将数据源切换成注解上指定的数据源
     *
     * @param point     父类，提供获取拦截方法的信息的功能，如所有参数：jp.getArgs()
     * @param ds        注解
     * @throws Throwable
     */
    @Before("@within(ds)")
    public void changeDataSourceByType(JoinPoint point, TargetDataSource ds) throws Throwable {
        this.dataSourceChange(point, ds);
    }

    /**
     * 切入有{@link TargetDataSource}注解的方法
     * 将数据源切换成注解上指定的数据源
     *
     * @param point     父类，提供获取拦截方法的信息的功能，如所有参数：jp.getArgs()
     * @param ds        注解
     * @throws Throwable
     */
    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
        this.dataSourceChange(point, ds);
    }

    /**
     * 切入有{@link TargetDataSource}注解的方法
     * 将数据源切换成注解上指定的数据源
     *
     * @param point     父类，提供获取拦截方法的信息的功能，如所有参数：jp.getArgs()
     * @param ds        注解
     */
    private boolean dataSourceChange(JoinPoint point, TargetDataSource ds) {
        String dsId = ds.name();
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            logger.error("数据源[{}]不存在，使用默认数据源 > {}", ds.name(), point.getSignature());
        } else {
            logger.debug("Use DataSource : {} > {}", ds.name(), point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(ds.name());
        }
        return true;
    }

    /**
     * 切入方法所属类上有{@link TargetDataSource}注解的方法
     * 将数据源切换成注解上指定的数据源
     *
     * @param point     父类，提供获取拦截方法的信息的功能，如所有参数：jp.getArgs()
     * @param ds        注解
     */
    @After("@within(ds)")
    public void restoreDataSourceOnType(JoinPoint point, TargetDataSource ds) {
        logger.debug("Revert DataSource : {} > {}", ds.name(), point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

    /**
     * 切入方法所属类上有{@link TargetDataSource}注解的方法
     * 将数据源切换成注解上指定的数据源
     *
     * @param point     父类，提供获取拦截方法的信息的功能，如所有参数：jp.getArgs()
     * @param ds        注解
     */
    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
        logger.debug("Revert DataSource : {} > {}", ds.name(), point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

}
