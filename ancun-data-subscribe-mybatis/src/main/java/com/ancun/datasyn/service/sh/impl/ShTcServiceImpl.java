package com.ancun.datasyn.service.sh.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.mapper.master.BizComboInfoMapper;
import com.ancun.common.persistence.mapper.sh.ShUserTaocanMapper;
import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.common.persistence.model.sh.ShUserTaocan;
import com.ancun.datasyn.activemq.Producer;
import com.ancun.datasyn.common.IBizProviceService;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.taocan.BossTaocanInfo;
import com.ancun.datasyn.pojo.taocan.TaocanInput;
import com.ancun.datasyn.service.sh.IShTcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 上海音证宝接口实现类
 * User: zkai
 * Date: 2016/5/19
 * Time: 19:25
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
@TargetDataSource(name = "shV2")
public class ShTcServiceImpl implements IShTcService {

    private static final Logger log = LoggerFactory.getLogger(ShTcServiceImpl.class);

    @Resource
    ShUserTaocanMapper shUserTaocanMapper;

    @Resource
    IBizProviceService iBizProviceService;

    @Resource
    private Producer producer;

    @Resource
    BizComboInfoMapper bizComboInfoMapper;


    /**
     * 插入上海套餐队列
     */
    @Override
    public void insertShTcInfoQ(TaocanInput input){
        // 得到需要同步的列表
        List<ShUserTaocan> shUserTaocanList = getSynTaocanList(input);
        checkList(shUserTaocanList);
        log.info("需要同步上海音证宝套餐列表 "+ shUserTaocanList.size() + " 条");
        // 将信息插入到队列中
        insertQueue(shUserTaocanList);

    }

    /**
     * 取得需要同步的套餐信息
     * @return
     */
    private List<ShUserTaocan> getSynTaocanList(TaocanInput input){
        Example example = new Example(ShUserTaocan.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("updateTime",input.getStartime());
        criteria.andLessThanOrEqualTo("updateTime",input.getEndtime());
        // 或者时间为0000-00-00 00:00:00
        example.or(new Example(ShUserTaocan.class).createCriteria().andEqualTo("updateTime", SynConstant.DEFAULT_DATE));
        return shUserTaocanMapper.selectByExample(example);
    }

    /**
     * 分省字段 对应 boss字段
     * @param shUserTaocan
     * @return
     */
    private BizComboInfo uniTcToBossTc(ShUserTaocan shUserTaocan){
        BizComboInfo taocanInfo = new BizComboInfo();
        taocanInfo.setTcid(shUserTaocan.getTcid()); // 套餐ID
        taocanInfo.setName((shUserTaocan.getTaocanName())); // 套餐名称
        taocanInfo.setPrice(shUserTaocan.getTaocanPrice()); // 价格(月/元)
        taocanInfo.setType(shUserTaocan.getTaocanType()); // 套餐类型(1:主叫录音;2:双向录音;3:接入号录音)
        taocanInfo.setCreateTime(shUserTaocan.getTaocanCreatetime()); // 创建时间
        taocanInfo.setFinishTime(shUserTaocan.getTaocanFinishtime()); // 停用时间
        taocanInfo.setSpace(Long.valueOf(shUserTaocan.getStoreSpace())); // 存储空间
        taocanInfo.setBizNo(SynConstant.BIZ_DX_SHANGHAI); // 业务编号
        taocanInfo.setStatus(shUserTaocan.getTaocanStatus()); // 状态(1:启用;2:停用)
        taocanInfo.setRemark(shUserTaocan.getTaocanRemark()); // 备注
        taocanInfo.setDefaultTaocan(null); // 存储时间
        taocanInfo.setCategory(null); // 套餐类别(1:存储空间;2:存储时间)
        taocanInfo.setDefaultTaocan(null); // 套餐标记默认为NULL(1:默认套餐)
        taocanInfo.setRpcode(SynConstant.SHANGHAI_RPCODE); // 省份代码
        return taocanInfo;
    }


    /**
     * 将信息插入到队列中
     * @param shUserTcList
     */
    private void insertQueue(List<ShUserTaocan> shUserTcList){
        Date synTime = new Date(); // 同步时间
        UUID uuid = UUID.randomUUID();
        for (ShUserTaocan shUserTaocan: shUserTcList) {
            BizComboInfo taocanInfo = new BizComboInfo();
            BossTaocanInfo bossTaocanInfo = new BossTaocanInfo();
            String errorInfo = null ; // 错误信息
            try {
                taocanInfo = uniTcToBossTc(shUserTaocan);
            }catch (Exception e){
                log.debug("上海音证宝字段对应boss字段异常 = {},shUserTaocan = {}",e,shUserTaocan);
                errorInfo = "上海音证宝字段对应boss字段异常:"+ e.getMessage() + " shUserTaocan:" + shUserTaocan;
            }
            bossTaocanInfo.setBizname("上海音证宝");
            bossTaocanInfo.setUuid(uuid);
            bossTaocanInfo.setBizComboInfo(taocanInfo);
            bossTaocanInfo.setSynSize(shUserTcList.size());
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
