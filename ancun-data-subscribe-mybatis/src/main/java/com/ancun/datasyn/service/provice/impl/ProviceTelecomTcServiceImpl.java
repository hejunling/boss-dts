package com.ancun.datasyn.service.provice.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.dx.UserTaocanMapper;
import com.ancun.common.persistence.model.dx.UserTaocan;
import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.CacheConstant;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.taocan.BossTaocanInfo;
import com.ancun.datasyn.pojo.taocan.TaocanInput;
import com.ancun.datasyn.service.provice.IProviceTelecomTcService;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 分省电信宝接口类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:25
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "dxProvince")
public class ProviceTelecomTcServiceImpl implements IProviceTelecomTcService {

    private static final Logger log = LoggerFactory.getLogger(ProviceTelecomTcServiceImpl.class);

    @Resource
    UserTaocanMapper userTaocanMapper;

    @Resource
    private Producer producer;


    /**
     * 插入分省电信队列
     */
    @Override
    public void insertProviceTcInfoQ(TaocanInput input){
        // 得到需要同步的列表
        List<UserTaocan> userTaocanList = getSynTaocanList(input);

        checkList(userTaocanList);

        log.info("需要同步分省电信套餐列表 "+ userTaocanList.size() + " 条");
        // 将信息插入到队列中
        insertQueue(userTaocanList);

    }

    /**
     * 取得需要同步的套餐信息
     * @return
     */
    private List<UserTaocan> getSynTaocanList(TaocanInput input){
        Example example = new Example(UserTaocan.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(UserTaocan.class).createCriteria().andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return userTaocanMapper.selectByExample(example);
    }

    /**
     * 分省字段 对应 boss字段
     * @param userTaocan
     * @return
     */
    private BizComboInfo uniTcToBossTc(UserTaocan userTaocan){
        BizComboInfo taocanInfo = new BizComboInfo();
        taocanInfo.setTcid(userTaocan.getTcid()); // 套餐ID
        taocanInfo.setName((userTaocan.getTaocanName())); // 套餐名称
        taocanInfo.setPrice(userTaocan.getTaocanPrice()); // 价格(月/元)
        taocanInfo.setType(userTaocan.getTaocanType()); // 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
        taocanInfo.setCreateTime(userTaocan.getTaocanCreatetime()); // 创建时间
        taocanInfo.setFinishTime(userTaocan.getTaocanFinishtime()); // 停用时间
        taocanInfo.setSpace(Long.valueOf(userTaocan.getStoreSpace())); // 存储空间
        String bizno = CacheConstant.DX_PROVINCE_MAP.get(userTaocan.getRpcode());
        if(StringUtil.isNotBlank(bizno)){
            taocanInfo.setBizNo( bizno); // 业务编号
        }else {
            throw new CustomException("PROVICE_BIZ 中rpcode对应的Bizno不存在");
        }

        taocanInfo.setStatus(userTaocan.getTaocanStatus()); // 状态(1:启用;2:停用)
        taocanInfo.setRemark(userTaocan.getTaocanRemark()); // 备注
        taocanInfo.setDuration(userTaocan.getTcduration()); // 存储时间
        taocanInfo.setCategory(null); // 套餐类别(1:存储空间;2:存储时间)
        taocanInfo.setDefaultTaocan(userTaocan.getTcFlag()); // 套餐标记默认为NULL(1:默认套餐)
        taocanInfo.setRpcode(userTaocan.getRpcode()); // 省份代码
        return taocanInfo;
    }


    /**
     * 将信息插入到队列中
     * @param userTcList
     */
    private void insertQueue(List<UserTaocan> userTcList){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (UserTaocan userTaocan: userTcList) {
            BizComboInfo taocanInfo = new BizComboInfo();
            BossTaocanInfo bossTaocanInfo = new BossTaocanInfo();
            String errorInfo = null ; // 错误信息
            try {
                taocanInfo = uniTcToBossTc(userTaocan);
            }catch (Exception e){
                log.debug("电信分省套餐字段对应boss字段异常 = {},userTaocan = {}",e,userTaocan);
                errorInfo = "电信分省套餐字段对应boss字段异常:"+ e.getMessage() + " userTaocan:" + userTaocan;
            }
            bossTaocanInfo.setBizname("分省电信");
            bossTaocanInfo.setUuid(uuid);
            bossTaocanInfo.setBizComboInfo(taocanInfo);
            bossTaocanInfo.setSynSize(userTcList.size());
            bossTaocanInfo.setSynTime(synTime);
            bossTaocanInfo.setErrorInfo(errorInfo);
            this.producer.sendComboQueue(bossTaocanInfo);
//            this.producer.sendComboQueue(taocanInfo);
        }
    }
    /**
     * 检查用户数量
     *
     * @param list
     */
    private void checkList(List list) {
        if (null == list || list.size() <= 0) {
            return;
        }
    }

}
