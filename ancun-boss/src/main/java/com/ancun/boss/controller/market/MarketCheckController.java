package com.ancun.boss.controller.market;

import com.ancun.boss.pojo.market.CallbackInput;
import com.ancun.boss.pojo.marketInfo.MarketCheckInput;
import com.ancun.boss.pojo.marketInfo.MarketCheckListInput;
import com.ancun.boss.pojo.marketInfo.MarketCheckOutput;
import com.ancun.boss.pojo.marketInfo.MarketCheckQueryInput;
import com.ancun.boss.service.market.IMarketCheckImportService;
import com.ancun.boss.service.market.MarketCheckService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.constant.ResponseConst;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 *
 *
 * @Created on 2015年9月21日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class MarketCheckController extends BaseController {

    @Resource
    private MarketCheckService service;

    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(MarketCheckController.class);

    /** 营销质检录音导入日志信息 */
    private static final String UPLOAD_CALLBACK_LOG_TEMPLATE = "营销质检录音文件包[{0}]已上传到保全中心";

    /** 营销质检录音导入service */
    @Resource
    private IMarketCheckImportService marketCheckService;

    /** 异步线程池 */
    @Resource
    private ThreadPoolTaskExecutor threadPool;

	/**
	 * 营销质检-查询
	 *
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/marketCheckListQuery", method = RequestMethod.POST)
	public RespBody<MarketCheckOutput> marketCheckListQuery(ReqBody<MarketCheckQueryInput> input) {

		MarketCheckOutput output = service.getMarketCheckListinfo(input.getContent());
		ThreadLocalUtil.setContent("我查询了营销质检内容");

		return new RespBody<MarketCheckOutput>(output);
	}

	/**
	 * 营销质检-新建/更新
	 *
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/marketCheckInfoSave", method = RequestMethod.POST)
	public RespBody<String> marketCheckInfoSave(ReqBody<MarketCheckInput> input) {


		service.addOrUpdateMarketCheckInfo(input.getContent());
		if (input.getContent().getModifyflag().equals("1")) {
			ThreadLocalUtil.setContent("新建了一条营销质检记录：" + input.getContent().getPhoneNo());
		} else if (input.getContent().getModifyflag().equals("2")) {
			ThreadLocalUtil.setContent("更新了一条营销质检记录：" + input.getContent().getPhoneNo());
		}


		return new RespBody<String>();
	}

	/**
	 * 营销质检-详细
	 *
	 * @param input
	 * @return
	 */
	@AccessToken(access = AccessType.WEB,checkAccess = true)
	@RequestMapping(value = "/marketCheckInfoQuery", method = RequestMethod.POST)
	public RespBody<MarketCheckOutput> marketCheckInfoQuery(ReqBody<MarketCheckQueryInput> input) {

		MarketCheckOutput output = new MarketCheckOutput();

		output = service.getMarketCheckInfo(input.getContent());
		ThreadLocalUtil.setContent("查询了一条营销质检详细：" + input.getContent());

		return new RespBody<MarketCheckOutput>(output);
	}
    /**
     * 营销质检录音导入
     *
     * @return  录音导入结果
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/marketCheckVoiceImport", method = RequestMethod.POST)
    public RespBody<String> marketCheckVoiceImport(@RequestHeader HttpHeaders headers, HttpServletRequest request) {

        long fileUpStartTime = System.nanoTime();
        logger.debug("营销质检录音导入开始：{}", System.nanoTime());
        logger.debug("当前线程ID：{}", Thread.currentThread().getId());

        try {
            // 取得请求体头部
            final HttpHeaders tempHeaders = HttpHeaders.readOnlyHttpHeaders(headers);
            // 取得文件字节列表
            final byte[] fileBytes = ByteStreams.toByteArray(request.getInputStream());

            //            // 营销质检录音导入
//            marketCheckService.marketCheckVoiceImport(headers, request.getInputStream());
            // 添加录音信息，并且准备欲上传文件列表
            final List<File> files = marketCheckService.marketCheckVoiceAdd(tempHeaders, fileBytes);

            // 异步执行加密发送文件
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    marketCheckService.encryptAndSendFiles(tempHeaders, files);
                }
            });

        } catch (IOException e) {
            throw new EduException(ResponseConst.SYSTEM_EXCEPTION, null, e);
        }


        // 营销质检录音导入日志信息
        String content = MessageFormat.format(UPLOAD_CALLBACK_LOG_TEMPLATE, marketCheckService.getFileName(headers));
        // 操作日志内容设置
        ThreadLocalUtil.setContent(content);

        logger.debug("营销质检录音导入结束,花费时间：{}", (System.nanoTime() - fileUpStartTime));

        return new RespBody<String>(null);
    }

    /**
     * 营销质检录音导入回调
     *
     * @return 营销质检录音导入回调结果
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/marketCheckVoiceImportCallback", method = RequestMethod.POST)
    public RespBody<String> marketCheckVoiceImportCallback(ReqBody<CallbackInput> input) {

        // 执行回调操作
        String content = marketCheckService.marketCheckVoiceImportCallback(input.getContent());

        // 操作日志内容设置
        ThreadLocalUtil.setContent(content);

        return new RespBody<String>(null);
    }

    /**
     * 营销质检数据导入
     *
     * @return 营销质检数据导入结果
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/marketCheckInfoImport", method = RequestMethod.POST)
    public RespBody<String> marketCheckInfoImport(ReqBody<MarketCheckListInput> input) {

    	String msg = "";

    	msg = service.importMarketCheckInfo(input.getContent());

    	return new RespBody<String>(msg);
    }

    /**
     * 营销质检录音试听
     *
     * @return 录音文件流
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/voiceDown", method = RequestMethod.POST)
    public void voiceDown(ReqBody<MarketCheckQueryInput> input, HttpServletResponse response) {
    	service.downloadVoice(response, input.getContent());
    }
}
