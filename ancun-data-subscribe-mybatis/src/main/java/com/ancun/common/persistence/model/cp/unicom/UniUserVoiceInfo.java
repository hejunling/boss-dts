package com.ancun.common.persistence.model.cp.unicom;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user_voice_info")
public class UniUserVoiceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Ö÷Œü
     */
    @Id
    @Column(name = "CPID")
    private Long cpid;

    /**
     * ÎÄŒþŒÇÂŒ±àºÅ
     */
    @Column(name = "RECORDNO")
    private String recordno;

    /**
     * ÓÃ»§±àºÅ
     */
    @Column(name = "IUSERNO")
    private String iuserno;

    @Column(name = "OFILENAME")
    private String ofilename;

    /**
     * ÂŒÒôÀŽÔŽ(1:ÊæÑ¶;2:°²Žæ)
     */
    @Column(name = "RECORDSOURCE")
    private String recordsource;

    /**
     * ²ŠŽò·œÊœ(1:951335;2:4000951335)
     */
    @Column(name = "CALLWAY")
    private String callway;

    /**
     * Íš»°ÀàÐÍ(1:Ö÷œÐ;2:±»œÐ)
     */
    @Column(name = "ICALLTYPE")
    private String icalltype;

    /**
     * Ö÷œÐºÅÂë
     */
    @Column(name = "CALLERNO")
    private String callerno;

    /**
     * ±»œÐºÅÂë
     */
    @Column(name = "CALLEDNO")
    private String calledno;

    /**
     * Íš»°ÆðÊŒÊ±Œä
     */
    @Column(name = "BEGINTIME")
    private Date begintime;

    /**
     * Íš»°œáÊøÊ±Œä
     */
    @Column(name = "ENDTIME")
    private Date endtime;

    /**
     * Íš»°Ê±³€(Ãë)
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

    /**
     * 是否删除录音(1:已删除;2:未删除)
     */
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
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取Ö÷Œü
     *
     * @return CPID - Ö÷Œü
     */
    public Long getCpid() {
        return cpid;
    }

    /**
     * 设置Ö÷Œü
     *
     * @param cpid Ö÷Œü
     */
    public void setCpid(Long cpid) {
        this.cpid = cpid;
    }

    /**
     * 获取ÎÄŒþŒÇÂŒ±àºÅ
     *
     * @return RECORDNO - ÎÄŒþŒÇÂŒ±àºÅ
     */
    public String getRecordno() {
        return recordno;
    }

    /**
     * 设置ÎÄŒþŒÇÂŒ±àºÅ
     *
     * @param recordno ÎÄŒþŒÇÂŒ±àºÅ
     */
    public void setRecordno(String recordno) {
        this.recordno = recordno;
    }

    /**
     * 获取ÓÃ»§±àºÅ
     *
     * @return IUSERNO - ÓÃ»§±àºÅ
     */
    public String getIuserno() {
        return iuserno;
    }

    /**
     * 设置ÓÃ»§±àºÅ
     *
     * @param iuserno ÓÃ»§±àºÅ
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
     * 获取ÂŒÒôÀŽÔŽ(1:ÊæÑ¶;2:°²Žæ)
     *
     * @return RECORDSOURCE - ÂŒÒôÀŽÔŽ(1:ÊæÑ¶;2:°²Žæ)
     */
    public String getRecordsource() {
        return recordsource;
    }

    /**
     * 设置ÂŒÒôÀŽÔŽ(1:ÊæÑ¶;2:°²Žæ)
     *
     * @param recordsource ÂŒÒôÀŽÔŽ(1:ÊæÑ¶;2:°²Žæ)
     */
    public void setRecordsource(String recordsource) {
        this.recordsource = recordsource;
    }

    /**
     * 获取²ŠŽò·œÊœ(1:951335;2:4000951335)
     *
     * @return CALLWAY - ²ŠŽò·œÊœ(1:951335;2:4000951335)
     */
    public String getCallway() {
        return callway;
    }

    /**
     * 设置²ŠŽò·œÊœ(1:951335;2:4000951335)
     *
     * @param callway ²ŠŽò·œÊœ(1:951335;2:4000951335)
     */
    public void setCallway(String callway) {
        this.callway = callway;
    }

    /**
     * 获取Íš»°ÀàÐÍ(1:Ö÷œÐ;2:±»œÐ)
     *
     * @return ICALLTYPE - Íš»°ÀàÐÍ(1:Ö÷œÐ;2:±»œÐ)
     */
    public String getIcalltype() {
        return icalltype;
    }

    /**
     * 设置Íš»°ÀàÐÍ(1:Ö÷œÐ;2:±»œÐ)
     *
     * @param icalltype Íš»°ÀàÐÍ(1:Ö÷œÐ;2:±»œÐ)
     */
    public void setIcalltype(String icalltype) {
        this.icalltype = icalltype;
    }

    /**
     * 获取Ö÷œÐºÅÂë
     *
     * @return CALLERNO - Ö÷œÐºÅÂë
     */
    public String getCallerno() {
        return callerno;
    }

    /**
     * 设置Ö÷œÐºÅÂë
     *
     * @param callerno Ö÷œÐºÅÂë
     */
    public void setCallerno(String callerno) {
        this.callerno = callerno;
    }

    /**
     * 获取±»œÐºÅÂë
     *
     * @return CALLEDNO - ±»œÐºÅÂë
     */
    public String getCalledno() {
        return calledno;
    }

    /**
     * 设置±»œÐºÅÂë
     *
     * @param calledno ±»œÐºÅÂë
     */
    public void setCalledno(String calledno) {
        this.calledno = calledno;
    }

    /**
     * 获取Íš»°ÆðÊŒÊ±Œä
     *
     * @return BEGINTIME - Íš»°ÆðÊŒÊ±Œä
     */
    public Date getBegintime() {
        return begintime;
    }

    /**
     * 设置Íš»°ÆðÊŒÊ±Œä
     *
     * @param begintime Íš»°ÆðÊŒÊ±Œä
     */
    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    /**
     * 获取Íš»°œáÊøÊ±Œä
     *
     * @return ENDTIME - Íš»°œáÊøÊ±Œä
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * 设置Íš»°œáÊøÊ±Œä
     *
     * @param endtime Íš»°œáÊøÊ±Œä
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 获取Íš»°Ê±³€(Ãë)
     *
     * @return DURATION - Íš»°Ê±³€(Ãë)
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * 设置Íš»°Ê±³€(Ãë)
     *
     * @param duration Íš»°Ê±³€(Ãë)
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

    @Override
    public String toString() {
        return "UniUserVoiceInfo{" +
                "cpid=" + cpid +
                ", recordno='" + recordno + '\'' +
                ", iuserno='" + iuserno + '\'' +
                ", ofilename='" + ofilename + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}