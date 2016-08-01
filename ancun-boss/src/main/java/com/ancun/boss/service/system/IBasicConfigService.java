package com.ancun.boss.service.system;

import com.ancun.boss.persistence.model.SystemBasicConfig;
import com.ancun.boss.pojo.system.BasicConfigInput;
import com.ancun.boss.pojo.system.BasicConfigOutput;
import com.ancun.core.exception.EduException;

import java.util.List;
import java.util.Map;

/**
 * 基础数据接口类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/29
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IBasicConfigService {
    /**
     * 通过MODULECODE 查询数据列表
     *
     * @param basicConfigInput
     * @return
     * @throws EduException
     */
    public List<SystemBasicConfig> queryListByMoudleCode(
            BasicConfigInput basicConfigInput) throws EduException;


    /**
     * 分页查询 基础数据信息
     *
     * @param basicConfigInput
     * @return
     * @throws EduException
     */
    public BasicConfigOutput queryBasicConfigList(BasicConfigInput basicConfigInput) throws EduException;


    /**
     * 根据主键查询详情
     *
     * @param basicno
     * @return
     * @throws EduException
     */
    public SystemBasicConfig queryConfigByBasicNo(Integer basicno) throws EduException;

    /**
     * 更新名字
     */
    public Integer updateSystemBasicConfigName(BasicConfigInput basicConfigInput) throws EduException;

    public List<SystemBasicConfig> selectSerialSystemBasicConfigMoudle() throws EduException;

    public SystemBasicConfig selectSingleSystemBasicConfigByBasicNo(SystemBasicConfig systemBasicConfig) throws EduException;

    /**
     * 根据模块code列表取得其相对应基础数据封装到map中
     *
     * @param moudlecodes 模块code列表
     * @return 封装好的Map
     */
    public Map<String, List<SystemBasicConfig>> queryByMoudlecodes(List<String> moudlecodes, String userno);
    
    public List<SystemBasicConfig> getMoudleListFromCache(String moudlecode);

    /**
     * 根据code 取得业务基本信息
     * add by zkai on 2016/04/07
     * @param code
     * @return
     */
    public SystemBasicConfig getBizInfoByCoed(String code);

}
