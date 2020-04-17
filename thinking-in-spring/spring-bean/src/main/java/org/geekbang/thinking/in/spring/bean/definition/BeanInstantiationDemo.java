package org.geekbang.thinking.in.spring.bean.definition;

/* Bean 实例化 示例
### 常规方式
1.通过构造器
2.通过静态方法
3.通过Bean工厂
4.通过FactoryBean
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanInstantiationDemo {

    public static void main(String[] args) {

        //配置 XML 配置文件
        //启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/bean-instantiation-context.xml");
        //静态方法实例化bean
        User user = (User) beanFactory.getBean("user-by-static-method", User.class);
        //实例方法实例化bean(bean工厂方法)
        User userByInstanceMthod = beanFactory.getBean("user-by-instance-method",User.class);
        //FactoryBean方法实例化 Bean
        User userByFactoryBean = beanFactory.getBean("user-by-factory-bean",User.class);


        System.out.println(user);
        System.out.println(userByInstanceMthod);
        System.out.println(userByFactoryBean);
        System.out.println(user == userByInstanceMthod);//false
        System.out.println(user == userByFactoryBean);


    }


}
