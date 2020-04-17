package org.geekbang.thinking.in.spring.bean.factory;

/* 默认 UserFactory 的实现（抽象工厂）
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

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

    //以下是销毁方法

    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy UserFactory 销毁中...");
    }

    //必须重写的 DisposableBean 中的方法
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy(): UserFactory 销毁中...");
    }

    public void doDestroy(){
        System.out.println("自定义销毁方法 doDestroy(): UserFactory 销毁中...");
    }


}
