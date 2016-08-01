package com.ancun.boss.business.service.dataDic.impl;


import com.ancun.boss.business.constant.Constant;
import com.ancun.boss.business.persistence.mapper.BizComboInfoMapper;
import com.ancun.boss.business.persistence.mapper.BizProviceMapper;
import com.ancun.boss.business.persistence.mapper.BizUserInfoMapper;
import com.ancun.boss.business.persistence.module.*;
import com.ancun.boss.business.pojo.taocanInfo.*;
import com.ancun.boss.business.service.dataDic.IQueryDictionaryService;
import com.ancun.boss.business.service.dataDic.IUserTaocanService;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.boss.util.StringUtils;
import com.ancun.core.exception.EduException;
import com.ancun.core.page.Page;
import com.ancun.utils.DateUtils;
import com.ancun.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 用户套餐处理
 * @Created on 2016年4月6日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class UserTaocanServiceImpl implements IUserTaocanService {

	@Resource
	private BizComboInfoMapper bizComboInfoMapper;

	@Resource
	private BizUserInfoMapper bizUserInfoMapper;

	@Resource
	private IQueryDictionaryService queryDictionaryService;

	@Resource
	private IBasicConfigService basicConfigService;

	@Resource
	private BizProviceMapper bizProviceMapper;

	/**
	 * 套餐添加
	 * @param in
     */
	@Override
	public void addTaocanInfo(TaocanInfoInput in) {

		//验证套餐名称,在该省份下已存在时抛出异常
		validTaocanName(in);

		if(StringUtils.isNotEmpty(in.getTcflag())){
			//验证是否存在默认套餐,在该省份下已存在时抛出异常
			validTcflag(in);
		}

		BizComboInfo bean = new BizComboInfo();
		bean.setName(in.getTaocanname());

		// 套餐价格
		Double taocanPrice = null;
		// 如果异常，套餐价格格式不正确
		try {
			taocanPrice	=Double.valueOf(in.getTaocanprice());
		}catch (Exception e){
			throw new EduException(MessageConstant.TAOCAN_PRICE_IS_ERROR);
		}

		bean.setPrice(BigDecimal.valueOf(taocanPrice));
		bean.setBizNo(in.getBizno());
		// 根据bizno找到对应省份
		bean.setRpcode(getBizProviceByBizno(in.getBizno()).getRpcode());

		bean.setSpace(Long.valueOf(in.getStorespace()));
		bean.setDuration(in.getTcduration());
		bean.setRemark(in.getTaocanremark());
		bean.setType(in.getTaocantype());
		bean.setCreateTime(DateUtils.getCurrentDate());
		bean.setDefaultTaocan(in.getTcflag());
		bean.setStatus(Constant.TAOCAN_STATUS_1);
		bean.setTcid(Long.valueOf(getMaxTcid(in.getBizno())) + 1 );
		if (bizComboInfoMapper.insert(bean) != 1)
			throw new EduException(MessageConstant.DATA_ADD_FAILED);
	}

	/**
	 * 验证套餐名称,在该业务下已存在时抛出异常
	 * @param in
	 */
	public void validTaocanName(TaocanInfoInput in) {
		BizComboInfoExample example = new BizComboInfoExample();
		BizComboInfoExample.Criteria criteria = example.createCriteria();

		// 业务编号
		criteria.andBizNoEqualTo(in.getBizno());
		// 套餐名称
		criteria.andNameEqualTo(in.getTaocanname());
		// 状态也为开通
		criteria.andStatusEqualTo(Constant.TAOCAN_STATUS_1);

		int countByExample = bizComboInfoMapper.countByExample(example);
		if (countByExample > 0){
			throw new EduException(MessageConstant.TAOCAN_NAME_REPEATED);
		}
	}


	/**
	 * 套餐修改
	 * @param in
     */
	@Override
	public void updateTaoCanInfo(TaocanInfoInput in) {

		// 得到修改信息
		TaocanDetailedOutput taocanGetTcOutput=getTaocanInfo(in.getTcid(),in.getOldbizno());

		// 老的业务编号不能为空
		if(StringUtil.isEmpty(in.getOldbizno())){
			throw new EduException(MessageConstant.OLD_BIZNO_NOTNULL);
		}

		// 套餐编号不能为空
		if(StringUtil.isEmpty(String.valueOf(in.getTcid()))){
			throw new EduException(MessageConstant.TCCODE_NOTNULL);
		}
		// 如果此套餐不存在，抛出异常
		if(StringUtil.isEmpty(String.valueOf(taocanGetTcOutput.getTaocaninfo().getTcid()))){
			throw new EduException(MessageConstant.TAOCAN_IS_NOT_FIND);
		}

		if(StringUtil.equals(in.getTcflag(),Constant.DEFAULT_TC_FLAG)){
			if(!(in.getTcflag()).equals(taocanGetTcOutput.getTaocaninfo().getTcflag()) || !StringUtil.equals(in.getOldbizno(),in.getBizno()) ){
				//验证是否存在默认套餐,在该省份下已存在时抛出异常
				validTcflag(in);
			}
		}


		//如果套餐名称修改了，判断修改的套餐名称是否有重復
		if(!(in.getTaocanname()).equals(taocanGetTcOutput.getTaocaninfo().getTaocanname())){
			validTaocanName(in);
		}
		BizComboInfo bean = new BizComboInfo();
		bean.setName(in.getTaocanname());

		// 套餐价格
		Double taocanPrice = null;
		// 如果异常，套餐价格格式不正确
		try {
			taocanPrice	=Double.valueOf(in.getTaocanprice());
		}catch (Exception e){
			throw new EduException(MessageConstant.TAOCAN_PRICE_IS_ERROR);
		}

		bean.setPrice(BigDecimal.valueOf(taocanPrice));
		bean.setBizNo(in.getBizno());

		// 省份编号
		bean.setRpcode(getBizProviceByBizno(in.getBizno()).getRpcode());

		bean.setSpace(Long.valueOf(in.getStorespace()));
		bean.setDuration(in.getTcduration());
		bean.setRemark(in.getTaocanremark());
		bean.setType(in.getTaocantype());
		bean.setDefaultTaocan(in.getTcflag());
		BizComboInfoExample example = new BizComboInfoExample();
		// 通过bizno,tcid才能找到对应的套餐
		example.createCriteria().andTcidEqualTo(in.getTcid()).andBizNoEqualTo(in.getOldbizno());
		if (bizComboInfoMapper.updateByExampleSelective(bean, example) != 1)
			throw new EduException(MessageConstant.DATA_MODIFY_FAILED);
	}


	/**
	 * 取得套餐列表
	 * @param in
	 * @return
     */
	@Override
	public TaocanListOutput getTaocanList(TaocanQueryInput in) {
		BizComboInfoExample example = new BizComboInfoExample();
		Page page = new Page();

		// 查询参数插入查询bean
		if (in != null) {
			String rpcode = in.getRpcode();
			BizComboInfoExample.Criteria criteria = example.createCriteria();
			if (StringUtil.isNotEmpty(rpcode)) {
				String[] rpcodeArray = rpcode.split(",");
				criteria.andRpcodeIn(Arrays.asList(rpcodeArray));
			}

			if (StringUtil.isNotEmpty(in.getBizno())) {
				criteria.andBizNoEqualTo(in.getBizno());
			}

			if (StringUtil.isNotEmpty(in.getTaocanname())) {// 套餐名称模糊查询
				criteria.andNameLike("%" + in.getTaocanname() + "%");
			}

			if (StringUtil.isNotEmpty(in.getTaocanprice())) {

				// 套餐价格
				Double taocanPrice = null;
				// 如果异常，套餐价格格式不正确
				try {
					taocanPrice	=Double.valueOf(in.getTaocanprice());
				}catch (Exception e){
					throw new EduException(MessageConstant.TAOCAN_PRICE_IS_ERROR);
				}
				criteria.andPriceEqualTo(BigDecimal.valueOf(taocanPrice));
			}
			// 状态为启用
			criteria.andStatusEqualTo(Constant.TAOCAN_STATUS_1);
			//设置分页参数
			if (!StringUtil.isEmpty(in.getCurrentpage())){
				page.setCurrentpage(Integer.valueOf(in.getCurrentpage()));
			}
			if (!StringUtil.isEmpty(in.getPagesize())){
				page.setPagesize(Integer.parseInt(in.getPagesize()));
			}

		}


		example.setPage(page);

		// 按创建时间倒序
		example.setOrderByClause("CREATE_TIME DESC");

		// 获得查询对象List
		List<BizComboInfo> list = bizComboInfoMapper.selectByExample(example);
		List<TaocanBaseInfo> relist = new ArrayList<TaocanBaseInfo>();
		String rpname = "";
		// 获取数据字典MAP
		HashMap<String, String> dicMap = queryDictionaryService.getDicAsmap();

		for (BizComboInfo taocanInfo : list) {
			TaocanBaseInfo info = new TaocanBaseInfo();

			// 套餐ID
			info.setTcid(taocanInfo.getTcid());

			// 在map中取省份名称（key：rpcode value：rpname）
			rpname = dicMap.get("PROVINCE_" +taocanInfo.getRpcode());
			// 省份名称
			if(StringUtil.isEmpty(rpname))
				info.setRpname(taocanInfo.getRpcode());
			else info.setRpname(rpname);

			// 业务编号
			info.setBizno(taocanInfo.getBizNo());

			// 业务名称
			info.setBizname(basicConfigService.getBizInfoByCoed(taocanInfo.getBizNo()).getName());

			// 省份编号
			info.setRpcode(taocanInfo.getRpcode());
			// 套餐大小
			info.setStorespace(String.valueOf(taocanInfo.getSpace()));
			// 套餐时长
			info.setTcduration(taocanInfo.getDuration());
			// 套餐名称
			info.setTaocanname(taocanInfo.getName());
			// 套餐价格
			info.setTaocanprice(taocanInfo.getPrice());
			// 备注
			info.setTaocanremark(taocanInfo.getRemark());
			// 套餐功能（主叫，双向）
			info.setTaocantype(taocanInfo.getType());
			relist.add(info);
		}

		//组装输出对象
		TaocanListOutput output = new TaocanListOutput();
		output.setTaocanlist(relist);
		output.setPageinfo(page);
		return output;
	}


	/**
	 * 根据套餐编号，业务编号取得套餐信息
	 * @param tcid 套餐编号
	 * @param bizno 业务编号
	 * @return
     */
	@Override
	public TaocanDetailedOutput getTaocanInfo(Long tcid, String bizno) {
		TaocanDetailedOutput output = new TaocanDetailedOutput();
		BizComboInfoExample bizComboInfoExample = new BizComboInfoExample();
		BizComboInfoExample.Criteria criteria = bizComboInfoExample.createCriteria();
		criteria.andTcidEqualTo(tcid);
		criteria.andBizNoEqualTo(bizno);
		List<BizComboInfo> taocanList = bizComboInfoMapper.selectByExample(bizComboInfoExample);
		if(taocanList.size() == 1){
			BizComboInfo taocan = taocanList.get(0);
			if (taocan != null) {
				// 获取数据字典MAP
				HashMap<String, String> dicMap = queryDictionaryService.getDicAsmap();

				TaocanBaseInfo result = new TaocanBaseInfo();
				result.setTcid(taocan.getTcid());
				String rpname = dicMap.get(Constant.PROVINCES +  "_" +taocan.getRpcode());
				if(StringUtil.isEmpty(rpname))
					result.setRpname(taocan.getRpcode());
				else result.setRpname(rpname);
				result.setRpcode(taocan.getRpcode());
				result.setStorespace(String.valueOf(taocan.getSpace()));
				result.setTaocanname(taocan.getName());
				result.setTaocanprice(taocan.getPrice());
				result.setTaocantype(taocan.getType());
				result.setTcduration(taocan.getDuration());
				result.setTaocanremark(taocan.getRemark());
				result.setTcflag(taocan.getDefaultTaocan());
				result.setBizno(taocan.getBizNo());
				output.setTaocaninfo(result);
			}
		}else {
			throw new EduException(MessageConstant.DATA_ERROR);
		}

		return output;
	}

	/**
	 * 套餐删除
	 * @param in
     */
	@Override
	public void deleteTaocan(TaocanDetailedInput in) {
		if (getTaocanStatus(in.getTcid(),in.getBizno()) > 0) {
			throw new EduException(MessageConstant.TAOCAN_NOALLOW_DELETE);
		} else {
			BizComboInfoExample bizComboInfoExample = new BizComboInfoExample();
			bizComboInfoExample.createCriteria().andTcidEqualTo(in.getTcid()).andBizNoEqualTo(in.getBizno());
//			if (bizComboInfoMapper.deleteByExample(bizComboInfoExample) != 1)
//				throw new EduException(MessageConstant.DATA_DELETE_FAILED);
			BizComboInfo bizComboInfo = new BizComboInfo();
			bizComboInfo.setFinishTime(DateUtils.getCurrentDate());
			bizComboInfo.setStatus(Constant.TAOCAN_STATUS_2);
			// 如果修改失败，提示数据删除异常
			if (bizComboInfoMapper.updateByExampleSelective(bizComboInfo, bizComboInfoExample) != 1)
				throw new EduException(MessageConstant.DATA_DELETE_FAILED);
		}
	}


	/**
	 * 取得最大套餐编号
	 * @return
     */
	private Integer getMaxTcid(String bizno) {
		int result = 0;
		BizComboInfoExample example = new BizComboInfoExample();
		example.createCriteria().andBizNoEqualTo(bizno);
		example.setOrderByClause("TCID desc");
		List<BizComboInfo> list = bizComboInfoMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			result = Integer.valueOf(list.get(0).getTcid().toString());
		}
		return result;
	}

	/**
	 * 查询BIZ_USER_INFO表中TaocanID为tcid且bizno为bizno的TaocanStatus
	 * @param tcid
	 * @return
     */
	private Integer getTaocanStatus(Long tcid,String bizno) {
		BizUserInfoExample example = new BizUserInfoExample();
		example.createCriteria().andTcidEqualTo(tcid)
		        .andStatusEqualTo(Constant.TAOCAN_STATUS_1)
				.andBizNoEqualTo(bizno);
		List<BizUserInfo> list = bizUserInfoMapper.selectByExample(example);
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}


	/**
	 * 验证是否存在默认套餐
	 * @param in
	 */
	public void validTcflag(TaocanInfoInput in) {
		BizComboInfoExample example = new BizComboInfoExample();
		BizComboInfoExample.Criteria criteria = example.createCriteria();
		criteria.andBizNoEqualTo(in.getBizno());
		criteria.andDefaultTaocanEqualTo(Constant.DEFAULT_TC_FLAG);
		criteria.andStatusEqualTo(Constant.TAOCAN_STATUS_1);
		criteria.andBizNoEqualTo(in.getBizno());

		int countByExample = bizComboInfoMapper.countByExample(example);
		if (countByExample > 0){
			throw new EduException(MessageConstant.TAOCAN_FLAG_REPEATED);
		}
	}

	/**
	 * 通过业务编号，取得省份编号
	 * @param bizno
	 * @return
     */
	private BizProvice getBizProviceByBizno(String bizno){
		BizProviceExample bizProviceExample = new BizProviceExample();
		BizProviceExample.Criteria criteria = bizProviceExample.createCriteria();
		criteria.andBizNoEqualTo(bizno);
		List<BizProvice> bizProviceList = bizProviceMapper.selectByExample(bizProviceExample);
		// 如果数据不为1。提示数据异常
		if(bizProviceList.size() != 1){
			throw  new EduException(MessageConstant.DATA_ERROR);
		}
		return bizProviceList.get(0);

	}

	/**
	 * 返回套餐map
	 * @param
	 * @return
	 */
	@Override
	public HashMap<String,String> getTaocanAsmap(){
		HashMap<String,String> map = new HashMap<String,String>();

		List<BizComboInfo> taocanList = bizComboInfoMapper.selectByExample(new BizComboInfoExample());

		//将list中的数据转存到map里
		for(BizComboInfo tc:taocanList){
			map.put(tc.getTcid().toString(), tc.getName());
			map.put(tc.getTcid().toString()+"tcType",tc.getType());// 套餐类型
		}
		return map;
	}

	/**
	 * 根据业务编号取得套餐信息
	 * @param bizNo 业务编号
	 * @return
	 */
	public TaocanListOutput getTaocanByBizNo(String bizNo){

		// 根据业务编号取得套餐信息（条件：业务编号，套餐状态：启用）
		BizComboInfoExample bizComboInfoExample = new BizComboInfoExample();
		bizComboInfoExample.createCriteria().andBizNoEqualTo(bizNo).andStatusEqualTo(Constant.TAOCAN_STATUS_1);
		List<BizComboInfo> list = bizComboInfoMapper.selectByExample(bizComboInfoExample);

		// 设值
		List<TaocanBaseInfo> taocanInfoList = new ArrayList<TaocanBaseInfo>();
		TaocanListOutput taocanListOutput = new TaocanListOutput();
		for (BizComboInfo bizComboInfo: list) {
			TaocanBaseInfo taocanInfo = new TaocanBaseInfo();
			taocanInfo.setRpcode(bizComboInfo.getRpcode());
			taocanInfo.setTcid(bizComboInfo.getTcid());
			taocanInfo.setTaocanname(bizComboInfo.getName());
			taocanInfo.setTaocantype(bizComboInfo.getType());
			taocanInfoList.add(taocanInfo);
		}
		taocanListOutput.setTaocanlist(taocanInfoList);
		return taocanListOutput;
	}


}
