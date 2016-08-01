package com.ancun.boss.persistence.biz;

import com.ancun.boss.persistence.model.FunctionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 查询功能菜单
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/10/13
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizFunctionInfoMapper {
    /**
     * 根据菜单编号 查询对应功能权限
     *
     * @param menuno
     * @return
     */
    List<FunctionInfo> queryFunByMenuNo(@Param("menuno") String menuno);
}
