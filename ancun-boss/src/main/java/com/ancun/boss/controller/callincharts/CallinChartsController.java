package com.ancun.boss.controller.callincharts;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ancun.boss.pojo.callincharts.CallinChartsInput;
import com.ancun.boss.pojo.callincharts.CallinChartsListOutput;
import com.ancun.boss.service.callincharts.ICallinChartsService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;

/**
 * 呼入电话报表统计
 * @author cys1993
 *
 */
@RestController
public class CallinChartsController extends BaseController{
	@Resource
	private ICallinChartsService callinChartsService;
	
	@AccessToken(access = AccessType.WEB, checkAccess = true)
	@RequestMapping(value = "/callinChartsList", method = RequestMethod.POST)
	/**
	 * 呼入电话统计报表
	 * @param input
	 * @return
	 * @throws EduException
	 */
	public RespBody<CallinChartsListOutput> queryCallinChartsList(ReqBody<CallinChartsInput> input) throws EduException{
		CallinChartsListOutput output = callinChartsService.queryCallinChartsList(input.getContent());
		ThreadLocalUtil.setContent("我查询了呼入来电统计");
		return new RespBody<CallinChartsListOutput>(output);
	}

}
