package com.ancun.boss.business.controller.dataDic;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.business.pojo.taocanInfo.TaocanDetailedOutput;
import com.ancun.boss.business.pojo.taocanInfo.TaocanInfoInput;
import com.ancun.boss.business.pojo.taocanInfo.TaocanInput;
import com.ancun.boss.business.pojo.taocanInfo.TaocanDetailedInput;
import com.ancun.boss.business.pojo.taocanInfo.TaocanListOutput;
import com.ancun.boss.business.pojo.taocanInfo.TaocanQueryInput;
import com.ancun.boss.business.service.dataDic.IUserTaocanService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;

/**
 * 套餐字典相关controller
 * @Created on 2016年4月7日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class TaocanController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(TaocanController.class);
	
	@Resource
	private IUserTaocanService taocanService;

	/**
	 * 套餐查询
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/mgrGetTcList", method = RequestMethod.POST)
	public RespBody<TaocanListOutput> mgrGetTcList(
			ReqBody<TaocanQueryInput> input) {
		TaocanQueryInput in = input.getContent();
		TaocanListOutput out = null;
		try{
			out = taocanService.getTaocanList(in);
		}catch (EduException e){
			log.error("code={},msg ={}",e.getCode(),e.getMessage(),e);
			out = new TaocanListOutput();
		}
		return new RespBody<TaocanListOutput>(out);
	}

	/**
	 * 套餐信息获取
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/mgrGetTcInfo", method = RequestMethod.POST)
	public RespBody<TaocanDetailedOutput> mgrGetTcInfo(ReqBody<TaocanDetailedInput> input) {
		TaocanDetailedInput bean = input.getContent();
		TaocanDetailedOutput result = taocanService.getTaocanInfo(bean.getTcid(),bean.getBizno());
		return new RespBody<TaocanDetailedOutput>(result);
	}

	/**
	 * 套餐添加
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/mgrAddTcInfo", method = RequestMethod.POST)
	public RespBody<String> mgrAddTcInfo(ReqBody<TaocanInfoInput> input) {
		TaocanInfoInput in = input.getContent();
		taocanService.addTaocanInfo(in);
		return new RespBody<String>(null);
	}

	/**
	 * 套餐信息修改
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/mgrUpdateTcInfo", method = RequestMethod.POST)
	public RespBody<String> mgrSaveTcInfo(ReqBody<TaocanInfoInput> input) {
		TaocanInfoInput in = input.getContent();
		taocanService.updateTaoCanInfo(in);
		return new RespBody<String>(null);
	}

	/**
	 * 套餐删除
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/mgrDeleteTc", method = RequestMethod.POST)
	@Transactional
	public RespBody<String> mgrDeleteTc(ReqBody<TaocanDetailedInput> input) {
		TaocanDetailedInput in = input.getContent();
		taocanService.deleteTaocan(in);
		return new RespBody<String>(null);
	}

	/**
	 * 根据业务编号取得套餐信息
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB)
	@RequestMapping(value = "/getTaocanByBizNo", method = RequestMethod.POST)
	public RespBody<TaocanListOutput> getTaocanByBizNo(ReqBody<TaocanInput> input) {
		TaocanInput in = input.getContent();
		TaocanListOutput output = taocanService.getTaocanByBizNo(in.getBizno());
		return new RespBody<TaocanListOutput>(output);
	}

}
