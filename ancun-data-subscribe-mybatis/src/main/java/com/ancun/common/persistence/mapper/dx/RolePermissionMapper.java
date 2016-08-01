package com.ancun.common.persistence.mapper.dx;

import com.ancun.common.persistence.model.dx.RolePermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface RolePermissionMapper extends Mapper<RolePermission> {

    /**
     *
     * @param userNo
     */
    void deleteRolePermissionByUserNo(@Param("userNo") String userNo);
}