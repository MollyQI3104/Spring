package org.geekbang.thinking.in.spring.dependency.lookup;


/* 层次性 依赖查找 示例
 *
 * @author Molly
 * @date 2020/4/20
 */

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class HierarchicalDependencyLookupDemo {

    public static void main(String[] args) {
        //创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 作为 配置类（configuration class）
        applicationContext.register(HierarchicalDependencyLookupDemo.class);

        //ConfigurableListableBeanFactory 既是单一类型，又是集合类型，又是层次类型
        //默认实现 DefaultListableBeanFactory 实现 ConfigurableListableBeanFactory，所以默认的既是...又是...
        //1. 获取 HierarchicalBeanFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("当前 BeanFactory 的 Parent Bean: " + beanFactory.getParentBeanFactory());
        //null

        //2. 设置 Parent BeanFactory
        HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        System.out.println("当前 BeanFactory 的 Parent Bean: " + beanFactory.getParentBeanFactory());

        displayContainsLocalBean(beanFactory,"user");
        displayContainsLocalBean(parentBeanFactory,"user");

        displayContainsBean(beanFactory,"user");//层次性查找
        displayContainsBean(parentBeanFactory,"user");

        //启动应用上下文
        applicationContext.refresh();

        //关闭应用上下文
        applicationContext.close();
    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName){

        System.out.printf("当前 BeanFactory[%s] 是否包含 bean[name: %s]: %s\n", beanFactory,beanName,
                containsBean(beanFactory,beanName));

    }
//递归实现
    private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName){
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if(parentBeanFactory instanceof HierarchicalBeanFactory){
            HierarchicalBeanFactory hierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
            if(containsBean(hierarchicalBeanFactory,beanName)){
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName){
        System.out.printf("当前 BeanFactory[%s] 是否包含 Local bean[name: %s]: %s\n", beanFactory,beanName,
                beanFactory.containsLocalBean(beanName));

    }


    private static ConfigurableListableBeanFactory createParentBeanFactory(){

        //创建 BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // xml 配置文件 classpath 路径
        String location = "META_INF/dependency-lookup-context";
        //加载配置
        reader.loadBeanDefinitions(location);
        return beanFactory;
    }




}
