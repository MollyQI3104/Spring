package org.geekbang.thinking.in.spring.bean.definition;

/* Bean 初始化
 *
 * @author Molly
 * @date 2020/4/18
 */


import org.geekbang.thinking.in.spring.bean.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration //Configuration class
public class BeanInitializationDemo {

    public static void main(String[] args) {
        //创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注册 配置类（configuration class）
        applicationContext.register(BeanInitializationDemo.class);

        //启动应用上下文
        applicationContext.refresh();//非延迟的初始化在此过程中

        //非延迟初始化 在 Spring 应用上下文启动完成后，被初始化 @Lazy(false) 或者不注解 默认就是非延迟
        System.out.println("Spring 应用上下文已启动...");
        //延迟初始化则是按需初始化，在依赖查找时触动了 @Lazy

        //依赖查找 UserFactory
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        //1. @PostConstruct 方法自动调用
        //2. 实现InitializingBean接口 afterPropertiesSet()
        //3. 自定义初始化方法 @Bean(initMethod = "initUserFactory") 或者 xml配置 或者 通过java api
        //且符合优先级

        System.out.println(userFactory);

        System.out.println("Spring 应用上下文准备关闭...");
        //关闭应用上下文（显式）
        applicationContext.close();//这个过程中调用了销毁方法
        System.out.println("Spring 应用上下文已关闭...");
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
    // @Lazy 延迟初始化
    public UserFactory userFactory(){
        return new DefaultUserFactory();
    }
}
