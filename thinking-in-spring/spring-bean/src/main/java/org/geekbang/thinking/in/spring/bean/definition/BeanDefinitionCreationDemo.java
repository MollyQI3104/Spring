package org.geekbang.thinking.in.spring.bean.definition;

/*
 * BeanDefinition 构建示例
 * BeanDefinition元信息 如何定义
 *
 * @author Molly
 * @date 2020/4/17
 */

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import javax.swing.*;

public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {
        //1.通过 BeanDefinitionBuilder 构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        //通过属性设置
        //类似在xml文件中配置 xml中的value都是字符类型
        //可以使用链式 便利操作
        beanDefinitionBuilder.addPropertyValue("id",1)
                .addPropertyValue("name","Molly");
        //获取 BeanDefinition 实例
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        //BeanDefinition 并非 Bean 终态。 可以自定义修改

        //2. 通过 AbstractBeanDefinition 以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        //设置Bean类型 Bean类型需要是具体类
        genericBeanDefinition.setBeanClass(User.class);
        //通过 MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue("id",1);
//        propertyValues.addPropertyValue("name","Molly");
        propertyValues.add("id",1)
                .add("name","Molly");
        //通过 set MutablePropertyValues 批量操作属性
        genericBeanDefinition.setPropertyValues(propertyValues);


    }
}
