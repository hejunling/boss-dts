/*
 * Copyright 2012-2016 the original author or authors.
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

package com.ancun.datasyn.service.provice.impl;

import com.ancun.Application;
import com.ancun.datasyn.pojo.voice.VoiceInput;
import com.ancun.datasyn.service.master.IVoiceservice;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import java.text.SimpleDateFormat;

/**
 * Integration tests for demo application.
 *
 * @author Eddú Meléndez
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class VoiceAcitivtyTest {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private IVoiceservice voiceservice;

    @Test
    public void sendSimpleMessage() throws InterruptedException, JMSException {
        VoiceInput input = new VoiceInput();
//        input.setStartime("2015-12-12 00:00:00");
//        input.setEndtime(dateFormat.format(new Date()));
        input.setStartime("2016-05-20 00:00:00");
        voiceservice.insertVoiceInfoQ(input);
    }

}
