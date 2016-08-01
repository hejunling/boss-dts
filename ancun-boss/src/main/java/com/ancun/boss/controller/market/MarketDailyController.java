package com.ancun.boss.controller.market;

import com.ancun.boss.persistence.model.MarketDaily;
import com.ancun.boss.pojo.error.ErrorInfoList;
import com.ancun.boss.pojo.market.ImportMarketDaily;
import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.boss.pojo.market.MarketDailyListOutput;
import com.ancun.boss.service.market.IMarketDailyService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 营销日报
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class MarketDailyController extends BaseController {

    @Resource
    private IMarketDailyService marketDailyService;

    /**
     * 查询营销日报列表
     *
     * @param marketDailyInputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/queryMarketDailyList", method = RequestMethod.POST)
    public RespBody<MarketDailyListOutput> queryMarketDailyList(ReqBody<MarketDailyInput> marketDailyInputReqBody) throws EduException {
        MarketDailyListOutput output = marketDailyService.queryMarketDailyList(marketDailyInputReqBody.getContent());
        return new RespBody<MarketDailyListOutput>(output);
    }

    /**
     * 查询营销日报详情
     *
     * @param marketDailyReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/queryMarketDailyInfo", method = RequestMethod.POST)
    public RespBody<MarketDaily> queryMarketDailyInfo(ReqBody<MarketDaily> marketDailyReqBody) throws EduException {
        Long id = marketDailyReqBody.getContent().getId();
        MarketDaily marketDaily = marketDailyService.queryMarketDailyInfo(id);
        return new RespBody<MarketDaily>(marketDaily);
    }

    /**
     * 新增或修改 营销日报
     *
     * @param
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/addOrUpdateMarketDaily", method = RequestMethod.POST)
    public RespBody<String> addOrUpdateMarketDaily(ReqBody<MarketDaily> marketDailyReqBody) throws EduException {
        MarketDaily marketDaily = marketDailyReqBody.getContent();
        marketDailyService.addOrUpdateMarketDaily(marketDaily);
        return new RespBody<String>();
    }

    /**
     * 删除 营销日报
     *
     * @param marketDailyReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/deleteMarketDaily", method = RequestMethod.POST)
    public RespBody<String> deleteMarketDaily(ReqBody<MarketDaily> marketDailyReqBody) throws EduException {
        MarketDaily marketDaily = marketDailyReqBody.getContent();
        marketDailyService.deleteMarketDaily(marketDaily.getId());
        return new RespBody<String>();
    }

    /**
     * 导入营销日报
     *
     * @param importMarketDailyReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/importMarketDaily", method = RequestMethod.POST)
    public RespBody<String> importMarketDaily(ReqBody<ImportMarketDaily> importMarketDailyReqBody) throws EduException {
        ImportMarketDaily importMarketDaily = importMarketDailyReqBody.getContent();

        String result = marketDailyService.importMarketDaily(importMarketDaily.getMarketdailylist(), importMarketDaily.getUserno());

        return new RespBody<String>(result);
    }


}
