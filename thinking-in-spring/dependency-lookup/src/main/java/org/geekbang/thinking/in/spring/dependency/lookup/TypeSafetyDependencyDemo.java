package org.geekbang.thinking.in.spring.dependency.lookup;

/* 类型安全 依赖查找 示例
 *
 * @author Molly
 * @date 2020/4/20
 */

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TypeSafetyDependencyDemo {
    public static void main(String[] args) {
        //创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 作为 配置类（configuration class）
        applicationContext.register(TypeSafetyDependencyDemo.class);
        //启动应用上下文
        applicationContext.refresh();

        //单一类型 3种查找方式安全性比较
        //1.演示 BeanFactory#getBean 方法的安全性  实时
        displayBeanFactoryGetBean(applicationContext);//不安全
        //NoSuchBeanDefinitionException

        //2.演示 ObjectFactory#getObject 方法的安全性  延时
        displayObjectFactoryGetObject(applicationContext);//不安全

        //3.演示 ObjectProvider#getIfAvailable 方法的安全性  延时
        displayObjectProviderIfAvailable(applicationContext);//安全

        //集合类型 2 种查找方式安全性比较
        //1.演示 ListableBeanFactory#getBeansOfType 方法的安全性  实时
        displayListableBeanFactoryGetBeansOfType(applicationContext);//安全

        //2.演示 ObjectProvider Stream 方法 操作的安全性  延时
        displayObjectProviderStreamOps(applicationContext);//安全

        //关闭应用上下文
        applicationContext.close();
    }

    private static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderStreamOps",()->userObjectProvider.stream().forEach(System.out::println));
    }

    private static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory beanFactory) {
        printBeansException("displayListableBeanFactoryGetBeansOfType",()->beanFactory.getBeansOfType(User.class));
    }

    private static void displayObjectProviderIfAvailable(AnnotationConfigApplicationContext applicationContext) {

        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderIfAvailable",()->userObjectProvider.getIfAvailable());
    }

    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
        //ObjectProvider is ObjectFactory
        ObjectFactory<User> userObjectFactory = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectFactoryGetObject",()->userObjectFactory.getObject());

    }

    public  static  void displayBeanFactoryGetBean(BeanFactory beanFactory){
        printBeansException("displayBeanFactoryGetBean",()->beanFactory.getBean(User.class));
    }

    private static void printBeansException(String source,Runnable runnable){
        System.err.println("==============================================");
        System.err.println("Source from :" + source);

        try{
            runnable.run();
        }catch(BeansException exception){
            exception.printStackTrace();
        }

    }
}
