package com.ancun.boss.controller.phoneResp;

import com.ancun.boss.pojo.phoneResp.*;
import com.ancun.boss.service.phoneResp.IPhoneRepService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.google.common.base.Joiner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 号码池清洗Controller
 *
 * @Created on 2015年11月05日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class PhoneRepController extends BaseController {

    /** 号码池清洗Service */
    @Resource
    private IPhoneRepService phoneRepService;

    /** 异步线程池 */
    @Resource
    private ThreadPoolTaskExecutor threadPool;

    /**
     * 获取时间下拉框选择项
     *
     * @param reqBody   参数列表
     * @return          结果集
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/getTimeOptions", method = RequestMethod.POST)
    public RespBody<GetTimeOptionsOutput> getTimeOptions(ReqBody<GetTimeOptionsInput> reqBody){

        String content = Joiner.on("").skipNulls().join(
                "查询了业务[",
                reqBody.getContent().getBusiness(),
                "]获取时间信息!"
        );

        // 设置日志内容
        ThreadLocalUtil.setContent(content);

        return new RespBody<GetTimeOptionsOutput>(phoneRepService.getTimeOptions(reqBody.getContent()));
    }

    /**
     * 号码库列表
     *
     * @param reqBody   参数列表
     * @return          结果集
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/phoneResps", method = RequestMethod.POST)
    public RespBody<PhoneRespsOutPut> phoneResps(ReqBody<PhoneRespInput> reqBody){

        // 取得号码库信息
        PhoneRespsOutPut outPut = phoneRepService.phoneResps(reqBody.getContent());

        // 设置日志内容
        ThreadLocalUtil.setContent("查询了号码库信息");

        return new RespBody<PhoneRespsOutPut>(outPut);
    }

    /**
     * 号码池清理
     *
     * @param reqBody 参数列表
     * @return  结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/phonePoolFilter", method = RequestMethod.POST)
    public RespBody<String> phonePoolFilter(final ReqBody<PhoneRepInput> reqBody){

        // 号码清洗
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                phoneRepService.phonePoolFilter(reqBody.getContent());
            }
        });

        String content = Joiner.on("").skipNulls().join(
                "为",
                reqBody.getContent().getBusiness(),
                "业务导入了",
                reqBody.getContent().getGettime(),
                "取得的号码信息：",
                reqBody.getContent().getPhones().toString()
        );
        ThreadLocalUtil.setContent(content);

        return new RespBody<String>();
    }

    /**
     * 显示号码分配界面用数据
     *
     * @param reqBody   参数列表
     * @return          结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/phoneDividPage", method = RequestMethod.POST)
    public RespBody<PhoneDividPageOutput> phoneDividPage(ReqBody<PhoneDividPageInput> reqBody){

        PhoneDividPageOutput output = phoneRepService.phoneDividPage(reqBody.getContent());

        // 设置日志内容
        ThreadLocalUtil.setContent("查询了号码库批次信息");

        return new RespBody<PhoneDividPageOutput>(output);
    }

    /**
     * 根据批次取得号码数量
     *
     * @param reqBody   参数列表
     * @return          结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/countPhones", method = RequestMethod.POST)
    public RespBody<Integer> countPhones(ReqBody<CountPhoneInput> reqBody){

        String content = Joiner.on("").skipNulls().join(
                "查询了号码库中业务[",
                reqBody.getContent().getBusiness(),
                "]获取时间[",
                reqBody.getContent().getGetTime(),
                "]的号码数量！"
        );
        // 设置日志内容
        ThreadLocalUtil.setContent(content);

        return new RespBody<Integer>(phoneRepService.countPhones(reqBody.getContent()));
    }

    /**
     * 号码池清理
     *
     * @param reqBody 参数列表
     * @return  结果
     */
    @AccessToken(access = AccessType.WEB,checkAccess = true)
    @RequestMapping(value = "/phoneDivid", method = RequestMethod.POST)
    public RespBody<String> phoneDivid(final ReqBody<PhoneDividInput> reqBody){

        // 分配号码
        phoneRepService.phoneDivid(reqBody.getContent());

        String content = Joiner.on("").skipNulls().join(
                "将号码库中业务[",
                reqBody.getContent().getBusiness(),
                "]获取时间[",
                reqBody.getContent().getGetTime(),
                "]的号码分配给了团队",
                reqBody.getContent().getTeams().toString()
        );

        return new RespBody<String>();
    }
}
