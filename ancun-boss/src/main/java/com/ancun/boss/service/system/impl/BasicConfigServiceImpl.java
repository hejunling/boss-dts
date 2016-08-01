package com.ancun.boss.service.system.impl;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.mapper.SystemBasicConfigMapper;
import com.ancun.boss.persistence.mapper.UserDatapermissionMapper;
import com.ancun.boss.persistence.model.SystemBasicConfig;
import com.ancun.boss.persistence.model.SystemBasicConfigExample;
import com.ancun.boss.persistence.model.UserDatapermission;
import com.ancun.boss.persistence.model.UserDatapermissionExample;
import com.ancun.boss.pojo.system.BasicConfigInput;
import com.ancun.boss.pojo.system.BasicConfigOutput;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.boss.service.system.ICacheConfigService;
import com.ancun.boss.util.ContainerUtil;
import com.ancun.boss.util.PyUtil;
import com.ancun.core.exception.EduException;
import com.ancun.utils.StringUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 基础数据接口实现类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/29
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class BasicConfigServiceImpl implements IBasicConfigService {

    private static final Logger loger = LoggerFactory.getLogger(BasicConfigServiceImpl.class);
    @Resource
    private SystemBasicConfigMapper systemBasicConfigMapper;
    @Resource
    private ICacheConfigService cacheConfigService;

    @Resource
    private UserDatapermissionMapper userDatapermissionMapper;

    @Override
    public List<SystemBasicConfig> queryListByMoudleCode(
            BasicConfigInput basicConfigInput) throws EduException {

        List<SystemBasicConfig> allList = null;
        if (StringUtil.isNotEmpty(basicConfigInput.getMoudlecode())) {
            //从缓存管理中读取数据
            allList = cacheConfigService.queryAllBasicConfigs();
        }


        if (allList == null || allList.size() <= 0) {
            throw new EduException(MessageConstant.BASIC_CONFIG_IS_NULL);
        }

        // 根据moudleCode 返回相应的基础数据
        List<SystemBasicConfig> moudleList = getMoudleList(basicConfigInput.getMoudlecode(), allList);


        // 如果是数据权限（BIZ-NAME）  只获取当前用户关联的基础数据
//        if (basicConfigInput.getMoudlecode().equals(BizRequestConstant.MOUDLE_CODE_BIZ_NAME)) {
//            moudleList = getBizList(basicConfigInput.getUserno(), moudleList);
//        }


//        List<SystemBasicConfig> backList = new LinkedList<SystemBasicConfig>();
//        for (SystemBasicConfig basicConfig : moudleList) {
//            if (StringUtil.isNotEmpty(basicConfigInput.getName())) {
////                    loger.debug("输入值{},数据库中值{},是否包含{}", basicConfigInput.getName().toLowerCase(), basicConfig.getSpell().toLowerCase(), (basicConfig.getSpell().toLowerCase()).indexOf(basicConfigInput.getName().toLowerCase()));
//                if ((basicConfig.getSpell().toLowerCase()).indexOf(basicConfigInput.getName().toLowerCase()) > -1 ||
//                        basicConfig.getName().toLowerCase().indexOf(basicConfigInput.getName()) > -1) {
//                    backList.add(basicConfig);
//                }
//
//            } else {
//                backList.add(basicConfig);
//            }
//        }

        return moudleList;
    }

    @Override
    public BasicConfigOutput queryBasicConfigList(BasicConfigInput basicConfigInput) throws EduException {
        SystemBasicConfigExample example = new SystemBasicConfigExample();
        SystemBasicConfigExample.Criteria criteria = example.createCriteria();

        if (StringUtil.isNotEmpty(basicConfigInput.getMoudlecode())) {
            criteria.andMoudlecodeEqualTo(basicConfigInput.getMoudlecode());
        }
        criteria.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        example.setPage(basicConfigInput.getPage());
        example.setOrderByClause("BASICNO");

        List<SystemBasicConfig> list   = systemBasicConfigMapper.selectByExample(example);

        BasicConfigOutput output = new BasicConfigOutput();
        output.setBasicconfiglist(list);
        output.setPageinfo(basicConfigInput.getPage());

        return output;

    }

    @Override
    public SystemBasicConfig queryConfigByBasicNo(Integer basicno) throws EduException {
        if (basicno == null) {
            throw new EduException(MessageConstant.ID_MUST_BE_NOT_EMPTY);
        }
        return systemBasicConfigMapper.selectByPrimaryKey(basicno);
    }


    /**
     * 当前用户关联数据权限数据
     *
     * @param userno
     * @param moudleList
     * @return
     */
    private List<SystemBasicConfig> getBizList(String userno, List<SystemBasicConfig> moudleList) {
        List<UserDatapermission> userDatapermissionList = queryUserDataPermission(userno);
        if (userDatapermissionList == null || userDatapermissionList.size() <= 0)
            return moudleList;

        List<SystemBasicConfig> resultList = new LinkedList<SystemBasicConfig>();
        for (SystemBasicConfig systemBasicConfig : moudleList) {
            for (UserDatapermission userDatapermission : userDatapermissionList) {
                if (systemBasicConfig.getBasicno().equals(userDatapermission.getBasicno())) {
                    resultList.add(systemBasicConfig);
                }
            }
        }

        return resultList;
    }

    /**
     * 根据moudleCode 获取List
     *
     * @param moudlecode
     * @param allList
     * @return
     */
    private List<SystemBasicConfig> getMoudleList(String moudlecode, List<SystemBasicConfig> allList) {
        List<SystemBasicConfig> moudleList = new LinkedList<SystemBasicConfig>();

        for (SystemBasicConfig systemBasicConfig : allList) {
            if (systemBasicConfig.getMoudlecode().equals(moudlecode)) {
                moudleList.add(systemBasicConfig);
            }
        }

        return moudleList;
    }
    
    /**
     *  从缓存中，根据moudleCode 获取List
     *
     * @param moudlecode
     * @param moudlecode
     * @return
     */
    public List<SystemBasicConfig> getMoudleListFromCache(String moudlecode) {
        List<SystemBasicConfig> moudleList = new LinkedList<SystemBasicConfig>();
        
        moudleList = cacheConfigService.queryAllBasicConfigs();
        
        for (SystemBasicConfig systemBasicConfig : moudleList) {
            if (systemBasicConfig.getMoudlecode().equals(moudlecode)) {
                moudleList.add(systemBasicConfig);
            }
        }

        return moudleList;
    }

    /**
     * 获取当前用户相关 数据权限
     *
     * @param userno
     * @return
     */
    private List<UserDatapermission> queryUserDataPermission(String userno) {
        UserDatapermissionExample example = new UserDatapermissionExample();
        UserDatapermissionExample.Criteria criteri = example.createCriteria();
        criteri.andUsernoEqualTo(userno);
        return userDatapermissionMapper.selectByExample(example);
    }




    @Override
    public SystemBasicConfig selectSingleSystemBasicConfigByBasicNo(SystemBasicConfig systemBasicConfig) throws EduException {

        SystemBasicConfigExample ex = new SystemBasicConfigExample();
        SystemBasicConfigExample.Criteria c = ex.createCriteria();
        c.andBasicnoEqualTo(systemBasicConfig.getBasicno());
        c.andDeletedEqualTo(BizRequestConstant.NO);

       List<SystemBasicConfig> sList=systemBasicConfigMapper.selectByExample(ex);

       if(ContainerUtil.isNotEmptyList(sList)){
    	   return sList.get(0);
       }else{
    	   return null;
       }
    }

	/**
	 * 根据模块code列表取得其相对应基础数据封装到map中
	 *
	 * @param moudlecodes	模块code列表
	 * @return	封装好的Map
	 */
	@Override
	public Map<String, List<SystemBasicConfig>> queryByMoudlecodes(List<String> moudlecodes, String userno) {

		Map<String, List<SystemBasicConfig>> listMultimapByMoudlecodes = Maps.newHashMap();

		// 循环设置基础数据
		for (String moudlecode : moudlecodes) {
			BasicConfigInput input = new BasicConfigInput();
			input.setMoudlecode(moudlecode);
			input.setUserno(userno);
			// 从缓存中取基础数据
			List<SystemBasicConfig> configs = queryListByMoudleCode(input);
			// 将这些基础数据填充到map中
			listMultimapByMoudlecodes.put(moudlecode, configs);
		}

		return listMultimapByMoudlecodes;
	}
    @Override
    public List<SystemBasicConfig> selectSerialSystemBasicConfigMoudle() throws EduException {

        return systemBasicConfigMapper.selectDistinctMoudle();
    }

    @Override
    @CacheEvict(value = "basicConfigCache", allEntries = true)// 清空basicConfigCache 缓存 再查询时运行sql
    public Integer updateSystemBasicConfigName(
            BasicConfigInput basicConfigInput) throws EduException {
        SystemBasicConfig newSystemBasicConfig = new SystemBasicConfig();
        newSystemBasicConfig.setBasicno(basicConfigInput.getBasicno());
        newSystemBasicConfig.setName(basicConfigInput.getName());
        newSystemBasicConfig .setSpell(PyUtil.cn2py(basicConfigInput.getName()));
        return systemBasicConfigMapper.updateByPrimaryKeySelective(newSystemBasicConfig);
    }

    /**
     * 根据code 取得业务基本信息
     * add by zkai on 2016/04/07
     * @param code
     * @return
     */
    public SystemBasicConfig getBizInfoByCoed(String code){
        if(StringUtil.isBlank(code)){
            return new SystemBasicConfig();
        }
        //从缓存管理中读取数据
        List<SystemBasicConfig> allList = cacheConfigService.queryAllBasicConfigs();
        if (allList == null || allList.size() <= 0) {
            throw new EduException(MessageConstant.BASIC_CONFIG_IS_NULL);
        }
        List<SystemBasicConfig> backList = new ArrayList<SystemBasicConfig>();
        for (SystemBasicConfig systemBasicConfig : allList) {
            if (systemBasicConfig.getCode().equals(code)) {
                backList.add(systemBasicConfig);
            }
        }
        if(backList.size() != 1){
            throw  new EduException(MessageConstant.DATA_ERROR);
        }
        return backList.get(0);

    }
}
