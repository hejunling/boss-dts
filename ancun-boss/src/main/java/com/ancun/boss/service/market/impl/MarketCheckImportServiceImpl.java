package com.ancun.boss.service.market.impl;

import com.ancun.boss.constant.ConfigConstant;
import com.ancun.boss.constant.FileUploadStatus;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.mapper.CalledVoiceMapper;
import com.ancun.boss.persistence.mapper.CalledVoiceZipMapper;
import com.ancun.boss.persistence.model.CalledVoice;
import com.ancun.boss.persistence.model.CalledVoiceExample;
import com.ancun.boss.persistence.model.CalledVoiceZip;
import com.ancun.boss.pojo.market.CallbackInput;
import com.ancun.boss.service.market.IMarketCheckImportService;
import com.ancun.common.biz.restclient.RestClient;
import com.ancun.common.biz.accesskey.FileKey;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.core.constant.Constants;
import com.ancun.core.constant.ResponseConst;
import com.ancun.core.exception.EduException;
import com.ancun.utils.*;
import com.google.common.base.*;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;


/**
 * 用户录音上传逻辑实现类
 *
 * @Created on 2015年9月22日
 * @author xieyushi
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class MarketCheckImportServiceImpl implements IMarketCheckImportService {

    private static final Logger logger = LoggerFactory.getLogger(MarketCheckImportServiceImpl.class);

    /** 业务名称 */
    private static final String BUSINESS        = "business";
    /** 外呼团队 */
    private static final String CALLED_TEAM     = "calledTeam";
    /** 营销时间 */
    private static final String MARKET_TIME     = "marketTime";
    /** 手机号码在文件名称中开始位置 */
    private static final String PHONE_START_POS = "phoneStartPos";
    /** 文件名称 */
    private static final String FILE_NAME       = "filename";
    /** 文件Md5 */
    private static final String FILE_MD5        = "reqcontentmd5";
    /** 登录用户名 */
    private static final String USERNO          = "userno";
    /** 保全中心AccessId */
    private static final String ACCESSID        = "accessid";
    /** 保全中心AccessKey */
    private static final String ACCESSKEY       = "accesskey";
    /** 完整文件名中文件夹间分隔符 */
    private static final String SEPARATOR       = File.separator;
    /** 反斜杠 */
    private static final String BACKSLASH       = "/";
    /** 手机号码长度 */
    private static final int PHONE_NUM_SIZE     = 11;
    /** 临时文件后缀 */
    private static final String FILE_EXTENSION  = ".data";
    /** 上传回调日志信息 */
    private static final String UPLOAD_CALLBACK_LOG_TEMPLATE    = "营销质检录音文件[{0}]上传{1}";
    /** 上传成功 */
    private static final String UPLOAD_SUCCESS  = "成功";
    /** 上传失败 */
    private static final String UPLOAD_FAILED   = "失败";

    /** 回调地址 */
    @Value("${CALLBACK_URI}")
    private String callbackUrl;
    /** 上传组件地址 */
    private final String uploadUri;

    /** 存储BUCKET */
    private final String bucketName;
    /** 临时目录 */
    private final String tempPath;
    /** 阿里云上accessId */
    private final String ossAccessId;
    /** 阿里云上accessKey */
    private final String ossAccessKey;

    /** 外呼录音记录ZIP信息操作mapper */
    @Resource
    private CalledVoiceZipMapper zipMapper;

    /** 外呼录音记录信息mapper */
    @Resource
    private CalledVoiceMapper voiceMapper;

    /** 手动事务 */
    @Resource
    private DataSourceTransactionManager txManager;

    /** rest请求客户端 */
    @Resource
    private RestClient restClient;

    /** 异步线程池 */
    @Resource
    private ThreadPoolTaskExecutor threadPool;

    /** 事务列表 */
    private List<TransactionStatus> transactionStatuses = Lists.newLinkedList();

    /**
     * 初期化系统变量
     */
    @Autowired
    public MarketCheckImportServiceImpl(BaseDataServiceImpl baseDataService) {

        // 阿里云buckt
        this.bucketName = baseDataService.getConfigMessage(null, ConfigConstant.ALIYUNOSS_BUCKET_CLIENT);

        // 临时文件目录
        String tempPath = baseDataService.getConfigMessage(null, ConfigConstant.TEMP_FILE_PATH);

        // 去除末尾的"//"
        if (tempPath.lastIndexOf(BACKSLASH) == tempPath.length() - 1) {
            this.tempPath = tempPath.substring(0, tempPath.length() - 1);
        } else {
            this.tempPath = tempPath;
        }

        // 阿里云accessid
        this.ossAccessId = baseDataService.getConfigMessage(null, ConfigConstant.ALIYUNOSS_ACCESSID);
        // 阿里云accesskey
        this.ossAccessKey = baseDataService.getConfigMessage(null, ConfigConstant.ALIYUNOSS_ACCESSKEY);
        // 上传组件url
        this.uploadUri = baseDataService.getConfigMessage(null, ConfigConstant.UPLOAD_URI);
    }

    /**
     * 用户录音上传
     * ① 添加录音信息，并且准备欲上传文件列表
     * ② 加密文件
     * ③ 将加密文件上传到上传组件
     *
     * @param headers       请求体头部
     * @param inputStream   文件流
     */
//    @Async
    @Override
    public void marketCheckVoiceImport(HttpHeaders headers, InputStream inputStream) throws IOException {

        long fileUpStartTime = System.currentTimeMillis();
        logger.debug("营销质检录音导入开始：{}ms", (System.currentTimeMillis() - fileUpStartTime));
        logger.debug("当前线程ID：{}", Thread.currentThread().getId());

        // 添加录音信息，并且准备欲上传文件列表
        List<File> files = marketCheckVoiceAdd(headers, ByteStreams.toByteArray(inputStream));
        // 加密发送文件
        encryptAndSendFiles(headers, files);
//        // 加密文件
//        List<File> encryptFiles = marketCheckVoiceEncrypt(files);
//        // 将加密文件上传到上传组件
//        marketCheckVoiceUpload(headers, encryptFiles);

        logger.debug("营销质检录音导入结束,花费时间：{}ms", (System.currentTimeMillis() - fileUpStartTime));

    }

    /**
     * 添加录音信息，并且准备欲上传文件列表{@code List<File>}
     * 1 准备工作
     *     ① 请求体头部信息{@code headers}取得相应信息
     *     ② 构建基础外呼录音记录ZIP信息
     *     ③ 构建基础外呼录音记录信息
     * 2 下载zip文件到临时目录
     *     ① 从流中取得文件保存到临时目录
     *     ② 校验文件是否完整
     * 3 添加外呼录音记录ZIP信息
     * 4 添加外呼录音记录信息
     *     ① 解压zip文件取得文件列表
     *     ② 根据取得的文件列表和1中的③准备的基础外呼录音记录信息添加外呼录音记录信息
     *
     * @param headers       请求体头部
     * @param fileBytes     文件字节列表
     * @return              欲上传文件列表{@code List<File>}
     */
    @Override
    public List<File> marketCheckVoiceAdd(HttpHeaders headers, byte[] fileBytes) {

        long fileUpStartTime = System.currentTimeMillis();
        logger.debug("营销质检录音导入取得录音开始：{}", System.currentTimeMillis());
        logger.debug("营销质检录音导入取得录音线程ID：{}", Thread.currentThread().getId());

        File tempZipFile = null;
        List<File> files = null;

        try {

            // 1 准备工作=======================================================
            // 取得手机号码在文件名称中开始位置
            int phoneStartPos = getPhoneStartPos(headers);
            // 取得文件MD5值
            String fileMd5 = getFileMd5(headers);
            // 构建基础外呼录音记录ZIP信息
            CalledVoiceZip baseCalledVoiceZip = prepareCalledVoiceZip(headers);
            // 构建基础外呼录音记录信息
            CalledVoice baseCalledVoice = prepareCalledVoice(headers);

            // 2 下载zip文件到临时目录=============================================
            tempZipFile = downloadZip(fileBytes, baseCalledVoiceZip.getVoiceFileName(), fileMd5);

            // 3 添加外呼录音记录ZIP信息===========================================
            addCalledVoiceZip(baseCalledVoiceZip);

            // 4 添加外呼录音记录信息==============================================
            // 解压zip文件取得文件列表
            files = unZip(tempZipFile);
            // 更新外呼录音记录信息，并过滤出需重新上传文件列表
            files = addCalledVoices(files, baseCalledVoice, phoneStartPos);

            // 将压缩文件加入到文件列表中
            files.add(0, tempZipFile);

            // 提交事务
            for (TransactionStatus status : transactionStatuses) {
                txManager.commit(status);
            }

            return files;

        } catch (Exception e) {

            // 回滚事务
            for (TransactionStatus status : transactionStatuses) {
                txManager.rollback(status);
            }

            // 删除文件
            for (File file : files) {
                deleteFile(file);
            }

            // 删除压缩文件
            deleteFile(tempZipFile);

            throw Throwables.propagate(e);
        } finally {
            // 清空事务
            transactionStatuses.clear();
            logger.debug("营销质检录音导入取得录音结束,花费时间：{}ms", (System.currentTimeMillis() - fileUpStartTime));
        }
    }

    /**
     * 将文件列表{@code files}中的文件加密并返回{@code List<File>}
     *
     * @param files 文件列表
     * @return      加密后文件列表
     */
    @Override
    public List<File> marketCheckVoiceEncrypt(List<File> files) {

        long fileEncryptTime = System.currentTimeMillis();
        logger.debug("文件[{}]加密开始：{}", files, fileEncryptTime);

        List<File> encryptFiles = Lists.transform(files, new Function<File, File>() {
            @Override
            public File apply(File input) {
                return encryptFile(input);
            }
        });

        logger.debug("文件[{}]加密结束, 花费时间: {}ms", files, (System.currentTimeMillis() - fileEncryptTime));

        return encryptFiles;

    }

    /**
     * 把文件列表{@code files}中的文件上传到上传组件
     *
     * @param files 文件列表
     */
    @Override
    public void marketCheckVoiceUpload( final HttpHeaders headers, List<File> files) {

        long doFileUploadTime = System.currentTimeMillis();
        logger.debug("上传文件[{}]开始 : {}", files, doFileUploadTime);

        // 循环上传文件
        for (final File file : files) {

            // 异步上传文件
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    // 如果上传失败
                    if (!uploadToUp2yun(headers, file)){
                        // 更改表状态和删除文件
                        marketCheckVoiceImportCallback(createUploadFailed(headers, file));
                    }
                }
            });
        }

        logger.debug("上传文件[{}]结束, 花费时间 : {}ms", files, (System.currentTimeMillis() - doFileUploadTime));

    }

    /**
     * 营销质检录音导入回调操作
     *
     * @param input 回调用参数
     * @return      回调结果
     */
    @Override
    public String marketCheckVoiceImportCallback(CallbackInput input) {

        // 上传结果
        String uploadResult = UPLOAD_FAILED;

        // 文件名
        String fileName = input.getFileName();

        // 查询条件构建
        CalledVoiceExample example = new CalledVoiceExample();
        example.createCriteria()
                // 被营销电话
                .andMakretPhoneEqualTo(Strings.nullToEmpty(input.getMarketPhone()))
                // 营销时间
                .andMarketTimeEqualTo(Strings.nullToEmpty(input.getMarketTime()))
                // 外呼团队
                .andCalledTeamEqualTo(Strings.nullToEmpty(input.getCalledTeam()))
                // 业务名称
                .andBusinessEqualTo(Strings.nullToEmpty(input.getBusiness()));

        // 取得记录集
        List<CalledVoice> voices = voiceMapper.selectByExample(example);

        // 记录多于一条
        if (voices.size() > 1) {
            throw new EduException(ResponseConst.RECORD_ABNORMAL);
        }

        // 查询到数据
        if (voices.size() == 1) {
            // 录音文件上传状态
            FileUploadStatus uploadStatus = FileUploadStatus.FAILED;

            // 录音文件上传成功
            if (ResponseConst.SUCCESS == input.getCallbackCode()) {
                uploadResult = UPLOAD_SUCCESS;
                uploadStatus = FileUploadStatus.SUCCESS;
            }

            // 构建录音信息用于更新
            CalledVoice voiceForUpdate = copy(voices.get(0));

            // 将被营销电话置为空，因为营销电话是分库字段
            voiceForUpdate.setMakretPhone(null);

            // 设置录音文件上传状态
            voiceForUpdate.setStatus(uploadStatus.name());

            // 更新录音文件状态
            voiceMapper.updateByExampleSelective(voiceForUpdate, example);

            // 设置文件名
            fileName = voiceForUpdate.getVoiceFileName();
        }

        // 取得文件名成功
        if (!Strings.isNullOrEmpty(fileName)) {
            // 删除未加密文件
            String noEncryptFileName = createFilePath(fileName);
            deleteFile(noEncryptFileName);

            // 删除加密文件
            String encryptFileName = createEncryptFilePath(fileName);
            deleteFile(encryptFileName);
        }

        return MessageFormat.format(UPLOAD_CALLBACK_LOG_TEMPLATE, fileName, uploadResult);
    }

    /**
     * 从请求体头部取得文件名
     *
     * @param headers   请求体头部
     * @return          文件名
     */
    @Override
    public String getFileName(HttpHeaders headers) {

        try {
            return URLDecoder.decode(headers.getFirst(FILE_NAME), Constants.CHARSETNAME_DEFAULT);
        } catch (UnsupportedEncodingException e) {
            throw new EduException(ResponseConst.SYSTEM_EXCEPTION, null, e);
        }

    }

    /**
     * 加密并发送文件
     *
     * @param headers   头部信息
     * @param files     文件列表
     */
    @Override
    @Async
    public void encryptAndSendFiles(HttpHeaders headers, List<File> files){

        long startTime = System.currentTimeMillis();
        logger.debug("营销质检录音导入加密并发送文件开始：{}", System.currentTimeMillis());
        logger.debug("营销质检录音导入加密并发送文件线程ID：{}", Thread.currentThread().getId());

        // 加密文件
        List<File> encryptFiles = marketCheckVoiceEncrypt(files);
        // 将加密文件上传到上传组件
        marketCheckVoiceUpload(headers, encryptFiles);

        logger.debug("营销质检录音导入加密并发送文件结束,花费时间：{}ms", (System.currentTimeMillis() - startTime));

    }

    /**
     * 开始一个事务
     */
    private void addTransactionStatus(){

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionStatus status = txManager.getTransaction(def);

        transactionStatuses.add(0, status);

    }

    /**
     * 从请求体头部信息{@code headers}构建外呼录音记录信息{@link CalledVoiceZip}
     *
     * @param headers   请求体头部信息{@code headers}
     * @return          外呼录音记录ZIP信息{@link CalledVoiceZip}
     */
    private CalledVoiceZip prepareCalledVoiceZip(HttpHeaders headers){

        CalledVoiceZip calledVoiceZip = new CalledVoiceZip();

        // 业务名称
        String business = headers.getFirst(BUSINESS);
        if (!Strings.isNullOrEmpty(business)) {
            calledVoiceZip.setBusiness(business);
        } else {
            throw new EduException(MessageConstant.MARKETCHECK_BUSINESS_MUST_BE_NOT_EMPTY);
        }

        // 外呼团队
        String calledTeam = headers.getFirst(CALLED_TEAM);
        if (!Strings.isNullOrEmpty(calledTeam)) {
            calledVoiceZip.setCalledTeam(calledTeam);
        } else {
            throw new EduException(MessageConstant.MARKETCHECK_CALLEDTEAM_MUST_BE_NOT_EMPTY);
        }

        // 营销时间
        calledVoiceZip.setMarketTime(getMarketTime(headers));

        // 文件名称
        String fileName = getFileName(headers);
        if (!Strings.isNullOrEmpty(fileName)) {
            calledVoiceZip.setVoiceFileName(fileName);
        } else {
            throw new EduException(MessageConstant.MARKETCHECK_FILENAME_MUST_BE__NOT_EMPTY);
        }

        // 存储路径
        String storePath = createStorePath(getStorePathPre(headers), getEncryptFileName(fileName));
        calledVoiceZip.setStorePath(storePath);

        // 存储BUCKET
        calledVoiceZip.setBucketName(bucketName);

        return calledVoiceZip;
    }

    /**
     * 从请求体头部信息{@code headers}构建外呼录音记录信息{@link CalledVoice}
     *
     * @param headers   请求体头部信息{@code headers}
     * @return          外呼录音记录信息{@link CalledVoice}
     */
    private CalledVoice prepareCalledVoice(HttpHeaders headers){

        CalledVoice calledVoice = new CalledVoice();

        // 业务名称
        String business = headers.getFirst(BUSINESS);
        if (!Strings.isNullOrEmpty(business)) {
            calledVoice.setBusiness(business);
        } else {
            throw new EduException(MessageConstant.MARKETCHECK_BUSINESS_MUST_BE_NOT_EMPTY);
        }

        // 外呼团队
        String calledTeam = headers.getFirst(CALLED_TEAM);
        if (!Strings.isNullOrEmpty(calledTeam)) {
            calledVoice.setCalledTeam(calledTeam);
        } else {
            throw new EduException(MessageConstant.MARKETCHECK_CALLEDTEAM_MUST_BE_NOT_EMPTY);
        }

        // 营销时间
        calledVoice.setMarketTime(getMarketTime(headers));

        // 存储路径
        calledVoice.setStorePath(getStorePathPre(headers));

        // 存储BUCKET
        calledVoice.setBucketName(bucketName);

        // 上传状态
        calledVoice.setStatus(FileUploadStatus.UPLOADING.name());

        return calledVoice;
    }

    /**
     * 从一个外呼录音记录信息{@code src}复制一份
     *
     * @param src   来源外呼录音记录信息
     * @return      复制外呼录音记录信息
     */
    private CalledVoice copy(CalledVoice src) {

        CalledVoice dst = new CalledVoice();

        dst.setId(src.getId());
        dst.setBucketName(src.getBucketName());
        dst.setMarketTime(src.getMarketTime());
        dst.setCalledTeam(src.getCalledTeam());
        dst.setStorePath(src.getStorePath());
        dst.setBusiness(src.getBusiness());
        dst.setVoiceFileName(src.getVoiceFileName());
        dst.setMakretPhone(src.getMakretPhone());
        dst.setMarketNo(src.getMarketNo());
        dst.setStatus(src.getStatus());

        return dst;
    }

    /**
     * 从请求体头部信息{@code headers}取得手机号码开始位置
     *
     * @param headers   请求体头部信息{@code headers}
     * @return          手机号码开始位置
     */
    private int getPhoneStartPos(HttpHeaders headers) {

        // 手机号码在文件名称中开始位置
        String phoneStartPos = headers.getFirst(PHONE_START_POS);
        // 判断是否为空
        if (Strings.isNullOrEmpty(phoneStartPos)) {
            throw new EduException(MessageConstant.MARKETCHECK_PHONESTARTPOS_MUST_BE_NOT_EMPTY);
        }
        // 判断是否是数字
        else if (!CharMatcher.JAVA_DIGIT.matchesAllOf(phoneStartPos)) {
            throw new EduException(MessageConstant.MARKETCHECK_PHONESTARTPOS_MUST_BE_DIGIT);
        }

        return Integer.valueOf(phoneStartPos);
    }

    /**
     * 从请求体头部信息{@code headers}取得营销时间
     *
     * @param headers   请求体头部信息{@code headers}
     * @return          存储路径前缀
     */
    private String getMarketTime(HttpHeaders headers) {

        // 营销时间
        String marketTime = headers.getFirst(MARKET_TIME);
        if (Strings.isNullOrEmpty(marketTime)) {
            throw new EduException(MessageConstant.MARKETCHECK_MARKETTIME_MUST_BE_NOT_EMPTY);
        }

        // 精确到天
        return DateUtil.stringToString(marketTime).substring(0, 8);
    }

    /**
     * 从请求体头部信息{@code headers}取得存储路径前缀
     *
     * @param headers   请求体头部信息{@code headers}
     * @return          存储路径前缀
     */
    private String getStorePathPre(HttpHeaders headers) {

        // 外呼团队
        String calledTeam = headers.getFirst(CALLED_TEAM);
        if (Strings.isNullOrEmpty(calledTeam)) {
            throw new EduException(MessageConstant.MARKETCHECK_CALLEDTEAM_MUST_BE_NOT_EMPTY);
        }

        return Joiner.on(BACKSLASH).skipNulls().join(calledTeam, getMarketTime(headers));
    }

    /**
     * 创建存储路劲
     *
     * @param storePathPre  存储路径前缀
     * @param fileName      文件名
     * @return              存储路径
     */
    private String createStorePath(String storePathPre, String fileName) {
        return Joiner.on(BACKSLASH).skipNulls().join(storePathPre, fileName);
    }

    /**
     * 从请求体头部信息{@code headers}取得文件Md5
     *
     * @param headers   请求体头部信息{@code headers}
     * @return          文件Md5
     */
    private String getFileMd5(HttpHeaders headers) {

        // 文件名称
        String fileMd5 = headers.getFirst(FILE_MD5);

        if (Strings.isNullOrEmpty(fileMd5)) {
            throw new EduException(MessageConstant.MARKETCHECK_FILEMD5_MUST_BE__NOT_EMPTY);
        }

        return fileMd5;
    }

    /**
     * 将输入流{@code inputStream}转变为文件{@code File}
     *
     * @param fileBytes     文件字节列表
     * @param fileName      文件名称
     * @param fileMd5       文件MD5
     * @return              取得文件{@code File}
     */
    private File downloadZip(byte[] fileBytes, String fileName, String fileMd5){

        // 临时文件构建完整路径名
        String fullFileName = createFilePathWithoutExtension(fileName);

        // 本地临时文件
        File tempZipFile = new File(fullFileName);

        // 将流下载到临时文件
        try {
            Files.write(fileBytes, tempZipFile);
        } catch (IOException e) {
            throw new EduException(ResponseConst.SYSTEM_EXCEPTION, "", e);
        }

        // 校验文件完整性
        String md5 = MD5Utils.md5file(fullFileName);
        if (!Objects.equal(md5, fileMd5)) {
            throw new EduException(MessageConstant.MARKETCHECK_FILEMD5_NOT_CONSISTENT);
        }

        return tempZipFile;
    }

    /**
     * 添加外呼录音记录Zip
     *
     * @param calledVoiceZip 外呼录音记录Zip
     */
    private void addCalledVoiceZip(CalledVoiceZip calledVoiceZip) {

        // 开始添加外呼录音记录Zip事务
        addTransactionStatus();

        // 添加外呼录音记录
        zipMapper.insertSelective(calledVoiceZip);

    }

    /**
     * 将压缩文件{@code zipFile}解压并取得文件列表{@code List<File>}
     *
     * @param zipFile   压缩文件
     * @return          文件列表{@code List<File>}
     */
    private List<File> unZip(File zipFile) {

        List<File> files = Lists.newLinkedList();

        try {

            long upZipTime = System.currentTimeMillis();
            logger.debug("解压Zip文件[{}]开始：{}", zipFile.getName(), upZipTime);

            // 构建压缩文件
            ZipFile zipIn = new ZipFile(zipFile);
            Enumeration e = zipIn.getEntries();
            while (e.hasMoreElements()){
                ZipEntry zipEntry = (ZipEntry)e.nextElement();

                // 如果是文件夹则不处理
                if (zipEntry.isDirectory()) {
                    continue;
                }

                // zip包中文件
                InputStream from = zipIn.getInputStream(zipEntry);

                // 分割压缩包中文件的完整名字
                Iterable<String> paths = Splitter.on(BACKSLASH).omitEmptyStrings().trimResults().split(zipEntry.getName());
                // 取得文件名
                String zipEntryName = Iterables.getLast(paths);
                // 临时文件完整名
                String toPath = createFilePathWithoutExtension(zipEntryName);
                // 临时文件
                File to = new File(toPath);

                // 保存到临时文件目录
                Files.write(ByteStreams.toByteArray(from), to);

                // 添加到临时文件处
                files.add(to);

                // 关闭流
                from.close();
            }

            // 关闭压缩文件流
            zipIn.close();

            logger.debug("解压Zip文件[{}]结束, 花费时间: {}ms", zipFile.getName(), (System.currentTimeMillis() - upZipTime));

        } catch (Throwable e) {
            throw new EduException(ResponseConst.SYSTEM_EXCEPTION, "", e);
        }

        return files;
    }

    /**
     * 批量更新外呼录音记录信息, 并过滤出需上传文件列表
     *
     * @param files             录音文件列表
     * @param baseCalledVoice   基础外呼录音记录信息
     * @param phoneStartPos     手机号码在文件名字中开始位置
     */
    private List<File> addCalledVoices(List<File> files, final CalledVoice baseCalledVoice, final int phoneStartPos){

        // 过滤出需上传文件
        Iterable<File> fileUploads = Iterables.filter(files, new Predicate<File>() {
            @Override
            public boolean apply(File input) {
                return updateCalledVoice(input, baseCalledVoice, phoneStartPos);
            }
        });

        return Lists.newLinkedList(fileUploads);
    }

    /**
     * 更新外呼录音记录信息
     *
     * @param file              录音文件
     * @param baseCalledVoice   基础外呼录音记录信息
     * @param phoneStartPos     手机号码在文件名字中开始位置
     */
    private boolean updateCalledVoice(File file, CalledVoice baseCalledVoice, int phoneStartPos){

        // 开始外呼录音记录增加事务
        addTransactionStatus();

        // 取得文件名
        String fileName = file.getName();

        // 取得电话号码
        String phone = getPhone(fileName, phoneStartPos);

        // 设置取得外呼录音记录信息条件
        CalledVoiceExample example = new CalledVoiceExample();
        example.createCriteria().andMakretPhoneEqualTo(phone);

        // 取得外呼录音记录信息
        List<CalledVoice> voices = voiceMapper.selectByExample(example);

        // 记录数大于1条
        if (voices.size() > 1) {
            throw new EduException(ResponseConst.RECORD_ABNORMAL);
        }

        // 默认欲上传文件
        FileUploadStatus currentStatus = FileUploadStatus.UPLOAD;

        // 如果查询到外呼录音记录信息
        if (voices.size() == 1) {
            // 取得外呼录音记录信息
            CalledVoice findVoice = voices.get(0);

            // 取得录音状态
            currentStatus = FileUploadStatus.valueOf(findVoice.getStatus());
        }

        // 文件未上传过进行上传
        if (currentStatus == FileUploadStatus.UPLOAD) {
            return addCalledVoice(phone, fileName, baseCalledVoice);
        }
        // 文件上传失败过，重新上传
        else if (currentStatus == FileUploadStatus.FAILED) {
            return updateCalledVoice(baseCalledVoice.getStorePath(), fileName, voices.get(0));
        }

        // 删除不需要上传文件
        file.delete();

        // 上传中，或者上传失败则不再上传
        return false;

    }

    /**
     * 增加外呼录音记录信息
     *
     * @param phone             被营销电话
     * @param fileName          录音文件列信息
     * @param baseCalledVoice   基础外呼录音记录信息
     * @return                  true 需要上传文件
     */
    private boolean addCalledVoice(String phone, String fileName, CalledVoice baseCalledVoice){

        // 构建外呼录音记录信息
        CalledVoice calledVoiceForAdd = copy(baseCalledVoice);
        // 被营销电话
        calledVoiceForAdd.setMakretPhone(phone);
        // 录音文件名
        calledVoiceForAdd.setVoiceFileName(fileName);
        // 存储路径
        String storePath = createStorePath(baseCalledVoice.getStorePath(),
                getEncryptFileNameWithoutExtension(fileName));
        calledVoiceForAdd.setStorePath(storePath);

        // 添加到数据表中
        voiceMapper.insertSelective(calledVoiceForAdd);

        return true;
    }

    /**
     * 增加外呼录音记录信息
     *
     * @param storePathPre  存储路径前缀
     * @param fileName      录音文件列信息
     * @param findVoice     基础外呼录音记录信息
     * @return              true 需要上传文件
     */
    private boolean updateCalledVoice(String storePathPre, String fileName, CalledVoice findVoice){

        // 构建外呼录音记录信息
        CalledVoice calledVoice = copy(findVoice);
        // 存储路径修改
        String storePath = createStorePath(storePathPre, getEncryptFileNameWithoutExtension(fileName));
        calledVoice.setStorePath(storePath);
        // 上传状态修改
        calledVoice.setStatus(FileUploadStatus.UPLOADING.name());

        // 设置取得外呼录音记录信息条件
        CalledVoiceExample example = new CalledVoiceExample();
        example.createCriteria().andMakretPhoneEqualTo(calledVoice.getMakretPhone());

        // 更新数据库
        voiceMapper.updateByExampleSelective(calledVoice, example);

        return true;
    }

    /**
     * 从文件名的指定位置截取手机号码
     *
     * @param fileName      文件名
     * @param phoneStartPos 开始位置
     * @return              手机号码
     */
    private String getPhone(String fileName, int phoneStartPos) {

        logger.debug("从文件名[{}]中的第{}个位置开始取得手机号码", fileName, phoneStartPos);

        // 截取电话号码
        int length = phoneStartPos + PHONE_NUM_SIZE;
        if (length > fileName.length()) {
            length = fileName.length();
        }
        String phone = fileName.substring(phoneStartPos, length);

        return validatePhone(phone);
    }

    /**
     * 加密文件
     *
     * @param srcFile   需加密文件{@code srcFile}
     */
    private File encryptFile(File srcFile) {

        // 流关闭器
        Closer closer = Closer.create();

        // 加密后完整文件名
        String destFilePath = createEncryptFilePath(srcFile.getName());

        try {

            long fileEncryptTime = System.currentTimeMillis();
            logger.debug("文件[{}]加密开始：{}", srcFile.getName(), fileEncryptTime);

            // 为文件添加加密字节
            boolean flg = AESUtils.encryptFile(FileKey.FILE_KEY, srcFile.getAbsolutePath(), destFilePath);
            if (!flg) {
                throw new EduException(MessageConstant.FILE_AES_ENCRYPT_FAIL);
            }
            // 文件加密
            MessageDigest ciphertext =  MessageDigest.getInstance("MD5");

            // 取得添加加密字节后文件
            File destFile = new File(destFilePath);

            // 如果文件存在
            if (destFile.exists()) {
                InputStream inputStream = new FileInputStream(destFile);

                // 将流注册到流关闭器，以便方便关闭
                closer.register(inputStream);

                // 加密添加加密字节后的文件
                ciphertext.update(ByteStreams.toByteArray(inputStream));
            }

            logger.debug("文件[{}]解密结束, 花费时间: {}ms", srcFile.getName(), (System.currentTimeMillis() - fileEncryptTime));

            return destFile;
        } catch (Exception e) {
            // 删除已加密的文件
            deleteFile(destFilePath);
            throw new EduException(MessageConstant.FILE_AES_ENCRYPT_FAIL, null, e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                throw new EduException(MessageConstant.FILE_AES_ENCRYPT_FAIL, null, e);
            }
        }
    }

    /**
     * 将加密后文件{@code file}上传到上传组件
     *
     * @param headers   请求体头部信息
     * @param file      欲上传文件
     * @return true成功，false失败
     */
    private boolean uploadToUp2yun(HttpHeaders headers, File file) {

        long doFileUploadTime = System.currentTimeMillis();
        logger.debug("上传文件[{}]开始 : {}", file.getName(), doFileUploadTime);

        // 准备需要的参数
        Map<String, String> paramMap = Maps.newHashMap();

        // 添加上传组件参数
        paramMap.putAll(this.prepareUp2yunParams(headers, file));

        // 添加上传组件回调保全中心接口用参数
        paramMap.putAll(this.prepareCallbackParams(headers, file));

        try {
            // POST方式发送文件到上传组件
//            uploadUri = "http://127.0.0.1:8090";

            // 发送字节流到指定地址
            String result = restClient.postFile(uploadUri, file, null, paramMap);

            return !Strings.isNullOrEmpty(result);
        } catch (Exception e) {
            throw new EduException(MessageConstant.POST_TO_UP2YUN_ERROR, null, e);
        } finally {
            logger.debug("上传文件[{}]结束, 花费时间 : {}ms", file.getName(), (System.currentTimeMillis() - doFileUploadTime));
        }
    }

    /**
     * 准备上传到云的参数
     *
     * @param headers   请求头
     * @param file      欲上传文件
     * @return          上传到云的参数列表
     */
    private Map<String, String> prepareUp2yunParams(HttpHeaders headers, File file){

        // 准备需要的参数
        Map<String, String> paramMap = Maps.newHashMap();
        // 存储路径
        paramMap.put("oss_bucket", this.bucketName);
        // OSS上显示名称
        String fileKey = createStorePath(getStorePathPre(headers),
                getFileNameWithoutExtension(Files.getNameWithoutExtension(file.getAbsolutePath())));
        paramMap.put("fileKey", fileKey);
        // 文件md5值
        paramMap.put("fileMd5", MD5Utils.md5file(file.getAbsolutePath()));
        // 文件大小
        paramMap.put("fileSize", String.valueOf(file.length()));
        // 阿里云的accessId
        paramMap.put("oss_accessid", this.ossAccessId);
        // 阿里云的accessKey
        paramMap.put("oss_accesskey", this.ossAccessKey);

        return paramMap;
    }

    /**
     * 准备上传组件回调保全中心回调接口参数
     *
     * @param headers   请求头
     * @param file      欲上传文件
     * @return          上传组件回调保全中心回调接口参数
     */
    private Map<String, String> prepareCallbackParams(HttpHeaders headers, File file){

        // 准备需要的参数
        Map<String, String> paramMap = Maps.newHashMap();

        // 回调地址
        paramMap.put("callbackUri", DESUtils.encrypt(callbackUrl, null));

        // 保全中心回调接口所需参数=============================================================
        // 访问保全中心用accessId
        paramMap.put("accessid", Strings.nullToEmpty(headers.getFirst(ACCESSID)));
        // 访问保全中心用accessKey
        paramMap.put("accesskey", Strings.nullToEmpty(headers.getFirst(ACCESSKEY)));
        // 外呼团队
        paramMap.put("calledTeam", Strings.nullToEmpty(headers.getFirst(CALLED_TEAM)));
        // 取得手机号码在文件名称中开始位置
        int phoneStartPos = getPhoneStartPos(headers);
        // 被营销电话
        String phone = "";
        // 如果文件名为请求头的文件名，则为压缩包，不需要传入手机号码
        if (!checkIsZipFile(headers, file.getName())) {

            // 从文件名中取得手机号码
            phone = getPhone(file.getName(), Constants.ENCRYPTED_NAME.length() + phoneStartPos);

        }
        paramMap.put("marketPhone", phone);
        // 营销时间
        paramMap.put("marketTime", Strings.nullToEmpty(getMarketTime(headers)));
        // 业务名称
        paramMap.put("business", Strings.nullToEmpty(headers.getFirst(BUSINESS)));
        // 登陆用户名
        paramMap.put("userno", Strings.nullToEmpty(headers.getFirst(USERNO)));
        // 文件名
        paramMap.put("fileName", getUnEncryptFileName(file.getName()));

        return paramMap;
    }

    /**
     * 取得未加密后文件名
     *
     * @param encryptfileName   机密后文件名
     * @return                  未加密后文件名
     */
    private String getUnEncryptFileName(String encryptfileName) {
        return Strings.isNullOrEmpty(encryptfileName) ? "" : encryptfileName.substring(Constants.ENCRYPTED_NAME.length());
    }

    /**
     * 取得加密后文件名
     *
     * @param fileName  文件名
     * @return          加密后文件名
     */
    private String getEncryptFileNameWithoutExtension(String fileName) {
        return Joiner.on("").join(Constants.ENCRYPTED_NAME, fileName);
    }

    /**
     * 取得加密后文件名
     *
     * @param fileName  文件名
     * @return          加密后文件名
     */
    private String getEncryptFileName(String fileName) {
        return Joiner.on("").join(Constants.ENCRYPTED_NAME, fileName, FILE_EXTENSION);
    }

    /**
     * 取得添加[.data]后缀后文件名
     *
     * @param fileName  文件名
     * @return          加密后文件名
     */
    private String getFileNameWithoutExtension(String fileName) {
        return Joiner.on("").join(fileName, FILE_EXTENSION);
    }

    /**
     * 创建加密文件完整路径
     *
     * @param fileName 文件名
     * @return 加密文件完整路径
     */
    private String createEncryptFilePath(String fileName) {

        // 加密文件名
        String encryptFileName = Joiner.on("").skipNulls().join(Constants.ENCRYPTED_NAME, fileName);

        return createFilePath(encryptFileName);
    }

    /**
     * 创建文件完整路径并添加后缀
     *
     * @param fileName 文件名
     * @return 临时文件完整路径
     */
    private String createFilePathWithoutExtension(String fileName){

        // 添加文件后缀
        String fileNameWithExtension = Joiner.on("").skipNulls().join(fileName, FILE_EXTENSION);

        return createFilePath(fileNameWithExtension);

    }

    /**
     * 创建文件完整路径
     *
     * @param fileName 文件名
     * @return 临时文件完整路径
     */
    private String createFilePath(String fileName) {
        return Joiner.on(SEPARATOR).skipNulls().join(this.tempPath, fileName);
    }

    /**
     * 判断该文件名文件是否zip文件
     *
     * @param headers   请求体头部
     * @param fileName  文件名
     * @return  true 是, false 否
     */
    private boolean checkIsZipFile(HttpHeaders headers, String fileName){
        return Objects.equal(
                getEncryptFileName(getFileName(headers)),
                fileName);
    }

    /**
     * 构建上传失败参数
     *
     * @param headers   请求体头部
     * @param file      文件
     * @return          上传失败参数
     */
    private CallbackInput createUploadFailed(HttpHeaders headers, File file) {

        // 上传到上传组件失败
        CallbackInput input = new CallbackInput();

        // 外呼团队
        input.setCalledTeam(Strings.nullToEmpty(headers.getFirst(CALLED_TEAM)));
        // 取得手机号码在文件名称中开始位置
        int phoneStartPos = getPhoneStartPos(headers);
        // 被营销电话
        String phone = "";
        // 如果文件名为请求头的文件名，则为压缩包，不需要传入手机号码
        if (!checkIsZipFile(headers, file.getName())) {

            // 从文件名中取得手机号码
            phone = getPhone(file.getName(), Constants.ENCRYPTED_NAME.length() + phoneStartPos);

        }
        input.setMarketPhone(phone);
        // 营销时间
        input.setMarketTime(Strings.nullToEmpty(getMarketTime(headers)));
        // 业务名称
        input.setBusiness(Strings.nullToEmpty(headers.getFirst(BUSINESS)));
        // 文件名
        input.setFileName(getUnEncryptFileName(file.getName()));
        // 上传失败
        input.setCallbackCode(ResponseConst.SYSTEM_EXCEPTION);

        return input;

    }

    /**
     * 删除文件
     *
     * @param tempPath 文件路径
     */
    private void deleteFile(String tempPath){

        // 新建这个文件
        File file = new File(tempPath);
        // 如果文件存在则删除文件
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 校验截取的号码字段
     *
     * @param phone     号码库对象
     * @return          号码
     */
    private String validatePhone(String phone) {

    	if (Strings.isNullOrEmpty(phone)) {
            throw new EduException(MessageConstant.MARKETCHECK_INTERCEPTED_PHONE_IS_EMPTY);
    	} else if (phone.length() < PHONE_NUM_SIZE) {
    		throw new EduException(MessageConstant.MARKETCHECK_INTERCEPTED_PHONE_LESSTHAN_11);
    	}else if (!phone.matches(ConfigConstant.PHONE_CHECK)) {
    		throw new EduException(MessageConstant.MARKETCHECK_INTERCEPTED_PHONE_IS_NOT_PHONE);
    	}

    	return phone;

    }

    /**
     * 删除文件
     *
     * @param file 欲要删除的文件
     */
    private void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public static void main(String[] args) {
//        String temp = "a";
//        System.out.println(CharMatcher.JAVA_DIGIT.matchesAllOf(temp));

//        IMarketCheckServiceImpl checkService = new IMarketCheckServiceImpl();
//        List<File> files = checkService.unZip(new File("E:\\boss\\营销录音.zip"));
//        System.out.println(files);
//        List<String> fileNames = Lists.transform(files, new Function<File, String>() {
//            @Override
//            public String apply(File input) {
//                return input.getName();
//            }
//        });
//        System.out.println(fileNames);

//        String marketTime = "2015-09-24 16:47:48";
//        marketTime = DateUtil.stringToString(marketTime).substring(0, 8);
//        System.out.println(marketTime);

//        String fileName1 = "1111";
//        String fileName2 = "1111.zip";
//        String fileName3 = "1111.111.zip";
//        System.out.println("fileName1 : " + Files.getNameWithoutExtension(fileName1));
//        System.out.println("fileName2 : " + Files.getNameWithoutExtension(fileName2));
//        System.out.println("fileName3 : " + Files.getNameWithoutExtension(fileName3));

        String encryptFileName = "encrypted_营销录音.data";
        System.out.println(encryptFileName.substring(Constants.ENCRYPTED_NAME.length()));

        String filePath = "/data/file/boss.accore/";
        if (filePath.length() == filePath.lastIndexOf("/") + 1) {
            System.out.println("OK");
        }

    }
}
