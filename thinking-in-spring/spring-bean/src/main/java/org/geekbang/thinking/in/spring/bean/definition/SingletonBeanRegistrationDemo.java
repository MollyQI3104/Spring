package org.geekbang.thinking.in.spring.bean.definition;

/* 单体 Bean 注册示例
 * 使用外部对象注册
 * @author Molly
 * @date 2020/4/18
 */

import org.geekbang.thinking.in.spring.bean.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonBeanRegistrationDemo {

    public static void main(String[] args) {
        //创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //创建一个外部 UserFactory 对象
        UserFactory userFactory = new DefaultUserFactory();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        //注册 外部单体对象
        beanFactory.registerSingleton("userFactory",userFactory);

        //启动应用上下文
        applicationContext.refresh();

        //通过依赖查找来获取 UserFactory 对象
        UserFactory userFactoryByLookup = beanFactory.getBean("userFactory",UserFactory.class);
        System.out.println("userFactoryByLookup == userFactory:" + (userFactoryByLookup == userFactory));

        //关闭应用上下文（显式）
        applicationContext.close();


    }
}
