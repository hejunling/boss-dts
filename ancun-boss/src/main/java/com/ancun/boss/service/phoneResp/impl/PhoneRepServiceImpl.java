package com.ancun.boss.service.phoneResp.impl;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.ConfigConstant;
import com.ancun.boss.persistence.biz.mapper.BizPhoneRepMapper;
import com.ancun.boss.persistence.mapper.PhoneSourceHelpMapper;
import com.ancun.boss.persistence.model.ImportFailedRecord;
import com.ancun.boss.persistence.model.PhoneRepository;
import com.ancun.boss.persistence.model.PhoneSourceHelp;
import com.ancun.boss.persistence.model.SystemBasicConfig;
import com.ancun.boss.pojo.Option;
import com.ancun.boss.pojo.phoneResp.*;
import com.ancun.boss.pojo.system.BasicConfigInput;
import com.ancun.boss.service.phoneResp.IPhoneRepService;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.utils.DateUtil;
import com.ancun.utils.StringUtil;
import com.ancun.utils.taskbus.TaskBus;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 号码池清洗Service接口实现
 *
 * @Created on 2015年11月05日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class PhoneRepServiceImpl implements IPhoneRepService {

    private static final Logger logger = LoggerFactory.getLogger(PhoneRepServiceImpl.class);

    /** 号码来源分隔符 */
    private static final String PHONESOURCE_SPLIT = "_";

    /** 自定义号码池清洗Mapper */
    @Resource
    private BizPhoneRepMapper bizPhoneRepMapper;

    /** 任务总线 */
    @Resource
    private TaskBus taskBus;

    /** 基础数据 */
    @Resource
	private IBasicConfigService basicConfigService;

    /** 批次帮助Mapper */
    @Resource
    private PhoneSourceHelpMapper phoneSourceHelpMapper;

    /**
     * 下拉框选择项
     *
     * @param input input参数
     * @return 下拉框选择项
     */
    @Override
    public GetTimeOptionsOutput getTimeOptions(GetTimeOptionsInput input) {

        // 取得获取日期列表
        List<Option> options = bizPhoneRepMapper.getTimeOptions(input.getBusiness());

        GetTimeOptionsOutput output = new GetTimeOptionsOutput();
        output.setOptions(options);

        return output;
    }

    /**
     * 号码库查询
     *
     * @param input input参数
     * @return      号码库列表output
     */
    @Override
    public PhoneRespsOutPut phoneResps(PhoneRespInput input) {

        // 返回结果
        PhoneRespsOutPut outPut = new PhoneRespsOutPut();

        // 取得号码库信息
        List<PhoneRepInfo> phoneRepInfos = bizPhoneRepMapper.phoneReps(input);

        // 设置分页信息
        outPut.setPageinfo(input.getPage());

        // 设置号码库信息
        outPut.setPhoneRepInfos(phoneRepInfos);

        return outPut;
    }

    /**
     * 号码池清洗
     *
     * @param input input参数
     */
    @Async
    @Override
    public void phonePoolFilter(PhoneRepInput input) {

        logger.info("号码池清洗开始！");

        // 号码来源
        String phoneSource = createPhoneSource(input);

        // 号码取得时间
        Date getTime = DateUtil.convertStringToDate( DateUtil.DEFULT_FORMAR, input.getGettime());

        // 校验并转换成指定对象
        List<Object> afterValidate = FluentIterable.from(input.getPhones())
                .transform(transferPhone(getTime, phoneSource, input.getUserno(), input.getBusiness())).toList();

        // 筛选出号码校验不通过列表
        List<ImportFailedRecord> failedRecords = FluentIterable.from(afterValidate).filter(ImportFailedRecord.class).toList();
        if (failedRecords != null && failedRecords.size() > 0) {
            // 批量添加导入失败记录
            bizPhoneRepMapper.insertImportFailedRecordBatch(failedRecords);
        }

        // 取得新增号码库数据信息
        List<PhoneRepository> phoneReps = FluentIterable.from(afterValidate).filter(PhoneRepository.class).toList();
        if (phoneReps != null && phoneReps.size() > 0) {

            // 添加批次业务关联信息
            addPhoneSource(input);

            // 将列表导入到数据库中
            bizPhoneRepMapper.insertPhoneRepBatch(phoneReps);

            // 开始任务
            taskBus.startTask(ConfigConstant.TASK_PHONE_FILTER, phoneReps);
        }
    }

    /**
     * 号码分配页面显示用数据准备
     *
     * @param input input参数
     * @return      号码分配页面显示用output
     */
    @Override
    public PhoneDividPageOutput phoneDividPage(PhoneDividPageInput input) {

        PhoneDividPageOutput output = new PhoneDividPageOutput();

        output.setPhoneSources(bizPhoneRepMapper.phoneSources());

        // 业务列表取得
        BasicConfigInput basicConfigInput = new BasicConfigInput();
        basicConfigInput.setMoudlecode(input.getMoudleCode());
        List<SystemBasicConfig> outCallteams =  basicConfigService.queryListByMoudleCode(basicConfigInput);
        output.setOutCallTeams(outCallteams);

        return output;
    }

    /**
     * 根据批次取得号码数量
     *
     * @param input 批次
     * @return      号码数
     */
    @Override
    public Integer countPhones(CountPhoneInput input) {
        return bizPhoneRepMapper.countDividPhonesByPhoneSource(input);
    }

    /**
     * 号码分配
     *
     * @param input input参数
     */
    @Async
    @Override
    public void phoneDivid(PhoneDividInput input) {

        CountPhoneInput countPhoneInput = new CountPhoneInput();
        countPhoneInput.setBusiness(input.getBusiness());
        countPhoneInput.setGetTime(input.getGetTime());

        // 取得用于分配的号码列表
        List<PhoneRepository> phonesForDivid = bizPhoneRepMapper.phonesForDivid(countPhoneInput);

        // 给每一个营销团队分配号码
        List<PhoneRepository> phonesAfterDivid = dividPhoneWithTeam(phonesForDivid, input.getTeams());

        bizPhoneRepMapper.updatePhoneRepBatch(phonesAfterDivid);
    }

    /**
     * 根据参数创建号码来源
     *
     * @param input 参数列表
     * @return  号码来源
     */
    private String createPhoneSource(PhoneRepInput input) {
        String getTime = input.getGettime().replaceAll("-", "");
        return Joiner.on(PHONESOURCE_SPLIT).skipNulls().join(input.getBusiness(), getTime);
    }

    /**
     * 将电话号码转换为指定对象
     *  ① 校验通过 转化为号码库对象
     *  ② 校验失败 转化为导入失败对象
     *
     * @param getTime       获取时间
     * @param phoneSource   号码来源
     * @param userno        用户名
     * @param business      业务类型
     * @return
     */
    private Function<String, Object> transferPhone(final Date getTime, final String phoneSource, final String userno, final String business){
        return new Function<String, Object>() {
            @Nullable
            @Override
            public Object apply(String input) {

                if (Strings.isNullOrEmpty(input)) {
                    return new Object();
                }

                // 校验号码
                String errMsg = validatePhone(input);

                // 校验通过
                if (errMsg == null){
                    return createPhoneRep(input, getTime, phoneSource, business);
                }

                return createRecord(errMsg, userno);
            }
        };
    }

    /**
     * 构建一个号码库数据
     *
     * @param phone         号码
     * @param getTime       取得时间
     * @param phoneSource   号码来源
     * @param business      业务类型
     * @return              号码库数据
     */
    private PhoneRepository createPhoneRep(String phone, Date getTime, String phoneSource, String business) {
        PhoneRepository phoneRep = new PhoneRepository();
        if (Strings.isNullOrEmpty(phoneRep.getId())) {
            phoneRep.setId(UUID.randomUUID().toString());
        }
        phoneRep.setPhone(phone);
        phoneRep.setGetTime(getTime);
        phoneRep.setPhoneSource(phoneSource);
        phoneRep.setFilterOver(BizRequestConstant.FILTER_OVER_UNCOMPLETE);
        phoneRep.setPayphone(BizRequestConstant.PAYPHONE_YES);
        phoneRep.setBizName(business);
        phoneRep.setCleanStatus(BizRequestConstant.CLEAN_STATUS_CLEANING);
        return phoneRep;
    }

    /**
     * 号码池清洗内容校验
     *
     * @param phone     号码库对象
     * @return          null(校验通过)/not null(校验不通过)
     */
    private String validatePhone(String phone) {

    	boolean flg = true;

    	String errMsg = "电话号码为" + phone + "的数据:";

    	if (StringUtil.isEmpty(phone)) {
    		errMsg += "电话号码为空。";
    		flg = false;
    	} else if (phone.length() > 16) {
    		errMsg += "电话号码长度超出。";
    		flg = false;
    	}

    	if (!phone.matches(ConfigConstant.PHONE_CHECK)) {
    		errMsg += "电话号码格式有误。";
    		flg = false;
    	}

    	return flg ? null : errMsg;

    }

    /**
     * 创建导入失败记录对象
     *
     * @param errMsg    失败信息
     * @param userno    导入人
     * @return          导入失败记录
     */
    private ImportFailedRecord createRecord(String errMsg, String userno){

        ImportFailedRecord record = new ImportFailedRecord();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        record.setMenuno("6ff760be-a1d0-447c-841c-fe980dbe43cb");
        record.setMenuname("号码库管理");
        record.setFuncname("导入");
        record.setImportTime(df.format(new Date()));
        record.setFailedRemark(errMsg);
        record.setUserno(userno);

        return record;
    }

    /**
     * 为一个营销团队分配号码
     *
     * @param phones    号码列表
     * @param teams     营销团队列表
     * @return          拥有团队信息的号码列表
     */
    private List<PhoneRepository> dividPhoneWithTeam(List<PhoneRepository> phones, List<PhoneDividInput.Team> teams) {

        // 前一个团队个数
        int prevTeamPhones = 0;
        // 团队列表索引
        int teamIndex = 0;
        // 电话列表索引
        int phonesIndex = 0;

        for (PhoneRepository phone : phones) {

            PhoneDividInput.Team team = teams.get(teamIndex);

            // 当号码索引大于等于当前团队所需要的数量时，则当前团队已分配满，分给下一个团队
            if (phonesIndex >= team.getDividnumber() + prevTeamPhones) {
                // 前一个团队个数
                prevTeamPhones += team.getDividnumber();
                // 移向后一个团队
                teamIndex = teamIndex + 1;
                // 如果超过团队列表长度，则跳出
                if (teamIndex >= teams.size()) {
                    break;
                }
                team = teams.get(teamIndex);
            }
            // 为号码设置团队信息
            phone = phoneForDivid(phone, team);

            // 号码索引移向下一个
            phonesIndex = phonesIndex + 1;
        }

        return phones;
    }

    /**
     * 创建用于分配的号码元素
     *
     * @param phone 号码
     * @param team  团队信息
     * @return      拥有营销团队信息的号码
     */
    private PhoneRepository phoneForDivid(PhoneRepository phone, PhoneDividInput.Team team){
        phone.setDividtime(team.getDividtime());
        phone.setDividtoname(team.getDividtoname());
        phone.setDividtono(team.getDividtono());
        phone.setDividnumber(team.getDividnumber());
        return phone;
    }

    /**
     * 新增业务批次关联信息
     *
     * @param input 参数列表
     * @return      变更函数
     */
    private int addPhoneSource(PhoneRepInput input) {

        PhoneSourceHelp help = new PhoneSourceHelp();
        // 号码取得时间
        Date getTime = DateUtil.convertStringToDate( DateUtil.DEFULT_FORMAR, input.getGettime());
        help.setGetTime(getTime);
        help.setBizName(input.getBusiness());
        help.setPhoneSource(createPhoneSource(input));

        return phoneSourceHelpMapper.insertSelective(help);
    }
}
