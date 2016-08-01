package com.ancun.boss.service.market;

import com.ancun.boss.pojo.market.CallbackInput;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 用户录音上传逻辑接口
 *
 * @Created on 2015年9月22日
 * @author xieyushi
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IMarketCheckImportService {

    /**
     * 用户录音上传
     *
     * @param headers       请求体头部
     * @param inputStream   文件流
     */
    void marketCheckVoiceImport(HttpHeaders headers, InputStream inputStream) throws IOException;

    /**
     * 添加录音信息，并且准备欲上传文件列表
     *
     * @param headers       请求体头部
     * @param fileBytes     文件字节列表
     * @return              欲上传文件列表
     */
    List<File> marketCheckVoiceAdd(HttpHeaders headers, byte[] fileBytes);

    /**
     * 将文件列表{@code files}中的文件加密并返回{@code List<File>}
     *
     * @param files 文件列表
     * @return      加密后文件列表
     */
    List<File> marketCheckVoiceEncrypt(List<File> files);

    /**
     * 把文件列表{@code files}中的文件上传到上传组件
     *
     * @param headers   请求体头部
     * @param files     文件列表
     */
    void marketCheckVoiceUpload( HttpHeaders headers, List<File> files);

    /**
     * 营销质检录音导入回调操作
     *
     * @param input 回调用参数
     * @return      回调结果
     */
    String marketCheckVoiceImportCallback(CallbackInput input);

    /**
     * 从请求体头部取得文件名
     *
     * @param headers   请求体头部
     * @return          文件名
     */
    String getFileName(HttpHeaders headers);

    /**
     * 加密并发送文件
     *
     * @param headers   头部信息
     * @param files     文件列表
     */
    void encryptAndSendFiles(HttpHeaders headers, List<File> files);

}
