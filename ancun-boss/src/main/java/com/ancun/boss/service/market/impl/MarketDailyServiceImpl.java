package com.ancun.boss.service.market.impl;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.BizMarketDailyMapper;
import com.ancun.boss.persistence.mapper.ImportFailedRecordMapper;
import com.ancun.boss.persistence.mapper.MarketDailyMapper;
import com.ancun.boss.persistence.model.ImportFailedRecord;
import com.ancun.boss.persistence.model.MarketDaily;
import com.ancun.boss.persistence.model.MarketDailyExample;
import com.ancun.boss.pojo.market.MarketDailyInput;
import com.ancun.boss.pojo.market.MarketDailyListOutput;
import com.ancun.boss.pojo.market.MarketDailyOutPut;
import com.ancun.boss.service.market.IMarketDailyService;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.core.exception.EduException;
import com.ancun.utils.StringUtil;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 营销日报 业务实现类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/30
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class MarketDailyServiceImpl implements IMarketDailyService {
    @Resource
    private MarketDailyMapper marketDailyMapper;

    @Resource
    private BizMarketDailyMapper bizMarketDailyMapper;

    @Resource
    private ImportFailedRecordMapper importMapper;

    @Resource
    private IBasicConfigService basicConfigService;

    @Override
    public MarketDailyListOutput queryMarketDailyList(MarketDailyInput input) throws EduException {

        List<MarketDailyOutPut> marketDailyOutPutList = bizMarketDailyMapper.queryDailyList(input);

        MarketDailyListOutput output = new MarketDailyListOutput();
        output.setMarketdailylist(marketDailyOutPutList);
        output.setPageinfo(input.getPage());
        return output;
    }

    @Override
    public MarketDaily queryMarketDailyInfo(Long id) throws EduException {
        if (id == null) {
            throw new EduException(MessageConstant.ID_MUST_BE_NOT_EMPTY);
        }
        return marketDailyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addOrUpdateMarketDaily(MarketDaily marketDaily) throws EduException {

        // 校验
        validateMarkDaily(marketDaily);

        // 接通率(接通数/外呼数量)
        marketDaily.setConnectPercent(StringUtil.percentage(marketDaily.getConnectNumber(), marketDaily.getCalledNumber()));

        // 日成功率（日成功量/外呼数量）
        marketDaily.setSuccessPercent(StringUtil.percentage(marketDaily.getSuccessDaily(), marketDaily.getCalledNumber()));

        // 接通成功率（日成功量/接通数）
        marketDaily.setConnectSuccessPercent(StringUtil.percentage(marketDaily.getSuccessDaily(), marketDaily.getConnectNumber()));

        // 人均外呼数（外呼数量/坐席数）
        marketDaily.setCalledAvg(StringUtil.getAvg(marketDaily.getCalledNumber(), marketDaily.getSeatsNumber()));

        // 人均接通数（接通数量/坐席数）
        marketDaily.setConnectedAvg(StringUtil.getAvg(marketDaily.getConnectNumber(), marketDaily.getSeatsNumber()));

        // 人均成功数（日成功量/坐席数）
        marketDaily.setSuccessAvg(StringUtil.getAvg(marketDaily.getSuccessDaily(), marketDaily.getSeatsNumber()));

        if (marketDaily != null && marketDaily.getId() == null) {
            //新增
            marketDaily.setOperator(BizRequestConstant.OPERATOR_DX);
            marketDailyMapper.insertSelective(marketDaily);
        } else {
            //修改
            MarketDailyExample example = new MarketDailyExample();
            MarketDailyExample.Criteria criteria = example.createCriteria();

            criteria.andIdEqualTo(marketDaily.getId());
            marketDailyMapper.updateByExampleSelective(marketDaily, example);
        }
    }

    /**
     * 校验内容
     *
     * @param marketDaily
     */
    private void validateMarkDaily(MarketDaily marketDaily) {
        // 业务名称
        if (StringUtil.isEmpty(marketDaily.getBusiness())) {
            throw new EduException(MessageConstant.MARKETCHECK_BUSINESS_MUST_BE_NOT_EMPTY);
        }
        //外呼团队
        if (StringUtil.isEmpty(marketDaily.getCalledNumber())) {
            throw new EduException(MessageConstant.MARKETCHECK_CALLEDTEAM_MUST_BE_NOT_EMPTY);
        }
        //营销时间
        if (StringUtil.isEmpty(marketDaily.getDateDaily())) {
            throw new EduException(MessageConstant.MARKETCHECK_MARKETTIME_MUST_BE_NOT_EMPTY);
        }

        //外呼样本量
        if (StringUtil.isEmpty(marketDaily.getCalledNumber())) {
            throw new EduException(MessageConstant.CALLED_NUMBER_MUST_BE_NOT_EMPTY);
        }
        // 接通数
        if (StringUtil.isEmpty(marketDaily.getConnectNumber())) {
            throw new EduException(MessageConstant.CONNECT_NUMBER_MUST_BE_NOT_EMPTY);
        }
        //日成功量
        if (StringUtil.isEmpty(marketDaily.getSuccessDaily())) {
            throw new EduException(MessageConstant.SUCCESS_DAILY_MUST_BE_NOT_EMPTY);
        }
        //座席数
        if (StringUtil.isEmpty(marketDaily.getSeatsNumber())) {
            throw new EduException(MessageConstant.SIT_NUMBER_MUST_BE_NOT_EMPTY_CODE);
        }
        if (StringUtil.isEmpty(marketDaily.getPhoneSource())) {
            throw new EduException(MessageConstant.PHONE_SOURCE_MUST_BE_NOT_EMPTY_CODE);
        }

    }

    @Override
    public void deleteMarketDaily(Long id) throws EduException {
        if (id == null) {
            throw new EduException(MessageConstant.ID_MUST_BE_NOT_EMPTY);
        }
        marketDailyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String importMarketDaily(List<MarketDaily> marketDailyList, String userno) throws EduException {
        if (marketDailyList == null || marketDailyList.size() <= 0) {
            throw new EduException(MessageConstant.IMPORT_DATA_ISENPTY);
        }

        String result = "";

        // 校验通过数
        List<MarketDaily> successList = new ArrayList<MarketDaily>();
        for (MarketDaily marketDaily : marketDailyList) {
            if (validateImport(marketDaily, userno)) {
                successList.add(marketDaily);
            }
        }

        if (successList.size() > 0) {
            for (MarketDaily m : successList) {
                addOrUpdateMarketDaily(m);
            }
        }
//        if(marketDailyList.size() - successList.size()>0) {
            result = "导入成功条数：" + successList.size() + "; 失败条数:" + (marketDailyList.size() - successList.size());
//        }


        return result;


    }

    private boolean validateImport(MarketDaily marketDaily, String userno) {
        boolean flag = true; // 是否有错误
        String message = "第" + marketDaily.getSort() + "行导入失败,失败原因：";
        if (StringUtil.isEmpty(marketDaily.getBusiness())) {
            message += "项目名称不能为空;";
            flag = false;
        }
        if (StringUtil.isEmpty(marketDaily.getCalledTeam())) {
            message += "外呼团队不能为空;";
            flag = false;
        }
        if (StringUtil.isEmpty(marketDaily.getDateDaily())) {
            message += "日期不能为空;";
            flag = false;
        }
        if (StringUtil.isEmpty(marketDaily.getCalledNumber())) {
            message += "外呼样本量不能为空;";
            flag = false;
        } else if (StringUtil.getWordLength(marketDaily.getCalledNumber()) > 8 || !StringUtil.isNumber(marketDaily.getCalledNumber())) {
            message += "外呼样本量只能为小于8位的整数;";
            flag = false;
        }

        if (StringUtil.isEmpty(marketDaily.getConnectNumber())) {
            message += "接通数不能为空;";
            flag = false;
        } else if (StringUtil.getWordLength(marketDaily.getConnectNumber()) > 8 || !StringUtil.isNumber(marketDaily.getConnectNumber())) {
            message += "接通数只能为小于8位的整数;";
            flag = false;
        }

        if (StringUtil.isEmpty(marketDaily.getSuccessDaily())) {
            message += "日成功量不能为空;";
            flag = false;
        } else if (StringUtil.getWordLength(marketDaily.getSuccessDaily()) > 8 || !StringUtil.isNumber(marketDaily.getSuccessDaily())) {
            message += "日成功量只能为小于8位的整数;";
            flag = false;
        }


        if (StringUtil.isEmpty(marketDaily.getSeatsNumber())) {
            message += "座席数不能为空;";
            flag = false;
        } else if (StringUtil.getWordLength(marketDaily.getSeatsNumber()) > 8 || !StringUtil.isNumber(marketDaily.getSeatsNumber())) {
            message += "座席数只能为小于8位的整数;";
            flag = false;
        }

        if (StringUtil.isEmpty(marketDaily.getPhoneSource())) {
            message += "客户来源（批次）不能为空;";
            flag = false;
        } else if (StringUtil.getWordLength(marketDaily.getPhoneSource()) > 16) {
            message += "客户来源（批次）不能超过16位;";
            flag = false;
        }

//        // 业务列表取得
//        BasicConfigInput basicConfigInput = new BasicConfigInput();
//        basicConfigInput.setMoudlecode(BasicConfigConstant.BIZ_NAME);
//        basicConfigInput.setUserno(userno);
//        List<SystemBasicConfig> businessList = basicConfigService.queryListByMoudleCode(basicConfigInput);
//
//        boolean businessFlg = false;
//
//        // 校验业务名称，存在时替换为业务code 不存在时提示业务名称错误
//        for (SystemBasicConfig business : businessList) {
//            if (business.getName().equals(marketDaily.getBusiness())) {
//                marketDaily.setBusiness(business.getCode());
//                businessFlg = true;
//                break;
//            }
//        }
//        if (!businessFlg) {
//            message += "业务名称不存在。";
//            flag = false;
//        }
//
//        // 外呼团队列表取得
//        basicConfigInput.setMoudlecode(BasicConfigConstant.OUT_CALL_TEA);
//        List<SystemBasicConfig> calledTeamList = basicConfigService.queryListByMoudleCode(basicConfigInput);
//
//        boolean calledTeamFlg = false;
//
//        // 校验外呼团队，存在时替换为业务code 不存在时提示外呼团队错误
//        for (SystemBasicConfig calledTeam : calledTeamList) {
//            if (calledTeam.getName().equals(marketDaily.getCalledTeam())) {
//                marketDaily.setCalledTeam(calledTeam.getCode());
//                calledTeamFlg = true;
//                break;
//            }
//        }
//        if (!calledTeamFlg) {
//            message += "外呼团队不存在。";
//            flag = false;
//        }


        if (!flag) {
            ImportFailedRecord record = new ImportFailedRecord();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            record.setMenuno("95c112e9-a098-48f3-be44-ff9251382a8d");
            record.setMenuname("营销数据管理");
            record.setFuncname("数据导入");
            record.setImportTime(df.format(new Date()));
            record.setFailedRemark(message);
            record.setUserno(userno);

            importMapper.insert(record);
        }

        return flag;
    }
}
