package com.ancun.common.persistence.model.cp.unicom;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_operatelog")
public class UniUserOperateLog {
    /**
     * Ö÷Œü
     */
    @Id
    @Column(name = "CPID")
    private Long cpid;

    /**
     * ²Ù×÷ÈÕÖŸ±àºÅ
     */
    @Column(name = "OPERATELOGNO")
    private String operatelogno;

    /**
     * µÇÂŒÀŽÔŽ
     */
    @Column(name = "LOGINSOURCE")
    private String loginsource;

    /**
     * ÓÃ»§Àà±ð
     */
    @Column(name = "USERTYPE")
    private String usertype;

    @Column(name = "OPUSER")
    private String opuser;

    /**
     * ÈÕÖŸÄÚÈÝ
     */
    @Column(name = "LOGCONTENT")
    private String logcontent;

    /**
     * ×ŽÌ¬ŽúÂë
     */
    @Column(name = "OPCODE")
    private String opcode;

    /**
     * ±ž×¢
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 请求时间
     */
    @Column(name = "OPDATETIME")
    private Date opdatetime;

    /**
     * IPµØÖ·
     */
    @Column(name = "IPADDRESS")
    private String ipaddress;

    /**
     * ÇëÇóµØÖ·
     */
    @Column(name = "REQUESTURL")
    private String requesturl;

    /**
     * ÍšÐÐÖ€±àºÅ
     */
    @Column(name = "ACCESSID")
    private String accessid;

    /**
     * ÍšÐÐÖ€ÃÜÔ¿
     */
    @Column(name = "ACCESSKEY")
    private String accesskey;

    @Column(name = "OPRECORDNO")
    private Long oprecordno;

    @Column(name = "OPCLASSNM")
    private String opclassnm;

    @Column(name = "OPMETHODNM")
    private String opmethodnm;

    @Column(name = "OPINTERFACENM")
    private String opinterfacenm;

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
     * 获取²Ù×÷ÈÕÖŸ±àºÅ
     *
     * @return OPERATELOGNO - ²Ù×÷ÈÕÖŸ±àºÅ
     */
    public String getOperatelogno() {
        return operatelogno;
    }

    /**
     * 设置²Ù×÷ÈÕÖŸ±àºÅ
     *
     * @param operatelogno ²Ù×÷ÈÕÖŸ±àºÅ
     */
    public void setOperatelogno(String operatelogno) {
        this.operatelogno = operatelogno;
    }

    /**
     * 获取µÇÂŒÀŽÔŽ
     *
     * @return LOGINSOURCE - µÇÂŒÀŽÔŽ
     */
    public String getLoginsource() {
        return loginsource;
    }

    /**
     * 设置µÇÂŒÀŽÔŽ
     *
     * @param loginsource µÇÂŒÀŽÔŽ
     */
    public void setLoginsource(String loginsource) {
        this.loginsource = loginsource;
    }

    /**
     * 获取ÓÃ»§Àà±ð
     *
     * @return USERTYPE - ÓÃ»§Àà±ð
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * 设置ÓÃ»§Àà±ð
     *
     * @param usertype ÓÃ»§Àà±ð
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    /**
     * @return OPUSER
     */
    public String getOpuser() {
        return opuser;
    }

    /**
     * @param opuser
     */
    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }

    /**
     * 获取ÈÕÖŸÄÚÈÝ
     *
     * @return LOGCONTENT - ÈÕÖŸÄÚÈÝ
     */
    public String getLogcontent() {
        return logcontent;
    }

    /**
     * 设置ÈÕÖŸÄÚÈÝ
     *
     * @param logcontent ÈÕÖŸÄÚÈÝ
     */
    public void setLogcontent(String logcontent) {
        this.logcontent = logcontent;
    }

    /**
     * 获取×ŽÌ¬ŽúÂë
     *
     * @return OPCODE - ×ŽÌ¬ŽúÂë
     */
    public String getOpcode() {
        return opcode;
    }

    /**
     * 设置×ŽÌ¬ŽúÂë
     *
     * @param opcode ×ŽÌ¬ŽúÂë
     */
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    /**
     * 获取±ž×¢
     *
     * @return REMARK - ±ž×¢
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置±ž×¢
     *
     * @param remark ±ž×¢
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取请求时间
     *
     * @return OPDATETIME - 请求时间
     */
    public Date getOpdatetime() {
        return opdatetime;
    }

    /**
     * 设置请求时间
     *
     * @param opdatetime 请求时间
     */
    public void setOpdatetime(Date opdatetime) {
        this.opdatetime = opdatetime;
    }

    /**
     * 获取IPµØÖ·
     *
     * @return IPADDRESS - IPµØÖ·
     */
    public String getIpaddress() {
        return ipaddress;
    }

    /**
     * 设置IPµØÖ·
     *
     * @param ipaddress IPµØÖ·
     */
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    /**
     * 获取ÇëÇóµØÖ·
     *
     * @return REQUESTURL - ÇëÇóµØÖ·
     */
    public String getRequesturl() {
        return requesturl;
    }

    /**
     * 设置ÇëÇóµØÖ·
     *
     * @param requesturl ÇëÇóµØÖ·
     */
    public void setRequesturl(String requesturl) {
        this.requesturl = requesturl;
    }

    /**
     * 获取ÍšÐÐÖ€±àºÅ
     *
     * @return ACCESSID - ÍšÐÐÖ€±àºÅ
     */
    public String getAccessid() {
        return accessid;
    }

    /**
     * 设置ÍšÐÐÖ€±àºÅ
     *
     * @param accessid ÍšÐÐÖ€±àºÅ
     */
    public void setAccessid(String accessid) {
        this.accessid = accessid;
    }

    /**
     * 获取ÍšÐÐÖ€ÃÜÔ¿
     *
     * @return ACCESSKEY - ÍšÐÐÖ€ÃÜÔ¿
     */
    public String getAccesskey() {
        return accesskey;
    }

    /**
     * 设置ÍšÐÐÖ€ÃÜÔ¿
     *
     * @param accesskey ÍšÐÐÖ€ÃÜÔ¿
     */
    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    /**
     * @return OPRECORDNO
     */
    public Long getOprecordno() {
        return oprecordno;
    }

    /**
     * @param oprecordno
     */
    public void setOprecordno(Long oprecordno) {
        this.oprecordno = oprecordno;
    }

    /**
     * @return OPCLASSNM
     */
    public String getOpclassnm() {
        return opclassnm;
    }

    /**
     * @param opclassnm
     */
    public void setOpclassnm(String opclassnm) {
        this.opclassnm = opclassnm;
    }

    /**
     * @return OPMETHODNM
     */
    public String getOpmethodnm() {
        return opmethodnm;
    }

    /**
     * @param opmethodnm
     */
    public void setOpmethodnm(String opmethodnm) {
        this.opmethodnm = opmethodnm;
    }

    /**
     * @return OPINTERFACENM
     */
    public String getOpinterfacenm() {
        return opinterfacenm;
    }

    /**
     * @param opinterfacenm
     */
    public void setOpinterfacenm(String opinterfacenm) {
        this.opinterfacenm = opinterfacenm;
    }
}