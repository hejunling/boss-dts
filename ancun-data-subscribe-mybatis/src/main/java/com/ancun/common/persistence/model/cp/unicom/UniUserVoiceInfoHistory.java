package com.ancun.common.persistence.model.cp.unicom;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_voice_info_history")
public class UniUserVoiceInfoHistory {
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

    @Column(name = "FILESIZE")
    private Long filesize;

    @Column(name = "LICENCENO")
    private String licenceno;

    @Column(name = "RECODERREMARK")
    private String recoderremark;

    @Column(name = "CPCERTIFICATEFLG")
    private String cpcertificateflg;

    @Column(name = "CPCERTIFICATEAPPLYTIME")
    private Date cpcertificateapplytime;

    @Column(name = "CPDELFLG")
    private String cpdelflg;

    @Column(name = "DELDATETIME")
    private Date deldatetime;

    @Column(name = "CPGETFLG")
    private String cpgetflg;

    @Column(name = "ACCESSCODE")
    private String accesscode;

    @Column(name = "GETDATETIME")
    private Date getdatetime;

    @Column(name = "DUEDATETIME")
    private Date duedatetime;

    /**
     * 备案时间
     */
    @Column(name = "LICENCEDATETIME")
    private Date licencedatetime;

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
     * @return OFILENAME
     */
    public String getOfilename() {
        return ofilename;
    }

    /**
     * @param ofilename
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
     * @return FILESIZE
     */
    public Long getFilesize() {
        return filesize;
    }

    /**
     * @param filesize
     */
    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    /**
     * @return LICENCENO
     */
    public String getLicenceno() {
        return licenceno;
    }

    /**
     * @param licenceno
     */
    public void setLicenceno(String licenceno) {
        this.licenceno = licenceno;
    }

    /**
     * @return RECODERREMARK
     */
    public String getRecoderremark() {
        return recoderremark;
    }

    /**
     * @param recoderremark
     */
    public void setRecoderremark(String recoderremark) {
        this.recoderremark = recoderremark;
    }

    /**
     * @return CPCERTIFICATEFLG
     */
    public String getCpcertificateflg() {
        return cpcertificateflg;
    }

    /**
     * @param cpcertificateflg
     */
    public void setCpcertificateflg(String cpcertificateflg) {
        this.cpcertificateflg = cpcertificateflg;
    }

    /**
     * @return CPCERTIFICATEAPPLYTIME
     */
    public Date getCpcertificateapplytime() {
        return cpcertificateapplytime;
    }

    /**
     * @param cpcertificateapplytime
     */
    public void setCpcertificateapplytime(Date cpcertificateapplytime) {
        this.cpcertificateapplytime = cpcertificateapplytime;
    }

    /**
     * @return CPDELFLG
     */
    public String getCpdelflg() {
        return cpdelflg;
    }

    /**
     * @param cpdelflg
     */
    public void setCpdelflg(String cpdelflg) {
        this.cpdelflg = cpdelflg;
    }

    /**
     * @return DELDATETIME
     */
    public Date getDeldatetime() {
        return deldatetime;
    }

    /**
     * @param deldatetime
     */
    public void setDeldatetime(Date deldatetime) {
        this.deldatetime = deldatetime;
    }

    /**
     * @return CPGETFLG
     */
    public String getCpgetflg() {
        return cpgetflg;
    }

    /**
     * @param cpgetflg
     */
    public void setCpgetflg(String cpgetflg) {
        this.cpgetflg = cpgetflg;
    }

    /**
     * @return ACCESSCODE
     */
    public String getAccesscode() {
        return accesscode;
    }

    /**
     * @param accesscode
     */
    public void setAccesscode(String accesscode) {
        this.accesscode = accesscode;
    }

    /**
     * @return GETDATETIME
     */
    public Date getGetdatetime() {
        return getdatetime;
    }

    /**
     * @param getdatetime
     */
    public void setGetdatetime(Date getdatetime) {
        this.getdatetime = getdatetime;
    }

    /**
     * @return DUEDATETIME
     */
    public Date getDuedatetime() {
        return duedatetime;
    }

    /**
     * @param duedatetime
     */
    public void setDuedatetime(Date duedatetime) {
        this.duedatetime = duedatetime;
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
}