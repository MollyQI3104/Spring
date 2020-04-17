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

@Configuration //Configuration class
public class BeanInitializationDemo {

    public static void main(String[] args) {
        //创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注册 配置类（configuration class）
        applicationContext.register(BeanInitializationDemo.class);

        //启动应用上下文
        applicationContext.refresh();

        //依赖查找 UserFactory
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        //1. @PostConstruct 方法自动调用
        //2. 实现InitializingBean接口 afterPropertiesSet()
        //3. 自定义初始化方法 @Bean(initMethod = "initUserFactory") 或者 xml配置 或者 通过java api
        //且符合优先级

        //关闭应用上下文（显式）
        applicationContext.close();
    }

    @Bean(initMethod = "initUserFactory")
    public UserFactory userFactory(){
        return new DefaultUserFactory();
    }
}
