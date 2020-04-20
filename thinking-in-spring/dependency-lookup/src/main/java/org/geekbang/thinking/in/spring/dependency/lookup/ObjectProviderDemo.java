package org.geekbang.thinking.in.spring.dependency.lookup;


/* 通过 ObjectProvider 进行依赖查找
 * ObjectProvider 是继承 ObjectFactory 实现的
 * 参考 package org.geekbang.thinking.in.spring.ioc.overview.dependency.lookup;
 *
 * @author Molly
 * @date 2020/4/20
 */

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class ObjectProviderDemo { // @Configuration是非必须的注解

    public static void main(String[] args) {
        //创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 作为 配置类（configuration class）
        applicationContext.register(ObjectProviderDemo.class);
        //启动应用上下文
        applicationContext.refresh();

        lookupByObjectProvider(applicationContext);//延迟查找
        lookupIfAvailable(applicationContext);
        lookupByStreamOps(applicationContext);//非延迟初始化bean 实现延迟查找 集合


        //关闭应用上下文
        applicationContext.close();
    }

    private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider =  applicationContext.getBeanProvider(String.class);
//        Iterable<String> stringIterable = objectProvider;
//        for(String string: stringIterable){
//            System.out.println(string);
//        }
        // Stream -> method reference
        objectProvider.stream().forEach(System.out::println);
    }

    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {

        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        User user = userObjectProvider.getIfAvailable(User::createUser);//当不存在user对象时new一个
        System.out.println("当前 User 对象:" + user);

    }

    @Bean
    @Primary
    public String helloWorld(){ //如果没有指定，方法名就是bean名称 名称 = "helloWorld"
        return "Hello,World";
    }

    @Bean
    public String message(){
        return "Message";
    }

    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {

        ObjectProvider<String> objectProvider =  applicationContext.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }


}
