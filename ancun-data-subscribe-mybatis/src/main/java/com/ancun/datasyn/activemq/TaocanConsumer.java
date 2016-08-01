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
import com.ancun.datasyn.pojo.taocan.BossTaocanInfo;
import com.ancun.datasyn.service.master.ITaocanservice;
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
 * 用户套餐消费
 */
@Component
public class TaocanConsumer {

    public static final Logger logger = LoggerFactory.getLogger(TaocanConsumer.class);

    private   Map<String,String> synTaocanMap = new ConcurrentHashMap<String, String>(); // 同步套餐信息

    @Resource
    private EmailConf emailConf;

    @Resource
    private ITaocanservice taocanservice;

    @Resource
    private BizSynRecordServiceImpl bizSynRecordService;


    @Resource(name = "taocanPool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @JmsListener(destination = "combo.queue")
    public void receiveComboQueue(final BossTaocanInfo bossTaocanInfo) {

        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                logger.debug("Consumer线程ID = {},bizComboinfo = {}", Thread.currentThread().getId(), bossTaocanInfo);

                String uuid = String.valueOf(bossTaocanInfo.getUuid());
                String successNumberKey =  uuid + "_successNumber"; // 成功 数量下标
                String errorNumberKey = uuid + "_errorNumber"; // 失败 数量下标
                String errorInfoKey = uuid + "_errorInfo" ;  //  错误 信息下标

                int successNunber = 0; // 同步成功条数
                int errorNumber = 0; // // 同步错误条数

                if(!synTaocanMap.containsKey(successNumberKey)){
                    synTaocanMap.put(successNumberKey, String.valueOf( successNunber) );
                }
                if(!synTaocanMap.containsKey(errorNumberKey)){
                    synTaocanMap.put(errorNumberKey, String.valueOf( errorNumber) );
                }
                if(!synTaocanMap.containsKey(errorInfoKey)){
                    synTaocanMap.put(errorInfoKey, "" );
                }

                if(StringUtil.isNotBlank(bossTaocanInfo.getErrorInfo())){
                    synTaocanMap.put(errorInfoKey,  synTaocanMap.get(errorInfoKey)+ bossTaocanInfo.getErrorInfo()+";\n");
                    synTaocanMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synTaocanMap.get(errorNumberKey)) + 1) );
                }else {
                    try {
                        // 消费套餐队列，插入到boss 表中
                        taocanservice.synBossTaocanInfo(bossTaocanInfo.getBizComboInfo());
                        // 如果成功，同步成功条数+1
                        synTaocanMap.put(successNumberKey, String.valueOf( Integer.parseInt(synTaocanMap.get(successNumberKey)) + 1) );
                    } catch (CustomException c){
                        logger.debug("套餐同步boss表失败={},bizComboInfo={}", c , bossTaocanInfo.getBizComboInfo());
                        synTaocanMap.put(errorInfoKey,  synTaocanMap.get(errorInfoKey)+" \n 套餐同步boss表失败原因:"
                                            + c.getMessage() + " 套餐同步boss失败对象bizComboInfo:"+ bossTaocanInfo.getBizComboInfo()+";\n");
                        // 如果失败，无返回错误条数，同步失败条数+1
                        if (c.getNumber() == 0){
                            synTaocanMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synTaocanMap.get(errorNumberKey)) + 1) );
                        }else {
                            // 有返回条数
                            synTaocanMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synTaocanMap.get(errorNumberKey)) + c.getNumber()) );
                        }

                    }catch (Exception e){
                        logger.debug("套餐同步boss表失败={},bizComboInfo={}", e , bossTaocanInfo.getBizComboInfo());
                        synTaocanMap.put(errorInfoKey,  synTaocanMap.get(errorInfoKey)+"; \n 套餐同步boss表失败原因:"
                                + e.getMessage() + " 套餐同步boss失败对象bizComboInfo:"+ bossTaocanInfo.getBizComboInfo()+";\n");
                        // 如果失败，同步失败条数+1
                        synTaocanMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synTaocanMap.get(errorNumberKey)) + 1) );
                    }
                }

                // 如果 成功条数+失败条数 == 需要同步信息 插入表业务同步结果记录表
                successNunber = Integer.parseInt(synTaocanMap.get(successNumberKey));
                errorNumber = Integer.parseInt(synTaocanMap.get(errorNumberKey));

                logger.debug("套餐同步总条数："+bossTaocanInfo.getSynSize());
                logger.debug("套餐同步成功条数："+successNunber);
                logger.debug("套餐同步失败条数："+errorNumber);

                if( (successNunber + errorNumber ) == bossTaocanInfo.getSynSize()){

                    // 插入表业务同步结果记录表
                    logger.debug("插入业务同步结果记录表");
                    BizSynRecord bizSynRecord = new BizSynRecord();
                    try {
                        bizSynRecord.setBizName(bossTaocanInfo.getBizname());
                        bizSynRecord.setMoudle("taocan");
                        bizSynRecord.setSynStartTimer(bossTaocanInfo.getSynTime());
                        bizSynRecord.setSynEndTimer(new Date());
                        bizSynRecord.setRpcode(bossTaocanInfo.getBizComboInfo().getRpcode());
                        bizSynRecord.setCitycode(null);
                        bizSynRecord.setSynCount(bossTaocanInfo.getSynSize());
                        bizSynRecord.setSynOkCount(Integer.parseInt(synTaocanMap.get(successNumberKey)));
                        bizSynRecordService.insert(bizSynRecord);
                    }catch (Exception e){
                        logger.error("业务同步结果记录表插入失败此 ={},bizSynRecord",e ,bizSynRecord);
                    }


                    if(errorNumber>0){
                        // 记录失败信息
                        logger.error(bossTaocanInfo.getBizname()+" 业务系统套餐同步boss套餐失败（bizComboInfo）" +
                                         "，错误信息：\n" +synTaocanMap.get(errorInfoKey));
                    }
                    // 发送邮件
                    String synStarTime = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss",bossTaocanInfo.getSynTime());
                    String topical = "业务系统套餐同步boss套餐";
                    String bizName = bossTaocanInfo.getBizname();
                    String synSize = String.valueOf(bossTaocanInfo.getSynSize());
                    sendEmail( topical, bizName, synStarTime, synSize, successNunber, errorNumber);

                    // 成功移除此key
                    synTaocanMap.remove(errorNumberKey);
                    synTaocanMap.remove(successNumberKey);
                    synTaocanMap.remove(errorInfoKey);
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
