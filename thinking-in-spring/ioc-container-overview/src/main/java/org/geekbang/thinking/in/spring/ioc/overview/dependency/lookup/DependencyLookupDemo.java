package org.geekbang.thinking.in.spring.ioc.overview.dependency.lookup;

/*
* 依赖查找示例
* 1.根据Bean名称（id）查找
*   实时查找
*   延时查找
* 2.根据Bean类型查找
*   单个Bean对象
*   集合Bean对象
* 3.根据Bean的id + 类型查找
*   User user = (User) beanFactory.getBean("user",User.class);
* 4.根据java注解查找
*   单个Bean对象
*   集合Bean对象
*
* */

import org.geekbang.thinking.in.spring.ioc.overview.annotation.Super;
import org.geekbang.thinking.in.spring.ioc.overview.domain.SuperUser;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class DependencyLookupDemo {

    public static void main(String[] args){
        //配置 XML 配置文件
        //启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META_INF/dependency-lookup-context");

        lookupInRealTime(beanFactory);//根据bean名称（id）查找 -- 实时查找 --直接
        lookupInLazy(beanFactory);//根据bean名称（id）查找 -- 延时查找 -- 间接

//        实时查找User{id=1, name='Molly'}
//        延迟查找User{id=1, name='Molly'}

        lookupByType(beanFactory);//根据bean类型查找 - 单一
        lookupByCollectionType(beanFactory);//根据bean类型查找 - 集合（多个数据）

        lookupByAnnotationType(beanFactory); //通过注解查找

    }

    private static void lookupInRealTime(BeanFactory beanFactory){
        User user = (User) beanFactory.getBean("user");//根据bean名称（id）查找 -- 实时查找

        System.out.println("实时查找"+ user);
    }

    private static void lookupInLazy(BeanFactory beanFactory){
        //根据bean名称（id）查找 -- 延时查找,借助ObjectFactoryCreatingFactoryBean
        //ObjectFactory没有生成新的bean，这是与FactoryBean的重大区别
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();//如果上一句不加泛型，返回Object类型

        System.out.println("延迟查找"+ user);

    }

    private static void lookupByType(BeanFactory beanFactory){
        User user = (User) beanFactory.getBean(User.class);//单一对象
        //superuser也是user
        //如果不在xml中为superuser标注primary，会报错
        //primary保证只找最主要的
        System.out.println(user);

    }

    private static void lookupByCollectionType(BeanFactory beanFactory){
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            Map<String,User> users = listableBeanFactory.getBeansOfType(User.class);//此调用返回map，id为key，内容为value
            System.out.println("查找到的所有User集合对象" + users);
//            查找到的所有User集合对象{user=User{id=1, name='Molly'}}
//            key: user value: User{id=1, name='Molly'}
        }
    }

    private static void lookupByAnnotationType(BeanFactory beanFactory){
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            Map<String,User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);//此调用返回map，id为key，内容为value
            System.out.println("查找到的 所有标注 @Super 的 User集合对象" + users);

        }

    }

}
