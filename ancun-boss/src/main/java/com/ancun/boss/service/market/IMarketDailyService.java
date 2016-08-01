package com.ancun.boss.service.market;

import com.ancun.boss.persistence.model.MarketDaily;
import com.ancun.boss.pojo.error.ErrorInfoList;
import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.boss.pojo.market.MarketDailyListOutput;
import com.ancun.core.exception.EduException;

import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2015/9/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IMarketDailyService {

    /**
     * 查询营销日版列表
     *
     * @param input
     * @return
     * @throws EduException
     */
    MarketDailyListOutput queryMarketDailyList(MarketDailyInput input) throws EduException;


    /**
     * 营销日报详情
     *
     * @param id
     * @return
     * @throws EduException
     */
    MarketDaily queryMarketDailyInfo(Long id) throws EduException;

    /**
     * 新增或修改营销日报
     *
     * @param marketDaily
     * @throws EduException
     */
    void addOrUpdateMarketDaily(MarketDaily marketDaily) throws EduException;


    /**
     * 删除营销日报
     *
     * @param id
     * @throws EduException
     */
    void deleteMarketDaily(Long id) throws EduException;


    /**
     * 导入营销日报
     *
     * @param marketDailyList
     * @param userno
     * @return
     * @throws EduException
     */
    String importMarketDaily(List<MarketDaily> marketDailyList, String userno) throws EduException;
}
