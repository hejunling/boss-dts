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
import com.ancun.datasyn.pojo.userlife.BossUserLifeInfo;
import com.ancun.datasyn.service.master.IUserLifeService;
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
 * 用户生命周期消费
 */
@Component
public class UserInfoLifeConsumer {

    public static final Logger logger = LoggerFactory.getLogger(UserInfoLifeConsumer.class);

    private   Map<String,String> synUserLifeMap = new ConcurrentHashMap<String, String>(); // 同步用户信息

    @Resource
    private EmailConf emailConf;

    @Resource
    private IUserLifeService userLifeService;

    @Resource
    private BizSynRecordServiceImpl bizSynRecordService;

    @Resource(name = "userLifePool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @JmsListener(destination = "lifeCircle.queue")
    public void receiveLifeCircleQueue(final BossUserLifeInfo bossUserLifeInfo) {

        threadPoolTaskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                logger.debug("Consumer线程ID = {},bossUserLifeInfo = {}", Thread.currentThread().getId(), bossUserLifeInfo);

                int successNunber = 0; // 同步 成功条数
                int errorNumber = 0; // // 同步 错误条数
                String uuid = String.valueOf(bossUserLifeInfo.getUuid());
                String successNumberKey =  uuid+ "_successNumber";
                String errorNumberKey = uuid+ "_errorNumber";
                String errorInfoKey = uuid + "_errorInfo" ;

                if(!synUserLifeMap.containsKey(successNumberKey)){
                    synUserLifeMap.put(successNumberKey, String.valueOf( successNunber) );
                }
                if(!synUserLifeMap.containsKey(errorNumberKey)){
                    synUserLifeMap.put(errorNumberKey, String.valueOf( errorNumber) );
                }
                if(!synUserLifeMap.containsKey(errorInfoKey)){
                    synUserLifeMap.put(errorInfoKey, "" );
                }

                if(StringUtil.isNotBlank(bossUserLifeInfo.getErrorInfo())){
                    synUserLifeMap.put(errorInfoKey,  synUserLifeMap.get(errorInfoKey)+ bossUserLifeInfo.getErrorInfo()+";\n");
                    synUserLifeMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synUserLifeMap.get(errorNumberKey)) + 1) );
                }else {
                    try {
                        // 消费用户生命周期队列，插入到boss 表中
                        userLifeService.synUserHistory(bossUserLifeInfo.getBizUserLifeCircle(),bossUserLifeInfo.getOpenTime()
                                                            ,bossUserLifeInfo.getCancelTime());
                        // 如果成功，同步成功条数+1
                        synUserLifeMap.put(successNumberKey, String.valueOf( Integer.parseInt(synUserLifeMap.get(successNumberKey)) + 1) );
                    }catch (CustomException c){
                        logger.debug("用户历史同步 boss表失败={},bizUserLifeCircle={}", c , bossUserLifeInfo.getBizUserLifeCircle());
                        synUserLifeMap.put(errorInfoKey,  synUserLifeMap.get(errorInfoKey)
                                +" \n 同步用户历史 boss表失败原因" + c.getMessage()
                                + " bizUserLifeCircle:"+ bossUserLifeInfo.getBizUserLifeCircle()+";");
                        // 如果失败，无返回错误条数，同步失败条数+1
                        if (c.getNumber() == 0){
                            synUserLifeMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synUserLifeMap.get(errorNumberKey)) + 1) );
                        }else {
                            // 有返回条数
                            synUserLifeMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synUserLifeMap.get(errorNumberKey)) + c.getNumber()) );
                        }

                    }catch (Exception e){
                        logger.debug("用户历史同步 boss表失败={},bizUserLifeCircle={}", e , bossUserLifeInfo.getBizUserLifeCircle());
                        synUserLifeMap.put(errorInfoKey,  synUserLifeMap.get(errorInfoKey)
                                                +" \n 同步用户历史 boss表失败原因" + e.getMessage()
                                                     + " bizUserLifeCircle:"+ bossUserLifeInfo.getBizUserLifeCircle()+";");
                        // 如果失败，同步失败条数+1
                        synUserLifeMap.put(errorNumberKey, String.valueOf(  Integer.parseInt(synUserLifeMap.get(errorNumberKey)) + 1) );
                    }
                }

                // 如果 成功条数+失败条数 == 需要同步信息 插入表业务同步结果记录表
                successNunber = Integer.parseInt(synUserLifeMap.get(successNumberKey));
                errorNumber = Integer.parseInt(synUserLifeMap.get(errorNumberKey));

                logger.debug("用户历史同步总条数："+bossUserLifeInfo.getSynSize());
                logger.debug("用户历史同步成功条数："+successNunber);
                logger.debug("用户历史同步失败条数："+errorNumber);

                if( (successNunber + errorNumber ) == bossUserLifeInfo.getSynSize()){

                    // 插入表业务同步结果记录表
                    logger.debug("插入业务同步结果记录表");
                    BizSynRecord bizSynRecord = new BizSynRecord();
                    try {
                        bizSynRecord.setBizName(bossUserLifeInfo.getBizname());
                        bizSynRecord.setMoudle("userLifeCircle");
                        bizSynRecord.setSynStartTimer(bossUserLifeInfo.getSynTime());
                        bizSynRecord.setSynEndTimer(new Date());
                        bizSynRecord.setRpcode(bossUserLifeInfo.getBizUserLifeCircle().getRpcode());
                        bizSynRecord.setCitycode(null);
                        bizSynRecord.setSynCount(bossUserLifeInfo.getSynSize());
                        bizSynRecord.setSynOkCount(Integer.parseInt(synUserLifeMap.get(successNumberKey)));
                        bizSynRecordService.insert(bizSynRecord);
                    }catch (Exception e){
                        logger.error("业务同步结果记录表插入失败此 ={},bizSynRecord",e ,bizSynRecord);
                    }

                    // 记录失败信息
                    if(errorNumber>0){
                        logger.error(bossUserLifeInfo.getBizname()+" 业务系统用户历史信息同步boss用户生命周期（bizComboInfo）" +
                                "，错误信息：\n" +synUserLifeMap.get(errorInfoKey));
                    }
                    // 发送邮件
                    String synStarTime = DateUtil.convertDateToString("yyyy-MM-dd HH:mm:ss",bossUserLifeInfo.getSynTime());
                    String topical = "业务系统用户历史信息同步boss用户生命周期";
                    String bizName = bossUserLifeInfo.getBizname();
                    String synSize = String.valueOf(bossUserLifeInfo.getSynSize());
                    sendEmail( topical, bizName, synStarTime, synSize, successNunber, errorNumber);

                    // 成功移除此key
                    synUserLifeMap.remove(errorNumberKey);
                    synUserLifeMap.remove(successNumberKey);
                    synUserLifeMap.remove(errorInfoKey);
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
