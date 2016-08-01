package com.ancun.boss.service.phoneResp;

import com.ancun.boss.pojo.phoneResp.*;

/**
 * 号码池清洗Service接口
 *
 * @Created on 2015年11月05日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IPhoneRepService {

    /**
     * 下拉框选择项
     *
     * @param input input参数
     * @return      下拉框选择项
     */
    GetTimeOptionsOutput getTimeOptions(GetTimeOptionsInput input);

    /**
     * 号码库查询
     *
     * @param input input参数
     * @return      号码库列表output
     */
    PhoneRespsOutPut phoneResps(PhoneRespInput input);

    /**
     * 号码池清理
     *
     * @param input input参数
     */
    void phonePoolFilter(PhoneRepInput input);

    /**
     * 号码分配页面显示用数据准备
     *
     * @param input input参数
     * @return      号码分配页面显示用output
     */
    PhoneDividPageOutput phoneDividPage(PhoneDividPageInput input);

    /**
     * 根据批次取得号码数量
     *
     * @param input 批次
     * @return      号码数
     */
    Integer countPhones(CountPhoneInput input);

    /**
     * 号码分配
     *
     * @param input input参数
     */
    void phoneDivid(PhoneDividInput input);
}