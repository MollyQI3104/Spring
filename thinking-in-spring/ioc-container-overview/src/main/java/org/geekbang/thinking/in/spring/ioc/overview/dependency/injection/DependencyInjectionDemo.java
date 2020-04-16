package org.geekbang.thinking.in.spring.ioc.overview.dependency.injection;

/*
* 依赖注入示例
*
* 依赖的来源
* 1.自定义的bean
* 2.内建依赖（非bean）
* 3.容器内建bean
*
*
* */

import org.geekbang.thinking.in.spring.ioc.overview.annotation.Super;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.geekbang.thinking.in.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

public class DependencyInjectionDemo {

    public static void main(String[] args){
        //配置 XML 配置文件
        //启动 Spring 应用上下文

        // ApplicationContext is a sub-interface of BeanFactory.
        //所以下面写法也正确
//      BeanFactory beanFactory = new ClassPathXmlApplicationContext("META_INF/dependency-injection-context");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META_INF/dependency-injection-context");

        //根据名称类型查找userRepository
        //user和superuser则是注入
        //1.自定义的bean
        UserRepository userRepository = applicationContext.getBean("userRepository",UserRepository.class);

        //手动配置 集合操作 ref bean中
        // superUser在前就会先输出 而autowire方法会按loopup的xml文件中顺序输出bean
//        System.out.println(userRepository.getUsers());

//        System.out.println(userRepository.getBeanFactory() == beanFactory);
        //false
        //UserRepository:
        //private ObjectFactory<User> objectFactory;

        //依赖注入
        //2.内建依赖
        System.out.println(userRepository.getBeanFactory());
        //依赖查找 (error)
        //查不出来，内置beanFactory不是一个bean对象，是容器内建的依赖
//        System.out.println(beanFactory.getBean(BeanFactory.class));

        ObjectFactory userFactory = userRepository.getObjectFactory();
        System.out.println(userFactory.getObject());

        System.out.println(userFactory.getObject() == applicationContext);
        //true
        //UserRepository:
        //private ObjectFactory<ApplicationContext> objectFactory;

        //3.容器内建bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的bean" + environment);


    }

    //讨论问题：BeanFactory 和 ApplicationContext 谁是底层ioc容器
    //BeanFactory 是底层ioc容器 提供配置框架 基本特性
    //ApplicationContext 在此基础上整合更多企业特性功能（aop，国际化等）
    //所以两个不会相等，是两个对象
    private static void whoIsIoCContainer(UserRepository userRepository, ApplicationContext applicationContext){

        //这个表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == applicationContext);

        //ClassPathXmlApplicationContext <- ConfigurableApplicationContext <- ApplicationContext <- BeanFactory
        //ConfigurableApplicationContext 提供getBeanFactory() 为什么？明明本身就是一个BeanFactory
        //是一种组合方式 类似代理 并不是自己有能力，而是外面组合一个对象

        //ApplicationContext 就是 BeanFactory
        // ApplicationContext is a sub-interface of BeanFactory.
    }

}
