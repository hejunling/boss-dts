package com.ancun.boss.business.service.allprovince;

import com.ancun.boss.business.pojo.allprovince.ProvinceServiceInput;

/**
 * 上海分省远程接口调用
 *
 * @author lwk
 * @version 1.0
 * @Created on 2016年3月14日
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IProvinceService {

    /**
     * 开通
     *
     * @param provinceInput
     * @return
     */
    void open(ProvinceServiceInput provinceInput);

    /**
     * 退订
     *
     * @param provinceInput
     * @return
     */
    void cancel(ProvinceServiceInput provinceInput);


    /**
     * 用户套餐变更
     *
     * @param userno       用户号码
     * @param changeTcType 变更类型（1：个人账号；2：企业账号；3：个人转企业）
     * @param oldTcID      老套餐ID
     * @param newTcID      新套餐ID
     * @param rollback     是否回滚
     */
    void postChangeRequest(String userno, String changeTcType, String oldTcID, String newTcID, boolean rollback);

    /**
     * 用户套餐变更
     * add by zkai
     */
    boolean postChangeRequest(ProvinceServiceInput provinceInput);

}
