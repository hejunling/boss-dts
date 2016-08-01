package com.ancun.boss.pojo.market;

        import com.ancun.boss.persistence.model.MarketDaily;
        import com.ancun.boss.pojo.BossBasePojo;

        import java.util.List;

/**
 * 营销日报导入请求类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/10/8
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class ImportMarketDaily extends BossBasePojo {

    /**
     * 导入营销日报列表
     */
    private List<MarketDaily> marketdailylist;

    public List<MarketDaily> getMarketdailylist() {
        return marketdailylist;
    }

    public void setMarketdailylist(List<MarketDaily> marketdailylist) {
        this.marketdailylist = marketdailylist;
    }
}
