package org.geekbang.thinking.in.spring.bean.factory;

/* 默认 UserFactory 的实现（抽象工厂）
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

public class DefaultUserFactory implements UserFactory, InitializingBean {

    //1.基于 @PostConstruct 注解初始化
    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct UserFactory 初始化中...");
    }

    public void initUserFactory(){
        System.out.println("自定义初始化方法 initUserFactory(): UserFactory 初始化中...");
    }

    //必须重写的 InitializingBean 中的方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet(): UserFactory 初始化中...");
    }
}
