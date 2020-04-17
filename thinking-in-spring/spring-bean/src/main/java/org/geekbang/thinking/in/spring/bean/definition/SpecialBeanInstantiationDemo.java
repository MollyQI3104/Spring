package org.geekbang.thinking.in.spring.bean.definition;

/* Bean 实例化 示例
### 特殊方式
1.ServiceLoaderFactoryBean
2.AutowireCapableBeanFactory
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.geekbang.thinking.in.spring.bean.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.bean.factory.UserFactory;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

import static java.util.ServiceLoader.load;

public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {

        //配置 XML 配置文件
        //启动 Spring 应用上下文
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/special-bean-instantiation-context.xml");
        //通过 ApplicationContext 获取 AutowireCapableBeanFactory
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        ServiceLoader<UserFactory> serviceLoader = beanFactory.getBean("userFactoryServiceLoader",ServiceLoader.class);
        display(serviceLoader);
//        demoServiceLoader();
        //两种serviceLoader方式基本一样

        //通过 AutowireCapableBeanFactory 创建 UserFactory对象
        //一定要用具体类
        UserFactory userFactory = beanFactory.createBean(DefaultUserFactory.class);
        System.out.println(userFactory.createUser());

    }

//    public static void demoServiceLoader(){
//        ServiceLoader<UserFactory> serviceLoader = load(UserFactory.class, Thread.currentThread().getContextClassLoader());
//        //serviceLoader类对应META-INF/services下文件，里面写几个类就会实例化几个,且去重
//        display(serviceLoader);
//
//    }

    private static void display(ServiceLoader<UserFactory> serviceLoader){

        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while(iterator.hasNext()){
            UserFactory userFactory = iterator.next();
            System.out.println(userFactory.createUser());
        }

    }


}
