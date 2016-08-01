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

package com.ancun.sample.activemq;

import com.ancun.Application;
import com.ancun.common.persistence.model.master.BizUserInfo;
import com.ancun.datasyn.activemq.Producer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Integration tests for demo application.
 *
 * @author Eddú Meléndez
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class SampleActiveMqTests {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private Producer producer;

    @Test
    public void sendSimpleMessage() throws InterruptedException, JMSException {
       /* BizUserInfo bizUserInfo = new BizUserInfo();
        bizUserInfo.setUserNo("15957178759");
        System.out.println("时间=" + dateFormat.format(new Date()));
        this.producer.send(bizUserInfo);

        BizUserInfo bizUserInfo1 = new BizUserInfo();
        bizUserInfo1.setUserNo("15957178751");
        this.producer.send(bizUserInfo1);

        BizUserInfo bizUserInfo2 = new BizUserInfo();
        bizUserInfo2.setUserNo("15957178752");
        this.producer.send(bizUserInfo2);*/
//        Thread.sleep(1000L);
//        assertThat(this.outputCapture.toString().contains("Test message")).isTrue();
    }

}
