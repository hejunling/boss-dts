package com.ancun.boss.business.service.bizuservoice.impl;

import com.ancun.boss.business.persistence.mapper.BizUserVoiceMapper;
import com.ancun.boss.business.pojo.bizvoice.*;
import com.ancun.boss.business.service.bizuservoice.IBizUserVoiceStatisticsService;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.service.system.IBasicConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户录音统计接口
 * User: zkai
 * Date: 2016/6/23
 * Time: 15:39
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class BizuserVoiceStatisticsServiceImpl  implements IBizUserVoiceStatisticsService{
    @Resource
    private BizUserVoiceMapper bizUserVoiceMapper;

    @Resource
    private IBasicConfigService basicConfigService;

    @Override
    public BizUserVoiceStatisticsListOutput statisticsBizUserVoiceInfo(BizUserVoiceStatisticsInput input) {

        BizUserVoiceStatisticsListOutput output = new BizUserVoiceStatisticsListOutput();;
        // 月平均分
        if(StringUtils.equals(input.getExporttype(), BizRequestConstant.MONTH_AVERAGE)){
            List<BizUserVoiceMonitorStatisticsInfo> monthList= bizUserVoiceMapper.statisticsMonthCount(input);

            for (BizUserVoiceMonitorStatisticsInfo bizUserVoiceMonitorStatisticsInfo: monthList ) {
                String bizno = bizUserVoiceMonitorStatisticsInfo.getBizno();
                if(StringUtils.isNotBlank(bizno)){
                    bizUserVoiceMonitorStatisticsInfo.setBizname(basicConfigService.getBizInfoByCoed(bizno).getName());
                }
            }

            output.setExportList(monthList);
        }
        // 季度平均分
        if(StringUtils.equals(input.getExporttype(),BizRequestConstant.QUARTER_AVERAGE)){
            List<BizUserVoiceQuarterStatisticsInfo> quarterList= bizUserVoiceMapper.statisticsQuarterCount(input);

            for (BizUserVoiceQuarterStatisticsInfo bizUserVoiceQuarterStatisticsInfo: quarterList ) {
                String bizno = bizUserVoiceQuarterStatisticsInfo.getBizno();
                if(StringUtils.isNotBlank(bizno)){
                    bizUserVoiceQuarterStatisticsInfo.setBizname(basicConfigService.getBizInfoByCoed(bizno).getName());
                }
            }

            output.setExportList(quarterList);
        }
        // 半年平均分
        if(StringUtils.equals(input.getExporttype(),BizRequestConstant.HALF_YEAR_AVERAGE)){
            List<BizUserVoiceHalfYearsStatisticsInfo> halfYearList= bizUserVoiceMapper.statisticsHalfYearCount(input);

            for (BizUserVoiceHalfYearsStatisticsInfo bizUserVoiceHalfYearsStatisticsInfo: halfYearList ) {
                String bizno = bizUserVoiceHalfYearsStatisticsInfo.getBizno();
                if(StringUtils.isNotBlank(bizno)){
                    bizUserVoiceHalfYearsStatisticsInfo.setBizname(basicConfigService.getBizInfoByCoed(bizno).getName());
                }
            }

            output.setExportList(halfYearList);
        }
        // 年平均分
        if(StringUtils.equals(input.getExporttype(),BizRequestConstant.YEAR_AVERAGE)){
            List<BizUserVoiceYearStatisticsInfo> yearList= bizUserVoiceMapper.statisticsYearCount(input);

            for (BizUserVoiceYearStatisticsInfo bizUserVoiceYearStatisticsInfo: yearList ) {
                String bizno = bizUserVoiceYearStatisticsInfo.getBizno();
                if(StringUtils.isNotBlank(bizno)){
                    bizUserVoiceYearStatisticsInfo.setBizname(basicConfigService.getBizInfoByCoed(bizno).getName());
                }
            }

            output.setExportList(yearList);
        }
        return output ;
    }

    /**
     * 用户录音量(表格形式展示)
     * @param input
     */
    public BizUserVoiceStatisticsTableOutput statisticsBizUserVoiceInfoTable(BizUserVoiceStatisticsTableInput input){
        BizUserVoiceStatisticsTableOutput out = new BizUserVoiceStatisticsTableOutput();
        List<BizUserVocieStatisticsTableInfo> bizUserVocieStatisticTableInfos= bizUserVoiceMapper.statisticsVoiceCount(input);

        for (BizUserVocieStatisticsTableInfo bizUserVocieStatisticTableInfo: bizUserVocieStatisticTableInfos ) {
            String bizno = bizUserVocieStatisticTableInfo.getBizno();
            if(StringUtils.isNotBlank(bizno)){
                bizUserVocieStatisticTableInfo.setBizname(basicConfigService.getBizInfoByCoed(bizno).getName());
                bizUserVocieStatisticTableInfo.setStarttime(input.getStartime());
                bizUserVocieStatisticTableInfo.setEndtime(input.getEndtime());
            }
        }
        out.setBizUserVocieStatisticsTableInfos(bizUserVocieStatisticTableInfos);
        out.setPageinfo(input.getPage());
        return out;
    }
}
