package com.ancun.datasubscribe.util.bizeventbus;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 广播事件实体
 *
 * @Created on 2016年03月09日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class PostEvent {

    public static final String DEFAULT = "default";

    private String tableName = DEFAULT;
//    private String bizNo = DEFAULT;
    private String userType = DEFAULT;
    private Object event;

    public static PostEvent builde(){
        return new PostEvent();
    }

    public PostEvent() {
    }

    public String getTableName() {
        return tableName;
    }

    public PostEvent setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

//    public String getBizNo() {
//        return bizNo;
//    }
//
//    public PostEvent setBizNo(String bizNo) {
//        this.bizNo = checkNotNull(bizNo);
//        return this;
//    }

    public String getUserType() {
        return userType;
    }

    public PostEvent setUserType(String userType) {
        this.userType = checkNotNull(userType);
        return this;
    }

    public Object getEvent() {
        return event;
    }

    public PostEvent setEvent(Object event) {
        this.event = checkNotNull(event);
        return this;
    }

    @Override
    public int hashCode() {
//        return Objects.hashCode(bizNo, userType, event);
        return Objects.hashCode(userType, event);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o instanceof PostEvent) {
            PostEvent ident = (PostEvent) o;
//            return bizNo.equals(ident.bizNo) && userType.equals(ident.userType) && Objects.equal(event, ident.event);
            return userType.equals(ident.userType) && Objects.equal(event, ident.event);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("PostEvent")
//                .add("bizNo", bizNo)
                .add("userType", userType)
                .add("event", event)
                .toString();
    }

}
