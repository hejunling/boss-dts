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
import com.ancun.datasyn.pojo.voice.BossVoiceInfo;
import com.ancun.datasyn.service.master.IVoiceservice;
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
 * 用户录音消费
 */
@Component
public class VoiceConsumer {

    public static final Logger logger = LoggerFactory.getLogger(VoiceConsumer.class);

    private   Map<String,String> synVoiceMap = new ConcurrentHashMap<String, String>(); // 同步录音信息

    @Resource
    private EmailConf emailConf;

    @Resource
    private IVoiceservice voiceservice;

    @Resource
    private BizSynRecordServiceImpl bizSynRecordService;


    @Resource(name = "voicePool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @JmsListener(destination = "voice.queue")
    public void receiveVoiceQueue(  final BossVoiceInfo bossVoiceInfo) {

        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                logger.debug("Consumer线程ID = {},bossVoiceInfo = {}", Thread.currentThread().getId(), bossVoiceInfo);

                String uuid = String.valueOf(bossVoiceInfo.getUuid());
                String successNumberKey =  uuid+ "_successNumber";
                String errorNumberKey = uuid+ "_errorNumber";
                String errorInfoKey = uuid + "_errorInfo" ;

//                String synTime =  DateUtil.convertDateToString("yyyyMMddHHmmssSSS",bossVoiceInfo.getSynTime()); // 同步时间
                int successNunber = 0; // 同步 成功条数
                int errorNumber = 0; // // 同步 错误条数

                if(!synVoiceMap.containsKey(successNumberKey)){
                    synVoiceMap.put(successNumberKey, String.valueOf( successNunber) );
                }
                if(!synVoiceMap.containsKey(errorNumberKey)){
                    synVoiceMap.put(errorNumberKey, String.valueOf( errorNumber) );
                }
                if(!synVoiceMap.containsKey(errorInfoKey)){
                    synVoiceMap.put(errorInfoKey, "" );
                }

                if(StringUtil.isNotBlank(bossVoiceInfo.getErrorInfo())){
                    synVoiceMap.put(errorInfoKey,  synVoiceMap.get(errorInfoKey)+ bossVoiceInfo.getErrorInfo()+"; \n");
                    synVoiceMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synVoiceMap.get(errorNumberKey)) + 1) );
                }else {
                    try {
                        // 消费套餐队列，插入到boss 表中
                        voiceservice.synBossVoiceInfo(bossVoiceInfo.getBizUserVoiceInfo());

                        // 如果成功，同步成功条数+1
                        synVoiceMap.put(successNumberKey, String.valueOf( Integer.parseInt(synVoiceMap.get(successNumberKey)) + 1) );

                    }catch (CustomException c){
                        logger.debug("录音同步boss表失败={},bizUserVoiceInfo={}", c , bossVoiceInfo.getBizUserVoiceInfo());
                        synVoiceMap.put(errorInfoKey,  synVoiceMap.get(errorInfoKey)+"\n 录音同步boss表失败原因:" + c.getMessage() + " 录音同步boss表失败对象bizUserVoiceInfo:"+ bossVoiceInfo.getBizUserVoiceInfo()+"; ");
                        // 如果失败，无返回错误条数，同步失败条数+1
                        if (c.getNumber() == 0){
                            synVoiceMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synVoiceMap.get(errorNumberKey)) + 1) );
                        }else {
                            // 有返回条数
                            synVoiceMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synVoiceMap.get(errorNumberKey)) + c.getNumber()) );
                        }
                    }catch (Exception e){
                        logger.debug("录音同步boss表失败={},bizUserVoiceInfo={}", e , bossVoiceInfo.getBizUserVoiceInfo());
                        synVoiceMap.put(errorInfoKey,  synVoiceMap.get(errorInfoKey)+"\n 录音同步boss表失败原因:" + e.getMessage() + " 录音同步boss表失败对象bizUserVoiceInfo:"+ bossVoiceInfo.getBizUserVoiceInfo()+"; ");
                        // 如果失败，同步失败条数+1
                        synVoiceMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synVoiceMap.get(errorNumberKey)) + 1) );
                    }
                }

                // 如果 成功条数+失败条数 == 需要同步信息 插入表业务同步结果记录表
                successNunber = Integer.parseInt(synVoiceMap.get(successNumberKey));
                logger.debug("录音同步总条数："+bossVoiceInfo.getSynSize());
                logger.debug("录音同步成功条数："+successNunber);
                errorNumber = Integer.parseInt(synVoiceMap.get(errorNumberKey));
                logger.debug("录音同步失败条数："+errorNumber);
                if( (successNunber + errorNumber ) == bossVoiceInfo.getSynSize()){

                    // 插入表业务同步结果记录表
                    logger.debug("插入业务同步结果记录表");
                    BizSynRecord bizSynRecord = new BizSynRecord();
                    try {
                        bizSynRecord.setBizName(bossVoiceInfo.getBizname());
                        bizSynRecord.setMoudle("voice");
                        bizSynRecord.setSynStartTimer(bossVoiceInfo.getSynTime());
                        bizSynRecord.setSynEndTimer(new Date());
                        bizSynRecord.setRpcode(bossVoiceInfo.getBizUserVoiceInfo().getRpcode());
                        bizSynRecord.setCitycode(bossVoiceInfo.getBizUserVoiceInfo().getCitycode());
                        bizSynRecord.setSynCount(bossVoiceInfo.getSynSize());
                        bizSynRecord.setSynOkCount(Integer.parseInt(synVoiceMap.get(successNumberKey)));
                        bizSynRecordService.insert(bizSynRecord);
                    }catch (Exception e){
                        logger.error("业务同步结果记录表插入失败此 ={},bizSynRecord",e ,bizSynRecord);
                    }

                    // 记录失败信息
                    if(errorNumber>0){
                        logger.error(bossVoiceInfo.getBizname()+" 业务系统录音同步boss录音表失败（bizVoiceInfo），错误信息：\n" +synVoiceMap.get(errorInfoKey));
                    }

                    // 发送邮件
                    String synStarTime = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss",bossVoiceInfo.getSynTime());
                    String topical = "业务系统录音同步boss录音";
                    String bizName = bossVoiceInfo.getBizname();
                    String synSize = String.valueOf(bossVoiceInfo.getSynSize());
                    sendEmail( topical, bizName, synStarTime, synSize, successNunber, errorNumber);

                    // 成功移除此key
                    synVoiceMap.remove(errorNumberKey);
                    synVoiceMap.remove(successNumberKey);
                    synVoiceMap.remove(errorInfoKey);
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
