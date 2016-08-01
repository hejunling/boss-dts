package com.ancun.common.persistence.model.master;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Consume {
    /**
     * 变更记录ID
     */
    @Id
    @Column(name = "RECORDE_ID")
    private String recordeId;

    /**
     * 从DTS推送到数据订阅组件次数
     */
    @Column(name = "PUSH_COUNT")
    private Integer pushCount;

    /**
     * 变更记录对应的MySQL实例运行进程的IP:PORT
     */
    @Column(name = "SERVER_ID")
    private String serverId;

    /**
     * 变更记录在binlog中的位点，返回的位点格式为binlog_offset@binlog_fid。其中binlog_offset为变更记录在binlog文件中的偏移量，binlog_fid为binlog文件的数字后缀，例如binlog文件名为mysql-bin.0008，那么binlog_fid为8。
     */
    @Column(name = "CHECKPOINT")
    private String checkpoint;

    /**
     * 变更记录在binlog中记录的运行的时间戳
     */
    @Column(name = "TIMESTAMP")
    private String timestamp;

    /**
     * 变更记录修改的表所在的数据库名
     */
    @Column(name = "DB_NAME")
    private String dbName;

    /**
     * 变更表名
     */
    @Column(name = "TABLE_NAME")
    private String tableName;

    /**
     * 记录的操作类型（0：insert，1：update，2：delete）
     */
    @Column(name = "ACT_TYPE")
    private Integer actType;

    /**
     * 业务编号
     */
    @Column(name = "BIZ_NO")
    private String bizNo;

    /**
     * 用户类型
     */
    @Column(name = "USER_TYPE")
    private String userType;

    /**
     * 状态（0消费中，1消费成功，2消费失败）
     */
    @Column(name = "STATUS")
    private Integer status;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 添加日期
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 更新日期
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 获取变更记录ID
     *
     * @return RECORDE_ID - 变更记录ID
     */
    public String getRecordeId() {
        return recordeId;
    }

    /**
     * 设置变更记录ID
     *
     * @param recordeId 变更记录ID
     */
    public void setRecordeId(String recordeId) {
        this.recordeId = recordeId;
    }

    /**
     * 获取从DTS推送到数据订阅组件次数
     *
     * @return PUSH_COUNT - 从DTS推送到数据订阅组件次数
     */
    public Integer getPushCount() {
        return pushCount;
    }

    /**
     * 设置从DTS推送到数据订阅组件次数
     *
     * @param pushCount 从DTS推送到数据订阅组件次数
     */
    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

    /**
     * 获取变更记录对应的MySQL实例运行进程的IP:PORT
     *
     * @return SERVER_ID - 变更记录对应的MySQL实例运行进程的IP:PORT
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * 设置变更记录对应的MySQL实例运行进程的IP:PORT
     *
     * @param serverId 变更记录对应的MySQL实例运行进程的IP:PORT
     */
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    /**
     * 获取变更记录在binlog中的位点，返回的位点格式为binlog_offset@binlog_fid。其中binlog_offset为变更记录在binlog文件中的偏移量，binlog_fid为binlog文件的数字后缀，例如binlog文件名为mysql-bin.0008，那么binlog_fid为8。
     *
     * @return CHECKPOINT - 变更记录在binlog中的位点，返回的位点格式为binlog_offset@binlog_fid。其中binlog_offset为变更记录在binlog文件中的偏移量，binlog_fid为binlog文件的数字后缀，例如binlog文件名为mysql-bin.0008，那么binlog_fid为8。
     */
    public String getCheckpoint() {
        return checkpoint;
    }

    /**
     * 设置变更记录在binlog中的位点，返回的位点格式为binlog_offset@binlog_fid。其中binlog_offset为变更记录在binlog文件中的偏移量，binlog_fid为binlog文件的数字后缀，例如binlog文件名为mysql-bin.0008，那么binlog_fid为8。
     *
     * @param checkpoint 变更记录在binlog中的位点，返回的位点格式为binlog_offset@binlog_fid。其中binlog_offset为变更记录在binlog文件中的偏移量，binlog_fid为binlog文件的数字后缀，例如binlog文件名为mysql-bin.0008，那么binlog_fid为8。
     */
    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }

    /**
     * 获取变更记录在binlog中记录的运行的时间戳
     *
     * @return TIMESTAMP - 变更记录在binlog中记录的运行的时间戳
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * 设置变更记录在binlog中记录的运行的时间戳
     *
     * @param timestamp 变更记录在binlog中记录的运行的时间戳
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 获取变更记录修改的表所在的数据库名
     *
     * @return DB_NAME - 变更记录修改的表所在的数据库名
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * 设置变更记录修改的表所在的数据库名
     *
     * @param dbName 变更记录修改的表所在的数据库名
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * 获取变更表名
     *
     * @return TABLE_NAME - 变更表名
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置变更表名
     *
     * @param tableName 变更表名
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取记录的操作类型（0：insert，1：update，2：delete）
     *
     * @return ACT_TYPE - 记录的操作类型（0：insert，1：update，2：delete）
     */
    public Integer getActType() {
        return actType;
    }

    /**
     * 设置记录的操作类型（0：insert，1：update，2：delete）
     *
     * @param actType 记录的操作类型（0：insert，1：update，2：delete）
     */
    public void setActType(Integer actType) {
        this.actType = actType;
    }

    /**
     * 获取业务编号
     *
     * @return BIZ_NO - 业务编号
     */
    public String getBizNo() {
        return bizNo;
    }

    /**
     * 设置业务编号
     *
     * @param bizNo 业务编号
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    /**
     * 获取用户类型
     *
     * @return USER_TYPE - 用户类型
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 设置用户类型
     *
     * @param userType 用户类型
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 获取状态（0消费中，1消费成功，2消费失败）
     *
     * @return STATUS - 状态（0消费中，1消费成功，2消费失败）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态（0消费中，1消费成功，2消费失败）
     *
     * @param status 状态（0消费中，1消费成功，2消费失败）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return REMARK - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取添加日期
     *
     * @return CREATE_TIME - 添加日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置添加日期
     *
     * @param createTime 添加日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新日期
     *
     * @return UPDATE_TIME - 更新日期
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新日期
     *
     * @param updateTime 更新日期
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("消费记录信息[Consume]")
                .add("recordeId", recordeId)
                .add("pushCount", pushCount)
                .add("serverId", serverId)
                .add("checkpoint", checkpoint)
                .add("timestamp", timestamp)
                .add("dbName", dbName)
                .add("tableName", tableName)
                .add("actType", actType)
                .add("bizNo", bizNo)
                .add("userType", userType)
                .add("status", status)
                .add("remark", remark)
                .add("createTime", createTime)
                .add("updateTime", updateTime)
                .toString();
    }
}