package com.ancun.common.persistence.model.dx;

import java.util.Date;
import javax.persistence.*;

@Table(name = "USER_FILE")
public class UserFile {
    /**
     * 文件编号
     */
    @Id
    @Column(name = "PF_RECORD_NO")
    private String pfRecordNo;

    /**
     * 用户编号
     */
    @Column(name = "PF_NOTARY_USER_NO")
    private String pfNotaryUserNo;

    /**
     * 省份代码
     */
    @Column(name = "RPCODE")
    private String rpcode;

    /**
     * 城市代码
     */
    @Column(name = "CITYCODE")
    private String citycode;

    /**
     * 文件原始名称
     */
    @Column(name = "PF_FILE_ORIGINAL_NAME")
    private String pfFileOriginalName;

    /**
     * 文件名称
     */
    @Column(name = "PF_FILE_NAME")
    private String pfFileName;

    /**
     * 文件MD5值
     */
    @Column(name = "PF_FILE_MD5")
    private String pfFileMd5;

    /**
     * 文件大小
     */
    @Column(name = "PF_FILE_SIZE")
    private Long pfFileSize;

    /**
     * 文件类型(1文档、2图片、3视频)
     */
    @Column(name = "PF_FILE_TYPE")
    private String pfFileType;

    /**
     * 上传时间
     */
    @Column(name = "PF_UPLOAD_TIME")
    private Date pfUploadTime;

    /**
     * 文件状态（1正常 2上传中）
     */
    @Column(name = "PF_STATUS")
    private String pfStatus;

    /**
     * OSS文件编号
     */
    @Column(name = "PF_CLOUD_FILE_NO")
    private String pfCloudFileNo;

    /**
     * 备注
     */
    @Column(name = "PF_REMARK")
    private String pfRemark;

    /**
     * OSS文件MD5值
     */
    @Column(name = "PF_CLOUD_FILE_MD5")
    private String pfCloudFileMd5;

    /**
     * OSS文件大小
     */
    @Column(name = "PF_CLOUD_FILE_SIZE")
    private Long pfCloudFileSize;

    /**
     * 存储路径
     */
    @Column(name = "PF_STOR_PATH")
    private String pfStorPath;

    /**
     * 存储名称
     */
    @Column(name = "PF_STOR_FILE_NAME")
    private String pfStorFileName;

    /**
     * 文件扩展名
     */
    @Column(name = "FILEEXTENSION")
    private String fileextension;

    /**
     * 获取文件编号
     *
     * @return PF_RECORD_NO - 文件编号
     */
    public String getPfRecordNo() {
        return pfRecordNo;
    }

    /**
     * 设置文件编号
     *
     * @param pfRecordNo 文件编号
     */
    public void setPfRecordNo(String pfRecordNo) {
        this.pfRecordNo = pfRecordNo;
    }

    /**
     * 获取用户编号
     *
     * @return PF_NOTARY_USER_NO - 用户编号
     */
    public String getPfNotaryUserNo() {
        return pfNotaryUserNo;
    }

    /**
     * 设置用户编号
     *
     * @param pfNotaryUserNo 用户编号
     */
    public void setPfNotaryUserNo(String pfNotaryUserNo) {
        this.pfNotaryUserNo = pfNotaryUserNo;
    }

    /**
     * 获取省份代码
     *
     * @return RPCODE - 省份代码
     */
    public String getRpcode() {
        return rpcode;
    }

    /**
     * 设置省份代码
     *
     * @param rpcode 省份代码
     */
    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }

    /**
     * 获取城市代码
     *
     * @return CITYCODE - 城市代码
     */
    public String getCitycode() {
        return citycode;
    }

    /**
     * 设置城市代码
     *
     * @param citycode 城市代码
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    /**
     * 获取文件原始名称
     *
     * @return PF_FILE_ORIGINAL_NAME - 文件原始名称
     */
    public String getPfFileOriginalName() {
        return pfFileOriginalName;
    }

    /**
     * 设置文件原始名称
     *
     * @param pfFileOriginalName 文件原始名称
     */
    public void setPfFileOriginalName(String pfFileOriginalName) {
        this.pfFileOriginalName = pfFileOriginalName;
    }

    /**
     * 获取文件名称
     *
     * @return PF_FILE_NAME - 文件名称
     */
    public String getPfFileName() {
        return pfFileName;
    }

    /**
     * 设置文件名称
     *
     * @param pfFileName 文件名称
     */
    public void setPfFileName(String pfFileName) {
        this.pfFileName = pfFileName;
    }

    /**
     * 获取文件MD5值
     *
     * @return PF_FILE_MD5 - 文件MD5值
     */
    public String getPfFileMd5() {
        return pfFileMd5;
    }

    /**
     * 设置文件MD5值
     *
     * @param pfFileMd5 文件MD5值
     */
    public void setPfFileMd5(String pfFileMd5) {
        this.pfFileMd5 = pfFileMd5;
    }

    /**
     * 获取文件大小
     *
     * @return PF_FILE_SIZE - 文件大小
     */
    public Long getPfFileSize() {
        return pfFileSize;
    }

    /**
     * 设置文件大小
     *
     * @param pfFileSize 文件大小
     */
    public void setPfFileSize(Long pfFileSize) {
        this.pfFileSize = pfFileSize;
    }

    /**
     * 获取文件类型(1文档、2图片、3视频)
     *
     * @return PF_FILE_TYPE - 文件类型(1文档、2图片、3视频)
     */
    public String getPfFileType() {
        return pfFileType;
    }

    /**
     * 设置文件类型(1文档、2图片、3视频)
     *
     * @param pfFileType 文件类型(1文档、2图片、3视频)
     */
    public void setPfFileType(String pfFileType) {
        this.pfFileType = pfFileType;
    }

    /**
     * 获取上传时间
     *
     * @return PF_UPLOAD_TIME - 上传时间
     */
    public Date getPfUploadTime() {
        return pfUploadTime;
    }

    /**
     * 设置上传时间
     *
     * @param pfUploadTime 上传时间
     */
    public void setPfUploadTime(Date pfUploadTime) {
        this.pfUploadTime = pfUploadTime;
    }

    /**
     * 获取文件状态（1正常 2上传中）
     *
     * @return PF_STATUS - 文件状态（1正常 2上传中）
     */
    public String getPfStatus() {
        return pfStatus;
    }

    /**
     * 设置文件状态（1正常 2上传中）
     *
     * @param pfStatus 文件状态（1正常 2上传中）
     */
    public void setPfStatus(String pfStatus) {
        this.pfStatus = pfStatus;
    }

    /**
     * 获取OSS文件编号
     *
     * @return PF_CLOUD_FILE_NO - OSS文件编号
     */
    public String getPfCloudFileNo() {
        return pfCloudFileNo;
    }

    /**
     * 设置OSS文件编号
     *
     * @param pfCloudFileNo OSS文件编号
     */
    public void setPfCloudFileNo(String pfCloudFileNo) {
        this.pfCloudFileNo = pfCloudFileNo;
    }

    /**
     * 获取备注
     *
     * @return PF_REMARK - 备注
     */
    public String getPfRemark() {
        return pfRemark;
    }

    /**
     * 设置备注
     *
     * @param pfRemark 备注
     */
    public void setPfRemark(String pfRemark) {
        this.pfRemark = pfRemark;
    }

    /**
     * 获取OSS文件MD5值
     *
     * @return PF_CLOUD_FILE_MD5 - OSS文件MD5值
     */
    public String getPfCloudFileMd5() {
        return pfCloudFileMd5;
    }

    /**
     * 设置OSS文件MD5值
     *
     * @param pfCloudFileMd5 OSS文件MD5值
     */
    public void setPfCloudFileMd5(String pfCloudFileMd5) {
        this.pfCloudFileMd5 = pfCloudFileMd5;
    }

    /**
     * 获取OSS文件大小
     *
     * @return PF_CLOUD_FILE_SIZE - OSS文件大小
     */
    public Long getPfCloudFileSize() {
        return pfCloudFileSize;
    }

    /**
     * 设置OSS文件大小
     *
     * @param pfCloudFileSize OSS文件大小
     */
    public void setPfCloudFileSize(Long pfCloudFileSize) {
        this.pfCloudFileSize = pfCloudFileSize;
    }

    /**
     * 获取存储路径
     *
     * @return PF_STOR_PATH - 存储路径
     */
    public String getPfStorPath() {
        return pfStorPath;
    }

    /**
     * 设置存储路径
     *
     * @param pfStorPath 存储路径
     */
    public void setPfStorPath(String pfStorPath) {
        this.pfStorPath = pfStorPath;
    }

    /**
     * 获取存储名称
     *
     * @return PF_STOR_FILE_NAME - 存储名称
     */
    public String getPfStorFileName() {
        return pfStorFileName;
    }

    /**
     * 设置存储名称
     *
     * @param pfStorFileName 存储名称
     */
    public void setPfStorFileName(String pfStorFileName) {
        this.pfStorFileName = pfStorFileName;
    }

    /**
     * 获取文件扩展名
     *
     * @return FILEEXTENSION - 文件扩展名
     */
    public String getFileextension() {
        return fileextension;
    }

    /**
     * 设置文件扩展名
     *
     * @param fileextension 文件扩展名
     */
    public void setFileextension(String fileextension) {
        this.fileextension = fileextension;
    }
}