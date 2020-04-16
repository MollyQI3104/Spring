package org.geekbang.thinking.in.spring.ioc.overview.container;

/* 注解能力的 ApplicationContext 作为 IoC容器 示例
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AnnotationApplicationContextAsIoCContainerDemo {

    public static void main(String[] args) {
        //创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 作为 配置类（configuration class）
        applicationContext.register(AnnotationApplicationContextAsIoCContainerDemo.class);
        //启动应用上下文
        applicationContext.refresh();

        lookupByCollectionType(applicationContext);//依赖查找集合对象

        //关闭应用上下文
        applicationContext.close();
    }

    /*
    * 通过java注解的方式，定义了一个Bean
    *
    * */
    @Bean
    public User user(){
        User user = new User();
        user.setId(1L);
        user.setName("Molly");
        return user;
    }

    private static void lookupByCollectionType(BeanFactory beanFactory){
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);//此调用返回map，id为key，内容为value
            System.out.println("查找到的所有User集合对象" + users);
        }
    }
}
