package com.ancun.datasyn.common.impl;

import com.ancun.common.datasource.TargetDataSource;
import com.ancun.common.persistence.model.master.BizProvice;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datasyn.cache.ICacheConfigService;
import com.ancun.datasyn.common.IBizProviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础数据 缓存
 *
 * @author zkai
 * @version 1.0
 * @Created on 2016/05/16
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class  BizProviceServiceImpl implements IBizProviceService {

    @Resource
    private ICacheConfigService iCacheConfigService;


    /**
     * 得到bizprovicemap
     * @return
     * @throws
     */
    public Map<String,String> getBizProviceMap(){
        // 从缓存中获取bizProvice
        List<BizProvice> bizProviceList = iCacheConfigService.queryAllBizProvice();
        if (bizProviceList == null || bizProviceList.size() <= 0) {
            throw new CustomException("bizProvice 数据为空");
        }

        Map<String,String > map = new HashMap<String, String>();
        try{
            for (int i = 0; i <bizProviceList.size() ; i++) {
                map.put(bizProviceList.get(i).getBizNo(),bizProviceList.get(i).getRpcode());
            }
        }catch (Exception e){
            throw  new CustomException("业务编号对应的省份信息为空或者业务编号对应的省份信息不唯一");
        }
        return map;

//        // 数据处理
//        List<BizProvice> bizProvices = new ArrayList<BizProvice>();
//        for (BizProvice bizProvice : bizProviceList) {
//            if (bizProvice.getBizNo().equals(bizno)) {
//                bizProvices.add(bizProvice);
//            }
//        }
//        if(bizProvices.size() != 1){
//            throw  new CustomException("业务编号对应的省份信息为空或者业务编号对应的省份信息不唯一");
//        }
//        return bizProvices.get(0).getRpcode();
    }
}
