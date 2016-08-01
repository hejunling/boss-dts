package com.ancun.common.persistence.mapper.dx;

import com.ancun.common.persistence.model.dx.UserInfo;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo> {

    /**
     * 更新，退订时间清空
     *
     * @param userInfo
     */
    void updateSelective(UserInfo userInfo);

    /**
     * 查询 所有rpcode
     *
     * @param config
     * @return
     */
    List<String> selectAllPersonRpcodes(BizTimerConfig config);

    /**
     * 查询 所有数量
     *
     * @param config
     * @return
     */
    int selectAllPersonCount(BizTimerConfig config);
}