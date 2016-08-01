package com.ancun.datasyn.task;

import com.ancun.common.persistence.model.master.BizTimerConfig;
import com.ancun.datadispense.conf.QueueConf;
import com.ancun.datasyn.constant.SynConstant;
import com.ancun.datasyn.pojo.taocan.TaocanInput;
import com.ancun.datasyn.pojo.userlife.UserLifeInput;
import com.ancun.datasyn.pojo.voice.VoiceInput;
import com.ancun.datasyn.service.master.IBizTimerCofigService;
import com.ancun.datasyn.service.master.ITaocanservice;
import com.ancun.datasyn.service.master.IUserLifeService;
import com.ancun.datasyn.service.master.IVoiceservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 同步事件
 *
 * @author mif
 * @version 1.0
 * @Created on 2016/5/17
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Component
public class SchedulerSynTask {

    public static final Logger logger = LoggerFactory.getLogger(SchedulerSynTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Resource
    private IBizTimerCofigService bizTimerCofigService;

    @Resource
    private UserSynTask userSynTask;

    @Resource
    private ITaocanservice taocanservice;

    @Resource
    private IVoiceservice voiceservice;

    @Resource
    private QueueConf queueConf;

    @Resource
    private IUserLifeService userLifeService;

    public void run(String moudle) {
        BizTimerConfig config = bizTimerCofigService.selectByMoudle(moudle);
        bizTimerCofigService.updateBizTimerConfig(moudle);

        String startTime = dateFormat.format(config.getSynStartTimer());
        String endTimer = dateFormat.format(config.getSynEndTimer());

        if (moudle.equals(SynConstant.SYN_USER) && queueConf.getUserOnOff()) {

            logger.info("执行用户信息同步,{}", dateFormat.format(new Date()));

            userSynTask.send2Producer(config);

        } else if (moudle.equals(SynConstant.SYN_VOICE) && queueConf.getVoiceOnOff()) {
            logger.info("执行录音同步,{}", dateFormat.format(new Date()));

            VoiceInput voiceInput = new VoiceInput();
            voiceInput.setStartime(startTime);
            voiceInput.setEndtime(endTimer);
            voiceservice.insertVoiceInfoQ(voiceInput);

        } else if (moudle.equals(SynConstant.SYN_COMBO) && queueConf.getTaocanOnOff()) {

            logger.info("执行套餐同步,{}", dateFormat.format(new Date()));
            TaocanInput taocanInput = new TaocanInput();
            taocanInput.setStartime(startTime);
            taocanInput.setEndtime(endTimer);
            taocanservice.insertTcInfoQ(taocanInput);
        } else if (moudle.equals(SynConstant.SYN_LIFECIRLE) && queueConf.getUserLifeOnOff()) {

            logger.info("执行生命周期同步,{}", dateFormat.format(new Date()));
            UserLifeInput userLifeInput = new UserLifeInput();
            userLifeInput.setStartime(startTime);
            userLifeInput.setEndtime(endTimer);
            userLifeService.insertUserLifeInfoQ(userLifeInput);
        }
    }
}
