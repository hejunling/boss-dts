package com.ancun.datasubscribe.util.bizeventbus;

import com.google.common.annotations.Beta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 监听器标识注解(参照guava 19.0的EventBus)
 *
 * @Created on 2016年03月03日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Beta
public @interface Subscribe {

    /** 数据变更表名 */
    String tableName() default PostEvent.DEFAULT;

    /** 子业务类别 */
    String bizNo() default PostEvent.DEFAULT;

    /** 用户类型 */
    String userType() default PostEvent.DEFAULT;
}
