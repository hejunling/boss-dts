package com.ancun.datasyn.activemq;

import com.ancun.datasyn.pojo.taocan.BossTaocanInfo;
import com.ancun.datasyn.pojo.user.BossUserInfo;
import com.ancun.datasyn.pojo.userlife.BossUserLifeInfo;
import com.ancun.datasyn.pojo.voice.BossVoiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * ActiveMQ生产者
 */
@Component
public class Producer{
    public static final Logger logger = LoggerFactory.getLogger(Producer.class);
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue userQueue;

    @Autowired
    private Queue comboQueue;

    @Autowired
    private Queue voiceQueue;

    @Autowired
    private Queue lifeCircleQueue;

//    @Override
//    public void run(String... args) throws Exception {
////        send("Sample message");
////        System.out.println("Message was sent to the Queue");
//    }

//    public void send(BizUserInfo bizUserInfo) {
//        System.out.println("Producer线程ID=" + Thread.currentThread().getId());
//        this.jmsMessagingTemplate.convertAndSend(this.userQueue, bizUserInfo);
//    }

    public void send(BossUserInfo bossUserInfo) {
        logger.debug("Producer (bossUserInfo)线程ID={} ,send bossUserInfo={}",Thread.currentThread().getId(),bossUserInfo);
        this.jmsMessagingTemplate.convertAndSend(this.userQueue, bossUserInfo);
    }

    public void sendComboQueue(BossTaocanInfo bossTaocanInfo){
        logger.debug("Producer (bossTaocanInfo)线程ID={} ,send bossTaocanInfo={}",Thread.currentThread().getId(),bossTaocanInfo);
        this.jmsMessagingTemplate.convertAndSend(this.comboQueue, bossTaocanInfo);
    }

    public void sendVoiceQueue(BossVoiceInfo bossVoiceInfo){
        logger.debug("Producer (bossVoiceInfo)线程ID={} ,send bossVoiceInfo={}",Thread.currentThread().getId(),bossVoiceInfo);
        this.jmsMessagingTemplate.convertAndSend(this.voiceQueue, bossVoiceInfo);
    }

    public void sendUserLifeQueue(BossUserLifeInfo bossUserLifeInfo){
        logger.debug("Producer (bossUserLifeInfo)线程ID={} ,send bossUserLifeInfo={}",Thread.currentThread().getId(),bossUserLifeInfo);
        this.jmsMessagingTemplate.convertAndSend(this.lifeCircleQueue, bossUserLifeInfo);
    }

}
