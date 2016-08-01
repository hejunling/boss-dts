package com.ancun.boss.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ancun.boss.persistence.mapper.SystemMenuInfoMapper;
import com.ancun.boss.persistence.model.SystemMenuInfo;
import com.ancun.boss.persistence.model.SystemMenuInfoExample;
import com.ancun.boss.service.system.ISystemMenuInfoService;
import com.ancun.boss.util.PyUtil;
import com.ancun.utils.StringUtil;

/**
 * 
 *
 * @Created on 2015年5月25日
 * @author kfc
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class SystemMenuInfoServiceImpl implements ISystemMenuInfoService {
	@Resource
	private SystemMenuInfoMapper systemMenuInfoMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ancun.boss.service.system.ISystemMenuInfoService#
	 * selectPoplarSystemMenuInfo
	 * (com.ancun.boss.persistence.model.SystemMenuInfo)
	 */
	@Override
	public List<SystemMenuInfo> selectPoplarSystemMenuInfo(
			SystemMenuInfo systemMenuInfo) {

		SystemMenuInfoExample systemMenuInfoExample = new SystemMenuInfoExample();
		systemMenuInfoExample.setOrderByClause("ordered");
		SystemMenuInfoExample.Criteria systemMenuInfoExampleCriteria = systemMenuInfoExample
				.createCriteria();

		if (systemMenuInfo.getPmenuinfo() != null) {
			systemMenuInfoExampleCriteria.andPmenuinfoEqualTo(systemMenuInfo
					.getPmenuinfo());
		}

		systemMenuInfoExampleCriteria.andDeletedEqualTo("NO");

		List<SystemMenuInfo> systemMenuInfoList = systemMenuInfoMapper
				.selectByExample(systemMenuInfoExample);

		for (SystemMenuInfo systemMenuInfoSingle : systemMenuInfoList) {
			if (systemMenuInfoSingle.getOrdered() == systemMenuInfoSingle
					.getMaxleaf()) {
				systemMenuInfoSingle.setIsLeaf("true");
			} else {
				systemMenuInfoSingle.setIsLeaf("false");
			}
		}

		return systemMenuInfoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ancun.boss.service.system.ISystemMenuInfoService#
	 * selectSingleSystemMenuInfo
	 * (com.ancun.boss.persistence.model.SystemMenuInfo)
	 */
	@Override
	public SystemMenuInfo selectSingleSystemMenuInfo(
			SystemMenuInfo systemMenuInfo) {

		SystemMenuInfoExample systemMenuInfoExample = new SystemMenuInfoExample();
		systemMenuInfoExample.setOrderByClause("ordered");
		SystemMenuInfoExample.Criteria systemMenuInfoExampleCriteria = systemMenuInfoExample
				.createCriteria();

		systemMenuInfoExampleCriteria.andDeletedEqualTo("NO");
		systemMenuInfoExampleCriteria.andMenunoEqualTo(systemMenuInfo
				.getMenuno());

		List<SystemMenuInfo> systemMenuInfoList = systemMenuInfoMapper
				.selectByExample(systemMenuInfoExample);
		if (systemMenuInfoList != null && systemMenuInfoList.size() > 0) {
			return systemMenuInfoList.get(0);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ancun.boss.service.system.ISystemMenuInfoService#insertSystemMenuInfo
	 * (com.ancun.boss.persistence.model.SystemMenuInfo)
	 */
	@Override
	public int insertSystemMenuInfo(SystemMenuInfo systemMenuInfo) {

		if (StringUtil.isEmpty(systemMenuInfo.getPmenuinfo())) {
			SystemMenuInfoExample systemMenuInfoExample = new SystemMenuInfoExample();
			int size = systemMenuInfoMapper
					.countByExample(systemMenuInfoExample);

			systemMenuInfo.setOrdered((long) (size + 1));
			systemMenuInfo.setMaxleaf((long) (size + 1));
			systemMenuInfo.setPmenuinfo("0");
			systemMenuInfo.setLeveled((long) 1);
		} else {
			SystemMenuInfo parentSystemMenuInfo = systemMenuInfoMapper
					.selectByPrimaryKey(systemMenuInfo.getPmenuinfo());

			Map<String, Long> orderedMap = new HashMap<String, Long>();
			orderedMap.put("changeValue", (long) 1);
			orderedMap.put("thresholdValue",
					(long) parentSystemMenuInfo.getMaxleaf());
			systemMenuInfoMapper.updatePoplarOrdered(orderedMap);

			Map<String, Long> maxleafMap = new HashMap<String, Long>();
			maxleafMap.put("changeValue", (long) 1);

			
			
			maxleafMap.put("thresholdValue",
						(long) parentSystemMenuInfo.getMaxleaf() - 1);
			
			if (parentSystemMenuInfo.getOrdered() == parentSystemMenuInfo
					.getMaxleaf())	
			{
				//叶子增加叶子
				systemMenuInfoMapper.updatePoplarMaxleaf(maxleafMap);
			}else
			{
				//结点增加叶子
				systemMenuInfoMapper.updatePoplarMaxleafExceptLittleBrother(maxleafMap);
			}

			systemMenuInfo.setOrdered(parentSystemMenuInfo.getMaxleaf() + 1);
			systemMenuInfo.setMaxleaf(parentSystemMenuInfo.getMaxleaf() + 1);
			systemMenuInfo.setLeveled(parentSystemMenuInfo.getLeveled() + 1);
		}

		systemMenuInfo.setSpell(PyUtil.cn2py(systemMenuInfo.getMenuname()));
		systemMenuInfo.setDeleted("NO");
		systemMenuInfo.setMenuno(UUID.randomUUID().toString());
		return systemMenuInfoMapper.insertSelective(systemMenuInfo);
	}

	/**
	 * 逻辑删除树
	 * <p>
	 * 更新delete字段，同时也清除order和maxLeaf。这样方便以后的查询 如果要恢复，则使用校验位pId。
	 * 
	 * 
	 */
	@Override
	public int deleteLogicalPoplarSystemMenuInfo(SystemMenuInfo systemMenuInfo) {

		SystemMenuInfo valueSystemMenuInfo = new SystemMenuInfo();
		valueSystemMenuInfo.setDeleted("YES");

		SystemMenuInfoExample systemMenuInfoExample = new SystemMenuInfoExample();
		SystemMenuInfoExample.Criteria systemMenuInfoExampleCriteria = systemMenuInfoExample
				.createCriteria();
		systemMenuInfoExampleCriteria
				.andOrderedGreaterThanOrEqualTo(systemMenuInfo.getOrdered());
		systemMenuInfoExampleCriteria
				.andOrderedLessThanOrEqualTo(systemMenuInfo.getMaxleaf());

		int i = systemMenuInfoMapper.updateByExample(valueSystemMenuInfo,
				systemMenuInfoExample);

		Map<String, Long> orderedMap = new HashMap<String, Long>();
		orderedMap.put("changeValue", systemMenuInfo.getOrdered()
				- systemMenuInfo.getMaxleaf() + 1);
		orderedMap.put("thresholdValue", systemMenuInfo.getOrdered());
		systemMenuInfoMapper.updatePoplarOrdered(orderedMap);

		Map<String, Long> maxleafMap = new HashMap<String, Long>();
		maxleafMap.put("changeValue", systemMenuInfo.getOrdered()
				- systemMenuInfo.getMaxleaf() + 1);
		maxleafMap.put("thresholdValue", systemMenuInfo.getOrdered());
		systemMenuInfoMapper.updatePoplarMaxleaf(maxleafMap);

		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ancun.boss.service.system.ISystemMenuInfoService#
	 * deleteSingleSystemMenuInfo
	 * (com.ancun.boss.persistence.model.SystemMenuInfo)
	 */
	@Override
	public int deleteLogicalSingleSystemMenuInfo(SystemMenuInfo systemMenuInfo) {
		if (systemMenuInfo.getOrdered() != systemMenuInfo.getMaxleaf()) {
			return 0;
		}
		
		systemMenuInfo = systemMenuInfoMapper
				.selectByPrimaryKey(systemMenuInfo.getMenuno());
		
		Map<String, Long> orderedMap = new HashMap<String, Long>();
		orderedMap.put("changeValue",(long)-1);
		orderedMap.put("thresholdValue", systemMenuInfo.getOrdered());
		systemMenuInfoMapper.updatePoplarOrdered(orderedMap);

		Map<String, Long> maxleafMap = new HashMap<String, Long>();
		maxleafMap.put("changeValue", (long)-1);
		maxleafMap.put("thresholdValue", systemMenuInfo.getOrdered()-1);
		systemMenuInfoMapper.updatePoplarMaxleaf(maxleafMap);
				
		
		systemMenuInfo.setDeleted("YES");
		return systemMenuInfoMapper.updateByPrimaryKey(systemMenuInfo);
	}

	/**
	 * 失败，有BUG……物理删除树
	 * */
	public int deletePhysicalPoplarSystemMenuInfo(SystemMenuInfo systemMenuInfo) {
		int i = systemMenuInfoMapper.deleteByPrimaryKey(systemMenuInfo
				.getMenuno());

		Map<String, Long> orderedMap = new HashMap<String, Long>();
		orderedMap.put("changeValue", systemMenuInfo.getOrdered()
				- systemMenuInfo.getMaxleaf() + 1);
		orderedMap.put("thresholdValue", systemMenuInfo.getOrdered());
		systemMenuInfoMapper.updatePoplarOrdered(orderedMap);

		Map<String, Long> maxleafMap = new HashMap<String, Long>();
		maxleafMap.put("changeValue", systemMenuInfo.getOrdered()
				- systemMenuInfo.getMaxleaf() + 1);
		maxleafMap.put("thresholdValue", systemMenuInfo.getOrdered());
		systemMenuInfoMapper.updatePoplarMaxleaf(maxleafMap);

		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ancun.boss.service.system.ISystemMenuInfoService#
	 * updateNotOrderInfoSystemMenuInfo
	 * (com.ancun.boss.persistence.model.SystemMenuInfo)
	 */
	@Override
	public int updateNotOrderInfoSystemMenuInfo(SystemMenuInfo systemMenuInfo) {
		systemMenuInfo.setLeveled(null);
		systemMenuInfo.setOrdered(null);
		systemMenuInfo.setMaxleaf(null);

		systemMenuInfo.setSpell(PyUtil.cn2py(systemMenuInfo.getMenuname()));

		return systemMenuInfoMapper.updateByPrimaryKeySelective(systemMenuInfo);
	}

}
