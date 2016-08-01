package com.ancun.datasyn.util;

import com.ancun.common.persistence.model.cp.telecom.TelUserInfo;
import com.ancun.common.persistence.model.cp.unicom.UniUserInfo;
import com.ancun.common.persistence.model.dx.EntUserInfo;
import com.ancun.common.persistence.model.dx.UserInfo;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.common.persistence.model.sh.ShBizUserInfo;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 数据同步数据对象转换工具类
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/5/18
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class SynPojoUtil {

    /**
     * 分省个人用户转换BOSS用户  BIZ_USER_INFO
     *
     * @param userInfo
     * @param dxProvinceMap
     * @return
     */
    public static BizUserInfo bizUserInfoTransform4ProvincePerson(UserInfo userInfo, Map<String, String> dxProvinceMap) {
        BizUserInfo bizUserInfo = new BizUserInfo();

        // update by zkai on 2016/05/27
        String bizno = dxProvinceMap.get(userInfo.getRpcode());
        if(StringUtil.isNotBlank(bizno)){
            bizUserInfo.setBizNo( bizno); // 业务编号
        }else {
            throw new CustomException("PROVICE_BIZ 中rpcode对应的Bizno不存在");
        }
//        bizUserInfo.setBizNo(dxProvinceMap.get(userInfo.getRpcode()));
        bizUserInfo.setUserNo(userInfo.getUserno());
        bizUserInfo.setPasswd(userInfo.getPassword());
        bizUserInfo.setPhonetype(userInfo.getPhonetype());
        bizUserInfo.setAccountType(SynConstant.ACCOUNTTYPE_PERSONAL);
        bizUserInfo.setUserType(SynConstant.USER_TYPE_PERSONAL);
        bizUserInfo.setRpcode(userInfo.getRpcode());
        bizUserInfo.setCityCode(userInfo.getCitycode());
        bizUserInfo.setPhone(userInfo.getPhone());
        bizUserInfo.setIntime(userInfo.getSignuptime());
        bizUserInfo.setStatus(userInfo.getUserstatus());
        bizUserInfo.setDefaultpwdflag(userInfo.getDefaultpwdflag());
        bizUserInfo.setInsource(userInfo.getIsignupsource());
        bizUserInfo.setOfftime(userInfo.getCanceldatetime());
        bizUserInfo.setBusinesstype(userInfo.getBusinesstype());
        bizUserInfo.setRemark(userInfo.getRemark());
        bizUserInfo.setRectip(userInfo.getRectipflag());
        bizUserInfo.setTcid(userInfo.getTaocanid());
        bizUserInfo.setCallerVoice(userInfo.getCallervoice());
        bizUserInfo.setCalledVoice(userInfo.getCalledvoice());
        bizUserInfo.setCallerRecord(userInfo.getCallerrecordvoice());
        bizUserInfo.setCalledRecord(userInfo.getCalledrecordvoice());

        bizUserInfo.setSex(userInfo.getSex());
        bizUserInfo.setIdentify(userInfo.getCertificatenum());
        return bizUserInfo;
    }


    /**
     * 分省企业用户转换BOSS用户  BIZ_USER_INFO
     *
     * @param entUser
     * @param dxProvinceMap
     * @return
     */
    public static BizUserInfo bizUserInfoTransform4ProvinceEnt(EntUserInfo entUser, Map<String, String> dxProvinceMap) {
        BizUserInfo bizUserInfo = new BizUserInfo();

        // update by zkai on 2016/05/27
        String bizno = dxProvinceMap.get(entUser.getRpcode());
        if(StringUtil.isNotBlank(bizno)){
            bizUserInfo.setBizNo( bizno); // 业务编号
        }else {
            throw new CustomException("PROVICE_BIZ 中rpcode对应的Bizno不存在");
        }
//        bizUserInfo.setBizNo(dxProvinceMap.get(entUser.getRpcode()));

        bizUserInfo.setEntNo(entUser.getUserno());
        bizUserInfo.setUserNo(entUser.getUserTel());
        bizUserInfo.setPasswd(entUser.getPassword());
        bizUserInfo.setPhonetype(entUser.getPhonetype());
        bizUserInfo.setAccountType(entUser.getAccounttype());
        bizUserInfo.setUserType(SynConstant.USER_TYPE_ENTERPRISES);
        bizUserInfo.setRpcode(entUser.getRpcode());
        bizUserInfo.setCityCode(entUser.getCitycode());
        bizUserInfo.setIntime(entUser.getSignuptime());
        bizUserInfo.setStatus(entUser.getUserstatus());
        bizUserInfo.setDefaultpwdflag(entUser.getDefaultpwdflag());
        bizUserInfo.setInsource(entUser.getIsignupsource());
        bizUserInfo.setOfftime(entUser.getCanceldatetime());
        bizUserInfo.setRemark(entUser.getRemark());
        bizUserInfo.setFax(entUser.getFax());
        bizUserInfo.setRectip(entUser.getRectipflag());
        bizUserInfo.setCallerflag(entUser.getCallerflag());
        bizUserInfo.setCalledflag(entUser.getCalledflag());
        bizUserInfo.setOrgNo(entUser.getOrgNo());
        bizUserInfo.setIsrefund(entUser.getIsrefund());
        bizUserInfo.setRefundamount(entUser.getRefundamount());
        bizUserInfo.setRefundremark(entUser.getRefundremark());
        bizUserInfo.setSmsLogin(entUser.getSmsLogin());
        bizUserInfo.setTcid(entUser.getTaocanid());
        bizUserInfo.setEmail(entUser.getEmail());
        bizUserInfo.setCallerVoice(entUser.getCallervoice());
        bizUserInfo.setCalledVoice(entUser.getCalledvoice());
        bizUserInfo.setCallerRecord(entUser.getCallerrecordvoice());
        bizUserInfo.setCalledRecord(entUser.getCalledrecordvoice());

        bizUserInfo.setRegNo(entUser.getCertificatenum());
        bizUserInfo.setName(entUser.getUsername());
        bizUserInfo.setAddress(entUser.getAddress());

        return bizUserInfo;
    }

    /**
     * 总部电信用户转BOSS系统用户 BIZ_USER_INFO
     *
     * @param telUserInfo
     * @return
     */
    public static BizUserInfo bizUserInfoTransform4CPTel(TelUserInfo telUserInfo) {
        BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setBizNo(SynConstant.BIZ_DX_CP);

        bizUserInfo.setUserNo(telUserInfo.getUserno());
        bizUserInfo.setPasswd(telUserInfo.getPassword());
        bizUserInfo.setPhonetype(telUserInfo.getPhonetype());
        bizUserInfo.setAccountType(SynConstant.ACCOUNTTYPE_PERSONAL);
        bizUserInfo.setUserType(StringUtils.isEmpty(telUserInfo.getUsertype()) ? SynConstant.USER_TYPE_PERSONAL : telUserInfo.getUsertype());
        bizUserInfo.setRpcode(telUserInfo.getRpcode());
        bizUserInfo.setCityCode(telUserInfo.getCitycode());
        bizUserInfo.setPhone(telUserInfo.getPhone());
        bizUserInfo.setIntime(telUserInfo.getSignuptime());
        bizUserInfo.setStatus(telUserInfo.getUserstatus());
        bizUserInfo.setDefaultpwdflag(telUserInfo.getDefaultpwdflag());
        bizUserInfo.setInsource(telUserInfo.getIsignupsource());
        bizUserInfo.setOfftime(telUserInfo.getCanceldatetime());
        bizUserInfo.setBusinesstype(telUserInfo.getBusinesstype());
        bizUserInfo.setRectip(telUserInfo.getRectipflag());

        return bizUserInfo;
    }

    /**
     * 总部联通用户转BOSS系统用户  BIZ_USER_INFO
     *
     * @param uniUserInfo
     * @return
     */

    public static BizUserInfo bizUserInfoTransform4CPUnicom(UniUserInfo uniUserInfo) {
        BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setBizNo(SynConstant.BIZ_LT_CP);

        bizUserInfo.setUserNo(uniUserInfo.getUserno());
        bizUserInfo.setPasswd(uniUserInfo.getPassword());
        bizUserInfo.setPhonetype(uniUserInfo.getPhonetype());
        bizUserInfo.setAccountType(SynConstant.ACCOUNTTYPE_PERSONAL);
        bizUserInfo.setUserType(StringUtils.isEmpty(uniUserInfo.getUsertype()) ? SynConstant.USER_TYPE_PERSONAL : uniUserInfo.getUsertype());
        bizUserInfo.setRpcode(uniUserInfo.getRpcode());
        bizUserInfo.setCityCode(uniUserInfo.getCitycode());
        bizUserInfo.setPhone(uniUserInfo.getPhone());
        bizUserInfo.setIntime(uniUserInfo.getSignuptime());
        bizUserInfo.setStatus(uniUserInfo.getUserstatus());
        bizUserInfo.setDefaultpwdflag(uniUserInfo.getDefaultpwdflag());
        bizUserInfo.setInsource(uniUserInfo.getIsignupsource());
        bizUserInfo.setOfftime(uniUserInfo.getCanceldatetime());
        bizUserInfo.setBusinesstype(uniUserInfo.getBusinesstype());
        bizUserInfo.setRectip(uniUserInfo.getRectipflag());
        bizUserInfo.setRemark(uniUserInfo.getRemark());
        bizUserInfo.setTcid(uniUserInfo.getTcid());

        return bizUserInfo;
    }

    /**
     * 上海音证宝转BOSS系统用户 BIZ_USER_INFO
     *
     * @param shBizUserInfo
     * @return
     */
    public static BizUserInfo bizUserInfoTransform4Sh(ShBizUserInfo shBizUserInfo) {
        BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setBizNo(SynConstant.BIZ_DX_SHANGHAI);

        bizUserInfo.setUserNo(shBizUserInfo.getUserTel());
        bizUserInfo.setPasswd(shBizUserInfo.getPassword());
        bizUserInfo.setUserType(shBizUserInfo.getUsertype());
        bizUserInfo.setEntNo(shBizUserInfo.getUserNo());
        bizUserInfo.setAccountType(shBizUserInfo.getAccounttype());
        bizUserInfo.setTcid(shBizUserInfo.getTaocanid());
        bizUserInfo.setOrgNo(shBizUserInfo.getOrgNo());
        bizUserInfo.setStatus(shBizUserInfo.getUserstatus());
        bizUserInfo.setRpcode(shBizUserInfo.getRpcode());
        bizUserInfo.setCityCode(shBizUserInfo.getCitycode());
        bizUserInfo.setPhone(shBizUserInfo.getContactphone());
        bizUserInfo.setRectip(shBizUserInfo.getRectipflag());
        bizUserInfo.setIntime(null == shBizUserInfo.getOpendatetime() ? shBizUserInfo.getSignuptime() : shBizUserInfo.getOpendatetime());
        bizUserInfo.setInsource(shBizUserInfo.getIsignupsource());
        bizUserInfo.setOfftime(shBizUserInfo.getCanceldatetime());
//        bizUserInfo.setCallerVoice(shBizUserInfo.getCallerflag());
//        bizUserInfo.setCalledVoice(shBizUserInfo.getCalledflag());

        bizUserInfo.setPhonetype(shBizUserInfo.getPhonetype());
        bizUserInfo.setDefaultpwdflag(shBizUserInfo.getDefaultpwdflag());
        bizUserInfo.setIsrefund(shBizUserInfo.getIsrefund());
        bizUserInfo.setRefundamount(shBizUserInfo.getRefundamount());
        bizUserInfo.setRefundremark(shBizUserInfo.getRefundremark());
        bizUserInfo.setSmsLogin(shBizUserInfo.getSmsLogin());
        bizUserInfo.setEmail(shBizUserInfo.getEmail());
        bizUserInfo.setBusinesstype(shBizUserInfo.getBusinesstype());
        bizUserInfo.setCallerflag(shBizUserInfo.getCallerflag());
        bizUserInfo.setCalledflag(shBizUserInfo.getCalledflag());
        bizUserInfo.setRemark(shBizUserInfo.getRemark());

        return bizUserInfo;
    }
}
