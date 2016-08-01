package com.ancun.boss.controller.cancelRequireCharts;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsInput;
import com.ancun.boss.pojo.cancelRequireCharts.CancelRequireChartsListOutput;
import com.ancun.boss.service.cancelRequireCharts.ICancelRequireChartsService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;

/**
 * 退费退订统计管理
 *
 * @Created on 2015年11月10日
 * @author jarvan
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class CancelRequireChartsController {
	@Resource
	private ICancelRequireChartsService cancelRequireChartsService;
	
	/**
	 * 退费退订统计
	 * 
	 */
	@AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/cancelRequireChartsList", method = RequestMethod.POST)
    public RespBody<CancelRequireChartsListOutput> queryCancelRequireChartsList(ReqBody<CancelRequireChartsInput> input) throws
            EduException {

		CancelRequireChartsListOutput output = cancelRequireChartsService.queryCancelRequireChartsList(input.getContent());
        ThreadLocalUtil.setContent("我查询了退费退订统计");

        return new RespBody<CancelRequireChartsListOutput>(output);
    }
	

}
