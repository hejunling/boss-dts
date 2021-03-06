package com.ancun.common.persistence.model.cp.telecom;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_voice_info_history")
public class TelUserVoiceInfoHistory {
    /**
     * 主键
     */
    @Id
    @Column(name = "CPID")
    private Long cpid;

    /**
     * 文件记录编号
     */
    @Column(name = "RECORDNO")
    private String recordno;

    /**
     * 用户编号
     */
    @Column(name = "IUSERNO")
    private String iuserno;

    /**
     * 原文件名
     */
    @Column(name = "OFILENAME")
    private String ofilename;

    /**
     * 录音来源(1:舒讯;2:安存)
     */
    @Column(name = "RECORDSOURCE")
    private String recordsource;

    /**
     * 拨打方式(1:951335;2:4000951335)
     */
    @Column(name = "CALLWAY")
    private String callway;

    /**
     * 通话类型(1:主叫;2:被叫)
     */
    @Column(name = "ICALLTYPE")
    private String icalltype;

    /**
     * 主叫号码
     */
    @Column(name = "CALLERNO")
    private String callerno;

    /**
     * 被叫号码
     */
    @Column(name = "CALLEDNO")
    private String calledno;

    /**
     * 通话起始时间
     */
    @Column(name = "BEGINTIME")
    private Date begintime;

    /**
     * 通话结束时间
     */
    @Column(name = "ENDTIME")
    private Date endtime;

    /**
     * 通话时长(秒)
     */
    @Column(name = "DURATION")
    private Long duration;

    /**
     * 文件大小
     */
    @Column(name = "FILESIZE")
    private Long filesize;

    /**
     * 备案号
     */
    @Column(name = "LICENCENO")
    private String licenceno;

    /**
     * 备案时间
     */
    @Column(name = "LICENCEDATETIME")
    private Date licencedatetime;

    /**
     * 录音说明
     */
    @Column(name = "RECODERREMARK")
    private String recoderremark;

    /**
     * 是否申请公证(1:已申请;2:未申请)
     */
    @Column(name = "CPCERTIFICATEFLG")
    private String cpcertificateflg;

    /**
     * 最新申请公证时间
     */
    @Column(name = "CPCERTIFICATEAPPLYTIME")
    private Date cpcertificateapplytime;

    /**
     * 是否删除录音(1:已删除;2:未删除)
     */
    @Column(name = "CPDELFLG")
    private String cpdelflg;

    /**
     * 最新删除时间
     */
    @Column(name = "DELDATETIME")
    private Date deldatetime;

    /**
     * 提取码是否有效(1:有效;2:无效)
     */
    @Column(name = "CPGETFLG")
    private String cpgetflg;

    /**
     * 提取码
     */
    @Column(name = "ACCESSCODE")
    private String accesscode;

    /**
     * 最新提取时间
     */
    @Column(name = "GETDATETIME")
    private Date getdatetime;

    /**
     * 到期时间
     */
    @Column(name = "DUEDATETIME")
    private Date duedatetime;

    /**
     * 获取主键
     *
     * @return CPID - 主键
     */
    public Long getCpid() {
        return cpid;
    }

    /**
     * 设置主键
     *
     * @param cpid 主键
     */
    public void setCpid(Long cpid) {
        this.cpid = cpid;
    }

    /**
     * 获取文件记录编号
     *
     * @return RECORDNO - 文件记录编号
     */
    public String getRecordno() {
        return recordno;
    }

    /**
     * 设置文件记录编号
     *
     * @param recordno 文件记录编号
     */
    public void setRecordno(String recordno) {
        this.recordno = recordno;
    }

    /**
     * 获取用户编号
     *
     * @return IUSERNO - 用户编号
     */
    public String getIuserno() {
        return iuserno;
    }

    /**
     * 设置用户编号
     *
     * @param iuserno 用户编号
     */
    public void setIuserno(String iuserno) {
        this.iuserno = iuserno;
    }

    /**
     * 获取原文件名
     *
     * @return OFILENAME - 原文件名
     */
    public String getOfilename() {
        return ofilename;
    }

    /**
     * 设置原文件名
     *
     * @param ofilename 原文件名
     */
    public void setOfilename(String ofilename) {
        this.ofilename = ofilename;
    }

    /**
     * 获取录音来源(1:舒讯;2:安存)
     *
     * @return RECORDSOURCE - 录音来源(1:舒讯;2:安存)
     */
    public String getRecordsource() {
        return recordsource;
    }

    /**
     * 设置录音来源(1:舒讯;2:安存)
     *
     * @param recordsource 录音来源(1:舒讯;2:安存)
     */
    public void setRecordsource(String recordsource) {
        this.recordsource = recordsource;
    }

    /**
     * 获取拨打方式(1:951335;2:4000951335)
     *
     * @return CALLWAY - 拨打方式(1:951335;2:4000951335)
     */
    public String getCallway() {
        return callway;
    }

    /**
     * 设置拨打方式(1:951335;2:4000951335)
     *
     * @param callway 拨打方式(1:951335;2:4000951335)
     */
    public void setCallway(String callway) {
        this.callway = callway;
    }

    /**
     * 获取通话类型(1:主叫;2:被叫)
     *
     * @return ICALLTYPE - 通话类型(1:主叫;2:被叫)
     */
    public String getIcalltype() {
        return icalltype;
    }

    /**
     * 设置通话类型(1:主叫;2:被叫)
     *
     * @param icalltype 通话类型(1:主叫;2:被叫)
     */
    public void setIcalltype(String icalltype) {
        this.icalltype = icalltype;
    }

    /**
     * 获取主叫号码
     *
     * @return CALLERNO - 主叫号码
     */
    public String getCallerno() {
        return callerno;
    }

    /**
     * 设置主叫号码
     *
     * @param callerno 主叫号码
     */
    public void setCallerno(String callerno) {
        this.callerno = callerno;
    }

    /**
     * 获取被叫号码
     *
     * @return CALLEDNO - 被叫号码
     */
    public String getCalledno() {
        return calledno;
    }

    /**
     * 设置被叫号码
     *
     * @param calledno 被叫号码
     */
    public void setCalledno(String calledno) {
        this.calledno = calledno;
    }

    /**
     * 获取通话起始时间
     *
     * @return BEGINTIME - 通话起始时间
     */
    public Date getBegintime() {
        return begintime;
    }

    /**
     * 设置通话起始时间
     *
     * @param begintime 通话起始时间
     */
    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    /**
     * 获取通话结束时间
     *
     * @return ENDTIME - 通话结束时间
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * 设置通话结束时间
     *
     * @param endtime 通话结束时间
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 获取通话时长(秒)
     *
     * @return DURATION - 通话时长(秒)
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * 设置通话时长(秒)
     *
     * @param duration 通话时长(秒)
     */
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    /**
     * 获取文件大小
     *
     * @return FILESIZE - 文件大小
     */
    public Long getFilesize() {
        return filesize;
    }

    /**
     * 设置文件大小
     *
     * @param filesize 文件大小
     */
    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    /**
     * 获取备案号
     *
     * @return LICENCENO - 备案号
     */
    public String getLicenceno() {
        return licenceno;
    }

    /**
     * 设置备案号
     *
     * @param licenceno 备案号
     */
    public void setLicenceno(String licenceno) {
        this.licenceno = licenceno;
    }

    /**
     * 获取备案时间
     *
     * @return LICENCEDATETIME - 备案时间
     */
    public Date getLicencedatetime() {
        return licencedatetime;
    }

    /**
     * 设置备案时间
     *
     * @param licencedatetime 备案时间
     */
    public void setLicencedatetime(Date licencedatetime) {
        this.licencedatetime = licencedatetime;
    }

    /**
     * 获取录音说明
     *
     * @return RECODERREMARK - 录音说明
     */
    public String getRecoderremark() {
        return recoderremark;
    }

    /**
     * 设置录音说明
     *
     * @param recoderremark 录音说明
     */
    public void setRecoderremark(String recoderremark) {
        this.recoderremark = recoderremark;
    }

    /**
     * 获取是否申请公证(1:已申请;2:未申请)
     *
     * @return CPCERTIFICATEFLG - 是否申请公证(1:已申请;2:未申请)
     */
    public String getCpcertificateflg() {
        return cpcertificateflg;
    }

    /**
     * 设置是否申请公证(1:已申请;2:未申请)
     *
     * @param cpcertificateflg 是否申请公证(1:已申请;2:未申请)
     */
    public void setCpcertificateflg(String cpcertificateflg) {
        this.cpcertificateflg = cpcertificateflg;
    }

    /**
     * 获取最新申请公证时间
     *
     * @return CPCERTIFICATEAPPLYTIME - 最新申请公证时间
     */
    public Date getCpcertificateapplytime() {
        return cpcertificateapplytime;
    }

    /**
     * 设置最新申请公证时间
     *
     * @param cpcertificateapplytime 最新申请公证时间
     */
    public void setCpcertificateapplytime(Date cpcertificateapplytime) {
        this.cpcertificateapplytime = cpcertificateapplytime;
    }

    /**
     * 获取是否删除录音(1:已删除;2:未删除)
     *
     * @return CPDELFLG - 是否删除录音(1:已删除;2:未删除)
     */
    public String getCpdelflg() {
        return cpdelflg;
    }

    /**
     * 设置是否删除录音(1:已删除;2:未删除)
     *
     * @param cpdelflg 是否删除录音(1:已删除;2:未删除)
     */
    public void setCpdelflg(String cpdelflg) {
        this.cpdelflg = cpdelflg;
    }

    /**
     * 获取最新删除时间
     *
     * @return DELDATETIME - 最新删除时间
     */
    public Date getDeldatetime() {
        return deldatetime;
    }

    /**
     * 设置最新删除时间
     *
     * @param deldatetime 最新删除时间
     */
    public void setDeldatetime(Date deldatetime) {
        this.deldatetime = deldatetime;
    }

    /**
     * 获取提取码是否有效(1:有效;2:无效)
     *
     * @return CPGETFLG - 提取码是否有效(1:有效;2:无效)
     */
    public String getCpgetflg() {
        return cpgetflg;
    }

    /**
     * 设置提取码是否有效(1:有效;2:无效)
     *
     * @param cpgetflg 提取码是否有效(1:有效;2:无效)
     */
    public void setCpgetflg(String cpgetflg) {
        this.cpgetflg = cpgetflg;
    }

    /**
     * 获取提取码
     *
     * @return ACCESSCODE - 提取码
     */
    public String getAccesscode() {
        return accesscode;
    }

    /**
     * 设置提取码
     *
     * @param accesscode 提取码
     */
    public void setAccesscode(String accesscode) {
        this.accesscode = accesscode;
    }

    /**
     * 获取最新提取时间
     *
     * @return GETDATETIME - 最新提取时间
     */
    public Date getGetdatetime() {
        return getdatetime;
    }

    /**
     * 设置最新提取时间
     *
     * @param getdatetime 最新提取时间
     */
    public void setGetdatetime(Date getdatetime) {
        this.getdatetime = getdatetime;
    }

    /**
     * 获取到期时间
     *
     * @return DUEDATETIME - 到期时间
     */
    public Date getDuedatetime() {
        return duedatetime;
    }

    /**
     * 设置到期时间
     *
     * @param duedatetime 到期时间
     */
    public void setDuedatetime(Date duedatetime) {
        this.duedatetime = duedatetime;
    }
}