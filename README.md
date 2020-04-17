# geekbang-lessons
极客时间课程工程
学习笔记

## 1.认识pom.xml中依赖管理与配置，名称一般与spring模块对应

## 2.依赖查找
### 1.根据Bean名称（id）查找
*   实时查找
*   延时查找
### 2.根据Bean类型查找
*   单个Bean对象
*   集合Bean对象
### 3.根据Bean的id + 类型查找
*   User user = (User) beanFactory.getBean("user",User.class);
### 4.根据java注解查找
*   单个Bean对象
*   集合Bean对象


## 3.依赖注入，依赖来源，ApplicationContext和BeanFactory的关系
## 4.ApplicationContext 和 BeanFactory作为IoC容器
## 5. BeanDefinition元信息 如何定义
   两种方法： 
   1.通过 BeanDefinitionBuilder 构建 
   2.通过 AbstractBeanDefinition 以及派生类
   
