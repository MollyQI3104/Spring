package org.geekbang.thinking.in.spring.bean.definition;

/* Bean 别名示例
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAliasDemo {

    public static void main(String[] args) {
        //配置 XML 配置文件
        //启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/bean-definition-context.xml");
        //通过别名 molly-user 获取曾用名 user 的 bean
        User mollyUser = (User) beanFactory.getBean("molly-user", User.class);
        User user = (User) beanFactory.getBean("user", User.class);

        System.out.println("user 是否和 molly-user Bean 相同：" + (user == mollyUser));
        //true

    }
}
