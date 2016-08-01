package com.ancun.datasyn.service.cp.unicom.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.cp.unicom.UniUserTaocanMapper;
import com.ancun.common.persistence.model.cp.unicom.UniUserTaocan;
import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.taocan.BossTaocanInfo;
import com.ancun.datasyn.pojo.taocan.TaocanInput;
import com.ancun.datasyn.service.cp.unicom.ICpUnicomTcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * cp联通接口实现类
 * User: zkai
 * Date: 2016/5/13
 * Time: 14:05
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "cpUnicom")
public class CpUnicomTcServiceImpl implements ICpUnicomTcService {
    private static final Logger log = LoggerFactory.getLogger(CpUnicomTcServiceImpl.class);

    @Resource
    UniUserTaocanMapper uniUserTaocanMapper;


    @Resource
    private Producer producer;



    /**
     * 插入分省联通队列
     */
    @Override
    public void insertUnicomTcInfoQ(TaocanInput input){
        // 得到需要同步的列表
        List<UniUserTaocan> uniUserTcList = getSynTaocanList(input);
        checkList(uniUserTcList);

        log.info("需要同步cp联通套餐列表 "+ uniUserTcList.size() + " 条");

        // 将信息插入到队列中
        insertQueue(uniUserTcList);

    }

    /**
     * 取得需要同步的套餐信息
     * @return
     */
    private List<UniUserTaocan> getSynTaocanList(TaocanInput input){
        Example example = new Example(UniUserTaocan.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(UniUserTaocan.class).createCriteria().andEqualTo("updateTime",SynConstant.DEFAULT_DATE));
        return uniUserTaocanMapper.selectByExample(example);
    }

    /**
     * 分省字段 对应 boss字段
     * @param uniUserTaocan
     * @return
     */
    private BizComboInfo uniTcToBossTc(UniUserTaocan uniUserTaocan){
        BizComboInfo taocanInfo = new BizComboInfo();
        taocanInfo.setTcid(uniUserTaocan.getTcid()); // 套餐ID
        taocanInfo.setName((uniUserTaocan.getTaocanName())); // 套餐名称
        taocanInfo.setPrice(uniUserTaocan.getTaocanPrice()); // 价格(月/元)
        taocanInfo.setType(uniUserTaocan.getTaocanType()); // 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
        taocanInfo.setCreateTime(uniUserTaocan.getTaocanCreatetime()); // 创建时间
        taocanInfo.setFinishTime(uniUserTaocan.getTaocanFinishtime()); // 停用时间
        taocanInfo.setSpace(Long.valueOf(uniUserTaocan.getStoreSpace())); // 存储空间
        taocanInfo.setBizNo(SynConstant.BIZ_LT_CP); // 业务编号
        taocanInfo.setStatus(uniUserTaocan.getTaocanStatus()); // 状态(1:启用;2:停用)
        taocanInfo.setRemark(uniUserTaocan.getTaocanRemark()); // 备注
        taocanInfo.setDefaultTaocan(null); // 存储时间
        taocanInfo.setCategory(null); // 套餐类别(1:存储空间;2:存储时间)
        taocanInfo.setDefaultTaocan(null); // 套餐标记默认为NULL(1:默认套餐)
        taocanInfo.setRpcode(SynConstant.ALL_PROVICE); // 省份代码
        return taocanInfo;
    }


    /**
     * 将信息插入到队列中
     * @param uniUserTcList
     */
    private void insertQueue(List<UniUserTaocan> uniUserTcList){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (UniUserTaocan uniUserTaocan: uniUserTcList) {
            BizComboInfo taocanInfo = new BizComboInfo();
            BossTaocanInfo bossTaocanInfo = new BossTaocanInfo();
            String errorInfo = null ; // 错误信息
            try {
                taocanInfo = uniTcToBossTc(uniUserTaocan);
            }catch (Exception e){
                log.debug("cp联通字段对应boss字段异常 = {},uniUserTaocan = {}",e,uniUserTaocan);
                errorInfo = "cp联通字段对应boss字段异常:"+ e.getMessage() + " uniUserTaocan:" + uniUserTaocan;
            }
            bossTaocanInfo.setBizname("cp联通");
            bossTaocanInfo.setUuid(uuid);
            bossTaocanInfo.setBizComboInfo(taocanInfo);
            bossTaocanInfo.setSynSize(uniUserTcList.size());
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

