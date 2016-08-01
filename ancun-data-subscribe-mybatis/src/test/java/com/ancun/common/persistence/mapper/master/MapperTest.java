package com.ancun.common.persistence.mapper.master;

import com.ancun.Application;
import com.ancun.common.persistence.model.master.Consume;
import com.ancun.common.persistence.model.master.Subscribe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * 测试通用Mapper泛型注入操作
 *
 * @Created on 2016年03月30日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class MapperTest {

    /** 使用泛型注入DTS通道信息Mapper */
    @Resource
    private Mapper<Subscribe> subscribeMapper;

    /** 使用泛型注入消费记录Mapper */
    @Resource
    private Mapper<Consume> consumeMapper;

    /**
     * 测试使用泛型注入取得所有DTS通道信息
     */
    @Test
    public void testSubscribes(){
        System.out.println(subscribeMapper.selectAll());
    }

    /**
     * 测试使用泛型注入取得所有消费记录信息
     */
    @Test
    public void testConsumes(){
        System.out.println(consumeMapper.selectAll());
    }

}
