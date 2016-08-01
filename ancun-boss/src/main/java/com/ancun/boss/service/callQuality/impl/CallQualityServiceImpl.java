package com.ancun.boss.service.callQuality.impl;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.mapper.CallQualityMapper;
import com.ancun.boss.persistence.mapper.CallInRecordMapper;
import com.ancun.boss.persistence.mapper.CallQualityInfoMapper;
import com.ancun.boss.persistence.mapper.ImportFailedRecordMapper;
import com.ancun.boss.persistence.mapper.SystemUserInfoMapper;
import com.ancun.boss.persistence.model.*;
import com.ancun.boss.pojo.callQualityInfo.*;
import com.ancun.boss.pojo.callQualityInfo.CallQualityInfo;
import com.ancun.boss.pojo.system.BasicConfigInput;
import com.ancun.boss.service.callQuality.CallQualityService;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.core.exception.EduException;
import com.ancun.core.page.Page;
import com.ancun.utils.DateUtil;
import com.ancun.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

public class CallQualityServiceImpl implements CallQualityService {

    @Resource
    private CallQualityInfoMapper callQualityInfoMapper;

    @Resource
    private SystemUserInfoMapper systemUserInfoMapper;

    @Resource
    private CallQualityMapper callQualityMapper;

    @Resource
    private ImportFailedRecordMapper importMapper;

    @Resource
    private IBasicConfigService basicConfigService;

    @Resource
    private CallInRecordMapper callInRecordMapper;

    public  static Map<String,String> scoremap=null;
    /**
     * 呼入质检-全部查询
     *
     * @param input
     * @return
     * @throws com.ancun.core.exception.EduException
     */
    @Override
    public CallQualityListOutput getCallQualityListinfo(CallQualityListInput input) {

        CallQualityListOutput output = new CallQualityListOutput();
        CallQualityInfoExample example = new CallQualityInfoExample();
        // 设置分页
        Page pageinfo = new Page();
        if((input.getCurrentpage() !=null) && (input.getCurrentpage() != "")){

            pageinfo.setCurrentpage(Integer.valueOf(input.getCurrentpage()));
            pageinfo.setPagesize(Integer.valueOf(input.getPagesize()));
            input.setPage(pageinfo);
        }

        List<CallQualityInfo> list = callQualityMapper.selectByRequest(input);
        output.setCallQualityInfoList(list);
        output.setPageinfo(pageinfo);

        return output;
    }

    /**
     * 呼入质检-新增/修改
     *
     * @param input
     * @return
     * @throws com.ancun.core.exception.EduException
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addOrUpdateCallQualityInfo(CallQualityInput input) throws EduException {
        Long checkId = null; // 质检ID(CALL_QUALITY_INFO.ID）

        // id为空，新增呼入质检
        if (StringUtils.isEmpty(input.getId())) {
            com.ancun.boss.persistence.model.CallQualityInfo callQualityInfo = new com.ancun.boss.persistence.model.CallQualityInfo();
            callQualityInfo.setCheckUserno(input.getCheckUserno());// 质检员工号
            callQualityInfo.setCheckedUserno(input.getCheckedUserno()); // 被质检人编号
            callQualityInfo.setCheckTime(DateUtil.stringToDate(input.getCheckTime(), "yyyy-MM-dd"));// 质检时间
            callQualityInfo.setDatumPoint(input.getDatumPoint());// 基准点
            callQualityInfo.setServiceAttitude(Integer.valueOf(input.getServiceAttitude())); //服务态度
            callQualityInfo.setBusinessEfficiency(Integer.valueOf(input.getBusinessEfficiency()));// 业务有效性
            callQualityInfo.setStandardOperation(Integer.valueOf(input.getStandardOperation())); // 规范操作
            callQualityInfo.setServiceSkills(Integer.valueOf(input.getServiceSkills())); // 服务技巧
            callQualityInfo.setTotalScore(Integer.valueOf(input.getTotalScore())); // 总分
            callQualityInfo.setRemark(input.getRemark()); // 总分
            callQualityInfo.setAskQuestion(input.getAskQuestion()); // 咨询问题

            // add by zkai on 2016/04/08 数据库新增字段（来电时间，来电电话）
            // ----------------------------------------------
            callQualityInfo.setTelTime(input.getTelTime()); // 来电时间
            callQualityInfo.setTelNo(input.getTelNo()); // 来电电话
            // ----------------------------------------------
            callQualityInfoMapper.insert(callQualityInfo);

            /**
             * add by zkai on 2016/04/08
             */
            checkId = callQualityInfo.getId();

        } else {
            // 修改校验
            validateCallQualityInfoForUpdate(Integer.valueOf(input.getId()));

            com.ancun.boss.persistence.model.CallQualityInfo callQualityInfo = new com.ancun.boss.persistence.model.CallQualityInfo();
            callQualityInfo.setId(Long.valueOf(input.getId()));// 主键编号
            callQualityInfo.setCheckUserno(input.getCheckUserno());// 质检员工号
            callQualityInfo.setCheckedUserno(input.getCheckedUserno()); // 被质检人编号
            callQualityInfo.setCheckTime(DateUtil.stringToDate(input.getCheckTime(), "yyyy-MM-dd"));// 质检时间
            callQualityInfo.setDatumPoint(input.getDatumPoint());// 基准点
            callQualityInfo.setServiceAttitude(Integer.valueOf(input.getServiceAttitude())); //服务态度
            callQualityInfo.setBusinessEfficiency(Integer.valueOf(input.getBusinessEfficiency()));// 业务有效性
            callQualityInfo.setStandardOperation(Integer.valueOf(input.getStandardOperation())); // 规范操作
            callQualityInfo.setServiceSkills(Integer.valueOf(input.getServiceSkills())); // 服务技巧
            callQualityInfo.setTotalScore(Integer.valueOf(input.getTotalScore())); // 总分
            callQualityInfo.setRemark(input.getRemark()); // 总分
            callQualityInfo.setAskQuestion(input.getAskQuestion()); // 咨询问题
            callQualityInfoMapper.updateByPrimaryKeySelective(callQualityInfo);

            /**
             * add by zkai on 2016/04/08
             */
            checkId = Long.valueOf(input.getId());

        }

        // 如果来电时间，来电电话都不为空
        if(!("null".equals((input.getTelTime()))) && StringUtils.isNotEmpty(input.getTelNo())){
            /**
             * add by zkai
             * 根据来电时间，来电电话修改call_in_recode表
             */
            // 验证呼入登记是否存在
            validateCallInRecordInfo(input.getTelTime(),input.getTelNo());

            // 条件拼接
            CallInRecordExample callInRecordExample = new CallInRecordExample();
            CallInRecordExample.Criteria criteria = callInRecordExample.createCriteria();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String telTime = formatter.format(input.getTelTime()); //来电时间格式转换
            criteria.andCalltimeEqualTo(telTime); //来电时间
            criteria.andCallphoneEqualTo(input.getTelNo());  // 来电号码

            // 修改内容
            CallInRecord callInRecord = new CallInRecord();
            callInRecord.setCheckId(checkId);
            callInRecordMapper.updateByExampleSelective(callInRecord,callInRecordExample);

        }

    }

    /**
     * 呼入质检-单个查询(详细)
     *
     * @param id 质检编号
     * @return
     * @throws com.ancun.core.exception.EduException
     */
    public CallQualityDetailOutput getCallQualityInfo(int id) throws EduException {

        CallQualityDetailOutput out = new CallQualityDetailOutput();

        CallQualityInfo callQualityInfo = new CallQualityInfo();
        callQualityInfo = callQualityMapper.selectByPrimaryKey(Long.valueOf(id));

        // 修改的质检编号不存在时，提示质检编号不存在
        if (callQualityInfo == null) {
            throw new EduException(MessageConstant.CALL_QUALITY_LIST_NULL);
        }

        out.setCallQualityInfo(callQualityInfo);
        return out;

    }

    /**
     * 呼入质检-删除
     *
     * @param id 编号
     * @return
     * @throws com.ancun.core.exception.EduException
     */
    public void deleteCallQualityInfo(int id) throws EduException {
        // 删除校验
        validateCallQualityInfoForUpdate(id);
        CallQualityInfoExample example = new CallQualityInfoExample();
        CallQualityInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(Long.valueOf(id));
        callQualityInfoMapper.deleteByExample(example);
    }

    /**
     * 呼入质检修改内容校验
     *
     * @param id 编号
     * @return
     * @throws com.ancun.core.exception.EduException
     */
    private void validateCallQualityInfoForUpdate(int id) throws EduException {

//    	CallQualityInfoExample example = new CallQualityInfoExample();
//		CallQualityInfoExample.Criteria c = example.createCriteria();
//
//		c.andIdEqualTo(Long.valueOf(input.getId()));
        CallQualityInfo callQualityInfo = new CallQualityInfo();
        callQualityInfo = callQualityMapper.selectByPrimaryKey(Long.valueOf(id));

        // 修改的质检编号不存在时，提示质检编号不存在
        if (callQualityInfo == null) {
            throw new EduException(MessageConstant.CALL_QUALITY_LIST_NULL);
        }
    }


    /**
     * 呼入质检数据导入
     *
     * @param input
     */
    public String importcallQualityInfo(CallQualityImportInput input) {
        if (input.getCallQualityInfoList().size() == 0) {
            throw new EduException(MessageConstant.CALL_QUALITY_IMPORT_LIST_NULL);
        }
        List<CallQualityInfo> callQualityInfos = new LinkedList<CallQualityInfo>();
        int successImportNumber = 0;
        int errorImportNumber = -1;
        for (CallQualityInput callQualityInput : input.getCallQualityInfoList()) {
            // 如果成功导入数据库
            if (validateImportCallQuality(callQualityInput, input.getUserno())) {
                // 获取时间
                String checkTime=callQualityInput.getCheckTime();

                // unix 时间戳
                checkTime= String.valueOf((Integer.valueOf(checkTime) - 25569) * 3600 * 24);
                Long timestamp = Long.parseLong(checkTime)*1000;
                // 时间转换成yyy-MM-dd 格式
                checkTime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));

                com.ancun.boss.persistence.model.CallQualityInfo m = new com.ancun.boss.persistence.model.CallQualityInfo();
                m.setCheckUserno(callQualityInput.getCheckUserno());// 质检员工号
                m.setCheckedUserno(callQualityInput.getCheckedUserno()); // 被质检人编号
                m.setCheckTime(DateUtil.stringToDate(checkTime, "yyyy-MM-dd"));// 质检时间
                m.setDatumPoint(callQualityInput.getDatumPoint());// 基准点

                // 如果基准点为0，总分为0
                if(StringUtils.equals(callQualityInput.getDatumPoint(),"0")){
                    m.setServiceAttitude(0); //服务态度
                    m.setBusinessEfficiency(0);// 业务有效性
                    m.setStandardOperation(0); // 规范操作
                    m.setServiceSkills(0); // 服务技巧
                    m.setTotalScore(0); // 总分
                }else {
                    m.setServiceAttitude(Integer.valueOf(callQualityInput.getServiceAttitude())); //服务态度
                    m.setBusinessEfficiency(Integer.valueOf(callQualityInput.getBusinessEfficiency()));// 业务有效性
                    m.setStandardOperation(Integer.valueOf(callQualityInput.getStandardOperation())); // 规范操作
                    m.setServiceSkills(Integer.valueOf(callQualityInput.getServiceSkills())); // 服务技巧
                    m.setTotalScore(Integer.valueOf(callQualityInput.getTotalScore())); // 总分
                }
                m.setRemark(callQualityInput.getRemark()); // 备注
                m.setAskQuestion(callQualityInput.getAskQuestion()); // 咨询问题
                // 插入数据库
                callQualityInfoMapper.insert(m);
                successImportNumber = successImportNumber + 1;
            }

        }
        errorImportNumber = input.getCallQualityInfoList().size() - successImportNumber;
        return "导入成功条数：" + successImportNumber + " 失败条数:" + errorImportNumber;
    }

    /**
     * 导入验证
     *
     * @param input
     * @param userno
     * @return boolen
     */
    public boolean validateImportCallQuality(CallQualityInput input, String userno) {
        boolean fla = true; // 返回
        boolean flg = true; // 总数验证
        String msg = "";

        // 取得分数map
        Map<String,String> map=getScoreMap();

        String rexServiceAttitude = map.get("fwtd"); //服务态度
        String rexStandardOperation = map.get("gfcz"); // 规范操作
        String rexServiceSkills = map.get("fwjq"); // 服务技巧
        String rexBusinessEfficiency = map.get("yjyxx"); // 业务有效性
        String rexTotalScore = "100"; // 总分
//        String rexServiceAttitude = "(^[0-9]$)|(^[1-2][0-9]$)|(^[3][0-2]$)"; // 0到32的整数
//        String rexStandardOperation = "(^[0-9]$)|(^[1][0-7]$)"; // 0到17的整数
//        String rexServiceSkills = "(^[0-9]$)|(^[1][0-6]$)"; // 0到16的整数
//        String rexBusinessEfficiency = "(^[0-9]$)|(^[1-2][0-9]$)|(^[3][0-5]$)"; // 0到35的整数
//        String rexTotalScore = "^(0|[1-9]\\d?|100)$"; // 0到100的整数

        if (StringUtils.isEmpty(input.getCheckUserno())) {
            msg = msg + "质检人编号不能为空;";
            fla = false;
        }
        else if(!validateUser(input.getCheckUserno())){
            msg = msg + "质检人编号异常;";
            fla = false;
        }
        if (StringUtils.isEmpty(input.getCheckTime())) {
            msg = msg + "质检时间不能为空;";
            fla = false;
        }else {
            String checkTime =input.getCheckTime();
            // 异常，时间格式不正确
            try{
                // unix 时间戳
                checkTime= String.valueOf((Integer.valueOf(checkTime) - 25569) * 3600 * 24);
                Long timestamp = Long.parseLong(checkTime)*1000;
                checkTime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
            }catch (Exception e){
                msg = msg + "质检时间格式不正确";
                fla = false;
            }
        }
        if (StringUtils.isEmpty(input.getCheckedUserno())) {
            msg = msg + "被质检人编号不能为空;";
            fla = false;
        }
        else if(!validateUser(input.getCheckedUserno())){
            msg = msg + "被质检人编号异常;";
            fla = false;
        }
        if (StringUtils.isEmpty(input.getAskQuestion())) {
            msg = msg + "咨询问题不能为空;";
            fla = false;
        }
        if (StringUtils.isEmpty(input.getDatumPoint())) {
            msg = msg + "基准点不能为空;";
            fla = false;
        }
        // 如果基准点为0，总数
        else if(StringUtils.equals(input.getDatumPoint(),"0")) {
            // 数据验证失败时，插入导入失败记录表
            if (!fla) {
                insetErrorInfo(msg,userno);
            }
            return fla;
        }
        try{
            if (StringUtils.isEmpty(input.getServiceAttitude())) {
                msg = msg + "服务态度不能为空;";
                fla = false;
                flg = false;

            } else if (Double.valueOf(input.getServiceAttitude())<0 || Double.valueOf(input.getServiceAttitude())>Double.valueOf(rexServiceAttitude)){
                msg = msg + "服务态度不是0-"+rexServiceAttitude+"之间数字;";
                fla = false;
                flg = false;
            }
        }catch (Exception e){
            msg = msg + "服务态度不是0-"+rexServiceAttitude+"之间数字;";
            fla = false;
            flg = false;
        }
        // 如果异常，输入内容不为double类型
        try {
            if (StringUtils.isEmpty(input.getBusinessEfficiency())) {
                msg = msg + "业务有效性不能为空;";
                fla = false;
                flg = false;
//        } else if (!input.getBusinessEfficiency().matches(rexBusinessEfficiency)) {
            }else if(Double.valueOf(input.getBusinessEfficiency())<0 || Double.valueOf(input.getBusinessEfficiency())>Double.valueOf(rexBusinessEfficiency)){
                msg = msg + "业务有效性不是0-"+rexBusinessEfficiency+"之间数字;";
                fla = false;
                flg = false;
            }
        }catch (Exception e){
            msg = msg + "业务有效性不是0-"+rexBusinessEfficiency+"之间数字;";
            fla = false;
            flg = false;
        }
        // 如果异常，输入内容不为double类型
        try {
            if (StringUtils.isEmpty(input.getStandardOperation())) {
                msg = msg + "规范操作不能为空;";
                fla = false;
                flg = false;
//        } else if (!input.getStandardOperation().matches(rexStandardOperation)) {
            } else if (Double.valueOf(input.getStandardOperation())<0 || Double.valueOf(input.getStandardOperation())>Double.valueOf(rexStandardOperation)) {
                msg = msg + "规范操作不是0-"+rexStandardOperation+"之间数字;";
                fla = false;
                flg = false;
            }
        }catch (Exception e){
            msg = msg + "规范操作不是0-"+rexStandardOperation+"之间数字;";
            fla = false;
            flg = false;
        }
        // 如果异常，输入内容不为double类型
        try {
            if (StringUtils.isEmpty(input.getServiceSkills())) {
                msg = msg + "服务技巧不能为空;";
                fla = false;
                flg = false;
//        } else if (!input.getServiceSkills().matches(rexServiceSkills)) {
            } else if (Double.valueOf(input.getServiceSkills())<0 || Double.valueOf(input.getServiceSkills())>Double.valueOf(rexServiceSkills)) {
                msg = msg + "服务技巧不是0-"+rexServiceSkills+"之间数字;";
                fla = false;
                flg = false;
            }
        }catch (Exception e){
            msg = msg + "服务技巧不是0-"+rexServiceSkills+"之间数字;";
            fla = false;
            flg = false;
        }

       try {
           if (StringUtils.isEmpty(input.getTotalScore())) {
               msg = msg + "总分不能为空;";
               fla = false;
               flg = false;
//        } else if (!input.getTotalScore().matches(rexTotalScore)) {
           } else if (Double.valueOf(input.getTotalScore())<0 || Double.valueOf(input.getTotalScore())>Double.valueOf(rexTotalScore)) {
               msg = msg + "总分不是0-100之间数字;";
               fla = false;
               flg = false;
           }
       }catch (Exception e){
           msg = msg + "总分不是0-100之间数字;";
           fla = false;
           flg = false;
       }
        if (flg) {
            if (StringUtil.isNotEmpty(input.getTotalScore()) && Integer.valueOf(input.getTotalScore()) != (Integer.valueOf(input.getServiceAttitude()) +
                    Integer.valueOf(input.getBusinessEfficiency()) + Integer.valueOf(input.getStandardOperation()) +
                    Integer.valueOf(input.getServiceSkills()))) {
                msg = msg + "总分值不正确;";
                fla = false;
            }
        }

        // 数据验证失败时，插入导入失败记录表
        if (!fla) {
            insetErrorInfo(msg,userno);
        }

        return fla;
    }

    /**
     * 数据验证失败时，插入导入失败记录表
     */
    public void  insetErrorInfo(String msg,String userno){
        ImportFailedRecord record = new ImportFailedRecord();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        record.setMenuno("c807418d-c237-492a-b220-849526092288");
        record.setMenuname("呼入质检");
        record.setFuncname("呼入质检数据导入");
        record.setImportTime(df.format(new Date()));
        record.setFailedRemark(msg);
        record.setUserno(userno);

        importMapper.insert(record);
    }


    /**
     * 用户验证
     * @param userNo 用户编号
     * @return
     */
    public boolean validateUser(String userNo){
        SystemUserInfoExample example = new SystemUserInfoExample();
        SystemUserInfoExample.Criteria c = example.createCriteria();

        c.andUsernoEqualTo(userNo);
        c.andDeletedEqualTo("NO");
        List<SystemUserInfo> list = systemUserInfoMapper.selectByExample(example);
        if(list==null ||  list.size()==0  || list.size()>1){
            return  false;
        }
        return  true;
    }

    /**
     * 得到基础数据最高分map
     * @return
     */
    public final  Map<String,String> getScoreMap(){

        BasicConfigInput basicConfigInput = new BasicConfigInput();
        basicConfigInput.setMoudlecode("CALLIN_CHECK");
        List<SystemBasicConfig> systemBasicConfigs= basicConfigService.queryListByMoudleCode(basicConfigInput);
        if(scoremap==null){
            scoremap=new HashMap<String,String>();
            for (int i = 0; i < systemBasicConfigs.size() ; i++) {
                SystemBasicConfig systemBasicConfig =systemBasicConfigs.get(i);
                scoremap.put(systemBasicConfig.getSpell(),systemBasicConfig.getCode());
            }
            return scoremap;
        }
        return  scoremap;
    }

    /**
     * 呼入质检数据统计
     * @param input
     */
    public CallQualityStatisticsListOutput statisticsCallQualityInfo(CallQualityStatisticsInput input){
        CallQualityStatisticsListOutput output = new CallQualityStatisticsListOutput();;
        // 月平均分
        if(StringUtils.equals(input.getExportType(), BizRequestConstant.MONTH_AVERAGE)){
            List<CallQualityMonthStatisticsInfo> monthList= callQualityMapper.statisticsMonthAverage(input);
            output.setExportList(monthList);
        }
        // 季度平均分
        if(StringUtils.equals(input.getExportType(),BizRequestConstant.QUARTER_AVERAGE)){
            List<CallQualityQuarterStatisticsInfo> quarterList= callQualityMapper.statisticsQuarterAverage(input);
            output.setExportList(quarterList);
        }
        // 半年平均分
        if(StringUtils.equals(input.getExportType(),BizRequestConstant.HALF_YEAR_AVERAGE)){
            List<CallQualityHalfYearStatisticsInfo> halfYearList= callQualityMapper.statisticsHalfYearAverage(input);
            output.setExportList(halfYearList);
        }
        // 年平均分
        if(StringUtils.equals(input.getExportType(),BizRequestConstant.YEAR_AVERAGE)){
            List<CallQualityYearStatisticsInfo> yearList= callQualityMapper.statisticsYearAverage(input);
            output.setExportList(yearList);
        }
        return output ;
    }

    /**
     * add by zkai
     * 根据来电时间和来电号码验证呼入登记记录是否存在
     */
    private void validateCallInRecordInfo(Date telTime,String telNo){
        CallInRecordExample callInRecordExample = new CallInRecordExample();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String telTime2 = formatter.format(telTime); //来电时间格式转换
        CallInRecordExample.Criteria criteria = callInRecordExample.createCriteria();
        criteria.andCallphoneEqualTo(telNo);
        criteria.andCalltimeEqualTo(telTime2);
        List<CallInRecord> callInRecords = callInRecordMapper.selectByExample(callInRecordExample);

        // 如果数据条数为0，提示呼入信息不存在
        if(callInRecords.size() == 0){
            throw new EduException(MessageConstant.CALL_IN_RECORD_NULL);
        }
        // 如果数据条数不为1，提示数据异常
        if(callInRecords.size() > 1){
            throw new EduException(MessageConstant.DATA_ERROR);
        }

    }

}
