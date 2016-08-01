package com.ancun.common.persistence.mapper.dx;

import com.ancun.common.persistence.model.dx.EntUserInfo;
import com.ancun.common.persistence.model.master.BizTimerConfig;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EntUserInfoMapper extends Mapper<EntUserInfo> {
    /**
     * 查询企业用户，与汇工作兼容
     *
     * @param userNo
     * @param userTel
     * @param rpCode
     * @return
     */
    List<EntUserInfo> selectEntUserInfos(@Param("userNo") String userNo, @Param("userTel") String userTel, @Param("rpCode") String rpCode);

    /**
     * 更新，退订时间清空
     *
     * @param entUserInfo
     */
    void updateSelective(EntUserInfo entUserInfo);


    /**
     * 查询 所有rpcode
     *
     * @param config
     * @return
     */
    List<String> selectAllEntRpcodes(BizTimerConfig config);

    /**
     * 查询 所有数量
     *
     * @param config
     * @return
     */
    int selectAllEntCount(BizTimerConfig config);
}