package org.geekbang.thinking.in.spring.ioc.overview.container;

/* BeanFactory 作为 IoC容器 示例
 * 这个例子说明，即使不使用applicationContext，用底层的beanFactory，也能使用bean，但是不能再用事件，资源管理等其他特性
 * 体现了两者区别
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

public class BeanFactoryAsIoCContainerDemo {

    public static void main(String[] args) {
        //创建 BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // xml 配置文件 classpath 路径
        String location = "META_INF/dependency-lookup-context";
        //加载配置
        int beanDefinitionCount = reader.loadBeanDefinitions(location);
        System.out.println("Bean 定义加载的数量: " + beanDefinitionCount);//返回多少个bean

        lookupByCollectionType(beanFactory);//依赖查找集合对象
    }

    private static void lookupByCollectionType(BeanFactory beanFactory){
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);//此调用返回map，id为key，内容为value
            System.out.println("查找到的所有User集合对象" + users);
        }
    }
}
