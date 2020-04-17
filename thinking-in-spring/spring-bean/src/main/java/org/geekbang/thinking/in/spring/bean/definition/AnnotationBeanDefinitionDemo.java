package org.geekbang.thinking.in.spring.bean.definition;

/* 一. 配置xml
 * 二. 注解
 * 注解 BeanDefinitionDemo 示例
 * 1.通过 @Bean 方式 定义
 * 2.通过 @Component 方式
 * 3.导入 @Import 进行导入
 * 不会重复注册
 * 三.通过 BeanDefinition 注册API 实现注册
 * 1.命名方式
 * 2.非命名
 * 3.配置类
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

//3.导入 @Import 进行导入
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        //创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注册 配置类（configuration class）
        applicationContext.register(AnnotationBeanDefinitionDemo.class);

        //通过 BeanDefinition 注册API 实现注册
        //1.命名 Bean 的注册方式
        registerUserBeanDefinition(applicationContext,"moolly-user");
        //2.非命名 注册方式
        registerUserBeanDefinition(applicationContext);
        //org.geekbang.thinking.in.spring.ioc.overview.domain.User#0

        //启动应用上下文
        applicationContext.refresh();

        //按照类型依赖查找
//        Map<String,Config> configBeans = applicationContext.getBeansOfType(Config.class);
        //结果：不会重复注册
        System.out.println("Config 类型的所有 beans：" + applicationContext.getBeansOfType(Config.class));
        System.out.println("User 类型的所有 beans：" + applicationContext.getBeansOfType(User.class));


        //关闭应用上下文（显式）
        applicationContext.close();
    }

    /*
    * java api 配置元信息
    * 命名 Bean 的注册方式
    *
    * */
    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName){
        //通过 BeanDefinitionBuilder 构建
        BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(User.class);
        //属性设置
        beanDefinitionBuilder.addPropertyValue("id",1L)
                .addPropertyValue("name","Molly");

        //判断如果 beanName 参数存在时
        if(StringUtils.hasText(beanName)){
            //注册 BeanDefinition
            registry.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition());
        }else{
            //非命名 Bean 注册方式
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(),registry);
        }


    }
    /*
     * java api 配置元信息
     * 非命名 Bean 的注册方式
     *
     * */
    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry){
        registerUserBeanDefinition(registry, null);
    }

    //2.通过 @Component 方式
    @Component //定义当前类作为 Spring Bean（组件）
    public static class Config{
        /*
         * 通过java注解的方式，定义了一个Bean
         * 名称，别名
         * */
        //1.通过 @Bean 方式 定义
        @Bean(name = {"user","molly-user"})
        public User user(){
            User user = new User();
            user.setId(1L);
            user.setName("Molly");
            return user;
        }

    }


}
