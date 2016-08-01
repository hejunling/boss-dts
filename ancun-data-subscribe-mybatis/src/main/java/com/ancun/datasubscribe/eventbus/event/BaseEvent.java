package com.ancun.datasubscribe.eventbus.event;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 基础业务事件
 *
 * @Created on 2016年03月03日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class BaseEvent {

    /** 涉及变更表的列表 */
    private List<TableChangeDetail> tableChangeList = Lists.newArrayList();

    /** 变更表数 */
    private int tableChangeCount = 0;

    /** 该业务主表 */
    private String tableName;

    /** 扩展字段 (用户信息同步业务 扩展字段有["UNIQUENO" : "", "BIZ_NO" : "", "USER_TYPE" : ""]) */
    private Map<String, String> extMap = Maps.newHashMap();

    /** 是否设置完成 */
    private boolean fullPackage = false;

    /**
     * 添加一个变更信息
     *
     * @param tableChangeDetail 变更信息
     */
    public BaseEvent addTableChange(TableChangeDetail tableChangeDetail){
        this.tableChangeList.add(tableChangeDetail);
        this.tableChangeCount += 1;
        return this;
    }

    /**
     * 添加一组变更信息
     *
     * @param tableChangeList   变更信息列表
     */
    public BaseEvent addTableChangeList(List<TableChangeDetail> tableChangeList) {
        this.tableChangeList.addAll(tableChangeList);
        this.tableChangeCount += tableChangeList.size();
        return this;
    }

    public List<TableChangeDetail> getTableChangeList() {
        return tableChangeList;
    }

    public int getTableChangeCount() {
        return tableChangeCount;
    }

    public void setTableChangeCount(int tableChangeCount) {
        this.tableChangeCount = tableChangeCount;
    }

    public String getTableName() {
        return tableName;
    }

    public BaseEvent setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Map<String, String> getExtMap() {
        return extMap;
    }

    public BaseEvent setExtMap(Map<String, String> extMap) {
        this.extMap = extMap;
        return this;
    }

    /**
     * 添加扩展
     *
     * @param key   键
     * @param value 值
     * @return      自己本身
     */
    public BaseEvent addExt(String key, String value) {
        this.extMap.put(key, value);
        return this;
    }

    public boolean isFullPackage() {
        return fullPackage;
    }

    public BaseEvent setFullPackage(boolean fullPackage) {
        this.fullPackage = fullPackage;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("事件详情[" + this.getClass().getSimpleName() + "]")
                .add("tableName", tableName)
                .add("extMap", extMap)
                .add("fullPackage", fullPackage)
                .add("tableChangeCount", tableChangeCount)
                .add("tableChangeList", tableChangeList)
                .toString();
    }
}
