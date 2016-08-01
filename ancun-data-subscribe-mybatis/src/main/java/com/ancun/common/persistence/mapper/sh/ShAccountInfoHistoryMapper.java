package com.ancun.common.persistence.mapper.sh;

import com.ancun.common.persistence.model.sh.ShAccountInfoHistory;
import com.ancun.common.persistence.model.sh.ShBizUserInfoHistory;
import com.ancun.datasyn.pojo.userlife.UserLifeInput;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ShAccountInfoHistoryMapper extends Mapper<ShAccountInfoHistory> {


    /**
     * 组装查询上海用户历史变化数据
     *
     * @param
     * @return
     */
    List<ShBizUserInfoHistory> selectShBizUserInfoHistory(UserLifeInput userLifeInput);
}