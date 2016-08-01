package com.ancun.datasubscribe.util.bizeventbus;

/**
 * 测试监听器1
 *
 * @Created on 2016年03月30日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class DemoListener {

    @Subscribe
    public void throwEeception(Integer UpdateEvent){
        throw new RuntimeException("测试！");
    }

}
