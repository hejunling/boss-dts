package com.ancun.boss.controller.system;

import com.ancun.boss.persistence.model.SystemBasicConfig;
import com.ancun.boss.pojo.system.BasicConfigInput;
import com.ancun.boss.pojo.system.BasicConfigOutput;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.core.annotation.AccessToken;
import com.ancun.core.annotation.AccessType;
import com.ancun.core.controller.BaseController;
import com.ancun.core.domain.request.ReqBody;
import com.ancun.core.domain.response.RespBody;
import com.ancun.core.exception.EduException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 基础数据
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/29
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RestController
public class BasicConfigController extends BaseController {
    @Resource
    private IBasicConfigService basicConfigService;

    /**
     * 通过模块编码获取数据列表
     *
     * @param basicConfigInputReqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/queryListByMoudleCode", method = RequestMethod.POST)
    public RespBody<BasicConfigOutput> queryListByMoudleCode(
            ReqBody<BasicConfigInput> basicConfigInputReqBody)
            throws EduException {
        BasicConfigInput basicConfigInput = basicConfigInputReqBody
                .getContent();
        List<SystemBasicConfig> list = basicConfigService
                .queryListByMoudleCode(basicConfigInput);
        return new RespBody<BasicConfigOutput>(new BasicConfigOutput(list));
    }


    /**
     * 基础数据列表
     *
     * @param reqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/queryBasicConfigList", method = RequestMethod.POST)
    public RespBody<BasicConfigOutput> queryBasicConfigList(ReqBody<BasicConfigInput> reqBody) throws EduException {
        BasicConfigInput basicConfigInput = reqBody.getContent();
        BasicConfigOutput output = basicConfigService.queryBasicConfigList(basicConfigInput);
        return new RespBody<BasicConfigOutput>(output);
    }


    /**
     * 基础数据模块类型列表
     *
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/queryBasicConfigTypes", method = RequestMethod.POST)
    public RespBody<BasicConfigOutput> queryBasicConfigTypes() throws EduException {
        List<SystemBasicConfig> list = basicConfigService.selectSerialSystemBasicConfigMoudle();
        return new RespBody<BasicConfigOutput>(new BasicConfigOutput(list));
    }

    /**
     * 基础数据详情
     *
     * @param reqBody
     * @return
     * @throws EduException
     */
    @AccessToken(access = AccessType.WEB)
    @RequestMapping(value = "/queryBasicConfigInfo", method = RequestMethod.POST)
    public RespBody<SystemBasicConfig> queryBasicConfigInfo(ReqBody<BasicConfigInput> reqBody) throws EduException {
        SystemBasicConfig systemBasicConfig = basicConfigService.queryConfigByBasicNo((reqBody.getContent().getBasicno()));
        return new RespBody<SystemBasicConfig>(systemBasicConfig);
    }

    /**
     * 更新名称
     */
    @AccessToken(access = AccessType.WEB)//, checkAccess = false
    @RequestMapping(value = "/updateSystemBasicConfigName", method = RequestMethod.POST)
    public RespBody<String> updateSystemBasicConfigName(ReqBody<BasicConfigInput> basicConfigInputReqBody) throws EduException {

        basicConfigService .updateSystemBasicConfigName(basicConfigInputReqBody.getContent());
        return new RespBody<String>();
    }

    /**
     * 根据模块code列表取得模块列表中所有模块信息
     *
     * @param reqBody 请求体参数
     * @return 需要的模块列表信息
     */
    @AccessToken(access = AccessType.WEB, checkAccess = true)
    @RequestMapping(value = "/queryByMoudlecodes", method = RequestMethod.POST)
    public RespBody<Map<String, List<SystemBasicConfig>>> queryByMoudlecodes(ReqBody<BasicConfigInput> reqBody) {

        List<String> moudlecodes = reqBody.getContent().getMoudlecodes();
        String userno = reqBody.getContent().getUserno();

        Map<String, List<SystemBasicConfig>> listMultimapByMoudlecodes = basicConfigService.queryByMoudlecodes(moudlecodes, userno);

        return new RespBody<Map<String, List<SystemBasicConfig>>>(listMultimapByMoudlecodes);
    }

}
