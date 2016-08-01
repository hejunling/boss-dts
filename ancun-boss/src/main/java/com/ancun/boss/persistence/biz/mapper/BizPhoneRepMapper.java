package com.ancun.boss.persistence.biz.mapper;

import com.ancun.boss.persistence.model.BlackUserInfo;
import com.ancun.boss.persistence.model.ImportFailedRecord;
import com.ancun.boss.persistence.model.PhoneRepository;
import com.ancun.boss.persistence.model.PhoneSourceHelp;
import com.ancun.boss.pojo.Option;
import com.ancun.boss.pojo.phoneResp.CountPhoneInput;
import com.ancun.boss.pojo.phoneResp.PhoneRepInfo;
import com.ancun.boss.pojo.phoneResp.PhoneRespInput;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 号码池清洗自定义Mapper
 *
 * @Created on 2015年11月05日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizPhoneRepMapper {

    /**
     * 黑名单列表
     *
     * @return  黑名单列表
     */
    @Cacheable( value = "blackUserInfoCache")
    List<BlackUserInfo> blackUserInfos();

    /**
     * 批量添加号码库信息
     *
     * @param phoneReps 需要添加的号码库信息列表
     * @return  影响条数
     */
    int insertPhoneRepBatch(List<PhoneRepository> phoneReps);

    /**
     * 批量更新号码库信息
     *
     * @param phoneReps 需要更新的号码库信息列表
     * @return  影响条数
     */
    int updatePhoneRepBatch(List<PhoneRepository> phoneReps);

    /**
     * 批量添加导入失败记录
     *
     * @param importFailedRecords  需要添加的导入失败记录
     * @return  影响条数
     */
    int insertImportFailedRecordBatch(List<ImportFailedRecord> importFailedRecords);

    /**
     * 查找过滤完成的非当前批次的所有号码
     *
     * @param phoneSource 当前批次
     * @return      已付费号码列表
     */
    List<PhoneRepository> notPayPhoneReps(String phoneSource);

    /**
     * 根据业务取得获取日期列表
     *
     * @param business  业务
     * @return          获取日期列表
     */
    List<Option> getTimeOptions(String business);

    /**
     * 根据条件查询号码库信息
     *
     * @param input 查询条件
     * @return      指定号码库信息
     */
    List<PhoneRepInfo> phoneReps(PhoneRespInput input);

    /**
     * 取得所有需要分配电话到营销团队的批次
     *
     * @return  批次列表
     */
    List<String> phoneSources();

    /**
     * 根据批次取得电话数量
     *
     * @param phoneSource  批次相关信息
     * @return          号码数量
     */
    int countDividPhonesByPhoneSource(CountPhoneInput phoneSource);

    /**
     * 根据批次取得用于分配的电话列表
     *
     * @param phoneSource   批次
     * @return              电话列表
     */
    List<PhoneRepository> phonesForDivid(CountPhoneInput phoneSource);

    /**
     * 查询取得除了指定批次外的其他批次列表
     *
     * @param phoneSource   指定批次
     * @return              批次列表
     */
    List<PhoneSourceHelp> phoneSourceHelps(String phoneSource);
}
