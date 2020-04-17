package org.geekbang.thinking.in.spring.bean.definition;

/* Bean 垃圾回收 示例
 *
 * @author Molly
 * @date 2020/4/18
 */

import org.geekbang.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class BeanGarbageCollection {
    public static void main(String[] args) {
        //创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注册 配置类（configuration class）
        applicationContext.register(BeanInitializationDemo.class);

        //启动应用上下文
        applicationContext.refresh();

        //关闭应用上下文（显式）
        applicationContext.close();//这个过程中调用了销毁方法
        System.out.println("Spring 应用上下文已关闭...");

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 强制触发GC
        System.gc();//回调finalize() 但是不会稳定回调
    }
}
