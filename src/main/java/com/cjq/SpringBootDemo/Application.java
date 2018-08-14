package com.cjq.SpringBootDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 启动类的扫描默认是扫描与该启动类同包以及其子包下的类
 * 如何自定义自动扫描的包的位置？
 * 例：
 * //自定义自动扫描的包
 * @ComponentScan(basePackages = {"com.test1","com.test2"})
 * @author v.cuijq
 */
@SpringBootApplication
@ServletComponentScan //配置druid必须加的注解，如果不加，访问页面打不开，filter和servlet、listener之类的需要单独进行注册才能使用，spring boot里面提供了该注解起到注册作用
//@EnableTransactionManagement   //启用事务  有些说可以不加 ,有些说要加(亲测不加也能实现事务管理) 等同于xml配置方式的 <tx:annotation-driven>
//@MapperScan("com.cjq.SpringBootDemo.mapper") //默认配置下mybatis的mapper的扫描
public class Application {

	
	@Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager){
        System.out.println("=================使用的事务管理器类型：" + platformTransactionManager.getClass().getName());
        return new Object();
    }
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
