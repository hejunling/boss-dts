/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ancun.datasyn.activemq;

import com.ancun.common.persistence.model.master.BizSynRecord;
import com.ancun.datadispense.conf.EmailConf;
import com.ancun.datadispense.util.CustomException;
import com.ancun.datadispense.util.SendEmail;
import com.ancun.datasyn.common.impl.BizSynRecordServiceImpl;
import com.ancun.datasyn.pojo.user.BossUserInfo;
import com.ancun.datasyn.service.master.IBizUserInfoService;
import com.ancun.utils.DateUtil;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户信息消费
 */
@Component
public class UserInfoConsumer {

    public static final Logger logger = LoggerFactory.getLogger(UserInfoConsumer.class);

    private   Map<String,String> synUserMap = new ConcurrentHashMap<String, String>(); // 同步用户信息

    @Resource
    private EmailConf emailConf;

    @Resource
    private BizSynRecordServiceImpl bizSynRecordService;

    @Resource(name = "userPool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private IBizUserInfoService bizUserInfoService;

    @JmsListener(destination = "user.queue")
    public void receiveUserQueue(final BossUserInfo bossUserInfo) {

        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {

                logger.debug("Consumer线程ID = {},bizUserInfo = {}", Thread.currentThread().getId(), bossUserInfo);

                String uuid = String.valueOf(bossUserInfo.getUuid());
                String successNumberKey =  uuid+ "_successNumber"; // 成功 数量下标
                String errorNumberKey = uuid+ "_errorNumber"; // 失败 数量下标
                String errorInfoKey = uuid + "_errorInfo" ;  //  错误 信息下标

//                String synTime =  DateUtil.convertDateToString("yyyyMMddHHmmssSSS",bossUserInfo.getSynTime()); // 同步时间
                int successNunber = 0; // 同步成功条数
                int errorNumber = 0; // // 同步错误条数

                if(!synUserMap.containsKey(successNumberKey)){
                    synUserMap.put(successNumberKey, String.valueOf( successNunber) );
                }
                if(!synUserMap.containsKey(errorNumberKey)){
                    synUserMap.put(errorNumberKey, String.valueOf( errorNumber) );
                }
                if(!synUserMap.containsKey(errorInfoKey)){
                    synUserMap.put(errorInfoKey, "" );
                }
                if(StringUtil.isNotBlank(bossUserInfo.getErrorInfo())){
                    synUserMap.put(errorInfoKey,  synUserMap.get(errorInfoKey)+ bossUserInfo.getErrorInfo()+";");
                    synUserMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synUserMap.get(errorNumberKey)) + 1) );
                }else {
                    try {
                        bizUserInfoService.consumerBizUserInfo(bossUserInfo.getBizUserInfo());
                        // 如果成功，同步成功条数+1
                        synUserMap.put(successNumberKey, String.valueOf( Integer.parseInt(synUserMap.get(successNumberKey)) + 1) );
                    } catch (CustomException c) {
                        logger.debug("用户同步boss表失败={},bizUserInfo={}", c , bossUserInfo.getBizUserInfo());
                        synUserMap.put(errorInfoKey,  synUserMap.get(errorInfoKey)+"\n 用户同步boss表插入失败:" + c.getMessage() + " bizUserInfo:"+ bossUserInfo.getBizUserInfo()+";");
                        // 如果失败，无返回错误条数，同步失败条数+1
                        if (c.getNumber() == 0){
                            synUserMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synUserMap.get(errorNumberKey)) + 1) );
                        }else {
                            // 有返回条数
                            synUserMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synUserMap.get(errorNumberKey)) + c.getNumber()) );
                        }
                    }catch (Exception e) {
                        logger.debug("用户同步boss表失败={},bizUserInfo={}", e , bossUserInfo.getBizUserInfo());
                        synUserMap.put(errorInfoKey,  synUserMap.get(errorInfoKey)+"\n 用户同步boss表插入失败:" + e.getMessage() + " bizUserInfo:"+ bossUserInfo.getBizUserInfo()+";");
                        // 如果失败，同步失败条数+1
                        synUserMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synUserMap.get(errorNumberKey)) + 1) );
                    }
                }
                logger.debug("用户同步boss表总条数："+ bossUserInfo.getSynSize());
                // 如果 成功条数+失败条数 == 需要同步信息 插入表业务同步结果记录表
                successNunber = Integer.parseInt(synUserMap.get(successNumberKey));
                logger.debug("用户同步boss表成功条数："+successNunber);
                errorNumber = Integer.parseInt(synUserMap.get(errorNumberKey));
                logger.debug("用户同步boss表失败条数："+errorNumber);
                if( (successNunber + errorNumber ) == bossUserInfo.getSynSize()){

                    // 插入表业务同步结果记录表
                    logger.debug("插入业务同步结果记录表");
                    BizSynRecord bizSynRecord = new BizSynRecord();
                    try {
                        bizSynRecord.setBizName(bossUserInfo.getBizname());
                        bizSynRecord.setMoudle("user");
                        bizSynRecord.setSynStartTimer(bossUserInfo.getSynTime());
                        bizSynRecord.setSynEndTimer(new Date());
                        bizSynRecord.setRpcode(bossUserInfo.getBizUserInfo().getRpcode());
                        bizSynRecord.setCitycode(bossUserInfo.getBizUserInfo().getCityCode());
                        bizSynRecord.setSynCount(bossUserInfo.getSynSize());
                        bizSynRecord.setSynOkCount(Integer.parseInt(synUserMap.get(successNumberKey)));
                        bizSynRecordService.insert(bizSynRecord);
                    }catch (Exception e){
                        logger.error("业务同步结果记录表插入失败此 ={},bizSynRecord",e ,bizSynRecord);
                    }

                    // 发送邮件
                    if(errorNumber>0){
                        // 记录失败信息
                        logger.error(bossUserInfo.getBizname()+" 业务系统用户同步boss用户信息表失败（bizVoiceInfo），错误信息：\n"
                                            +synUserMap.get(errorInfoKey));

                    }
                    // 发送邮件
                    String synStarTime = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss",bossUserInfo.getSynTime());
                    String topical = "业务系统用户同步boss用户信息";
                    String bizName = bossUserInfo.getBizname();
                    String synSize = String.valueOf(bossUserInfo.getSynSize());
                    sendEmail( topical, bizName, synStarTime, synSize, successNunber, errorNumber);

                    // 成功移除此key
                    synUserMap.remove(errorNumberKey);
                    synUserMap.remove(successNumberKey);
                    synUserMap.remove(errorInfoKey);
                }

            }
        });

    }

    /**
     * 发送邮件
     * @param topical
     * @param bizName
     * @param synStarTime
     * @param synSize
     * @param synSucNumber
     * @param synErrNumber
     */
    private void sendEmail(String topical,String bizName,String synStarTime,String synSize,int synSucNumber,int synErrNumber){
        String message =topical + " 同步开始时间："+ synStarTime+"</br>";
        message += topical + " 同步结束时间：" + DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss",new Date() )+"</br>";
        message += "所属业务："+ bizName+"</br>";
        message += "同步总条数："+ synSize+"</br>";
        message += "同步成功条数："+ synSucNumber+"</br>";
        message += "同步失败条数："+ synErrNumber+"</br>";
        SendEmail.send(emailConf, message);
    }
}
