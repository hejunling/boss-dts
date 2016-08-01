package com.ancun.datasyn.service.master.impl;

import com.ancun.common.persistence.mapper.master.BizComboInfoMapper;
import com.ancun.common.persistence.model.master.BizComboInfo;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datasyn.pojo.taocan.TaocanInput;
import com.ancun.datasyn.service.cp.unicom.ICpUnicomTcService;
import com.ancun.datasyn.service.master.ITaocanservice;
import com.ancun.datasyn.service.provice.IProviceTelecomTcService;
import com.ancun.datasyn.service.provice.IProviceUnicomTcService;
import com.ancun.datasyn.service.sh.IShTcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 套餐实现类
 * User: zkai
 * Date: 2016/5/19
 * Time: 16:02
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class TaocanServiceImpl implements ITaocanservice {
    private static final Logger log = LoggerFactory.getLogger(TaocanServiceImpl.class);

    @Resource
    BizComboInfoMapper bizComboInfoMapper;

    @Resource
    ICpUnicomTcService unicomTcService;

    @Resource
    IShTcService shTcService;

    @Resource
    IProviceTelecomTcService proviceTelecomTcService;

    @Resource
    IProviceUnicomTcService proviceUnicomTcService;

    /**
     * 将个业务信息插入到队列
     */
    public void insertTcInfoQ(TaocanInput input){
       // cp联通队列
        unicomTcService.insertUnicomTcInfoQ(input);
        // 上海电信队列
        shTcService.insertShTcInfoQ(input);
       // 电信分省
        proviceTelecomTcService.insertProviceTcInfoQ(input);
       // 联通电信
        proviceUnicomTcService.insertProviceTcInfoQ(input);
    }


    /**
     * 同步套餐信息（插入，修改）
     * @param taocanInfo
     */
    public void synBossTaocanInfo(BizComboInfo taocanInfo) {
        List<BizComboInfo> bizComboInfos = vailBossTaocan(taocanInfo);
        if(bizComboInfos.size() == 1 ){
            // 修改boss系统数据
            try{
                updateBossTaocanInfo(taocanInfo);
            }catch (Exception updateE){
                log.error("boss 系统套餐数据修改失败",updateE);
                throw new CustomException("boss 系统套餐数据修改失败，bizComboInfo=" + taocanInfo+"错误原因="+updateE);
            }

        }else if(bizComboInfos.size() == 0){
            // 插入boss系统
            try {
                insertBossTaocanInfo(taocanInfo);
            }catch (Exception insertE){
                log.error("boss 系统套餐数据添加失败",insertE);
                throw new CustomException("boss 系统套餐数据添加失败，bizComboInfo=" + taocanInfo+"错误原因="+insertE);
            }

        }else {
            log.error("boss 系统套餐数据异常");
            throw new CustomException("boss 系统套餐数据异常,同bizno，tcid不唯一",bizComboInfos.size());
        }
    }

    /**
     * 校验boss系统套餐信息
     * @param taocanInfo
     * @return
     */
    private  List<BizComboInfo> vailBossTaocan(BizComboInfo taocanInfo){
        String bizno = taocanInfo.getBizNo();
        Long tcid = taocanInfo.getTcid();

        // boss taocan信息查询对象
        BizComboInfo taocanInfoSelect = new BizComboInfo();
        taocanInfoSelect.setBizNo(bizno);
        taocanInfoSelect.setTcid(tcid);
        List<BizComboInfo> bizComboInfos = bizComboInfoMapper.select(taocanInfoSelect);
        return bizComboInfos;

    }

    /**
     * 修改boss的套餐信息
     * @param taocanInfo
     */
    private void updateBossTaocanInfo(BizComboInfo taocanInfo){
        // boss taocan信息查询对象
        Example example = new Example(BizComboInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("tcid",taocanInfo.getTcid());
        criteria.andEqualTo("bizNo",taocanInfo.getBizNo());
        bizComboInfoMapper.updateByExample(taocanInfo,example);

    }

    /**
     * 添加boss的套餐信息
     * @param taocanInfo
     */
    private void insertBossTaocanInfo(BizComboInfo taocanInfo){
        bizComboInfoMapper.insert(taocanInfo);
    }
}
