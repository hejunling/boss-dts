package com.ancun.common.persistence.mapper.master;

import com.ancun.common.persistence.model.master.BizUserInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizUserInfoMapper extends Mapper<BizUserInfo> {

    /**
     * @param bizNo
     * @return
     */
    List<BizUserInfo> selectBizUserInfoByBizNo(@Param("bizNo") String bizNo);
}