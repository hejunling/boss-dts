package com.ancun.common.persistence.mapper.master;

import com.ancun.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 测试消费记录MAPPER
 *
 * @Created on 2016年03月30日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class ConsumeMapperTest {

    @Resource
    private ConsumeMapper consumeMapper;

    @Test
    public void testSelectAll(){
        System.out.println(consumeMapper.selectAll());
    }

}
